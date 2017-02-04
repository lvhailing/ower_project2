package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultIsLoginContentBean implements IMouldType {
	
	private String flag;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
