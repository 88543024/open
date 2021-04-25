package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.TConfigUser;
import models.record.SysLog;
import models.record.TGrantedLog;
import models.record.SysLog.LogType;

@CRUD.For(TConfigUser.class)
public class Users extends CRUD{
	private static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 跳转修改密码页面
	 */
    public static void editPwdPage() {
        render();
    }
	
    /**
     * 修改超管密码
     */
    public static void modifyAdminPwd() {
    	String username = params.get("username");
    	String oldPwd = params.get("oldPwd");
    	String newPwd1 = params.get("newPwd1");
    	String newPwd2 = params.get("newPwd2");
    	String flag = "0";
    	if(username!=null&&!"".equals(username)){
    		TConfigUser user = TConfigUser.getByName(username);
    		if(user!=null){
    			if(user.PASSWORD!=null&&oldPwd!=null){
    				boolean b = user.PASSWORD.equals(oldPwd);
    				if(b){
    					if(newPwd1!=null&&newPwd2!=null&&!"".equals(newPwd1.trim())&&!"".equals(newPwd2.trim())){
    						if(newPwd1.equals(newPwd2)){
    							flag = "1";//两次密码正确，原密码正确(修改成功)
    							TConfigUser us = TConfigUser.findById(user.id);
    							us.PASSWORD = newPwd1;
    							us.save();
    							//系统日志记录
    							SysLog slog = new SysLog();
    							slog.LOG_TYPE = LogType.USER_OPERATE;
    							slog.LOG_CONTENT="超管修改密码";
    							slog.OPERATION_MAN = session.get("username");
    							slog.CREATE_LOG_TIME = new Date();
    							slog.save();
    						}else{
    							flag = "5";//两次新密码输入不一致
    						}
    					}else{
    						flag = "6";//新密码不能为空
    					}
    				}else{
    					flag = "2";//原密码不正确
    				}
    			}else{
    				flag = "3";//请填写密码后再提交
    			}
    		}else{
    			flag = "4";//无此用户
    		}
    	}else{
    		flag = "4";//无此用户
    	}
    	renderJSON(flag);
        
    }
    
    /**
     * 跳转到添加用户的页面
     */
    public static void add() {
        render();
    }
    
    public static void saveUser() {
    	TConfigUser user = new TConfigUser();
    	user.PERMISSION = params.get("permission");
    	user.USER_NAME = params.get("userName");
    	user.DISPLAY_NAME = params.get("displayName");
    	user.DEPARTMENT = params.get("department");
    	user.TELPHONE = params.get("telephone");
    	user.EMAIL = params.get("email");
    	user.PASSWORD = params.get("password");
    	user.save();
    	redirect(request.controller + ".list");
    }
    
    /**
     * 跳转到用户授权页面
     * @param id
     */
    public static void assign (Long id) {
    	TConfigUser object = TConfigUser.findById(id);
    	render(object);
    }
    
    /**
     * 用户授权
     * @param id
     * @param permission
     */
    public static void assignPermission(Long id,String permission) {
    	TConfigUser object = TConfigUser.findById(id);
    	object.PERMISSION = permission;
    	object.save();
    	//系统日志记录
		SysLog slog = new SysLog();
		slog.LOG_TYPE = LogType.USER_OPERATE;
		slog.LOG_CONTENT = session.get("username")+"授予"+object.USER_NAME+permission+"的权限";
		slog.OPERATION_MAN = session.get("username");
		slog.CREATE_LOG_TIME = new Date();
		slog.save();
    	redirect(request.controller + ".list");
    }
    
    /**
     * 用户授权AJAX
     * @param id
     * @param permission
     */
    public static void assignPermissionAjax(Long id,String permission) {
    	String flag = "0";//授权成功
    	try {
    		TConfigUser object = TConfigUser.findById(id);
        	object.PERMISSION = permission;
        	TGrantedLog grantedLog = new TGrantedLog();
        	grantedLog.UNAME = object.USER_NAME;
        	grantedLog.ROLETYPE = permission;//权限/角色
        	grantedLog.GRANTPERSON = session.get("username");//授权人
        	grantedLog.GRANTTIME = sdf.format(new Date());//授权时间
        	object.save();
        	grantedLog.save();//授权日志记录
        	
        	//系统日志记录
    		SysLog slog = new SysLog();
    		slog.LOG_TYPE = LogType.USER_OPERATE;
    		slog.LOG_CONTENT = session.get("username")+"授予"+object.USER_NAME+permission+"的权限";
    		slog.OPERATION_MAN = session.get("username");
    		slog.CREATE_LOG_TIME = new Date();
    		slog.save();
    		
		} catch (Exception e) {
			flag = "1";//授权失败
			renderJSON(flag);
			e.printStackTrace();
		}
    	renderJSON(flag);
    }
    
    public static void view (Long id) {
    	TConfigUser object = TConfigUser.findById(id);
    	render(object);
    }

    public static void edit (Long id) {
    	TConfigUser object = TConfigUser.findById(id);
    	object.DISPLAY_NAME = params.get("displayName");
    	object.DEPARTMENT = params.get("department");
    	object.TELPHONE = params.get("telphone");
    	object.EMAIL = params.get("email");
    	String permissionNew = params.get("permission");
    	object.PERMISSION = permissionNew;
    	if(permissionNew!=null&&object.PERMISSION!=null){
    		if(!(permissionNew.trim()).equals(object.PERMISSION.trim())){
    			TGrantedLog grantedLog = new TGrantedLog();
            	grantedLog.UNAME = object.USER_NAME;
            	grantedLog.ROLETYPE = permissionNew;//权限/角色
            	grantedLog.GRANTPERSON = session.get("username");//授权人
            	grantedLog.GRANTTIME = sdf.format(new Date());//授权时间
            	grantedLog.save();//授权日志记录
            	
            	//系统日志记录
        		SysLog slog = new SysLog();
        		slog.LOG_TYPE = LogType.USER_OPERATE;
        		slog.LOG_CONTENT = session.get("username")+"修改了"+object.USER_NAME+"的基本信息";
        		slog.OPERATION_MAN = session.get("username");
        		slog.CREATE_LOG_TIME = new Date();
        		slog.save();
    		}
    	}
    	
    	object.save();
    	redirect(request.controller + ".list");
    }
    
    
}
