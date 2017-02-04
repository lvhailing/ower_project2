package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultDeclarationListContentBean implements IMouldType {
	private MouldList<ResultDeclarationListItemBean> orderList;
	private String auditStatus;
	
	public MouldList<ResultDeclarationListItemBean> getOrderList() {
		return orderList;
	}
	public void setOrderList(MouldList<ResultDeclarationListItemBean> orderList) {
		this.orderList = orderList;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	

}
