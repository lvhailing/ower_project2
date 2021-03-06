package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultSelectCustomerContentItemBean implements IMouldType {
	private String name;
	private String remark;
	private String sex;
	private String id;
	
	public ResultSelectCustomerContentItemBean(String name, String remark,
			String sex, String id) {
		super();
		this.name = name;
		this.remark = remark;
		this.sex = sex;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
