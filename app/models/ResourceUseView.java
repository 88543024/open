package models;

import play.db.jpa.Model;
import javax.persistence.Entity;

@Entity
public class ResourceUseView extends Model{
	
	public String ID;
	public String Resourcecategory;
	public int CPU; //Cpu总数
	public int Memory; //内存总数
	public int CPUUse; //Cpu已用
	public int MemoryUse; //内存已用
	public int StoreSize; //存储总数
	public int StoreUse; //存储已用
	public int CPURemain;//Cpu剩余
	public int MemoryRemain;//内存剩余
	public int StoreRemain;//存储剩余
	
}
