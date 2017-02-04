package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class FindPWDbyPhoneVerifyBean implements IMouldType {
	private String userName;
	private String validateCode;
	private String token;

	public FindPWDbyPhoneVerifyBean(String userName, String validateCode, String token) {
		this.userName = userName;
		this.validateCode = validateCode;
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
