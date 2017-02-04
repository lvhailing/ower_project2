package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class InfoDetailDataBean implements IMouldType {
	private String id;
	private String ptype;
	private String token;

	public InfoDetailDataBean(String id, String ptype, String token) {
		this.id = id;
		this.ptype = ptype;
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
