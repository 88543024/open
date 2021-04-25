package controllers;

import java.util.Date;
import java.util.List;

import models.TConfigUser;
import models.TImage;
import models.TResVM;
import models.TScheduling;
import models.TriggerParams;
import models.X86SynchDto;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

import play.Logger;
import rest.GetImageContact;
import rest.GetVMContact;
import soap.NimDiscovery;
import utils.AdUtil;

@CRUD.For(TScheduling.class)
public class Schedulings extends CRUD implements Job{
	private static SchedulerFactory schedFact = null;
	static{
		schedFact = new org.quartz.impl.StdSchedulerFactory();
	}
	
	public static final String USERINFO = "USERINFO";
	public static final String X86RESOURCE = "X86RESOURCE";
	public static final String X86PERFORMANCE = "X86PERFORMANCE";
	public static final String TSAMINFO = "TSAMINFO";
	
	public static final String EVERY_DAY = "EVERY_DAY";
	public static final String EVERY_WEEK = "EVERY_WEEK";
	public static final String EVERY_MONTH = "EVERY_MONTH";
	
	public static void list(){
		
		//tsam信息
		TScheduling tsamInfoDay = TScheduling.getScheBySchTypeAndOption(TSAMINFO,EVERY_DAY);
		TScheduling tsamInfoWeek = TScheduling.getScheBySchTypeAndOption(TSAMINFO,EVERY_WEEK);
		TScheduling tsamInfoMonth = TScheduling.getScheBySchTypeAndOption(TSAMINFO,EVERY_MONTH);
		
		//AD 用户信息
		TScheduling userInfoDay = TScheduling.getScheBySchTypeAndOption(USERINFO,EVERY_DAY);
		TScheduling userInfoWeek = TScheduling.getScheBySchTypeAndOption(USERINFO,EVERY_WEEK);
		TScheduling userInfoMonth = TScheduling.getScheBySchTypeAndOption(USERINFO,EVERY_MONTH);
		
		//x86资产信息
		TScheduling x86ResourceDay = TScheduling.getScheBySchTypeAndOption(X86RESOURCE,EVERY_DAY);
		TScheduling x86ResourceWeek = TScheduling.getScheBySchTypeAndOption(X86RESOURCE,EVERY_WEEK);
		TScheduling x86ResourceMonth = TScheduling.getScheBySchTypeAndOption(X86RESOURCE,EVERY_MONTH);
		TScheduling x86PerformanceDay = TScheduling.getScheBySchTypeAndOption(X86PERFORMANCE,EVERY_DAY);
		TScheduling x86PerformanceWeek = TScheduling.getScheBySchTypeAndOption(X86PERFORMANCE,EVERY_WEEK);
		TScheduling x86PerformanceMonth = TScheduling.getScheBySchTypeAndOption(X86PERFORMANCE,EVERY_MONTH);
		
	    render(userInfoDay,userInfoWeek,userInfoMonth,x86ResourceDay,x86ResourceWeek,x86ResourceMonth,x86PerformanceDay,x86PerformanceWeek,x86PerformanceMonth,tsamInfoDay,tsamInfoWeek,tsamInfoMonth);
	   //render(userInfoDay,userInfoWeek,userInfoMonth,tsamInfoDay,tsamInfoWeek,tsamInfoMonth);
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("Generating report - "+ arg0.getJobDetail().getFullName() 
				+ ", type ="+ arg0.getJobDetail().getJobDataMap().get("type"));
		
		String type = (String) arg0.getJobDetail().getJobDataMap().get("type");
		if(type!=null&&"X86RES".equals(type)){
			//调用X86资源同步接口
			//do somethings...... TODO
			Logger.info("X86Resource Synchronizing");
		}else if(type!=null&&"X86PERF".equals(type)){
			//调用X86性能同步接口
			//do somethings...... TODO
			Logger.info("X86Performance Synchronizing");
		}else if(type!=null&&"USERINFO".equals(type)){
			Logger.info("AD UserInfo Synchronizing");
			try {
				String user = "SINOPEC\\YT-Cloud-Test";
				String pwd = "YT-Cloud-Test";
				String[] ips = { "10.246.144.11", "10.246.144.12", "10.246.144.13" };
				for (String ip : ips) {
					if (AdUtil.adCheck(ip, user, pwd)) {
						List<TConfigUser> userList = AdUtil.GetADInfoList(ip);//调用AD域同步接口
						TConfigUser.updOrInsertAD(userList);
						Logger.info("AD UserInfo Synchronize Completed!");
						break;
					}
				}
			} catch (Exception e) {
				Logger.info("AD UserInfo Synchronize Failed!");
				e.printStackTrace();
			}
		}else if(type!=null&&"TSAMINFO".equals(type)){
			 Logger.info("TSAMINFO get Start");
			try{
				List<TResVM> getList = GetVMContact.getVM();
				if(getList != null && getList.size()>0){
					TResVM.deleteAll();
					for(int i = 0; i< getList.size(); i++ ){
					 	TResVM vm  = getList.get(i);
			       /*String query = "";
						   if(vm != null){
							   String IP = vm.IP;
							   String NAME = vm.NAME;
							   if( IP == null && NAME !=null){
						           query = "select t from TResVM t where t.IP is null or t.NAME = '"+NAME+"'";
							   }else if(IP != null && NAME == null){
								   query = "select t from TResVM t where t.IP ='"+IP+"'or t.NAME is null";
							   }else if(IP == null && NAME == null){
								   query = "select t from TResVM t where t.IP is null or t.NAME is null"; 
							   }else{
								   query = "select t from TResVM t where t.IP = '"+IP+"' or t.NAME = '"+NAME+"'";
							   }
							}
						   List<TResVM> Objes = TResVM.find(query).fetch();
						   if(Objes!=null && Objes.size()>0){
							   Logger.info("已保存vm");
						   }else{
							   vm.save();
							   Logger.info("保存vm");
						   }*/
						   vm.save();
					 }
			   }
			}catch(Exception e){
			  Logger.info("TSAMINFO get Failed");
			}
		}
	}
	
