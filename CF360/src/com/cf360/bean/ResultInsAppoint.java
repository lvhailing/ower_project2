package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultInsAppoint implements IMouldType{
	MouldList<MouldList<ResultDeclarationSearchCommissionratioDataBean>> resultCommissionData;

	public MouldList<MouldList<ResultDeclarationSearchCommissionratioDataBean>> getResultCommissionData() {
		return resultCommissionData;
	}

	public void setResultCommissionData(
			MouldList<MouldList<ResultDeclarationSearchCommissionratioDataBean>> resultCommissionData) {
		this.resultCommissionData = resultCommissionData;
	}


	

}
