package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class MessageListBean implements IMouldType {
	private String pageNo;
	private String token;

	public MessageListBean(String pageNo, String token) {
		this.pageNo = pageNo;
		this.token = token;
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