	public static void autoSynchronous(){
		
		String tsamInfoDay_hour = params.get("tsamInfoDay_hour");//TSAM信息每天几点
		String tsamInfoDay_minute = params.get("tsamInfoDay_minute");//几分
		String tsamInfoWeek_dayStr = params.get("tsamInfoWeek_day");//TSAM信息每周几
		Integer tsamInfoWeek_day = Integer.valueOf(tsamInfoWeek_dayStr);
		String tsamInfoWeek_hour = params.get("tsamInfoWeek_hour");//TSAM信息每周几，几点
		String tsamInfoWeek_minute = params.get("tsamInfoWeek_minute");//几分
		String tsamInfoMonth_day = params.get("tsamInfoMonth_day");//TSAM信息每月几号，或最后一天
		String tsamInfoMonth_hour = params.get("tsamInfoMonth_hour");//TSAM信息每月几号，或最后一天的几点
		String tsamInfoMonth_minute = params.get("tsamInfoMonth_minute");//几分
		
		String userInfoDay_hour = params.get("userInfoDay_hour");//AD用户信息每天几点
		String userInfoDay_minute = params.get("userInfoDay_minute");//几分
		String userInfoWeek_dayStr = params.get("userInfoWeek_day");//AD用户信息每周几
		Integer userInfoWeek_day = Integer.valueOf(userInfoWeek_dayStr);
		String userInfoWeek_hour = params.get("userInfoWeek_hour");//AD用户信息每周几，几点
		String userInfoWeek_minute = params.get("userInfoWeek_minute");//几分
		String userInfoMonth_day = params.get("userInfoMonth_day");//AD用户信息每月几号，或最后一天
		String userInfoMonth_hour = params.get("userInfoMonth_hour");//AD用户信息每月几号，或最后一天的几点
		String userInfoMonth_minute = params.get("userInfoMonth_minute");//几分
		
/*		String resourceDay_hour = params.get("resourceDay_hour");//x86资源信息每天几点
		String resourceDay_minute = params.get("resourceDay_minute");//几分
		String resourceWeek_dayStr = params.get("resourceWeek_day");//x86资源信息每周几
		Integer resourceWeek_day = Integer.valueOf(resourceWeek_dayStr);
		String resourceWeek_hour = params.get("resourceWeek_hour");//x86资源信息每周几，几点
		String resourceWeek_minute = params.get("resourceWeek_minute");//几分
		String resourceMonth_day = params.get("resourceMonth_day");//x86资源信息每月几号，或最后一天
		String resourceMonth_hour = params.get("resourceMonth_hour");//x86资源信息每月几号，或最后一天的几点
		String resourceMonth_minute = params.get("resourceMonth_minute");//几分
		
		String performanceDay_hour = params.get("performanceDay_hour");//x86性能信息每天几点
		String performanceDay_minute = params.get("performanceDay_minute");//几分
		String performanceWeek_dayStr = params.get("performanceWeek_day");//x86性能信息每周几
		Integer performanceWeek_day = Integer.valueOf(performanceWeek_dayStr);
		String performanceWeek_hour = params.get("performanceWeek_hour");//x86性能信息每周几，几点
		String performanceWeek_minute = params.get("performanceWeek_minute");//几分
		String performanceMonth_day = params.get("performanceMonth_day");//x86性能信息每月几号，或最后一天
		String performanceMonth_hour = params.get("performanceMonth_hour");//x86性能信息信息每月几号，或最后一天的几点
		String performanceMonth_minute = params.get("performanceMonth_minute");//几分
*/		
		X86SynchDto dto = new X86SynchDto();
		
		//TSAM
		dto.setTsamInfoDay_hour(tsamInfoDay_hour);
		dto.setTsamInfoDay_minute(tsamInfoDay_minute);
		dto.setTsamInfoWeek_day(tsamInfoWeek_dayStr);
		dto.setTsamInfoWeek_hour(tsamInfoWeek_hour);
		dto.setTsamInfoWeek_minute(tsamInfoWeek_minute);
		dto.setTsamInfoMonth_day(tsamInfoMonth_day);
		dto.setTsamInfoMonth_hour(tsamInfoMonth_hour);
		dto.setTsamInfoMonth_minute(tsamInfoMonth_minute);
		
		//AD
		dto.setUserInfoDay_hour(userInfoDay_hour);
		dto.setUserInfoDay_minute(userInfoDay_minute);
		dto.setUserInfoWeek_day(userInfoWeek_dayStr);
		dto.setUserInfoWeek_hour(userInfoWeek_hour);
		dto.setUserInfoWeek_minute(userInfoWeek_minute);
		dto.setUserInfoMonth_day(userInfoMonth_day);
		dto.setUserInfoMonth_hour(userInfoMonth_hour);
		dto.setUserInfoMonth_minute(userInfoMonth_minute);
/*		//X86资源信息
		dto.setResourceDay_hour(resourceDay_hour);
		dto.setResourceDay_minute(resourceDay_minute);
		dto.setResourceWeek_day(resourceWeek_dayStr);
		dto.setResourceWeek_hour(resourceWeek_hour);
		dto.setResourceWeek_minute(resourceWeek_minute);
		dto.setResourceMonth_day(resourceMonth_day);
		dto.setResourceMonth_hour(resourceMonth_hour);
		dto.setResourceMonth_minute(resourceMonth_minute);
		//X86性能信息
		dto.setPerformanceDay_hour(performanceDay_hour);
		dto.setPerformanceDay_minute(performanceDay_minute);
		dto.setPerformanceWeek_day(performanceWeek_dayStr);
		dto.setPerformanceWeek_hour(performanceWeek_hour);
		dto.setPerformanceWeek_minute(performanceWeek_minute);
		dto.setPerformanceMonth_day(performanceMonth_day);
		dto.setPerformanceMonth_hour(performanceMonth_hour);
		dto.setPerformanceMonth_minute(performanceMonth_minute);*/
		
		
		boolean flag = false;//启动调度器是否成功
		
		//tsamInfo
		Integer tsamInfoDay_time_hour = null;//每天的小时
		if(tsamInfoDay_hour!=null&&!"".equals(tsamInfoDay_hour.trim())){
			tsamInfoDay_time_hour = Integer.valueOf(tsamInfoDay_hour);
		}
		Integer tsamInfoDay_time_minute = null;//每天的分钟
		if(tsamInfoDay_minute!=null&&!"".equals(tsamInfoDay_minute.trim())){
			tsamInfoDay_time_minute = Integer.valueOf(tsamInfoDay_minute);
		}
		
		Integer tsamInfoWeek_time_hour = null;//每周的小时
		
		if(tsamInfoWeek_hour!=null&&!"".equals(tsamInfoWeek_hour.trim())){
			tsamInfoWeek_time_hour = Integer.valueOf(tsamInfoWeek_hour);
		}
		Integer tsamInfoWeek_time_minute = null;//每周的分钟
		if(tsamInfoWeek_hour!=null&&!"".equals(tsamInfoWeek_hour.trim())){
			tsamInfoWeek_time_minute = Integer.valueOf(tsamInfoWeek_hour);
		}
		
		String tsamMoStr = new String(" 0 ");
		if(tsamInfoMonth_hour!=null&&!"".equals(tsamInfoMonth_hour.trim())&&tsamInfoMonth_minute!=null&&!"".equals(tsamInfoMonth_minute.trim())){
			tsamMoStr = tsamMoStr + tsamInfoMonth_minute + " " + tsamInfoMonth_hour + " L * ?";
		}
		
		//AD tsamInfo
		Integer userInfoDay_time_hour = null;//每天的小时
		//boolean flag = false;//启动调度器是否成功
		if(userInfoDay_hour!=null&&!"".equals(userInfoDay_hour.trim())){
			userInfoDay_time_hour = Integer.valueOf(userInfoDay_hour);
		}
		Integer userInfoDay_time_minute = null;//每天的分钟
		if(userInfoDay_minute!=null&&!"".equals(userInfoDay_minute.trim())){
			userInfoDay_time_minute = Integer.valueOf(userInfoDay_minute);
		}
		
		Integer userInfoWeek_time_hour = null;//每周的小时
		
		if(userInfoWeek_hour!=null&&!"".equals(userInfoWeek_hour.trim())){
			userInfoWeek_time_hour = Integer.valueOf(userInfoWeek_hour);
		}
		Integer userInfoWeek_time_minute = null;//每周的分钟
		if(userInfoWeek_hour!=null&&!"".equals(userInfoWeek_hour.trim())){
			userInfoWeek_time_minute = Integer.valueOf(userInfoWeek_hour);
		}
		
		String usrMoStr = new String(" 0 ");
		if(userInfoMonth_hour!=null&&!"".equals(userInfoMonth_hour.trim())&&userInfoMonth_minute!=null&&!"".equals(userInfoMonth_minute.trim())){
			usrMoStr = usrMoStr + userInfoMonth_minute + " " + userInfoMonth_hour + " L * ?";
		}
		
		/*//Resource
		Integer resourceDay_time_hour = null;//每天的小时
		if(resourceDay_hour!=null&&!"".equals(resourceDay_hour.trim())){
			resourceDay_time_hour = Integer.valueOf(resourceDay_hour);
		}
		Integer resourceDay_time_minute = null;//每天的分钟
		if(resourceDay_minute!=null&&!"".equals(resourceDay_minute.trim())){
			resourceDay_time_minute = Integer.valueOf(resourceDay_minute);
		}
		
		Integer resourceWeek_time_hour = null;//每周的小时
		
		if(resourceWeek_hour!=null&&!"".equals(resourceWeek_hour.trim())){
			resourceWeek_time_hour = Integer.valueOf(resourceWeek_hour);
		}
		Integer resourceWeek_time_minute = null;//每周的分钟
		if(resourceWeek_hour!=null&&!"".equals(resourceWeek_hour.trim())){
			resourceWeek_time_minute = Integer.valueOf(resourceWeek_hour);
		}
		
		String resMoStr = new String(" 0 ");
		if(resourceMonth_hour!=null&&!"".equals(resourceMonth_hour.trim())&&resourceMonth_minute!=null&&!"".equals(resourceMonth_minute.trim())){
			resMoStr = resMoStr + resourceMonth_minute + " " + resourceMonth_hour + " L * ?";
		}
		
		//Performance
		Integer performanceDay_time_hour = null;//每天的小时
		if(performanceDay_hour!=null&&!"".equals(performanceDay_hour.trim())){
			performanceDay_time_hour = Integer.valueOf(performanceDay_hour);
		}
		Integer performanceDay_time_minute = null;//每天的分钟
		if(performanceDay_minute!=null&&!"".equals(performanceDay_minute.trim())){
			performanceDay_time_minute = Integer.valueOf(performanceDay_minute);
		}
		
		Integer performanceWeek_time_hour = null;//每周的小时
		if(performanceWeek_hour!=null&&!"".equals(performanceWeek_hour.trim())){
			performanceWeek_time_hour = Integer.valueOf(performanceWeek_hour);
		}
		Integer performanceWeek_time_minute = null;//每周的分钟
		if(performanceWeek_minute!=null&&!"".equals(performanceWeek_minute.trim())){
			performanceWeek_time_minute = Integer.valueOf(performanceWeek_minute);
		}
		String perfMoStr = new String(" 0 ");
		if(performanceMonth_hour!=null&&!"".equals(performanceMonth_hour.trim())&&performanceMonth_minute!=null&&!"".equals(performanceMonth_minute)){
			perfMoStr = perfMoStr + performanceMonth_minute + " " + performanceMonth_hour + " L * ?";
		}
		
		//---------
*/		TriggerParams tp = new TriggerParams();
		
		tp.setTsamInfoDay_time_hour(tsamInfoDay_time_hour);
    	tp.setTsamInfoDay_time_minute(tsamInfoDay_time_minute);
    	tp.setTsamInfoWeek_day(tsamInfoWeek_day);
    	tp.setTsamInfoWeek_time_hour(tsamInfoWeek_time_hour);
    	tp.setTsamInfoWeek_time_minute(tsamInfoWeek_time_minute);
    	tp.setTsamMoStr(tsamMoStr);
		
		tp.setUserInfoDay_time_hour(userInfoDay_time_hour);
    	tp.setUserInfoDay_time_minute(userInfoDay_time_minute);
    	tp.setUserInfoWeek_day(userInfoWeek_day);
    	tp.setUserInfoWeek_time_hour(userInfoWeek_time_hour);
    	tp.setUserInfoWeek_time_minute(userInfoWeek_time_minute);
    	tp.setUsrMoStr(usrMoStr);
		
/*    	tp.setResourceDay_time_hour(resourceDay_time_hour);
    	tp.setResourceDay_time_minute(resourceDay_time_minute);
    	tp.setResourceWeek_day(resourceWeek_day);
    	tp.setResourceWeek_time_hour(resourceWeek_time_hour);
    	tp.setResourceWeek_time_minute(resourceWeek_time_minute);
    	tp.setResMoStr(resMoStr);
    	
    	tp.setPerformanceDay_time_hour(performanceDay_time_hour);
    	tp.setPerformanceDay_time_minute(performanceDay_time_minute);
    	tp.setPerformanceWeek_day(performanceWeek_day);
    	tp.setPerformanceWeek_time_hour(performanceWeek_time_hour);
    	tp.setPerformanceWeek_time_minute(performanceWeek_time_minute);
    	tp.setPerfMoStr(perfMoStr);*/
		
		flag = autoSchedu(dto, tp, 0);
		renderJSON(flag);
	}

	
	//手动同步TSAM信息
	public static void manualTSAMInfoSynchronous(){
		boolean flag = false;//接口返回值
		Logger.info("tsamInfo Synchronizing");
		try {
			List<TResVM> getList = GetVMContact.getVM();
			if(getList != null && getList.size()>0){
				TResVM.delete("delete TResVM t where t.SYS_INFO !=?","朝阳门利旧");
				for(int i = 0; i< getList.size(); i++ ){
					TResVM vm  = getList.get(i);
					  /* String query = "";
					     if(vm != null){
						   String IP = vm.IP;
						   String NAME = vm.NAME;
						   if( IP == null && NAME !=null){
					           query = "select t from TResVM t where t.IP is null or t.NAME = '"+NAME+"'";
						   }else if(IP != null && NAME == null){
							   query = "select t from TResVM t where t.IP ='"+IP+"'or t.NAME is null";
						   }else if(IP == null && NAME == null){
							   query = "select t from TResVM t where t.IP is null or t.NAME is null"; 
						   }else{
							   query = "select t from TResVM t where t.IP = '"+IP+"' or t.NAME = '"+NAME+"'";
						   }
						}*/
					  // List<TResVM> Objes = TResVM.find(query).fetch();
				      /*System.out.println("---------------"+vm.NAME);
					   if(Objes!=null && Objes.size()>0){
						   Logger.info("已保存");
					   }else{
						 
						   Logger.info("保存");
					   }*/
					   vm.save();
				 }
		   }
			flag = true;
		} catch (Exception e) {
			Logger.info("tsamInfo Synchronize Failed!");
			e.printStackTrace();
		}
		renderJSON(flag);
	}
	
