/**
 * File Name：ITMConstants.java
 * 
 * Version： 
 * Date：Mar 12, 2012 
 * Copyright CloudWei Dev Team 2012  
 * All Rights Reserved. 
 *
 */
package utils;

/**
 * 
 * Project Name：cloudwei 
 * Class Name：ITMConstants
 * Class Desc： 
 * Author：Spring 
 * Create Date：Mar 12, 2012 10:04:49 AM 
 * Last Modified By：Spring 
 * Last Modified：Mar 12, 2012 10:04:49 AM 
 * Remarks： 
 * @version  
 * 
 */
public interface Constants {
	
	public final static int CMDB_CHANGE_TYPE_CAPACITY = 1;
	public final static int CMDB_CHANGE_TYPE_STATUS = 2;
	public final static int CMDB_CHANGE_TYPE_NEW = 3;
	public final static int CMDB_CHANGE_TYPE_DELETE = 4;
	
	public final static String CMDB_CHANGE_CAPACITY = "cmdb.capacity";
	public final static String CMDB_CHANGE_STATUS = "cmdb.status";
	public final static String CMDB_CHANGE_NEW = "cmdb.new";
	public final static String CMDB_CHANGE_DELETE = "cmdb.delete";

	public final static String ASSET_STATUS_RUNNING = "RUNNING";
	public final static String ASSET_STATUS_STOPPED = "STOPPED";
	
	public final static Integer UPDATE_FLAG_EXISTED = 1;
	public final static Integer UPDATE_FLAG_NONEXISTED = 0;
	
	public final static String ALARM_STATUS_OPEN = "Open";
	public final static String ALARM_STATUS_CLOSE = "Close";
	public final static String ALARM_STATUS_INSERVICE = "In Service";
	
	public final static Integer ALARM_SEVERITY_CLEAR = 0;
	public final static Integer ALARM_SEVERITY_INFORMATION = 1;
	public final static Integer ALARM_SEVERITY_WARNING = 2;
	public final static Integer ALARM_SEVERITY_MINOR = 3;
	public final static Integer ALARM_SEVERITY_MAJOR = 4;
	public final static Integer ALARM_SEVERITY_CRITICAL = 5;
	public final static Integer ALARM_SEVERITY_FATAL = 6;
	
	public final static String ALARM_SEVERITY_CLEAR_STR = "Clear";
	public final static String ALARM_SEVERITY_INFORMATION_STR = "Information";
	public final static String ALARM_SEVERITY_WARNING_STR = "Warning";
	public final static String ALARM_SEVERITY_MINOR_STR = "Minor";
	public final static String ALARM_SEVERITY_MAJOR_STR = "Major";
	public final static String ALARM_SEVERITY_CRITICAL_STR = "Critical";
	public final static String ALARM_SEVERITY_FATAL_STR = "Fatal";
	
	public final static String PORT_TYPE_10 = "N";
	public final static String PORT_TYPE_11 = "NL";
	public final static String PORT_TYPE_12 = "F/NL";
	public final static String PORT_TYPE_13 = "Nx";
	public final static String PORT_TYPE_14 = "E";
	public final static String PORT_TYPE_15 = "F";
	public final static String PORT_TYPE_16 = "FL";
	public final static String PORT_TYPE_17 = "B";
	public final static String PORT_TYPE_18 = "G";
	
    public final static String COLOR_SEVERITY_INFORMATION = "#0000ee";
    public final static String COLOR_SEVERITY_CLEAR = "#44bb55";
    public final static String COLOR_SEVERITY_WARNING = "#ffee11";
    public final static String COLOR_SEVERITY_MINOR = "#ffbb11";
    public final static String COLOR_SEVERITY_MAJOR = "#ff9911";
    public final static String COLOR_SEVERITY_CRITICAL = "#ee0011";
    public final static String COLOR_SEVERITY_FATAL = "#9f9f9f";
	
	public final static String STATUS_DEVICE_TYPE_CONTROLLER = "controller";
	public final static String STATUS_DEVICE_TYPE_PORT = "port";
	public final static String STATUS_DEVICE_TYPE_RAIDGROUP = "raidgroup";
	public final static String STATUS_DEVICE_TYPE_DISK = "disk";
	public final static String STATUS_DEVICE_TYPE_VOLUME = "volume";
	
