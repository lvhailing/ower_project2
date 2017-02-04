package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultMessageListDataContentBean implements IMouldType {
	
	private String count;
	private String flag;
	private String message;
	private MouldList<MessageListDataContentBean> messageList;
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
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
	public MouldList<MessageListDataContentBean> getMessageList() {
		return messageList;
	}
	public void setMessageList(MouldList<MessageListDataContentBean> messageList) {
		this.messageList = messageList;
	}
	
	
	
}
