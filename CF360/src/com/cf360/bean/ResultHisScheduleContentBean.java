package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultHisScheduleContentBean implements IMouldType {
	private MouldList<ResultHisScheduleItemBean> userCustomerScheduleList;

	public MouldList<ResultHisScheduleItemBean> getUserCustomerScheduleList() {
		return userCustomerScheduleList;
	}

	public void setUserCustomerScheduleList(
			MouldList<ResultHisScheduleItemBean> userCustomerScheduleList) {
		this.userCustomerScheduleList = userCustomerScheduleList;
	}

}
