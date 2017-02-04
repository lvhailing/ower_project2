package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultSelectProductReturnAppolistBean implements IMouldType {
	private	String AMOUNT;
	private	String CREATETIME;
	private	String CUSTOMERNAME;
	private	String CUSTOMERPHONE;
	private	String ID;
	private	String PRODUCTCATEGORY;
	private	String PRODUCTID;
	private	String PRODUCTNAME;
	private	String REBATEPROPORTION;
	private	String REMARK;
	private	String STATUS;
	private	String USERMOBILE;
	private	String USERNAME;
	
	public ResultSelectProductReturnAppolistBean(String aMOUNT,
			String cREATETIME, String cUSTOMERNAME, String cUSTOMERPHONE,
			String iD, String pRODUCTCATEGORY, String pRODUCTID,
			String pRODUCTNAME, String rEBATEPROPORTION, String rEMARK,
			String sTATUS, String uSERMOBILE, String uSERNAME) {
		setAMOUNT(aMOUNT);
		setCREATETIME(cREATETIME);
		setCUSTOMERNAME(cUSTOMERNAME);
		setCUSTOMERPHONE(cUSTOMERPHONE);
		setID(iD);
		setPRODUCTCATEGORY(pRODUCTCATEGORY);
		setPRODUCTID(pRODUCTID);
		setPRODUCTNAME(pRODUCTNAME);
		setREBATEPROPORTION(rEBATEPROPORTION);
		setREMARK(rEMARK);
		setSTATUS(sTATUS);
		setUSERMOBILE(uSERMOBILE);
		setUSERNAME(uSERNAME);
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}
	public String getCUSTOMERNAME() {
		return CUSTOMERNAME;
	}
	public void setCUSTOMERNAME(String cUSTOMERNAME) {
		CUSTOMERNAME = cUSTOMERNAME;
	}
	public String getCUSTOMERPHONE() {
		return CUSTOMERPHONE;
	}
	public void setCUSTOMERPHONE(String cUSTOMERPHONE) {
		CUSTOMERPHONE = cUSTOMERPHONE;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getPRODUCTCATEGORY() {
		return PRODUCTCATEGORY;
	}
	public void setPRODUCTCATEGORY(String pRODUCTCATEGORY) {
		PRODUCTCATEGORY = pRODUCTCATEGORY;
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
	public String getREBATEPROPORTION() {
		return REBATEPROPORTION;
	}
	public void setREBATEPROPORTION(String rEBATEPROPORTION) {
		REBATEPROPORTION = rEBATEPROPORTION;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getUSERMOBILE() {
		return USERMOBILE;
	}
	public void setUSERMOBILE(String uSERMOBILE) {
		USERMOBILE = uSERMOBILE;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	

}