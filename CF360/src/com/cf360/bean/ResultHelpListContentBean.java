package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultHelpListContentBean implements IMouldType {
	private String ID;		//问题分类编号（备用）
	private String PID;		//问题编号（备用）
	private String CATEGORYNAME;		//问题名称
	private String PTOPIC;		//问题标题
	private String PEDITTIME;		//问题编辑时间
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getPID() {
		return PID;
	}
	public void setPID(String pID) {
		PID = pID;
	}
	public String getCATEGORYNAME() {
		return CATEGORYNAME;
	}
	public void setCATEGORYNAME(String cATEGORYNAME) {
		CATEGORYNAME = cATEGORYNAME;
	}
	public String getPTOPIC() {
		return PTOPIC;
	}
	public void setPTOPIC(String pTOPIC) {
		PTOPIC = pTOPIC;
	}
	public String getPEDITTIME() {
		return PEDITTIME;
	}
	public void setPEDITTIME(String pEDITTIME) {
		PEDITTIME = pEDITTIME;
	}
	
	
	
}	
