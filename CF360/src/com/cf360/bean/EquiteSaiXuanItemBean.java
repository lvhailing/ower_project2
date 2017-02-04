package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class EquiteSaiXuanItemBean implements IMouldType{
	private String category;
	private String filterType;
	private String fundList;
	private String investmentTypeSMList;
	private String sortWay;
	private String statusList;
	private String pageNo;
	private String token;
	
	
	public EquiteSaiXuanItemBean(String category,String filterType
			,String fundList,String investmentTypeSMList
			,String sortWay,String statusList,String pageNo,String token) {
		super();
		this.category=category;
		this.filterType=filterType;
		this.fundList=fundList;
		this.investmentTypeSMList=investmentTypeSMList;
		this.sortWay=sortWay;
		this.category=category;
		this.statusList=statusList;
		this.pageNo=pageNo;
		this.token = token;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
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



	public String getInvestmentTypeSMList() {
		return investmentTypeSMList;
	}



	public void setInvestmentTypeSMList(String investmentTypeSMList) {
		this.investmentTypeSMList = investmentTypeSMList;
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
