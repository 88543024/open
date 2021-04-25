package controllers;

import utils.MailUtil;


public class MyRunner implements Runnable{
	
	private Integer ELEMENT_ID;
	private String PROJECT_NAME;
	private Long tWorkOrderId;

	@Override
	public void run() {
		System.out.println("getmail1111");
		//MailUtil.getMail(ELEMENT_ID,PROJECT_NAME,tWorkOrderId);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) { 
			return;
		}
	}

	public Integer getELEMENT_ID() {
		return ELEMENT_ID;
	}


	public void setELEMENT_ID(Integer eLEMENTID) {
		ELEMENT_ID = eLEMENTID;
	}


	public String getPROJECT_NAME() {
		return PROJECT_NAME;
	}


	public void setPROJECT_NAME(String pROJECTNAME) {
		PROJECT_NAME = pROJECTNAME;
	}


	public Long gettWorkOrderId() {
		return tWorkOrderId;
	}


	public void settWorkOrderId(Long tWorkOrderId) {
		this.tWorkOrderId = tWorkOrderId;
	}

}
