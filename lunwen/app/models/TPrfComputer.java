package models;


public class TPrfComputer extends PrfModel {	
	// CPU利用率	
	public Integer CPU_UTILIZATION;
	//内存利用率	
	public Integer MEMORY_UTILIZATION;
	//系统盘已用	(GB)
	public Integer ROOT_VG_USED;
	//数据盘已用	(GB)
	public Integer DATA_VG_USED;
	//网络Ping情况
	public String PING_STATUS;
}
