package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class MessageListDataContentBean implements IMouldType {
	private String ID;
	private String CREATETIME;
	private String TOPIC;
	private String CONTENT;
	private String STATUS;			//unread未读 read 已读
	private String TYPE;			//TYPE消息类型，预约，报单。。
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
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
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	
	
	
}
