package com.cf360.bean;

import java.util.ArrayList;
import java.util.List;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultEquityContentBean implements IMouldType {
	private ArrayList<String> fundList;
	private ArrayList<String> investmentTypeSMList;
	private ArrayList<String> statusList;
	private MouldList<ResultEquityListItem> productPrivateAppList;
	private String auditStatus;
	
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public ArrayList<String> getFundList() {
		return fundList;
	}
	public void setFundList(ArrayList<String> fundList) {
		this.fundList = fundList;
	}
	public ArrayList<String> getInvestmentTypeSMList() {
		return investmentTypeSMList;
	}
	public void setInvestmentTypeSMList(ArrayList<String> investmentTypeSMList) {
		this.investmentTypeSMList = investmentTypeSMList;
	}
	public ArrayList<String> getStatusList() {
		return statusList;
	}
	public void setStatusList(ArrayList<String> statusList) {
		this.statusList = statusList;
	}
	public MouldList<ResultEquityListItem> getProductPrivateAppList() {
		return productPrivateAppList;
	}
	public void setProductPrivateAppList(
			MouldList<ResultEquityListItem> productPrivateAppList) {
		this.productPrivateAppList = productPrivateAppList;
	}
}
