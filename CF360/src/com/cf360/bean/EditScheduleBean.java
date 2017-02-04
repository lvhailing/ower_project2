package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class EditScheduleBean implements IMouldType {
	private String id;
	private String customerName;
	private String customerId;
	private String type;
	private String topic;
	private String status;
	private String endTime;
	private String startTime;
	private String scheduleAmind;
	private String amindTime;
	private String scheduleDesc;
	private String token;

	public EditScheduleBean(String id, String customerName, String customerId, String type, String topic, String status, String endTime, String startTime, String scheduleAmind, String amindTime, String scheduleDesc, String token) {
		this.id = id;
		this.customerName = customerName;
		this.customerId = customerId;
		this.type = type;
		this.topic = topic;
		this.status = status;
		this.endTime = endTime;
		this.startTime = startTime;
		this.scheduleAmind = scheduleAmind;
		this.amindTime = amindTime;
		this.scheduleDesc = scheduleDesc;
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getScheduleAmind() {
		return scheduleAmind;
	}

	public void setScheduleAmind(String scheduleAmind) {
		this.scheduleAmind = scheduleAmind;
	}

	public String getAmindTime() {
		return amindTime;
	}

	public void setAmindTime(String amindTime) {
		this.amindTime = amindTime;
	}

	public String getScheduleDesc() {
		return scheduleDesc;
	}

	public void setScheduleDesc(String scheduleDesc) {
		this.scheduleDesc = scheduleDesc;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
