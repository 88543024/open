/**
 * File Name：PrfModel.java
 *
 * Version：
 * Date：2012-3-6
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package models;

import java.sql.Timestamp;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * 
 * Project Name：com.cloudwei.monitor.model
 * Class Name：PrfModel
 * Class Desc：
 * Author：tigaly
 * Create Date：2012-3-6 上午11:42:57
 * Last Modified By：tigaly
 * Last Modified：2012-3-6 上午11:42:57
 * Remarks：
 * @version 
 * 
 */

@MappedSuperclass
public class PrfModel extends Model {
	public String TIME_ID;
	@Required
	public Integer ELEMENT_ID;
	public Timestamp START_TIME;
	public Timestamp END_TIME;
	public Integer INTERVAL_LEN;
	@Required
	public Timestamp UPDATE_TIME; 
}
