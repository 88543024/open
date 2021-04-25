package models;

import play.db.jpa.Model;
import javax.persistence.Entity;

@Entity
public class TempAllSystemsInfo extends Model{
	public String ID;
	public String ManagementDepartmentName; //主管处室
	public String SystemName; //系统名称
	public int CPU; //CPU数量
	public int Memory; //内存数量
	public int StoreSize; //存储卷大小容量
	public int VirSerNum; //虚拟服务器数量
	
	public int num; //序号
	
}
