package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class PostInsuranceResultBean implements IMouldType {
    private String ageCoverage;
    private String amount;
    private String currency;
    private String customerName;
    private String customerPhone;
    private String deputyInsuranceAmount;
    private String deputyInsuranceName;
    private String deputyRebateProportion;
    private String payLimitTime;
    private String productCategory;
    private String productId;
    private String productName;
    private String rebateProportion;
    private String remark;
    private String totalamount;
    private String userName;
    private String userPhone;
    private String token;

    public PostInsuranceResultBean(String ageCoverage, String amount, String currency, String customerName, String customerPhone, String deputyInsuranceAmount, String deputyInsuranceName, String deputyRebateProportion, String payLimitTime, String productCategory, String productId, String productName, String rebateProportion, String remark, String totalamount, String userName, String userPhone, String token) {
        this.ageCoverage = ageCoverage;
        this.amount = amount;
        this.currency = currency;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.deputyInsuranceAmount = deputyInsuranceAmount;
        this.deputyInsuranceName = deputyInsuranceName;
        this.deputyRebateProportion = deputyRebateProportion;
        this.payLimitTime = payLimitTime;
        this.productCategory = productCategory;
        this.productId = productId;
        this.productName = productName;
        this.rebateProportion = rebateProportion;
        this.remark = remark;
        this.totalamount = totalamount;
        this.userName = userName;
        this.userPhone = userPhone;
        this.token = token;
    }

    public String getAgeCoverage() {
        return ageCoverage;
    }

    public void setAgeCoverage(String ageCoverage) {
        this.ageCoverage = ageCoverage;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDeputyInsuranceAmount() {
        return deputyInsuranceAmount;
    }

    public void setDeputyInsuranceAmount(String deputyInsuranceAmount) {
        this.deputyInsuranceAmount = deputyInsuranceAmount;
    }

    public String getDeputyInsuranceName() {
        return deputyInsuranceName;
    }

    public void setDeputyInsuranceName(String deputyInsuranceName) {
        this.deputyInsuranceName = deputyInsuranceName;
    }

    public String getDeputyRebateProportion() {
        return deputyRebateProportion;
    }

    public void setDeputyRebateProportion(String deputyRebateProportion) {
        this.deputyRebateProportion = deputyRebateProportion;
    }

    public String getPayLimitTime() {
        return payLimitTime;
    }

    public void setPayLimitTime(String payLimitTime) {
        this.payLimitTime = payLimitTime;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
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

    public String getRebateProportion() {
        return rebateProportion;
    }

    public void setRebateProportion(String rebateProportion) {
        this.rebateProportion = rebateProportion;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}