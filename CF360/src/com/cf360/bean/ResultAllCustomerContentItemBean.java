package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultAllCustomerContentItemBean implements IMouldType {
	private String name;
	private String remark;
	private String id;
	
	public ResultAllCustomerContentItemBean(String name, String remark,
			String id) {
		super();
		this.name = name;
		this.remark = remark;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
