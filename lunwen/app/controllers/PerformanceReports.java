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

import models.report.BaseReport;
import models.report.ComboBox;
import models.report.PerformanceReport;

import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.mvc.Controller;
import utils.JdbcOracleUtilsBack;
import utils.Portal_JdbcUtils;

public class PerformanceReports extends Controller{

	public static void everyTimeReport(String modelName) {
        render(modelName);
    }
	
    public static void show() {
    	queryGrid();
    }
    
    private static void queryGrid() {
    	// 最外层Map
    	Map<String, Object> outmap = new TreeMap<String, Object>();
    	//查询出来的总列表
    	List<BaseReport> result = queryKPIList();
    	
    	//过滤重复的Ip，找出有多少个不同的IpAddress作为key
    	Map<String,BaseReport> ipMap = new TreeMap<String, BaseReport>();
    	for(int i = 0;i<result.size();i++){//找出有多少个不同的IpAddress作为key
    		PerformanceReport report = (PerformanceReport) result.get(i);
    		ipMap.put(report.ipAddress, report);
    	}
    	Iterator iter = (Iterator)ipMap.keySet().iterator();
		while(iter.hasNext()){
			//内层Map，图表Map
	    	Map<String, List<ComboBox>> kpiMap = new TreeMap<String, List<ComboBox>>();
	    	String ipKey = (String) iter.next();
	    	
	    	List<ComboBox> maxList = new ArrayList<ComboBox>();
	    	List<ComboBox> avgList = new ArrayList<ComboBox>();
	    	for(int i = 0;i<result.size();i++){
	    		PerformanceReport report = (PerformanceReport) result.get(i);
	    		if(ipKey.equals(report.ipAddress)){
	    			if("CPU".equals(report.businessSys)){
	    				
	    			}else if("内存".equals(report.businessSys)){
	    				
	    			}else if("网络".equals(report.businessSys)){
	    				
	    			}else if("存储".equals(report.businessSys)){
	    				
	    			}
	    			ComboBox maxComb = new ComboBox();
	    			ComboBox avgComb = new ComboBox();
	    			
	    			maxComb.setId(report.hapDate);
	    			maxComb.setText(report.max);
	    			avgComb.setId(report.hapDate);
	    			avgComb.setText(report.avg);
		    		maxList.add(maxComb);
		    		avgList.add(avgComb);
	    		}
	    		
	    	}
	    	kpiMap.put("max", maxList);
	    	kpiMap.put("avg", avgList);
			outmap.put(ipKey, kpiMap);
		}
    	
    	outmap.put("grid", result);
    	renderJSON(outmap);
    }
    
//    public static void exp(String fileName) throws Exception {
//        String[] headers = new String[] { "序号", "模块名称", "业务系统", "IP地址", "主机名", "Cpu最小值", "Cpu最大值", "Cpu平均值", "发生时间" };
//        List<BaseReport> result = queryCpuDayList();
//        ReportUtils.expExcelWithImg(fileName, "服务器CPU使用率查询日报", headers, result, response);
//    }
    
    //KPI类别
    public static void getBusinessList() {
    	List<ComboBox> result = new ArrayList<ComboBox>();
    	ComboBox cpuComb = new ComboBox();
    	ComboBox memComb = new ComboBox();
    	ComboBox networkComb = new ComboBox();
    	ComboBox diskComb = new ComboBox();
    	cpuComb.setId("1");
    	cpuComb.setText("CPU");
    	memComb.setId("2");
    	memComb.setText("内存");
    	networkComb.setId("3");
    	networkComb.setText("网络");
    	diskComb.setId("4");
    	diskComb.setText("存储");
    	result.add(cpuComb);
    	result.add(memComb);
    	result.add(networkComb);
    	result.add(diskComb);
    	renderJSON(result);
    }
    
    //颗粒
    public static void getModelNameList() {
    	List<ComboBox> result = new ArrayList<ComboBox>();
    	ComboBox hourComb = new ComboBox();
    	ComboBox dayComb = new ComboBox();
    	ComboBox weekComb = new ComboBox();
    	hourComb.setId("1");
    	hourComb.setText("小时");
    	dayComb.setId("2");
    	dayComb.setText("天");
    	weekComb.setId("3");
    	weekComb.setText("周");
    	result.add(hourComb);
    	result.add(dayComb);
    	result.add(weekComb);
    	renderJSON(result);
    }
    
