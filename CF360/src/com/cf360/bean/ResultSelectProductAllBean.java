package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultSelectProductAllBean implements IMouldType {
	private String NAME;
	private String ID;
	private String ISSUER;
	private String CATEGORY;
	private String APPOID;
	
	public ResultSelectProductAllBean(String nAME, String iD, String iSSUER,String cATEGORY,String aPPOID) {
		super();
		setID(iD);
		setISSUER(iSSUER);
		setNAME(nAME);
		setCATEGORY(cATEGORY);
		setAPPOID(aPPOID);
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
	
	
}