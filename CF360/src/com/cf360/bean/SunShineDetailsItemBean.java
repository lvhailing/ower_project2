package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class SunShineDetailsItemBean implements IMouldType {

	private String id;
	private String type;
	private String token;

	public SunShineDetailsItemBean(String id, String type, String token) {
		this.id = id;
		this.type = type;
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
