package com.cf360.bean;

import java.util.ArrayList;
import java.util.List;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultInsuranceContentBean implements IMouldType{
	private ArrayList<String> companyShortNames;
	private ArrayList<String> insuranceCategorys;
	private MouldList<InsuranceListBean> productsList;
	private String auditStatus;
	
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public ArrayList<String> getCompanyShortNames() {
		return companyShortNames;
	}
	public void setCompanyShortNames(ArrayList<String> companyShortNames) {
		this.companyShortNames = companyShortNames;
	}
	public ArrayList<String> getInsuranceCategorys() {
		return insuranceCategorys;
	}
	public void setInsuranceCategorys(ArrayList<String> insuranceCategorys) {
		this.insuranceCategorys = insuranceCategorys;
	}
	public MouldList<InsuranceListBean> getProductsList() {
		return productsList;
	}
	public void setProductsList(MouldList<InsuranceListBean> productsList) {
		this.productsList = productsList;
	}

}
