package controllers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controllers.CRUD.ObjectType;

import play.db.jpa.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.Controller;

import utils.Format;
import utils.Portal_JdbcUtils;
import models.ResourceUseView;
import models.TempAllSystemsInfo;

@CRUD.For(TempAllSystemsInfo.class)
public class MicResMeasure extends CRUD {
	
	/*public static void list(){
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
		obj.ID="1";
		obj.TypeName = "resx86";
		obj.Amount = 3;
		Objects.add(obj);
		render(Objects);
	}*/
	
	
	public static void list(int page, String search, String searchFields, String orderBy, String order) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        String sql = "select * from [smcm].[Temp_AllSystemsInfo] where IDCLookupID_cat=2";
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
		     int i = 0;
			while(rs.next()){
				TempAllSystemsInfo obj = new TempAllSystemsInfo();
				i++;
				obj.SystemName = rs.getString("SystemName");
				obj.CPU = rs.getInt("CPU数量");
				obj.Memory = rs.getInt("内存数量");
				obj.StoreSize = rs.getInt("存储卷大小容量");
				obj.VirSerNum = rs.getInt("虚拟服务器数量");
				obj.num = i;
				objects.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Portal_JdbcUtils.free(rs, ps, conn);
		}
        
		/*
        TempAllSystemsInfo obj = new TempAllSystemsInfo();
		obj.ID = "1";
		obj.CPU = 87;
		obj.Memory = 239;
		objects.add(obj);
		
		TempAllSystemsInfo obj1 = new TempAllSystemsInfo();
		obj1.ID = "2";
		obj.CPU = 187;
		obj.Memory = 69;;
		objects.add(obj1);
		
		TempAllSystemsInfo obj2 = new TempAllSystemsInfo();
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
        String sql = "select * from [smcm].[Temp_AllSystemsInfo] where IDCLookupID_cat=2";
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
		     int i = 0;
			while(rs.next()){
				TempAllSystemsInfo obj = new TempAllSystemsInfo();
				i++;
				obj.ManagementDepartmentName = rs.getString("ManagementDepartmentName");
				obj.SystemName = rs.getString("SystemName");
				obj.CPU = rs.getInt("CPU数量");
				obj.Memory = rs.getInt("内存数量");
				obj.StoreSize = rs.getInt("存储卷大小容量");
				obj.VirSerNum = rs.getInt("虚拟服务器数量");
				obj.num = i;
				objects.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Portal_JdbcUtils.free(rs, ps, conn);
		}
        /*
        TempAllSystemsInfo obj = new TempAllSystemsInfo();
		obj.ID = "1";
		obj.CPU = 87;
		obj.Memory = 239;
		objects.add(obj);
		
		TempAllSystemsInfo obj1 = new TempAllSystemsInfo();
		obj1.ID = "2";
		obj.CPU = 187;
		obj.Memory = 69;;
		objects.add(obj1);
		
		TempAllSystemsInfo obj2 = new TempAllSystemsInfo();
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
	
	public static void main(String[] args) {
		//list();
	}
}
