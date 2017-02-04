package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultAppointContentBean implements IMouldType {
	
	private String amount;
	private String appointmentId;
	private String productName;
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
}
