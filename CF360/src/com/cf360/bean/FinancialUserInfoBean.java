package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class FinancialUserInfoBean implements IMouldType {
	
	private String realName;		//真实姓名
	private String regionaProvince;	//所在省
	private String regionaCity;		//所在市
	private String companyName;		//工作单位
	private String email;		//
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
	
	
}
