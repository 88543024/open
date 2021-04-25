package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import play.Logger;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import utils.Portal_JdbcUtils;
import controllers.CRUD.ObjectType;
import models.TAlarm;
import models.VResPool;


@CRUD.For(TAlarm.class)
public class Alarms extends CRUD{
	
	
	
	
	public static void previousPage(int startPosition) {  
        int totalUpload = TAlarm.findAll().size();  
        if(startPosition == 0) {  
            startPosition = startPosition;  
        }  
        else {  
            startPosition = startPosition - 1;  
        }  
        showAllUploads(startPosition);  
    }  
  
    public static void nextPage(int startPosition) {  
        int totalUpload = TAlarm.findAll().size();  
        if(startPosition >= totalUpload/5) {  
            startPosition = startPosition;  
        }  
        else {  
            startPosition = startPosition + 1;  
        }  
        showAllUploads(startPosition);  
    }
	
	
	public static void showAllUploads(int page){
		ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        PreparedStatement st = null;
    	ResultSet rs = null;
		Connection conn = null;
		List<Model> objects = new ArrayList<Model>();
		 
	    //分页查询条件
        int startIndex = (page-1)*10;
        int endIndex =  startIndex + 10;
        
       /* String query = "SELECT * FROM ( SELECT A.Device_Name,A.Device_Original_Severity,A.Alarm_Code,A.Alarm_Description,A.Last_Occurence,A.Subdevice_Name,A.Device_Type , ROWNUM RN  FROM (SELECT * FROM T_ALARM ) A WHERE ROWNUM <= "+endIndex+" ) WHERE RN >= "+startIndex;
        try {
			 conn = Portal_JdbcOracleUtils.getConnection();
			 st = conn.prepareStatement(query);
			 rs = st.executeQuery();
			 while(rs.next()){
				 TAlarm alt = new TAlarm();
				 alt.DEVICE_NAME = rs.getString(1);
				 alt.DEVICE_ORIGINAL_SEVERITY = rs.getString(2);
				 alt.ALARM_CODE = rs.getString(3);
				 alt.ALARM_DESCRIPTION = rs.getString(4);
				 alt.LAST_OCCURENCE = rs.getTimestamp(5);
				 alt.SUBDEVICE_NAME = rs.getString(6);
				 alt.DEVICE_TYPE = rs.getString(7);
				 objects.add(alt);
			 }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Portal_JdbcOracleUtils.free(rs, st, conn);
		}*/
        TAlarm.find("Select A FROM TAlarm A ").from(startIndex).fetch(endIndex);
		int count = TAlarm.find("Select A FROM TAlarm A ").fetch().size();
		System.out.println("总数：..."+count);
		render(type,objects, count, page);
	}
	
	
	/**
	 * 查询已经处理完成的工单
	 */
	public static void list(int page, String search, String searchFields, String orderBy, String order ,String where) {
		ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        List<Model> objects = new ArrayList<Model>();
       // int offset = (page - 1) * getPageSize();
        //List<String> properties = searchFields == null ? new ArrayList<String>(0) : Arrays.asList(searchFields.split("[ ]"));
       //objects = type.findPage(1, search, searchFields, orderBy, order, where)
		PreparedStatement st = null;
    	ResultSet rs = null;
		Connection conn = null;
     
        if (page < 1) {
            page = 1;
        }
        //分页查询条件
        int startIndex = (page-1)*10;
        int endIndex =  startIndex + 300;
        
       // String query = "SELECT * FROM ( SELECT A.Device_Name,A.Device_Original_Severity,A.Alarm_Code,A.Alarm_Description,A.Last_Occurence,A.Subdevice_Name,A.Device_Type , ROWNUM RN  FROM (SELECT * FROM T_ALARM order by last_occurence desc) A WHERE ROWNUM <= "+endIndex+" ) WHERE RN >= "+startIndex ;
        String query = "SELECT A.Device_Name,A.Device_Original_Severity,A.Alarm_Code,A.Alarm_Description,A.Last_Occurence,A.Subdevice_Name,A.Device_Type FROM (SELECT * FROM T_ALARM order by last_occurence desc)A";
      //  System.out.println("SELECT * FROM ( SELECT A.Device_Name,A.Device_Original_Severity,A.Alarm_Code,A.Alarm_Description,A.Last_Occurence,A.Subdevice_Name,A.Device_Type , ROWNUM RN  FROM (SELECT * FROM T_ALARM order by last_occurence desc) A WHERE ROWNUM <= "+endIndex+" ) WHERE RN >= "+startIndex);
        try {
			 conn = Portal_JdbcUtils.getConnection();
			 st = conn.prepareStatement(query);
			 rs = st.executeQuery();
			 int i = 0;
			 while(rs.next()){
				 i++;
				 TAlarm alt = new TAlarm();
				 alt.DEVICE_NAME = rs.getString(1);
				 alt.DEVICE_ORIGINAL_SEVERITY = rs.getString(2);
				 alt.ALARM_CODE = rs.getString(3);
				 alt.ALARM_DESCRIPTION = rs.getString(4);
				 alt.LAST_OCCURENCE = rs.getTimestamp(5);
				 alt.SUBDEVICE_NAME = rs.getString(6);
				 alt.DEVICE_TYPE = rs.getString(7);
				 alt.SerialNumber = i;
				/* if(checkTime(alt.LAST_OCCURENCE)){
					 alt.styleCss = "red";
				 }*/
				 objects.add(alt);
			 }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Portal_JdbcUtils.free(rs, st, conn);
		}
        //objects = TAlarm.find("Select A FROM TAlarm A ").from(0).fetch(100);
       // Long count = type.count(search, searchFields, (String) request.args.get("where"));
        //System.out.println(count);
        //Long Total = count;
        int START = 0;
        int END = 10;
       //objects = TAlarm.find("Select A FROM TAlarm A ").fetch();
/*        objects = TAlarm.find("Select A FROM TAlarm A order by FIRST_OCCURENCE desc").from(0).fetch(500);*/
		//int count = TAlarm.find("Select A FROM TAlarm A ").fetch().size();
		//System.out.println("总数：..."+count);
        int count = 300;
		int Total = count;
		//render(type,objects, count, page);
		render("Alarms/listback.html",type,objects, count,START,END,Total, page, orderBy, order);
    }
	public static Date getNextDay(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -1);  
        date = calendar.getTime();  
        return date;  
    } 
	
	public static boolean checkTime(Timestamp  times){
		  boolean res = false;
		  String altTime = times.toString().split(" ")[0];
		  System.out.println(altTime);
		  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		  dateFormat.setLenient(false);
		try {
			Date altDate =  dateFormat.parse(altTime);
			Date yesterDay =  dateFormat.parse(dateFormat.format(getNextDay(new Date())));
			System.out.println("altDate---->"+altDate);
			System.out.println("yesterDay---->"+yesterDay);
			res = yesterDay.equals(altDate);
			System.out.println("res---->"+res);
		} catch (ParseException e) {
			e.printStackTrace();
		}//util类型
		return res;
	}
}
