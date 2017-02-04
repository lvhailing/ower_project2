package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class SInsurancetempBean implements IMouldType {
	/*	category
	companyShortName
	filterType
	pageNo
	sortType*/

	private String category;
	private String companyShortName;
	private String filterType;
	private String pageNo;
	private String sortType;
	private String token;

	public SInsurancetempBean(String category, String companyShortName, String filterType, String pageNo, String sortType, String token) {
		this.category = category;
		this.companyShortName = companyShortName;
		this.filterType = filterType;
		this.pageNo = pageNo;
		this.sortType = sortType;
		this.token = token;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCompanyShortName() {
		return companyShortName;
	}

	public void setCompanyShortName(String companyShortName) {
		this.companyShortName = companyShortName;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
