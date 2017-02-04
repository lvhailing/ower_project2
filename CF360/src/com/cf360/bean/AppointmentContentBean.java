package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class AppointmentContentBean implements IMouldType {
	private String ageCoverage;
	private String amount;
	private String customerName;
	private String customerPhone;
	private String deputyInsuranceName;
	private String payLimitTime;
	private String productCategory;
	private String productId;
	private String productName;
	private String rebateProportion;
	private String remark;
	public AppointmentContentBean(
	String ageCoverage,String amount,String customerName
	,String customerPhone,String deputyInsuranceName
	,String payLimitTime,String productCategory
	,String productId, String productName
	,String rebateProportion,String remark) {
		super();
		this.ageCoverage=ageCoverage;
		this.amount=amount;
		this.customerName=customerName;
		this.customerPhone=customerPhone;
		this.deputyInsuranceName=deputyInsuranceName;
		this.payLimitTime=payLimitTime;
		this.productCategory=productCategory;
		this.productId=productId;
		this.productName=productName;
		this.rebateProportion=rebateProportion;
		this.remark=remark;
	}
	public String getAgeCoverage() {
		return ageCoverage;
	}
	public void setAgeCoverage(String ageCoverage) {
		this.ageCoverage = ageCoverage;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getDeputyInsuranceName() {
		return deputyInsuranceName;
	}
	public void setDeputyInsuranceName(String deputyInsuranceName) {
		this.deputyInsuranceName = deputyInsuranceName;
	}
	public String getPayLimitTime() {
		return payLimitTime;
	}
	public void setPayLimitTime(String payLimitTime) {
		this.payLimitTime = payLimitTime;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRebateProportion() {
		return rebateProportion;
	}
	public void setRebateProportion(String rebateProportion) {
		this.rebateProportion = rebateProportion;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


}
