package com.cf360.bean;

import com.cf360.mould.types.IMouldType;


public class FocusBean implements IMouldType {
	private String category;
	private String pageNo;
	private String token;

	public FocusBean(String category, String pageNo, String token) {
		this.category = category;
		this.pageNo = pageNo;
		this.token = token;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
