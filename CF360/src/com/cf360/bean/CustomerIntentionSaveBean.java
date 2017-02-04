package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class CustomerIntentionSaveBean implements IMouldType {
	private String id;
	private String operation;
	private String investType;
	private String investTrade;
	private String investMoney;
	private String investYear;
	private String investIncome;
	private String token;

	public CustomerIntentionSaveBean(String id, String operation, String investType, String investTrade, String investMoney, String investYear, String investIncome, String token) {
		this.id = id;
		this.operation = operation;
		this.investType = investType;
		this.investTrade = investTrade;
		this.investMoney = investMoney;
		this.investYear = investYear;
		this.investIncome = investIncome;
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

	public String getInvestType() {
		return investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

	public String getInvestTrade() {
		return investTrade;
	}

	public void setInvestTrade(String investTrade) {
		this.investTrade = investTrade;
	}

	public String getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(String investMoney) {
		this.investMoney = investMoney;
	}

	public String getInvestYear() {
		return investYear;
	}

	public void setInvestYear(String investYear) {
		this.investYear = investYear;
	}

	public String getInvestIncome() {
		return investIncome;
	}

	public void setInvestIncome(String investIncome) {
		this.investIncome = investIncome;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
