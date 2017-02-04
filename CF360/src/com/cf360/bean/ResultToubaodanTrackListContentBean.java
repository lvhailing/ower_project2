package com.cf360.bean;

import java.util.ArrayList;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultToubaodanTrackListContentBean implements IMouldType {
	private MouldList<ResultToubaodanTrackExpressListItemBean> expressInfoList;
	private MouldList<ResultToubaodanTrackListItemBean> insurancePolicyList;
	private MouldList<ResultToubaodanTrackInitListBean> initList;
	private MouldList<ResultToubaodanTrackAuditListBean> auditList;
	private MouldList<ResultToubaodanTrackUnsignListBean> unsignList;
	private MouldList<ResultToubaodanTrackUncallbackListBean> uncallbackList;
	private MouldList<ResultToubaodanTrackUnreturnListBean> unreturnList;
	private MouldList<ResultToubaodanTrackCallbackedListBean> callbackedList;
	private ArrayList<String> reciveMsg;
	private ArrayList<String> reciveMsg2;
	
	public ResultToubaodanTrackListContentBean(
			MouldList<ResultToubaodanTrackExpressListItemBean> expressInfoList,
			MouldList<ResultToubaodanTrackListItemBean> insurancePolicyList,
			MouldList<ResultToubaodanTrackInitListBean> initList,
			MouldList<ResultToubaodanTrackAuditListBean> auditList,
			ArrayList<String> reciveMsg,
			ArrayList<String> reciveMsg2,
			MouldList<ResultToubaodanTrackUnsignListBean> unsignList,
			MouldList<ResultToubaodanTrackUncallbackListBean> uncallbackList,
			MouldList<ResultToubaodanTrackUnreturnListBean> unreturnList,
			MouldList<ResultToubaodanTrackCallbackedListBean> callbackedList) {
		setExpressInfoList(expressInfoList);
		setInsurancePolicyList(insurancePolicyList);
		setInitList(initList);
		setReciveMsg(reciveMsg);
		setReciveMsg2(reciveMsg2);
		setAuditList(auditList);
		setUnsignList(unsignList);
		setUncallbackList(uncallbackList);
		setUnreturnList(unreturnList);
		setCallbackedList(callbackedList);
	}
	
	public MouldList<ResultToubaodanTrackExpressListItemBean> getExpressInfoList() {
		return expressInfoList;
	}

	public void setExpressInfoList(
			MouldList<ResultToubaodanTrackExpressListItemBean> expressInfoList) {
		this.expressInfoList = expressInfoList;
	}

	public MouldList<ResultToubaodanTrackListItemBean> getInsurancePolicyList() {
		return insurancePolicyList;
	}

	public void setInsurancePolicyList(
			MouldList<ResultToubaodanTrackListItemBean> insurancePolicyList) {
		this.insurancePolicyList = insurancePolicyList;
	}

	public MouldList<ResultToubaodanTrackInitListBean> getInitList() {
		return initList;
	}
	public void setInitList(MouldList<ResultToubaodanTrackInitListBean> initList) {
		this.initList = initList;
	}
	public MouldList<ResultToubaodanTrackAuditListBean> getAuditList() {
		return auditList;
	}
	public void setAuditList(MouldList<ResultToubaodanTrackAuditListBean> auditList) {
		this.auditList = auditList;
	}
	public MouldList<ResultToubaodanTrackUnsignListBean> getUnsignList() {
		return unsignList;
	}
	public void setUnsignList(
			MouldList<ResultToubaodanTrackUnsignListBean> unsignList) {
		this.unsignList = unsignList;
	}
	public MouldList<ResultToubaodanTrackUncallbackListBean> getUncallbackList() {
		return uncallbackList;
	}
	public void setUncallbackList(
			MouldList<ResultToubaodanTrackUncallbackListBean> uncallbackList) {
		this.uncallbackList = uncallbackList;
	}
	public MouldList<ResultToubaodanTrackUnreturnListBean> getUnreturnList() {
		return unreturnList;
	}
	public void setUnreturnList(
			MouldList<ResultToubaodanTrackUnreturnListBean> unreturnList) {
		this.unreturnList = unreturnList;
	}
	public MouldList<ResultToubaodanTrackCallbackedListBean> getCallbackedList() {
		return callbackedList;
	}
	public void setCallbackedList(
			MouldList<ResultToubaodanTrackCallbackedListBean> callbackedList) {
		this.callbackedList = callbackedList;
	}

	public ArrayList<String> getReciveMsg() {
		return reciveMsg;
	}

	public void setReciveMsg(ArrayList<String> reciveMsg) {
		this.reciveMsg = reciveMsg;
	}

	public ArrayList<String> getReciveMsg2() {
		return reciveMsg2;
	}

	public void setReciveMsg2(ArrayList<String> reciveMsg2) {
		this.reciveMsg2 = reciveMsg2;
	}

}