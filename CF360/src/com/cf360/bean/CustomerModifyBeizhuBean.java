package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class CustomerModifyBeizhuBean implements IMouldType {
	private String customerId;
	private String remark;
	private String token;

	public CustomerModifyBeizhuBean(String customerId, String remark, String token) {
		this.customerId = customerId;
		this.remark = remark;
		this.token = token;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
