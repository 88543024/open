package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import controllers.CRUD.Hidden;

import play.Logger;
import play.data.validation.Required;
import play.db.jpa.GenericModel;
import play.db.jpa.Model;
import utils.Constants;

/**
 * 
 * Project Name：com.cloudwei.monitor.webserver 
 * Class Name：TConfigMenu 
 * Class Desc： 
 * Author：Spring 
 * Create Date：Mar 4, 2012 4:53:32 PM 
 * Last Modified By：Spring 
 * Last Modified：Mar 4, 2012 4:53:32 PM 
 * Remarks： 
 * @version  
 * 
 */
@Entity
@Table(name="T_CONFIG_MENU",uniqueConstraints={@UniqueConstraint(columnNames={"menuId"})})
public class TConfigMenu extends Model {	
	
	@Required
	public String menuName;
	
	@Required
	public String menuType;	
	
	@Required
	public String elementType;
	
	@Required
	public String module;
	

	@Required
	public String keyValue;
	
	public String menuId;
	
	public String parent;
	
	public Integer position;
	
	public Integer subPosition;
	
	public String description;
	
	public Boolean visible;
	
	public String url;
	
	public String image;
	
	public static List<TConfigMenu> findMenus(String menuType){
		return find("byMenuType", menuType).fetch();
	}
	
	public static List<TConfigMenu> findMenus(String parent,String menuType){
		return find("byParentAndMenuType", parent, menuType).fetch();
	}
	
	public static List<TConfigMenu> topbar(String groupName){
		if(null!=groupName&&!"".equals(groupName)&&groupName.equals(Constants.ADMIN)){
				return find("menuType='TOP' and visible=1 order by position").fetch();
		}else if(null!=groupName&&!"".equals(groupName)&&groupName.equals(Constants.ADMIN)){
			return find(" select m from TConfigMenu m where m.menuType = 'TOP' and m.visible = 1 and exists (select u2m.id from TResUser2Menu u2m where u2m.MENU_ID = m.id )").fetch();
		}
		else{
			return find("menuType='TOP' and visible=1 and keyvalue !='SETUP' and parent = 0 order by position").fetch();
		}
	}
	
	public static List<TConfigMenu> sidebar(String module){
		return find("menuType='SIDE' and module='"+module+"' and visible=1 order by position,subPosition asc").fetch();
	}

}
