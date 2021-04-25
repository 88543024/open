package models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;

import play.db.jpa.Model;

@Entity
@Table(name="T_RESOURE")
@AccessType("field")
public class TResource extends Model {
	
	/** 项目名称 */
	public String PRO_NAME;//add by wzj
	/** 机房位置 */
	public String ROOM_POSITION;
	/** 内部编号 */
	public String INTERNAL_NUMBER ;
	/** 机柜编号 */
	public String RACK_NUMBER;
	/** 具体位置 */
	public String LOCATION;
	/** 应用名称 */
    public String APP_NAME;
	/** 品牌 */
	public String VENDOR;
	/** 型号 */
	public String MODEL;
	/** 序列号 */
	public String SN;
	/** 配置 */
	public String CONFIG;
	/** 设备类型 */
	public String DEVICE_TYPE;
	/** 分区信息 */
	public String PARTITION_INFO;
	/** IP地址 */
    public String IP;
    /** 操作系统 */
    public String OS;
	/** 进入机房时间 */
    public Timestamp BEGIN_DATE;
	/** 保修截止时间 */
    public Timestamp END_DATE;
	/** 资产所属部门 */
    public String DEPARTMENT;
	/** 应用部门 */
    public String APP_DEPARTMENT;
	/** 联系人 */
    public String CONTACTS;
	/** 联系电话 */
    public String TELEPHONE;
	/** 备注 */
    public String REMARK;
}
