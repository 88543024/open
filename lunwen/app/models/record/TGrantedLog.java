package models.record;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;

import play.data.validation.Required;
import play.db.jpa.Model;
/**
 * 授权记录查询
 * @author wzj
 */
@Entity
@Table(name="T_GRANTED_LOG")
@AccessType("field")
public class TGrantedLog extends Model{
	
	public String UNAME;//用户名
	
	public String ROLETYPE;//角色类别、权限（none、supermanager/manager/operator）

	public String GRANTPERSON;//授权人
	
	public String GRANTTIME;//授权时间
}
