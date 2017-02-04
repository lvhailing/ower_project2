package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class FinancialBean implements IMouldType {
	private String mobile;
	private String realName;
	private String regionaProvince;
	private String regionaCity;
	private String companyName;
	private String email;
	private String businessCardName;
	private String token;

	public FinancialBean(String mobile, String realName, String regionaProvince, String regionaCity, String companyName, String email, String businessCardName, String token) {
		this.mobile = mobile;
		this.realName = realName;
		this.regionaProvince = regionaProvince;
		this.regionaCity = regionaCity;
		this.companyName = companyName;
		this.email = email;
		this.businessCardName = businessCardName;
		this.token = token;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRegionaProvince() {
		return regionaProvince;
	}

	public void setRegionaProvince(String regionaProvince) {
		this.regionaProvince = regionaProvince;
	}

	public String getRegionaCity() {
		return regionaCity;
	}

	public void setRegionaCity(String regionaCity) {
		this.regionaCity = regionaCity;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBusinessCardName() {
		return businessCardName;
	}

	public void setBusinessCardName(String businessCardName) {
		this.businessCardName = businessCardName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
