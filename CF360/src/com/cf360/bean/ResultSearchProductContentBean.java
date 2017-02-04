package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultSearchProductContentBean implements IMouldType {
	
	private MouldList<SearchProductContentBean> productList;
	private String productName;
	private String searchRemark;
	private String auditStatus;
	
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public MouldList<SearchProductContentBean> getProductList() {
		return productList;
	}
	public void setProductList(MouldList<SearchProductContentBean> productList) {
		this.productList = productList;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSearchRemark() {
		return searchRemark;
	}
	public void setSearchRemark(String searchRemark) {
		this.searchRemark = searchRemark;
	}
	
	
}
