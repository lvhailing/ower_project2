package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultToubaodanTrackCallbackedListBean implements IMouldType {
	private String creatorName;
	private String noticeDesc;
	private String createTime;
	
	public ResultToubaodanTrackCallbackedListBean(String creatorName,
			String noticeDesc, String createTime) {
		setCreateTime(createTime);
		setCreatorName(creatorName);
		setNoticeDesc(noticeDesc);
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getNoticeDesc() {
		return noticeDesc;
	}

	public void setNoticeDesc(String noticeDesc) {
		this.noticeDesc = noticeDesc;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}