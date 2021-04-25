package models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.AccessType;

import models.base.Model;
import models.base.ResModel;

@Entity
@Table(name="T_RES_REPORT_RECORD",uniqueConstraints={@UniqueConstraint(columnNames={"ID","REPORT_ID"})})
@AccessType("field")
public class TResReportRecord extends play.db.jpa.Model{
	public String NAME;
	public String REPORT_ID;
	public String REPORT_NAME;
	public String REPORT_DESCRIPTION;
	public String REPORT_TYPE;
	public String REPORT_CREATER;
	public String REPORT_MONITOR;
	public Timestamp CREATE_TIME;
	public Timestamp EXPORT_TIME;
	public String DOWNLOAD_PATH;
	public int DOWNLOAD_TIMES;
	public int IS_MAIL;
	@Transient
	public int NUM;
}
