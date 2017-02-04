package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultXinPopContentBean implements IMouldType{
	
	private Boolean flag;
	private String message;
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
