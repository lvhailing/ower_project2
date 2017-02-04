package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class InsuranceAgentBean implements IMouldType {
	private String orgId;

	public InsuranceAgentBean(String orgId) {
		super();
		this.orgId = orgId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
