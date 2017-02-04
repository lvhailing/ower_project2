package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class HisBaoDanBean implements IMouldType {
	private String idcard;
	private String page;
	private String token;

	public HisBaoDanBean(String idcard, String page, String token) {
		this.idcard = idcard;
		this.page = page;
		this.token = token;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
