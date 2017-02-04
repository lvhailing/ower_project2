package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultFindPWDbyPhoneContent implements IMouldType {

	private String flag;
	private String message;
	
	public ResultFindPWDbyPhoneContent(String flag, String message) {
		super();
		this.flag = flag;
		this.message = message;
	}
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

	
}