package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class InsurancetempBean implements IMouldType {
	private String filterType;
	private String pageNo;
	private String sortType;
	private String token;


	public InsurancetempBean(String filterType, String pageNo, String sortType, String token) {
		this.filterType = filterType;
		this.pageNo = pageNo;
		this.sortType = sortType;
		this.token = token;
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
