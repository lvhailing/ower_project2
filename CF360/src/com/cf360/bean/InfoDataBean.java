package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class InfoDataBean implements IMouldType {
	private String CATEGORY;
	private String pageNo;
	private String token;

	public InfoDataBean(String CATEGORY, String pageNo, String token) {
		this.CATEGORY = CATEGORY;
		this.pageNo = pageNo;
		this.token = token;
	}

	public String getCATEGORY() {
		return CATEGORY;
	}

	public void setCATEGORY(String CATEGORY) {
		this.CATEGORY = CATEGORY;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
