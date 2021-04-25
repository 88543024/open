package controllers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.TResource;
import models.record.SysLog;
import models.record.SysLog.LogType;
//资产管理
@CRUD.For(TResource.class)
public class Resoures extends CRUD{
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
     * 跳转到添加页面
     */
    public static void blank() {
        render();
    }
	
    public static void add() {
    	String flag = "0";
    	try {
    		TResource object = new TResource();
    		object.PRO_NAME = params.get("proName");
    		object.ROOM_POSITION = params.get("roomPosition");
    		object.INTERNAL_NUMBER = params.get("internalNum");
    		object.RACK_NUMBER = params.get("rackNum");
    		object.LOCATION = params.get("location");
    		object.APP_NAME = params.get("appName");
    		object.VENDOR = params.get("vendor");
    		object.MODEL = params.get("model");
    		object.SN = params.get("sn");
    		object.CONFIG = params.get("config");
    		object.DEVICE_TYPE = params.get("deviceType");
    		object.PARTITION_INFO = params.get("partitionInfo");
    		object.IP = params.get("ip");
    		object.OS = params.get("os");
    		object.DEPARTMENT = params.get("department");
    		object.APP_DEPARTMENT = params.get("appDepartment");
    		object.CONTACTS = params.get("contacts");
    		object.TELEPHONE = params.get("telephone");
    		object.REMARK = params.get("remark");
    		String beginDate =params.get("beginDate");
    		String endDate =params.get("endDate");
    			Date begin = sdf1.parse(beginDate);
    			Date end = sdf1.parse(endDate);
    			Timestamp ts = new Timestamp(begin.getTime());
    			Timestamp tse = new Timestamp(end.getTime());
    			object.BEGIN_DATE = ts;
    			object.END_DATE = tse;
    		object.save();
    		
    		//系统日志记录
			SysLog slog = new SysLog();
			slog.LOG_TYPE = LogType.RESOURCE_OPERATE;
			slog.LOG_CONTENT = session.get("username")+"进行了资产新建操作";
			slog.OPERATION_MAN = session.get("username");
			slog.CREATE_LOG_TIME = new Date();
			slog.save();
		} catch (Exception e) {
			flag = "1";
			e.printStackTrace();
		}
    	renderJSON(flag);
    }
	
	//详情页面
	public static void view(Long id){
		TResource object = TResource.findById(id);
	    render(object);
	}
	//修改页面
	public static void edit(Long id){
		TResource object = TResource.findById(id);
	    render(object);
	}
	
	public static void modify(Long id){
		TResource object = TResource.findById(id);
		object.PRO_NAME = params.get("proName");
		object.ROOM_POSITION = params.get("roomPosition");
		object.INTERNAL_NUMBER = params.get("internalNum");
		object.RACK_NUMBER = params.get("rackNum");
		object.LOCATION = params.get("location");
		object.APP_NAME = params.get("appName");
		object.VENDOR = params.get("vendor");
		object.MODEL = params.get("model");
		object.SN = params.get("sn");
		object.CONFIG = params.get("config");
		object.DEVICE_TYPE = params.get("deviceType");
		object.PARTITION_INFO = params.get("partitionInfo");
		object.IP = params.get("ip");
		object.OS = params.get("os");
		object.DEPARTMENT = params.get("department");
		object.APP_DEPARTMENT = params.get("appDepartment");
		object.CONTACTS = params.get("contacts");
		object.TELEPHONE = params.get("telephone");
		object.REMARK = params.get("remark");
		String beginDate =params.get("beginDate");
		String endDate =params.get("endDate");
		try {
			Date begin = sdf1.parse(beginDate);
			Date end = sdf1.parse(endDate);
			Timestamp ts = new Timestamp(begin.getTime());
			Timestamp tse = new Timestamp(end.getTime());
			object.BEGIN_DATE = ts;
			object.END_DATE = tse;
			object.save();
			
			//系统日志记录
			SysLog slog = new SysLog();
			slog.LOG_TYPE = LogType.RESOURCE_OPERATE;
			slog.LOG_CONTENT = session.get("username")+"进行了资产更新操作";
			slog.OPERATION_MAN = session.get("username");
			slog.CREATE_LOG_TIME = new Date();
			slog.save();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    
	    redirect(request.controller + ".list");
	}
	
	public static void delete(Long id){
		TResource object = TResource.findById(id);
		int flag = 1;//删除失败
		try {
			object.delete();
			flag=0;//删除成功
			//系统日志记录
			SysLog slog = new SysLog();
			slog.LOG_TYPE = LogType.RESOURCE_OPERATE;
			slog.LOG_CONTENT = session.get("username")+"进行了资产删除操作";
			slog.OPERATION_MAN = session.get("username");
			slog.CREATE_LOG_TIME = new Date();
			slog.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    renderJSON(flag);
	}
	
//	public static void delete(Long id){
//	TResource object = TResource.findById(id);
//	try {
//		if(id != null && !"".equals(id)) {
//			object.delete();
//			response.print("0,"+id);
//		}
//	} catch (Exception e) {
//		response.print("1,*");
//		e.printStackTrace();
//	}
//}
}
