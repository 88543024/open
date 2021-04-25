package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.TDataDic;
import models.TImage;
import models.TMsWorkorder;
import models.TResVM;
import models.VProjectVMSum;
import models.VResPool;
import models.VmSingle;
import models.record.TResOperationLog;
import play.Logger;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import rest.ContactClient;
import rest.ModifyContact;
import rest.MyContactClient;
import rest.ReStartServerContact;
import rest.RemoveServerContact;
import rest.StopServerContact;
import soap.ChangePassword;
import utils.MailUtil;
import utils.Portal_JdbcUtils;
//资源管理
@CRUD.For(TResVM.class)
public class ResourceVMs extends CRUD{

	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//显示子菜单
	public static void menuPro(){
	    List<TResVM> vmList = TResVM.find("select t.SYS_INFO from TResVM t group by SYS_INFO").fetch();
		StringBuffer menuList = new StringBuffer();
		for(int i=0; i<vmList.size();i++){
			menuList.append("<li>");
			menuList.append("<a href=\"/ResourceVMs/menuViews?ProName="+vmList.get(i)+"\"><span class=\"mm-text\">"+vmList.get(i)+"</span></a>");
			menuList.append("</li>");
		}
		renderJSON(menuList);
	}
	//显示子菜单内容
	public static void menuViews(){
		String proName = params.get("ProName");
	    List<TResVM> vmList = TResVM.find("select t from TResVM t where t.SYS_INFO = '"+proName+"'").fetch();
	    Connection conn = null;
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    List<Model> objects = new ArrayList<Model>();
	    try{
	    	conn = Portal_JdbcUtils.getConnection();
	    	for(TResVM vm : vmList){
	    		
	    		vm.CREATE_TIME = dateFormat(vm.CREATE_TIME);
	         	vm.PENDING_TIME = dateFormat(vm.PENDING_TIME);
	         	
	    		//内存平均利用率
		    	//st = conn.prepareStatement("select \"Used_Real_Mem_Pct\" from \"Unix_Memory\" where \"System_Name\" like '"+vm.NAME+"%' order by \"WRITETIME\" ;");
	         	st = conn.prepareStatement("select Used_Real_Mem_Pct from Unix_Memory where System_Name like '"+vm.NAME+"%' order by WRITETIME desc");
		    	rs = st.executeQuery();
		    	while(rs.next()){
		    		String MemRate = rs.getString(1);
		    		vm.MemoryUSERate = MemRate;
		    		//System.out.println(MemRate);
		    	}
		    	//Cpu平均百分比
		    	//st = conn.prepareStatement("select \"t.CPU_Usage\" from (select \"CPU_Usage\" from \"SMP_CPU\" where \"System_Name\" like '"+vm.NAME+"%' order by \"WRITETIME\" desc) t");
		    	st = conn.prepareStatement("select CPU_Usage from SMP_CPU where System_Name like '"+vm.NAME+"%' order by WRITETIME desc");
		    	rs = st.executeQuery();
		    	rs = st.executeQuery();
		    	while(rs.next()){
		    		String CpuRate = rs.getString(1);
		    		vm.CpuUSERate = CpuRate;
		    	}
		    	//RootVg平均百分比
		    	//st = conn.prepareStatement("select \"t.Used_Pct\" from (select \"Used_Pct\" from \"AIX_Volume_Groups\" where \"System_Name\" like '"+vm.NAME+"%' and \"Name\" = 'rootvg' order by \"WRITETIME\" desc) t");
		    	st = conn.prepareStatement("select Used_Pct from AIX_Volume_Groups where System_Name like '"+vm.NAME+"%' and Name = 'rootvg' order by WRITETIME desc");
		    	rs = st.executeQuery();
		    	while(rs.next()){
		    		String RootVgRate = rs.getString(1);
		    		vm.RootVgRate =RootVgRate; 
		    	}
		    	//DataVg平均百分比
		    	st = conn.prepareStatement("select Used_Pct from AIX_Volume_Groups where System_Name like '"+vm.NAME+"%' and Name = 'datavg' order by WRITETIME desc");
		    	rs = st.executeQuery();
		    	while(rs.next()){
		    		String DataVgRate = rs.getString(1);
		    		vm.DataVgRate = DataVgRate;
		    	}
		    	objects.add(vm);
	    	}
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	Portal_JdbcUtils.free(rs, st, conn);
	    }
	    render(objects,proName);
	}
	
