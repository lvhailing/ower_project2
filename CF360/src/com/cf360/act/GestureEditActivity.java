package com.cf360.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.ApplicationConsts;
import com.cf360.R;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;
import com.cf360.widget.GestureContentView;
import com.cf360.widget.GestureDrawline.GestureCallBack;

/**
 * 
 * 手势密码设置界面
 * 
 */
public class GestureEditActivity extends BaseActivity implements OnClickListener {
	
	/** 首次提示绘制手势密码，可以选择跳过 */
	public static final String PARAM_IS_FIRST_ADVICE = "PARAM_IS_FIRST_ADVICE";
	// private TextView mTextTitle;
	// private TextView mTextCancel;
	// private LockIndicator mLockIndicator;
	private TextView mTextTip;
	private FrameLayout mGestureContainer;
	private GestureContentView mGestureContentView;
	private TextView mTextReset;
	private boolean mIsFirstInput = true;
	private String mFirstPassword = null;
	private String mConfirmPassword = null;
	private int mParamIntentCode;

	private static final int SPLASHACT = 0;
	private static final int LOGINACT = 1;
	private static final int SETTINGACT = 2;
	private static final int VERIFYACT = 3;

	private int comeFlag = LOGINACT;
	private int titleName;
	private String tomain = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_gesture_edit);
		Intent i = getIntent();
		comeFlag = i.getExtras().getInt("comeflag");
		titleName = getIntent().getExtras().getInt("title");
		tomain = getIntent().getStringExtra("tomain");
//		System.out.println("titleName"+titleName);
		initTopTitle();
		setUpViews();
		setUpListeners();
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
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
	
	private void setUpViews() {
		// mTextTitle = (TextView) findViewById(R.id.text_title);
		// mTextCancel = (TextView) findViewById(R.id.text_cancel);
		mTextReset = (TextView) findViewById(R.id.text_reset);
		mTextReset.setClickable(false);
//		mTextReset.setVisibility(View.GONE);
		// mLockIndicator = (LockIndicator) findViewById(R.id.lock_indicator);
		mTextTip = (TextView) findViewById(R.id.text_tip);
		mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);
		// 初始化一个显示各个点的viewGroup
		mGestureContentView = new GestureContentView(this, false, "",
				new GestureCallBack() {
					@Override
					public void onGestureCodeInput(String inputCode) {
						if (!isInputPassValidate(inputCode)) {
							mTextTip.setText(Html
									.fromHtml("<font color='#c70c1e'>最少连接4个点, 请重新输入</font>"));
							mGestureContentView.clearDrawlineState(0L);
							return;
						}
						if (mIsFirstInput) {
							mFirstPassword = inputCode;
							// updateCodeList(inputCode);
							mGestureContentView.clearDrawlineState(0L);
							mTextReset.setClickable(true);
							mTextReset.setVisibility(View.VISIBLE);
							mTextReset
									.setText(getString(R.string.reset_gesture_code));
						} else {
							if (inputCode.equals(mFirstPassword)) {
								mGestureContentView.clearDrawlineState(0L);
								// Intent i = new
								// Intent(GestureEditActivity.this,
								// MainActivity.class);
								// startActivity(i);
								try {
									PreferenceUtil.setGesturePwd(DESUtil
											.encrypt(mFirstPassword));
								} catch (Exception e) {
									e.printStackTrace();
								}
								switch (comeFlag) {
								case SPLASHACT:
									Intent i_splash = new Intent();
									i_splash.setClass(GestureEditActivity.this,
											MainActivity.class);
									Toast.makeText(GestureEditActivity.this, "设置成功", Toast.LENGTH_LONG).show();
									startActivity(i_splash);
									finish();
									break;
								case SETTINGACT:
									Intent i_set = new Intent();
									i_set.setClass(GestureEditActivity.this,
											GestureVerifyActivity.class);
									i_set.putExtra("from",
											ApplicationConsts.ACTIVITY_GESEDIT);
									i_set.putExtra("title", R.string.title_changegesture);
									setResult(RESULT_OK);
									startActivity(i_set);
									finish();
									break;
								case LOGINACT:
									Toast.makeText(GestureEditActivity.this, "设置成功", Toast.LENGTH_LONG).show();
									if(tomain!=null){
										if(tomain.equals(GestureVerifyActivity.TOMAIN)){
											Intent i_main = new Intent(GestureEditActivity.this,
													MainActivity.class);
											startActivity(i_main);
										}
									}
									finish();
									break;
								case VERIFYACT:
									Intent i_verify = new Intent();
									i_verify.setClass(GestureEditActivity.this,
											GestureVerifyActivity.class);
									i_verify.putExtra("from",
											ApplicationConsts.ACTIVITY_GESVERIFY);
									startActivity(i_verify);
									finish();
									break;
								}

							} else {
								mTextTip.setText(Html
										.fromHtml("<font color='#c70c1e'>与上一次绘制不一致，请重新绘制</font>"));
								// 左右移动动画
								Animation shakeAnimation = AnimationUtils
										.loadAnimation(
												GestureEditActivity.this,
												R.anim.shake);
								mTextTip.startAnimation(shakeAnimation);
								// 保持绘制的线，1.5秒后清除
								mGestureContentView.clearDrawlineState(1300L);
							}
						}
						mIsFirstInput = false;
					}

					@Override
					public void checkedSuccess() {
					}

					@Override
					public void checkedFail() {

					}
				});
		android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(
				android.widget.FrameLayout.LayoutParams.WRAP_CONTENT,
				android.widget.FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_HORIZONTAL;
		mGestureContentView.setLayoutParams(params);
		// 设置手势解锁显示到哪个布局里面
		mGestureContentView.setParentView(mGestureContainer);
		// updateCodeList("");
	}

	private void setUpListeners() {
		// mTextCancel.setOnClickListener(this);
		mTextReset.setOnClickListener(this);
	}

	// private void updateCodeList(String inputCode) {
	// // 更新选择的图案
	// mLockIndicator.setPath(inputCode);
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.text_cancel:
		// this.finish();
		// break;
		case R.id.text_reset:
			if(mIsFirstInput){
				Intent iMain = new Intent(GestureEditActivity.this,
						MainActivity.class);
//				startActivity(iMain);
				if(comeFlag==SPLASHACT){
					startActivity(iMain);
				}else{
					if(tomain!=null){
						if(tomain.equals(GestureVerifyActivity.TOMAIN)){
							startActivity(iMain);
						}
					}else{
						setResult(LOGINACT, iMain);
					}
				}
				PreferenceUtil.setGestureChose(false);
				PreferenceUtil.setFirstLogin(true);
				PreferenceUtil.setGesturePwd("");
				finish();
			}else{
				mIsFirstInput = true;
				mTextReset.setClickable(true);
//				mTextReset.setVisibility(View.GONE);
				mTextReset
				.setText(getString(R.string.set_gesture_pattern_jump));
			}
			// updateCodeList("");
			//mTextReset.setText(getString(R.string.set_gesture_pattern_reason));
			break;
		default:
			break;
		}
	}

	private boolean isInputPassValidate(String inputPassword) {
		if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4) {
			return false;
		}
		return true;
	}

}
