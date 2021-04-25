package models.view;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.base.Model;

@Entity
@Table(name = "V_HOST_LUN_MAPPING")
public class VHostLunMapping extends Model {

	public String HOST_NAME;
	public String HBA_WWN;
	public String VOLUME_NAME;
	public Long CAPACITY;
	public String SUBSYSTEM_NAME;

}
