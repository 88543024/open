package models.rest.createVM.step2.res;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StepTwoResBody {
	
	@JsonProperty("CreateSRM_SRCREATEResponse")
    private SrmSrCreateResponseBody createSrmSrCreateResponse = new SrmSrCreateResponseBody();

	public SrmSrCreateResponseBody getCreateSrmSrCreateResponse() {
		return createSrmSrCreateResponse;
	}

	public void setCreateSrmSrCreateResponse(
			SrmSrCreateResponseBody createSrmSrCreateResponse) {
		this.createSrmSrCreateResponse = createSrmSrCreateResponse;
	}

    
}
