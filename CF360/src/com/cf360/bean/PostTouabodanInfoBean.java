package com.cf360.bean;

import com.cf360.mould.types.IMouldType;


public class PostTouabodanInfoBean implements IMouldType {
	private String receiveInfo;
	private boolean isChecked;
	
	public PostTouabodanInfoBean() {
		super();
	}
	public String getReceiveInfo() {
		return receiveInfo;
	}
	public void setReceiveInfo(String receiveInfo) {
		this.receiveInfo = receiveInfo;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	
}
