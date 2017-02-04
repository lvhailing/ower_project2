package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ChangePWDBean implements IMouldType {
	private String mobile;
	private String oldPassword;
	private String password;
	private String rePassword;
	private String token;
	public ChangePWDBean(String mobile, String oldPassword, String password,
			String rePassword,String token) {
		super();
		this.mobile = mobile;
		this.oldPassword = oldPassword;
		this.password = password;
		this.rePassword = rePassword;
		this.token = token;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
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


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
