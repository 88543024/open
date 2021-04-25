package models.rest.createVM.step2.res;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SrmSrCreateSet {
	
	@JsonProperty("SR")
	private Sr sr = new Sr();

	public Sr getSr() {
		return sr;
	}

	public void setSr(Sr sr) {
		this.sr = sr;
	}
    
}
