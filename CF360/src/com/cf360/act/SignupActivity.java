package com.cf360.act;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.ApplicationConsts;
import com.cf360.R;
import com.cf360.bean.ResultIsRegisterContentBean;
import com.cf360.bean.ResultSentSMSContentBean;
import com.cf360.bean.ResultSignupContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.StringUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

/**
 * 用户注册第一个界面
 * 
 */

public class SignupActivity extends BaseActivity implements OnClickListener {

	private EditText edt_phone, edt_auth;
	private Button btn_getPhone, btn_true;
	private CheckBox cb_agree;
	private TextView tv_web;

	private MyHandler mHandler;
	private int time = 60;
	private boolean flag = true;
	private int button = 0;
	private String btnString;

	private boolean smsflag = false;
	private String phone;
	private String validateCode;
	private String mark;
	//private String phoneEnd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_signup);
		initView();
		initData();
		initTopTitle();
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setLogo(R.drawable.img_logo, false).setIndicator(R.drawable.back)
				.setCenterText(getResources().getString(R.string.title_signup))
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
		ActivityStack stack=ActivityStack.getActivityManage();
		stack.addActivity(this);
		// 验证码
		edt_auth = (EditText) findViewById(R.id.signup_authcode);
		// 手机号
		edt_phone = (EditText) findViewById(R.id.signup_username);
		btn_getPhone = (Button) findViewById(R.id.signup_getSMS);
		btn_true = (Button) findViewById(R.id.signup_true);
		cb_agree = (CheckBox) findViewById(R.id.signup_checkbox);
		tv_web = (TextView) findViewById(R.id.signup_web);
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void initData() {
		mHandler = new MyHandler();
		btnString = getResources().getString(R.string.signup_time);
		btn_getPhone.setOnClickListener(this);
		btn_true.setOnClickListener(this);
		tv_web.setOnClickListener(this);
		cb_agree.setChecked(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(myRunnable);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.signup_getSMS:
			if (!TextUtils.isEmpty(edt_phone.getText().toString())) {
				if (StringUtil.isMobileNO(edt_phone.getText().toString())) {
					requestSMS();
				} else {
					Toast.makeText(SignupActivity.this, "请输入正确的手机号码",
							Toast.LENGTH_SHORT).show();
					edt_phone.requestFocusFromTouch();
				}
			} else {
				Toast.makeText(SignupActivity.this, "手机号不能为空",
						Toast.LENGTH_SHORT).show();
				edt_phone.requestFocusFromTouch();
			}
			break;
		case R.id.signup_true:
			String phone = edt_phone.getText().toString();
			String validateCode = edt_auth.getText().toString();
			if (TextUtils.isEmpty(phone)) {
				Toast.makeText(SignupActivity.this, "手机号不能为空",
						Toast.LENGTH_SHORT).show();
				edt_phone.requestFocusFromTouch();

			} else {
				//if (phone.equals(phoneEnd)) {
					if (StringUtil.isMobileNO(edt_phone.getText().toString())) {
						if (TextUtils.isEmpty(validateCode)) {
							Toast.makeText(SignupActivity.this, "验证码不能为空",
									Toast.LENGTH_SHORT).show();
							edt_auth.requestFocusFromTouch();
						} else {
							if (cb_agree.isChecked()) {
								requestData(phone, validateCode);
							} else {
								Toast.makeText(SignupActivity.this, "是否同意服务协议",
										Toast.LENGTH_SHORT).show();
							}

						}
					} else {
						Toast.makeText(SignupActivity.this, "请输入正确的手机号码",
								Toast.LENGTH_SHORT).show();
						edt_phone.requestFocusFromTouch();
					}

				/*} else {
					Toast.makeText(SignupActivity.this, "手机号码 与验证码不对应",
							Toast.LENGTH_SHORT).show();
					edt_phone.requestFocusFromTouch();
				}*/
			}
			break;
		case R.id.signup_web:
			Intent i_web = new Intent(SignupActivity.this, WebActivity.class);
			i_web.putExtra("type", WebActivity.WEBTYPE_SIGNUP);
			startActivity(i_web);
			break;
		default:
			break;
		}
	}

	private void startThread() {
		if (smsflag) {
			Thread t = new Thread(myRunnable);
			flag = true;
			button = R.id.signup_getSMS;
			t.start();
		}
	}

	private void setButtonStyle(int time) {
		if (time == 0) {
			btn_getPhone.setClickable(true);
			btn_getPhone.setTextColor(getResources()
					.getColor(R.color.txt_white));
			btn_getPhone.setBackgroundResource(R.drawable.shape_button);
			btn_getPhone.setText(getResources().getString(
					R.string.signup_getphoneauth));
		} else if (time < 60) {
			btn_getPhone.setClickable(false);
			btn_getPhone.setBackgroundResource(R.drawable.shape_button_gray_verify);
			btn_getPhone
					.setTextColor(getResources().getColor(R.color.txt_gray));
			btn_getPhone.setText(time + btnString);

		}
	}

	@SuppressLint("HandlerLeak")
	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			setButtonStyle(msg.arg1);
		}

	}

	Runnable myRunnable = new Runnable() {

		@Override
		public void run() {
			while (flag) {
				Message msg = new Message();
				time -= 1;
				msg.arg1 = time;
				if (time == 0) {
					flag = false;
					mHandler.sendMessage(msg);
					time = 60;
					mHandler.removeCallbacks(myRunnable);
				} else {
					mHandler.sendMessage(msg);
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	};

	private void requestData(final String mobile, final String validateCode) {
		HtmlRequest.signUpOne(SignupActivity.this, mobile, validateCode,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							ResultSignupContentBean data = (ResultSignupContentBean) params.result;
							if (Boolean.parseBoolean(data.getFlag())) {
								Toast.makeText(SignupActivity.this, data.getMessage(),
										Toast.LENGTH_LONG).show();
								// 验证通过后， 跳转到下一个注册界面
								Intent intent = new Intent(SignupActivity.this,
										Signup2Activity.class);
								intent.putExtra("phone", mobile);
								startActivity(intent);
							} else {
								Toast.makeText(SignupActivity.this,
										data.getMessage(), Toast.LENGTH_SHORT)
										.show();
								edt_auth.requestFocusFromTouch();
							}
						} else {
							Toast.makeText(SignupActivity.this, "加载失败，请确认网络通畅",
									Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	private void requestSMS() {
		final String userMobile = edt_phone.getText().toString();
		String mathRandom = String.valueOf(Math.random());
		HtmlRequest.sentSMS(SignupActivity.this, userMobile,
				ApplicationConsts.REGISTER, mathRandom,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						ResultSentSMSContentBean b = (ResultSentSMSContentBean) params.result;
						if (b != null) {
							if (Boolean.parseBoolean(b.getFlag())) {
								Toast.makeText(SignupActivity.this, "短信发送成功",
										Toast.LENGTH_LONG).show();
//								phoneEnd = userMobile;
								smsflag = true;
								startThread();
							} else {
								smsflag = false;
								Toast.makeText(SignupActivity.this,
										b.getMessage(), Toast.LENGTH_LONG)
										.show();
							}
						} else {
							Toast.makeText(SignupActivity.this, "加载失败，请确认网络通畅",
									Toast.LENGTH_LONG).show();
						}
					}
				});
	}
}
