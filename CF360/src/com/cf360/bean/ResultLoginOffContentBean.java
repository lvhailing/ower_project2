package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultLoginOffContentBean implements IMouldType {
	private String flag;
	private String msg;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}