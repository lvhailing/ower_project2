package com.cf360.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class MygridVeiw extends GridView {
	public MygridVeiw(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MygridVeiw(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public MygridVeiw(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	  * ���ò�����
	  */
	 public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	 {
	  int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
	    MeasureSpec.AT_MOST);
	  super.onMeasure(widthMeasureSpec, expandSpec);

	 }
	 
	

}
