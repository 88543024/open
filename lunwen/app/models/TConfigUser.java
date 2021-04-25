package models;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.deadbolt.Role;
import models.deadbolt.RoleHolder;

import org.hibernate.annotations.AccessType;

import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="T_WEBSYS_CONFIG_USER",uniqueConstraints={@UniqueConstraint(columnNames={"USER_NAME","PERMISSION"})})
@AccessType("field")
public class TConfigUser extends Model implements RoleHolder {
	
	/** 用户账号 */
	@Required
	public String USER_NAME;
	/** 密码*/
	@Password
	public String PASSWORD;
	/** 显示名称*/
	public String DISPLAY_NAME;
	/** 部门*/
	public String DEPARTMENT;
	/** 手机*/
	public String TELPHONE;
	/** 邮箱*/
	@Email
	public String EMAIL;
	/** 权限*/
	@Required
	public String PERMISSION;
	
	public static TConfigUser connect(String username, String password) {
        return find("byUser_NAMEAndPassword", username, password).first();
    }
	
    public static TConfigUser getByName(String username){
        return find("byUSER_NAME", username).first();
    }
    public List<? extends Role> getRoles(){
        return Arrays.asList(new MyRole("supermanager"),
                new MyRole("manager"),
                new MyRole("operator"));
    }
    
    /**
     * 批量插入or更新用户表（AD用户信息同步）
     * @param userList
     */
    public static void updOrInsertAD (List<TConfigUser> userList) {
    	if(userList!=null&&userList.size()>0){
    		for(TConfigUser user : userList ){
    			TConfigUser oldUser = TConfigUser.getByName(user.USER_NAME);
    			if(oldUser!=null){
    				oldUser.USER_NAME = user.USER_NAME;
    				oldUser.DISPLAY_NAME = user.DISPLAY_NAME;
    				oldUser.DEPARTMENT = user.DEPARTMENT;
    				oldUser.EMAIL = user.EMAIL;
    				oldUser.save();
    			}else{
    				user.save();
    			}
    		}
    		
    	}
    	
    }
    
}
