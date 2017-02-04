package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultEditDeclarationDeputyBean implements IMouldType {
	private String AGECOVERAGE;
	private String LIMITE;
	
	public ResultEditDeclarationDeputyBean(String aGECOVERAGE, String lIMITE) {
		super();
		setAGECOVERAGE(aGECOVERAGE);
		setLIMITE(lIMITE);
	}
	public String getAGECOVERAGE() {
		return AGECOVERAGE;
	}
	public void setAGECOVERAGE(String aGECOVERAGE) {
		AGECOVERAGE = aGECOVERAGE;
	}
	public String getLIMITE() {
		return LIMITE;
	}
	public void setLIMITE(String lIMITE) {
		LIMITE = lIMITE;
	}
	
	
}