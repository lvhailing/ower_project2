package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ChoseMyBankBean implements IMouldType {
	
	private String bankName;
	private int banklogo;
	private boolean state;
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public int getBanklogo() {
		return banklogo;
	}
	public void setBanklogo(int banklogo) {
		this.banklogo = banklogo;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	
	
	
}
