package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class HelpListBean implements IMouldType {
	private int page;

	public HelpListBean(int page) {
		super();
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
}	
