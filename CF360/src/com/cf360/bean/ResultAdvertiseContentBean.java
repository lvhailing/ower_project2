package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

/**
 *  首页轮播图 接收后台返回json对应数据（2B）
 */
public class ResultAdvertiseContentBean implements IMouldType {
    private String picture;  //图片的存放路径
    private String url;  //图片跳转链接
    private String description; //图片描述

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
