package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultContractListContentBean implements IMouldType {
	private MouldList<ResultContractListItemBean> contractAppList;
	private String auditStatus;

	public MouldList<ResultContractListItemBean> getContractAppList() {
		return contractAppList;
	}

	public void setContractAppList(
			MouldList<ResultContractListItemBean> contractAppList) {
		this.contractAppList = contractAppList;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

}