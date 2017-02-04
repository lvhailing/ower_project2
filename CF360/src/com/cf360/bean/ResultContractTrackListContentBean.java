package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultContractTrackListContentBean implements IMouldType {
	private MouldList<ResultContractTrackListItemBean> contractList;
	private MouldList<ResultContractTrackInitListBean> initList;
	private MouldList<ResultContractTrackAuditListBean> auditList;
	private MouldList<ResultContractTrackUnsignListBean> unsignList;
	private MouldList<ResultContractTrackUncallbackListBean> uncallbackList;
	private MouldList<ResultContractTrackUnreturnListBean> unreturnList;
	private MouldList<ResultContractTrackCallbackedListBean> callbackedList;
	
	public ResultContractTrackListContentBean(
			MouldList<ResultContractTrackListItemBean> contractList,
			MouldList<ResultContractTrackInitListBean> initList,
			MouldList<ResultContractTrackAuditListBean> auditList,
			MouldList<ResultContractTrackUnsignListBean> unsignList,
			MouldList<ResultContractTrackUncallbackListBean> uncallbackList,
			MouldList<ResultContractTrackUnreturnListBean> unreturnList,
			MouldList<ResultContractTrackCallbackedListBean> callbackedList) {
		setInitList(initList);
		setAuditList(auditList);
		setUnsignList(unsignList);
		setUncallbackList(uncallbackList);
		setUnreturnList(unreturnList);
		setCallbackedList(callbackedList);
	}
	public MouldList<ResultContractTrackListItemBean> getContractList() {
		return contractList;
	}
	public void setContractList(
			MouldList<ResultContractTrackListItemBean> contractList) {
		this.contractList = contractList;
	}
	public MouldList<ResultContractTrackInitListBean> getInitList() {
		return initList;
	}
	public void setInitList(MouldList<ResultContractTrackInitListBean> initList) {
		this.initList = initList;
	}
	public MouldList<ResultContractTrackAuditListBean> getAuditList() {
		return auditList;
	}
	public void setAuditList(MouldList<ResultContractTrackAuditListBean> auditList) {
		this.auditList = auditList;
	}
	public MouldList<ResultContractTrackUnsignListBean> getUnsignList() {
		return unsignList;
	}
	public void setUnsignList(
			MouldList<ResultContractTrackUnsignListBean> unsignList) {
		this.unsignList = unsignList;
	}
	public MouldList<ResultContractTrackUncallbackListBean> getUncallbackList() {
		return uncallbackList;
	}
	public void setUncallbackList(
			MouldList<ResultContractTrackUncallbackListBean> uncallbackList) {
		this.uncallbackList = uncallbackList;
	}
	public MouldList<ResultContractTrackUnreturnListBean> getUnreturnList() {
		return unreturnList;
	}
	public void setUnreturnList(
			MouldList<ResultContractTrackUnreturnListBean> unreturnList) {
		this.unreturnList = unreturnList;
	}
	public MouldList<ResultContractTrackCallbackedListBean> getCallbackedList() {
		return callbackedList;
	}
	public void setCallbackedList(
			MouldList<ResultContractTrackCallbackedListBean> callbackedList) {
		this.callbackedList = callbackedList;
	}

}