	//手动同步AD用户信息
	public static void manualUserInfoSynchronous(){
		boolean flag = false;//接口返回值
		Logger.info("AD tsamInfo Synchronizing");
		try {
			String user = "SINOPEC\\YT-Cloud-Test";
			String pwd = "YT-Cloud-Test";
			String[] ips = { "10.246.144.11", "10.246.144.12", "10.246.144.13" };
			for (String ip : ips) {
				if (AdUtil.adCheck(ip, user, pwd)) {
					List<TConfigUser> userList = AdUtil.GetADInfoList(ip);//调用AD同步接口 
					TConfigUser.updOrInsertAD(userList);
					Logger.info("AD tsamInfo Synchronize Completed!");
					flag = true;
					break;
				}
			}
			
		} catch (Exception e) {
			Logger.info("AD UserInfo Synchronize Failed!");
			e.printStackTrace();
		}
		renderJSON(flag);
	}
	
	//手动同步X86资源信息
	public static void manualX86ResSynchronous(){
		//调用接口 dosomething... TODO
		Logger.info("X86Resource Synchronizing");
		boolean flag = true;//接口返回值
		renderJSON(flag);
	}
	
	//手动同步X86性能信息
	public static void manualX86PerfSynchronous(){
		//调用接口 dosomething... TODO
		Logger.info("X86Performance Synchronizing");
		boolean flag = true;//接口返回值
		renderJSON(flag);
	}
	
