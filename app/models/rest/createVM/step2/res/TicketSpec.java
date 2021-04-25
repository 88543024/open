package models.rest.createVM.step2.res;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketSpec {
	@JsonProperty("ASSETATTRID")
	private String ASSETATTRID;
	
	@JsonProperty("CHANGEBY")
	private String CHANGEBY;
	
	@JsonProperty("CHANGEDATE")
	private String CHANGEDATE;
	
	@JsonProperty("CLASSSPECID")
	private Long CLASSSPECID;
	
	@JsonProperty("CLASSSTRUCTUREID")
	private String CLASSSTRUCTUREID;
	
	@JsonProperty("DISPLAYSEQUENCE")
	private Integer DISPLAYSEQUENCE;
	
	@JsonProperty("MANDATORY")
	private Boolean MANDATORY;
	
	@JsonProperty("MEASUREUNITID")
	private String MEASUREUNITID;
	
	@JsonProperty("REFOBJECTID")
	private Long REFOBJECTID;
	
	@JsonProperty("REFOBJECTNAME")
	private String REFOBJECTNAME;
	
	@JsonProperty("TICKETSPECID")
	private Long TICKETSPECID;
    
    @JsonProperty("ASSETATTRIBUTE")
    private List<AssetAttribute> assetAttributeList = new ArrayList<AssetAttribute>();
    
    
	public String getASSETATTRID() {
		return ASSETATTRID;
	}
	public void setASSETATTRID(String aSSETATTRID) {
		ASSETATTRID = aSSETATTRID;
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
	public Long getCLASSSPECID() {
		return CLASSSPECID;
	}
	public void setCLASSSPECID(Long cLASSSPECID) {
		CLASSSPECID = cLASSSPECID;
	}
	public String getCLASSSTRUCTUREID() {
		return CLASSSTRUCTUREID;
	}
	public void setCLASSSTRUCTUREID(String cLASSSTRUCTUREID) {
		CLASSSTRUCTUREID = cLASSSTRUCTUREID;
	}
	public Integer getDISPLAYSEQUENCE() {
		return DISPLAYSEQUENCE;
	}
	public void setDISPLAYSEQUENCE(Integer dISPLAYSEQUENCE) {
		DISPLAYSEQUENCE = dISPLAYSEQUENCE;
	}
	public Boolean getMANDATORY() {
		return MANDATORY;
	}
	public void setMANDATORY(Boolean mANDATORY) {
		MANDATORY = mANDATORY;
	}
	public Long getREFOBJECTID() {
		return REFOBJECTID;
	}
	public void setREFOBJECTID(Long rEFOBJECTID) {
		REFOBJECTID = rEFOBJECTID;
	}
	public String getREFOBJECTNAME() {
		return REFOBJECTNAME;
	}
	public void setREFOBJECTNAME(String rEFOBJECTNAME) {
		REFOBJECTNAME = rEFOBJECTNAME;
	}
	public Long getTICKETSPECID() {
		return TICKETSPECID;
	}
	public void setTICKETSPECID(Long tICKETSPECID) {
		TICKETSPECID = tICKETSPECID;
	}
	public List<AssetAttribute> getAssetAttributeList() {
		return assetAttributeList;
	}
	public void setAssetAttributeList(List<AssetAttribute> assetAttributeList) {
		this.assetAttributeList = assetAttributeList;
	}
	public String getMEASUREUNITID() {
		return MEASUREUNITID;
	}
	public void setMEASUREUNITID(String mEASUREUNITID) {
		MEASUREUNITID = mEASUREUNITID;
	}

    
}
