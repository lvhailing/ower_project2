package com.cf360.bean;

import com.cf360.mould.types.IMouldType;


public class SignContractBean implements IMouldType {
	private String contractId;
	private String token;

	public SignContractBean(String contractId, String token) {
		this.contractId = contractId;
		this.token = token;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
