package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class XintuoEmailBean implements IMouldType{
	private String email;
	private String productId;
	private String token;

	public XintuoEmailBean(String email, String productId, String token) {
		this.email = email;
		this.productId = productId;
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
