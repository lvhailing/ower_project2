package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultUserLoginContentBean implements IMouldType {

	private String flag;
	private String message;
	private String userId;
	private String nickName;
	private String phone;
	private String type;
	private String realName;
	// public UserLoginResultBean(String flag, String message){
	// setFlag(flag);
	// setMessage(message);
	// }
	// {flag=true, message=, nickName=aaaasw, userId=14120415074007298439}
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ResultUserLoginContentBean(String flag, String userId, String message, String nickName, String phone, String type, String realName, String token) {
		this.flag = flag;
		this.userId = userId;
		this.message = message;
		this.nickName = nickName;
		this.phone = phone;
		this.type = type;
		this.realName = realName;
		this.token = token;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	

}