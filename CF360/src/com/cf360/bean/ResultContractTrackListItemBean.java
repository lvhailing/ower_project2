package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultContractTrackListItemBean implements IMouldType {
	private String STATUS;
	private String CUSTOMERNAME;
	private String AMOUNT;
	private String ACCEPTER;
	private String ACCEPTPHONE;
	private String ACCEPTADDRESS;
	private String EXPRESSNAME;//寄出的快递公司
	private String EXPRESSCODE;//寄出的运单号	
	private String EXPRESSURL;//寄出的快递地址
	private String EXPRESSNAMEBACK;//寄回的快递公司
	private String EXPRESSCODEBACK;//寄回的运单号
	private String EXPRESSURLBACK;//寄回的快递地址
	private String USERNAME;//理财师姓名     
	private String USERPHONE;//理财师电话
	
	private String PAYLIMITTIME;//年限
	private String AGECOVERAGE;//年龄段
	private String DEPUTYINSURANCENAME;//副险名称
	private String PRODUCTID;//产品id
	private String NAME;

	
	public ResultContractTrackListItemBean(String sTATUS, String cUSTOMERNAME,
			String aMOUNT, String aCCEPTER, String aCCEPTPHONE,
			String aCCEPTADDRESS, String eXPRESSNAME, String eXPRESSCODE,
			String eXPRESSURL, String eXPRESSNAMEBACK, String eXPRESSCODEBACK,
			String eXPRESSURLBACK,String uSERNAME,String uSERPHONE,String pAYLIMITTIME,String aGECOVERAGE,String dEPUTYINSURANCENAME,String pRODUCTID,String nAME) {
		setSTATUS(sTATUS);
		setCUSTOMERNAME(cUSTOMERNAME);
		setAMOUNT(aMOUNT);
		setACCEPTER(aCCEPTER);
		setACCEPTPHONE(aCCEPTPHONE);
		setACCEPTADDRESS(aCCEPTADDRESS);
		setEXPRESSNAME(eXPRESSNAME);
		setEXPRESSCODE(eXPRESSCODE);
		setEXPRESSURL(eXPRESSURL);
		setEXPRESSNAMEBACK(eXPRESSNAMEBACK);
		setEXPRESSCODEBACK(eXPRESSCODEBACK);
		setEXPRESSURLBACK(eXPRESSURLBACK);
		setUSERNAME(uSERNAME);
		setUSERPHONE(uSERPHONE);
		setPAYLIMITTIME(pAYLIMITTIME);
		setPRODUCTID(pRODUCTID);
		setAGECOVERAGE(aGECOVERAGE);
		setDEPUTYINSURANCENAME(dEPUTYINSURANCENAME);
		setNAME(nAME);
	}
	
	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getPRODUCTID() {
		return PRODUCTID;
	}

	public void setPRODUCTID(String pRODUCTID) {
		PRODUCTID = pRODUCTID;
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
	public String getEXPRESSNAME() {
		return EXPRESSNAME;
	}
	public void setEXPRESSNAME(String eXPRESSNAME) {
		EXPRESSNAME = eXPRESSNAME;
	}
	public String getEXPRESSCODE() {
		return EXPRESSCODE;
	}
	public void setEXPRESSCODE(String eXPRESSCODE) {
		EXPRESSCODE = eXPRESSCODE;
	}
	public String getEXPRESSURL() {
		return EXPRESSURL;
	}
	public void setEXPRESSURL(String eXPRESSURL) {
		EXPRESSURL = eXPRESSURL;
	}
	public String getEXPRESSNAMEBACK() {
		return EXPRESSNAMEBACK;
	}
	public void setEXPRESSNAMEBACK(String eXPRESSNAMEBACK) {
		EXPRESSNAMEBACK = eXPRESSNAMEBACK;
	}
	public String getEXPRESSCODEBACK() {
		return EXPRESSCODEBACK;
	}
	public void setEXPRESSCODEBACK(String eXPRESSCODEBACK) {
		EXPRESSCODEBACK = eXPRESSCODEBACK;
	}
	public String getEXPRESSURLBACK() {
		return EXPRESSURLBACK;
	}
	public void setEXPRESSURLBACK(String eXPRESSURLBACK) {
		EXPRESSURLBACK = eXPRESSURLBACK;
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