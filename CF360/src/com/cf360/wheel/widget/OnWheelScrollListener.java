package com.cf360.wheel.widget;

public interface OnWheelScrollListener {
	/**
	 * Callback method to be invoked when scrolling started.
	 * 
	 * @param wheel
	 *            the wheel view whose state has changed.
	 */
	void onScrollingStarted(WheelView_new wheel);

	/**
	 * Callback method to be invoked when scrolling ended.
	 * 
	 * @param wheel
	 *            the wheel view whose state has changed.
	 */
	void onScrollingFinished(WheelView_new wheel);
}
