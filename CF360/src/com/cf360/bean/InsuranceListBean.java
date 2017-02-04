package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class InsuranceListBean implements IMouldType{
	
 /*   "COMMISSIONRATIO": "1~4%",
    "COMPANY_SHORT_NAME": "中国平安",
    "ID": "15082000000003541343",
    "MAX_COMMISSION_RATIO": "4",
    "MIN_COMMISSION_RATIO": "1",
    "ROWNUMTEMP": 1,
    "SHORT_NAME": "少儿保险001",
    "TIME_LIMIT": "0-10岁"*/

	
	private String COMMISSIONRATIO;
	private String COMPANY_SHORT_NAME;
	private String ID;
	private String MAX_COMMISSION_RATIO;
	private String MIN_COMMISSION_RATIO;
	private String ROWNUMTEMP;
	private String SHORT_NAME;
	private String TIME_LIMIT;
	private String RECOMMEND_STATUS;
	private String SELLING_STATUS;
	public String getCOMMISSIONRATIO() {
		return COMMISSIONRATIO;
	}
	public void setCOMMISSIONRATIO(String cOMMISSIONRATIO) {
		COMMISSIONRATIO = cOMMISSIONRATIO;
	}
	public String getCOMPANY_SHORT_NAME() {
		return COMPANY_SHORT_NAME;
	}
	public void setCOMPANY_SHORT_NAME(String cOMPANY_SHORT_NAME) {
		COMPANY_SHORT_NAME = cOMPANY_SHORT_NAME;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getMAX_COMMISSION_RATIO() {
		return MAX_COMMISSION_RATIO;
	}
	public void setMAX_COMMISSION_RATIO(String mAX_COMMISSION_RATIO) {
		MAX_COMMISSION_RATIO = mAX_COMMISSION_RATIO;
	}
	public String getMIN_COMMISSION_RATIO() {
		return MIN_COMMISSION_RATIO;
	}
	public void setMIN_COMMISSION_RATIO(String mIN_COMMISSION_RATIO) {
		MIN_COMMISSION_RATIO = mIN_COMMISSION_RATIO;
	}
	public String getROWNUMTEMP() {
		return ROWNUMTEMP;
	}
	public void setROWNUMTEMP(String rOWNUMTEMP) {
		ROWNUMTEMP = rOWNUMTEMP;
	}
	public String getSHORT_NAME() {
		return SHORT_NAME;
	}
	public void setSHORT_NAME(String sHORT_NAME) {
		SHORT_NAME = sHORT_NAME;
	}
	public String getTIME_LIMIT() {
		return TIME_LIMIT;
	}
	public void setTIME_LIMIT(String tIME_LIMIT) {
		TIME_LIMIT = tIME_LIMIT;
	}
	public String getRECOMMEND_STATUS() {
		return RECOMMEND_STATUS;
	}
	public void setRECOMMEND_STATUS(String rECOMMEND_STATUS) {
		RECOMMEND_STATUS = rECOMMEND_STATUS;
	}
	public String getSELLING_STATUS() {
		return SELLING_STATUS;
	}
	public void setSELLING_STATUS(String sELLING_STATUS) {
		SELLING_STATUS = sELLING_STATUS;
	}
	
	
	
}
