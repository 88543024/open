package controllers;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;

import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import controllers.CRUD.ObjectType;

import net.sf.oval.internal.Log;

import sun.util.logging.resources.logging;
import utils.Format;

import models.TResReportRecord;

@CRUD.For(TResReportRecord.class)
public class ReportRecord extends CRUD {
	
	private static List<TResReportRecord> _dataSet;

	public static void index() {
		render();
	}
	/**
	 * add by wanghaipeng 20120509 begin
	 * @Title: downloadReport 
	 * @Description: TODO 
	 * @param @param reportUrl
	 * @param @param reportName 
	 * @return void 
	 * @throws 
	 * @since CloudWei v1.0.0
	 */
	public static void downloadReport(String reportUrl, String reportName,String reportId) {
		FileInputStream inputStream = null;
		ByteArrayOutputStream myout = null;
		boolean flag = false;
		try {
//			reportUrl = reportUrl.replace("\\", "\\\\");
			
			// 设置response的编码方式
			response.setContentTypeIfNotSet("application/x-msdownload");
			String pathUrl = reportUrl+ File.separator + reportName;
			System.out.println("reportUrl---"+reportUrl+ File.separator + reportName);
			String fileName = URLEncoder.encode(reportName, "UTF-8");
			inputStream = new FileInputStream(pathUrl);
			// 解决中文乱码
//			if (fileName.length() > 450)
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gbk"), "iso-8859-1"));
			BufferedInputStream buff = new BufferedInputStream(inputStream);
			File file = new File(pathUrl);
			byte[] b = new byte[1024];

			long k = 0;// 该值用于计算当前实际下载了多少字节

			myout = response.out;

			while (k < file.length()) {

				int j = buff.read(b, 0, 1024);
				k += j;

				// 将b中的数据写到客户端的内存
				myout.write(b, 0, j);

			}

			// 将写入到客户端的内存的数据,刷新到磁盘
			myout.flush();
			if(null!=reportId&&!"".equals(reportId)){
				long rid = Long.parseLong(reportId);
				TResReportRecord report = TResReportRecord.findById(rid);
				int downloadTime = report.DOWNLOAD_TIMES;
				report.DOWNLOAD_TIMES = downloadTime+1;
				report.EXPORT_TIME = new Timestamp(System.currentTimeMillis());
				report._save();
			}else{
				
			}
			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (myout != null) {
				try {
					myout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//renderJSON(flag);
		}

	}
	
	public static void list(int page, String search, String searchFields, String orderBy, String order) {
		 ObjectType type = ObjectType.get(getControllerClass());
	       
	        notFoundIfNull(type);
	        if (page < 1) {
	            page = 1;
	        }
	        
	        String query = null;
	       String reportType = params.get("reportType");
	        	query = " select a from TResReportRecord a where 1=1 ";
	      if(reportType!=null&&!reportType.equals("")){
	    	  reportType = Format.getReportType(reportType);
	    	  query += " and a.REPORT_TYPE = '"+reportType+"'";
	      }
	        if(null!=search&&!"".equals(search)){
	        	query += " and (a.NAME like '%"+search+"%' or a.REPORT_ID like '%"+search+"%' or a.REPORT_NAME like '%"+search+"%'or a.REPORT_DESCRIPTION like '%"+search+"%' or a.REPORT_TYPE like '%"+search+"%' or a.REPORT_CREATER like '%"+search+"%' or a.REPORT_MONITOR like '%"+search+"%')";
	        }
	        System.out.println(query);
	        
	        query += " order by a.REPORT_DESCRIPTION desc,a.CREATE_TIME desc";
	        List<TResReportRecord> objects = TResReportRecord.find(query).fetch();
	        _dataSet = objects;
	        int offset = getPageSize();
	        int en = page * getPageSize() ;
			if (en > objects.size()) {
				en = objects.size();
				offset = objects.size()%offset;
			}
	        int totalCount = objects.size();
	        int count = objects.size();
	        objects = objects.subList((page - 1) * getPageSize(),en );
	        int i = 0;
	        for(TResReportRecord obj : objects){
	        	i++;
	        	obj.NUM = i;
	        }
	        try {
	            render(type, objects,count, totalCount,  page, orderBy, orderBy,query,reportType);
	        } catch (TemplateNotFoundException e) {
	            render("CRUD/list.html", type, objects, count, totalCount,  page, orderBy, order,reportType);
	        }
    }
	//add by wanghaipeng 20120509 end

}
