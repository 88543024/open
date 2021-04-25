package models.rest.createVM.step2.res;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PmrdpVsr {
	@JsonProperty("PMRDPVSRPARMID")
	private String PMRDPVSRPARMID;
	
	@JsonProperty("SRCATTRNAME")
	private String SRCATTRNAME;
	
	@JsonProperty("SRCATTRVAL")
	private String SRCATTRVAL;
	
	@JsonProperty("SRCTOKEN")
	private String SRCTOKEN;
	
	@JsonProperty("SRCTYPE")
	private String SRCTYPE;
	
	@JsonProperty("SRID")
	private String SRID;
	
	public String getPMRDPVSRPARMID() {
		return PMRDPVSRPARMID;
	}
	public void setPMRDPVSRPARMID(String pMRDPVSRPARMID) {
		PMRDPVSRPARMID = pMRDPVSRPARMID;
	}
	public String getSRCATTRNAME() {
		return SRCATTRNAME;
	}
	public void setSRCATTRNAME(String sRCATTRNAME) {
		SRCATTRNAME = sRCATTRNAME;
	}
	public String getSRCATTRVAL() {
		return SRCATTRVAL;
	}
	public void setSRCATTRVAL(String sRCATTRVAL) {
		SRCATTRVAL = sRCATTRVAL;
	}
	public String getSRCTOKEN() {
		return SRCTOKEN;
	}
	public void setSRCTOKEN(String sRCTOKEN) {
		SRCTOKEN = sRCTOKEN;
	}
	public String getSRCTYPE() {
		return SRCTYPE;
	}
	public void setSRCTYPE(String sRCTYPE) {
		SRCTYPE = sRCTYPE;
	}
	public String getSRID() {
		return SRID;
	}
	public void setSRID(String sRID) {
		SRID = sRID;
	}
	
}
