package com.cf360.bean;

import com.cf360.mould.types.IMouldType;


public class PostContractBean implements IMouldType {
	private String contractId;
	private String expressNameBack;
	private String expressCodeBack;
	private String expressUrlBack;
	private String token;
	public PostContractBean(String contractId,String expressNameBack,String expressCodeBack,String expressUrlBack,String token) {
		setContractId(contractId);
		setExpressNameBack(expressNameBack);
		setExpressCodeBack(expressCodeBack);
		setExpressUrlBack(expressUrlBack);
		setToken(token);
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getExpressNameBack() {
		return expressNameBack;
	}
	public void setExpressNameBack(String expressNameBack) {
		this.expressNameBack = expressNameBack;
	}
	public String getExpressCodeBack() {
		return expressCodeBack;
	}
	public void setExpressCodeBack(String expressCodeBack) {
		this.expressCodeBack = expressCodeBack;
	}
	public String getExpressUrlBack() {
		return expressUrlBack;
	}
	public void setExpressUrlBack(String expressUrlBack) {
		this.expressUrlBack = expressUrlBack;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
