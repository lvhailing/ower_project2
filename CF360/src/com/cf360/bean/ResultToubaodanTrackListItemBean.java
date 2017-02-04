package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultToubaodanTrackListItemBean implements IMouldType {
	private String ID;
	private String STATUS;
	private String CUSTOMERNAME;
	private String AMOUNT;
	private String ACCEPTER;
	private String ACCEPTPHONE;
	private String ACCEPTADDRESS;

	private String USERNAME;//理财师姓名     
	private String USERPHONE;//理财师电话
	
	private String PAYLIMITTIME;//年限
	private String AGECOVERAGE;//年龄段
	private String DEPUTYINSURANCENAME;//副险名称
	private String PRODUCTID;//产品id
	private String AGE;
	private String PRODUCTNAME;
	

	
	public ResultToubaodanTrackListItemBean(String aGE, String iD,String sTATUS, String cUSTOMERNAME,
			String aMOUNT, String aCCEPTER, String aCCEPTPHONE,
			String aCCEPTADDRESS, String uSERNAME,String uSERPHONE,String pAYLIMITTIME,String aGECOVERAGE,String dEPUTYINSURANCENAME,String pRODUCTID,String pRODUCTNAME) {
		setAGE(aGE);
		setID(iD);
		setSTATUS(sTATUS);
		setCUSTOMERNAME(cUSTOMERNAME);
		setAMOUNT(aMOUNT);
		setACCEPTER(aCCEPTER);
		setACCEPTPHONE(aCCEPTPHONE);
		setACCEPTADDRESS(aCCEPTADDRESS);
		setUSERNAME(uSERNAME);
		setUSERPHONE(uSERPHONE);
		setPAYLIMITTIME(pAYLIMITTIME);
		setPRODUCTID(pRODUCTID);
		setAGECOVERAGE(aGECOVERAGE);
		setDEPUTYINSURANCENAME(dEPUTYINSURANCENAME);
		setPRODUCTNAME(pRODUCTNAME);
	}
	
	public String getPRODUCTNAME() {
		return PRODUCTNAME;
	}

	public void setPRODUCTNAME(String pRODUCTNAME) {
		PRODUCTNAME = pRODUCTNAME;
	}

	public String getAGE() {
		return AGE;
	}

	public void setAGE(String aGE) {
		AGE = aGE;
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

	public String getPAYLIMITTIME() {
		return PAYLIMITTIME;
	}

	public void setPAYLIMITTIME(String pAYLIMITTIME) {
		PAYLIMITTIME = pAYLIMITTIME;
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
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getACCEPTER() {
		return ACCEPTER;
	}
	public void setACCEPTER(String aCCEPTER) {
		ACCEPTER = aCCEPTER;
	}
	public String getACCEPTPHONE() {
		return ACCEPTPHONE;
	}
	public void setACCEPTPHONE(String aCCEPTPHONE) {
		ACCEPTPHONE = aCCEPTPHONE;
	}
	public String getACCEPTADDRESS() {
		return ACCEPTADDRESS;
	}
	public void setACCEPTADDRESS(String aCCEPTADDRESS) {
		ACCEPTADDRESS = aCCEPTADDRESS;
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