package jobs;

import models.TConfigUser;
import models.TScheduling;
import models.TriggerParams;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import controllers.Schedulings;


@OnApplicationStart
public class Bootstrap extends Job{
	public static final String USERINFO = "USERINFO";
	public static final String X86RESOURCE = "X86RESOURCE";
	public static final String X86PERFORMANCE = "X86PERFORMANCE";
	public static final String TSAMINFO = "TSAMINFO";
	
	public static final String EVERY_DAY = "EVERY_DAY";
	public static final String EVERY_WEEK = "EVERY_WEEK";
	public static final String EVERY_MONTH = "EVERY_MONTH";
	
    public void doJob(){
        Logger.info("start to init the system table...");
        this.initSuperManagerData();
        this.initData();
        this.initSynch();
        Logger.info("lunwen init successful...");
    }
    
    private void initSuperManagerData(){
    	Logger.info("start to init the T_ConfigUser table...");
    	TConfigUser user = TConfigUser.getByName("admin");
    	if(user==null){
    		TConfigUser superUser = new TConfigUser();
    		superUser.USER_NAME = "admin";
    		superUser.PASSWORD = "123456";
    		superUser.DISPLAY_NAME = "admin";
    		superUser.PERMISSION = "supermanager";
    		superUser.save();
    	}
    	Logger.info("Init the T_ConfigUser table successful...");
    }
    
