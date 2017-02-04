package com.cf360.event;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * 自定义键盘事件
 * 
 * @author shaxiaoning
 *
 */
public class KeyboardEvent {
	
	private KeyboardEvent() {

	}

	/**
	 * 隐藏键盘
	 * @param mContext
	 */
	public static void hideSoftInput(Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm.isActive())
		imm.hideSoftInputFromWindow(((Activity) mContext).getCurrentFocus()
				.getWindowToken(), 0);
	}
	/**
	 * 显示键盘
	 * 
	 * @param mContext
	 */
	public static void showSoftInput(Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInputFromInputMethod(((Activity) mContext).getCurrentFocus()
				.getWindowToken(), 0);
	}
}
