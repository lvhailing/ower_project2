package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultMyPersonContentBean implements IMouldType {
	
	private String auditStatus;
	private String insuranceAgentAuditStatus;
	private String totalIncome;
	private MyPersonContentBean userAccount;
	
	public String getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
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
	public MyPersonContentBean getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(MyPersonContentBean userAccount) {
		this.userAccount = userAccount;
	}
	
	
	
}
