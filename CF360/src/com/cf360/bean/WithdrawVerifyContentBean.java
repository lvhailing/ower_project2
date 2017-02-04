package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class WithdrawVerifyContentBean implements IMouldType {
	
	private String flag;
	private String result;
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
