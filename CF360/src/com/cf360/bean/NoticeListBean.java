package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class NoticeListBean implements IMouldType {
	private int page;
	private String token;

	public NoticeListBean(int page, String token) {
		this.page = page;
		this.token = token;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
