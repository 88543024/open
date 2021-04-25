/**
 * File Name：ResModel.java
 *
 * Version：
 * Date：2012-3-6
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models.base;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.MappedSuperclass;


import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Index;

import play.db.jpa.JPA;

/**
 * 
 * Project Name：com.cloudwei.monitor.model
 * Class Name：Res
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-6 上午11:24:46
 * Last Modified By：tigaly
 * Last Modified：2012-3-6 上午11:24:46
 * Remarks：
 * @version 
 * 
 */
@MappedSuperclass
public class ResModel extends Model implements Comparable<ResModel>{
	public String SUBSYSTEM_ID;
	public String ELEMENT_NAME;
	public String DISPLAY_NAME;
	public String DESCRIPTION;
	public String VENDOR_ID;
	public String MODEL_ID;
	public String OPERATIONAL_STATUS;
	public String CONSOLIDATED_STATUS;
	public String PROPAGATED_STATUS;
	public String HEALTH_STATUS;
	public String USER_ATTRIB1;
	public String USER_ATTRIB2;
	public String USER_ATTRIB3;
	public Timestamp DISCOVERED_TIME; 
	public Timestamp UPDATE_TIME; 
	/**
	 * The time of something changed(E.g. Capicity changed,Status changed) 
	 */
	public Timestamp CHANGED_TIME;
	
	@Override
	public int compareTo(ResModel resModel) {
		if(USER_ATTRIB1 != null && resModel.USER_ATTRIB1 != null && USER_ATTRIB2 != null && resModel.USER_ATTRIB2 != null){
			if(Integer.valueOf(this.USER_ATTRIB1,16).intValue() < Integer.valueOf(resModel.USER_ATTRIB1,16).intValue()){
				return -1;
			}else if(Integer.valueOf(this.USER_ATTRIB1,16).intValue() > Integer.valueOf(resModel.USER_ATTRIB1,16).intValue()){
				return 1;
			}else if(Integer.valueOf(this.USER_ATTRIB1,16).intValue() == Integer.valueOf(resModel.USER_ATTRIB1,16).intValue()){
				if(Integer.valueOf(this.USER_ATTRIB2,16).intValue() < Integer.valueOf(resModel.USER_ATTRIB2,16).intValue()){
					return -1;
				}else if(Integer.valueOf(this.USER_ATTRIB2,16).intValue() > Integer.valueOf(resModel.USER_ATTRIB2,16).intValue()){
					return 1;
				}
			}
		}
		return 0;
	}
	
	
	public Object get(String fieldName){
		try {
			Field field = getClass().getField(fieldName);
			return field.get(this);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void set(String fieldName,String value){
		try {
			Field field = getClass().getField(fieldName);
			field.set(this, value);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}
	
}
