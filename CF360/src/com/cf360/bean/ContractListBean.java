package com.cf360.bean;

import com.cf360.mould.types.IMouldType;


public class ContractListBean implements IMouldType {
	private String page;
	private String token;

	public ContractListBean(String page, String token) {
		this.page = page;
		this.token = token;
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
