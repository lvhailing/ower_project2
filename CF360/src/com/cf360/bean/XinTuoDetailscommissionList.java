package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class XinTuoDetailscommissionList implements IMouldType {

	private String amount;
	private String  annualRate;
	private String  commission;
	private String id;
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getAnnualRate() {
		return annualRate;
	}
	public void setAnnualRate(String annualRate) {
		this.annualRate = annualRate;
	}
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