	public final static String ASSET_TYPE_STORAGE = "STORAGE";
	public final static String ASSET_TYPE_SWITCH = "SWITCH";
	public final static String ASSET_TYPE_HOST = "HOST";
	public final static String ASSET_TYPE_TAPE = "TAPE";
	public final static String ASSET_TYPE_DATABASE = "DATABASE";
	public final static String ASSET_TYPE_CONTROLLER = "CONTROLLER";
	
	public static String ORACLE = "ORACLE";
    public static String MYSQL = "MYSQL";
    public static String KPI_QUERY_INTERVAL_QUARTER = "quarter";
    public static String KPI_QUERY_INTERVAL_HOUR = "hour";
    public static String KPI_QUERY_INTERVAL_DAY = "day";
    public static String KPI_SUB_TYPE_BASE = "base";
    public static String KPI_SUB_TYPE_EXTEND = "extend";
    
	/**
	 * Fusion Chart
	 */
	public final static String PLUGIN_TYPE_PIE = "PIE";
	public final static String PLUGIN_TYPE_COLUMN = "COLUMN";
	public final static String PLUGIN_TYPE_COMBINE = "COMBINE";
	public final static String PLUGIN_TYPE_COLUMN3D	="Column3D";
	public final static String PLUGIN_TYPE_COLUMN2D	="Column2D";
	public final static String PLUGIN_TYPE_LINE	="Line";
	public final static String PLUGIN_TYPE_AREA2D	="Area2D";
	public final static String PLUGIN_TYPE_BAR2D	="Bar2D";
	public final static String PLUGIN_TYPE_PIE2D	="Pie2D";
	public final static String PLUGIN_TYPE_PIE3D	="Pie3D";
	public final static String PLUGIN_TYPE_DOUGHNUT2D	="Doughnut2D";
	public final static String PLUGIN_TYPE_DOUGHNUT3D	="Doughnut3D";
	public final static String PLUGIN_TYPE_PARETO2D	="Pareto2D";
	public final static String PLUGIN_TYPE_PARETO3D	="Pareto3D";
	public final static String PLUGIN_TYPE_MSCOLUMN2D	="MSColumn2D";
	public final static String PLUGIN_TYPE_MSCOLUMN3D	="MSColumn3D";
	public final static String PLUGIN_TYPE_MSLINE	="MSLine";
	public final static String PLUGIN_TYPE_MSBAR2D	="MSBar2D";
	public final static String PLUGIN_TYPE_MSBAR3D	="MSBar3D";
	public final static String PLUGIN_TYPE_MSAREA	="MSArea";
	public final static String PLUGIN_TYPE_MARIMEKKO	="Marimekko";
	public final static String PLUGIN_TYPE_ZOOMLINE	="ZoomLine";
	public final static String PLUGIN_TYPE_STACKEDCOLUMN3D	="StackedColumn3D";
	public final static String PLUGIN_TYPE_STACKEDCOLUMN2D	="StackedColumn2D";
	public final static String PLUGIN_TYPE_STACKEDBAR2D	="StackedBar2D";
	public final static String PLUGIN_TYPE_STACKEDBAR3D	="StackedBar3D";
	public final static String PLUGIN_TYPE_STACKEDAREA2D	="StackedArea2D";
	public final static String PLUGIN_TYPE_MSSTACKEDCOLUMN2D	="MSStackedColumn2D";
	public final static String PLUGIN_TYPE_MSCOMBI3D	="MSCombi3D";
	public final static String PLUGIN_TYPE_MSCOMBI2D	="MSCombi2D";
	public final static String PLUGIN_TYPE_MSCOLUMNLINE3D	="MSColumnLine3D";
	public final static String PLUGIN_TYPE_STACKEDCOLUMN2DLINE	="StackedColumn2DLine";
	public final static String PLUGIN_TYPE_STACKEDCOLUMN3DLINE	="StackedColumn3DLine";
	public final static String PLUGIN_TYPE_MSCOMBIDY2D	="MSCombiDY2D";
	public final static String PLUGIN_TYPE_MSCOLUMN3DLINEDY	="MSColumn3DLineDY";
	public final static String PLUGIN_TYPE_STACKEDCOLUMN3DLINEDY	="StackedColumn3DLineDY";
	public final static String PLUGIN_TYPE_MSSTACKEDCOLUMN2DLINEDY	="MSStackedColumn2DLineDY";
	public final static String PLUGIN_TYPE_SCATTER	="Scatter";
	public final static String PLUGIN_TYPE_BUBBLE	="Bubble";
	public final static String PLUGIN_TYPE_SCROLLCOLUMN2D	="ScrollColumn2D";
	public final static String PLUGIN_TYPE_SCROLLLINE2D	="ScrollLine2D";
	public final static String PLUGIN_TYPE_SCROLLAREA2D	="ScrollArea2D";
	public final static String PLUGIN_TYPE_SSGRID	="SSGrid";
	