	public static void list(int page, String search, String searchFields, String orderBy, String order) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
//      List<Model> objects = type.findPage(page, search, searchFields, orderBy, order, (String) request.args.get("where"));
        List<Model> objects = new ArrayList<Model>();
        List<TResVM> vmList = TResVM.findAll();
        for(int i=0;i<vmList.size();i++){
        	TResVM vm = vmList.get(i);
        	vm.CREATE_TIME = dateFormat(vm.CREATE_TIME);
        	vm.PENDING_TIME = dateFormat(vm.PENDING_TIME);
        	objects.add(vm);
        }
        
        Long count = type.count(search, searchFields, (String) request.args.get("where"));
        Long totalCount = type.count(null, null, (String) request.args.get("where"));
        try {
            render(type, objects, count, totalCount, page, orderBy, order);
        } catch (TemplateNotFoundException e) {
            render("CRUD/list.html", type, objects, count, totalCount, page, orderBy, order);
        }
    }
	
	
	public static void view(Long id){
		TResVM object = TResVM.findById(id);
		try{
			object.CREATE_TIME = dateFormat(object.CREATE_TIME);
			object.PENDING_TIME = dateFormat(object.PENDING_TIME);
			String UPDATE_TIME = object.UPDATE_TIME;
			if(UPDATE_TIME!=null && UPDATE_TIME != ""){
				object.UPDATE_TIME = dateFormat(UPDATE_TIME);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	    render(object);
	}
	public static void viewUp(Long id){
		TResVM object = TResVM.findById(id);
	    render(object);
	}
	
	public static void sendMs(){
		//String wkId = params.get("wkId"); //工单ID
		String id = params.get("id"); //资源ID
		String rootPas =  params.get("rootPas"); //虚拟机root密码
		boolean flag = false;
		List<TMsWorkorder> tmsList = new ArrayList<TMsWorkorder>();
		try{
			//if(wkId ==null || wkId == "0" ){
			
				Long vmID = Long.valueOf(id);
				
				TResVM object = TResVM.findById(vmID);
				TMsWorkorder tmsModel = new TMsWorkorder();
				tmsModel.vcpu = object.CPU_NUMBER.toString();
				tmsModel.memory = object.MEMORY_SIZE.toString();
				tmsModel.os = object.OS;
				//tmsModel.dbsoft = object.dbsoft;
				//tmsModel.middleware = object.middleware;
				//tmsModel.disksize = object.disksize;
				tmsModel.hostname = object.NAME;
				tmsModel.ip = object.IP;
				tmsModel.username = "root";
				tmsModel.password = rootPas;
				tmsList.add(tmsModel);
				MailUtil.callMsWS(tmsList, null);
			//}else{
				//Workorder tmsModel = Workorder.findById(id);
			/*	Long wkID = Long.valueOf(wkId);
				MailUtil.callMsWS(tmsList, wkID);*/
				
			//}
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		renderJSON(flag);
	}
	
	
	
	public static void blank() {
		
		List<TDataDic> dicList = TDataDic.findAll();
		List<TDataDic> osPatchList = new ArrayList();
		List<TDataDic> dbPatchList = new ArrayList();
		List<TDataDic> webPatchList = new ArrayList();
		List<TDataDic> cpuList = new ArrayList();
		List<TDataDic> memoryList = new ArrayList();
		List<TDataDic> networkSegment = new ArrayList();
		
		for (TDataDic dic : dicList) {
			if (dic.DIC_TYPE != null && "OSPATCH".equals(dic.DIC_TYPE)) {
				//osPatchList.add(dic);
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
		osPatchList = getImageOs();
		List<VResPool> vResPools = VResPool.getVResPool();
		VResPool vResP = new VResPool();
		if(vResPools != null && vResPools.size() >0){
			vResP = vResPools.get(0);
		}
		render(osPatchList, dbPatchList,webPatchList, cpuList, memoryList,vResP,networkSegment);
    }
	public static void applyRes() {
		String flag = "0";
		String submitType = params.get("submitType");// 提交按钮的类型：1资源单独申请；2返回
		VmSingle obj = null;
		if (submitType != null && "2".equals(submitType)) {
			redirect(request.controller + ".list");
		} else {
			String countStr = params.get("count");
			Integer count = Integer.valueOf(countStr);
			String projectName = params.get("projectName");
			String memory = params.get("memory");
			String os = params.get("os");
			String dbsoft = params.get("dbsoft");
			String middleware = params.get("middleware");
			String disksize = params.get("disksize");
			String vcpu = params.get("vcpu");
			String segment = params.get("segment");
			
			obj = new VmSingle();
			obj.VmCount = count;
			obj.dbSoft = dbsoft;
			obj.memorySize = memory;
			obj.middleWare = middleware;
			obj.diskSize = disksize;
			obj.vcpuNum = vcpu;
//			obj.status = "6";//1未处理;2提交中3提交成功;4提交失败;5虚拟机创建中;6虚拟机创建完成;7虚拟机创建失败;8手工操作完成
			obj.status = "4";
			obj.projectName = projectName;
			obj.applyTime = sdf1.format(new Date());
			String imageId = "";
			if(os != null){
				TImage image = getImageByOs(os);
				imageId = image.image_id;
				os = image.OS_PATCH;
			}
	/*		if(memory != null ){
				int intM = 0;
				try{
				    intM = Integer.valueOf(memory);
					intM = intM * 1024;
				}catch(Exception e){
					e.printStackTrace();
				}
				memory = Integer.toString(intM);
			}*/
			obj.sysOs = os;
			//调用虚机申请接口do something
			try {
				//判断项目名称是projectName的项目是否存在，存在调用追加方法。
				if(getProCount(projectName) != 0){
					ContactClient.addProject(memory, os, dbsoft,
							middleware, disksize+"G", vcpu,
							0, projectName,
							imageId, count + "", null,segment);
				}else{
				    MyContactClient.createProject(memory, os, dbsoft,
						middleware, disksize, vcpu,
						0, obj.projectName,
						imageId, obj.VmCount + "", null,segment);
				}
				obj.status = "3";// 虚拟机创建成功
				flag = "3";
				TResOperationLog oPer = new TResOperationLog();
				oPer.WORK_ORDER_ID = "0";
				oPer.OPERATION_MAN = session.get("username");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				String time = df.format(new Date()); //当前系统时间
				oPer.OPERATION_TIME = time;
				oPer.OPERATION_CONTENT = "单独申请了资源，项目"+projectName+"下创建了"+count+"台虚拟机。";
				oPer.save();
			} catch (Exception e) {
				obj.status = "4";// 虚拟机创建失败
				flag = "4";
				e.printStackTrace();
			}
			obj.save();
		}
		renderJSON(flag);
    }
	
	//虚拟机删除
	public static void delRes() {
		String flag = "0";//提交失败
		String vsId = params.get("vsId");//虚机ID
		try {
			RemoveServerContact.modifyServer(vsId);
			flag = "1";//提交成功
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJSON(flag);
	}
		
	//虚拟机挂起
	public static void hangRes() {
		String flag = "0";//提交失败
		String vsId = params.get("vsId");//虚机ID
		try {
			StopServerContact.modifyServer(vsId);
			flag = "1";//提交成功
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJSON(flag);
	}
	
	//虚拟机重启
	public static void restartRes() {
		String flag = "0";//提交失败
		String vsId = params.get("vsId");//虚机ID
		try {
			ReStartServerContact.modifyServer(vsId);
			flag = "1";//提交成功
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJSON(flag);
	}
	
	//跳转到虚拟机调整页面
	public static void adjust(Long id,String vsId) {
		TResVM obj = TResVM.findById(id);
		String projectName = "";
		if(obj != null){
		 projectName = obj.SYS_INFO;
		}
		List<TDataDic> dicList = TDataDic.findAll();
		List<TDataDic> cpuList = new ArrayList();
		List<TDataDic> memoryList = new ArrayList();
		for (TDataDic dic : dicList) {
			if (dic.DIC_TYPE != null && "VCPU_NUM".equals(dic.DIC_TYPE)) {
				cpuList.add(dic);
			} else if (dic.DIC_TYPE != null && "MEMORY_SIZE".equals(dic.DIC_TYPE)) {
				memoryList.add(dic);
			}
		}
		render(obj, cpuList, memoryList,projectName);
	}
	//跳转到修改密码页面
	public static void modifyPasView(Long id){
		TResVM obj = TResVM.findById(id);
		render(obj);
	}
	//重置密码
	public static void modifypas(){
		String vmId = params.get("vmId");
		boolean flag = false;
		try{
			Long resId = Long.valueOf(vmId);
			TResVM obj = TResVM.findById(resId);
			String id = obj.DEVICE_ID;
			ChangePassword.changePassword("", "", "", id);
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			renderJSON(flag);
		}
	}
	
	//虚拟机调整
	public static void adjustRes() {
		String flag = "0";//提交失败
		String vsId = params.get("vsId");//虚机ID
		String vcpu = params.get("vcpu");//虚拟cpu
		String memory = params.get("memory");//内存
		String disksize = params.get("disksize");//磁盘大小
		try {
			ModifyContact.modifyServer(vsId, memory, disksize, vcpu);
			flag = "1";//提交成功
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJSON(flag);
	}
	
	//日期格式转换
	//String str = "2014-11-04T01:17:10+08:00";
	public static String dateFormat(String str){
		String datetime = str;
		if(str!=null&&str.length()>=19){
			String dateStr = str.substring(0,10);
	        String timeStr = str.substring(11,16);
	        datetime = dateStr +" "+ timeStr;
		}
        return datetime;
	}
	
	//验证项目名称是否存在
	public static void verifyHas(){
		String flag = "0";//不存在
		int num = 0;
		String projectName = params.get("projectName");//虚机ID
		num = getProCount(projectName);
		if(num > 0){
			flag = "1"; //存在
		}
		renderJSON(flag);
	}
	
	//获取申请项目资源名称是否存在
	public static int getProCount(String name) {
		int num = 0;
		PreparedStatement st = null;
    	ResultSet rs = null;
		Connection conn = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) as num from \"v_sinopec_project_vm_sum\" where " ).append("1=1");
		sql.append(" and PROJECT_NAME = '").append(name).append("'");
        Logger.info("sql = "+sql);
        try {
			conn = Portal_JdbcUtils.getConnection();
			 st = conn.prepareStatement(sql.toString());
			 rs = st.executeQuery();
			 if(rs!=null){
				 while(rs.next()){
					 num = rs.getInt("num");
				 }
			 }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	
	//获取系统镜像信息
	public static List<TDataDic> getImageOs(){
		List<TDataDic> osPatchList = new ArrayList();
		try{
		List<TImage> images= TImage.find("IS_REGISTER", "1").fetch();
		if(images != null && images.size()>0){
			for(TImage image : images){
				TDataDic dic = new TDataDic();
				dic.VALUE = image.imageName;
				dic.DISPLAY_VALUE = image.imageName;
				dic.DISPLAY_VALUE_DESC = image.image_id; //注册的镜像id
				osPatchList.add(dic);
			}
		}
	   }catch(Exception e){
		   e.printStackTrace();
	   }
		return osPatchList;
	}
	//获取系统镜像信息
	public static TImage getImageByOs(String os){
		TImage image = new TImage();
		try{
		List<TImage> images= TImage.find("image_id",os).fetch();
		if(images != null && images.size()>0){
			image = images.get(0);
		}
	   }catch(Exception e){
		   e.printStackTrace();
	   }
		return image;
	}
}
