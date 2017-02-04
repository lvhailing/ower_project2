package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class InsuranceBean implements IMouldType {
	private String insuranceAgentCode;
	private String idNo;
	private String insuranceAgentType;
	private String token;

	public InsuranceBean(String insuranceAgentCode, String idNo, String insuranceAgentType, String token) {
		this.insuranceAgentCode = insuranceAgentCode;
		this.idNo = idNo;
		this.insuranceAgentType = insuranceAgentType;
		this.token = token;
	}

	public String getInsuranceAgentCode() {
		return insuranceAgentCode;
	}

	public void setInsuranceAgentCode(String insuranceAgentCode) {
		this.insuranceAgentCode = insuranceAgentCode;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getInsuranceAgentType() {
		return insuranceAgentType;
	}

	public void setInsuranceAgentType(String insuranceAgentType) {
		this.insuranceAgentType = insuranceAgentType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
