package com.cf360.bean;

/**
 * Created by hasee on 2016/6/17.
 */
public class MyAccountBean {

    private String token;

    public MyAccountBean(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
