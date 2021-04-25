package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import models.TConfigKPI;
import models.TResHost;
import models.TResReport;
import models.TResVM;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import play.Logger;
import play.mvc.Controller;
import utils.JdbcOracleUtilsBack;
import utils.Portal_JdbcOracleUtilsBACK;

@CRUD.For(TResReport.class)
public class Report extends CRUD {

	public static void params(String reportId) {
		TResReport report = TResReport.getReportByReportId(reportId);
		render(report);
	}

	public static void signalKpi(String reportId) {
		TResReport report = TResReport.getReportByReportId(reportId);
		render(report);
	}
	
	public static void vosparam(String reportId) {
		TResReport report = TResReport.getReportByReportId(reportId);
		render(report);
	}

	public static void getKPIs() {
		String reportId = params.get("reportId");
		String query = "select kpi from TConfigKPI kpi,TConfigReport2KPI r2k where kpi.KPI_ID = r2k.KPI_ID and r2k.REPORT_ID = '"
				+ reportId + "'";
		List<TConfigKPI> klist = TConfigKPI.find(query).fetch();
		JsonArray array = new JsonArray();
		for (TConfigKPI o : klist) {
			JsonObject json = new JsonObject();
			json.addProperty("value", o.KPI_ID);
			json.addProperty("name", o.DISPLAY_NAME);
			array.add(json);
		}
		renderText(array.toString());
	}

	//返回单个项目名称
	public static void getProName(){
		String name = params.get("system_name");
	    String queryPro = "select t.sys_info from T_Res_VM t where t.name = '"+name+"'";
		Connection conn = null;
		PreparedStatement st  = null; 
		ResultSet rs  = null;
		String namePro = "";
		try{
			conn = Portal_JdbcOracleUtilsBACK.getConnection();
			st = conn.prepareStatement(queryPro);
			rs = st.executeQuery();
			while(rs.next()){
				namePro = rs.getString(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			Portal_JdbcOracleUtilsBACK.free(rs, st, conn);
		}
		renderText(namePro);
	}
	//返回项目名称
	public static void getPro(){
	    String queryPro = "select t.sys_info from T_Res_VM t group by SYS_INFO";
		Connection conn = null;
		PreparedStatement st  = null; 
		ResultSet rs  = null;
	    List<Object[]> list = new ArrayList<Object[]>();
	    try{
	    	conn = Portal_JdbcOracleUtilsBACK.getConnection();
			st = conn.prepareStatement(queryPro);
			rs = st.executeQuery();
			while(rs.next()){
			 Object[] o =  {rs.getString("sys_info"),rs.getString("sys_info")};
			 list.add(o);
			}
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	Portal_JdbcOracleUtilsBACK.free(rs, st, conn);
	    }
	    JsonArray array = new JsonArray();
		for (Object[] o : list) {
			String name = (String) o[0];
			String val = (String) o[1];
			JsonObject json = new JsonObject();
			json.addProperty("name", name);
			if(val!=null){
				json.addProperty("value", val);
			}else{
				json.addProperty("value", name);
			}
			array.add(json);
		}
		renderText(array.toString());
	}
	//返回虚拟机信息
	public static void getSystems() {
		/*List<Object[]> list = TResHost.find("select h.DISPLAY_NAME,h.NAME from TResHost h where h.USER_ATTRIB1 = 'itm'").fetch();*/
		String ProName = params.get("proName");
		Connection conn = null;
		PreparedStatement st  = null; 
		ResultSet rs  = null;
	    List<Object[]> list = new ArrayList<Object[]>();
		try{
			 conn = Portal_JdbcOracleUtilsBACK.getConnection();
			 String querySql = "";
			 if("".equals(ProName) || ProName == null){
				 querySql = "select v.NAME ||'  '||v.ip as DISPLAY_NAME,h.NAME as NAME from T_Res_Host h right join T_Res_VM v on h.DISPLAY_NAME = v.NAME  order by v.name";
			 }else{
				 querySql = "select v.NAME ||'  '||v.ip as DISPLAY_NAME,h.NAME as NAME from T_Res_Host h right join T_Res_VM v on h.DISPLAY_NAME = v.NAME where v.sys_info = '"+ProName+"' order by v.name";
			 }
			 st = conn.prepareStatement(querySql);
			 rs = st.executeQuery();
			 while(rs.next()){
				 Object[] o =  {rs.getString("DISPLAY_NAME"),rs.getString("NAME")};
				 list.add(o);
			 }
			 
		}catch(Exception e){
			 e.printStackTrace();
		}finally{
			Portal_JdbcOracleUtilsBACK.free(rs, st, conn);
		}
		JsonArray array = new JsonArray();
		for (Object[] o : list) {
			String name = (String) o[0];
			String val = (String) o[1];
			JsonObject json = new JsonObject();
			json.addProperty("name", name);
			if(val!=null){
				json.addProperty("value", val);
			}else{
				String vName = name.split(" ")[0];
				//String vName =name.substring(0, name.lastIndexOf(" "));
				//System.out.println(vName);
				json.addProperty("value", vName+":KUX");
			}
			array.add(json);
		}

		renderText(array.toString());
	}

	public static void getVoss() {
		List<Object[]> list = TResHost.find("select h.DISPLAY_NAME,h.NAME from TResHost h where h.USER_ATTRIB1 = 'vos'").fetch();
		/*Connection conn = null;
		PreparedStatement st  = null; 
		ResultSet rs  = null;
	    List<Object[]> list = new ArrayList<Object[]>();
		try{
			 conn = Portal_JdbcOracleUtils.getConnection();
			 String querySql = "select v.NAME as DISPLAY_NAME,h.NAME as NAME from T_Res_Host h right join T_Res_VM v on h.DISPLAY_NAME = v.NAME order by v.name";
			 st = conn.prepareStatement(querySql);
			 rs = st.executeQuery();
			 int i = 0;
			 while(rs.next()){
				 i++;
				 System.out.println("---->"+i);
				 Object[] o =  {rs.getString("DISPLAY_NAME"),rs.getString("NAME")};
				 list.add(o);
			 }
		}catch(Exception e){
			 e.printStackTrace();
		}finally{
			Portal_JdbcOracleUtils.free(rs, st, conn);
		}*/
		JsonArray array = new JsonArray();
		for (Object[] o : list) {
			String name = (String) o[0];
			String val = (String) o[1];
			JsonObject json = new JsonObject();
			json.addProperty("name", name);
			if(val != null){
				json.addProperty("value", val);
			}else{
				json.addProperty("value", name+":VA");
			}
			array.add(json);
		}

		renderText(array.toString());
	}

}
