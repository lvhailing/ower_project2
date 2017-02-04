package com.cf360.act;


import com.cf360.ApplicationConsts;
import com.cf360.R;
import com.cf360.fragment.MenuLeftFragment.ReceiveBroadCast;
import com.cf360.net.UserLoadout;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * 设置
 * 
 */
public class SettingActivity extends BaseActivity implements OnClickListener{
	
	
	private ImageButton imgbtn_setting_gesture_chose;
	private RelativeLayout rl_setting_gesture_update,rl_setting_password_update,rl_setting_advice,rl_setting_help,
		rl_setting_score,rl_setting_agreemnet,rl_setting_about,rl_setting_phone,rl_setting_gesture_chose;
	private Button btn_setting_logout;
	private Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_setting);
		context = this;
//		registerBoradcastReceiver();
		initTopTitle();
		initView();
		initData();
		
		
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_setting))
				.setLogo(R.drawable.img_logo, false).setIndicator(R.drawable.back)
				.showMore(false).setOnActionListener(new OnActionListener() {

					@Override
					public void onMenu(int id) {
					}

					@Override
					public void onBack() {
						finish();
					}

					@Override
					public void onAction(int id) {

					}
				});
	}
	
	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		imgbtn_setting_gesture_chose = (ImageButton) findViewById(R.id.imgbtn_setting_gesture_chose);
		rl_setting_gesture_chose = (RelativeLayout) findViewById(R.id.rl_setting_gesture_chose);
		rl_setting_gesture_update = (RelativeLayout) findViewById(R.id.rl_setting_gesture_update);
		rl_setting_password_update = (RelativeLayout) findViewById(R.id.rl_setting_password_update);
		rl_setting_advice = (RelativeLayout) findViewById(R.id.rl_setting_advice);
		rl_setting_help = (RelativeLayout) findViewById(R.id.rl_setting_help);
		rl_setting_score = (RelativeLayout) findViewById(R.id.rl_setting_score);
		rl_setting_agreemnet = (RelativeLayout) findViewById(R.id.rl_setting_agreemnet);
		rl_setting_about = (RelativeLayout) findViewById(R.id.rl_setting_about);
		rl_setting_phone = (RelativeLayout) findViewById(R.id.rl_setting_phone);
		btn_setting_logout = (Button) findViewById(R.id.btn_setting_logout);
		
		if(PreferenceUtil.isGestureChose()){
			imgbtn_setting_gesture_chose.setImageResource(R.drawable.message2);
		}else{
			imgbtn_setting_gesture_chose.setImageResource(R.drawable.message1);
		}
		
		imgbtn_setting_gesture_chose.setOnClickListener(this);
		rl_setting_gesture_update.setOnClickListener(this);
		rl_setting_password_update.setOnClickListener(this);
		rl_setting_advice.setOnClickListener(this);
		rl_setting_help.setOnClickListener(this);
		rl_setting_score.setOnClickListener(this);
		rl_setting_agreemnet.setOnClickListener(this);
		rl_setting_about.setOnClickListener(this);
		rl_setting_phone.setOnClickListener(this);
		btn_setting_logout.setOnClickListener(this);
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void initData() {
		if(PreferenceUtil.isLogin()){
			rl_setting_gesture_chose.setVisibility(View.VISIBLE);
			rl_setting_gesture_update.setVisibility(View.VISIBLE);
			rl_setting_password_update.setVisibility(View.VISIBLE);
			rl_setting_advice.setVisibility(View.VISIBLE);
			btn_setting_logout.setVisibility(View.VISIBLE);
		}else{
			rl_setting_gesture_chose.setVisibility(View.GONE);
			rl_setting_gesture_update.setVisibility(View.GONE);
			rl_setting_password_update.setVisibility(View.GONE);
			rl_setting_advice.setVisibility(View.GONE);
			btn_setting_logout.setVisibility(View.INVISIBLE);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgbtn_setting_gesture_chose:
			if(PreferenceUtil.isGestureChose()){
				PreferenceUtil.setGestureChose(false);
				imgbtn_setting_gesture_chose.setImageResource(R.drawable.message1);
				
			}else{
				PreferenceUtil.setGestureChose(true);
				imgbtn_setting_gesture_chose.setImageResource(R.drawable.message2);
			}
			PreferenceUtil.setFirstLogin(true);
			PreferenceUtil.setGesturePwd("");
			break;
		case R.id.rl_setting_gesture_update:
			Intent i_ges = new Intent(SettingActivity.this,
					ChangeGestureActivity.class);
			i_ges.putExtra("from", ApplicationConsts.ACTIVITY_ACCOUNTSET);
			startActivity(i_ges);
			break;
		case R.id.rl_setting_password_update:
			Intent i_pwd = new Intent(SettingActivity.this,
					ChangePasswordActivity.class);
			startActivity(i_pwd);
			break;
		case R.id.rl_setting_advice:
			Intent i_advice = new Intent(SettingActivity.this,AdviceActivity.class);
			startActivity(i_advice);
			break;
		case R.id.rl_setting_help:
			Intent i_setting_help = new Intent(SettingActivity.this,SettingHelpActivity.class);
			startActivity(i_setting_help);
			break;
		case R.id.rl_setting_score:
			
			break;
		case R.id.rl_setting_agreemnet:
			
			break;
		case R.id.rl_setting_about:

//			Intent i_about = new Intent(SettingActivity.this,AboutActivity.class);
//			startActivity(i_about);
			Intent i_about = new Intent(SettingActivity.this,WebActivity.class);
			i_about.putExtra("title","关于我们");
			i_about.putExtra("type",WebActivity.WEBTYPE_ABOUTUS);
			i_about.putExtra("url",ApplicationConsts.URL_WEB_EQUIT_ABOUTUS);

			startActivity(i_about);

			break;
		case R.id.rl_setting_phone:
			Intent i_phone = new Intent(Intent.ACTION_DIAL,
					Uri.parse("tel:4000669355"));
			i_phone.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i_phone);
			break;
		case R.id.btn_setting_logout:
//			finish();
			UserLoadout out = new UserLoadout(SettingActivity.this);
			out.requestData();
			break;

		default:
			break;
		}
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	//	private ReceiveBroadCast receiveBroadCast; // 广播实例
//	String myActionName = "vjinkeexit";
//
//	// 注册广播
//	public void registerBoradcastReceiver() {
//		receiveBroadCast = new ReceiveBroadCast();
//		IntentFilter filter = new IntentFilter();
//		filter.addAction(myActionName); // 只有持有相同的action的接受者才能接收此广播
//		context.registerReceiver(receiveBroadCast, filter);
////		Log.e(tag, "registerBoradcastReceiver====");
//	}
//
//	// 定义一个BroadcastReceiver广播接收类：
//	public class ReceiveBroadCast extends BroadcastReceiver {
//		@Override
//		public void onReceive(Context context, Intent data) {
//			String actionName = data.getAction();
////			Log.e(tag, "actionName=="+actionName+"==data.getExtras()"+data.getExtras());
//			if (actionName.equals(myActionName)) {
//				// 得到广播中得到的数据，并显示出来
//				Bundle extras = data.getExtras();
//				if (extras != null) {
//					initData();
//				}
//			}
//		}
//	}
}
