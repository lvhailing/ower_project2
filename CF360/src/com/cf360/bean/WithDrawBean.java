package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class WithDrawBean implements IMouldType {
	private String userId;
	private String appType;
	private String token;

	public WithDrawBean(String userId, String appType, String token) {
		this.userId = userId;
		this.appType = appType;
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
