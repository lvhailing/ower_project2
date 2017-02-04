package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class CustomerAddBean implements IMouldType {
	private String operation;
	private String customerType;
	private String code;
	private String name;
	private String sex;
	private String idcard;
	private String mobilePhone;
	private String valueAssessment;
	private String token;

	public CustomerAddBean(String operation, String customerType, String code, String name, String sex, String idcard, String mobilePhone, String valueAssessment, String token) {
		this.operation = operation;
		this.customerType = customerType;
		this.code = code;
		this.name = name;
		this.sex = sex;
		this.idcard = idcard;
		this.mobilePhone = mobilePhone;
		this.valueAssessment = valueAssessment;
		this.token = token;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getValueAssessment() {
		return valueAssessment;
	}

	public void setValueAssessment(String valueAssessment) {
		this.valueAssessment = valueAssessment;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
