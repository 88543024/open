package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.AccessType;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="T_WORK_ORDER",uniqueConstraints={@UniqueConstraint(columnNames={"ELEMENT_ID","PROJECT_ID"})})
@AccessType("field")
public class TWorkorder extends Model {
	/** 申请单标识 */
	@Required
	public Integer ELEMENT_ID;
	/** 项目名称 */
	public String PROJECT_NAME;
	/** 项目编号 */	
	@Required
	public String PROJECT_ID;
	/** 系统简称 */	
	public String SYS_SHORTNAME;
	/** 系统名称 */	
	public String SYS_NAME;
	/** 主管处室标识 */	
	public Integer COMPETENT_OFFICE_ID;
	/** 主管处室名称 */	
	public String COMPETENT_OFFICE_NAME;
	/** 业务牵头部门 */	
	public String BUSINESS_LEAD_DEPARTMENT;
	/** 业务牵头部门联系人 */
    public String BUSINESS_LEAD_PERSON;
	/** 务牵头部门联系人电话 */
    public String BUSINESS_LEAD_PHONE;
	/** 申请时间	datetime */	
    public String APPLY_TIME;
	/** 申请人	string */
    public String APPLY_PERSON;
	/** 申请单类型		1资源申请总部/2资源申请企业/3资源追加总部/4资源追加企业/5资源调整/6资源优化建议 */
    public String APPLY_TYPE;
	/** 申请单名称 */	
    public String APLLY_NAME;
	/** X86虚拟机资源		json */
    public String X86_RESOURCE;
	/** X86群集		Json */
    public String x86_CLUSTER;
	/**  小机资源	Json(包括中间件、数据库) */
    public String MINICOMPUTER_RESOURCE;
	/**  小机资源其他需求	string */	
    public String MINICOMPUTER_REMARKS;
	/**  小机资源对应工单标识 */	
    public Integer MINICOMPUTER_WORKORDER_ID;
	/** 工单标识 */	
    public Integer WORKORDER_ID;
	/** 执行人 */	
    public String OPERATOR;
    /** 分配人*/
    public String ASSIGNER;
	/** 响应时间 */	
    public String RESPONSE_TIME;
    /** 完成时间 */	
    public String COMPLETION_TIME;
    /** 工单状态 */
    public String STATUS;//1未处理;2配单中;3已配单;4处理中(执行中);5已处理(执行完)
    /** 工单进度*/
    public String PROGRESS;
    /** 备注 */
    public String REMARK;
    /** url **/
    public String SiteUrl;
    
