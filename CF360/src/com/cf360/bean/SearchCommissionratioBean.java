package com.cf360.bean;

import com.cf360.mould.types.IMouldType;


public class SearchCommissionratioBean implements IMouldType {
	private	String productId;
	private String deputyInsuranceName; 
	private String ageCoverage;
	private String payLimitTime;
	private String token;

	public SearchCommissionratioBean(String productId, String deputyInsuranceName, String ageCoverage, String payLimitTime, String token) {
		this.productId = productId;
		this.deputyInsuranceName = deputyInsuranceName;
		this.ageCoverage = ageCoverage;
		this.payLimitTime = payLimitTime;
		this.token = token;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDeputyInsuranceName() {
		return deputyInsuranceName;
	}

	public void setDeputyInsuranceName(String deputyInsuranceName) {
		this.deputyInsuranceName = deputyInsuranceName;
	}

	public String getAgeCoverage() {
		return ageCoverage;
	}

	public void setAgeCoverage(String ageCoverage) {
		this.ageCoverage = ageCoverage;
	}

	public String getPayLimitTime() {
		return payLimitTime;
	}

	public void setPayLimitTime(String payLimitTime) {
		this.payLimitTime = payLimitTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
