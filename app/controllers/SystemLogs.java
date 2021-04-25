package controllers;

import java.text.SimpleDateFormat;

import models.record.SysLog;
//系统日志
@CRUD.For(SysLog.class)
public class SystemLogs extends CRUD{
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void view(Long id){
		SysLog object = SysLog.findById(id);
	    render(object);
	}
	
	
}
