package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultIsInsuranceContentBean implements IMouldType {
	private String flag;
	private String insuranceAgentCode;
	private String isInsuranceAgentAudited;
	private String message;
	private String idNo;	//身份证号码
	private String insuranceAgentType;  //证书类型
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getInsuranceAgentType() {
		return insuranceAgentType;
	}

	public void setInsuranceAgentType(String insuranceAgentType) {
		this.insuranceAgentType = insuranceAgentType;
	}

	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getInsuranceAgentCode() {
		return insuranceAgentCode;
	}
	public void setInsuranceAgentCode(String insuranceAgentCode) {
		this.insuranceAgentCode = insuranceAgentCode;
	}
	public String getIsInsuranceAgentAudited() {
		return isInsuranceAgentAudited;
	}
	public void setIsInsuranceAgentAudited(String isInsuranceAgentAudited) {
		this.isInsuranceAgentAudited = isInsuranceAgentAudited;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
