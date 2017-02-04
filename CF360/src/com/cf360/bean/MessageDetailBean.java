package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class MessageDetailBean implements IMouldType {
	private String messageId;
	private String token;

	public MessageDetailBean(String messageId, String token) {
		this.messageId = messageId;
		this.token = token;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
