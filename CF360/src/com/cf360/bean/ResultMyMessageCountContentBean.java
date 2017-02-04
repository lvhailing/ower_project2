package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultMyMessageCountContentBean implements IMouldType {
	private String unReadyCount;

	public String getUnReadyCount() {
		return unReadyCount;
	}

	public void setUnReadyCount(String unReadyCount) {
		this.unReadyCount = unReadyCount;
	}
	
}
