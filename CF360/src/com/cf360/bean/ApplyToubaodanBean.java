package com.cf360.bean;

public class ApplyToubaodanBean {
	String appId;
	String productId;
	String productName;
	String customerName;
	String userName;
	String userPhone;
	//String amount;
	String accepter;
	String acceptPhone;
	String acceptAddress;
	String deputyInsuranceName;
	String payLimitTime;
	String ageCoverage;
	String age;
	String token;
	public ApplyToubaodanBean(String appId, String productId,
			String productName, String customerName, String userName,
			String userPhone, String accepter,
			String acceptPhone, String acceptAddress,
			String deputyInsuranceName, String payLimitTime, String ageCoverage,String age,String token) {
		setAppId(appId);
		setProductId(productId);
		setProductName(productName);
		setCustomerName(customerName);
		setUserName(userName);
		setUserPhone(userPhone);
	//	setAmount(amount);
		setAccepter(accepter);
		setAcceptPhone(acceptPhone);
		setAcceptAddress(acceptAddress);
		setDeputyInsuranceName(deputyInsuranceName);
		setPayLimitTime(payLimitTime);
		setAgeCoverage(ageCoverage);
		setAge(age);
		setToken(token);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getAccepter() {
		return accepter;
	}
	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}
	public String getAcceptPhone() {
		return acceptPhone;
	}
	public void setAcceptPhone(String acceptPhone) {
		this.acceptPhone = acceptPhone;
	}
	public String getAcceptAddress() {
		return acceptAddress;
	}
	public void setAcceptAddress(String acceptAddress) {
		this.acceptAddress = acceptAddress;
	}
	public String getDeputyInsuranceName() {
		return deputyInsuranceName;
	}
	public void setDeputyInsuranceName(String deputyInsuranceName) {
		this.deputyInsuranceName = deputyInsuranceName;
	}
	public String getPayLimitTime() {
		return payLimitTime;
	}
	public void setPayLimitTime(String payLimitTime) {
		this.payLimitTime = payLimitTime;
	}
	public String getAgeCoverage() {
		return ageCoverage;
	}
	public void setAgeCoverage(String ageCoverage) {
		this.ageCoverage = ageCoverage;
	}


}
