package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultAllCustomerContentBean implements IMouldType {
	private MouldList<ResultAllCustomerContentItemBean> userCustomerlist;

	public ResultAllCustomerContentBean(
			MouldList<ResultAllCustomerContentItemBean> userCustomerlist) {
		super();
		this.userCustomerlist = userCustomerlist;
	}

	public MouldList<ResultAllCustomerContentItemBean> getUserCustomerlist() {
		return userCustomerlist;
	}

	public void setUserCustomerlist(
			MouldList<ResultAllCustomerContentItemBean> userCustomerlist) {
		this.userCustomerlist = userCustomerlist;
	}

	
}
