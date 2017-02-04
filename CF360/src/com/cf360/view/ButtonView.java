package com.cf360.view;

import java.util.ArrayList;

import com.cf360.R;
import com.cf360.act.LoginActivity;
import com.cf360.act.MTrustDetailsActivity;
import com.cf360.act.TrustDetailsActivity;
import com.cf360.popupwindow.ExpandTabView.OnButtonClickListener;
import com.cf360.uitls.PreferenceUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
/**
 * button按钮点击事件
 * @author hasee
 *
 */
public class ButtonView extends LinearLayout{
	private Context mContext;
	private ToggleButton selectedButton;
	private int selectPosition;
	private ArrayList<String> mTextArray = new ArrayList<String>();
	private ArrayList<ToggleButton> mToggleButton = new ArrayList<ToggleButton>();
	public ButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}


	public ButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ButtonView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		setOrientation(LinearLayout.HORIZONTAL);
		mTextArray.add("我要预约");
		mTextArray.add("发送邮件");
		mTextArray.add("加入关注");

	}
	/**
	 * 根据选择的位置设置tabitem显示的值
	 */
	public void setTitle(String valueText, int position) {
		if (position < mToggleButton.size()) {
			mToggleButton.get(position).setText(valueText);
		}
	}

	public void setTitle(String title){

	}
	/**
	 * 根据选择的位置获取tabitem显示的值
	 */
	public String getTitle(int position) {
		if (position < mToggleButton.size() && mToggleButton.get(position).getText() != null) {
			return mToggleButton.get(position).getText().toString();
		}
		return "";
	}
	/**
	 * 设置Button的样式
	 */
	public void setValue() {
		if(mContext==null){
			return ;
		}
		for (int i = 0; i < 3; i++){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ToggleButton tButton = (ToggleButton) inflater.inflate(R.layout.button_view, this, false);
			addView(tButton);
			View line = new TextView(mContext);
			line.setBackgroundResource(R.drawable.choosebar_line2);
			if (i < 2)  {
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(2, LinearLayout.LayoutParams.FILL_PARENT);
				addView(line, lp);
			}
			mToggleButton.add(tButton);
			tButton.setTag(i);
			tButton.setText(mTextArray.get(i));


			tButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {

					// initPopupWindow();
					ToggleButton tButton = (ToggleButton) view;

					if (selectedButton != null && selectedButton != tButton) {
						selectedButton.setChecked(false);
					}
					selectedButton = tButton;
					selectPosition = (Integer) selectedButton.getTag();
					if(PreferenceUtil.isLogin()){
						if (mOnButtonClickListener != null && tButton.isChecked()) {
							mOnButtonClickListener.onClick(selectPosition);
							selectedButton.setChecked(false);
						}
					}else{
						selectedButton.setChecked(false);
						Intent intent = new Intent(mContext,LoginActivity.class);
						mContext.startActivity(intent);
						//selectedButton.setChecked(false);
					}
				}
			});
		}


	} 

	private OnViewClickListener mOnButtonClickListener;

	/**
	 * 设置tabitem的点击监听事件
	 */
	public void setOnViewClickListener(OnViewClickListener l) {
		mOnButtonClickListener = l;
	}

	/**
	 * 自定义tabitem点击回调接口
	 */
	public interface OnViewClickListener {
		public void onClick(int selectPosition);
	}
}
