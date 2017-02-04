package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class PayAttentionToBean implements IMouldType{
	private String category;
	private String productId;
	private String token;

	public PayAttentionToBean(String category, String productId, String token) {
		this.category = category;
		this.productId = productId;
		this.token = token;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
