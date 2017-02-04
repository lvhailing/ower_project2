package com.cf360.act;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.bean.ResultSignupContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.HtmlRequest;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.StringUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

/**
 * 用户注册第二个界面
 * 
 */
public class Signup2Activity extends BaseActivity implements OnClickListener {
	private EditText edtPw, edtNickname, edtChannelPhone;
	private Button btnSignup;
	private String phone;
	private ActivityStack stack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_signup2);
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
		stack = ActivityStack.getActivityManage();
		stack.addActivity(this);
		edtPw = (EditText) findViewById(R.id.signup2_password);
		edtNickname = (EditText) findViewById(R.id.signup2_nickname);
		edtChannelPhone = (EditText) findViewById(R.id.signup2_channelPhone);
		btnSignup = (Button) findViewById(R.id.signup_true);
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void initData() {
		phone = getIntent().getStringExtra("phone");
		btnSignup.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signup_true:
			String password = edtPw.getText().toString();
			String nickName = edtNickname.getText().toString();
			String channelPhone = edtChannelPhone.getText().toString();
			if (password.length() < 6 || password.length() > 16) {
				Toast.makeText(Signup2Activity.this, "密码长度在6-16个字符之间",
						Toast.LENGTH_SHORT).show();
			} else {
				if (!StringUtil.hasBlank(password)) {
					if (!TextUtils.isEmpty(nickName)) {
						if (nickName.length() > 12) {
							Toast.makeText(Signup2Activity.this,
									"昵称长度不能超过12个字符", Toast.LENGTH_SHORT).show();
						} else {
							if (!StringUtil.hasSpecialWord(password)) {

								if (!TextUtils.isEmpty(channelPhone)) {
									if (StringUtil.isMobileNO(channelPhone)) {
										requestData(phone, password, nickName,
												channelPhone);

									} else {
										Toast.makeText(Signup2Activity.this,
												"请输入正确的手机号码",
												Toast.LENGTH_SHORT).show();
										edtChannelPhone.requestFocusFromTouch();
									}
								} else {
									requestData(phone, password, nickName,
											channelPhone);
								}

							} else {
								Toast.makeText(Signup2Activity.this,
										"密码不能含有除下划线外其它特殊字符", Toast.LENGTH_SHORT)
										.show();
							}
						}
					} else {
						Toast.makeText(Signup2Activity.this, "昵称不能为空",
								Toast.LENGTH_SHORT).show();
						edtNickname.requestFocusFromTouch();
					}
				} else {
					Toast.makeText(Signup2Activity.this, "密码不能包含空格",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		default:
			break;
		}
	}

	private void requestData(final String mobile, String password,
			String nickName, String channelManagerPhone) {
		HtmlRequest.signUpTwo(Signup2Activity.this, mobile, password, nickName,
				channelManagerPhone, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							ResultSignupContentBean data = (ResultSignupContentBean) params.result;
							if (Boolean.parseBoolean(data.getFlag())) {
								Toast.makeText(Signup2Activity.this, "注册成功",
										Toast.LENGTH_SHORT).show();
								// 跳转到理财师认证界面
								Intent intent = new Intent(
										Signup2Activity.this,
										LoginActivity.class);
								startActivity(intent);
								stack.removeAllActivity();
							} else {
								Toast.makeText(Signup2Activity.this,
										data.getMessage(), Toast.LENGTH_SHORT)
										.show();
							}
						} else {
							Toast.makeText(Signup2Activity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}
}
