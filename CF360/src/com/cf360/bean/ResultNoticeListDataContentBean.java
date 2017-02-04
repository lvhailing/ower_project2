package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultNoticeListDataContentBean implements IMouldType {
	
	private String page;
	private MouldList<NoticeListDataContentBean> bulletin;
	private String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public MouldList<NoticeListDataContentBean> getBulletin() {
		return bulletin;
	}
	public void setBulletin(MouldList<NoticeListDataContentBean> bulletin) {
		this.bulletin = bulletin;
	}
	
	
}
