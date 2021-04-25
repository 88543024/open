package models;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;

import play.db.jpa.Model;

@Entity
@Table(name="T_VM_SINGLE")
@AccessType("field")
public class VmSingle extends Model{
	/** 虚拟机CPU*/
	public String vcpuNum;
	/** 内存 */
	public String memorySize;
	/** 操作系统 */
	public String sysOs;
	/** 数据库 */
	public String dbSoft;
	/** 中间件 */
	public String middleWare;
	/** 磁盘大小 */
	public String diskSize;
	/** 这一类的虚机要创建的个数 */
	public Integer VmCount;
	/** 手工操作标记 */
	public String manualFlag;
	/** 状态 */
	public String status;//1未处理;2提交中3提交成功;4提交失败;5虚拟机创建中;6虚拟机创建完成;7虚拟机创建失败;8手工操作完成
	/** 主机名称 */
	public String hostName;
	/** IP地址 */
	public String ipAdderss;
	/** 用户名 */
	public String userName;
	/** 密码 */
	public String passWord;
	/** 备注 */
	public String reMark;
	
	public String projectName;
	
	public String applyTime;
}
