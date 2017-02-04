package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultApplyDeclarationContentBean implements IMouldType {
	private String flag;
	private String message;
	private String orderId;
	private String productName;
	
	public ResultApplyDeclarationContentBean(String flag, String message,
			String orderId, String productName) {
		super();
		this.flag = flag;
		this.message = message;
		this.orderId = orderId;
		this.productName = productName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
