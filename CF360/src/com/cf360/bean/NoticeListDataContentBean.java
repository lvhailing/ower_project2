package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class NoticeListDataContentBean implements IMouldType {
	
	private String TYPE;
	private String CREATETIME;
	private String TOPIC;
	private String ID;
	private String CONTENT;
	private String DESCRIPTION;
	
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}
	public String getTOPIC() {
		return TOPIC;
	}
	public void setTOPIC(String tOPIC) {
		TOPIC = tOPIC;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	
}
