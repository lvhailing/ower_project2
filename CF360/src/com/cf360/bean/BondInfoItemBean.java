package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class BondInfoItemBean implements IMouldType {
	
	private String caId;

	public BondInfoItemBean(String caId) {
		super();
		this.caId = caId;
	}

	public String getCaId() {
		return caId;
	}

	public void setCaId(String caId) {
		this.caId = caId;
	}
	
	
	
}
