package com.cf360.act;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cf360.ApplicationConsts;
import com.cf360.R;
import com.cf360.bean.ResultFindPWDbyPhoneContent;
import com.cf360.bean.ResultSentSMSContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.HtmlRequest;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.StringUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

public class FindPasswordActivity extends BaseActivity implements
		OnClickListener {

	private Button btnOk, btnGetSMS;
	private EditText edtPhone, edtNew, edtNewAgain, edtAuth;
	private boolean smsflag = false;
	private MyHandler mHandler;
	private int time = 60;
	private boolean flag = true;
	private int button = 0;
	private String btnString;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_findpassword);
		initTopTitle();
		initView();
		initData();
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		btnGetSMS = (Button) findViewById(R.id.findpd_getSMS);
		btnOk = (Button) findViewById(R.id.findpd_ok);
		edtPhone = (EditText) findViewById(R.id.findpd_phone);
		edtNew = (EditText) findViewById(R.id.findpd_newpwd);
		edtNewAgain = (EditText) findViewById(R.id.findpd_newpwd2);
		edtAuth = (EditText) findViewById(R.id.findpd_authcode);
		btnOk.setOnClickListener(this);
		btnGetSMS.setOnClickListener(this);

		edtNew.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					String pwd = edtNew.getText().toString();
					if (pwd.equals("")) {
						Toast.makeText(FindPasswordActivity.this,
								getResources().getString(R.string.findpd_null),
								Toast.LENGTH_LONG).show();
					} else if (pwd.length() < 6) {
						Toast.makeText(
								FindPasswordActivity.this,
								getResources().getString(R.string.findpd_short),
								Toast.LENGTH_LONG).show();
					}
				} 
			}
		});
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void initData() {
		mHandler = new MyHandler();
		btnString = getResources().getString(R.string.signup_time);
	}

	// 请求找回密码
	private void requestData(String mobile, String password, String rePassword,String validateCode) {
		HtmlRequest.findPWDbyPhone(FindPasswordActivity.this, mobile, password,rePassword,validateCode,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							ResultFindPWDbyPhoneContent data = (ResultFindPWDbyPhoneContent) params.result;
							if (Boolean.parseBoolean(data.getFlag())) {
								Toast.makeText(
										FindPasswordActivity.this,
										getResources().getString(
												R.string.findpd_success),
										Toast.LENGTH_LONG).show();
								Intent i = new Intent();
								i.putExtra("result", "ok");
								setResult(RESULT_OK, i);
								finish();
							} else {
								Toast.makeText(FindPasswordActivity.this,
										data.getMessage(), Toast.LENGTH_LONG)
										.show();
							}
						} else {
							Toast.makeText(FindPasswordActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setLogo(R.drawable.img_logo, false)
				.setIndicator(R.drawable.back)
				.setCenterText(getResources().getString(R.string.title_findpwd))
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
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.findpd_phone:
			break;
		case R.id.findpd_getSMS:
			if (!TextUtils.isEmpty(edtPhone.getText())) {
				if (StringUtil.isMobileNO(edtPhone.getText().toString())) {
					requestSMS();
				} else {
					Toast.makeText(FindPasswordActivity.this, "请输入正确的手机号",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(FindPasswordActivity.this, "请输入完整信息",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.findpd_ok:
			String newpwd = edtNew.getText().toString();
			String newpwdAgain = edtNewAgain.getText().toString();
			String phone = edtPhone.getText().toString();
			String authCode = edtAuth.getText().toString();

			if (TextUtils.isEmpty(newpwd)) {
				Toast.makeText(FindPasswordActivity.this, "请输入新密码",
						Toast.LENGTH_SHORT).show();
			} else {

				if (!newpwd.equals(edtNewAgain.getText().toString())) {
					Toast.makeText(FindPasswordActivity.this,
							getResources().getString(R.string.findpd_noequal),
							Toast.LENGTH_LONG).show();
				} else {
					if (StringUtil.hasBlank(newpwd)) {
						Toast.makeText(
								FindPasswordActivity.this,
								getResources().getString(
										R.string.findpd_hasspace),
								Toast.LENGTH_LONG).show();
					} else {
						if (newpwd.length() < 6 || newpwd.length() > 16) {
							Toast.makeText(FindPasswordActivity.this,
									"密码长度在6-16个字符之间", Toast.LENGTH_SHORT)
									.show();
						} else if (StringUtil.hasSpecialWord(newpwd)) {
							Toast.makeText(FindPasswordActivity.this,
									"密码不能含有除下划线外其它特殊字符", Toast.LENGTH_SHORT)
									.show();
						} else {
							if (!TextUtils.isEmpty(authCode)) {
								if (TextUtils.isEmpty(phone)) {
									Toast.makeText(FindPasswordActivity.this, "手机号不能为空",
											Toast.LENGTH_SHORT).show();
									edtPhone.requestFocusFromTouch();

								} else {
									if (StringUtil.isMobileNO(edtPhone.getText().toString())) {
											requestData(phone,newpwd,newpwdAgain,authCode);
									}else{
										Toast.makeText(FindPasswordActivity.this, "请输入正确的手机号码",
												Toast.LENGTH_SHORT).show();
										edtPhone.requestFocusFromTouch();
									}
								}
							} else {
								Toast.makeText(FindPasswordActivity.this,
										"您输入的短信验证码不正确", Toast.LENGTH_SHORT)
										.show();
							}

						}
					}
				}
			}
			break;
		default:
			break;
		}
	}



	// 请求短信验证码
	private void requestSMS() {
		final String userMobile = edtPhone.getText().toString();
		String mathRandom=String.valueOf(Math.random());
		HtmlRequest.sentSMS(FindPasswordActivity.this, userMobile,
				ApplicationConsts.LOGINRET,mathRandom, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						ResultSentSMSContentBean b = (ResultSentSMSContentBean) params.result;
						if (b != null) {
							if (Boolean.parseBoolean(b.getFlag())) {
								Toast.makeText(FindPasswordActivity.this,
										"短信发送成功", Toast.LENGTH_LONG).show();
								smsflag = true;
								startThread();
							} else {
								smsflag = false;
								Toast.makeText(FindPasswordActivity.this,
										b.getMessage(), Toast.LENGTH_LONG)
										.show();
							}
						} else {
							Toast.makeText(FindPasswordActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	private void startThread() {
		if (smsflag) {
			Thread t = new Thread(myRunnable);
			flag = true;
			button = R.id.signup_getSMS;
			t.start();
		}
	}

	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			setButtonStyle(msg.arg1);
		}

	}

	private void setButtonStyle(int time) {
		if (time == 0) {
			btnGetSMS.setText(getResources().getString(
					R.string.signup_getphoneauth));
			btnGetSMS.setClickable(true);
			btnGetSMS.setTextColor(getResources().getColor(R.color.txt_white));
			btnGetSMS.setBackgroundResource(R.drawable.shape_button);
		} else {
			if (time <= 59) {
				btnGetSMS.setClickable(false);
				btnGetSMS.setBackgroundResource(R.drawable.shape_button_gray_verify);
				btnGetSMS.setTextColor(getResources()
						.getColor(R.color.txt_gray));
				btnGetSMS.setText(time + btnString);
			}
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
}