	//保存定时信息
	private static void saveX86Synch(X86SynchDto dto) {
		
		TScheduling tsamInfoDay = TScheduling.getScheBySchTypeAndOption(TSAMINFO,EVERY_DAY);
		TScheduling tsamInfoWeek = TScheduling.getScheBySchTypeAndOption(TSAMINFO,EVERY_WEEK);
		TScheduling tsamInfoMonth = TScheduling.getScheBySchTypeAndOption(TSAMINFO,EVERY_MONTH);
		
		TScheduling userInfoDay = TScheduling.getScheBySchTypeAndOption(USERINFO,EVERY_DAY);
		TScheduling userInfoWeek = TScheduling.getScheBySchTypeAndOption(USERINFO,EVERY_WEEK);
		TScheduling userInfoMonth = TScheduling.getScheBySchTypeAndOption(USERINFO,EVERY_MONTH);
		
		/*TScheduling x86ResourceDay = TScheduling.getScheBySchTypeAndOption(X86RESOURCE,EVERY_DAY);
		TScheduling x86ResourceWeek = TScheduling.getScheBySchTypeAndOption(X86RESOURCE,EVERY_WEEK);
		TScheduling x86ResourceMonth = TScheduling.getScheBySchTypeAndOption(X86RESOURCE,EVERY_MONTH);
		TScheduling x86PerformanceDay = TScheduling.getScheBySchTypeAndOption(X86PERFORMANCE,EVERY_DAY);
		TScheduling x86PerformanceWeek = TScheduling.getScheBySchTypeAndOption(X86PERFORMANCE,EVERY_WEEK);
		TScheduling x86PerformanceMonth = TScheduling.getScheBySchTypeAndOption(X86PERFORMANCE,EVERY_MONTH);*/
		
		
		//TSAM
		tsamInfoDay.OPERATE_HOUR = dto.getTsamInfoDay_hour();
		tsamInfoDay.OPERATE_MINUTE = dto.getTsamInfoDay_minute();
		tsamInfoDay.save();
		
		tsamInfoWeek.OPERATE_DAY = dto.getTsamInfoWeek_day();
		tsamInfoWeek.OPERATE_HOUR = dto.getTsamInfoWeek_hour();
		tsamInfoWeek.OPERATE_MINUTE = dto.getTsamInfoWeek_minute();
		tsamInfoWeek.save();
		
		tsamInfoMonth.OPERATE_DAY = dto.getTsamInfoMonth_day();
		tsamInfoMonth.OPERATE_HOUR = dto.getTsamInfoMonth_hour();
		tsamInfoMonth.OPERATE_MINUTE = dto.getTsamInfoMonth_minute();
		tsamInfoMonth.save();
		
		//AD用户
		userInfoDay.OPERATE_HOUR = dto.getUserInfoDay_hour();
		userInfoDay.OPERATE_MINUTE = dto.getUserInfoDay_minute();
		userInfoDay.save();
		
		userInfoWeek.OPERATE_DAY = dto.getUserInfoWeek_day();
		userInfoWeek.OPERATE_HOUR = dto.getUserInfoWeek_hour();
		userInfoWeek.OPERATE_MINUTE = dto.getUserInfoWeek_minute();
		userInfoWeek.save();
		
		userInfoMonth.OPERATE_DAY = dto.getUserInfoMonth_day();
		userInfoMonth.OPERATE_HOUR = dto.getUserInfoMonth_hour();
		userInfoMonth.OPERATE_MINUTE = dto.getUserInfoMonth_minute();
		userInfoMonth.save();
		
		
		
		/*//x86资源
		x86ResourceDay.OPERATE_HOUR = dto.getResourceDay_hour();
		x86ResourceDay.OPERATE_MINUTE = dto.getResourceDay_minute();
		x86ResourceDay.save();
		
		x86ResourceWeek.OPERATE_DAY = dto.getResourceWeek_day();
		x86ResourceWeek.OPERATE_HOUR = dto.getResourceWeek_hour();
		x86ResourceWeek.OPERATE_MINUTE = dto.getResourceWeek_minute();
		x86ResourceWeek.save();
		
		x86ResourceMonth.OPERATE_DAY = dto.getResourceMonth_day();
		x86ResourceMonth.OPERATE_HOUR = dto.getResourceMonth_hour();
		x86ResourceMonth.OPERATE_MINUTE = dto.getResourceMonth_minute();
		x86ResourceMonth.save();
		//x86性能
		x86PerformanceDay.OPERATE_HOUR = dto.getPerformanceDay_hour();
		x86PerformanceDay.OPERATE_MINUTE = dto.getPerformanceDay_minute();
		x86PerformanceDay.save();
		
		x86PerformanceWeek.OPERATE_DAY = dto.getPerformanceWeek_day();
		x86PerformanceWeek.OPERATE_HOUR = dto.getPerformanceWeek_hour();
		x86PerformanceWeek.OPERATE_MINUTE = dto.getPerformanceWeek_minute();
		x86PerformanceWeek.save();
		
		x86PerformanceMonth.OPERATE_DAY = dto.getPerformanceMonth_day();
		x86PerformanceMonth.OPERATE_HOUR = dto.getPerformanceMonth_hour();
		x86PerformanceMonth.OPERATE_MINUTE = dto.getPerformanceMonth_minute();
		x86PerformanceMonth.save();*/
		
	}
	
