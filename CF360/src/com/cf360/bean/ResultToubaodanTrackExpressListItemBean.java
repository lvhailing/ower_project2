package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultToubaodanTrackExpressListItemBean implements IMouldType {

	private String EXPRESSNAME;// 寄出的快递公司
	private String EXPRESSCODE;// 寄出的运单号
	private String EXPRESSURL;// 寄出的快递地址
	private String EXPRESSNAMEBACK;// 寄回的快递公司
	private String EXPRESSCODEBACK;// 寄回的运单号
	private String EXPRESSURLBACK;// 寄回的快递地址

	public ResultToubaodanTrackExpressListItemBean(String eXPRESSNAME,
			String eXPRESSCODE, String eXPRESSURL, String eXPRESSNAMEBACK,
			String eXPRESSCODEBACK, String eXPRESSURLBACK) {
		setEXPRESSCODE(eXPRESSCODEBACK);
		setEXPRESSCODEBACK(eXPRESSCODEBACK);
		setEXPRESSNAME(eXPRESSNAME);
		setEXPRESSNAMEBACK(eXPRESSNAMEBACK);
		setEXPRESSURL(eXPRESSURLBACK);
		setEXPRESSURLBACK(eXPRESSURLBACK);
	}

	public String getEXPRESSNAME() {
		return EXPRESSNAME;
	}

	public void setEXPRESSNAME(String eXPRESSNAME) {
		EXPRESSNAME = eXPRESSNAME;
	}

	public String getEXPRESSCODE() {
		return EXPRESSCODE;
	}

	public void setEXPRESSCODE(String eXPRESSCODE) {
		EXPRESSCODE = eXPRESSCODE;
	}

	public String getEXPRESSURL() {
		return EXPRESSURL;
	}

	public void setEXPRESSURL(String eXPRESSURL) {
		EXPRESSURL = eXPRESSURL;
	}

	public String getEXPRESSNAMEBACK() {
		return EXPRESSNAMEBACK;
	}

	public void setEXPRESSNAMEBACK(String eXPRESSNAMEBACK) {
		EXPRESSNAMEBACK = eXPRESSNAMEBACK;
	}

	public String getEXPRESSCODEBACK() {
		return EXPRESSCODEBACK;
	}

	public void setEXPRESSCODEBACK(String eXPRESSCODEBACK) {
		EXPRESSCODEBACK = eXPRESSCODEBACK;
	}

	public String getEXPRESSURLBACK() {
		return EXPRESSURLBACK;
	}

	public void setEXPRESSURLBACK(String eXPRESSURLBACK) {
		EXPRESSURLBACK = eXPRESSURLBACK;
	}

}