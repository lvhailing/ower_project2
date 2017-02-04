package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class FindPWDbyPhoneBean implements IMouldType {
	private String mobile;
	private String password; 
	private String rePassword;
	private String validateCode;
	private String token;

	public FindPWDbyPhoneBean(String mobile, String password, String rePassword, String validateCode, String token) {
		this.mobile = mobile;
		this.password = password;
		this.rePassword = rePassword;
		this.validateCode = validateCode;
		this.token = token;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
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
