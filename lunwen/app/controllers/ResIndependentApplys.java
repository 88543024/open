package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.TDataDic;
import models.VmSingle;
import rest.ContactClient;
//资源单独申请
@CRUD.For(VmSingle.class)
public class ResIndependentApplys extends CRUD{
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void view(Long id){
		VmSingle object = VmSingle.findById(id);
	    render(object);
	}
	
	/**
	 * 资源申请页面
	 */
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
			}else if(dic.DIC_TYPE !=null 
					&&"SEGMENT".endsWith(dic.DIC_TYPE)){
				networkSegment.add(dic);
			}
		}
		
		render(osPatchList, dbPatchList,webPatchList, cpuList, memoryList,networkSegment);
	}
	
	//跳转到手工操作页面
	public static void manual() {
		String countStr = params.get("count");
		Integer count = Integer.valueOf(countStr);
		String projectName = params.get("projectName");
		String memory = params.get("memory");
		String os = params.get("os");
		String dbsoft = params.get("dbsoft");
		String middleware = params.get("middleware");
		String disksize = params.get("disksize");
		String vcpu = params.get("vcpu");
		String submitType = params.get("submitType");// 提交按钮的类型：1资源单独申请；2返回
		String segment = params.get("segment");
		
		VmSingle obj = null;
		if (submitType != null && "2".equals(submitType)) {
			redirect(request.controller + ".list");
		} else {
			obj = new VmSingle();
			obj.VmCount = count;
			obj.dbSoft = dbsoft;
			obj.memorySize = memory;
			obj.sysOs = os;
			obj.dbSoft = dbsoft;
			obj.middleWare = middleware;
			obj.diskSize = disksize;
			obj.vcpuNum = vcpu;
//			obj.status = "6";//1未处理;2提交中3提交成功;4提交失败;5虚拟机创建中;6虚拟机创建完成;7虚拟机创建失败;8手工操作完成
			obj.status = "4";
			obj.projectName = projectName;
			obj.applyTime = sdf1.format(new Date());
			String imageId = "";
			//调用虚机申请接口do something
			try {
				ContactClient.addProject(memory, os, dbsoft,
						middleware, disksize, vcpu,
						0, projectName,
						imageId, count + "", null,segment);
				obj.status = "3";// 虚拟机创建成功
			} catch (Exception e) {
				obj.status = "4";// 虚拟机创建失败
				e.printStackTrace();
			}
			obj.save();
		}
		render(obj);
	}
	
	//选择上一步还是下一步（上一步：资源申请页面，下一步：跳到添加备注页面）
	public static void remark(Long id){
		VmSingle obj = VmSingle.findById(id);
		String step = params.get("step");// 是上一步还是下一步
		if(step != null && "1".equals(step)){//手工操作中选择了上一步
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
			render("/ResIndependentApplys/blank.html", obj, id,osPatchList, dbPatchList, webPatchList, cpuList, memoryList);
		
		}else if(step != null && "2".equals(step)){//手工操作中选择了下一步
			String isSuccess = params.get("isSuccess");
			obj.manualFlag = isSuccess;
			obj.status = "8";//手工操作已完成
			obj.save();
			render(obj);
		}
		
	}
	
	//添加备注
	public static void addRemark(Long id) {
		VmSingle obj = VmSingle.findById(id);
		String remark = params.get("remark");
		if(remark!=null&&!"".equals(remark.trim())){
			String oldRemark = obj.reMark;
			Date date = new Date();
			String dateStr = sdf1.format(date);
			obj.reMark = null2Str(oldRemark).trim() + "   "+ null2Str(remark).trim() + dateStr + "   ";
			obj.save();
		}
		redirect(request.controller + ".list");
	}
	
	//null转""
	public static String null2Str(String str) {
		if (str == null) {
			str = "";
		}
		return str;
	}
	
}