	/**
	 * Power Chart
	 */
	public final static String PLUGIN_TYPE_LOGMSCOLUMN2D	="LogMSColumn2D";
	public final static String PLUGIN_TYPE_LOGMSLINE	="LogMSLine";
	public final static String PLUGIN_TYPE_SPLINE	="Spline";
	public final static String PLUGIN_TYPE_SPLINEAREA	="SplineArea";
	public final static String PLUGIN_TYPE_MSSPLINE	="MSSpline";
	public final static String PLUGIN_TYPE_MSSPLINEAREA	="MSSplineArea";
	public final static String PLUGIN_TYPE_ERRORBAR2D	="ErrorBar2D";
	public final static String PLUGIN_TYPE_ERRORLINE2D	="ErrorLine2D";
	public final static String PLUGIN_TYPE_ERRORSCATTER	="ErrorScatter";
	public final static String PLUGIN_TYPE_INVERSEMSAREA2D	="InverseMSArea2D";
	public final static String PLUGIN_TYPE_INVERSEMSCOLUMN2D	="InverseMSColumn2D";
	public final static String PLUGIN_TYPE_INVERSEMSLINE	="InverseMSLine";
	public final static String PLUGIN_TYPE_MSAREA2D	="MSArea2D";
	public final static String PLUGIN_TYPE_RADAR	="Radar";
	public final static String PLUGIN_TYPE_HEATMAP	="HeatMap";
	public final static String PLUGIN_TYPE_BOXANDWHISKER2D	="BoxAndWhisker2D";
	public final static String PLUGIN_TYPE_CANDLESTICK	="Candlestick";
	public final static String PLUGIN_TYPE_DRAGNODE	="DragNode";
	public final static String PLUGIN_TYPE_MSSTEPLINE	="MSStepline";
	public final static String PLUGIN_TYPE_MULTIAXISLINE	="MultiAxisLine";
	public final static String PLUGIN_TYPE_MULTILEVELPIE	="MultiLevelPie";
	public final static String PLUGIN_TYPE_WATERFALL2D	="Waterfall2D";
	public final static String PLUGIN_TYPE_KAGI	="Kagi";

	/**
	 * Widgets Chart
	 */
	public final static String PLUGIN_TYPE_ANGULARGAUGE	="AngularGauge";
	public final static String PLUGIN_TYPE_BULB	="Bulb";
	public final static String PLUGIN_TYPE_CYLINDER	="Cylinder";
	public final static String PLUGIN_TYPE_HLED	="HLED";
	public final static String PLUGIN_TYPE_HLINEARGAUGE	="HLinearGauge";
	public final static String PLUGIN_TYPE_THERMOMETER	="Thermometer";
	public final static String PLUGIN_TYPE_VLED	="VLED";
	public final static String PLUGIN_TYPE_REALTIMEAREA	="RealTimeArea";
	public final static String PLUGIN_TYPE_REALTIMECOLUMN	="RealTimeColumn";
	public final static String PLUGIN_TYPE_REALTIMELINE	="RealTimeLine";
	public final static String PLUGIN_TYPE_REALTIMESTACKEDAREA	="RealTimeStackedArea";
	public final static String PLUGIN_TYPE_REALTIMESTACKEDCOLUM	="RealTimeStackedColum";
	public final static String PLUGIN_TYPE_REALTIMELINEDY	="RealTimeLineDY";
	public final static String PLUGIN_TYPE_SPARKLINE	="SparkLine";
	public final static String PLUGIN_TYPE_SPARKCOLUMN	="SparkColumn";
	public final static String PLUGIN_TYPE_SPARKWINLOSS	="SparkWinLoss";
	public final static String PLUGIN_TYPE_HBULLET	="HBullet";
	public final static String PLUGIN_TYPE_VBULLET	="VBullet";
	public final static String PLUGIN_TYPE_FUNNEL	="Funnel";
	public final static String PLUGIN_TYPE_PYRAMID	="Pyramid";
	public final static String PLUGIN_TYPE_GANTT	="Gantt";
	public final static String PLUGIN_TYPE_DRAWINGPAD	="DrawingPad";


	
	
