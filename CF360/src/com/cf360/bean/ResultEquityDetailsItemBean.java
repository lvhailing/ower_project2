package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultEquityDetailsItemBean implements IMouldType {
	private String check;
	private String code;
	private ResultXinDetailsContentBean data;
	private String msg;
	
	public ResultEquityDetailsItemBean(String check, String code,
			ResultXinDetailsContentBean data, String msg) {
		setCheck(check);
		setCode(code);
		setData(data);
		setMsg(msg);
		}
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
	public ResultXinDetailsContentBean getData() {
		return data;
	}
	public void setData(ResultXinDetailsContentBean data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	

}
