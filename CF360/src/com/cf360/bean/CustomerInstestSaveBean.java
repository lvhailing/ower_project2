package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class CustomerInstestSaveBean implements IMouldType {
	 private String id;
	 private String operation;
	 private String interestSport;
	 private String interestArt;
	 private String interestArder;
	 private String token;

	public CustomerInstestSaveBean(String id, String operation, String interestSport, String interestArt, String interestArder, String token) {
		this.id = id;
		this.operation = operation;
		this.interestSport = interestSport;
		this.interestArt = interestArt;
		this.interestArder = interestArder;
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

	public String getInterestSport() {
		return interestSport;
	}

	public void setInterestSport(String interestSport) {
		this.interestSport = interestSport;
	}

	public String getInterestArt() {
		return interestArt;
	}

	public void setInterestArt(String interestArt) {
		this.interestArt = interestArt;
	}

	public String getInterestArder() {
		return interestArder;
	}

	public void setInterestArder(String interestArder) {
		this.interestArder = interestArder;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
