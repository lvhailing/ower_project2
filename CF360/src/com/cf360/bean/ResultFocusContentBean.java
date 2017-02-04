package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;


public class ResultFocusContentBean implements IMouldType {
	private String flag;
	private String message;
	private MouldList<ResultFocusContentListBean> productList;
	private String auditStatus;
	
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MouldList<ResultFocusContentListBean> getProductList() {
		return productList;
	}
	public void setProductList(MouldList<ResultFocusContentListBean> productList) {
		this.productList = productList;
	}


}
