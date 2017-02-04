package com.cf360.bean;

import java.util.ArrayList;
import java.util.List;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultDeclarationDetailsListContentBean implements IMouldType {
	private MouldList<ResultDeclarationDetailsOrderBean> order;
	private MouldList<ResultDeclarationDetailsUserPaymentBean> userPayment;
	private String productTrackingNewHandeldesc;
	
	public ResultDeclarationDetailsListContentBean(
			MouldList<ResultDeclarationDetailsOrderBean> order,
			MouldList<ResultDeclarationDetailsUserPaymentBean> userPayment,String productTrackingNewHandeldesc ) {
		setOrder(order);
		setUserPayment(userPayment);
		setProductTrackingNewHandeldesc(productTrackingNewHandeldesc);
	}
	
	public String getProductTrackingNewHandeldesc() {
		return productTrackingNewHandeldesc;
	}

	public void setProductTrackingNewHandeldesc(String productTrackingNewHandeldesc) {
		this.productTrackingNewHandeldesc = productTrackingNewHandeldesc;
	}

	public MouldList<ResultDeclarationDetailsOrderBean> getOrder() {
		return order;
	}

	public void setOrder(MouldList<ResultDeclarationDetailsOrderBean> order) {
		this.order = order;
	}

	public MouldList<ResultDeclarationDetailsUserPaymentBean> getUserPayment() {
		return userPayment;
	}

	public void setUserPayment(
			MouldList<ResultDeclarationDetailsUserPaymentBean> userPayment) {
		this.userPayment = userPayment;
	}

	

}