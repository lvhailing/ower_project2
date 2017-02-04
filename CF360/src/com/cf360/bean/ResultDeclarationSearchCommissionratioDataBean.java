package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultDeclarationSearchCommissionratioDataBean implements IMouldType {
	private String ID;
	private String PRODUCTNAME;
	private String COMMISSIONRATIO;
	private String currency;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
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
	public String getCOMMISSIONRATIO() {
		return COMMISSIONRATIO;
	}
	public void setCOMMISSIONRATIO(String cOMMISSIONRATIO) {
		COMMISSIONRATIO = cOMMISSIONRATIO;
	}

}
