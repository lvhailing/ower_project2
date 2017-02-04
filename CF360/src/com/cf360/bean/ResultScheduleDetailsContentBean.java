package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultScheduleDetailsContentBean implements IMouldType {
	private boolean flag;
	private String message;
	private ResultScheduleDetailsDataBean userCustomerSchedule;
	

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResultScheduleDetailsDataBean getUserCustomerSchedule() {
		return userCustomerSchedule;
	}

	public void setUserCustomerSchedule(
			ResultScheduleDetailsDataBean userCustomerSchedule) {
		this.userCustomerSchedule = userCustomerSchedule;
	}

}
