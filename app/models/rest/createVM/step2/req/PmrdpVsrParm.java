package models.rest.createVM.step2.req;

public class PmrdpVsrParm {

	private String SRCATTRNAME;//源属性名称
	private String SRCATTRVAL;//源属性值
	private String SRCNAME;//源名称
	private String SRCTOKEN;//源标记
	private String SRCTYPE;//子类型(可以是 VST 或者是 SRT)
	
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
	public String getSRCNAME() {
		return SRCNAME;
	}
	public void setSRCNAME(String sRCNAME) {
		SRCNAME = sRCNAME;
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
    
}
