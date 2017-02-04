package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class SearchProductContentBean implements IMouldType {
	private String NAME;	//产品简称
	private String ID;	//产品编号
	private String SALETYPE;	//销售方式（0包销/1分销）
	private String INVESTMENTSTAGE;	//产品期限、投资期限、累计净值、保险期间
	private String CATEGORY;	//产品类型
	private String AMOUNT;	//认购金额、保险公司简称
	private String MINANNUALRATE;	//最小预期收益率
	private String COMMISSION;	//返佣比例、前端反应、后端返佣
	private String RECRUITMENTPROCESS;	//募集进度
	private String HASPROCESS;	//是否有进度条
	private String NAME3;	//认购金额key,保险公司key
	private String NAME4;	//产品期限key累计净值key投资期限key保险
	private String NAME5;	//返佣比例key
	private String SELLINGSTATUS;	//是否热销 0不显示  1 显示
	private String RECOMMENDSTATUS;	//推荐状态 0不显示  1 显示
	private String CREDITLEVEL;
	
	
	public String getCREDITLEVEL() {
		return CREDITLEVEL;
	}
	public void setCREDITLEVEL(String cREDITLEVEL) {
		CREDITLEVEL = cREDITLEVEL;
	}
	public String getSELLINGSTATUS() {
		return SELLINGSTATUS;
	}
	public void setSELLINGSTATUS(String sELLINGSTATUS) {
		SELLINGSTATUS = sELLINGSTATUS;
	}
	public String getRECOMMENDSTATUS() {
		return RECOMMENDSTATUS;
	}
	public void setRECOMMENDSTATUS(String rECOMMENDSTATUS) {
		RECOMMENDSTATUS = rECOMMENDSTATUS;
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
	public String getSALETYPE() {
		return SALETYPE;
	}
	public void setSALETYPE(String sALETYPE) {
		SALETYPE = sALETYPE;
	}
	public String getINVESTMENTSTAGE() {
		return INVESTMENTSTAGE;
	}
	public void setINVESTMENTSTAGE(String iNVESTMENTSTAGE) {
		INVESTMENTSTAGE = iNVESTMENTSTAGE;
	}
	public String getCATEGORY() {
		return CATEGORY;
	}
	public void setCATEGORY(String cATEGORY) {
		CATEGORY = cATEGORY;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getMINANNUALRATE() {
		return MINANNUALRATE;
	}
	public void setMINANNUALRATE(String mINANNUALRATE) {
		MINANNUALRATE = mINANNUALRATE;
	}
	public String getCOMMISSION() {
		return COMMISSION;
	}
	public void setCOMMISSION(String cOMMISSION) {
		COMMISSION = cOMMISSION;
	}
	public String getRECRUITMENTPROCESS() {
		return RECRUITMENTPROCESS;
	}
	public void setRECRUITMENTPROCESS(String rECRUITMENTPROCESS) {
		RECRUITMENTPROCESS = rECRUITMENTPROCESS;
	}
	public String getHASPROCESS() {
		return HASPROCESS;
	}
	public void setHASPROCESS(String hASPROCESS) {
		HASPROCESS = hASPROCESS;
	}
	public String getNAME3() {
		return NAME3;
	}
	public void setNAME3(String nAME3) {
		NAME3 = nAME3;
	}
	public String getNAME4() {
		return NAME4;
	}
	public void setNAME4(String nAME4) {
		NAME4 = nAME4;
	}
	public String getNAME5() {
		return NAME5;
	}
	public void setNAME5(String nAME5) {
		NAME5 = nAME5;
	}
	
	
}
