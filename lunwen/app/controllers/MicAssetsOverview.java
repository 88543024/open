package controllers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controllers.CRUD.ObjectType;

import play.db.jpa.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.Controller;

import utils.Format;
import utils.Portal_JdbcUtils;
import models.VwAssetFailurePercent;
import models.vwAllAssetInfo;

@CRUD.For(VwAssetFailurePercent.class)
public class MicAssetsOverview extends CRUD {
	
	/*public static void list(){
		List<VwAssetFailurePercent>Objects = new ArrayList<VwAssetFailurePercent>();
		try {
			Connection conn = Portal_JdbcUtils.getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from vwAssetFailurePercent where IDCLookupID_cat=2");
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
	
/*	public static void list(){
		try {
			Connection conn = Portal_JdbcUtils.getConnection();
		} catch (SQLException e) {
			System.out.println("ddddatatata---------------"+e);
			e.printStackTrace();
		}
	}*/
	public static void list(int page, String search, String searchFields, String orderBy, String order) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        String sql = "select * from [smcm].[vwAssetFailurePercent] where IDCLookupID_cat=2";
       /*if(search!=null&&!"".equals(search)){
        	sql += "";
        }*/
        List<Model> objects = new ArrayList<Model>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
       try {
		    conn = Portal_JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				VwAssetFailurePercent obj = new VwAssetFailurePercent();
				String typeName = rs.getString("TypeName");
				int amount = rs.getInt("Amount");
				obj.TypeName = typeName;
				obj.Amount = amount;
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
	
	public static void listReport(int page, String search, String searchFields, String orderBy, String order) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        String sql = "select * from [smcm].[vwAllAssetsInfo] where IDCLookupID_cat=2 and AssetType='物理服务器'";
        System.out.println("----vwAllAssetsInfo----");
      /*  if(search!=null&&!"".equals(search)){
        	sql += "";
        }*/
        List<Model> objects = new ArrayList<Model>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
			 conn = Portal_JdbcUtils.getConnection();
			 ps = conn.prepareStatement(sql);
			 rs = ps.executeQuery();
			int i = 0;
			
				/*ResultSetMetaData rsm = rs.getMetaData();
				int colNum = 0;
				colNum = rsm.getColumnCount();
				for(int a = 1;a <= colNum;i++)
				{
				 String name = rsm.getColumnName(i);
				 System.out.println("ColumnName"+name);
				}*/
			while(rs.next()){
				i++;
				vwAllAssetInfo obj = new vwAllAssetInfo();
				obj.Name = rs.getString("Name");
				obj.SN = rs.getString("SN");
				obj.VendorName_cat = rs.getString("VendorName");
				obj.Model = rs.getString("Model");
				obj.IP =  rs.getString("IP");
				obj.CabinetID = rs.getString("CabinetID");
				obj.CabinetLocat = rs.getString("CabinetLocat");
				//obj.AssetType = rs.getString("AssetType");
				obj.num = i;
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
        Long count = type.count(search, searchFields, (String) request.args.get("where"));
        Long totalCount = type.count(null, null, (String) request.args.get("where"));
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
