package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultEditDeclarationOrderBean implements IMouldType {
	private String AGECOVERAGE;
	private String AMOUNT;
	private String BANKCARD;
	private String CUSTOMERIDCARD;
	private String CUSTOMERNAME;
	private String CONTRACTID;
	private String COSTAMOUNT;
	private String DEPUTYINSURANCEID;
	private String DEPUTYINSURANCENAME;
	private String ID;
	private String IDCARDNEGATIVE;
	private String IDCARDPOSITIVE;
	private String PAYLIMITTIME;
	private String PAYMENTRECEIPT;
	private String PAYMONEYTIME;
	private String PRODUCTCATEGORY;
	private String PRODUCTID;
	private String PRODUCTNAME;
	private String REBATEPROPORTION;
	private String REMARK;
	private String SIGNATUREPAGE;
	private String USERNAME;
	private String USERPHONE;

	public ResultEditDeclarationOrderBean(String aGECOVERAGE, String aMOUNT,
			String bANKCARD, String cUSTOMERIDCARD, String cUSTOMERNAME,
			String cONTRACTID,String cOSTAMOUNT,String dEPUTYINSURANCEID, String dEPUTYINSURANCENAME, String iD,
			String iDCARDNEGATIVE, String iDCARDPOSITIVE, String pAYLIMITTIME,
			String pAYMENTRECEIPT, String pAYMONEYTIME, String pRODUCTCATEGORY,
			String pRODUCTID, String pRODUCTNAME, String rEBATEPROPORTION,
			String rEMARK, String sIGNATUREPAGE, String uSERNAME,
			String uSERPHONE) {
		super();
		setAGECOVERAGE(aGECOVERAGE);
		setAMOUNT(aMOUNT);
		setBANKCARD(bANKCARD);
		setCUSTOMERIDCARD(cUSTOMERIDCARD);
		setCUSTOMERNAME(cUSTOMERNAME);
		setCONTRACTID(cONTRACTID);
		setCOSTAMOUNT(cOSTAMOUNT);
		setDEPUTYINSURANCEID(dEPUTYINSURANCEID);
		setDEPUTYINSURANCENAME(dEPUTYINSURANCENAME);
		setID(iD);
		setIDCARDNEGATIVE(iDCARDNEGATIVE);
		setIDCARDPOSITIVE(iDCARDPOSITIVE);
		setPAYLIMITTIME(pAYLIMITTIME);
		setPAYMENTRECEIPT(pAYMENTRECEIPT);
		setPAYMONEYTIME(pAYMONEYTIME);
		setPRODUCTCATEGORY(pRODUCTCATEGORY);
		setPRODUCTID(pRODUCTID);
		setPRODUCTNAME(pRODUCTNAME);
		setREBATEPROPORTION(rEBATEPROPORTION);
		setREMARK(rEMARK);
		setSIGNATUREPAGE(sIGNATUREPAGE);
		setUSERNAME(uSERNAME);
		setUSERPHONE(uSERPHONE);
	}

	public String getCOSTAMOUNT() {
		return COSTAMOUNT;
	}

	public void setCOSTAMOUNT(String cOSTAMOUNT) {
		COSTAMOUNT = cOSTAMOUNT;
	}

	public String getDEPUTYINSURANCEID() {
		return DEPUTYINSURANCEID;
	}

	public void setDEPUTYINSURANCEID(String dEPUTYINSURANCEID) {
		DEPUTYINSURANCEID = dEPUTYINSURANCEID;
	}

	public String getCUSTOMERIDCARD() {
		return CUSTOMERIDCARD;
	}

	public void setCUSTOMERIDCARD(String cUSTOMERIDCARD) {
		CUSTOMERIDCARD = cUSTOMERIDCARD;
	}

	public String getAGECOVERAGE() {
		return AGECOVERAGE;
	}

	public void setAGECOVERAGE(String aGECOVERAGE) {
		AGECOVERAGE = aGECOVERAGE;
	}

	public String getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}

	public String getBANKCARD() {
		return BANKCARD;
	}

	public void setBANKCARD(String bANKCARD) {
		BANKCARD = bANKCARD;
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

	public String getDEPUTYINSURANCENAME() {
		return DEPUTYINSURANCENAME;
	}

	public void setDEPUTYINSURANCENAME(String dEPUTYINSURANCENAME) {
		DEPUTYINSURANCENAME = dEPUTYINSURANCENAME;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getIDCARDNEGATIVE() {
		return IDCARDNEGATIVE;
	}

	public void setIDCARDNEGATIVE(String iDCARDNEGATIVE) {
		IDCARDNEGATIVE = iDCARDNEGATIVE;
	}


	public String getIDCARDPOSITIVE() {
		return IDCARDPOSITIVE;
	}

	public void setIDCARDPOSITIVE(String iDCARDPOSITIVE) {
		IDCARDPOSITIVE = iDCARDPOSITIVE;
	}

	public String getPAYLIMITTIME() {
		return PAYLIMITTIME;
	}

	public void setPAYLIMITTIME(String pAYLIMITTIME) {
		PAYLIMITTIME = pAYLIMITTIME;
	}

	public String getPAYMENTRECEIPT() {
		return PAYMENTRECEIPT;
	}

	public void setPAYMENTRECEIPT(String pAYMENTRECEIPT) {
		PAYMENTRECEIPT = pAYMENTRECEIPT;
	}

	public String getPAYMONEYTIME() {
		return PAYMONEYTIME;
	}

	public void setPAYMONEYTIME(String pAYMONEYTIME) {
		PAYMONEYTIME = pAYMONEYTIME;
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

	public String getSIGNATUREPAGE() {
		return SIGNATUREPAGE;
	}

	public void setSIGNATUREPAGE(String sIGNATUREPAGE) {
		SIGNATUREPAGE = sIGNATUREPAGE;
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
}