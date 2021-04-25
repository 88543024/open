/**
 * File Name：TResSwitch.java
 *
 * Version：
 * Date：2012-3-2
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Formula;

import utils.Constants;

import models.base.ResModel;

/**
 * 
 * Project Name：com.cldouwei.monitor.model
 * Class Name：TResSwitch
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-2 上午12:04:40
 * Last Modified By：tigaly
 * Last Modified：2012-3-2 上午12:04:40
 * Remarks：
 * @version 
 * 
 */
@Entity
@Table(name="T_RES_SWITCH",uniqueConstraints={@UniqueConstraint(columnNames={"NAME"})})
@AccessType("field")
public class TResSwitch extends ResModel{
	public String PARENT_SWITCH_WWN;
	public String SERIAL_NUMBER;
	public String CONTACT;
	public String LOCATION;
	public String IP_ADDRESS;
	/**
	 *  1 - Physical Device<br>
	 *  2 - Virtual Device
	 */
	public Short IS_PHYSICAL;
	public String DOMAIN;
	public String MANAGEMENT_ID;
	public String MGMT_TEL_ADDR;
	public String MGMT_SNMP_ADDR;
	public String MGMT_URL_ADDR;
	public String VERSION;
	public String DEDICATED;
	public Short SWITCH_MODE;
	public String PHYSICAL_PACKAGE_ID;
	@Formula("(select count(*) from T_RES_SWITCH2PORT s2p where s2p.SWITCH_ID=ID)")
	public Integer PORT_NUM;
	@Formula("(select count(*) from T_RES_SWITCH_BLADE b where b.SUBSYSTEM_ID=ID)")
	public Integer BLADE_NUM;
	
	@Formula("(select cs.statusKey from T_CONFIG_STATUS cs where cs.deviceType = 'Common'  and OPERATIONAL_STATUS = cs.statusValue)")
	public String STATUS;
	
	public static List findSwitchs(String userName,String groupName){
		if(null!=groupName&&groupName.equals(utils.Constants.ADMIN)){
			return TResSwitch.findAll();
		}else{
			List list = TResSwitch.find(" select s from TResSwitch s ,TResBusiness2Device b2d,TResBusiness2User b2u where s.ID = b2d.DEVICE_ID  b2d.DEVICE_TYPE = 'Switch' and b2d.BUSINESS_ID = b2u.BUSINESS_ID and b2u.USERNAME ='"+userName+"'").fetch();
			return list;
		}
	}
	
	public static List getSwitchs(String userName,String groupName){
		if(null!=groupName&&groupName.equals(utils.Constants.ADMIN)){
			List list = TResSwitch.find("select distinct s from TResSwitch s,TConfigManagePortlet mp where s.ID = mp.ELEMENT_ID").fetch();
			return list;
		}else{
			List list = TResSwitch.find(" select s from TResSwitch s ,TResBusiness2Device b2d,TResBusiness2User b2u where s.ID = b2d.DEVICE_ID and b2d.BUSINESS_ID = b2u.BUSINESS_ID and b2u.USERNAME ='"+userName+"' and b2d.DEVICE_TYPE = '"+Constants.DEVICE_TYPE_SWITCH+"'").fetch();
			return list;
		}
	}
}
