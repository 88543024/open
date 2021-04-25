package controllers;

import models.TDataDic;

@CRUD.For(TDataDic.class)
public class DataDics extends CRUD {

    public static void blank() {
    	render();
    }
    
    public static void addDataDic() {
    	String flag = "0";
    	String dicType = params.get("dicType");
    	String value = params.get("value");
    	if(value!=null&&!"".equals(value.trim())){
    		boolean b = ifExist(dicType, value);
    		if(b){
    			flag = "2";//此种类型的数据字典值已经存在，请重填
    		}else{
    			TDataDic dic = new TDataDic();
    			dic.DIC_TYPE = dicType;
    	    	dic.VALUE = value;
    	    	dic.DISPLAY_VALUE = params.get("displayValue");
    	    	dic.DISPLAY_VALUE_DESC = params.get("desc");
    	    	if(dicType!=null&&"OSPATCH".equals(dicType.trim())){
    	    		dic.DIC_TYPE_DESC = "操作系统数据字典";
    	    	}else if(dicType!=null&&"DBPATCH".equals(dicType.trim())){
    	    		dic.DIC_TYPE_DESC = "数据库数据字典";
    	    	}else if(dicType!=null&&"WEBPATCH".equals(dicType.trim())){
    	    		dic.DIC_TYPE_DESC = "中间件数据字典";
    	    	}else if(dicType!=null&&"VCPU_NUM".equals(dicType.trim())){
    	    		dic.DIC_TYPE_DESC = "CPU核数数据字典";
    	    	}else if(dicType!=null&&"MEMORY_SIZE".equals(dicType.trim())){
    	    		dic.DIC_TYPE_DESC = "内存大小数据字典";
    	    	}else if(dicType!=null&&"RES_Expire_Remind".equals(dicType.trim())){
    	    		dic.DIC_TYPE_DESC = "资源到期提前预警天数";
    	    	}
    	    	else if(dicType!=null&&"REPORT_TopN".equals(dicType.trim())){
    	    		dic.DIC_TYPE_DESC = "报表查询TopN";
    	    	}
    	    	dic.save();
    	    	flag = "3";//此种类型的数据字典值不存在，可以添加
    		}
    	}else{
    		flag = "1";//数据字典value值不能为空
    	}
    	renderJSON(flag);
    }
    
	public static void view(Long id){
		TDataDic object = TDataDic.findById(id);
		render(object);
	}
    
	public static void editDataDic(Long id){
		TDataDic object = TDataDic.findById(id);
		object.DISPLAY_VALUE = params.get("displayValue");
		String remark = params.get("remark");
		if(remark!=null&&!"".equals(remark.trim())){
			object.DISPLAY_VALUE_DESC = remark;
		}
		object.save();
		redirect(request.controller + ".list");
	}
	
	
	public static boolean ifExist(String dicType,String value){
		boolean flag = false;//
		if(dicType!=null&&!"".equals(dicType.trim()) && (value!=null&&!"".equals(value.trim()))){
			TDataDic dic = TDataDic.getDicByTypeAndValue(dicType, value);
			if(dic!=null){
				flag = true;
			}
		}
		return flag;
	}
	
	
}