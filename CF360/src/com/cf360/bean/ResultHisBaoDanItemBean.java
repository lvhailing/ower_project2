package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultHisBaoDanItemBean implements IMouldType {
	private String PRODUCTNAME;//产品名称
	private String PRODUCTCATEGORY;//产品类型
	private String ID;//报单ID
	private String CREATETIME;//成交日期
	public String getPRODUCTNAME() {
		return PRODUCTNAME;
	}
	public void setPRODUCTNAME(String pRODUCTNAME) {
		PRODUCTNAME = pRODUCTNAME;
	}
	public String getPRODUCTCATEGORY() {
		return PRODUCTCATEGORY;
	}
	public void setPRODUCTCATEGORY(String pRODUCTCATEGORY) {
		PRODUCTCATEGORY = pRODUCTCATEGORY;
	}
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
	

}