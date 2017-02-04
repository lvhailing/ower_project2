package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;


public class ResultFinancialToUserAuditBean implements IMouldType {
	private String check;
	private String code;
	private ResultFinancialToUserAuditContentBean data;
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
	public ResultFinancialToUserAuditContentBean getData() {
		return data;
	}

	public void setData(ResultFinancialToUserAuditContentBean data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
