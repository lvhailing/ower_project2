package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class SunShineSaiXuanItemBean implements IMouldType{
	private String category;
	private String companyList;
	private String filterType;
	private String fundList;
	private String investmentTypeList;
	private String sortWay;
	private String statusList;
	private String pageNo;
	private String token;


	public SunShineSaiXuanItemBean(String category, String companyList, String filterType, String fundList, String investmentTypeList, String sortWay, String statusList, String pageNo, String token) {
		this.category = category;
		this.companyList = companyList;
		this.filterType = filterType;
		this.fundList = fundList;
		this.investmentTypeList = investmentTypeList;
		this.sortWay = sortWay;
		this.statusList = statusList;
		this.pageNo = pageNo;
		this.token = token;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCompanyList() {
		return companyList;
	}

	public void setCompanyList(String companyList) {
		this.companyList = companyList;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public String getFundList() {
		return fundList;
	}

	public void setFundList(String fundList) {
		this.fundList = fundList;
	}

	public String getInvestmentTypeList() {
		return investmentTypeList;
	}

	public void setInvestmentTypeList(String investmentTypeList) {
		this.investmentTypeList = investmentTypeList;
	}

	public String getSortWay() {
		return sortWay;
	}

	public void setSortWay(String sortWay) {
		this.sortWay = sortWay;
	}

	public String getStatusList() {
		return statusList;
	}

	public void setStatusList(String statusList) {
		this.statusList = statusList;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
