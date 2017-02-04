package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class SignUpOneBean implements IMouldType {

	private String mobile;
	private String validateCode;

	public SignUpOneBean() {
	}

	public SignUpOneBean(String mobile, String validateCode) {
		setMobile(mobile);
		setValidateCode(validateCode);
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

}
