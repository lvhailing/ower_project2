package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultApplyToubaodanContentBean implements IMouldType {
	private String flag;
	private String message;
	private String policyId;
	private String productName;
	
	public ResultApplyToubaodanContentBean(String flag, String message,
			String policyId, String productName) {
		super();
		this.flag = flag;
		this.message = message;
		this.policyId = policyId;
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
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

}
