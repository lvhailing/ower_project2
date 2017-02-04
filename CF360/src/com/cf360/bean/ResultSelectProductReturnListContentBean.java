package com.cf360.bean;

import java.io.Serializable;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultSelectProductReturnListContentBean implements IMouldType,Serializable{
	private static final long serialVersionUID = 1L;
	private	String id;
	private	String category;
	private	String name;
	private	boolean flag;
	private String currency;
	
	private MouldList<ResultSelectProductReturnInsuranceChildBean> insuranceChild;
	private MouldList<ResultSelectProductReturnInsuranceLimitBean> insuranceLimit;
	private MouldList<ResultSelectProductReturnInsuranceAgecoverAgeBean> insuranceAgecoverAge;
	private MouldList<ResultSelectProductReturnInsuranceCommissionRatioBean> insuranceCommissionRatio;
	private MouldList<ResultSelectProductReturnAppolistBean> appolist;
	private String minPayAmount;
	private String canOrderAmount;
	private String orderAmount;
	
	
	public ResultSelectProductReturnListContentBean(
			String minPayAmount,
			String canOrderAmount,
			String orderAmount,
			String id,
			String category,
			String name,
			boolean flag,
			String currency,
			MouldList<ResultSelectProductReturnInsuranceChildBean> insuranceChild,
			MouldList<ResultSelectProductReturnInsuranceLimitBean> insuranceLimit,
			MouldList<ResultSelectProductReturnInsuranceAgecoverAgeBean> insuranceAgecoverAge,
			MouldList<ResultSelectProductReturnInsuranceCommissionRatioBean> insuranceCommissionRatio,
			MouldList<ResultSelectProductReturnAppolistBean> appolist) {
		setMinPayAmount(minPayAmount);
		setCanOrderAmount(canOrderAmount);
		setId(id);
		setCategory(category);
		setName(name);
		setFlag(flag);
		setInsuranceChild(insuranceChild);
		setInsuranceLimit(insuranceLimit);
		setInsuranceAgecoverAge(insuranceAgecoverAge);
		setInsuranceCommissionRatio(insuranceCommissionRatio);
		setAppolist(appolist);
		setCurrency(currency);
		setOrderAmount(orderAmount);
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

	public String getMinPayAmount() {
		return minPayAmount;
	}

	public void setMinPayAmount(String minPayAmount) {
		this.minPayAmount = minPayAmount;
	}

	public String getCanOrderAmount() {
		return canOrderAmount;
	}

	public void setCanOrderAmount(String canOrderAmount) {
		this.canOrderAmount = canOrderAmount;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public MouldList<ResultSelectProductReturnInsuranceChildBean> getInsuranceChild() {
		return insuranceChild;
	}
	public void setInsuranceChild(
			MouldList<ResultSelectProductReturnInsuranceChildBean> insuranceChild) {
		this.insuranceChild = insuranceChild;
	}
	public MouldList<ResultSelectProductReturnInsuranceLimitBean> getInsuranceLimit() {
		return insuranceLimit;
	}
	public void setInsuranceLimit(
			MouldList<ResultSelectProductReturnInsuranceLimitBean> insuranceLimit) {
		this.insuranceLimit = insuranceLimit;
	}
	public MouldList<ResultSelectProductReturnInsuranceAgecoverAgeBean> getInsuranceAgecoverAge() {
		return insuranceAgecoverAge;
	}
	public void setInsuranceAgecoverAge(
			MouldList<ResultSelectProductReturnInsuranceAgecoverAgeBean> insuranceAgecoverAge) {
		this.insuranceAgecoverAge = insuranceAgecoverAge;
	}
	public MouldList<ResultSelectProductReturnInsuranceCommissionRatioBean> getInsuranceCommissionRatio() {
		return insuranceCommissionRatio;
	}
	public void setInsuranceCommissionRatio(
			MouldList<ResultSelectProductReturnInsuranceCommissionRatioBean> insuranceCommissionRatio) {
		this.insuranceCommissionRatio = insuranceCommissionRatio;
	}
	public MouldList<ResultSelectProductReturnAppolistBean> getAppolist() {
		return appolist;
	}
	public void setAppolist(
			MouldList<ResultSelectProductReturnAppolistBean> appolist) {
		this.appolist = appolist;
	}
	

}