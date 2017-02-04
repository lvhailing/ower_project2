package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultDeclarationDetailsUserPaymentBean implements IMouldType {
	private String ANNUALAMOUNT;
	private String CREATETIME;
	private String ADDCREATETIME;
	
	public ResultDeclarationDetailsUserPaymentBean(String aNNUALAMOUNT,
			String cREATETIME, String aDDCREATETIME) {
		setADDCREATETIME(aDDCREATETIME);
		setANNUALAMOUNT(aNNUALAMOUNT);
		setCREATETIME(aDDCREATETIME);
	}
	
	public String getANNUALAMOUNT() {
		return ANNUALAMOUNT;
	}

	public void setANNUALAMOUNT(String aNNUALAMOUNT) {
		ANNUALAMOUNT = aNNUALAMOUNT;
	}

	public String getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}
	public String getADDCREATETIME() {
		return ADDCREATETIME;
	}
	public void setADDCREATETIME(String aDDCREATETIME) {
		ADDCREATETIME = aDDCREATETIME;
	}
	
	
}