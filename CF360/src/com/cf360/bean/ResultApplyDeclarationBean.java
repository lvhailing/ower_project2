package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultApplyDeclarationBean implements IMouldType {
	private String check;
	private String code;
	private ResultApplyDeclarationContentBean data;
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

	public ResultApplyDeclarationContentBean getData() {
		return data;
	}

	public void setData(ResultApplyDeclarationContentBean data) {
		this.data = data;
	}

}
