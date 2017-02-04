package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;


public class ResultInfoDetailDataBean implements IMouldType {
	private String check;
	private String code;
	private ResultInfoDataDetailContentBean data;
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

	

	public ResultInfoDataDetailContentBean getData() {
		return data;
	}

	public void setData(ResultInfoDataDetailContentBean data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
