package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class IsRegisterBean implements IMouldType {
	private String mobile;

	public IsRegisterBean(String mobile) {
		super();
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	
}
