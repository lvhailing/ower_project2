package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultSunShineListItem implements IMouldType{
	
	private String category;
	private String id;
	private String maxFrontcomm;
	private String minAmount;
	private String minFrontcomm;
	private String name;
	private String sumNet;
	private String recommendStatus;
	private String saleType;
	private String sellingStatus;
	private String recruitmentProcess;
	
	public String getRecruitmentProcess() {
		return recruitmentProcess;
	}
	public void setRecruitmentProcess(String recruitmentProcess) {
		this.recruitmentProcess = recruitmentProcess;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMaxFrontcomm() {
		return maxFrontcomm;
	}
	public void setMaxFrontcomm(String maxFrontcomm) {
		this.maxFrontcomm = maxFrontcomm;
	}
	public String getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}
	public String getMinFrontcomm() {
		return minFrontcomm;
	}
	public void setMinFrontcomm(String minFrontcomm) {
		this.minFrontcomm = minFrontcomm;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSumNet() {
		return sumNet;
	}
	public void setSumNet(String sumNet) {
		this.sumNet = sumNet;
	}
	public String getRecommendStatus() {
		return recommendStatus;
	}
	public void setRecommendStatus(String recommendStatus) {
		this.recommendStatus = recommendStatus;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getSellingStatus() {
		return sellingStatus;
	}
	public void setSellingStatus(String sellingStatus) {
		this.sellingStatus = sellingStatus;
	}
	

}
