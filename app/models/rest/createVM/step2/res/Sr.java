package models.rest.createVM.step2.res;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sr {
	@JsonProperty("AFFECTEDDATE")
	private String AFFECTEDDATE;
	
//  public String AFFECTEDMAIL;
	@JsonProperty("AFFECTEDPERSON")
	private String AFFECTEDPERSON;
	
	@JsonProperty("CHANGEBY")
	private String CHANGEBY;
	
	@JsonProperty("CHANGEDATE")
	private String CHANGEDATE;
	
	@JsonProperty("CLASS")
	private String CLASS;
	
	@JsonProperty("CLASSIFICATIONID")
	private String CLASSIFICATIONID;
	
	@JsonProperty("CLASSSTRUCTUREID")
	private String CLASSSTRUCTUREID;
	
	@JsonProperty("COMMODITY")
	private String COMMODITY;
	
	@JsonProperty("COMMODITYGROUP")
	private String COMMODITYGROUP;
	
	@JsonProperty("CREATEDBY")
	private String CREATEDBY;
	
	@JsonProperty("CREATIONDATE")
	private String CREATIONDATE;
	
	@JsonProperty("DESCRIPTION")
	private String DESCRIPTION;
	
	@JsonProperty("HASSOLUTION")
	private Boolean HASSOLUTION;
	
	@JsonProperty("ISKNOWNERROR")
	private Boolean ISKNOWNERROR;
	
	@JsonProperty("PLUSPCUSTOMER")
	private String PLUSPCUSTOMER;
	
	@JsonProperty("PMSCCRID")
	private String PMSCCRID;
	
	@JsonProperty("PMSCITEMNUM")
	private String PMSCITEMNUM;
	
	@JsonProperty("REPORTDATE")
	private String REPORTDATE;
	
	@JsonProperty("REPORTEDBY")
	private String REPORTEDBY;
	
//    public String REPORTEDEMAIL;
	@JsonProperty("SELFSERVSOLACCESS")
	private Boolean SELFSERVSOLACCESS;
	
	@JsonProperty("STATUS")
	private String STATUS;
	
	@JsonProperty("TARGETSTART")
	private String TARGETSTART;//
	
	@JsonProperty("TARGETFINISH")
	private String TARGETFINISH;//
	
	@JsonProperty("TICKETID")
	private String TICKETID;
	
	@JsonProperty("TICKETUID")
	private Long TICKETUID;
	
    @JsonProperty("TICKETSPEC")
    private List<TicketSpec> ticSpecList = new ArrayList<TicketSpec>();
	
    @JsonProperty("PMRDPVSRPARM")
    private List<PmrdpVsr> pmrdpVsrList = new ArrayList<PmrdpVsr>();
    
    public String getAFFECTEDDATE() {
		return AFFECTEDDATE;
	}
	public void setAFFECTEDDATE(String aFFECTEDDATE) {
		AFFECTEDDATE = aFFECTEDDATE;
	}
	public String getAFFECTEDPERSON() {
		return AFFECTEDPERSON;
	}
	public void setAFFECTEDPERSON(String aFFECTEDPERSON) {
		AFFECTEDPERSON = aFFECTEDPERSON;
	}
	public String getCHANGEBY() {
		return CHANGEBY;
	}
	public void setCHANGEBY(String cHANGEBY) {
		CHANGEBY = cHANGEBY;
	}
	public String getCHANGEDATE() {
		return CHANGEDATE;
	}
	public void setCHANGEDATE(String cHANGEDATE) {
		CHANGEDATE = cHANGEDATE;
	}
	public String getCLASS() {
		return CLASS;
	}
	public void setCLASS(String cLASS) {
		CLASS = cLASS;
	}
	public String getCLASSIFICATIONID() {
		return CLASSIFICATIONID;
	}
	public void setCLASSIFICATIONID(String cLASSIFICATIONID) {
		CLASSIFICATIONID = cLASSIFICATIONID;
	}
	public String getCLASSSTRUCTUREID() {
		return CLASSSTRUCTUREID;
	}
	public void setCLASSSTRUCTUREID(String cLASSSTRUCTUREID) {
		CLASSSTRUCTUREID = cLASSSTRUCTUREID;
	}
	public String getCOMMODITY() {
		return COMMODITY;
	}
	public void setCOMMODITY(String cOMMODITY) {
		COMMODITY = cOMMODITY;
	}
	public String getCOMMODITYGROUP() {
		return COMMODITYGROUP;
	}
	public void setCOMMODITYGROUP(String cOMMODITYGROUP) {
		COMMODITYGROUP = cOMMODITYGROUP;
	}
	public String getCREATIONDATE() {
		return CREATIONDATE;
	}
	public void setCREATIONDATE(String cREATIONDATE) {
		CREATIONDATE = cREATIONDATE;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	public Boolean getHASSOLUTION() {
		return HASSOLUTION;
	}
	public void setHASSOLUTION(Boolean hASSOLUTION) {
		HASSOLUTION = hASSOLUTION;
	}
	public Boolean getISKNOWNERROR() {
		return ISKNOWNERROR;
	}
	public void setISKNOWNERROR(Boolean iSKNOWNERROR) {
		ISKNOWNERROR = iSKNOWNERROR;
	}
	public String getPLUSPCUSTOMER() {
		return PLUSPCUSTOMER;
	}
	public void setPLUSPCUSTOMER(String pLUSPCUSTOMER) {
		PLUSPCUSTOMER = pLUSPCUSTOMER;
	}
	public String getPMSCCRID() {
		return PMSCCRID;
	}
	public void setPMSCCRID(String pMSCCRID) {
		PMSCCRID = pMSCCRID;
	}
	public String getPMSCITEMNUM() {
		return PMSCITEMNUM;
	}
	public void setPMSCITEMNUM(String pMSCITEMNUM) {
		PMSCITEMNUM = pMSCITEMNUM;
	}
	public String getREPORTDATE() {
		return REPORTDATE;
	}
	public void setREPORTDATE(String rEPORTDATE) {
		REPORTDATE = rEPORTDATE;
	}
	public String getREPORTEDBY() {
		return REPORTEDBY;
	}
	public void setREPORTEDBY(String rEPORTEDBY) {
		REPORTEDBY = rEPORTEDBY;
	}
	public Boolean getSELFSERVSOLACCESS() {
		return SELFSERVSOLACCESS;
	}
	public void setSELFSERVSOLACCESS(Boolean sELFSERVSOLACCESS) {
		SELFSERVSOLACCESS = sELFSERVSOLACCESS;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getTICKETID() {
		return TICKETID;
	}
	public void setTICKETID(String tICKETID) {
		TICKETID = tICKETID;
	}
	public Long getTICKETUID() {
		return TICKETUID;
	}
	public List<TicketSpec> getTicSpecList() {
		return ticSpecList;
	}
	public void setTicSpecList(List<TicketSpec> ticSpecList) {
		this.ticSpecList = ticSpecList;
	}
	public void setTICKETUID(Long tICKETUID) {
		TICKETUID = tICKETUID;
	}
	public String getTARGETSTART() {
		return TARGETSTART;
	}
	public void setTARGETSTART(String tARGETSTART) {
		TARGETSTART = tARGETSTART;
	}
	public String getTARGETFINISH() {
		return TARGETFINISH;
	}
	public void setTARGETFINISH(String tARGETFINISH) {
		TARGETFINISH = tARGETFINISH;
	}
	public List<PmrdpVsr> getPmrdpVsrList() {
		return pmrdpVsrList;
	}
	public void setPmrdpVsrList(List<PmrdpVsr> pmrdpVsrList) {
		this.pmrdpVsrList = pmrdpVsrList;
	}
	public String getCREATEDBY() {
		return CREATEDBY;
	}
	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

}
