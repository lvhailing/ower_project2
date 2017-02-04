package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultMyBankListContentBean implements IMouldType {
	
	private MouldList<MyBankListItemBean> bankList;
	private String flag;
	private String message;
	
	public MouldList<MyBankListItemBean> getBankList() {
		return bankList;
	}
	public void setBankList(MouldList<MyBankListItemBean> bankList) {
		this.bankList = bankList;
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
