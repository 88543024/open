package models;

import play.db.jpa.Model;

public class vwAllAssetInfo extends Model{
	public int num; //序号
/*	public String SystemName; //系统名称
*/	public String Name; //服务器编号
	public String SN; //数量
	public String VendorName_cat; //品牌
	public String Model; //型号
	public String IP; //IP
	public String CabinetID; //机柜
	public String CabinetLocat; //所在机柜位置
/*	public String ServerRole; //用途
*/	public String AssetType; //物理服务器
}
