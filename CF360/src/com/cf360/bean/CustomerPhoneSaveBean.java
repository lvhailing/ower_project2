package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class CustomerPhoneSaveBean implements IMouldType {
	
	private String id;
	private String operation;
	private String homePhone;
	private String mobilePhone;
	private String prePhone;
	private String qqNumber;
	private String fax;
	private String email;
	private String birthProvince;
	private String birthCity;
	private String liveProvince;
	private String liveCity;
	private String token;

	public CustomerPhoneSaveBean(String id, String operation, String homePhone, String mobilePhone, String prePhone, String qqNumber, String fax, String email, String birthProvince, String birthCity, String liveProvince, String liveCity, String token) {
		this.id = id;
		this.operation = operation;
		this.homePhone = homePhone;
		this.mobilePhone = mobilePhone;
		this.prePhone = prePhone;
		this.qqNumber = qqNumber;
		this.fax = fax;
		this.email = email;
		this.birthProvince = birthProvince;
		this.birthCity = birthCity;
		this.liveProvince = liveProvince;
		this.liveCity = liveCity;
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

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPrePhone() {
		return prePhone;
	}

	public void setPrePhone(String prePhone) {
		this.prePhone = prePhone;
	}

	public String getQqNumber() {
		return qqNumber;
	}

	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthProvince() {
		return birthProvince;
	}

	public void setBirthProvince(String birthProvince) {
		this.birthProvince = birthProvince;
	}

	public String getBirthCity() {
		return birthCity;
	}

	public void setBirthCity(String birthCity) {
		this.birthCity = birthCity;
	}

	public String getLiveProvince() {
		return liveProvince;
	}

	public void setLiveProvince(String liveProvince) {
		this.liveProvince = liveProvince;
	}

	public String getLiveCity() {
		return liveCity;
	}

	public void setLiveCity(String liveCity) {
		this.liveCity = liveCity;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
