package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class SendSMSForApplyDeclarationBean implements IMouldType {
	private String busiType;
	private String orderId;
	private String productName;
	private String token;
	public SendSMSForApplyDeclarationBean(String busiType,
			String orderId, String productName,String token) {
		setBusiType(busiType);
		setProductName(productName);
		setOrderId(orderId);
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
