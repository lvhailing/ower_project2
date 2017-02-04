package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultSelectProductOrderBean implements IMouldType {
	private String NAME;
	private String ID;
	private String ISSUER;
	private String CUSTOMERNAME;
	private String totalAmount;
	private String CREATETIME;
	private String CATEGORY;
	private String APPOID;
	private String AMOUNT;
	private String CURRENCY;
	
	public ResultSelectProductOrderBean(String aMOUNT,String nAME, String iD, String iSSUER,
			String cUSTOMERNAME, String totalamount, String cREATETIME,String cATEGORY,String aPPOID,String cURRENCY) {
		super();
		setAMOUNT(aMOUNT);
		setTotalAmount(totalamount);
		setCREATETIME(cREATETIME);
		setCUSTOMERNAME(cUSTOMERNAME);
		setID(iD);
		setISSUER(iSSUER);
		setNAME(cUSTOMERNAME);
		setCATEGORY(cATEGORY);
		setAPPOID(aPPOID);
		setCURRENCY(cURRENCY);
	}
	
	public String getCURRENCY() {
		return CURRENCY;
	}

	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}

	public String getAPPOID() {
		return APPOID;
	}

	public void setAPPOID(String aPPOID) {
		APPOID = aPPOID;
	}

	public String getCATEGORY() {
		return CATEGORY;
	}

	public void setCATEGORY(String cATEGORY) {
		CATEGORY = cATEGORY;
	}

	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getISSUER() {
		return ISSUER;
	}
	public void setISSUER(String iSSUER) {
		ISSUER = iSSUER;
	}
	public String getCUSTOMERNAME() {
		return CUSTOMERNAME;
	}
	public void setCUSTOMERNAME(String cUSTOMERNAME) {
		CUSTOMERNAME = cUSTOMERNAME;
	}


	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}

	public String getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	
	
}