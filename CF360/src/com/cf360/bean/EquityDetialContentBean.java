package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class EquityDetialContentBean implements IMouldType {
	private String attentionStatus;
	private String auditStatus;
	
	
	
	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAttentionStatus() {
		return attentionStatus;
	}

	public void setAttentionStatus(String attentionStatus) {
		this.attentionStatus = attentionStatus;
	}

	private MouldList<ResultEquityDetialsItem> commList;
	private  ProductPrivateDetailList  productPrivateDetailList;

	public MouldList<ResultEquityDetialsItem> getCommList() {
		return commList;
	}

	public void setCommList(MouldList<ResultEquityDetialsItem> commList) {
		this.commList = commList;
	}

	public ProductPrivateDetailList getProductPrivateDetailList() {
		return productPrivateDetailList;
	}

	public void setProductPrivateDetailList(
			ProductPrivateDetailList productPrivateDetailList) {
		this.productPrivateDetailList = productPrivateDetailList;
	}
	

}
