package com.cf360.bean;

import java.util.ArrayList;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultDeclarationDetailsOrderBean implements IMouldType {
	private String ID;
	private String PRODUCTNAME;
	private String STATUS;
	private String CONTRACTID;
	private String CUSTOMERNAME;
	private String AMOUNT;
	private String PAYMONEYTIME;
	private String COMMISSIONWAY;
	private String REBATEPROPORTION;
	private String CREATETIME;
	private String IDCARDPOSITIVE;
	private String IDCARDNEGATIVE;
	private String BANKCARD;
	private String SIGNATUREPAGE;
	private String PAYMENTRECEIPT;
	private String REALNAME;
	private String MOBILE;
	private String REMARK;
	private String AGECOVERAGE;
	private String DEPUTYINSURANCENAME;
	private String PAYLIMITTIME;
	private String PRODUCTCATEGORY;
	private String PRODUCTID;
	private ArrayList<String> AMOUNT2;
	private ArrayList<String> REBATEPROPORTION2;
	private String USERNAME;
	private String USERPHONE;
	private String COSTAMOUNT;

	public ResultDeclarationDetailsOrderBean(String iD, String pRODUCTNAME,
			String sTATUS, String cONTRACTID, String cUSTOMERNAME,
			String aMOUNT, String pAYMONEYTIME, String cOMMISSIONWAY,
			String rEBATEPROPORTION, String cREATETIME, String iDCARDPOSITIVE,
			String iDCARDNEGATIVE, String bANKCARD, String sIGNATUREPAGE,
			String pAYMENTRECEIPT, String rEALNAME, String mOBILE,
			String rEMARK, String aGECOVERAGE, String dEPUTYINSURANCENAME,
			String pAYLIMITTIME, String pRODUCTCATEGORY, String pRODUCTID,
			ArrayList<String> AMOUNT2, ArrayList<String> rEBATEPROPORTION2,String USERNAME,String USERPHONE,String COSTAMOUNT) {
		setID(iD);
		setPRODUCTNAME(pRODUCTNAME);
		setSTATUS(sTATUS);
		setCONTRACTID(cONTRACTID);
		setCUSTOMERNAME(cUSTOMERNAME);
		setAMOUNT(aMOUNT);
		setPAYMONEYTIME(pAYMONEYTIME);
		setCOMMISSIONWAY(cOMMISSIONWAY);
		setREBATEPROPORTION(rEBATEPROPORTION);
		setCREATETIME(cREATETIME);
		setIDCARDPOSITIVE(iDCARDPOSITIVE);
		setIDCARDNEGATIVE(iDCARDNEGATIVE);
		setBANKCARD(bANKCARD);
		setSIGNATUREPAGE(sIGNATUREPAGE);
		setPAYMENTRECEIPT(pAYMENTRECEIPT);
		setREALNAME(rEALNAME);
		setMOBILE(mOBILE);
		setREMARK(rEMARK);
		setAGECOVERAGE(aGECOVERAGE);
		setDEPUTYINSURANCENAME(dEPUTYINSURANCENAME);
		setPAYLIMITTIME(pAYLIMITTIME);
		setPRODUCTCATEGORY(pRODUCTCATEGORY);
		setPRODUCTID(pRODUCTID);
		setAMOUNT2(AMOUNT2);
		setREBATEPROPORTION2(rEBATEPROPORTION2);
		setUSERNAME(USERNAME);
		setUSERPHONE(USERPHONE);
		setCOSTAMOUNT(COSTAMOUNT);
	}

	public String getCOSTAMOUNT() {
		return COSTAMOUNT;
	}

	public void setCOSTAMOUNT(String cOSTAMOUNT) {
		COSTAMOUNT = cOSTAMOUNT;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getUSERPHONE() {
		return USERPHONE;
	}

	public void setUSERPHONE(String uSERPHONE) {
		USERPHONE = uSERPHONE;
	}

	public ArrayList<String> getREBATEPROPORTION2() {
		return REBATEPROPORTION2;
	}

	public void setREBATEPROPORTION2(ArrayList<String> rEBATEPROPORTION2) {
		REBATEPROPORTION2 = rEBATEPROPORTION2;
	}

	public ArrayList<String> getAMOUNT2() {
		return AMOUNT2;
	}

	public void setAMOUNT2(ArrayList<String> aMOUNT2) {
		AMOUNT2 = aMOUNT2;
	}

	public String getAGECOVERAGE() {
		return AGECOVERAGE;
	}

	public void setAGECOVERAGE(String aGECOVERAGE) {
		AGECOVERAGE = aGECOVERAGE;
	}

	public String getDEPUTYINSURANCENAME() {
		return DEPUTYINSURANCENAME;
	}

	public void setDEPUTYINSURANCENAME(String dEPUTYINSURANCENAME) {
		DEPUTYINSURANCENAME = dEPUTYINSURANCENAME;
	}

	public String getPAYLIMITTIME() {
		return PAYLIMITTIME;
	}

	public void setPAYLIMITTIME(String pAYLIMITTIME) {
		PAYLIMITTIME = pAYLIMITTIME;
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

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getCONTRACTID() {
		return CONTRACTID;
	}

	public void setCONTRACTID(String cONTRACTID) {
		CONTRACTID = cONTRACTID;
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

	public String getPAYMONEYTIME() {
		return PAYMONEYTIME;
	}

	public void setPAYMONEYTIME(String pAYMONEYTIME) {
		PAYMONEYTIME = pAYMONEYTIME;
	}

	public String getCOMMISSIONWAY() {
		return COMMISSIONWAY;
	}

	public void setCOMMISSIONWAY(String cOMMISSIONWAY) {
		COMMISSIONWAY = cOMMISSIONWAY;
	}

	public String getREBATEPROPORTION() {
		return REBATEPROPORTION;
	}

	public void setREBATEPROPORTION(String rEBATEPROPORTION) {
		REBATEPROPORTION = rEBATEPROPORTION;
	}

	public String getCREATETIME() {
		return CREATETIME;
	}

	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}

	public String getIDCARDPOSITIVE() {
		return IDCARDPOSITIVE;
	}

	public void setIDCARDPOSITIVE(String iDCARDPOSITIVE) {
		IDCARDPOSITIVE = iDCARDPOSITIVE;
	}

	public String getIDCARDNEGATIVE() {
		return IDCARDNEGATIVE;
	}

	public void setIDCARDNEGATIVE(String iDCARDNEGATIVE) {
		IDCARDNEGATIVE = iDCARDNEGATIVE;
	}

	public String getBANKCARD() {
		return BANKCARD;
	}

	public void setBANKCARD(String bANKCARD) {
		BANKCARD = bANKCARD;
	}

	public String getSIGNATUREPAGE() {
		return SIGNATUREPAGE;
	}

	public void setSIGNATUREPAGE(String sIGNATUREPAGE) {
		SIGNATUREPAGE = sIGNATUREPAGE;
	}

	public String getPAYMENTRECEIPT() {
		return PAYMENTRECEIPT;
	}

	public void setPAYMENTRECEIPT(String pAYMENTRECEIPT) {
		PAYMENTRECEIPT = pAYMENTRECEIPT;
	}

	public String getREALNAME() {
		return REALNAME;
	}

	public void setREALNAME(String rEALNAME) {
		REALNAME = rEALNAME;
	}

	public String getMOBILE() {
		return MOBILE;
	}

	public void setMOBILE(String mOBILE) {
		MOBILE = mOBILE;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
}