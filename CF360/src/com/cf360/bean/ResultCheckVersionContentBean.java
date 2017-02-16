package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

/**
 *  检查版本更新 接收后台返回json对应数据（2B）
 */
public class ResultCheckVersionContentBean implements IMouldType {
    private String version;
    private String url;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
