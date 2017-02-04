package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultContractListItemBean implements IMouldType {
	private String PRODUCTNAME;
	private String STATUS;
	private String CUSTOMERNAME;
	private String CONTRACTID;
	private String TYPE;
	private String NOTICEDESC;
	private String CREATETIME;
	private String CREATORNAME;
	private String NICKNAME;

	public ResultContractListItemBean(String nICKNAME,String pRODUCTNAME, String sTATUS,
			String cUSTOMERNAME, String cONTRACTID, String tYPE,
			String nOTICEDESC, String cREATETIME, String cREATORNAME) {
		setSTATUS(sTATUS);
		setCUSTOMERNAME(cUSTOMERNAME);
		setCONTRACTID(cONTRACTID);
		setTYPE(tYPE);
		setNOTICEDESC(nOTICEDESC);
		setCREATETIME(cREATETIME);
		setCREATORNAME(cREATORNAME);
		setNICKNAME(nICKNAME);
	}

	public String getNICKNAME() {
		return NICKNAME;
	}

	public void setNICKNAME(String nICKNAME) {
		NICKNAME = nICKNAME;
	}

	public String getNOTICEDESC() {
		return NOTICEDESC;
	}

	public void setNOTICEDESC(String nOTICEDESC) {
		NOTICEDESC = nOTICEDESC;
	}

	public String getCREATETIME() {
		return CREATETIME;
	}

	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}

	public String getCREATORNAME() {
		return CREATORNAME;
	}

	public void setCREATORNAME(String cREATORNAME) {
		CREATORNAME = cREATORNAME;
	}

	public String getPRODUCTNAME() {
		return PRODUCTNAME;
	}

	public void setPRODUCTNAME(String pRODUCTNAME) {
		PRODUCTNAME = pRODUCTNAME;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getCUSTOMERNAME() {
		return CUSTOMERNAME;
	}

	public void setCUSTOMERNAME(String cUSTOMERNAME) {
		CUSTOMERNAME = cUSTOMERNAME;
	}

	public String getCONTRACTID() {
		return CONTRACTID;
	}

	public void setCONTRACTID(String cONTRACTID) {
		CONTRACTID = cONTRACTID;
	}

	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}

}