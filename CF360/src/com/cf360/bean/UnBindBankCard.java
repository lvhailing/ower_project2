package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class UnBindBankCard implements IMouldType {
	private String remoteValidateCode;
	private String bankId;
	private String token;

	public UnBindBankCard(String remoteValidateCode, String bankId, String token) {
		this.remoteValidateCode = remoteValidateCode;
		this.bankId = bankId;
		this.token = token;
	}

	public String getRemoteValidateCode() {
		return remoteValidateCode;
	}

	public void setRemoteValidateCode(String remoteValidateCode) {
		this.remoteValidateCode = remoteValidateCode;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
