package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class AdviceBean implements IMouldType {
	private String content;
	private String mobile;
	private String email;
	private String token;

	public AdviceBean(String content, String mobile, String email, String token) {
		this.content = content;
		this.mobile = mobile;
		this.email = email;
		this.token = token;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
