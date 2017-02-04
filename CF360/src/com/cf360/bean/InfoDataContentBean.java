package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class InfoDataContentBean implements IMouldType {
	
	private String ID;		//集合id
	private String PRODUCT_ID;		//产品id
	private String PRODUCT_NAME;		//产品名称
	private String CUSTOMER_NAME;		//客户姓名
	private String CUSTOMERPHONE;		//客户手机号
	private String TYPE;		//收支类型（in，activity，out）对应收入，活动，支出
	private String ANNUAL_AMOUNT;		//收益金额
	private String STATUS;		//收支状态handling返佣中，success已完成
	private String CREATE_TIME;		//创建时间
	private String INCOTYPE;	//数字正负
	private String PTYPE;		//收支类型
	
	public String getPTYPE() {
		return PTYPE;
	}
	public void setPTYPE(String pTYPE) {
		PTYPE = pTYPE;
	}
	public String getINCOTYPE() {
		return INCOTYPE;
	}
	public void setINCOTYPE(String iNCOTYPE) {
		INCOTYPE = iNCOTYPE;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}
	public void setPRODUCT_ID(String pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}
	public String getPRODUCT_NAME() {
		return PRODUCT_NAME;
	}
	public void setPRODUCT_NAME(String pRODUCT_NAME) {
		PRODUCT_NAME = pRODUCT_NAME;
	}
	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}
	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}
	public String getCUSTOMERPHONE() {
		return CUSTOMERPHONE;
	}
	public void setCUSTOMERPHONE(String cUSTOMERPHONE) {
		CUSTOMERPHONE = cUSTOMERPHONE;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getANNUAL_AMOUNT() {
		return ANNUAL_AMOUNT;
	}
	public void setANNUAL_AMOUNT(String aNNUAL_AMOUNT) {
		ANNUAL_AMOUNT = aNNUAL_AMOUNT;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(String cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	
}
