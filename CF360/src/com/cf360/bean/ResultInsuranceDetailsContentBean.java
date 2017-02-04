package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultInsuranceDetailsContentBean implements IMouldType{
	private String attentionStatus;
	private String photosAttachmentsName;
	private String photosAttachmentsPath;
	private String infoAttachmentsPath;
	private String currency;
	private String currencyShow;
	private MouldList<InsuranceLimitBean> insuranceLimit;
	private MouldList<InsuranceAgecoverBean> insuranceAgecoverAge;
	private MouldList<InsuranceChildBean> insuranceChild;
	private MouldList<InsuranceDetailsListBean> productsList;
	private String bannerAttachmentsPath;
	private String auditStatus;
	
	
	
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getCurrencyShow() {
		return currencyShow;
	}
	public void setCurrencyShow(String currencyShow) {
		this.currencyShow = currencyShow;
	}
	public String getBannerAttachmentsPath() {
		return bannerAttachmentsPath;
	}
	public void setBannerAttachmentsPath(String bannerAttachmentsPath) {
		this.bannerAttachmentsPath = bannerAttachmentsPath;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPhotosAttachmentsName() {
		return photosAttachmentsName;
	}
	public void setPhotosAttachmentsName(String photosAttachmentsName) {
		this.photosAttachmentsName = photosAttachmentsName;
	}
	public String getPhotosAttachmentsPath() {
		return photosAttachmentsPath;
	}
	public void setPhotosAttachmentsPath(String photosAttachmentsPath) {
		this.photosAttachmentsPath = photosAttachmentsPath;
	}
	public MouldList<InsuranceDetailsListBean> getProductsList() {
		return productsList;
	}
	public void setProductsList(MouldList<InsuranceDetailsListBean> productsList) {
		this.productsList = productsList;
	}
	public String getAttentionStatus() {
		return attentionStatus;
	}
	public void setAttentionStatus(String attentionStatus) {
		this.attentionStatus = attentionStatus;
	}
	public MouldList<InsuranceLimitBean> getInsuranceLimit() {
		return insuranceLimit;
	}
	public void setInsuranceLimit(MouldList<InsuranceLimitBean> insuranceLimit) {
		this.insuranceLimit = insuranceLimit;
	}
	public MouldList<InsuranceAgecoverBean> getInsuranceAgecoverAge() {
		return insuranceAgecoverAge;
	}
	public void setInsuranceAgecoverAge(
			MouldList<InsuranceAgecoverBean> insuranceAgecoverAge) {
		this.insuranceAgecoverAge = insuranceAgecoverAge;
	}
	public MouldList<InsuranceChildBean> getInsuranceChild() {
		return insuranceChild;
	}
	public void setInsuranceChild(MouldList<InsuranceChildBean> insuranceChild) {
		this.insuranceChild = insuranceChild;
	}
	public String getInfoAttachmentsPath() {
		return infoAttachmentsPath;
	}
	public void setInfoAttachmentsPath(String infoAttachmentsPath) {
		this.infoAttachmentsPath = infoAttachmentsPath;
	}
	
	

}
