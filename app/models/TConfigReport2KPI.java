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
 * Project Name：istorm_v2.0 
 * Class Name：TConfigReport2KPI 
 * Class Desc： 
 * Author：Fred 
 * Create Date：2012-4-26 上午11:55:37 
 * Last Modified By：Fred 
 * Last Modified：2012-4-26 上午11:55:37 
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
