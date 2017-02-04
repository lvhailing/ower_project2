package com.cf360.bean;

import com.cf360.mould.types.IMouldType;


public class PostTouabodanBean implements IMouldType {
	private String policyOrderId;
	private String expressFiles;
	private String expressName;
	private String expressCode;
	private	String expressIP;
	private String token;
	public PostTouabodanBean(String policyOrderId, String expressFiles,
			String expressName, String expressCode, String expressIP,String token) {
		setPolicyOrderId(policyOrderId);
		setExpressFiles(expressFiles);
		setExpressName(expressName);
		setExpressCode(expressCode);
		setExpressIP(expressIP);
		setToken(token);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPolicyOrderId() {
		return policyOrderId;
	}
	public void setPolicyOrderId(String policyOrderId) {
		this.policyOrderId = policyOrderId;
	}
	public String getExpressFiles() {
		return expressFiles;
	}
	public void setExpressFiles(String expressFiles) {
		this.expressFiles = expressFiles;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getExpressCode() {
		return expressCode;
	}
	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}
	public String getExpressIP() {
		return expressIP;
	}
	public void setExpressIP(String expressIP) {
		this.expressIP = expressIP;
	}
	
}
