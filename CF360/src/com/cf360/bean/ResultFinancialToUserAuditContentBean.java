package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultFinancialToUserAuditContentBean implements IMouldType {
	
	private String flag;
	private String message;
	private String auditStatus;
	private FinancialUserInfoBean user;
	private String busineseCardUrl;		//理财师名片地址
	private String businessCardName;		//理财师名字
	
	public String getBusinessCardName() {
		return businessCardName;
	}
	public void setBusinessCardName(String businessCardName) {
		this.businessCardName = businessCardName;
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
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public FinancialUserInfoBean getUser() {
		return user;
	}
	public void setUser(FinancialUserInfoBean user) {
		this.user = user;
	}
	public String getBusineseCardUrl() {
		return busineseCardUrl;
	}
	public void setBusineseCardUrl(String busineseCardUrl) {
		this.busineseCardUrl = busineseCardUrl;
	}
	
	
}
