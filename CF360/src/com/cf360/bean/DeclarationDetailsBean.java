package com.cf360.bean;

import com.cf360.mould.types.IMouldType;


public class DeclarationDetailsBean implements IMouldType {
	private String id;
	private String token;

	public DeclarationDetailsBean(String id, String token) {
		this.id = id;
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
