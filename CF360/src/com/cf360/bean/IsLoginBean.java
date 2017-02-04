package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class IsLoginBean implements IMouldType {
	private String userId;
	private String token;

	public IsLoginBean(String userId, String token) {
		this.userId = userId;
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
