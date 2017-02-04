package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultGenZongCustomerContentItemBean implements IMouldType {
	private String topic;
	private String customerName;
	private String startTime;
	private String endTime;
	private String id;
	
	
	public ResultGenZongCustomerContentItemBean(String topic,
			String customerName, String startTime, String endTime, String id) {
		super();
		this.topic = topic;
		this.customerName = customerName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
