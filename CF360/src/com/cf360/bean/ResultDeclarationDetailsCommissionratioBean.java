package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultDeclarationDetailsCommissionratioBean implements IMouldType {
	private String PRODUCTNAME;
	private String PAYLIMIT;
	private String AGECOVERAGE;
	private String COMMISSIONRATIO;
	
	public ResultDeclarationDetailsCommissionratioBean(String pRODUCTNAME,
			String pAYLIMIT, String aGECOVERAGE, String cOMMISSIONRATIO) {
		setAGECOVERAGE(aGECOVERAGE);
		setCOMMISSIONRATIO(cOMMISSIONRATIO);
		setPAYLIMIT(pAYLIMIT);
		setPRODUCTNAME(pRODUCTNAME);
	}
	public String getPRODUCTNAME() {
		return PRODUCTNAME;
	}
	public void setPRODUCTNAME(String pRODUCTNAME) {
		PRODUCTNAME = pRODUCTNAME;
	}
	public String getPAYLIMIT() {
		return PAYLIMIT;
	}
	public void setPAYLIMIT(String pAYLIMIT) {
		PAYLIMIT = pAYLIMIT;
	}
	public String getAGECOVERAGE() {
		return AGECOVERAGE;
	}
	public void setAGECOVERAGE(String aGECOVERAGE) {
		AGECOVERAGE = aGECOVERAGE;
	}
	public String getCOMMISSIONRATIO() {
		return COMMISSIONRATIO;
	}
	public void setCOMMISSIONRATIO(String cOMMISSIONRATIO) {
		COMMISSIONRATIO = cOMMISSIONRATIO;
	}
	
	
}