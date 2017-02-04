package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultUserInfoContentBean implements IMouldType {
	
	private String openAccountStatus;		//开户状态 true/false
	private String availableBalance;		//账户余额
	private String isFirstInvest;			//是否第一次投资true/false
	private String isTendered;				//是否投资此产品true/false
	private String initAmount;				//起投金额
	private String increaseAmount;			//递增金额
	private String productStatus;			//init：初始状态 tender：融资中 fail：融资失败 success：融资成功 repaying: 还款中 repayed：已还清 prepayed 提前还款结清 running：运行中
	private String tenderStart;				//产品开标时间
	
	private String highSingleInvest;		//最高单笔
	private String ifLimitSingle;			//是否限制最高单笔（yes/no）
	private String ifLimitCount;			//是否限制最高笔数（yes/no）
	private String highInvestCount;			//最高笔数
	private String investCount;				//已投笔数
	
	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getTenderStart() {
		return tenderStart;
	}

	public void setTenderStart(String tenderStart) {
		this.tenderStart = tenderStart;
	}

	public String getInitAmount() {
		return initAmount;
	}

	public void setInitAmount(String initAmount) {
		this.initAmount = initAmount;
	}

	public String getIncreaseAmount() {
		return increaseAmount;
	}

	public void setIncreaseAmount(String increaseAmount) {
		this.increaseAmount = increaseAmount;
	}

	public String getOpenAccountStatus() {
		return openAccountStatus;
	}

	public void setOpenAccountStatus(String openAccountStatus) {
		this.openAccountStatus = openAccountStatus;
	}

	public String getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getIsFirstInvest() {
		return isFirstInvest;
	}

	public void setIsFirstInvest(String isFirstInvest) {
		this.isFirstInvest = isFirstInvest;
	}

	public String getIsTendered() {
		return isTendered;
	}

	public void setIsTendered(String isTendered) {
		this.isTendered = isTendered;
	}

	public String getHighSingleInvest() {
		return highSingleInvest;
	}

	public void setHighSingleInvest(String highSingleInvest) {
		this.highSingleInvest = highSingleInvest;
	}

	public String getIfLimitSingle() {
		return ifLimitSingle;
	}

	public void setIfLimitSingle(String ifLimitSingle) {
		this.ifLimitSingle = ifLimitSingle;
	}

	public String getIfLimitCount() {
		return ifLimitCount;
	}

	public void setIfLimitCount(String ifLimitCount) {
		this.ifLimitCount = ifLimitCount;
	}

	public String getHighInvestCount() {
		return highInvestCount;
	}

	public void setHighInvestCount(String highInvestCount) {
		this.highInvestCount = highInvestCount;
	}

	public String getInvestCount() {
		return investCount;
	}

	public void setInvestCount(String investCount) {
		this.investCount = investCount;
	}
	
}