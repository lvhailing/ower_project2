package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultToubaodanListContentBean implements IMouldType {
	private MouldList<ResultToubaodanListItemBean> policyListForApp;
	private String auditStatus;
	private String insuranceAgentAuditStatus;


	public MouldList<ResultToubaodanListItemBean> getPolicyListForApp() {
		return policyListForApp;
	}

	public void setPolicyListForApp(
			MouldList<ResultToubaodanListItemBean> policyListForApp) {
		this.policyListForApp = policyListForApp;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getInsuranceAgentAuditStatus() {
		return insuranceAgentAuditStatus;
	}

	public void setInsuranceAgentAuditStatus(String insuranceAgentAuditStatus) {
		this.insuranceAgentAuditStatus = insuranceAgentAuditStatus;
	}

}