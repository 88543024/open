package models;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="T_IMAGE")
@AccessType("field")
public class TImage extends Model{
	
	public String imageName;//镜像名称
	@Required
	public String path;//镜像路径
	@Required
	public String OS_PATCH;//操作系统版本
	@Required
	public String WEB_PATCH;//中间件版本
	@Required
	public String DB_PATCH;//数据库版本
	public String remark;//镜像备注
	public String image_id;//镜像
	public String IS_REGISTER;//镜像是否注册
}
