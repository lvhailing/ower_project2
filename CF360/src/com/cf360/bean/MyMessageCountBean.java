package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class MyMessageCountBean implements IMouldType {
	private String mobile;
	private String token;

	public MyMessageCountBean(String mobile, String token) {
		this.mobile = mobile;
		this.token = token;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
