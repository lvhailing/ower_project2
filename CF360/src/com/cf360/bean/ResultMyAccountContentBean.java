package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultMyAccountContentBean implements IMouldType {
	
	private String auditStatus;
	private String insuranceAgentAuditStatus;
	private MyAccountContentBean userAccount;
	private String totalIncome;
	private String realName;
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
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
	public MyAccountContentBean getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(MyAccountContentBean userAccount) {
		this.userAccount = userAccount;
	}
	
	
}
