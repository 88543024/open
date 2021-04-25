/**
 * File Name：TConfigKPI.java 
 * 
 * Version： 
 * Date：2012-3-13 
 * Copyright CloudWei Dev Team 2012  
 * All Rights Reserved. 
 *
 */
package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.db.jpa.Model;

/**
 * 
 * Project Name：cloudwei 
 * Class Name：TConfigKPI 
 * Class Desc： 
 * Author：Fred 
 * Create Date：2012-3-13 下午5:49:50 
 * Last Modified By：Fred 
 * Last Modified：2012-3-13 下午5:49:50 
 * Remarks： 
 * @version  
 * 
 */

@Entity
@Table(name="T_CONFIG_KPI",uniqueConstraints={@UniqueConstraint(columnNames={"KPI_ID"})})
public class TConfigKPI extends Model{

	public String KPI_ID;
	public String KPI_NAME;
	public String DISPLAY_NAME;
	public String KPI_TYPE;
//	public String CALCULATION_RULE;
	public String DESCRIPTION;
	public Date CREATE_TIME;
	public Date MODIFY_TIME;
	public String KPI_SUBTYPE;
	
}
