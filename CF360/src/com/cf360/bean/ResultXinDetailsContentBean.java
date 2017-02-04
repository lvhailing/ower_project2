package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultXinDetailsContentBean  implements IMouldType{
	private String attentionStatus;
	private MouldList<XinTuoDetailscommissionList> commissionList;
	private XinTuoDetailsproductTrust productTrust;
	private int minAmount;
	private String auditStatus;
	
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getAttentionStatus() {
		return attentionStatus;
	}
	public void setAttentionStatus(String attentionStatus) {
		this.attentionStatus = attentionStatus;
	}
	public MouldList<XinTuoDetailscommissionList> getCommissionList() {
		return commissionList;
	}
	public void setCommissionList(
			MouldList<XinTuoDetailscommissionList> commissionList) {
		this.commissionList = commissionList;
	}
	
	public int getMinAmount() {
		return minAmount;
	}
	public XinTuoDetailsproductTrust getProductTrust() {
		return productTrust;
	}
	public void setProductTrust(XinTuoDetailsproductTrust productTrust) {
		this.productTrust = productTrust;
	}
	public void setMinAmount(int minAmount) {
		this.minAmount = minAmount;
	}
	


}
