package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;


public class ResultFocusBean implements IMouldType {
	private String check;
	private String code;
	private ResultFocusContentBean data;

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

	public ResultFocusContentBean getData() {
		return data;
	}

	public void setData(ResultFocusContentBean data) {
		this.data = data;
	}

}
