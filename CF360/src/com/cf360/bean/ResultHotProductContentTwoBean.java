package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultHotProductContentTwoBean implements IMouldType {
	private String auditStatus;
	private MouldList<ResultHotProductContentBean> hotProduct;
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public MouldList<ResultHotProductContentBean> getHotProduct() {
		return hotProduct;
	}
	public void setHotProduct(MouldList<ResultHotProductContentBean> hotProduct) {
		this.hotProduct = hotProduct;
	}
	
	
}
