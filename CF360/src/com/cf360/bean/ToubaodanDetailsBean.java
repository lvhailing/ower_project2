package com.cf360.bean;

import com.cf360.mould.types.IMouldType;


public class ToubaodanDetailsBean implements IMouldType {
	private String policyOrderId;
	private String status;
	private String token;

	public ToubaodanDetailsBean(String policyOrderId, String status, String token) {
		this.policyOrderId = policyOrderId;
		this.status = status;
		this.token = token;
	}

	public String getPolicyOrderId() {
		return policyOrderId;
	}

	public void setPolicyOrderId(String policyOrderId) {
		this.policyOrderId = policyOrderId;
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
