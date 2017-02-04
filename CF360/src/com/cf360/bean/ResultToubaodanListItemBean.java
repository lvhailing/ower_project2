package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultToubaodanListItemBean implements IMouldType {
	private String PRODUCTNAME;
	private String STATUS;
	private String CUSTOMERNAME;
	private String POLICYORDERID;
	private String NOTICEDESC;
	private String CREATETIME;
	private String CREATORNAME;

	public ResultToubaodanListItemBean(String pRODUCTNAME, String sTATUS,
			String cUSTOMERNAME, String pOLICYORDERID,
			String nOTICEDESC, String cREATETIME, String cREATORNAME) {
		setSTATUS(sTATUS);
		setCUSTOMERNAME(cUSTOMERNAME);
		setNOTICEDESC(nOTICEDESC);
		setPOLICYORDERID(pOLICYORDERID);
		setCREATETIME(cREATETIME);
		setCREATORNAME(cREATORNAME);
	}

	public String getPOLICYORDERID() {
		return POLICYORDERID;
	}

	public void setPOLICYORDERID(String pOLICYORDERID) {
		POLICYORDERID = pOLICYORDERID;
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

}