	public final static String DASHBOARD_TYPE_DASHBOARD = "dashboard";
	public final static String DASHBOARD_TYPE_PERFORMANCE = "performance";
	
	public final static String KPI_TYPE_FUNCTION_CFG = "Configuration";
	public final static String KPI_TYPE_FUNCTION_PRF = "Performance";
	public final static String KPI_TYPE_FUNCTION_STATUS = "Status";
	public final static String KPI_TYPE_FUNCTION_STATS = "Statistics";
	public final static String KPI_TYPE_FUNCTION_ASSET = "Asset";
	public final static String KPI_TYPE_FUNCTION_ALARM = "Alarm";
	
	public final static String DEVICE_TYPE_HOST = "Host";
	public final static String DEVICE_TYPE_SWITCH = "Switch";
	public final static String DEVICE_TYPE_SUBSYSTEM = "Subsystem";
	public final static String DEVICE_TYPE_PORT = "Port";
	public final static String DEVICE_TYPE_PV = "PV";
	public final static String DEVICE_TYPE_FILESYSTEM = "FileSystem";
	public final static String DEVICE_TYPE_VOLUME = "Volume";
	public final static String DEVICE_TYPE_DISK = "Disk";
	public final static String DEVICE_TYPE_PHYSICAL_DISK = "Physical Disk";
	public final static String DEVICE_TYPE_HOST_PORT = "Host_port";
	public final static String DEVICE_TYPE_FC_PORT = "FC_port";
	public final static String DEVICE_TYPE_STG_PORT = "STG_port";
	public final static String DEVICE_TYPE_TAPE = "Tape";
	public final static String DEVICE_TYPE_LUN_MAPPING = "lun_mapping";
	public final static String DEVICE_TYPE_ZONE = "zone";
	public final static String DEVICE_TYPE_STORAGE_FILE_SYSTEM = "storage_file_system";
	public final static String DEVICE_TYPE_STORAGE_FILE = "storage_file";
	public final static String DEVICE_TYPE_BATTERY = "Battery";
	public final static String DEVICE_TYPE_PORT2PORT = "Port2Port";
	public final static String USER = "User";
	
	
	//add by wanghaipeng 2012-07-13 begin
	public final static String GROUP_NAME = "GroupName";//管理组名称
	public final static String USER_NAME = "UserName";//用户名称
	public final static String ADMIN = "Admin";//系统管理员组
	public final static String MONITOR = "Monitor";//系统管理员
	//add by wanghaipeng 2012-07-13 end
	
	/**
	 * For trap
	 */
	
	public final static String OID = "OID";
	public final static String VARIABLE = "VARIABLE";
	public final static String RESPONSE = "RESPONSE";
	
	public final static String DataBase = "oracle";
	
	/**
	 * For dbtype
	 */
	
	public final static String DB_TYPE_MYSQL = "Mysql";
	public final static String DB_TYPE_ORACLE = "Oracle";
	
	/**
	 * For Aggregation
	 */
	public final static String AGGREGATION_ZONE_MEMBER = "aggregation_zone_member";
	public final static String AGGREGATION_SUBSYSTEM = "aggregation_subsystem";
	public final static String AGGREGATION_SWITCH = "aggregation_switch";
	public final static String AGGREGATION_SWITCH_LINK = "aggregation_switch_link";
	public final static String AGGREGATION_SWITCH_PORT = "aggregation_switch_port";
	public final static String AGGREGATION_STORAGE_LINK = "aggregation_storage_link";
	public final static String AGGREGATION_STORAGE_PORT = "aggregation_storage_port";
	public final static String AGGREGATION_HOST = "aggregation_host";
	public final static String CHANGED_SUMMARY = "changed_summary";
	public final static String BUSINESS_RELAIOION = "business_relation";
	

	/**
	 * For Portlet  Manage
	 */
	public final static String PORTLET_MANAGE_DEFAULT = "Default";
	public final static String PORTLET_MANAGE_UNIQUE = "Unique";
	public final static String PORTLET_MANAGE_NONE = "None";
	
	public final static int SUMMARY_TYPE_STATUS = 2;
	public final static int SUMMARY_TYPE_CONFIG = 1;
	
	
	
	
}