	public String getSiteUrl() {
		return SiteUrl;
	}
	public void setSiteUrl(String siteUrl) {
		SiteUrl = siteUrl;
	}
	public Integer getELEMENT_ID() {
		return ELEMENT_ID;
	}
	public void setELEMENT_ID(Integer eLEMENT_ID) {
		ELEMENT_ID = eLEMENT_ID;
	}
	public String getPROJECT_NAME() {
		return PROJECT_NAME;
	}
	public void setPROJECT_NAME(String pROJECT_NAME) {
		PROJECT_NAME = pROJECT_NAME;
	}
	public String getPROJECT_ID() {
		return PROJECT_ID;
	}
	public void setPROJECT_ID(String pROJECT_ID) {
		PROJECT_ID = pROJECT_ID;
	}
	public String getSYS_SHORTNAME() {
		return SYS_SHORTNAME;
	}
	public void setSYS_SHORTNAME(String sYS_SHORTNAME) {
		SYS_SHORTNAME = sYS_SHORTNAME;
	}
	public String getSYS_NAME() {
		return SYS_NAME;
	}
	public void setSYS_NAME(String sYS_NAME) {
		SYS_NAME = sYS_NAME;
	}
	public Integer getCOMPETENT_OFFICE_ID() {
		return COMPETENT_OFFICE_ID;
	}
	public void setCOMPETENT_OFFICE_ID(Integer cOMPETENT_OFFICE_ID) {
		COMPETENT_OFFICE_ID = cOMPETENT_OFFICE_ID;
	}
	public String getCOMPETENT_OFFICE_NAME() {
		return COMPETENT_OFFICE_NAME;
	}
	public void setCOMPETENT_OFFICE_NAME(String cOMPETENT_OFFICE_NAME) {
		COMPETENT_OFFICE_NAME = cOMPETENT_OFFICE_NAME;
	}
	public String getBUSINESS_LEAD_DEPARTMENT() {
		return BUSINESS_LEAD_DEPARTMENT;
	}
	public void setBUSINESS_LEAD_DEPARTMENT(String bUSINESS_LEAD_DEPARTMENT) {
		BUSINESS_LEAD_DEPARTMENT = bUSINESS_LEAD_DEPARTMENT;
	}
	public String getBUSINESS_LEAD_PERSON() {
		return BUSINESS_LEAD_PERSON;
	}
	public void setBUSINESS_LEAD_PERSON(String bUSINESS_LEAD_PERSON) {
		BUSINESS_LEAD_PERSON = bUSINESS_LEAD_PERSON;
	}
	public String getBUSINESS_LEAD_PHONE() {
		return BUSINESS_LEAD_PHONE;
	}
	public void setBUSINESS_LEAD_PHONE(String bUSINESS_LEAD_PHONE) {
		BUSINESS_LEAD_PHONE = bUSINESS_LEAD_PHONE;
	}
	public String getAPPLY_TIME() {
		return APPLY_TIME;
	}
	public void setAPPLY_TIME(String aPPLY_TIME) {
		APPLY_TIME = aPPLY_TIME;
	}
	public String getAPPLY_PERSON() {
		return APPLY_PERSON;
	}
	public void setAPPLY_PERSON(String aPPLY_PERSON) {
		APPLY_PERSON = aPPLY_PERSON;
	}
	public String getAPPLY_TYPE() {
		return APPLY_TYPE;
	}
	public void setAPPLY_TYPE(String aPPLY_TYPE) {
		APPLY_TYPE = aPPLY_TYPE;
	}
	public String getAPLLY_NAME() {
		return APLLY_NAME;
	}
	public void setAPLLY_NAME(String aPLLY_NAME) {
		APLLY_NAME = aPLLY_NAME;
	}
	public String getX86_RESOURCE() {
		return X86_RESOURCE;
	}
	public void setX86_RESOURCE(String x86_RESOURCE) {
		X86_RESOURCE = x86_RESOURCE;
	}
	public String getX86_CLUSTER() {
		return x86_CLUSTER;
	}
	public void setX86_CLUSTER(String x86_CLUSTER) {
		this.x86_CLUSTER = x86_CLUSTER;
	}
	public String getMINICOMPUTER_RESOURCE() {
		return MINICOMPUTER_RESOURCE;
	}
	public void setMINICOMPUTER_RESOURCE(String mINICOMPUTER_RESOURCE) {
		MINICOMPUTER_RESOURCE = mINICOMPUTER_RESOURCE;
	}
	public String getMINICOMPUTER_REMARKS() {
		return MINICOMPUTER_REMARKS;
	}
	public void setMINICOMPUTER_REMARKS(String mINICOMPUTER_REMARKS) {
		MINICOMPUTER_REMARKS = mINICOMPUTER_REMARKS;
	}
	public Integer getMINICOMPUTER_WORKORDER_ID() {
		return MINICOMPUTER_WORKORDER_ID;
	}
	public void setMINICOMPUTER_WORKORDER_ID(Integer mINICOMPUTER_WORKORDER_ID) {
		MINICOMPUTER_WORKORDER_ID = mINICOMPUTER_WORKORDER_ID;
	}
	public Integer getWORKORDER_ID() {
		return WORKORDER_ID;
	}
	public void setWORKORDER_ID(Integer wORKORDER_ID) {
		WORKORDER_ID = wORKORDER_ID;
	}
	public String getOPERATOR() {
		return OPERATOR;
	}
	public void setOPERATOR(String oPERATOR) {
		OPERATOR = oPERATOR;
	}
	public String getASSIGNER() {
		return ASSIGNER;
	}
	public void setASSIGNER(String aSSIGNER) {
		ASSIGNER = aSSIGNER;
	}
	public String getRESPONSE_TIME() {
		return RESPONSE_TIME;
	}
	public void setRESPONSE_TIME(String rESPONSE_TIME) {
		RESPONSE_TIME = rESPONSE_TIME;
	}
	public String getCOMPLETION_TIME() {
		return COMPLETION_TIME;
	}
	public void setCOMPLETION_TIME(String cOMPLETION_TIME) {
		COMPLETION_TIME = cOMPLETION_TIME;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getPROGRESS() {
		return PROGRESS;
	}
	public void setPROGRESS(String pROGRESS) {
		PROGRESS = pROGRESS;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
    
	
	//找出执行人为当前用户的未配单的工单
	 public static int getNumForManager(){
		 List<TWorkorder> workorderList = TWorkorder.find("STATUS='0' or STATUS IS NUll or STATUS='' or STATUS = '1'").fetch();
		 int count = workorderList.size();
	     return count;
	 }
	 //找出执行人为当前用户的未执行的工单
	 public static int getNumForOperator(String operator){
		 List<TWorkorder> workorderList = TWorkorder.find("OPERATOR=? and STATUS='3' ", operator).fetch();
		 int count = workorderList.size();
		 return count;
	 }
	 
//	 public static int getNumByProjectName(String PROJECT_NAME){
//		 List<TWorkorder> workorderList = TWorkorder.find("byPROJECT_NAME",PROJECT_NAME).fetch();
//		 int count = workorderList.size();
//	     return count;
//	 }
	 
	 public static List<TWorkorder> getWorkOrderListByProName(String PROJECT_NAME){
		 List<TWorkorder> workorderList = TWorkorder.find("byPROJECT_NAME",PROJECT_NAME).fetch();
		 if(workorderList==null){
			 workorderList= new ArrayList<TWorkorder>();
		 }
	     return workorderList;
	 }
	 //根据PROJECTName查询处理中的工单数量
	 public static int getNumByProjectNameAndStatus(String PROJECT_NAME){
		 List<TWorkorder> workorderList = TWorkorder.find("PROJECT_NAME=? and STATUS='4'",PROJECT_NAME).fetch();
		 int count = 0;
		 if(workorderList!=null){
			 count = workorderList.size();
		 }
		 return count;
	 }
	 public static TWorkorder getWorkOrderByEleId(String elementId){
		 TWorkorder workorder = TWorkorder.find("byELEMENT_ID",elementId).first();
	     return workorder;
	 }
	 
}
