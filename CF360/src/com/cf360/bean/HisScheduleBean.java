package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class HisScheduleBean implements IMouldType {
	private String customerId;
	private String page;
	private String token;

	public HisScheduleBean(String customerId, String page, String token) {
		this.customerId = customerId;
		this.page = page;
		this.token = token;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
