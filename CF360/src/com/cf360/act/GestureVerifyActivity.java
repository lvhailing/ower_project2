package com.cf360.act;

import java.util.Observable;
import java.util.Observer;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.ApplicationConsts;
import com.cf360.R;
import com.cf360.bean.ResultUserLoginContentBean;
import com.cf360.net.UserLogin;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.uitls.StringUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;
import com.cf360.widget.GestureContentView;
import com.cf360.widget.GestureDrawline.GestureCallBack;

/**
 * 
 * 手势绘制/校验界面
 * 
 */
public class GestureVerifyActivity extends BaseActivity implements
		android.view.View.OnClickListener, Observer {
	
	private TextView mTextTip;
	private FrameLayout mGestureContainer;
	private GestureContentView mGestureContentView;
	private TextView mTextForget;
	String s = null;
	private int current_num = 1;
	private static final int MAX_NUM = 5;
	public static final String TOMAIN = "6";

	private String from = null;
	private int titleName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_gesture_verify);
		from = getIntent().getExtras().getString("from");
		titleName = getIntent().getExtras().getInt("title");
		initTopTitle();
		initView();
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setLogo(R.drawable.img_logo, false)
				.setIndicator(R.drawable.back)
				.setCenterText(getResources().getString(titleName))
				.showMore(false).setOnActionListener(new OnActionListener() {

					@Override
					public void onMenu(int id) {
						System.out.println("menu");
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
	
	@Override
	protected void onResume() {
		super.onResume();
		UserLogin.getInstance().addObserver(this);
		setUpViews();
	}

	private void initView() {
		
		mTextTip = (TextView) findViewById(R.id.text_title_message);
		mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);
		mTextForget = (TextView) findViewById(R.id.text_forget_gesture);
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void setUpViews() {
		try {
			s = DESUtil.decrypt(PreferenceUtil.getGesturePwd());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (from.equals(ApplicationConsts.ACTIVITY_GESVERIFY)) {
			mTextForget.setVisibility(View.GONE);
		}else if(from.equals(ApplicationConsts.ACTIVITY_SPLASH)){
			mTextForget.setText("忘记手势密码");
			mTextForget.setOnClickListener(this);
		}else if(from.equals(ApplicationConsts.ACTIVITY_GESEDIT)){
			mTextForget.setText("忘记手势密码");
			mTextForget.setOnClickListener(this);
		}
		// 初始化一个显示各个点的viewGroup
		mGestureContentView = new GestureContentView(this, true, s,
				new GestureCallBack() {

					@Override
					public void onGestureCodeInput(String inputCode) {

					}

					@Override
					public void checkedSuccess() {
						mGestureContentView.clearDrawlineState(0L);
						try {
							if (from.equals(ApplicationConsts.ACTIVITY_SPLASH)) {
								GestureVerifyActivity.this.finish();
//								UserLogin.getInstance().userlogining(
//										getApplicationContext(),
//										DESUtil.decrypt(PreferenceUtil
//												.getAutoLoginAccount()),
//										DESUtil.decrypt(PreferenceUtil
//												.getAutoLoginPwd()), "");
								Intent i = new Intent(
										GestureVerifyActivity.this,
										MainActivity.class);
								
								startActivity(i);
							} else if (from
									.equals(ApplicationConsts.ACTIVITY_GESEDIT)) {
								Toast.makeText(GestureVerifyActivity.this,
										"设置成功", Toast.LENGTH_SHORT)
										.show();
								finish();
							} else if (from
									.equals(ApplicationConsts.ACTIVITY_GESVERIFY)) {
								UserLogin.getInstance().userlogining(
										getApplicationContext(),
										DESUtil.decrypt(PreferenceUtil
												.getAutoLoginAccount()),
										DESUtil.decrypt(PreferenceUtil
												.getAutoLoginPwd()), "");
								Intent i = new Intent(
										GestureVerifyActivity.this,
										MainActivity.class);
								Toast.makeText(GestureVerifyActivity.this,
										"设置成功", Toast.LENGTH_SHORT)
										.show();
								startActivity(i);
								finish();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void checkedFail() {
						if (current_num >= MAX_NUM) {
							AlertDialog.Builder builder = new Builder(
									GestureVerifyActivity.this);
							builder.setMessage("您输入的手势密码错误次数已达到最大次数，请使用登录密码进行登录");
							builder.setTitle("密码错误");
							builder.setPositiveButton("确认",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											from = ApplicationConsts.ACTIVITY_GESVERIFY;
											Intent intent = new Intent();
											intent.setClass(
													GestureVerifyActivity.this,
													LoginActivity.class);
//											startActivityForResult(ilogin, 99);
											
											intent.putExtra("tomain", TOMAIN);
											PreferenceUtil.setFirstLogin(true);
											PreferenceUtil.setGesturePwd("");
											PreferenceUtil.setLogin(false);
											startActivity(intent);
											finish();
											
										}
									});
							builder.create().show();
						} else {
							mGestureContentView.clearDrawlineState(1300L);
							mTextTip.setVisibility(View.VISIBLE);
							String str1, str2, str3;
							str1 = "密码错误,您还可以尝试";
							str2 = str1 + (MAX_NUM - current_num);
							str3 = str2 + "次";

							mTextTip.setText(StringUtil.setTextStyle(
									GestureVerifyActivity.this, str1, str2,
									str3, R.color.txt_red, R.color.txt_red,
									R.color.txt_red, 16, 16, 16, 0, 0, 0));
							// 左右移动动画
							Animation shakeAnimation = AnimationUtils
									.loadAnimation(GestureVerifyActivity.this,
											R.anim.shake);
							mTextTip.startAnimation(shakeAnimation);
							current_num++;
						}
					}
				});
		android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(
				android.widget.FrameLayout.LayoutParams.WRAP_CONTENT,
				android.widget.FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_HORIZONTAL;
		mGestureContentView.setLayoutParams(params);
		// 设置手势解锁显示到哪个布局里面
		mGestureContentView.setParentView(mGestureContainer);
	}


	private String getProtectedMobile(String phoneNumber) {
		if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append(phoneNumber.subSequence(0, 3));
		builder.append("****");
		builder.append(phoneNumber.subSequence(7, 11));
		return builder.toString();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_forget_gesture:
			dialog();
			
			break;
		default:
			break;
		}
	}

	public void dialog() {
		AlertDialog.Builder builder = new Builder(GestureVerifyActivity.this);
		builder.setMessage("忘记手势密码，需要重新登录并设置手势密码");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if(from.equals(ApplicationConsts.ACTIVITY_SPLASH)||from.equals(ApplicationConsts.ACTIVITY_GESEDIT)){
					Intent intent=new Intent(GestureVerifyActivity.this,LoginActivity.class);
					intent.putExtra("tomain", TOMAIN);
					PreferenceUtil.setFirstLogin(true);
					PreferenceUtil.setGesturePwd("");
					PreferenceUtil.setLogin(false);
					startActivity(intent);
					finish();
				}else{
					
					Intent iforget = new Intent();
					iforget.setClass(GestureVerifyActivity.this,
							ChangeGestureActivity.class);
					iforget.putExtra("from", ApplicationConsts.ACTIVITY_GESVERIFY);
					iforget.putExtra("title", R.string.title_gestureset);
					startActivityForResult(iforget, 88);
				}
			}
		});
		
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 99) {
			if (resultCode == RESULT_OK) {
				Intent i = new Intent();
				i.setClass(GestureVerifyActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		} else if (requestCode == 88) {
			if (resultCode == RESULT_OK) {
				finish();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private ResultUserLoginContentBean bean;

	@Override
	public void update(Observable observable, Object data) {
		bean = (ResultUserLoginContentBean) data;
		if (bean != null) {
			if (Boolean.parseBoolean(bean.getFlag())) {
				if (from.equals(ApplicationConsts.ACTIVITY_SPLASH)) {
					finish();
				}
			}
		}
	}

}
