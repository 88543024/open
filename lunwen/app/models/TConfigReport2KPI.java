/**
 * File Name：TConfigReport2KPI.java 
 * 
 * Version： 
 * Date：2012-4-26 
 * Copyright CloudWei Dev Team 2012  
 * All Rights Reserved. 
 *
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.db.jpa.Model;

/**
 * 
 * Project Name:lunwen 
 * Remarks： 
 * @version  
 * 
 */
@Entity
@Table(name="T_CONFIG_REPORT2KPI",uniqueConstraints={@UniqueConstraint(columnNames={"REPORT_ID","KPI_ID"})})
public class TConfigReport2KPI extends Model{

	public String KPI_ID;
	public String REPORT_ID;
	
	
}
