package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ScheduleDetailsBean implements IMouldType {
	private String scheduleId;
	private String token;

	public ScheduleDetailsBean(String scheduleId, String token) {
		this.scheduleId = scheduleId;
		this.token = token;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
