package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class CustomerInfoSaveBean implements IMouldType {
	private String id;
	private String operation;
	private String customerType;
	private String code;
	private String name;
	private String sex;
	private String age;
	private String idcard;
	private String nation;
	private String company;
	private String position;
	private String profession;
	private String token;

	public CustomerInfoSaveBean(String id, String operation, String customerType, String code, String name, String sex, String age, String idcard, String nation, String company, String position, String profession, String token) {
		this.id = id;
		this.operation = operation;
		this.customerType = customerType;
		this.code = code;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.idcard = idcard;
		this.nation = nation;
		this.company = company;
		this.position = position;
		this.profession = profession;
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