    private static StringBuilder queryParam(){
    	StringBuilder where = new StringBuilder();
        where.append(" 1 = 1 ");
        if (StringUtils.isNotBlank(params.get("ipAddress"))) {
            String[] strs = StringUtils.split(params.get("ipAddress"), ",");
            StringBuffer sb = new StringBuffer("( ");
            for (int i = 0; i < strs.length; i++) {
                if (i == strs.length - 1) {
                    sb.append("'").append(strs[i]).append("'");
                } else {
                    sb.append("'").append(strs[i]).append("', ");
                }
            }
            sb.append(")");
            where.append(" and System_Name in ").append(sb.toString());
        }
        if (StringUtils.isNotBlank(params.get("beginDate"))) {
        	String bdate = params.get("beginDate");
        	String year = bdate.substring(2,4);
        	String month = bdate.substring(5,7);
        	String day = bdate.substring(8,10);
            where.append(" and  TO_NUMBER(WRITETIME) >= ").append("1"+year+month+day+"000000000");
        }
        if (StringUtils.isNotBlank(params.get("endDate"))) {
        	String bdate = params.get("endDate");
        	String year = bdate.substring(2,4);
        	String month = bdate.substring(5,7);
        	String day = bdate.substring(8,10);
        	where.append(" and  TO_NUMBER(WRITETIME) <= ").append("1"+year+month+day+"000000000");
        }
        return where;
    }
    //根据条件不同，查询不同的表
    private static List<BaseReport> queryKPIList() {
    	String modelName = params.get("modelName");//日报类别（1小时、2天、3周）
    	String businessSys = params.get("business");//性能KPI（CPU、内存、网络、存储）
    	
    	PreparedStatement st = null;
    	ResultSet rs = null;
		Connection conn = null;
		StringBuilder where = queryParam();//获取查询条件
		StringBuffer sql = new StringBuffer();
		List<BaseReport> result = new ArrayList<BaseReport>();
		
		if(businessSys!=null&&"1".equals(businessSys)){//CPU
			if(modelName!=null&&"1".equals(modelName)){
				sql.append(" select \"MAX_CPU_Usage\",\"AVG_CPU_Usage\",\"System_Name\",WRITETIME from SMP_CPU_H where " ).append(where.toString());
		        sql.append(" order by \"System_Name\", WRITETIME");
		        Logger.info("sql = "+sql);
			}else if(modelName!=null&&"2".equals(modelName)){
				sql.append(" select \"MAX_CPU_Usage\",\"AVG_CPU_Usage\",\"System_Name\",WRITETIME from SMP_CPU_D where " ).append(where.toString());
		        sql.append(" order by \"System_Name\", WRITETIME");
		        Logger.info("sql = "+sql);
			}else if(modelName!=null&&"3".equals(modelName)){
				sql.append(" select \"MAX_CPU_Usage\",\"AVG_CPU_Usage\",\"System_Name\",WRITETIME from SMP_CPU_W where " ).append(where.toString());
		        sql.append(" order by \"System_Name\", WRITETIME");
		        Logger.info("sql = "+sql);
			}
		}else if(businessSys!=null&&"2".equals(businessSys)){//内存
			if(modelName!=null&&"1".equals(modelName)){
				sql.append(" select \"MAX_Used_Real_Mem_Pct\",\"AVG_Used_Real_Mem_Pct\",\"System_Name\",WRITETIME from \"Unix_Memory_H\" where " ).append(where.toString());
		        sql.append(" order by \"System_Name\", WRITETIME");
		        Logger.info("sql = "+sql);
			}else if(modelName!=null&&"2".equals(modelName)){
				sql.append(" select \"MAX_Used_Real_Mem_Pct\",\"AVG_Used_Real_Mem_Pct\",\"System_Name\",WRITETIME from \"Unix_Memory_D\" where " ).append(where.toString());
		        sql.append(" order by \"System_Name\", WRITETIME");
		        Logger.info("sql = "+sql);
			}else if(modelName!=null&&"3".equals(modelName)){
				sql.append(" select \"MAX_Used_Real_Mem_Pct\",\"AVG_Used_Real_Mem_Pct\",\"System_Name\",WRITETIME from \"Unix_Memory_W\" where " ).append(where.toString());
		        sql.append(" order by \"System_Name\", WRITETIME");
		        Logger.info("sql = "+sql);
			}
		}else if(businessSys!=null&&"3".equals(businessSys)){//网络
			
		}else if(businessSys!=null&&"4".equals(businessSys)){//存储
			
		}
		try {
			conn = Portal_JdbcUtils.getConnection();
			 st = conn.prepareStatement(sql.toString());
			 rs = st.executeQuery();
			 while(rs.next()){
				 PerformanceReport report = new PerformanceReport();
	             report.max = rs.getString(1);
	             report.avg = rs.getString(2);
	             report.ipAddress = rs.getString(3);
	             String writeTime = rs.getString(4);
	             String year = "20"+writeTime.substring(1,3);
	             String month = writeTime.substring(3,5);
	             String day = writeTime.substring(5,7);
	             String hour = writeTime.substring(7,9);
	             String minute = writeTime.substring(9,11);
	             String second = writeTime.substring(11,13);
	             
	             report.hapDate = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
	             
	             if(businessSys!=null&&"1".equals(businessSys)){
	            	 report.businessSys = "CPU";
	             }else if(businessSys!=null&&"2".equals(businessSys)){
	            	 report.businessSys = "内存";
	             }else if(businessSys!=null&&"3".equals(businessSys)){
	            	 report.businessSys = "网络";
	             }else if(businessSys!=null&&"4".equals(businessSys)){
	            	 report.businessSys = "存储";
	             }else{
	            	 report.businessSys = "";
	             }
	             
	             if(modelName!=null&&"1".equals(modelName)){
	            	 report.modelName = "小时";
	             }else if(modelName!=null&&"2".equals(modelName)){
	            	 report.modelName = "天";
	             }else if(modelName!=null&&"3".equals(modelName)){
	            	 report.modelName = "周";
	             }else{
	            	 report.modelName = "";
	             }
	             result.add(report);
			 }
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
    	
    }
    
     
}
