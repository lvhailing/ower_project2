package com.cf360.bean;

import java.util.ArrayList;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultOrderDetailListDataBean implements IMouldType {
	private MouldList<ResultOrderDetailContentBean> appoitment;
	private ArrayList<String> deputyList;

	public MouldList<ResultOrderDetailContentBean> getAppoitment() {
		return appoitment;
	}
	public void setAppoitment(MouldList<ResultOrderDetailContentBean> appoitment) {
		this.appoitment = appoitment;
	}
	public ArrayList<String> getDeputyList() {
		return deputyList;
	}
	public void setDeputyList(ArrayList<String> deputyList) {
		this.deputyList = deputyList;
	}
	

}
