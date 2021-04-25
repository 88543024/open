package controllers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import controllers.CRUD.ObjectType;
import play.db.jpa.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.Controller;
import utils.Format;
import utils.Portal_JdbcUtils;
import models.TResHost;
import models.TempAllSystemsInfo;
import models.VwAssetFailurePercent;
import models.VwPerformanceDaily;

@CRUD.For(VwPerformanceDaily.class)
public class MicResRun extends CRUD {
	
	public static void list(){
		List<VwAssetFailurePercent>Objects = new ArrayList<VwAssetFailurePercent>();
		try {
			Connection conn = Portal_JdbcUtils.getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from vwAssetFailurePercent");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String typeName = rs.getString("TypeName");
				String amount = rs.getString("Amount");
				System.out.println(typeName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VwAssetFailurePercent obj = new VwAssetFailurePercent();
		//obj.ID="1";
		obj.TypeName = "resx86";
		obj.Amount = 3;
		Objects.add(obj);
		render(Objects);
	}
	
	public static void paramsPage(){
		render();
	}
	public static void getSystems() {
		List<Object[]> list = TResHost
				.find("select h.DISPLAY_NAME,h.NAME from TResHost h where h.USER_ATTRIB1 = 'itm'")
				.fetch();
		JsonArray array = new JsonArray();
		for (Object[] o : list) {
			String name = (String) o[0];
			String val = (String) o[1];
			JsonObject json = new JsonObject();
			json.addProperty("name", name);
			json.addProperty("value", val);
			array.add(json);
		}
		renderText(array.toString());
	}

	public static void list(int page, String search, String searchFields, String orderBy, String order) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        String sql = "select * from [dbo].[vwPerformanceDaily] where IDCLookupID_cat=2";
        if(search!=null&&!"".equals(search)){
        	sql += "";
        }
        List<Model> objects = new ArrayList<Model>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
         try {
			 conn = Portal_JdbcUtils.getConnection();
			 ps = conn.prepareStatement(sql);
		     rs = ps.executeQuery();
			while(rs.next()){
				VwPerformanceDaily obj = new VwPerformanceDaily();
				//obj.SystemName = rs.getString("SystemName");
				obj.ServerName = rs.getString("ServerName");
		/*		obj.CPU = rs.getInt("CPU");
				obj.Memory = rs.getInt("Memory");
				obj.StoreSize = rs.getInt("StoreSize");*/
				objects.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Portal_JdbcUtils.free(rs, ps, conn);
		}
        
		/*
        VwPerformanceDaily obj = new VwPerformanceDaily();
		obj.ID = "1";
		obj.CPU = 87;
		obj.Memory = 239;
		objects.add(obj);
		
		VwPerformanceDaily obj1 = new VwPerformanceDaily();
		obj1.ID = "2";
		obj.CPU = 187;
		obj.Memory = 69;;
		objects.add(obj1);
		
		VwPerformanceDaily obj2 = new VwPerformanceDaily();
		obj2.ID = "3";
		obj.CPU = 98;
		obj.Memory = 239;
		objects.add(obj);*/
		
		
		int st = (page-1)*15;
        int en = page*15;;
        if(objects.size()<en){
        	en = objects.size();
        }
       objects = objects.subList(st, en);
//        List<Model> objects = type.findPage(page, search, searchFields, orderBy, order, (String) request.args.get("where"));
        Long count = type.count(search, searchFields, (String) request.args.get("where"));
        Long totalCount = type.count(null, null, (String) request.args.get("where"));
        
        long totaCount = objects.size();
        try {
            render(type, objects, count, totalCount, page, orderBy, order);
        } catch (TemplateNotFoundException e) {
            render("CRUD/list.html", type, objects, count, totalCount, page, orderBy, order);
        }
    }
	
	

	public static void listResUser(int page, String search, String searchFields, String orderBy, String order) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        String sql = "select * from [dbo].[vwPerformanceDaily] where IDCLookupID_cat=2";
        if(search!=null&&!"".equals(search)){
        	sql += "";
        }
        List<Model> objects = new ArrayList<Model>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
         try {
			 conn = Portal_JdbcUtils.getConnection();
			 ps = conn.prepareStatement(sql);
		     rs = ps.executeQuery();
			while(rs.next()){
				VwPerformanceDaily obj = new VwPerformanceDaily();
				obj.ServerName = rs.getString("ServerName");
				obj.ISVM = rs.getInt("ISVM");
				if(obj.ISVM == 0){
					obj.isvmStr = "否";
				}else if(obj.ISVM == 1){
					obj.isvmStr = "是";
				}
				obj.FeatureDomain = rs.getString("FeatureDomain");
				obj.DateTime = rs.getDate("DateTime");
				obj.ProcessorAvgValue = rs.getFloat("ProcessorAvgValue");
				obj.ProcessorMaxValue = rs.getFloat("ProcessorMaxValue");
				obj.MemoryAvgValue = rs.getFloat("MemoryAvgValue");
				obj.MemoryMaxValue = rs.getFloat("MemoryMaxValue");
		/*		obj.CPU = rs.getInt("CPU");
				obj.Memory = rs.getInt("Memory");
				obj.StoreSize = rs.getInt("StoreSize");*/
				objects.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Portal_JdbcUtils.free(rs, ps, conn);
		}
		
		int st = (page-1)*15;
        int en = page*15;;
        if(objects.size()<en){
        	en = objects.size();
        }
       objects = objects.subList(st, en);
//        List<Model> objects = type.findPage(page, search, searchFields, orderBy, order, (String) request.args.get("where"));
        Long count = type.count(search, searchFields, (String) request.args.get("where"));
        Long totalCount = type.count(null, null, (String) request.args.get("where"));
        
        long totaCount = objects.size();
        try {
            render(type, objects, count, totalCount, page, orderBy, order);
        } catch (TemplateNotFoundException e) {
            render("CRUD/list.html", type, objects, count, totalCount, page, orderBy, order);
        }
    }
	
	public static void listResUserReport(int page, String search, String searchFields, String orderBy, String order) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        String ServerName = params.get("page");
        if (page < 1) {
            page = 1;
        }
       String sql = "select * from [dbo].[vwPerformanceDaily] where IDCLookupID_cat=2 and ServerName='"+ServerName+"' order by DateTime";
        if(search!=null&&!"".equals(search)){
        	sql += "";
        }
        List<VwPerformanceDaily> objects = new ArrayList<VwPerformanceDaily>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
         try {
			 conn = Portal_JdbcUtils.getConnection();
			 ps = conn.prepareStatement(sql);
		     rs = ps.executeQuery();
			while(rs.next()){
				VwPerformanceDaily obj = new VwPerformanceDaily();
				obj.ServerName = rs.getString("ServerName");
				
				obj.ISVM = rs.getInt("ISVM");
				if(obj.ISVM ==0){
					obj.isvmStr = "否";
				}else if(obj.ISVM == 1){
					obj.isvmStr = "是";
				}
				obj.FeatureDomain = rs.getString("FeatureDomain");
				obj.DateTime = rs.getDate("DateTime");
				obj.dateStr = Format.parseString(obj.DateTime, 15);
				
				obj.ProcessorAvgValue = rs.getFloat("ProcessorAvgValue");
				obj.ProcessorMaxValue = rs.getFloat("ProcessorMaxValue");
				obj.MemoryAvgValue = rs.getFloat("MemoryAvgValue");
				obj.MemoryMaxValue = rs.getFloat("MemoryMaxValue");
			/*	obj.CPU = rs.getInt("CPU");
				obj.Memory = rs.getInt("Memory");
				obj.StoreSize = rs.getInt("StoreSize");*/
				objects.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Portal_JdbcUtils.free(rs, ps, conn);
		}
		
        /*VwPerformanceDaily obj = new VwPerformanceDaily();
        obj.ServerName = "YK2PX-R00057";
		obj.ID = "1";
		obj.CPU = 87;
		obj.Memory = 239;
		//obj.DateTime = new Date();
		obj.dateStr = "2014/12/20";
		obj.ProcessorAvgValue = 0.3f;
		obj.ProcessorMaxValue = 0.6f;
		obj.MemoryAvgValue = 0.4f;
		obj.MemoryMaxValue = 0.9f;
		objects.add(obj);
		
		VwPerformanceDaily obj1 = new VwPerformanceDaily();
        obj1.ServerName = "YK2PX-R00057";
		obj1.ID = "2";
		obj1.CPU = 187;
		obj1.Memory = 69;
		obj1.dateStr = "2014/12/21";
		obj1.ProcessorAvgValue = 0.1f;
		obj1.ProcessorMaxValue = 0.4f;
		obj1.MemoryAvgValue = 0.2f;
		obj1.MemoryMaxValue = 0.5f;
		objects.add(obj1);
		
		VwPerformanceDaily obj2 = new VwPerformanceDaily();
        obj2.ServerName = "YK2PX-R00057";
		obj2.ID = "3";
		obj2.CPU = 98;
		obj2.Memory = 239;
		obj2.dateStr = "2014/12/22";
		obj2.ProcessorAvgValue = 0.2f;
		obj2.ProcessorMaxValue = 0.5f;
		obj2.MemoryAvgValue = 0.1f;
		obj2.MemoryMaxValue = 0.7f;
		objects.add(obj2);*/
		
		
		StringBuffer json = new StringBuffer(); //将数据传到前台
		
		if(objects.size()>0){
	       json.append(json.append(objects.get(0).ServerName)).append("|");
		}
	    StringBuffer dates = new StringBuffer(); //日期数据
	    StringBuffer averageCup = new StringBuffer(); //cup平均数据
	    StringBuffer maxCup = new StringBuffer(); //cup最大数据
	    StringBuffer averageMemory = new StringBuffer(); //内存平均数据
	    StringBuffer maxMemory = new StringBuffer(); //最大内存数据
	    
	   for(int i=0; i<objects.size(); i++){
		   if(i<objects.size()-1){
		/*	  if(i==0){
				  //dates.append(""); 
				  averageCup.append("["); 
				  maxCup.append("["); 
				  averageMemory.append("["); //日期 x轴数据
				  maxMemory.append("["); 
			  } */
			  dates.append(objects.get(i).dateStr).append(",");
			  averageCup.append(objects.get(i).ProcessorAvgValue+"").append(",");
			  maxCup.append(objects.get(i).ProcessorMaxValue+"").append(",");
			  averageMemory.append(objects.get(i).MemoryAvgValue+"").append(",");
			  maxMemory.append(objects.get(i).MemoryMaxValue+"").append(",");
		   }else{
			  dates.append(objects.get(i).dateStr).append("");;
			  averageCup.append(objects.get(i).ProcessorAvgValue+"");
			  maxCup.append(objects.get(i).ProcessorMaxValue+"");
			  averageMemory.append(objects.get(i).MemoryAvgValue+"");
			  maxMemory.append(objects.get(i).MemoryMaxValue+"");
		   }
	     }
	   json.append(dates); 
	   json.append("|"); 
	   json.append(averageCup);
	   json.append("|");
	   json.append(maxCup);
	   json.append("|");
	   json.append(averageMemory);
	   json.append("|");
	   json.append(maxMemory);
        try {
            //renderJSON(json);
            render(type, json);
        } catch (TemplateNotFoundException e) {
            render("CRUD/list.html");
        }
    }
	public static void alistResUserReport(){
	     render();
	}
	
	public static void main(String[] args) {
		//list();
	}
}
