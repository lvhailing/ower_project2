package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultOrderListContentBean implements IMouldType {
	private String ID;
	private String PRODUCTID;
	private String PRODUCTNAME;
	private String STATUS;
	private String AMOUNT;
	private String CUSTOMERNAME;
	private String PRODUCTCATEGORY;
	private String APPOITMENTTIME;
	private String USERNAME;
	private String CURRENCY;
	
	public ResultOrderListContentBean(String aMOUNT,String iD, String pRODUCTID,
			String pRODUCTNAME, String sTATUS, String cUSTOMERNAME,
			String aPPOITMENTTIME,String pRODUCTCATEGORY,String USERNAME,String cURRENCY) {
		setAMOUNT(aMOUNT);
		setID(iD);
		setPRODUCTID(pRODUCTID);
		setPRODUCTNAME(pRODUCTNAME);
		setSTATUS(sTATUS);
		setCUSTOMERNAME(cUSTOMERNAME);
		setAPPOITMENTTIME(aPPOITMENTTIME);
		setPRODUCTCATEGORY(pRODUCTCATEGORY);
		setUSERNAME(USERNAME);
		setCURRENCY(cURRENCY);
	}
	
	public String getCURRENCY() {
		return CURRENCY;
	}

	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
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
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getPRODUCTID() {
		return PRODUCTID;
	}
	public void setPRODUCTID(String pRODUCTID) {
		PRODUCTID = pRODUCTID;
	}
	public String getPRODUCTNAME() {
		return PRODUCTNAME;
	}
	public void setPRODUCTNAME(String pRODUCTNAME) {
		PRODUCTNAME = pRODUCTNAME;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getCUSTOMERNAME() {
		return CUSTOMERNAME;
	}
	public void setCUSTOMERNAME(String cUSTOMERNAME) {
		CUSTOMERNAME = cUSTOMERNAME;
	}
	public String getAPPOITMENTTIME() {
		return APPOITMENTTIME;
	}
	public void setAPPOITMENTTIME(String aPPOITMENTTIME) {
		APPOITMENTTIME = aPPOITMENTTIME;
	}

}