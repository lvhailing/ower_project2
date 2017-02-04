package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class SignUpTwoBean implements IMouldType {
	private String channelManagerPhone;
	private String mobile;
	private String nickName;
	private String password;

	public SignUpTwoBean() {
	}


	public SignUpTwoBean(String channelManagerPhone, String mobile,
			String nickName, String password) {
		super();
		setChannelManagerPhone(channelManagerPhone);
		setMobile(mobile);
		setNickName(nickName);
		setPassword(password);
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getChannelManagerPhone() {
		return channelManagerPhone;
	}

	public void setChannelManagerPhone(String channelManagerPhone) {
		this.channelManagerPhone = channelManagerPhone;
	}

}
