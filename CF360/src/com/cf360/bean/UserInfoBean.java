package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class UserInfoBean implements IMouldType {
	private String userId;
	private String productId;

	public UserInfoBean(String userId, String productId) {
		setProductId(productId);
		setUserId(userId);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
