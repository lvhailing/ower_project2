package com.cf360.act;

import com.cf360.event.KeyboardEvent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
public abstract class AbsBaseActivity extends FragmentActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	/**
	 * 隐藏键盘事件
	 */
	protected OnTouchListener ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			KeyboardEvent.hideSoftInput(v.getContext());
			return false;
		}
	};

}
