package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultMyCustomerContentBean implements IMouldType {
	
	private MouldList<ResultMyCustomerContentItemBean> result;
	private String shortName;
	
	public MouldList<ResultMyCustomerContentItemBean> getResult() {
		return result;
	}
	public void setResult(MouldList<ResultMyCustomerContentItemBean> result) {
		this.result = result;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	
	
	
}
