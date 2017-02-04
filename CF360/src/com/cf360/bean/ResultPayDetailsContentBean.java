package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultPayDetailsContentBean implements IMouldType{
/*	"attentionStatus": "1",
    "flag": "true",
    "message": "更改已关注状态成功！"*/
	private String attentionStatus;
	private String flag;
	private String message;
	public String getAttentionStatus() {
		return attentionStatus;
	}
	public void setAttentionStatus(String attentionStatus) {
		this.attentionStatus = attentionStatus;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
