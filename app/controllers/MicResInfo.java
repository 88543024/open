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

@CRUD.For(ResourceUseView.class)
public class MicResInfo extends CRUD {
	
	public static void list(int page, String search, String searchFields, String orderBy, String order) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        String sql = "select * from [dbo].[ResourceUseView]";
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
				ResourceUseView obj = new ResourceUseView();
				obj.CPU = rs.getInt("CPU");
				obj.CPUUse = rs.getInt("CPUUse");
				obj.Memory = rs.getInt("Memory");
				obj.MemoryUse = rs.getInt("MemoryUse");
				obj.StoreSize = rs.getInt("StoreSize");
				obj.StoreUse = rs.getInt("StoreUse");
				obj.CPURemain = rs.getInt("CPURemain");
				obj.MemoryRemain = rs.getInt("MemoryRemain");
				obj.StoreRemain = rs.getInt("StoreRemain");
				objects.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Portal_JdbcUtils.free(rs, ps, conn);
		}
        
        ResourceUseView obj = new ResourceUseView();
		obj.ID = "1";
		obj.CPU = 87;
		obj.Memory = 239;
		objects.add(obj);
		
		ResourceUseView obj1 = new ResourceUseView();
		obj1.ID = "2";
		obj.CPU = 187;
		obj.Memory = 69;;
		objects.add(obj1);
		
		ResourceUseView obj2 = new ResourceUseView();
		obj2.ID = "3";
		obj.CPU = 98;
		obj.Memory = 239;
		objects.add(obj);
		int st = (page-1)*15;
        int en = page*15;;
        if(objects.size()<en){
        	en = objects.size();
        }
       objects = objects.subList(st, en);
//      List<Model> objects = type.findPage(page, search, searchFields, orderBy, order, (String) request.args.get("where"));
        Long count = type.count(search, searchFields, (String) request.args.get("where"));
        Long totalCount = type.count(null, null, (String) request.args.get("where"));
        
        long totaCount = objects.size();
        try {
            render(type, objects, count, totalCount, page, orderBy, order);
        } catch (TemplateNotFoundException e) {
            render("CRUD/list.html", type, objects, count, totalCount, page, orderBy, order);
        }
    }
	

	public static void listMemory(int page, String search, String searchFields, String orderBy, String order) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        String sql = "select * from [dbo].[ResourceUseView]";
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
				ResourceUseView obj = new ResourceUseView();
				obj.CPU = rs.getInt("CPU");
				obj.CPUUse = rs.getInt("CPUUse");
				obj.Memory = rs.getInt("Memory");
				obj.MemoryUse = rs.getInt("MemoryUse");
				obj.StoreSize = rs.getInt("StoreSize");
				obj.StoreUse = rs.getInt("StoreUse");
				obj.CPURemain = rs.getInt("CPURemain");
				obj.MemoryRemain = rs.getInt("MemoryRemain");
				obj.StoreRemain = rs.getInt("StoreRemain");
				objects.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Portal_JdbcUtils.free(rs, ps, conn);
		}
        
		
        ResourceUseView obj = new ResourceUseView();
		obj.ID = "1";
		obj.CPU = 87;
		obj.Memory = 239;
		objects.add(obj);
		
		ResourceUseView obj1 = new ResourceUseView();
		obj1.ID = "2";
		obj.CPU = 187;
		obj.Memory = 69;;
		objects.add(obj1);
		
		ResourceUseView obj2 = new ResourceUseView();
		obj2.ID = "3";
		obj.CPU = 98;
		obj.Memory = 239;
		objects.add(obj);
		
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
	
	public static void listStore(int page, String search, String searchFields, String orderBy, String order) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        String sql = "select * from [dbo].[ResourceUseView]";
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
				ResourceUseView obj = new ResourceUseView();
				obj.StoreUse = rs.getInt("StoreUse");
				obj.StoreRemain = rs.getInt("StoreRemain");
				objects.add(obj);
			}
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }finally{
			 Portal_JdbcUtils.free(rs, ps, conn);
		 }
        
        ResourceUseView obj = new ResourceUseView();
		obj.ID = "1";
		obj.CPU = 87;
		obj.Memory = 239;
		objects.add(obj);
		
		ResourceUseView obj1 = new ResourceUseView();
		obj1.ID = "2";
		obj.CPU = 187;
		obj.Memory = 69;;
		objects.add(obj1);
		
		ResourceUseView obj2 = new ResourceUseView();
		obj2.ID = "3";
		obj.CPU = 98;
		obj.Memory = 239;
		objects.add(obj);
		
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