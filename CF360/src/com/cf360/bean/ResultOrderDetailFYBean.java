package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultOrderDetailFYBean implements IMouldType {
	private String PRODUCTNAME;//副险产品名称
	private String AGECOVERAGE;//副险产品年龄段
	private String PAYLIMIT;//副险产品年限
	private String COMMISSIONRATIO;//副险产品返佣比例
	
	public ResultOrderDetailFYBean(String pRODUCTNAME, String aGECOVERAGE,
			String pAYLIMIT, String cOMMISSIONRATIO) {
		setPRODUCTNAME(pRODUCTNAME);
		setAGECOVERAGE(aGECOVERAGE);
		setPAYLIMIT(pAYLIMIT);
		setCOMMISSIONRATIO(cOMMISSIONRATIO);
	}
	public String getPRODUCTNAME() {
		return PRODUCTNAME;
	}
	public void setPRODUCTNAME(String pRODUCTNAME) {
		PRODUCTNAME = pRODUCTNAME;
	}
	public String getAGECOVERAGE() {
		return AGECOVERAGE;
	}
	public void setAGECOVERAGE(String aGECOVERAGE) {
		AGECOVERAGE = aGECOVERAGE;
	}
	public String getPAYLIMIT() {
		return PAYLIMIT;
	}
	public void setPAYLIMIT(String pAYLIMIT) {
		PAYLIMIT = pAYLIMIT;
	}
	public String getCOMMISSIONRATIO() {
		return COMMISSIONRATIO;
	}
	public void setCOMMISSIONRATIO(String cOMMISSIONRATIO) {
		COMMISSIONRATIO = cOMMISSIONRATIO;
	}
	
}