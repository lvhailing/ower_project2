package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class SunShineItemBean implements IMouldType{
	
	private String category;
	private String filterType;
	private String pageNo;
	private String sortWay;
	private String token;


	public SunShineItemBean(String category, String filterType, String pageNo, String sortWay, String token) {
		this.category = category;
		this.filterType = filterType;
		this.pageNo = pageNo;
		this.sortWay = sortWay;
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

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getSortWay() {
		return sortWay;
	}

	public void setSortWay(String sortWay) {
		this.sortWay = sortWay;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
