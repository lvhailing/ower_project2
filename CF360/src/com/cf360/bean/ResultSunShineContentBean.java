package com.cf360.bean;

import java.util.ArrayList;
import java.util.List;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultSunShineContentBean implements IMouldType{
	private ArrayList<String> companyList;
	private ArrayList<String> fundList;
	private ArrayList<String> investmentTypeList;
	private ArrayList<String> statusList;
	private MouldList<ResultSunShineListItem> productPrivateAppList;
	private String auditStatus;
	
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public ArrayList<String> getCompanyList() {
		return companyList;
	}
	public void setCompanyList(ArrayList<String> companyList) {
		this.companyList = companyList;
	}
	public ArrayList<String> getFundList() {
		return fundList;
	}
	public void setFundList(ArrayList<String> fundList) {
		this.fundList = fundList;
	}
	public ArrayList<String> getInvestmentTypeList() {
		return investmentTypeList;
	}
	public void setInvestmentTypeList(ArrayList<String> investmentTypeList) {
		this.investmentTypeList = investmentTypeList;
	}
	public ArrayList<String> getStatusList() {
		return statusList;
	}
	public void setStatusList(ArrayList<String> statusList) {
		this.statusList = statusList;
	}
	public MouldList<ResultSunShineListItem> getProductPrivateAppList() {
		return productPrivateAppList;
	}
	public void setProductPrivateAppList(
			MouldList<ResultSunShineListItem> productPrivateAppList) {
		this.productPrivateAppList = productPrivateAppList;
	}
	
	
}
