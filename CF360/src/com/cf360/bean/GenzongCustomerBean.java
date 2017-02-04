package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class GenzongCustomerBean implements IMouldType {
	 private String customerId;
	 private String status;
	 private String timeType;
	 private String startTime;
	 private String page;
	 private String token;

	public GenzongCustomerBean(String customerId, String status, String timeType, String startTime, String page, String token) {
		this.customerId = customerId;
		this.status = status;
		this.timeType = timeType;
		this.startTime = startTime;
		this.page = page;
		this.token = token;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
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
