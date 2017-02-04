package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class FinancialPictureBean implements IMouldType{
	private String title;
	private String img;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
}
