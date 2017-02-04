package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultWithdrawContentBean implements IMouldType {
	private String availableBalance;
	private String frozenBalance;
	private String investTotalAmount;
	
	 private MouldList<BankCardBean> bankCards;
	 private String openAcctId;

	public String getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getFrozenBalance() {
		return frozenBalance;
	}

	public void setFrozenBalance(String frozenBalance) {
		this.frozenBalance = frozenBalance;
	}

	public String getInvestTotalAmount() {
		return investTotalAmount;
	}

	public void setInvestTotalAmount(String investTotalAmount) {
		this.investTotalAmount = investTotalAmount;
	}

	
	
	 public MouldList<BankCardBean> getBankCards() {
		return bankCards;
	}

	public void setBankCards(MouldList<BankCardBean> bankCards) {
		this.bankCards = bankCards;
	}

	public String getOpenAcctId() {
	 return openAcctId;
	 }
	
	 public void setOpenAcctId(String openAcctId) {
	 this.openAcctId = openAcctId;
	 }

}
