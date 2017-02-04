package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class SearchProductBean implements IMouldType {
	private String productName;
	private String pageNo;
	public SearchProductBean(String productName, String pageNo) {
		super();
		this.productName = productName;
		this.pageNo = pageNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	
	
	
}
