package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class CustomerFinancialSaveBean implements IMouldType {
	private String id;
	private String operation;
	private String car;
	private String house;
	private String valueAssessment;
	private String yearSalary;
	private String token;

	public CustomerFinancialSaveBean(String id, String operation, String car, String house, String valueAssessment, String yearSalary, String token) {
		this.id = id;
		this.operation = operation;
		this.car = car;
		this.house = house;
		this.valueAssessment = valueAssessment;
		this.yearSalary = yearSalary;
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

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getValueAssessment() {
		return valueAssessment;
	}

	public void setValueAssessment(String valueAssessment) {
		this.valueAssessment = valueAssessment;
	}

	public String getYearSalary() {
		return yearSalary;
	}

	public void setYearSalary(String yearSalary) {
		this.yearSalary = yearSalary;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
