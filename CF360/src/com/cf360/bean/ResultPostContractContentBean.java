package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultPostContractContentBean implements IMouldType {
	private String flag;
	private String message;
	
	public ResultPostContractContentBean(String flag, String message) {
		setFlag(flag);
		setMessage(message);
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
