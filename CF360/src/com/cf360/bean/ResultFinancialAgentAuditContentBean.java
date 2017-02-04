package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultFinancialAgentAuditContentBean implements IMouldType {
	private String orgInfo;	//机构信息实体类
	private String orgName;	//机构名称
	private String legalPersonName;	//法人名称
	private String regionaProvince;	//所在城市地点（省）
	private String regionaCity;	//所在城市地点（市）
	private String moreAddress;	//地址详情（联系地址）
	private String orgInServerUrl;	//图片服务器地址
	private String busiCodePath;	//营业执照附件的path
	private String busiCodeName;	//营业执照附件的名称
	private String registrationCodePath;	//税务登记证附件的path
	private String registrationCodeName;	//税务登记证附件的名称
	private String inStuCodePath;	//组织机构代码附件的path
	private String inStuCodeName;	//组织机构代码附件的名称
	private String bankAccountLicenceName;	//银行开户许可证附件的path
	private String bankAccountLicencePath;	//银行开户许可证附件的名称
	private String bankCreditCodePath;	//银行信用代码证附件的path
	private String bankCreditCodeName;	//银行信用代码证附件的名称
	private String flag;	//true/false 成功或失败
	private String message;	//结果信息说明
	public String getOrgInfo() {
		return orgInfo;
	}
	public void setOrgInfo(String orgInfo) {
		this.orgInfo = orgInfo;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getLegalPersonName() {
		return legalPersonName;
	}
	public void setLegalPersonName(String legalPersonName) {
		this.legalPersonName = legalPersonName;
	}
	public String getRegionaProvince() {
		return regionaProvince;
	}
	public void setRegionaProvince(String regionaProvince) {
		this.regionaProvince = regionaProvince;
	}
	public String getRegionaCity() {
		return regionaCity;
	}
	public void setRegionaCity(String regionaCity) {
		this.regionaCity = regionaCity;
	}
	public String getMoreAddress() {
		return moreAddress;
	}
	public void setMoreAddress(String moreAddress) {
		this.moreAddress = moreAddress;
	}
	public String getOrgInServerUrl() {
		return orgInServerUrl;
	}
	public void setOrgInServerUrl(String orgInServerUrl) {
		this.orgInServerUrl = orgInServerUrl;
	}
	public String getBusiCodePath() {
		return busiCodePath;
	}
	public void setBusiCodePath(String busiCodePath) {
		this.busiCodePath = busiCodePath;
	}
	public String getBusiCodeName() {
		return busiCodeName;
	}
	public void setBusiCodeName(String busiCodeName) {
		this.busiCodeName = busiCodeName;
	}
	public String getRegistrationCodePath() {
		return registrationCodePath;
	}
	public void setRegistrationCodePath(String registrationCodePath) {
		this.registrationCodePath = registrationCodePath;
	}
	public String getRegistrationCodeName() {
		return registrationCodeName;
	}
	public void setRegistrationCodeName(String registrationCodeName) {
		this.registrationCodeName = registrationCodeName;
	}
	public String getInStuCodePath() {
		return inStuCodePath;
	}
	public void setInStuCodePath(String inStuCodePath) {
		this.inStuCodePath = inStuCodePath;
	}
	public String getInStuCodeName() {
		return inStuCodeName;
	}
	public void setInStuCodeName(String inStuCodeName) {
		this.inStuCodeName = inStuCodeName;
	}
	public String getBankAccountLicenceName() {
		return bankAccountLicenceName;
	}
	public void setBankAccountLicenceName(String bankAccountLicenceName) {
		this.bankAccountLicenceName = bankAccountLicenceName;
	}
	public String getBankAccountLicencePath() {
		return bankAccountLicencePath;
	}
	public void setBankAccountLicencePath(String bankAccountLicencePath) {
		this.bankAccountLicencePath = bankAccountLicencePath;
	}
	public String getBankCreditCodePath() {
		return bankCreditCodePath;
	}
	public void setBankCreditCodePath(String bankCreditCodePath) {
		this.bankCreditCodePath = bankCreditCodePath;
	}
	public String getBankCreditCodeName() {
		return bankCreditCodeName;
	}
	public void setBankCreditCodeName(String bankCreditCodeName) {
		this.bankCreditCodeName = bankCreditCodeName;
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
	
	
}
