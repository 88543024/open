package models;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;

import play.db.jpa.Model;

@Entity
@Table(name="T_RESOURCE_POOL")
@AccessType("field")
public class TResourcePool extends Model{
	//CPU池总数目
	public Integer CPU_NUMBER_TOTAL;
	//CPU池使用数目
	public Integer CPU_NUMBER_USED;
	//内存池总数 （GB)
	public Integer MEMORY_SIZE_TOTAL;
	//内存池使用数
	public Integer MEMORY_SIZE_USED;
	//存储池总量 (GB)
	public Integer CAPACITY_TOTAL;
	//存储池使用量
	public Integer CAPACITY_USED;

}
