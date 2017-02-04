package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultApplyContractContentBean implements IMouldType {
	private String flag;
	private String message;
	private String contractId;
	private String productName;
	
	public ResultApplyContractContentBean(String flag, String message,
			String contractId, String productName) {
		super();
		this.flag = flag;
		this.message = message;
		this.contractId = contractId;
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
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

}
