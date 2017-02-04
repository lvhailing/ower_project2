package com.cf360.bean;

import java.util.ArrayList;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultXinTuoItemContentBean implements IMouldType {
	
	private String flag;
	private ArrayList<String> commissionLst;
	private ArrayList<String> annualRateLst;
	private ArrayList<String> investmentFieldLst;
	private ArrayList<String> issuerLst;
	private String commission;
	private String annualRate;
	private String investmentField;
	private String issuer;
	private MouldList<ResultXinTuoListViewItem> trustList;
	private String auditStatus;
	
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public ArrayList<String> getCommissionLst() {
		return commissionLst;
	}
	public void setCommissionLst(ArrayList<String> commissionLst) {
		this.commissionLst = commissionLst;
	}
	public ArrayList<String> getAnnualRateLst() {
		return annualRateLst;
	}
	public void setAnnualRateLst(ArrayList<String> annualRateLst) {
		this.annualRateLst = annualRateLst;
	}
	public ArrayList<String> getInvestmentFieldLst() {
		return investmentFieldLst;
	}
	public void setInvestmentFieldLst(ArrayList<String> investmentFieldLst) {
		this.investmentFieldLst = investmentFieldLst;
	}
	public ArrayList<String> getIssuerLst() {
		return issuerLst;
	}
	public void setIssuerLst(ArrayList<String> issuerLst) {
		this.issuerLst = issuerLst;
	}
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	public String getAnnualRate() {
		return annualRate;
	}
	public void setAnnualRate(String annualRate) {
		this.annualRate = annualRate;
	}
	public String getInvestmentField() {
		return investmentField;
	}
	public void setInvestmentField(String investmentField) {
		this.investmentField = investmentField;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public MouldList<ResultXinTuoListViewItem> getTrustList() {
		return trustList;
	}
	public void setTrustList(MouldList<ResultXinTuoListViewItem> trustList) {
		this.trustList = trustList;
	}
	

}
