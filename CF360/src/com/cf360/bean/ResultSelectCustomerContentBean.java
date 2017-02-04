package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultSelectCustomerContentBean implements IMouldType {
	private MouldList<ResultSelectCustomerContentItemBean> userCustomerList;

	public ResultSelectCustomerContentBean(
			MouldList<ResultSelectCustomerContentItemBean> userCustomerList) {
		super();
		this.userCustomerList = userCustomerList;
	}

	public MouldList<ResultSelectCustomerContentItemBean> getUserCustomerList() {
		return userCustomerList;
	}

	public void setUserCustomerList(
			MouldList<ResultSelectCustomerContentItemBean> userCustomerList) {
		this.userCustomerList = userCustomerList;
	}
	
}
