package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class MyBankItemBean implements IMouldType {
	private String name;
	private String bankName;
	private String bankNumber;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	
}
