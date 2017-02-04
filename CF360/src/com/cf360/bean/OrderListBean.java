package com.cf360.bean;

import com.cf360.mould.types.IMouldType;


public class OrderListBean implements IMouldType {
	private String category;
	private String page;
	private String status;
	private String token;

	public OrderListBean(String category, String page, String status, String token) {
		this.category = category;
		this.page = page;
		this.status = status;
		this.token = token;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
