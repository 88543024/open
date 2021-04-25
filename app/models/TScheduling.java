package models;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="T_SCHEDULING")
@AccessType("field")
public class TScheduling extends Model {
	/** 调度管理类型 */
	@Required
	public String SCH_TYPE;
	/** 选项（循环执行日:每天/每周/每月） */
	public String SCH_OPTION;//
	/** 循环执行日 （周二、月末最后一天）*/
	public String OPERATE_DAY;
	/** 循环执行小时 */
	public String OPERATE_HOUR;
	/** 循环执行分钟 */
	public String OPERATE_MINUTE;
	
	public static TScheduling getScheBySchTypeAndOption(String schType,String option) {
        return find("bySCH_TYPEAndSCH_OPTION", schType, option).first();
    }
    
}
