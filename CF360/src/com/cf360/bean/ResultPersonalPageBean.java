package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultPersonalPageBean implements IMouldType {
	private String check;
	private String code;
	private ResultPersonalPageContentBean data;
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

	public ResultPersonalPageContentBean getData() {
		return data;
	}

	public void setData(ResultPersonalPageContentBean data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
