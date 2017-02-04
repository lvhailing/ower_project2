package com.cf360.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.bean.ResultFindPWDbyPhoneContent;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.net.UserLoadout;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.uitls.StringUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

public class ChangePasswordActivity extends BaseActivity implements
		OnClickListener {

	private EditText edt_old, edt_new, edt_again;
	private Button btnOk;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_changepwd);
		initView();
		initTopTitle();
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		edt_again = (EditText) findViewById(R.id.changepwd_new2);
		edt_old = (EditText) findViewById(R.id.changepwd_old);
		edt_new = (EditText) findViewById(R.id.changepwd_new);
		btnOk = (Button) findViewById(R.id.changepwd_ok);
		btnOk.setOnClickListener(this);
//		changeBackground(edt_old);
//		changeBackground(edt_new);
//		changeBackground(edt_again);
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

//	private void changeBackground(final View view) {
//		view.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (hasFocus) {
//					view.setBackgroundResource(R.drawable.login_username);
//				} else {
//					view.setBackgroundResource(R.drawable.login_password);
//				}
//			}
//		});
//	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_changepwd))
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
		case R.id.changepwd_ok:
			String pwdnew = edt_new.getText().toString();
			String pwdnew2 = edt_again.getText().toString();
			if (StringUtil.hasBlank(pwdnew2)) {
				Toast.makeText(ChangePasswordActivity.this, "密码中不能含有空格",
						Toast.LENGTH_SHORT).show();
			} else {
				if (pwdnew.equals(pwdnew2)) {
					if (!StringUtil.hasSpecialWord(pwdnew2)) {
						if (pwdnew.length() < 6 || pwdnew.length() > 16) {
							Toast.makeText(ChangePasswordActivity.this,
									"密码长度在6-16个字符之间", Toast.LENGTH_SHORT)
									.show();
						} else {
							requestData();
						}
					} else {
						Toast.makeText(ChangePasswordActivity.this,
								"密码不能含有除下划线外其它特殊字符", Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(ChangePasswordActivity.this,
							"两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		default:
			break;
		}
	}

	private void requestData() {
		try {
			HtmlRequest.changePassword(ChangePasswordActivity.this, DESUtil
					.decrypt(PreferenceUtil.getPhone()), edt_old.getText()
					.toString(),edt_new.getText().toString(), edt_again.getText().toString(),
					new OnRequestListener() {

						@Override
						public void onRequestFinished(BaseParams params) {
							if (params.result != null) {
								ResultFindPWDbyPhoneContent bean = (ResultFindPWDbyPhoneContent) params.result;
								if (Boolean.parseBoolean(bean.getFlag())) {
									Toast.makeText(ChangePasswordActivity.this,
											"密码修改成功,请重新登录", Toast.LENGTH_SHORT)
											.show();
									UserLoadout out = new UserLoadout(
											ChangePasswordActivity.this);
									out.requestData();
									finish();
								} else {
									Toast.makeText(ChangePasswordActivity.this,
											bean.getMessage(), Toast.LENGTH_SHORT)
											.show();
								}
							}else{
								Toast.makeText(ChangePasswordActivity.this,
										"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
