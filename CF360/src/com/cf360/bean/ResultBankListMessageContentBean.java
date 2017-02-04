package com.cf360.bean;

import java.util.List;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultBankListMessageContentBean implements IMouldType {

	
	private String flag;
	private List<String> keys;
	private String message;
	private List<String> values;
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public List getKeys() {
		return keys;
	}
	public void setKeys(List keys) {
		this.keys = keys;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List getValues() {
		return values;
	}
	public void setValues(List values) {
		this.values = values;
	}
	
}
