package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class SentSMSForAppointmentBean implements IMouldType {
	private String productName;
	private String amount;
	private String appoitmentId;
	private String busiType;
	public SentSMSForAppointmentBean(String productName, String amount,
			String appoitmentId, String busiType) {
		super();
		this.productName = productName;
		this.amount = amount;
		this.appoitmentId = appoitmentId;
		this.busiType = busiType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAppoitmentId() {
		return appoitmentId;
	}
	public void setAppoitmentId(String appoitmentId) {
		this.appoitmentId = appoitmentId;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
}
