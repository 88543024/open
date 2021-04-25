package models;

import java.util.Date;

import play.db.jpa.Model;
import javax.persistence.Entity;

@Entity
public class VwPerformanceDaily extends Model{
	public String ID;
	public String SystemName; //系统名称
	public String ServerName; //服务器名称
	public int ISVM; //是否虚拟机
	public String isvmStr;
	public String FeatureDomain; //功能域
	public Date DateTime; //日期
	public String dateStr;
	public float ProcessorAvgValue;//CPU平均利用率
	public float ProcessorMaxValue; //CPU最大利用率
	public float MemoryAvgValue;//内存平均利用率
	public float MemoryMaxValue;//内存最大利用率
	public int CPU; //CPU数量
	public int Memory; //内存数量
	public int StoreSize; //存储卷大小容量
	public int VirSerNum; //虚拟服务器数量
	
}