    private void initData(){
    	Logger.info("start to init the T_SCHEDULING table...");
		//tsam信息
		TScheduling tsamInfoDay = TScheduling.getScheBySchTypeAndOption(TSAMINFO,EVERY_DAY);
		TScheduling tsamInfoWeek = TScheduling.getScheBySchTypeAndOption(TSAMINFO,EVERY_WEEK);
		TScheduling tsamInfoMonth = TScheduling.getScheBySchTypeAndOption(TSAMINFO,EVERY_MONTH);
		
    	TScheduling userInfoDay = TScheduling.getScheBySchTypeAndOption(USERINFO,EVERY_DAY);
		TScheduling userInfoWeek = TScheduling.getScheBySchTypeAndOption(USERINFO,EVERY_WEEK);
		TScheduling userInfoMonth = TScheduling.getScheBySchTypeAndOption(USERINFO,EVERY_MONTH);
		
    	TScheduling x86ResourceDay = TScheduling.getScheBySchTypeAndOption(X86RESOURCE,EVERY_DAY);
		TScheduling x86ResourceWeek = TScheduling.getScheBySchTypeAndOption(X86RESOURCE,EVERY_WEEK);
		TScheduling x86ResourceMonth = TScheduling.getScheBySchTypeAndOption(X86RESOURCE,EVERY_MONTH);
		TScheduling x86PerformanceDay = TScheduling.getScheBySchTypeAndOption(X86PERFORMANCE,EVERY_DAY);
		TScheduling x86PerformanceWeek = TScheduling.getScheBySchTypeAndOption(X86PERFORMANCE,EVERY_WEEK);
		TScheduling x86PerformanceMonth = TScheduling.getScheBySchTypeAndOption(X86PERFORMANCE,EVERY_MONTH);
		
		//TSAM 定时信息
		if(tsamInfoDay==null){
			tsamInfoDay = new TScheduling();
			tsamInfoDay.SCH_TYPE = TSAMINFO;
			tsamInfoDay.SCH_OPTION = EVERY_DAY;
			tsamInfoDay.OPERATE_HOUR = "23";
			tsamInfoDay.OPERATE_MINUTE = "30";
			tsamInfoDay.save();
		}
		if(tsamInfoWeek==null){
			tsamInfoWeek = new TScheduling();
			tsamInfoWeek.SCH_TYPE = TSAMINFO;
			tsamInfoWeek.SCH_OPTION = EVERY_WEEK;
			tsamInfoWeek.OPERATE_DAY = "3";
			tsamInfoWeek.OPERATE_HOUR = "23";
			tsamInfoWeek.OPERATE_MINUTE = "30";
			tsamInfoWeek.save();
		}
		if(tsamInfoMonth==null){
			tsamInfoMonth = new TScheduling();
			tsamInfoMonth.SCH_TYPE = TSAMINFO;
			tsamInfoMonth.SCH_OPTION = EVERY_MONTH;
			tsamInfoMonth.OPERATE_DAY = "月末最后一天";
			tsamInfoMonth.OPERATE_HOUR = "23";
			tsamInfoMonth.OPERATE_MINUTE = "30";
			tsamInfoMonth.save();
		}
		
		//-----AD user
		if(userInfoDay==null){
			userInfoDay = new TScheduling();
			userInfoDay.SCH_TYPE = USERINFO;
			userInfoDay.SCH_OPTION = EVERY_DAY;
			userInfoDay.OPERATE_HOUR = "23";
			userInfoDay.OPERATE_MINUTE = "30";
			userInfoDay.save();
		}
		if(userInfoWeek==null){
			userInfoWeek = new TScheduling();
			userInfoWeek.SCH_TYPE = USERINFO;
			userInfoWeek.SCH_OPTION = EVERY_WEEK;
			userInfoWeek.OPERATE_DAY = "3";
			userInfoWeek.OPERATE_HOUR = "23";
			userInfoWeek.OPERATE_MINUTE = "30";
			userInfoWeek.save();
		}
		if(userInfoMonth==null){
			userInfoMonth = new TScheduling();
			userInfoMonth.SCH_TYPE = USERINFO;
			userInfoMonth.SCH_OPTION = EVERY_MONTH;
			userInfoMonth.OPERATE_DAY = "月末最后一天";
			userInfoMonth.OPERATE_HOUR = "23";
			userInfoMonth.OPERATE_MINUTE = "30";
			userInfoMonth.save();
		}
		
		//x86 信息
		if(x86ResourceDay==null){
			x86ResourceDay = new TScheduling();
			x86ResourceDay.SCH_TYPE = X86RESOURCE;
			x86ResourceDay.SCH_OPTION = EVERY_DAY;
			x86ResourceDay.OPERATE_HOUR = "23";
			x86ResourceDay.OPERATE_MINUTE = "30";
			x86ResourceDay.save();
		}
		if(x86ResourceWeek==null){
			x86ResourceWeek = new TScheduling();
			x86ResourceWeek.SCH_TYPE = X86RESOURCE;
			x86ResourceWeek.SCH_OPTION = EVERY_WEEK;
			x86ResourceWeek.OPERATE_DAY = "3";
			x86ResourceWeek.OPERATE_HOUR = "23";
			x86ResourceWeek.OPERATE_MINUTE = "30";
			x86ResourceWeek.save();
		}
		if(x86ResourceMonth==null){
			x86ResourceMonth = new TScheduling();
			x86ResourceMonth.SCH_TYPE = X86RESOURCE;
			x86ResourceMonth.SCH_OPTION = EVERY_MONTH;
			x86ResourceMonth.OPERATE_DAY = "月末最后一天";
			x86ResourceMonth.OPERATE_HOUR = "23";
			x86ResourceMonth.OPERATE_MINUTE = "30";
			x86ResourceMonth.save();
		}
		if(x86PerformanceDay==null){
			x86PerformanceDay = new TScheduling();
			x86PerformanceDay.SCH_TYPE = X86PERFORMANCE;
			x86PerformanceDay.SCH_OPTION = EVERY_DAY;
			x86PerformanceDay.OPERATE_HOUR = "23";
			x86PerformanceDay.OPERATE_MINUTE = "30";
			x86PerformanceDay.save();
		}
		if(x86PerformanceWeek==null){
			x86PerformanceWeek = new TScheduling();
			x86PerformanceWeek.SCH_TYPE = X86PERFORMANCE;
			x86PerformanceWeek.SCH_OPTION = EVERY_WEEK;
			x86PerformanceWeek.OPERATE_DAY = "3";
			x86PerformanceWeek.OPERATE_HOUR = "23";
			x86PerformanceWeek.OPERATE_MINUTE = "30";
			x86PerformanceWeek.save();
		}
		if(x86PerformanceMonth==null){
			x86PerformanceMonth = new TScheduling();
			x86PerformanceMonth.SCH_TYPE = X86PERFORMANCE;
			x86PerformanceMonth.SCH_OPTION = EVERY_MONTH;
			x86PerformanceMonth.OPERATE_DAY = "月末最后一天";
			x86PerformanceMonth.OPERATE_HOUR = "23";
			x86PerformanceMonth.OPERATE_MINUTE = "30";
			x86PerformanceMonth.save();
		}
		Logger.info("Init the T_SCHEDULING table successful...");
    }
    
