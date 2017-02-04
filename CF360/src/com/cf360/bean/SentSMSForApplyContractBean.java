package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class SentSMSForApplyContractBean implements IMouldType {
	private String mobile;
	private String busiType;
	private String contractId;
	private String productName;
	
	public SentSMSForApplyContractBean(String mobile, String busiType,
			String contractId, String productName) {
		setMobile(mobile);
		setBusiType(busiType);
		setContractId(contractId);
		setProductName(productName);
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
}
