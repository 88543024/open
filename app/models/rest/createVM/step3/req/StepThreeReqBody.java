package models.rest.createVM.step3.req;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建虚拟机第三步骤所需要的参数
 * @author wzj
 * 
 */

public class StepThreeReqBody {

	private String PMSCCRID;
	private String SOURCE;
	private String TICKETID;
	private String ticketUid;//第三步的url中用
	private List<TicAttr> attrList = new ArrayList<TicAttr>();
	
	public String getPMSCCRID() {
		return PMSCCRID;
	}
	public void setPMSCCRID(String pMSCCRID) {
		PMSCCRID = pMSCCRID;
	}
	public String getSOURCE() {
		return SOURCE;
	}
	public void setSOURCE(String sOURCE) {
		SOURCE = sOURCE;
	}
	public String getTICKETID() {
		return TICKETID;
	}
	public void setTICKETID(String tICKETID) {
		TICKETID = tICKETID;
	}
	public List<TicAttr> getAttrList() {
		return attrList;
	}
	public void setAttrList(List<TicAttr> attrList) {
		this.attrList = attrList;
	}
	public String getTicketUid() {
		return ticketUid;
	}
	public void setTicketUid(String ticketUid) {
		this.ticketUid = ticketUid;
	}
	
}
