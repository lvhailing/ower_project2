package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class BindBankCard implements IMouldType {
	private String remoteValidateCode;
	private String openBankId;
	private String usrCustId;
	private String openAcctId;
	private String userName;
	private String token;

	public BindBankCard(String remoteValidateCode, String openBankId, String usrCustId, String openAcctId, String userName, String token) {
		this.remoteValidateCode = remoteValidateCode;
		this.openBankId = openBankId;
		this.usrCustId = usrCustId;
		this.openAcctId = openAcctId;
		this.userName = userName;
		this.token = token;
	}

	public String getRemoteValidateCode() {
		return remoteValidateCode;
	}

	public void setRemoteValidateCode(String remoteValidateCode) {
		this.remoteValidateCode = remoteValidateCode;
	}

	public String getOpenBankId() {
		return openBankId;
	}

	public void setOpenBankId(String openBankId) {
		this.openBankId = openBankId;
	}

	public String getUsrCustId() {
		return usrCustId;
	}

	public void setUsrCustId(String usrCustId) {
		this.usrCustId = usrCustId;
	}

	public String getOpenAcctId() {
		return openAcctId;
	}

	public void setOpenAcctId(String openAcctId) {
		this.openAcctId = openAcctId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
