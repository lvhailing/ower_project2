package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class PersonalPageBean implements IMouldType {
	private String page;
	private String id;

	public PersonalPageBean(String id, String page) {
		setId(id);
		setPage(page);
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
