package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultInfoDataContentBean implements IMouldType {
	private MouldList<InfoDataContentBean> incomeStatementList;

	public MouldList<InfoDataContentBean> getIncomeStatementList() {
		return incomeStatementList;
	}

	public void setIncomeStatementList(
			MouldList<InfoDataContentBean> incomeStatementList) {
		this.incomeStatementList = incomeStatementList;
	}
	
	
}
