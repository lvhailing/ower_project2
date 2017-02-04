package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class SunShineContentBean implements IMouldType{
	private	String attentionStatus;
	private MouldList<ResultSunShineItem> commList;
	private  ResultSunshineObject productPrivateDetailList;
	private String auditStatus;
	
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public MouldList<ResultSunShineItem> getCommList() {
		return commList;
	}
	public void setCommList(MouldList<ResultSunShineItem> commList) {
		this.commList = commList;
	}
	public ResultSunshineObject getProductPrivateDetailList() {
		return productPrivateDetailList;
	}
	public void setProductPrivateDetailList(
			ResultSunshineObject productPrivateDetailList) {
		this.productPrivateDetailList = productPrivateDetailList;
	}
	public String getAttentionStatus() {
		return attentionStatus;
	}
	public void setAttentionStatus(String attentionStatus) {
		this.attentionStatus = attentionStatus;
	}
	
	
}
