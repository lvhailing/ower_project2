package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class SendSMSForApplyToubaodanBean implements IMouldType {
	private String busiType;
	private String policyId;
	private String productName;
	private String token;
	public SendSMSForApplyToubaodanBean(String busiType,
			String policyId, String productName,String token) {
		setBusiType(busiType);
		setPolicyId(policyId);
		setProductName(productName);
		setToken(token);
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
