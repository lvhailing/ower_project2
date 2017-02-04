package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ChangePhoneBean implements IMouldType {
	private String userId;
	private String validateCode;
	private String token;
	public ChangePhoneBean(String userId, String validateCode,String token) {
		setUserId(userId);
		setValidateCode(validateCode);
		setToken(token);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}