    //自动同步
    private void initSynch(){
    	//tsam信息
		TScheduling tsamInfoDay = TScheduling.getScheBySchTypeAndOption(TSAMINFO,EVERY_DAY);
		TScheduling tsamInfoWeek = TScheduling.getScheBySchTypeAndOption(TSAMINFO,EVERY_WEEK);
		TScheduling tsamInfoMonth = TScheduling.getScheBySchTypeAndOption(TSAMINFO,EVERY_MONTH);
		
    	TScheduling userInfoDay = TScheduling.getScheBySchTypeAndOption(USERINFO,EVERY_DAY);
		TScheduling userInfoWeek = TScheduling.getScheBySchTypeAndOption(USERINFO,EVERY_WEEK);
		TScheduling userInfoMonth = TScheduling.getScheBySchTypeAndOption(USERINFO,EVERY_MONTH);
		
    	TScheduling x86ResourceDay = TScheduling.getScheBySchTypeAndOption(X86RESOURCE,EVERY_DAY);
		TScheduling x86ResourceWeek = TScheduling.getScheBySchTypeAndOption(X86RESOURCE,EVERY_WEEK);
		TScheduling x86ResourceMonth = TScheduling.getScheBySchTypeAndOption(X86RESOURCE,EVERY_MONTH);
		TScheduling x86PerformanceDay = TScheduling.getScheBySchTypeAndOption(X86PERFORMANCE,EVERY_DAY);
		TScheduling x86PerformanceWeek = TScheduling.getScheBySchTypeAndOption(X86PERFORMANCE,EVERY_WEEK);
		TScheduling x86PerformanceMonth = TScheduling.getScheBySchTypeAndOption(X86PERFORMANCE,EVERY_MONTH);
		
		
		//TSAM 信息
		Integer tsamInfoDay_time_hour = null;//每天的小时
		if(tsamInfoDay.OPERATE_HOUR!=null&&!"".equals(tsamInfoDay.OPERATE_HOUR)){
			tsamInfoDay_time_hour = Integer.valueOf(tsamInfoDay.OPERATE_HOUR);
		}
		Integer tsamInfoDay_time_minute = null;//每天的分钟
		if(tsamInfoDay.OPERATE_MINUTE!=null&&!"".equals(tsamInfoDay.OPERATE_MINUTE)){
			tsamInfoDay_time_minute = Integer.valueOf(tsamInfoDay.OPERATE_MINUTE);
		}
		Integer tsamInfoWeek_time_hour = null;//每周的小时
		if(tsamInfoWeek.OPERATE_HOUR!=null&&!"".equals(tsamInfoWeek.OPERATE_HOUR.trim())){
			tsamInfoWeek_time_hour = Integer.valueOf(tsamInfoWeek.OPERATE_HOUR);
		}
		Integer tsamInfoWeek_time_minute = null;//每周的分钟
		if(tsamInfoWeek.OPERATE_MINUTE!=null&&!"".equals(tsamInfoWeek.OPERATE_MINUTE.trim())){
			tsamInfoWeek_time_minute = Integer.valueOf(tsamInfoWeek.OPERATE_MINUTE);
		}
		String tsamMoStr = new String(" 0 ");
		if(tsamInfoMonth.OPERATE_HOUR!=null&&!"".equals(tsamInfoMonth.OPERATE_HOUR.trim())&&tsamInfoMonth.OPERATE_MINUTE!=null
				&&!"".equals(tsamInfoMonth.OPERATE_MINUTE.trim())){
			tsamMoStr = tsamMoStr + tsamInfoMonth.OPERATE_MINUTE + " " + tsamInfoMonth.OPERATE_HOUR + " L * ?";
		}
		
		//AD UserInfo
		Integer userInfoDay_time_hour = null;//每天的小时
		if(userInfoDay.OPERATE_HOUR!=null&&!"".equals(userInfoDay.OPERATE_HOUR)){
			userInfoDay_time_hour = Integer.valueOf(userInfoDay.OPERATE_HOUR);
		}
		Integer userInfoDay_time_minute = null;//每天的分钟
		if(userInfoDay.OPERATE_MINUTE!=null&&!"".equals(userInfoDay.OPERATE_MINUTE)){
			userInfoDay_time_minute = Integer.valueOf(userInfoDay.OPERATE_MINUTE);
		}
		Integer userInfoWeek_time_hour = null;//每周的小时
		if(userInfoWeek.OPERATE_HOUR!=null&&!"".equals(userInfoWeek.OPERATE_HOUR.trim())){
			userInfoWeek_time_hour = Integer.valueOf(userInfoWeek.OPERATE_HOUR);
		}
		Integer userInfoWeek_time_minute = null;//每周的分钟
		if(userInfoWeek.OPERATE_MINUTE!=null&&!"".equals(userInfoWeek.OPERATE_MINUTE.trim())){
			userInfoWeek_time_minute = Integer.valueOf(userInfoWeek.OPERATE_MINUTE);
		}
		String usrMoStr = new String(" 0 ");
		if(userInfoMonth.OPERATE_HOUR!=null&&!"".equals(userInfoMonth.OPERATE_HOUR.trim())&&userInfoMonth.OPERATE_MINUTE!=null
				&&!"".equals(userInfoMonth.OPERATE_MINUTE.trim())){
			usrMoStr = usrMoStr + userInfoMonth.OPERATE_MINUTE + " " + userInfoMonth.OPERATE_HOUR + " L * ?";
		}
		
		//Resource
		Integer resourceDay_time_hour = null;//每天的小时
		if(x86ResourceDay.OPERATE_HOUR!=null&&!"".equals(x86ResourceDay.OPERATE_HOUR)){
			resourceDay_time_hour = Integer.valueOf(x86ResourceDay.OPERATE_HOUR);
		}
		Integer resourceDay_time_minute = null;//每天的分钟
		if(x86ResourceDay.OPERATE_MINUTE!=null&&!"".equals(x86ResourceDay.OPERATE_MINUTE)){
			resourceDay_time_minute = Integer.valueOf(x86ResourceDay.OPERATE_MINUTE);
		}
		Integer resourceWeek_time_hour = null;//每周的小时
		if(x86ResourceWeek.OPERATE_HOUR!=null&&!"".equals(x86ResourceWeek.OPERATE_HOUR.trim())){
			resourceWeek_time_hour = Integer.valueOf(x86ResourceWeek.OPERATE_HOUR);
		}
		Integer resourceWeek_time_minute = null;//每周的分钟
		if(x86ResourceWeek.OPERATE_MINUTE!=null&&!"".equals(x86ResourceWeek.OPERATE_MINUTE.trim())){
			resourceWeek_time_minute = Integer.valueOf(x86ResourceWeek.OPERATE_MINUTE);
		}
		String resMoStr = new String(" 0 ");
		if(x86ResourceMonth.OPERATE_HOUR!=null&&!"".equals(x86ResourceMonth.OPERATE_HOUR.trim())&&x86ResourceMonth.OPERATE_MINUTE!=null
				&&!"".equals(x86ResourceMonth.OPERATE_MINUTE.trim())){
			resMoStr = resMoStr + x86ResourceMonth.OPERATE_MINUTE + " " + x86ResourceMonth.OPERATE_HOUR + " L * ?";
		}
		
		//Performance
		Integer performanceDay_time_hour = null;//每天的小时
		if(x86PerformanceDay.OPERATE_HOUR!=null&&!"".equals(x86PerformanceDay.OPERATE_HOUR.trim())){
			performanceDay_time_hour = Integer.valueOf(x86PerformanceDay.OPERATE_HOUR);
		}
		Integer performanceDay_time_minute = null;//每天的分钟
		if(x86PerformanceDay.OPERATE_MINUTE!=null&&!"".equals(x86PerformanceDay.OPERATE_MINUTE.trim())){
			performanceDay_time_minute = Integer.valueOf(x86PerformanceDay.OPERATE_MINUTE);
		}
		
		Integer performanceWeek_time_hour = null;//每周的小时
		if(x86PerformanceWeek.OPERATE_HOUR!=null&&!"".equals(x86PerformanceWeek.OPERATE_HOUR.trim())){
			performanceWeek_time_hour = Integer.valueOf(x86PerformanceWeek.OPERATE_HOUR);
		}
		Integer performanceWeek_time_minute = null;//每周的分钟
		if(x86PerformanceWeek.OPERATE_MINUTE!=null&&!"".equals(x86PerformanceWeek.OPERATE_MINUTE.trim())){
			performanceWeek_time_minute = Integer.valueOf(x86PerformanceWeek.OPERATE_MINUTE);
		}
		
		String perfMoStr = new String(" 0 ");
		if(x86PerformanceMonth.OPERATE_HOUR!=null&&!"".equals(x86PerformanceMonth.OPERATE_HOUR.trim())
				&&x86PerformanceMonth.OPERATE_MINUTE!=null&&!"".equals(x86PerformanceMonth.OPERATE_MINUTE.trim())){
			perfMoStr = perfMoStr + x86PerformanceMonth.OPERATE_MINUTE + " " + x86PerformanceMonth.OPERATE_HOUR + " L * ?";
		}
		
		TriggerParams tp = new TriggerParams();
		
		tp.setTsamInfoDay_time_hour(tsamInfoDay_time_hour);
    	tp.setTsamInfoDay_time_minute(tsamInfoDay_time_minute);
    	tp.setTsamInfoWeek_day(Integer.valueOf(tsamInfoWeek.OPERATE_DAY));
    	tp.setTsamInfoWeek_time_hour(tsamInfoWeek_time_hour);
    	tp.setTsamInfoWeek_time_minute(tsamInfoWeek_time_minute);
    	tp.setTsamMoStr(tsamMoStr);
    	
		tp.setUserInfoDay_time_hour(userInfoDay_time_hour);
    	tp.setUserInfoDay_time_minute(userInfoDay_time_minute);
    	tp.setUserInfoWeek_day(Integer.valueOf(userInfoWeek.OPERATE_DAY));
    	tp.setUserInfoWeek_time_hour(userInfoWeek_time_hour);
    	tp.setUserInfoWeek_time_minute(userInfoWeek_time_minute);
    	tp.setUsrMoStr(usrMoStr);
		
    	tp.setResourceDay_time_hour(resourceDay_time_hour);
    	tp.setResourceDay_time_minute(resourceDay_time_minute);
    	tp.setResourceWeek_day(Integer.valueOf(x86ResourceWeek.OPERATE_DAY));
    	tp.setResourceWeek_time_hour(resourceWeek_time_hour);
    	tp.setResourceWeek_time_minute(resourceWeek_time_minute);
    	tp.setResMoStr(resMoStr);
    	
    	tp.setPerformanceDay_time_hour(performanceDay_time_hour);
    	tp.setPerformanceDay_time_minute(performanceDay_time_minute);
    	tp.setPerformanceWeek_day(Integer.valueOf(x86PerformanceWeek.OPERATE_DAY));
    	tp.setPerformanceWeek_time_hour(performanceWeek_time_hour);
    	tp.setPerformanceWeek_time_minute(performanceWeek_time_minute);
    	tp.setPerfMoStr(perfMoStr);
    	
    	boolean b = Schedulings.autoSchedu(null, tp, 1);
    	
    }
    
    
    
}
