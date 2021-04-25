package controllers;

import java.util.List;

import models.TConfigUser;
import play.Logger;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.i18n.Lang;
import play.mvc.With;
import controllers.CRUD.ObjectType;
import controllers.deadbolt.Deadbolt;

@With(Deadbolt.class)
public class Application extends Secure {

    public static void index() {
    	Logger.info(session.get("username")+"***********");
    	String username = session.get("username");
    	if(username!=null){
    		TConfigUser user = TConfigUser.getByName(username);
    		if(user!=null){
    			if(session.get("permission")==null){
    				session.put("permission",user.PERMISSION);
    				Logger.info(session.get("permission")+"---------");
    			}
        		
    		}
    		
    	}
    	String where = "";
    	String operatorRep = "";
		 if(Security.connected().contains("\\")){
			 operatorRep = Security.connected().replace("\\", "\\\\");
		 }
    	if(session.get("permission")!=null&&("supermanager".equals(session.get("permission"))||"manager".equals(session.get("permission")))){
    		where = "STATUS = '0' or STATUS IS NULL or STATUS = '' or StATUS = '1' ";
    	}else if(session.get("permission")!=null&&("operator".equals(session.get("permission")))){
    		where = " STATUS='3' and OPERATOR='"+operatorRep+"'";
    	}
    	
    	list(1, null, null, "ELEMENT_ID", "ASC" ,where);
//        render();
    }
    
    public static void setLang(String lang) {  
    	//System.out.println("---langlanglang--"+lang);
        Lang.change(lang);  
        index();  
        //render("Application/index.html",true);
    } 
    
    public static void list(int page, String search, String searchFields, String orderBy, String order ,String where) {
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
            render("Application/index.html",type, objects, count, totalCount, page, orderBy, order);
        } catch (TemplateNotFoundException e) {
            render("CRUD/list.html", type, objects, count, totalCount, page, orderBy, order);
        }
    }
    
    public static void blank(){
    	render();
    }
    public static void view(Long id){
    	Orders.view(id);
    }
    
    public static void show(Long id){
    	render();
    }
    
}