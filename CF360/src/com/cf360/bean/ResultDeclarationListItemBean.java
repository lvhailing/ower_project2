package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultDeclarationListItemBean implements IMouldType {
	private String ID;
	private String PRODUCTNAME;
	private String CUSTOMERNAME;
	private String AMOUNT;
	private String STATUS;
	private String CREATETIME;
	private String PRODUCTCATEGORY;
	private String totalAmount;//打款金额
	
	public ResultDeclarationListItemBean(String ID, String pRODUCTNAME,
			String cUSTOMERNAME, String aMOUNT, String sTATUS, String cREATETIME,String pRODUCTCATEGORY,String totalAmount) {
		setAMOUNT(aMOUNT);
		setCREATETIME(cREATETIME);
		setCUSTOMERNAME(cUSTOMERNAME);
		setID(ID);
		setPRODUCTNAME(pRODUCTNAME);
		setSTATUS(sTATUS);
		setPRODUCTCATEGORY(pRODUCTCATEGORY);
		setTotalAmount(totalAmount);
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
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
	public String getPRODUCTNAME() {
		return PRODUCTNAME;
	}
	public void setPRODUCTNAME(String pRODUCTNAME) {
		PRODUCTNAME = pRODUCTNAME;
	}
	public String getCUSTOMERNAME() {
		return CUSTOMERNAME;
	}
	public void setCUSTOMERNAME(String cUSTOMERNAME) {
		CUSTOMERNAME = cUSTOMERNAME;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}
	
	

}