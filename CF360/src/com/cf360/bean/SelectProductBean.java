package com.cf360.bean;

import com.cf360.mould.types.IMouldType;


public class SelectProductBean implements IMouldType {
	private String productName;
	private String category;
	private String selectType;
	private String token;

	public SelectProductBean(String productName, String category, String selectType, String token) {
		this.productName = productName;
		this.category = category;
		this.selectType = selectType;
		this.token = token;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
