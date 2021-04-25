package models;

import play.db.jpa.Model;
import javax.persistence.Entity;

@Entity
public class VwAssetFailurePercent extends Model{
	public int num; //序号
	//public String ID;
	//public String name;
	public String TypeName;
	public int Amount;
}
