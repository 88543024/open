package models.record;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;

import play.db.jpa.Model;
/**
 * 资源操作日志
 * @author wzj
 */
@Entity
@Table(name="T_RES_OPERATION_LOG")
@AccessType("field")
public class TResOperationLog extends Model{
	
	public String WORK_ORDER_ID;//工单编号
	
	public String IP;
	
	public String OPERATION_TIME;//调整时间

	public String APPLY_MAN;//申请人
	
	public String OPERATION_MAN;//操作人
	
	public String OPERATION_CONTENT;//调整内容
	
}
