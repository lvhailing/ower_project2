package com.cf360.bean;

import com.cf360.mould.types.IMouldType;


public class SelectProductReturnBean implements IMouldType {
	private String id;
	private String category;
	private String appoId;
	private String token;
	public SelectProductReturnBean(String id, String category, String appoId,String token) {
		setId(id);
		setAppoId(appoId);
		setCategory(category);
		setToken(token);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAppoId() {
		return appoId;
	}
	public void setAppoId(String appoId) {
		this.appoId = appoId;
	}

}
