package com.cf360.bean;

import java.util.ArrayList;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultEditDeclarationListContentBean implements IMouldType {
	private ArrayList<String> amountList;
	private ArrayList<String> nameList;
	private ArrayList<String> rationList;
	private MouldList<ResultEditDeclarationDeputyBean> AgeCoverageData;
	private ArrayList<String> payLimits;
	private MouldList<ResultEditDeclarationDeputyNameBean> deputyName;
	private MouldList<ResultEditDeclarationOrderBean> order;
	private String canOrderAmount;
	private String minPayAmount;
	private String currency;
	private String orderAmount;

	public ResultEditDeclarationListContentBean(ArrayList<String> amountList,
			ArrayList<String> nameList, ArrayList<String> rationList,
			MouldList<ResultEditDeclarationDeputyBean> ageCoverageData,
			ArrayList<String> payLimits,
			MouldList<ResultEditDeclarationDeputyNameBean> deputyName,
			MouldList<ResultEditDeclarationOrderBean> order,
			String canOrderAmount, String minPayAmount, String currency,
			String orderAmount) {
		super();
		this.amountList = amountList;
		this.nameList = nameList;
		this.rationList = rationList;
		AgeCoverageData = ageCoverageData;
		this.payLimits = payLimits;
		this.deputyName = deputyName;
		this.order = order;
		this.canOrderAmount = canOrderAmount;
		this.minPayAmount = minPayAmount;
		this.currency = currency;
		this.orderAmount = orderAmount;
	}

	public MouldList<ResultEditDeclarationDeputyBean> getAgeCoverageData() {
		return AgeCoverageData;
	}

	public void setAgeCoverageData(
			MouldList<ResultEditDeclarationDeputyBean> ageCoverageData) {
		AgeCoverageData = ageCoverageData;
	}

	public ArrayList<String> getPayLimits() {
		return payLimits;
	}

	public void setPayLimits(ArrayList<String> payLimits) {
		this.payLimits = payLimits;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCanOrderAmount() {
		return canOrderAmount;
	}

	public void setCanOrderAmount(String canOrderAmount) {
		this.canOrderAmount = canOrderAmount;
	}

	public String getMinPayAmount() {
		return minPayAmount;
	}

	public void setMinPayAmount(String minPayAmount) {
		this.minPayAmount = minPayAmount;
	}

	public ArrayList<String> getAmountList() {
		return amountList;
	}

	public void setAmountList(ArrayList<String> amountList) {
		this.amountList = amountList;
	}

	public ArrayList<String> getNameList() {
		return nameList;
	}

	public void setNameList(ArrayList<String> nameList) {
		this.nameList = nameList;
	}

	public ArrayList<String> getRationList() {
		return rationList;
	}

	public void setRationList(ArrayList<String> rationList) {
		this.rationList = rationList;
	}

	public MouldList<ResultEditDeclarationDeputyNameBean> getDeputyName() {
		return deputyName;
	}

	public void setDeputyName(
			MouldList<ResultEditDeclarationDeputyNameBean> deputyName) {
		this.deputyName = deputyName;
	}

	public MouldList<ResultEditDeclarationOrderBean> getOrder() {
		return order;
	}

	public void setOrder(MouldList<ResultEditDeclarationOrderBean> order) {
		this.order = order;
	}

}