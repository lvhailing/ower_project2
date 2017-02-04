package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultHelpDetailContentBean implements IMouldType {
	private String ID;
	private String TOPIC;
	private String NAME;
	private String CREATETIME;
	private String PROBLEMANSWER;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getTOPIC() {
		return TOPIC;
	}
	public void setTOPIC(String tOPIC) {
		TOPIC = tOPIC;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}
	public String getPROBLEMANSWER() {
		return PROBLEMANSWER;
	}
	public void setPROBLEMANSWER(String pROBLEMANSWER) {
		PROBLEMANSWER = pROBLEMANSWER;
	}
	
}
