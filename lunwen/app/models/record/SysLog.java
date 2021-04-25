package models.record;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;

import play.db.jpa.Model;
/**
 * 系统日志
 * @author wzj
 */
@Entity
@Table(name="T_SYS_LOG")
@AccessType("field")
public class SysLog extends Model{
	
	public enum LogType{
		 USER_OPERATE, //用户操作
		 ORDER_OPERATE, //工单操作
		 RESOURCEVM_OPERATE, //资源操作
		 RESOURCE_OPERATE, //资产操作
		 REPORT_QUERY //报表查询
	}
	
	public LogType LOG_TYPE;//日志类型
	
	public String LOG_CONTENT;//日志内容
	
	public String OPERATION_MAN;//操作人
	
	public Date CREATE_LOG_TIME;//日志创建时间
	
	
}
