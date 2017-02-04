package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultHisBaoDanContentBean implements IMouldType {
	private MouldList<ResultHisBaoDanItemBean> orderList;

	public MouldList<ResultHisBaoDanItemBean> getOrderList() {
		return orderList;
	}

	public void setOrderList(MouldList<ResultHisBaoDanItemBean> orderList) {
		this.orderList = orderList;
	}

}
