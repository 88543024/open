package controllers;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;

import play.Logger;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import controllers.CRUD.ObjectType;

import models.TConfigUser;
import models.TDataDic;
import models.TImage;
import models.TMsWorkorder;
import models.TResVM;
import models.TWorkorder;
import models.record.SysLog;
import models.record.SysLog.LogType;
import rest.ContactClient;
import rest.MyContactClient;
import utils.MailUtil;

@CRUD.For(TWorkorder.class)
public class Orders extends CRUD {
	private static SimpleDateFormat sdf1 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 未处理工单查询
	 */
	public static void listUnFinish(){
		String where = "";
		where = "STATUS != '5' or STATUS IS NULL ";
		String finishType = "0"; //未完成工单
		list(1, null, null, "ELEMENT_ID", "ASC" ,where,finishType);
	}
	/**
	 * 查询已经处理完成的工单
	 */
	public static void listFinish(){
		String where = "";
		where = "STATUS = '5' ";
		String finishType = "1"; //已经完成工单
		list(1, null, null, "ELEMENT_ID", "ASC" ,where,finishType);
	}
	
	/**
	 * 查询已经处理完成的工单
	 */
	public static void list(int page, String search, String searchFields, String orderBy, String order ,String where,String finishType) {
    	ObjectType type = ObjectType.get(Orders.class);
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        Logger.info("where: " + where);
        List<Model> objects = type.findPage(page, search, searchFields, orderBy, order, where);
        Long count = type.count(search, searchFields, where);
        Long totalCount = type.count(null, null, where);
        try {
            render("Orders/orderHas.html",type, objects, count, totalCount, page, orderBy, order,finishType);
        } catch (TemplateNotFoundException e) {
            render("CRUD/list.html", type, objects, count, totalCount, page, orderBy, order);
        }
    }
	
