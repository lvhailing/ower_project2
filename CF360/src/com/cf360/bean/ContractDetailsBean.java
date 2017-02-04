package com.cf360.bean;

import com.cf360.mould.types.IMouldType;


public class ContractDetailsBean implements IMouldType {
	private String contractId;
	private String status;
	private String type;
	private String token;
	public ContractDetailsBean(String contractId, String status, String type,String token) {
		setContractId(contractId);
		setStatus(status);
		setType(type);
		setToken(token);
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
