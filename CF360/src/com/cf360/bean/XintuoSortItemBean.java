package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class XintuoSortItemBean implements IMouldType {
	private String category;
	private String defaultType;
	private String filterType;
	private String pageNo;
	private String token;

	public XintuoSortItemBean(String category, String defaultType, String filterType, String pageNo, String token) {
		this.category = category;
		this.defaultType = defaultType;
		this.filterType = filterType;
		this.pageNo = pageNo;
		this.token = token;
	}

	public XintuoSortItemBean(String category, String defaultType, String filterType, String pageNo) {
		this.category = category;
		this.defaultType = defaultType;
		this.filterType = filterType;
		this.pageNo = pageNo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
