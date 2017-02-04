package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class XintuoSaiItemBean implements IMouldType {
	private String annualRate;
	//private String annualRate;
	private String category;
	private String commission;
	private String defaultType;
	private String filterType;
	private String investmentField;
	private String issuer;
	private String pageNo;
	private String token;
	/*private String commission;
	private String commissionMax;
	private String creditLevelMax;
	private String defaultType;
	private int filterType;
	private String investmentField;
	private String issuer;
	private int pageNo;
	private String serviceFeeRateMax;*/








/*	public XintuoSaiItemBean(String annualRate,String category
			,String commission,String defaultType
			,int filterType,String investmentField
			,String issuer,int pageNo) {
		//this.annualRate=annualRate;
		this.category=category;
		//this.commission=commission;
		this.defaultType=defaultType;
		this.filterType=filterType;
		//this.investmentField=investmentField;
		//this.issuer=issuer;
		this.pageNo=pageNo;
	}*/


	public XintuoSaiItemBean(String annualRate,String category
			,String commission
			,String defaultType
			,String filterType
			,String investmentField
			,String issuer
			,String pageNo,String token) {
		this.annualRate=annualRate;
		this.category=category;
		this.commission=commission;
		this.defaultType=defaultType;
		this.filterType=filterType;
		this.investmentField=investmentField;
		this.issuer=issuer;
		this.pageNo=pageNo;
		this.token = token;
	}








	public String getAnnualRate() {
		return annualRate;
	}








	public void setAnnualRate(String annualRate) {
		this.annualRate = annualRate;
	}








	public String getCategory() {
		return category;
	}








	public void setCategory(String category) {
		this.category = category;
	}








	public String getCommission() {
		return commission;
	}








	public void setCommission(String commission) {
		this.commission = commission;
	}








	public String getDefaultType() {
		return defaultType;
	}








	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}








	public String getFilterType() {
		return filterType;
	}








	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}








	public String getInvestmentField() {
		return investmentField;
	}








	public void setInvestmentField(String investmentField) {
		this.investmentField = investmentField;
	}








	public String getIssuer() {
		return issuer;
	}








	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}








	public String getPageNo() {
		return pageNo;
	}








	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
