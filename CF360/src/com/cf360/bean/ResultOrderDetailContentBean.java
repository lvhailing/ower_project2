package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultOrderDetailContentBean implements IMouldType {
	private String ID;
	private String PRODUCTID;
	private String PRODUCTNAME;
	private String STATUS;
	private String AMOUNT;
	private String CUSTOMERNAME;
	private String CUSTOMERPHONE;
	private String APPOITMENTTIME;
	private String REBATEPROPORTION;
	private String REMARK;
	private String AGECOVERAGE;
	private String PAYLIMITTIME;
	private String DEPUTYINSURANCENAME; 
	private String USERNAME; 
	private String USERPHONE; 
	private String PRODUCTCATEGORY; 
	private String CURRENCY; 
	private String PRODUCTSTATUS;
	private String UPPERANDLOWERFRAME;
	

	public ResultOrderDetailContentBean(String iD, String pRODUCTID,
			String pRODUCTNAME, String sTATUS, String aMOUNT,
			String cUSTOMERNAME, String cUSTOMERPHONE, String aPPOITMENTTIME,
			String rEBATEPROPORTION, String rEMARK, String aGECOVERAGE,
			String pAYLIMITTIME, String dEPUTYINSURANCENAME, String uSERNAME,
			String uSERPHONE, String pRODUCTCATEGORY, String cURRENCY,
			String pRODUCTSTATUS, String uPPERANDLOWERFRAME) {
		super();
		ID = iD;
		PRODUCTID = pRODUCTID;
		PRODUCTNAME = pRODUCTNAME;
		STATUS = sTATUS;
		AMOUNT = aMOUNT;
		CUSTOMERNAME = cUSTOMERNAME;
		CUSTOMERPHONE = cUSTOMERPHONE;
		APPOITMENTTIME = aPPOITMENTTIME;
		REBATEPROPORTION = rEBATEPROPORTION;
		REMARK = rEMARK;
		AGECOVERAGE = aGECOVERAGE;
		PAYLIMITTIME = pAYLIMITTIME;
		DEPUTYINSURANCENAME = dEPUTYINSURANCENAME;
		USERNAME = uSERNAME;
		USERPHONE = uSERPHONE;
		PRODUCTCATEGORY = pRODUCTCATEGORY;
		CURRENCY = cURRENCY;
		PRODUCTSTATUS = pRODUCTSTATUS;
		UPPERANDLOWERFRAME = uPPERANDLOWERFRAME;
	}

	public String getPRODUCTSTATUS() {
		return PRODUCTSTATUS;
	}

	public void setPRODUCTSTATUS(String pRODUCTSTATUS) {
		PRODUCTSTATUS = pRODUCTSTATUS;
	}

	public String getUPPERANDLOWERFRAME() {
		return UPPERANDLOWERFRAME;
	}

	public void setUPPERANDLOWERFRAME(String uPPERANDLOWERFRAME) {
		UPPERANDLOWERFRAME = uPPERANDLOWERFRAME;
	}

	public String getCURRENCY() {
		return CURRENCY;
	}

	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}

	public String getPRODUCTCATEGORY() {
		return PRODUCTCATEGORY;
	}

	public void setPRODUCTCATEGORY(String pRODUCTCATEGORY) {
		PRODUCTCATEGORY = pRODUCTCATEGORY;
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

	public String getId() {
		return ID;
	}

	public void setId(String ID) {
		this.ID = ID;
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

	public String getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
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

	public String getAPPOITMENTTIME() {
		return APPOITMENTTIME;
	}

	public void setAPPOITMENTTIME(String aPPOITMENTTIME) {
		APPOITMENTTIME = aPPOITMENTTIME;
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

	public String getAGECOVERAGE() {
		return AGECOVERAGE;
	}

	public void setAGECOVERAGE(String aGECOVERAGE) {
		AGECOVERAGE = aGECOVERAGE;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getPAYLIMITTIME() {
		return PAYLIMITTIME;
	}

	public void setPAYLIMITTIME(String pAYLIMITTIME) {
		PAYLIMITTIME = pAYLIMITTIME;
	}

	public String getDEPUTYINSURANCENAME() {
		return DEPUTYINSURANCENAME;
	}

	public void setDEPUTYINSURANCENAME(String dEPUTYINSURANCENAME) {
		DEPUTYINSURANCENAME = dEPUTYINSURANCENAME;
	}

}