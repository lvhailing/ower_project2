package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class BindPhoneBean implements IMouldType {
	private String userId;
	private String newMobile;
	private String validateCode;
	private String token;

	public BindPhoneBean(String userId, String newMobile, String validateCode, String token) {
		this.userId = userId;
		this.newMobile = newMobile;
		this.validateCode = validateCode;
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNewMobile() {
		return newMobile;
	}

	public void setNewMobile(String newMobile) {
		this.newMobile = newMobile;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
