package models.rest.createVM.step2.req;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建虚拟机第二步骤所需要的参数
 * @author wzj
 * 
 */
public class StepTwoReqBody {
	
	private String CREATEBY;//服务创建人
	private String REPORTEDBY;//
	private String CHANGEBY;//
	private String CLASS;//
	private String PMSCCRID;//来自第一次请求的 PMSCCRNUM
	private String DESCRIPTION;//摘要描述
	private String PMSCITEMNUM;//
//	private String CLASSIFICATIONID;//offering的分类标识
	private String CLASSSTRUCTUREID;
	private String COMMODITYGROUP;//标识提供服务的组
	private String COMMODITY;//提供或购买的服务
	private String TARGETSTART;
	private String TARGETFINISH;
	private String ITEMNUM;//offering编号
	private String ITEMSETID;//
	private String ORDERUNIT;//
	private String QTY;//
	private String PLUSPCUSTOMER;//
	
	private List<PmrdpVsrParm> paramList = new ArrayList<PmrdpVsrParm>();//变量服务请求参数映射

	public String getCREATEBY() {
		return CREATEBY;
	}

	public void setCREATEBY(String cREATEBY) {
		CREATEBY = cREATEBY;
	}

	public String getREPORTEDBY() {
		return REPORTEDBY;
	}

	public void setREPORTEDBY(String rEPORTEDBY) {
		REPORTEDBY = rEPORTEDBY;
	}

	public String getCHANGEBY() {
		return CHANGEBY;
	}

	public void setCHANGEBY(String cHANGEBY) {
		CHANGEBY = cHANGEBY;
	}

	public String getCLASS() {
		return CLASS;
	}

	public void setCLASS(String cLASS) {
		CLASS = cLASS;
	}

	public String getPMSCCRID() {
		return PMSCCRID;
	}

	public void setPMSCCRID(String pMSCCRID) {
		PMSCCRID = pMSCCRID;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getPMSCITEMNUM() {
		return PMSCITEMNUM;
	}

	public void setPMSCITEMNUM(String pMSCITEMNUM) {
		PMSCITEMNUM = pMSCITEMNUM;
	}

	public String getCLASSSTRUCTUREID() {
		return CLASSSTRUCTUREID;
	}

	public void setCLASSSTRUCTUREID(String cLASSSTRUCTUREID) {
		CLASSSTRUCTUREID = cLASSSTRUCTUREID;
	}

	public String getCOMMODITYGROUP() {
		return COMMODITYGROUP;
	}

	public void setCOMMODITYGROUP(String cOMMODITYGROUP) {
		COMMODITYGROUP = cOMMODITYGROUP;
	}

	public String getCOMMODITY() {
		return COMMODITY;
	}

	public void setCOMMODITY(String cOMMODITY) {
		COMMODITY = cOMMODITY;
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

	public String getITEMNUM() {
		return ITEMNUM;
	}

	public void setITEMNUM(String iTEMNUM) {
		ITEMNUM = iTEMNUM;
	}

	public String getITEMSETID() {
		return ITEMSETID;
	}

	public void setITEMSETID(String iTEMSETID) {
		ITEMSETID = iTEMSETID;
	}

	public String getORDERUNIT() {
		return ORDERUNIT;
	}

	public void setORDERUNIT(String oRDERUNIT) {
		ORDERUNIT = oRDERUNIT;
	}

	public String getQTY() {
		return QTY;
	}

	public void setQTY(String qTY) {
		QTY = qTY;
	}

	public String getPLUSPCUSTOMER() {
		return PLUSPCUSTOMER;
	}

	public void setPLUSPCUSTOMER(String pLUSPCUSTOMER) {
		PLUSPCUSTOMER = pLUSPCUSTOMER;
	}

	public List<PmrdpVsrParm> getParamList() {
		return paramList;
	}

	public void setParamList(List<PmrdpVsrParm> paramList) {
		this.paramList = paramList;
	}

	
}
