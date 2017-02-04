package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class AttentionContentBean implements IMouldType{
	private String photosAttachmentsName;
	private String photosAttachmentsPath;
	private MouldList<InsuranceDetailsListBean> productsList;
	public String getPhotosAttachmentsName() {
		return photosAttachmentsName;
	}
	public void setPhotosAttachmentsName(String photosAttachmentsName) {
		this.photosAttachmentsName = photosAttachmentsName;
	}
	public String getPhotosAttachmentsPath() {
		return photosAttachmentsPath;
	}
	public void setPhotosAttachmentsPath(String photosAttachmentsPath) {
		this.photosAttachmentsPath = photosAttachmentsPath;
	}
	public MouldList<InsuranceDetailsListBean> getProductsList() {
		return productsList;
	}
	public void setProductsList(MouldList<InsuranceDetailsListBean> productsList) {
		this.productsList = productsList;
	}
	
	

}
