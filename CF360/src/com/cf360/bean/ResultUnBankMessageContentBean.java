package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultUnBankMessageContentBean implements IMouldType {
	private String flag;
	private String message;
	private String resultCode;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	
	
}
