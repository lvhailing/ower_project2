package com.cf360.bean;

import java.util.List;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class InfoDataDetailContentBean implements IMouldType {
	private String ID;		//集合id
	private String PRODUCTID;		//产品id
	private String PRODUCTNAME;		//产品名称
	private String PRODUCTNAME2;		//产品名称
	private String CUSTOMERNAME;		//客户姓名
	private String CUSTOMERPHONE;		//客户手机号
	private String TYPE;		//收支类型（in，activity，out）对应收入，活动，支出
	private String AMOUNT;		//打款金额
	private String ANNUALAMOUNT;		//收益金额
	private String STATUS;		//收支状态handling返佣中，success已完成
	private String CREATETIME;		//创建时间
//	private String PROPORTION;	//返佣比例					已废弃参数，后台修改为list类型
	private String COMMISSION;	//返佣方式
	private String ORDERID;
	private String ORDERDATE;
	
	private String CARDNAME;
	private String USERNAME;
	private String USERPHONE;
	private List REBATEPROPORTION2;
	private List AMOUNT2;
	private String EXCHANGE_RATE;		//汇率
	
	private String REMARK;		//活动类型 备注名称
	
	private String CARD_NUMBER;  //提现客户银行卡号
	
	public String getCARD_NUMBER() {
		return CARD_NUMBER;
	}
	public void setCARD_NUMBER(String cARD_NUMBER) {
		CARD_NUMBER = cARD_NUMBER;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getEXCHANGE_RATE() {
		return EXCHANGE_RATE;
	}
	public void setEXCHANGE_RATE(String eXCHANGE_RATE) {
		EXCHANGE_RATE = eXCHANGE_RATE;
	}
	public List getREBATEPROPORTION2() {
		return REBATEPROPORTION2;
	}
	public void setREBATEPROPORTION2(List rEBATEPROPORTION2) {
		REBATEPROPORTION2 = rEBATEPROPORTION2;
	}
	public List getAMOUNT2() {
		return AMOUNT2;
	}
	public void setAMOUNT2(List aMOUNT2) {
		AMOUNT2 = aMOUNT2;
	}
	public String getCARDNAME() {
		return CARDNAME;
	}
	public void setCARDNAME(String cARDNAME) {
		CARDNAME = cARDNAME;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public String getUSERPHONE() {
		return USERPHONE;
	}
	public void setUSERPHONE(String uSERPHONE) {
		USERPHONE = uSERPHONE;
	}
	public String getPRODUCTNAME2() {
		return PRODUCTNAME2;
	}
	public void setPRODUCTNAME2(String pRODUCTNAME2) {
		PRODUCTNAME2 = pRODUCTNAME2;
	}
	public String getORDERID() {
		return ORDERID;
	}
	public void setORDERID(String oRDERID) {
		ORDERID = oRDERID;
	}
	public String getORDERDATE() {
		return ORDERDATE;
	}
	public void setORDERDATE(String oRDERDATE) {
		ORDERDATE = oRDERDATE;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getPRODUCTID() {
		return PRODUCTID;
	}
	public void setPRODUCTID(String pRODUCTID) {
		PRODUCTID = pRODUCTID;
	}
	public String getPRODUCTNAME() {
		return PRODUCTNAME;
	}
	public void setPRODUCTNAME(String pRODUCTNAME) {
		PRODUCTNAME = pRODUCTNAME;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getCUSTOMERNAME() {
		return CUSTOMERNAME;
	}
	public void setCUSTOMERNAME(String cUSTOMERNAME) {
		CUSTOMERNAME = cUSTOMERNAME;
	}
	public String getCUSTOMERPHONE() {
		return CUSTOMERPHONE;
	}
	public void setCUSTOMERPHONE(String cUSTOMERPHONE) {
		CUSTOMERPHONE = cUSTOMERPHONE;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getANNUALAMOUNT() {
		return ANNUALAMOUNT;
	}
	public void setANNUALAMOUNT(String aNNUALAMOUNT) {
		ANNUALAMOUNT = aNNUALAMOUNT;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}
	
//	public String getPROPORTION() {
//		return PROPORTION;
//	}
//	public void setPROPORTION(String pROPORTION) {
//		PROPORTION = pROPORTION;
//	}
	
	public String getCOMMISSION() {
		return COMMISSION;
	}
	public void setCOMMISSION(String cOMMISSION) {
		COMMISSION = cOMMISSION;
	}
	
}
