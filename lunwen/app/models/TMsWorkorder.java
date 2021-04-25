package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="T_MS_WORK_ORDER")
@AccessType("field")
public class TMsWorkorder extends Model{
	/** 虚拟机CPU*/
	public String vcpu;
	/** 内存 */
	public String memory;
	/** 操作系统 */
	public String os;
	/** 数据库 */
	public String dbsoft;
	/** 中间件 */
	public String middleware;
	/** 磁盘大小 */
	public String disksize;
	/** 这一类的虚机要创建的个数 */
	public Integer count;
	/** 备注 */
	public String memo;
	/** 状态 */
	public String status;//1工单未处理;2虚拟机创建中;3虚拟机创建完成;4虚拟机调整中;5虚拟机调整完成;6手工操作完成  //已废弃
						//1工单未处理;2虚拟机创建中;3虚拟机创建完成;4虚拟机创建失败;5虚拟机调整中;6虚拟机调整完成;7虚拟机调整失败，8手工操作完成
	/** 主机名称 */
	public String hostname;
	/** IP地址 */
	public String ip;
	/** 用户名 */
	public String username;
	/** 密码 */
	public String password;
	/**工单ID */
	@Required
	public Integer ELEMENT_ID;
	
	public String appleType;//add by wzj 申请类型：RES_INDEPENDENT_APPLY、单独申请；null：工单申请
	

    public static TMsWorkorder getOrderByElementId(Integer elementId){
        return find("byELEMENT_ID", elementId).first();
    }
	
    public static List<TMsWorkorder> getOrderListByElementId(Integer elementId){
        List<TMsWorkorder> tMsWorkorderList = TMsWorkorder.find("ELEMENT_ID=? order by id asc", elementId).fetch();
        return tMsWorkorderList;
    }
    
    public static List<TMsWorkorder> getOrderListCompleted() {
    	List<TMsWorkorder> tMsWorkorderList = TMsWorkorder.find("status='2' or status='3' or status='5' or status='6' or status='8' order by id asc").fetch();
        return tMsWorkorderList;
    }
}
