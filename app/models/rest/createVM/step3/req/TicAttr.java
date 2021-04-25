package models.rest.createVM.step3.req;

public class TicAttr {

    private String ASSETATTRID;
    private String valType;//可能是NUMVALUE,ALNVALUE,TABLEVALUE
    private String VAL;
    private String REFOBJECTID;
    private String SECTION;
    private String TICKETSPECID;
    
	public String getASSETATTRID() {
		return ASSETATTRID;
	}
	public void setASSETATTRID(String aSSETATTRID) {
		ASSETATTRID = aSSETATTRID;
	}
	public String getVAL() {
		return VAL;
	}
	public void setVAL(String vAL) {
		VAL = vAL;
	}
	public String getREFOBJECTID() {
		return REFOBJECTID;
	}
	public void setREFOBJECTID(String rEFOBJECTID) {
		REFOBJECTID = rEFOBJECTID;
	}
	public String getSECTION() {
		return SECTION;
	}
	public void setSECTION(String sECTION) {
		SECTION = sECTION;
	}
	public String getTICKETSPECID() {
		return TICKETSPECID;
	}
	public void setTICKETSPECID(String tICKETSPECID) {
		TICKETSPECID = tICKETSPECID;
	}
	public String getValType() {
		return valType;
	}
	public void setValType(String valType) {
		this.valType = valType;
	}
    
}
