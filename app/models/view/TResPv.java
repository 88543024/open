package models.view;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;

import models.base.Model;
import models.base.ResModel;


@Entity
@Table(name = "T_RES_PV")
@AccessType("field")
public class TResPv extends Model{
/*    @javax.persistence.Id
    @javax.persistence.GeneratedValue*/
	//public String id;
	//public String NAME;
	//业务字段
	public String StoreName;
	public String VolumeName ;
	//实体字段
    public Long CAPACITY;
	public String VOLUME_ID;
	public String SUBSYSTEM_ID;
	public Date CHANGED_TIME;
	public String CONSOLIDATED_STATUS;
	public String DESCRIPTION;
	public Date DISCOVERED_TIME;
	public String DISPLAY_NAME;
	public String ELEMENT_NAME;
	public String HEALTH_STATUS;
	public String MODEL_ID;
	public String OPERATIONAL_STATUS;
	public String PROPAGATED_STATUS;
	//public String SUBSYSTEM_ID;
	public Date UPDATE_TIME;
	public String USER_ATTRIB1;
	public String USER_ATTRIB2;
	public String USER_ATTRIB3;
	public String VENDOR_ID;
	//public int CAPACITY;
	public String DEVICE_TYPE;
	public String DISK_TYPE;
	public String FIRMWARE_REV;
	public Long FLAGS;
	public Long FREE_PP;
	public Long IS_REMOTE;
	public String MANUFACTURE;
	public String PARENT_PATH_ID;
	public String PARENT_PATH_NAME;
	public String RAID_LEVEL;
	public String RAWDEVICE_ID;
	public String SERIAL_NUMBER;
	public String SPEED;
	public String TAG;
	public Long TOTAL_PP;
	public String UNSUPPORTED_MODEL;
	public Long USED_CAPACITY;
	public Long USED_PP;
	public Long USE_COUNT;
	public String VG_ID;
	//public String VOLUME_ID;
	
	/* public static List<TResPv> getTResPvByEleId(String subSystemId){
		// List <TResPv> tResPv =(List)TResPv.find("SUBSYSTEM_ID",subSystemId);
		 String a = "D6DF9DCCF66CA7E6674850193A29F6D6";
		 List <TResPv> tResPv = TResPv.find("select a from TResPv a where a.SUBSYSTEM_ID = '"+a+"'").fetch();
		 return tResPv;
	 }*/
}
