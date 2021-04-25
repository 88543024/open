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
import javax.persistence.Lob;
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
@Table(name="T_CONFIG_KPI_QUERY",uniqueConstraints={@UniqueConstraint(columnNames={"KPI_ID","DB_TYPE","QUERY_MODE","INTERVAL_LEN"})})
public class TConfigKPIQuery extends Model{

	public String KPI_ID;
	public String DB_TYPE;
	@Lob
	public String KPI_SQL;
	@Lob
	public String KPI_HQL;
	public Date CREATE_TIME;
	public Date MODIFY_TIME;
	public String KPI_RULE;
	public String KPI_TABLE;
	public String QUERY_MODE;
	public String ADD_RULE;
	public String INTERVAL_LEN;
	
	
}
