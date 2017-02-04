package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultSelectProductListContentBean implements IMouldType {
	private MouldList<ResultSelectProductAllBean> productList;
	private MouldList<ResultSelectProductOrderBean> appProductList;
	
	public ResultSelectProductListContentBean(
			MouldList<ResultSelectProductAllBean> productList,
			MouldList<ResultSelectProductOrderBean> appProductList) {
		setAppProductList(appProductList);
		setProductList(productList);
	}
	public MouldList<ResultSelectProductAllBean> getProductList() {
		return productList;
	}
	public void setProductList(MouldList<ResultSelectProductAllBean> productList) {
		this.productList = productList;
	}
	public MouldList<ResultSelectProductOrderBean> getAppProductList() {
		return appProductList;
	}
	public void setAppProductList(
			MouldList<ResultSelectProductOrderBean> appProductList) {
		this.appProductList = appProductList;
	}

}