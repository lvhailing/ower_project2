package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultOrderListBean implements IMouldType {
	private String check;
	private String code;
	private MouldList<ResultOrderListContentBean> data;
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

	public MouldList<ResultOrderListContentBean> getData() {
		return data;
	}

	public void setData(MouldList<ResultOrderListContentBean> data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
