package models.rest.createVM.step2.res;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SrmSrCreateResponseBody {
	@JsonProperty("rsStart")
	private Integer rsStart;
	
	@JsonProperty("rsCount")
	private Integer rsCount;
	
	@JsonProperty("rsTotal")
	private Integer rsTotal;
    
    @JsonProperty("SRM_SRCREATESet")
    private SrmSrCreateSet srmSet = new SrmSrCreateSet();
    
	public Integer getRsStart() {
		return rsStart;
	}
	public void setRsStart(Integer rsStart) {
		this.rsStart = rsStart;
	}
	public Integer getRsCount() {
		return rsCount;
	}
	public void setRsCount(Integer rsCount) {
		this.rsCount = rsCount;
	}
	public Integer getRsTotal() {
		return rsTotal;
	}
	public void setRsTotal(Integer rsTotal) {
		this.rsTotal = rsTotal;
	}
	public SrmSrCreateSet getSrmSet() {
		return srmSet;
	}
	public void setSrmSet(SrmSrCreateSet srmSet) {
		this.srmSet = srmSet;
	}
	
}
