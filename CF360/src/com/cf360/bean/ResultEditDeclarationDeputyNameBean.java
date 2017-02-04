package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultEditDeclarationDeputyNameBean implements IMouldType {
	private String ID;
	private String NAME;
	
	public ResultEditDeclarationDeputyNameBean(String iD, String nAME) {
		super();
		setID(iD);
		setNAME(nAME);
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	
	
}