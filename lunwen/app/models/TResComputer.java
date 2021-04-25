package models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.AccessType;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="T_RES_COMPUTER",uniqueConstraints={@UniqueConstraint(columnNames={"IP","NAME"})})
@AccessType("field")
public class TResComputer extends Model {
	//机器名称	
	@Required
	public String NAME;
	//系统信息	
	public String SYS_INFO;
	//操作系统	
	public String OS;
    //IP地址	
	@Required
	public String IP;
	//安全等保	String
	public String SECURITY_LEVEL;
	//服务器角色	String
	public String SERVER_ROLE;
	//机器配置	
	public Integer CPU_NUMBER;
	
	public Integer MEMORY_SIZE;	
	//数据盘大小	(GB)
	public Integer DATA_VG_SIZE;	
	//状态	
	public String STATUS;
	//更新时间	
	@Required
	public Timestamp UPDATE_TIME;
	
	public Integer WORK_ORDER_ID;
	//地点url
	public String SITE_URL;
}