	/**
	 * 展示工单详细信息
	 * 
	 * @param id
	 */
	public static void view(Long id) {
		TWorkorder object = TWorkorder.findById(id);
		render(object);
	}
	public static void vmList(Integer ELEMENT_ID){
		List<TMsWorkorder> objs = new ArrayList();// 微软工单
		if (ELEMENT_ID != null) {
			objs = TMsWorkorder.getOrderListByElementId(ELEMENT_ID);
		}
		render(objs);
	} 
	public static void mslist(Long id) {
		TWorkorder object = TWorkorder.findById(id);
		Integer elementID = object.ELEMENT_ID;
		List<TMsWorkorder> objects = TMsWorkorder.getOrderListByElementId(elementID);
		render(objects,object);
	}
	public static void viewUp(String id) {
		String elementId = params.get("elementId");
		TMsWorkorder object = new TMsWorkorder();
		TWorkorder tobject = new TWorkorder();
		// 数据字典
		List<TDataDic> dicList = TDataDic.findAll();
		List<TDataDic> osPatchList = new ArrayList();
		List<TDataDic> dbPatchList = new ArrayList();
		List<TDataDic> webPatchList = new ArrayList();
		List<TDataDic> cpuList = new ArrayList();
		List<TDataDic> memoryList = new ArrayList();
		
		//已注册镜像
		List<TImage> imageList = TImage.find("IS_REGISTER", "1").fetch();
		try{
			Long ID = Long.valueOf(id);
			tobject = TWorkorder.getWorkOrderByEleId(elementId);
			object =  TMsWorkorder.findById(ID);
			for (TDataDic dic : dicList) {
				if (dic.DIC_TYPE != null && "OSPATCH".equals(dic.DIC_TYPE)) {
					osPatchList.add(dic);
				} else if (dic.DIC_TYPE != null && "DBPATCH".equals(dic.DIC_TYPE)) {
					dbPatchList.add(dic);
				} else if (dic.DIC_TYPE != null && "WEBPATCH".equals(dic.DIC_TYPE)) {
					webPatchList.add(dic);
				} else if (dic.DIC_TYPE != null && "VCPU_NUM".equals(dic.DIC_TYPE)) {
					cpuList.add(dic);
				} else if (dic.DIC_TYPE != null
						&& "MEMORY_SIZE".equals(dic.DIC_TYPE)) {
					memoryList.add(dic);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		render(object,tobject,osPatchList, dbPatchList,webPatchList, cpuList, memoryList,imageList);
	}
	
	public static void sendMs(){
		String id = params.get("id"); //工单ID
		boolean flag = false;
		List<TMsWorkorder> tmsList = new ArrayList<TMsWorkorder>();
		try{
				Long wkID = Long.valueOf(id);
				TWorkorder workOrd = TWorkorder.findById(wkID);
				Integer elementId = workOrd.ELEMENT_ID;
				tmsList = TMsWorkorder.getOrderListByElementId(elementId);
				flag = MailUtil.callMsWS(tmsList, wkID);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		renderJSON(flag);
	}
	
	/**
	 * 弹出派单页面
	 * 
	 * @param id
	 */
	public static void assign(Long id) {
		TWorkorder object = TWorkorder.findById(id);
		List<TMsWorkorder> objs = new ArrayList();
		if (object.ELEMENT_ID != null) {
			objs = getTMsWorkOrders(object.ELEMENT_ID);
		}
		List<TConfigUser> userlist = TConfigUser.findAll();
		List<TConfigUser> users = new ArrayList<TConfigUser>();
		for (int i = 0; i < userlist.size(); i++) {
			if (userlist.get(i).PERMISSION != null) {
				if ("operator".equalsIgnoreCase(userlist.get(i).PERMISSION.trim())) {
					users.add(userlist.get(i));
				}
			}
		}
		render(object, users,objs);
	}

	public static void selOper() {
		List<TConfigUser> userlist = TConfigUser.findAll();
		List<TConfigUser> opusers = new ArrayList<TConfigUser>();
		for (int i = 0; i < userlist.size(); i++) {
			if (userlist.get(i).PERMISSION != null) {
				if ("operator".equalsIgnoreCase(userlist.get(i).PERMISSION.trim())) {
					opusers.add(userlist.get(i));
				}
			}
		}
		render(opusers);
	}

	/**
	 * 派单操作
	 * 
	 * @param id
	 * @param userId
	 */
	public static void assignOrder(Long id, Long userId) {
		TWorkorder object = TWorkorder.findById(id);
		TConfigUser users = TConfigUser.findById(userId);
		object.setOPERATOR(users.USER_NAME);// 工单执行人（由派单操作指定？）
		object.setASSIGNER(session.get("username"));// 分配人（派单人）
		object.setSTATUS("3");// 3我方工单状态（已配单）
		object.save();
		
		//系统日志记录
		SysLog slog = new SysLog();
		slog.LOG_TYPE = LogType.ORDER_OPERATE;
		slog.LOG_CONTENT = session.get("username")+"派单给"+users.USER_NAME;
		slog.OPERATION_MAN = session.get("username");
		slog.CREATE_LOG_TIME = new Date();
		slog.save();
		redirect(request.controller + ".list");
	}

	/**
	 * 跳转到工单备注页面----对完成状态的工单备注（我方工单）
	 * 
	 * @param id
	 */
	public static void alertMemo(Long id) {
		TWorkorder object = TWorkorder.findById(id);
		List<TMsWorkorder> objs = new ArrayList();
		if (object.ELEMENT_ID != null) {
			objs = TMsWorkorder.getOrderListByElementId(object.ELEMENT_ID);
		}
		render(object,objs);
	}

	/**
	 * 对完成状态的工单进行备注操作（我方工单）
	 * 
	 * @param id
	 */
	public static void addMemo(Long id) {
		TWorkorder object = TWorkorder.findById(id);
		String remark = params.get("remark");
		if (remark != null && !"".equals(remark.trim())) {
			String oldRemark = null2Str(object.getREMARK()).trim();
			String operator = object.getOPERATOR();// 执行人
			Date date = new Date();
			String dateStr = sdf1.format(date);
			String newRemark = null2Str(oldRemark) + "  "+ null2Str(remark.trim()) + null2Str(operator) + "|"	+ dateStr + "     ";
			if(newRemark.length()<500){
				object.setREMARK(newRemark);
			}
			object.save();
		}
		redirect(request.controller + ".list");
	}

	/**
	 * 跳转到执行工单的页面（即列出我方工单的项目信息，允许续加备注）
	 * 
	 * @param id
	 */
	public static void operate(Long id) {
		TWorkorder object = TWorkorder.findById(id);
		List<TMsWorkorder> objList = null;// 微软工单
		Integer flag = 0;// 无微软工单关联
		Integer osFlag = 0;// os版本信息不存在
		Integer dbFlag = 0;// db版本信息不存在
		Integer middlewareFlag = 0;// middleware版本信息不存在
		String os_will = "";// 要添加的OS版本字符串组合，已，分隔
		String db_will = "";// 要添加的DB版本字符串组合，已，分隔
		String web_will = "";// 要添加的MiddleWare版本字符串组合，已，分隔
		if (object.ELEMENT_ID != null) {
			objList = TMsWorkorder.getOrderListByElementId(object.ELEMENT_ID);
			if (objList != null && objList.size() > 0) {
				flag = 1;// 有微软工单关联
				for (int i = 0; i < objList.size(); i++) {
					String os = objList.get(i).os;
					TDataDic osDic = TDataDic.getDicByTypeAndValue("OSPATCH",os);
					if (osDic != null) {
						osFlag = 1;
						break;
					} else {
						os_will = os_will + os + ",";
					}
				}
				for (int i = 0; i < objList.size(); i++) {
					String dbsoft = objList.get(i).dbsoft;
					if(dbsoft == null){
						dbFlag = 1;
						break;
					}
					TDataDic dbDic = TDataDic.getDicByTypeAndValue("DBPATCH",dbsoft);
					if (dbDic != null) {
						dbFlag = 1;
						break;
					} else {
						db_will = db_will + dbsoft + ",";
					}
				}
				for (int i = 0; i < objList.size(); i++) {
					String web = objList.get(i).middleware;
					TDataDic middlewareDic = TDataDic.getDicByTypeAndValue("WEBPATCH", web);
					if(web == null){
						middlewareFlag = 1;
						break;
					}
					if (middlewareDic != null) {
						middlewareFlag = 1;
						break;
					} else {
						web_will = web_will + web + ",";
					}
				}

			}
		}

		render(object,objList,flag, osFlag, dbFlag, middlewareFlag, os_will, db_will,
				web_will);
	}

	
	/**
	 * 第一步：资源申请（处理微软工单）
	 * 
	 * @param id
	 */
	/*public static void lookOrEditVM(Long id) {
		TWorkorder object = TWorkorder.findById(id);
		String remark = params.get("remark");
		if (remark != null && !"".equals(remark.trim())) {
			String oldRemark = null2Str(object.getREMARK()).trim();
			String operator = object.getOPERATOR();// 执行人
			Date date = new Date();
			String dateStr = sdf1.format(date);
			object.setREMARK(null2Str(oldRemark) + "  "
					+ null2Str(remark.trim()) + null2Str(operator) + "|"
					+ dateStr + "     ");
		}
		if (session.get("permission") != null
				&& ("supermanager".equals(session.get("permission")) || "manager"
						.equals(session.get("permission")))) {
			object.setOPERATOR(session.get("username"));
		}

		TMsWorkorder obj = null;// 微软工单
		if (object.ELEMENT_ID != null) {
			obj = getTMsWorkOrder(object.ELEMENT_ID);
		} else {
			obj = new TMsWorkorder();
			obj.ELEMENT_ID = object.ELEMENT_ID;
		}
		if (obj == null) {
			obj = new TMsWorkorder();
			obj.ELEMENT_ID = object.ELEMENT_ID;
			object.setSTATUS("4");// 4我方工单处理中(执行中)
		}
		object.save();
		Long tWorkOrderId = id;// 我方工单的主键ID

		// 数据字典
		List<TDataDic> dicList = TDataDic.findAll();
		List<TDataDic> osPatchList = new ArrayList();
		List<TDataDic> dbPatchList = new ArrayList();
		List<TDataDic> webPatchList = new ArrayList();
		List<TDataDic> cpuList = new ArrayList();
		List<TDataDic> memoryList = new ArrayList();
		
		List<TImage> imageList = TImage.find("IS_REGISTER", "1").fetch();
		
		for (TDataDic dic : dicList) {
			if (dic.DIC_TYPE != null && "OSPATCH".equals(dic.DIC_TYPE)) {
				osPatchList.add(dic);
			} else if (dic.DIC_TYPE != null && "DBPATCH".equals(dic.DIC_TYPE)) {
				dbPatchList.add(dic);
			} else if (dic.DIC_TYPE != null && "WEBPATCH".equals(dic.DIC_TYPE)) {
				webPatchList.add(dic);
			} else if (dic.DIC_TYPE != null && "VCPU_NUM".equals(dic.DIC_TYPE)) {
				cpuList.add(dic);
			} else if (dic.DIC_TYPE != null
					&& "MEMORY_SIZE".equals(dic.DIC_TYPE)) {
				memoryList.add(dic);
			}
		}
		if (obj.status != null && ("3".equals(obj.status))|| "6".equals(obj.status)) {// 虚拟机创建已完成或者虚拟机调整完成
			render("/Orders/manual.html", obj, tWorkOrderId, object,imageList);
		} else {
			render(obj, tWorkOrderId, object, osPatchList, dbPatchList,
					webPatchList, cpuList, memoryList,imageList);
		}

	}*/
	
	/**
	 * 第一步：资源申请（处理微软工单）
	 * 
	 * @param id
	 */
	public static void lookOrEditVMS(Long id) {
		TWorkorder object = TWorkorder.findById(id);
		String remark = params.get("remark");
		if (remark != null && !"".equals(remark.trim())) {
			String oldRemark = null2Str(object.getREMARK()).trim();
			String operator = object.getOPERATOR();// 执行人
			Date date = new Date();
			String dateStr = sdf1.format(date);
			String newRemark = null2Str(oldRemark) + "  "+ null2Str(remark.trim()) + null2Str(operator) + "|"	+ dateStr + "     ";
			if(newRemark.length()<500){
				object.setREMARK(newRemark);
			}
		}
		if(session.get("permission") != null && ("supermanager".equals(session.get("permission")) || "manager".equals(session.get("permission")))) {
			object.setOPERATOR(session.get("username"));
		}
		
		TMsWorkorder TMsobj = new TMsWorkorder();// 微软工单
		List<TMsWorkorder> objs = new ArrayList();
		if (object.ELEMENT_ID != null) {
			objs = getTMsWorkOrders(object.ELEMENT_ID);
			if(objs!=null && objs.size()>0){
				TMsobj = objs.get(0);
			}
		}
		object.save();
		Long tWorkOrderId = id;// 我方工单的主键ID
		// 数据字典
		List<TDataDic> dicList = TDataDic.findAll();
		List<TDataDic> osPatchList = new ArrayList();
		List<TDataDic> dbPatchList = new ArrayList();
		List<TDataDic> webPatchList = new ArrayList();
		List<TDataDic> cpuList = new ArrayList();
		List<TDataDic> memoryList = new ArrayList();
		List<TDataDic> networkSegment = new ArrayList();
		List<TImage> imageList = TImage.find("IS_REGISTER", "1").fetch();
		
		for (TDataDic dic : dicList) {
			if (dic.DIC_TYPE != null && "OSPATCH".equals(dic.DIC_TYPE)) {
				osPatchList.add(dic);
			} else if (dic.DIC_TYPE != null && "DBPATCH".equals(dic.DIC_TYPE)) {
				dbPatchList.add(dic);
			} else if (dic.DIC_TYPE != null && "WEBPATCH".equals(dic.DIC_TYPE)) {
				webPatchList.add(dic);
			} else if (dic.DIC_TYPE != null && "VCPU_NUM".equals(dic.DIC_TYPE)) {
				cpuList.add(dic);
			} else if (dic.DIC_TYPE != null && "MEMORY_SIZE".equals(dic.DIC_TYPE)) {
				memoryList.add(dic);
			}else if(dic.DIC_TYPE !=null 
					&&"SEGMENT".endsWith(dic.DIC_TYPE)){
				networkSegment.add(dic);
			}
		}
		if (TMsobj.status != null && ("3".equals(TMsobj.status))|| "6".equals(TMsobj.status)) {// 虚拟机创建已完成或者虚拟机调整完成
			render("/Orders/batchManual.html", TMsobj, tWorkOrderId, object,imageList,networkSegment);
		} else {
			render(objs, tWorkOrderId, object, osPatchList, dbPatchList,webPatchList, cpuList, memoryList,imageList,networkSegment);
		}

	}

	/**
	 * 第二步：手工完成存储的工作后，为任务标识存储任务调整完成（处理微软工单）
	 * 
	 * @param id
	 */
	public static void manual(Long id) {
		/*TMsWorkorder obj = TMsWorkorder.findById(id);
		String countStr = params.get("count");
		Integer count = null;
		if(countStr!=null&&!"".equals(countStr)){
			count = Integer.valueOf(countStr);
		}
		String memory = params.get("memory");
		String os = params.get("os");
		String dbsoft = params.get("dbsoft");
		String middleware = params.get("middleware");
		String disksize = params.get("disksize");
		String vcpu = params.get("vcpu");
		// String status = params.get("status");
		// String hostname = params.get("hostname");
		// String ip = params.get("ip");
		// String username = params.get("username");
		String tWorkOrderIdStr = params.get("tWorkOrderId");
		Long tWorkOrderId = Long.valueOf(tWorkOrderIdStr);
		TWorkorder object = TWorkorder.findById(tWorkOrderId);// 我方工单
		String ELEMENT_IDStr = params.get("ELEMENT_ID");
		Integer ELEMENT_ID = Integer.valueOf(ELEMENT_IDStr);
		String submitType = params.get("submitType");// 提交按钮的类型：1资源申请2资源删除3挂起4暂停5返回
		String imageId = params.get("imageId");//镜像ID
		if (submitType != null && "5".equals(submitType)) {
			redirect(request.controller + ".list");
		} else {
			obj.count = count;
			obj.memory = memory;
			if(os!=null&&!"".equals(os.trim())&&!"no".equals(os.trim())){
				obj.os = os;
			}
			if(dbsoft!=null&&!"".equals(dbsoft.trim())&&!"no".equals(dbsoft.trim())){
				obj.dbsoft = dbsoft;
			}
			if(middleware!=null&&!"".equals(middleware.trim())&&!"no".equals(middleware.trim())){
				obj.middleware = middleware;
			}
			obj.disksize = disksize;
			obj.vcpu = vcpu;
			// obj.hostname = hostname;
			// obj.ip = ip;
			// obj.username = username;
			obj.ELEMENT_ID = ELEMENT_ID;
			// obj.status = "3";//微软工单虚拟机创建成功
			// obj.status = "4";//微软工单虚拟机创建失败
			//String imageId = "";
			
			//系统日志记录
			SysLog slog = new SysLog();
			slog.LOG_TYPE = LogType.ORDER_OPERATE;
			slog.LOG_CONTENT = session.get("username")+"执行了资源申请操作";
			slog.OPERATION_MAN = session.get("username");
			slog.CREATE_LOG_TIME = new Date();
			slog.save();
			// 调用REST API创建虚拟机
			if (object.APPLY_TYPE != null) {
			if ("资源申请总部".equals(object.APPLY_TYPE)||"资源申请企业".equals(object.APPLY_TYPE)) {
					try {
						MyContactClient.createProject(memory, os, dbsoft,
								middleware, disksize, vcpu,
								object.ELEMENT_ID, object.PROJECT_NAME,
								imageId, obj.count + "", tWorkOrderId);//create project有问题，所以暂时注释，用add
					obj.status = "3";//微软工单虚拟机申请提交成功
					} catch (Exception e) {
						obj.status = "4";//微软工单虚拟机申请提交失败
						e.printStackTrace();
					}
				}else if("资源追加总部".equals(object.APPLY_TYPE)||"资源追加企业".equals(object.APPLY_TYPE)){
					try {
						ContactClient.addProject(memory, os, dbsoft,
								middleware, disksize, vcpu,
								object.ELEMENT_ID, object.PROJECT_NAME,
								imageId, obj.count + "", tWorkOrderId);
						obj.status = "3";//微软工单虚拟机追加提交成功
					} catch (Exception e) {
						obj.status = "4";//微软工单虚拟机追加提交失败
						e.printStackTrace();
					}
				}
				
			}
			
			// boolean b = callMsWS(obj);//微软消息通知接口
			// if(b){
			// obj.status = "3";//微软工单虚拟机创建成功
			// }else{
			// obj.status = "4";//微软工单虚拟机创建失败
			// }

			TWorkorder order = TWorkorder.findById(tWorkOrderId);
			MyRunner myRunner = new MyRunner();
			myRunner.setELEMENT_ID(obj.ELEMENT_ID);
			myRunner.setPROJECT_NAME(order.PROJECT_NAME);
			myRunner.settWorkOrderId(tWorkOrderId);
			Thread t = new Thread(myRunner);
			t.start();
			
			obj.save();
			render(obj, tWorkOrderId);
		}*/

	}

	/**
	 * 第二步：手工完成存储的工作后，为任务标识存储任务调整完成（处理微软工单） 批量申请
	 * 
	 * @param id
	 */				   
	public static void batchManual() {
		//TMsWorkorder obj = TMsWorkorder.findById(id);
		String ELEMENT_IDStr = params.get("ELEMENT_ID");
		Integer ELEMENT_ID = Integer.valueOf(ELEMENT_IDStr);
		//String submitType = params.get("submitType");//是否取消
		String tWorkOrderIdStr = params.get("tWorkOrderId");
		Long tWorkOrderId = Long.valueOf(tWorkOrderIdStr);
		TWorkorder object = TWorkorder.findById(tWorkOrderId);// 我方工单
	
		TMsWorkorder TMsobj = new TMsWorkorder();// 微软工单
		List<TMsWorkorder> objs = new ArrayList();
		if (object.ELEMENT_ID != null) {
			objs = getTMsWorkOrders(object.ELEMENT_ID);
			if(objs!=null && objs.size()>0){
				TMsobj = objs.get(0);
			}
		}
		int num = 0;
		for(TMsWorkorder obj : objs){
			num ++;
			String countStr = params.get(obj.id+"_count");
			Integer count = null;
			if(countStr!=null&&!"".equals(countStr)){
				count = Integer.valueOf(countStr);
			}
			String memory = params.get(obj.id+"_memory");
			String os = params.get(obj.id+"_os");
			String dbsoft = params.get(obj.id+"_dbsoft");
			String middleware = params.get(obj.id+"_middleware");
			String disksize = params.get(obj.id+"_disksize");
			String vcpu = params.get(obj.id+"_vcpu");
			String imageId = params.get(obj.id+"_imageId");//镜像ID
			String segment = params.get(obj.id+"_segment");//选择网络源
			if(count!=null && count>0){
				obj.count = count;
			 }
			if(memory != null){
				obj.memory = memory;
			}
			if(os!=null && !"".equals(os.trim()) && !"no".equals(os.trim())){
				obj.os = os;
			}
			if(dbsoft!=null&&!"".equals(dbsoft.trim())&&!"no".equals(dbsoft.trim())){
				obj.dbsoft = dbsoft;
			}
			if(middleware!=null&&!"".equals(middleware.trim())&&!"no".equals(middleware.trim())){
				obj.middleware = middleware;
			}
			//obj.disksize = disksize;
			obj.vcpu = vcpu;
				// 调用REST API创建虚拟机
			if (object.APPLY_TYPE != null) {
				if ("资源申请总部".equals(object.APPLY_TYPE)||"资源申请企业".equals(object.APPLY_TYPE) && num == 1) {
						try {
							MyContactClient.createProject(memory, os, dbsoft,
									middleware, disksize, vcpu,
									object.ELEMENT_ID, object.PROJECT_NAME,
									imageId, obj.count + "", tWorkOrderId,segment);
							obj.status = "3";//微软工单虚拟机申请提交成功
						} catch (Exception e) {
							obj.status = "4";//微软工单虚拟机申请提交失败
							e.printStackTrace();
						}
					}else if("资源追加总部".equals(object.APPLY_TYPE)||"资源追加企业".equals(object.APPLY_TYPE) || num > 1){
						try {
					      ContactClient.addProject(memory, os, dbsoft,
									 middleware, disksize, vcpu,
									object.ELEMENT_ID, object.PROJECT_NAME,
									imageId, obj.count + "", tWorkOrderId,segment);	
							obj.status = "3";//微软工单虚拟机追加提交成功
						} catch (Exception e) {
							obj.status = "4";//微软工单虚拟机追加提交失败
							e.printStackTrace();
						}
					}
					
				}
				obj.save();
		}
		object.STATUS = "5";
		//系统日志记录
		SysLog slog = new SysLog();
		slog.LOG_TYPE = LogType.ORDER_OPERATE;
		slog.LOG_CONTENT = session.get("username")+"执行了资源申请操作";
		slog.OPERATION_MAN = session.get("username");
		slog.CREATE_LOG_TIME = new Date();
		slog.save();
		render("/Orders/batchManual.html",object,TMsobj);
	}

	/**
	 * 第三步：工单备注（我方工单备注）（调用接口）
	 * 
	 * @param id
	 */
	/*public static void remarkWorkOrder(Integer ELEMENT_ID) {
		String msWorkOrderIdStr = params.get("msWorkOrderId");
		Long msWorkOrderId = Long.valueOf(msWorkOrderIdStr);
		TMsWorkorder obj = TMsWorkorder.findById(msWorkOrderId);
		String step = params.get("step");// 是上一步还是下一步
		String tWorkOrderIdStr = params.get("tWorkOrderId");
		Long tWorkOrderId = Long.valueOf(tWorkOrderIdStr);
		TWorkorder object = TWorkorder.findById(tWorkOrderId);
		if (step != null && "1".equals(step)) {// 手工操作中选择了上一步

			// 数据字典
			List<TDataDic> dicList = TDataDic.findAll();
			List<TDataDic> osPatchList = new ArrayList();
			List<TDataDic> dbPatchList = new ArrayList();
			List<TDataDic> webPatchList = new ArrayList();
			List<TDataDic> cpuList = new ArrayList();
			List<TDataDic> memoryList = new ArrayList();
			for (TDataDic dic : dicList) {
				if (dic.DIC_TYPE != null && "OSPATCH".equals(dic.DIC_TYPE)) {
					osPatchList.add(dic);
				} else if (dic.DIC_TYPE != null
						&& "DBPATCH".equals(dic.DIC_TYPE)) {
					dbPatchList.add(dic);
				} else if (dic.DIC_TYPE != null
						&& "WEBPATCH".equals(dic.DIC_TYPE)) {
					webPatchList.add(dic);
				} else if (dic.DIC_TYPE != null
						&& "VCPU_NUM".equals(dic.DIC_TYPE)) {
					cpuList.add(dic);
				} else if (dic.DIC_TYPE != null
						&& "MEMORY_SIZE".equals(dic.DIC_TYPE)) {
					memoryList.add(dic);
				}
			}
			render("/Orders/lookOrEditVM.html", obj, tWorkOrderId, object,
					osPatchList, dbPatchList, webPatchList, cpuList, memoryList);
		} else if (step != null && "2".equals(step)) {// 手工操作中选择了下一步
			// 是否完成手工操作
			String isSuccess = params.get("isSuccess");
			obj.memo = isSuccess;
			obj.status = "8";// 微软工单完成
			obj.ELEMENT_ID = ELEMENT_ID;
			obj.save();
			TMsWorkorder msOrder = getTMsWorkOrder(obj.ELEMENT_ID);
			if (msOrder == null) {
				object.STATUS = "5";// 我方工单状态完成
			} else {
				object.STATUS = "4";// 我方工单状态执行中
			}
			
			//系统日志记录
			SysLog slog = new SysLog();
			slog.LOG_TYPE = LogType.ORDER_OPERATE;
			slog.LOG_CONTENT = session.get("username")+"进行了手工操作标记，标记为完成";
			slog.OPERATION_MAN = session.get("username");
			slog.CREATE_LOG_TIME = new Date();
			slog.save();
			
			object.save();
			render(obj, tWorkOrderId);
		}

	}*/
	
	/**
	 * 第三步：工单备注（我方工单备注）（调用接口）
	 * 
	 * @param id
	 */
	public static void remarkWorkOrder(Integer ELEMENT_ID) {
		//String msWorkOrderIdStr = params.get("msWorkOrderId");
		//Long msWorkOrderId = Long.valueOf(msWorkOrderIdStr);
		List<TMsWorkorder> objs = getTMsWorkOrders(ELEMENT_ID);
		String step = params.get("step");// 是上一步还是下一步
		String tWorkOrderIdStr = params.get("tWorkOrderId");
		Long tWorkOrderId = Long.valueOf(tWorkOrderIdStr);
		TWorkorder object = TWorkorder.findById(tWorkOrderId);
		
		if (step != null && "1".equals(step)) {// 手工操作中选择了上一步
			// 数据字典
			List<TDataDic> dicList = TDataDic.findAll();
			List<TDataDic> osPatchList = new ArrayList();
			List<TDataDic> dbPatchList = new ArrayList();
			List<TDataDic> webPatchList = new ArrayList();
			List<TDataDic> cpuList = new ArrayList();
			List<TDataDic> memoryList = new ArrayList();
			List<TDataDic> networkSegment = new ArrayList();
			//已注册镜像
			List<TImage> imageList = TImage.find("IS_REGISTER", "1").fetch();
			
			for (TDataDic dic : dicList) {
				if (dic.DIC_TYPE != null && "OSPATCH".equals(dic.DIC_TYPE)) {
					osPatchList.add(dic);
				} else if (dic.DIC_TYPE != null
						&& "DBPATCH".equals(dic.DIC_TYPE)) {
					dbPatchList.add(dic);
				} else if (dic.DIC_TYPE != null
						&& "WEBPATCH".equals(dic.DIC_TYPE)) {
					webPatchList.add(dic);
				} else if (dic.DIC_TYPE != null
						&& "VCPU_NUM".equals(dic.DIC_TYPE)) {
					cpuList.add(dic);
				} else if (dic.DIC_TYPE != null
						&& "MEMORY_SIZE".equals(dic.DIC_TYPE)) {
					memoryList.add(dic);
				}else if(dic.DIC_TYPE !=null 
						&&"SEGMENT".endsWith(dic.DIC_TYPE)){
					networkSegment.add(dic);
				}
			}
			render("/Orders/lookOrEditVMS.html", objs, tWorkOrderId, object,osPatchList, dbPatchList, webPatchList, cpuList, memoryList,imageList,networkSegment);
		} else if (step != null && "2".equals(step)) {// 手工操作中选择了下一步
			// 是否完成手工操作
			String isSuccess = params.get("isSuccess");
			//TMsWorkorder msOrder = getTMsWorkOrder(ELEMENT_ID);
			    /*if (msOrder == null) {
					object.STATUS = "5";// 我方工单状态完成
				} else {
					object.STATUS = "4";// 我方工单状态执行中
				}*/
			if(objs != null && objs.size()>0){
				//object.STATUS = "4";// 我方工单状态执行中
			    for(TMsWorkorder obj : objs){
					obj.memo = isSuccess;
					obj.status = "8";// 微软工单手工操作完成
					obj.save();
				}
			}else{
				//object.STATUS = "5";// 我方工单状态完成
			}
			//系统日志记录
			SysLog slog = new SysLog();
			slog.LOG_TYPE = LogType.ORDER_OPERATE;
			slog.LOG_CONTENT = session.get("username")+"进行了手工操作标记，标记为完成";
			slog.OPERATION_MAN = session.get("username");
			slog.CREATE_LOG_TIME = new Date();
			slog.save();
			//object.save();
			render(object);
		}

	}
	
	//保存账户信息
	public static void saveMSPass(){
		boolean flag = false;
		String id = params.get("id"); //资源ID
		String rootPas =  params.get("rootPas"); //虚拟机root密码
		String username =  params.get("username"); //虚拟机username
		String hostname =  params.get("hostname"); //虚拟机hostname
		String ip =  params.get("ip"); //虚拟机ip
		try{
			Long ID = Long.valueOf(id);
			TMsWorkorder tmsModel = TMsWorkorder.findById(ID);
			tmsModel.username = username;
			tmsModel.hostname = hostname;
			tmsModel.password = rootPas;
			tmsModel.ip = ip;
			tmsModel.save();
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		renderJSON(flag);
	}

	// 给工单添加备注
	public static void addMSRemark(Long id) {
		//TMsWorkorder obj = TMsWorkorder.findById(id);
		String memo = params.get("memo");
		//String tWorkOrderIdStr = params.get("tWorkOrderId");
		// obj.status = "6";//微软工单状态处理完成
		//obj.save();
		if (memo != null && !"".equals(memo.trim()) && id != null && !"".equals(id)) {
			//Long tWorkOrderId = Long.valueOf(tWorkOrderIdStr);
			TWorkorder order = TWorkorder.findById(id);
			String oldRemark = order.getREMARK();
			Date date = new Date();
			String dateStr = sdf1.format(date);
			String operator = order.getOPERATOR();// 执行人
			
			String newRemark = null2Str(oldRemark) + "  "+ null2Str(memo.trim()) + null2Str(operator) + "|"	+ dateStr + "     ";
			if(newRemark.length()<500){
				order.setREMARK(newRemark);
			}
			//order.setREMARK(null2Str(oldRemark).trim() + "	"+ null2Str(memo).trim() +"["+ null2Str(operator) + "|" + dateStr+ "]     ");
			//TMsWorkorder msOrder = getTMsWorkOrder(order.ELEMENT_ID);
			/* if(msOrder==null){
			    order.STATUS = "5";//我方工单状态完成
			 }else{
			    order.STATUS = "4";//我方工单状态执行中
			 }*/
			 
			order.save();
			//系统日志记录
			SysLog slog = new SysLog();
			slog.LOG_TYPE = LogType.ORDER_OPERATE;
			slog.LOG_CONTENT = session.get("username")+"进行了工单备注操作";
			slog.OPERATION_MAN = session.get("username");
			slog.CREATE_LOG_TIME = new Date();
			slog.save();
		}
		
		redirect(request.controller + ".list");
	}

	/**
	 * 查询状态为未处理的微软工单，从查询结果中取出1个返回
	 * 
	 * @param elementId
	 * @return
	 */
	private static TMsWorkorder getTMsWorkOrder(Integer elementId) {
		TMsWorkorder msOrder = null;
		if (elementId != null) {
			List<TMsWorkorder> resultList = TMsWorkorder.getOrderListByElementId(elementId);
			if (resultList != null && resultList.size() > 0) {
				for (TMsWorkorder order : resultList) {
					String status = order.status;
					if (status == null || !"8".equals(status.trim())) {// 8微软工单处理完成（手工操作完成）
						msOrder = order;
					}
				}
			}
		}
		return msOrder;
	}
	
	/**
	 * 返回未完成工单
	 * 
	 * @param elementId
	 * @return
	 */
	private static List<TMsWorkorder> getTMsWorkOrders(Integer elementId) {
		List <TMsWorkorder> msOrders = new ArrayList<TMsWorkorder>();
		if (elementId != null) {
			List<TMsWorkorder> resultList = TMsWorkorder.getOrderListByElementId(elementId);
			if (resultList != null && resultList.size() > 0) {
				for (TMsWorkorder order : resultList) {
					String status = order.status;
					if (status == null || !"8".equals(status.trim())) {// 8微软工单处理完成（手工操作完成）
						msOrders.add(order);
					}
				}
			}
		}
		return msOrders;
	}

	// 查询未处理工单数
	public static void asd() {
		int wcount = 0;
		String permiss = session.get("permission");
		if (permiss != null) {
			if ("supermanager".equals(permiss)) {
				wcount = TWorkorder.getNumForManager();
			} else if ("operator".equals(permiss)) {
				wcount = TWorkorder.getNumForOperator(session.get("username"));
			}
			
		}
		renderJSON(wcount);
	}

	/**
	 * NULL转""
	 * 
	 * @param str
	 * @return str
	 */
	public static String null2Str(String str) {
		if (str == null) {
			str = "";
		}
		return str;
	}

}
