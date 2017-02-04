package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultGenZongCustomerContentBean implements IMouldType {
	private MouldList<ResultGenZongCustomerContentItemBean> GenZongCustomerList;

	public MouldList<ResultGenZongCustomerContentItemBean> getGenZongCustomerList() {
		return GenZongCustomerList;
	}

	public void setGenZongCustomerList(
			MouldList<ResultGenZongCustomerContentItemBean> genZongCustomerList) {
		GenZongCustomerList = genZongCustomerList;
	}

	
}
