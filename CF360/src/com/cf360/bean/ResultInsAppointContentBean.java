package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultInsAppointContentBean implements IMouldType {
	private String check;
	private String code;
	private ResultInsAppoint data;
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


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ResultInsAppoint getData() {
		return data;
	}

	public void setData(ResultInsAppoint data) {
		this.data = data;
	}
	

}
