package controllers;

import models.TConfigUser;
import utils.AdUtil;

public class Security extends Secure.Security {

    static boolean authentify(String username, String password) {
//        return TConfigUser.connect(username, password) != null;
    	TConfigUser user = TConfigUser.connect(username, password);
    	if(user==null){
    		user = TConfigUser.getByName(username);
    	}
    	boolean b = false;
    	if(user!=null){
    		if(user.PERMISSION!=null){
    			if("supermanager".equals(user.PERMISSION.trim())){
    	 			b = true;
    	 			System.out.println("supermanager:"+user.USER_NAME);
    			}else if("manager".equals(user.PERMISSION.trim())||"operator".equals(user.PERMISSION.trim())){
//				    String userName = "SINOPEC\\YT-Cloud-Test";
//				    String pwd = "YT-Cloud-Test";
    				username = "SINOPEC\\"+username;
				    String[] ips = {"10.246.144.11","10.246.144.12","10.246.144.13"};
				    for (String ip : ips){
//				    		b = AdUtil.adCheck(ip,userName,pwd);
				    		b = AdUtil.adCheck(ip, username, password);
				    		if(b){
				    			session.put("username",user.USER_NAME);
				    			break;
				    		}
				    }
    				
//    				String ip = "192.168.0.199";
//    				b = adCheck(ip,user.USER_NAME,user.PASSWORD);
//    				System.out.println("AD:"+user.USER_NAME);
//    				session.put("username",user.USER_NAME);
        		}else{
        			b = false;
        		}
    		}else{
    			b = false;
    		}
    		
    	}else{
    		b = false;
    	}
    	return b;
    }
    
    static boolean check(String profile) {
        if("admin".equals(profile)) {
            return true;
        }
        return false;
    }
    
    static void onDisconnected() {
        Application.index();
    }
    
    static void onAuthenticated() {
        Application.index();
    }
    
    
}

