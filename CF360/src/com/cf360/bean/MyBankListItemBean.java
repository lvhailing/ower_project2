package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class MyBankListItemBean implements IMouldType {
	
	private String openAcctId;		//银行卡号
	private String id;
	private String usrCustId;		//银行全称(如：中国银行)
	private String userName;		//银行用户姓名
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOpenAcctId() {
		return openAcctId;
	}
	public void setOpenAcctId(String openAcctId) {
		this.openAcctId = openAcctId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsrCustId() {
		return usrCustId;
	}
	public void setUsrCustId(String usrCustId) {
		this.usrCustId = usrCustId;
	}
	
	
	
}