	//----------------------------------------------------------------------------------------
	/**
	 * 定时器设定
	 * @param dto
	 */
	public static boolean autoSchedu(X86SynchDto dto,TriggerParams tp,Integer type){
		boolean flag = false;
		try {
			Scheduler sched = schedFact.getScheduler();
			if(sched.isStarted()){
				sched.shutdown();
				sched = schedFact.getScheduler();
				sched.start();
			}else{
				sched.start();
			}
			//创建一个JobDetail，指明name，groupname，以及具体的Job类名，该Job负责定义需要执行任务
	/*		JobDetail jobDetail1 = new JobDetail("myJob1", "myJobGroup1",Schedulings.class);
			JobDetail jobDetail2 = new JobDetail("myJob2", "myJobGroup2",Schedulings.class);
			JobDetail jobDetail3 = new JobDetail("myJob3", "myJobGroup3",Schedulings.class);
			JobDetail jobDetail4 = new JobDetail("myJob4", "myJobGroup4",Schedulings.class);
			JobDetail jobDetail5 = new JobDetail("myJob5", "myJobGroup5",Schedulings.class);
			JobDetail jobDetail6 = new JobDetail("myJob6", "myJobGroup6",Schedulings.class);*/
			JobDetail jobDetail7 = new JobDetail("myJob7", "myJobGroup7",Schedulings.class);
			JobDetail jobDetail8 = new JobDetail("myJob8", "myJobGroup8",Schedulings.class);
			JobDetail jobDetail9 = new JobDetail("myJob9", "myJobGroup9",Schedulings.class);
			JobDetail jobDetail_10 = new JobDetail("myJob_10", "myJobGroup_10",Schedulings.class);
			JobDetail jobDetail_11 = new JobDetail("myJob_11", "myJobGroup_11",Schedulings.class);
			JobDetail jobDetail_12 = new JobDetail("myJob_12", "myJobGroup_12",Schedulings.class);
			
/*			jobDetail1.getJobDataMap().put("type", "X86RES");
			jobDetail2.getJobDataMap().put("type", "X86RES");
			jobDetail3.getJobDataMap().put("type", "X86RES");
			jobDetail4.getJobDataMap().put("type", "X86PERF");
			jobDetail5.getJobDataMap().put("type", "X86PERF");
			jobDetail6.getJobDataMap().put("type", "X86PERF");*/
			jobDetail7.getJobDataMap().put("type", "USERINFO");
			jobDetail8.getJobDataMap().put("type", "USERINFO");
			jobDetail9.getJobDataMap().put("type", "USERINFO");
			jobDetail_10.getJobDataMap().put("type", "TSAMINFO");
			jobDetail_11.getJobDataMap().put("type", "TSAMINFO");
			jobDetail_12.getJobDataMap().put("type", "TSAMINFO");
			
/*			//创建一个每周触发的Trigger，指明星期几几点几分执行
			Trigger trigger1 = TriggerUtils.makeDailyTrigger("myTrigger1", tp.getResourceDay_time_hour(), tp.getResourceDay_time_minute());//每天
			Trigger trigger2 = TriggerUtils.makeWeeklyTrigger(tp.getResourceWeek_day(), tp.getResourceWeek_time_hour(), tp.getResourceWeek_time_minute());//每周
			CronTrigger trigger3 = new CronTrigger("myTrigger3", "myGroup3");//每月
			trigger3.setCronExpression(tp.getResMoStr());//" 0 15 10 L * ?"每月最后一天的上午10:15触发
			
			Trigger trigger4 = TriggerUtils.makeDailyTrigger("myTrigger4", tp.getPerformanceDay_time_hour(), tp.getPerformanceDay_time_minute());//每天23:48触发
			Trigger trigger5 = TriggerUtils.makeWeeklyTrigger(tp.getPerformanceWeek_day(), tp.getPerformanceWeek_time_hour(), tp.getPerformanceWeek_time_minute());//每周二13:45触发
			CronTrigger trigger6 = new CronTrigger("myTrigger6", "myGroup6");
			trigger6.setCronExpression(tp.getPerfMoStr());//" 0 15 10 L * ?"每月最后一天的上午10:15触发
*/			
			Trigger trigger7 = TriggerUtils.makeDailyTrigger("myTrigger7", tp.getUserInfoDay_time_hour(), tp.getUserInfoDay_time_minute());//每天23:48触发
			Trigger trigger8 = TriggerUtils.makeWeeklyTrigger(tp.getUserInfoWeek_day(), tp.getUserInfoWeek_time_hour(), tp.getUserInfoWeek_time_minute());//每周二13:45触发
			CronTrigger trigger9 = new CronTrigger("myTrigger9", "myGroup9");
			trigger9.setCronExpression(tp.getUsrMoStr());//" 0 15 10 L * ?"每月最后一天的上午10:15触发
			
			Trigger trigger_10 = TriggerUtils.makeDailyTrigger("myTrigger_10", tp.getTsamInfoDay_time_hour(), tp.getTsamInfoDay_time_minute());//每天23:48触发
			Trigger trigger_11 = TriggerUtils.makeWeeklyTrigger(tp.getTsamInfoWeek_day(), tp.getTsamInfoWeek_time_hour(), tp.getTsamInfoWeek_time_minute());//每周二13:45触发
			CronTrigger trigger_12 = new CronTrigger("myTrigger_12", "myGroup_12");
			trigger_12.setCronExpression(tp.getTsamMoStr());//" 0 15 10 L * ?"每月最后一天的上午10:15触发
			
/*			trigger1.setGroup("myTriggerGroup1");
			trigger2.setGroup("myTriggerGroup2");
			trigger3.setGroup("myTriggerGroup3");
			trigger4.setGroup("myTriggerGroup4");
			trigger5.setGroup("myTriggerGroup5");
			trigger6.setGroup("myTriggerGroup6");*/
			trigger7.setGroup("myTriggerGroup7");
			trigger8.setGroup("myTriggerGroup8");
			trigger9.setGroup("myTriggerGroup9");
			trigger_10.setGroup("myTriggerGroup_10");
			trigger_11.setGroup("myTriggerGroup_11");
			trigger_12.setGroup("myTriggerGroup_12");
			
			//从当前时间的下一秒开始执行
/*			trigger1.setStartTime(TriggerUtils.getEvenSecondDate(new Date()));
			trigger2.setStartTime(TriggerUtils.getEvenSecondDate(new Date()));
			trigger3.setStartTime(TriggerUtils.getEvenSecondDate(new Date()));
			trigger4.setStartTime(TriggerUtils.getEvenSecondDate(new Date()));
			trigger5.setStartTime(TriggerUtils.getEvenSecondDate(new Date()));
			trigger6.setStartTime(TriggerUtils.getEvenSecondDate(new Date()));*/
			trigger7.setStartTime(TriggerUtils.getEvenSecondDate(new Date()));
			trigger8.setStartTime(TriggerUtils.getEvenSecondDate(new Date()));
			trigger9.setStartTime(TriggerUtils.getEvenSecondDate(new Date()));
			trigger_10.setStartTime(TriggerUtils.getEvenSecondDate(new Date()));
			trigger_11.setStartTime(TriggerUtils.getEvenSecondDate(new Date()));
			trigger_12.setStartTime(TriggerUtils.getEvenSecondDate(new Date()));
			
			//指明trigger的name
/*			trigger1.setName("myTrigger1");
			trigger2.setName("myTrigger2");
			trigger3.setName("myTrigger3");
			trigger4.setName("myTrigger4");
			trigger5.setName("myTrigger5");
			trigger6.setName("myTrigger6");*/
			trigger7.setName("myTrigger7");
			trigger8.setName("myTrigger8");
			trigger9.setName("myTrigger9");
			trigger_10.setName("myTrigger_10");
			trigger_11.setName("myTrigger_11");
			trigger_12.setName("myTrigger_12");
			
			//用scheduler将JobDetail与Trigger关联在一起，开始调度任务
/*			sched.scheduleJob(jobDetail1, trigger1);
			sched.scheduleJob(jobDetail2, trigger2);
			sched.scheduleJob(jobDetail3, trigger3);
			sched.scheduleJob(jobDetail4, trigger4);
			sched.scheduleJob(jobDetail5, trigger5);
			sched.scheduleJob(jobDetail6, trigger6);*/
			sched.scheduleJob(jobDetail7, trigger7);
			sched.scheduleJob(jobDetail8, trigger8);
			sched.scheduleJob(jobDetail9, trigger9);
			sched.scheduleJob(jobDetail_10, trigger_10);
			sched.scheduleJob(jobDetail_11, trigger_11);
			sched.scheduleJob(jobDetail_12, trigger_12);
			
			Logger.info("Start Listen");
			if(type==0){//0保存数据库,1不保存
				saveX86Synch(dto);
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
}
