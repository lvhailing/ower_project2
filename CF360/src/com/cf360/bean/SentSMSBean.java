package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class SentSMSBean implements IMouldType {
	private String mobile;
	private String busiType;
	private String mathRandom;
	
	public SentSMSBean(String mobile, String busiType,String mathRandom) {
		super();
		this.mobile = mobile;
		this.busiType = busiType;
		this.mathRandom=mathRandom;
	}
	public String getUserMobile() {
		return mobile;
	}
	public void setUserMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getMathRandom() {
		return mathRandom;
	}
	public void setMathRandom(String mathRandom) {
		this.mathRandom = mathRandom;
	}
	
}
