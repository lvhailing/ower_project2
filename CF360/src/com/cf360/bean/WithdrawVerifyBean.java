package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class WithdrawVerifyBean implements IMouldType {
	private String bankCardId;
	private String validCode;
	private String transAmount;
	private String token;

	public WithdrawVerifyBean(String bankCardId, String validCode, String transAmount, String token) {
		this.bankCardId = bankCardId;
		this.validCode = validCode;
		this.transAmount = transAmount;
		this.token = token;
	}

	public String getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
