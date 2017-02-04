package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultChoseBankListContentBean implements IMouldType {
	
	private MouldList<MyBankListItemBean> bankList;
	private String flag;
	private String message;
	private String avlBal;
	private String realName;
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getAvlBal() {
		return avlBal;
	}
	public void setAvlBal(String avlBal) {
		this.avlBal = avlBal;
	}
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
