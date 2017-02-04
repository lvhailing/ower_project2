package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class SelectCustomerBean implements IMouldType {
    private String customerName;
    private String token;

    public SelectCustomerBean(String customerName, String token) {
        this.customerName = customerName;
        this.token = token;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
