package models.rest.createVM.step2.res;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AssetAttribute {

	@JsonProperty("ASSETATTRIBUTEID")
	private Long ASSETATTRIBUTEID;
	
	@JsonProperty("DATATYPE")
	private String DATATYPE;
	
	@JsonProperty("DESCRIPTION")
    private String DESCRIPTION;
    
	@JsonProperty("DOMAINID")
    private String DOMAINID;
    
	@JsonProperty("MEASUREUNITID")
    private String MEASUREUNITID;//单位?
    
	public Long getASSETATTRIBUTEID() {
		return ASSETATTRIBUTEID;
	}
	public void setASSETATTRIBUTEID(Long aSSETATTRIBUTEID) {
		ASSETATTRIBUTEID = aSSETATTRIBUTEID;
	}
	public String getDATATYPE() {
		return DATATYPE;
	}
	public void setDATATYPE(String dATATYPE) {
		DATATYPE = dATATYPE;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	public String getDOMAINID() {
		return DOMAINID;
	}
	public void setDOMAINID(String dOMAINID) {
		DOMAINID = dOMAINID;
	}
	public String getMEASUREUNITID() {
		return MEASUREUNITID;
	}
	public void setMEASUREUNITID(String mEASUREUNITID) {
		MEASUREUNITID = mEASUREUNITID;
	}
    
}
