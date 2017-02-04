package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class HelpDetailBean implements IMouldType {
	private String id;

	public HelpDetailBean(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
