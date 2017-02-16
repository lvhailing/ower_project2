package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

/**
 *  首页轮播图 接收后台返回json对应数据（1B）
 */
public class ResultAdvertiseBean implements IMouldType {
    private String check;
    private String code;
    private MouldList<ResultAdvertiseContentBean> data;
    private String msg;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MouldList<ResultAdvertiseContentBean> getData() {
        return data;
    }

    public void setData(MouldList<ResultAdvertiseContentBean> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
