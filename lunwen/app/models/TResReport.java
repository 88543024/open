package models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


import play.data.validation.Required;
import play.db.jpa.Model;


@Entity
@Table(name="T_RES_REPORT",uniqueConstraints={@UniqueConstraint(columnNames={"REPORT_ID"})})
@Access(AccessType.FIELD)
public class TResReport extends Model{
	@Required
	public String REPORT_ID;
	@Required
	public String REPORT_NAME;
	public String REPORT_DESCRIPTION;
	public String REPORT_CREATER;
	public Date CREATE_TIME;
	public String URL;
	public String DEVICE_TYPE;
	public String RPT_FILE_URL;
	public String REPORT_TYPE;
	public String IMG_URL;
	public String REPORT_ICON;
	public String SUB_DEVICE_TYPE;
	
	public static TResReport getReportByReportId(String reportId){
		List<TResReport> rlist = find("byREPORT_ID",reportId).fetch();
		if(rlist.size()>0){
			return rlist.get(0);
		}else{
			return null;
		}
	}
	
}
