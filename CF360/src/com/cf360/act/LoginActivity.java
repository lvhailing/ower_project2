package com.cf360.act;

import java.util.Observable;
import java.util.Observer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.bean.ResultUserLoginContentBean;
import com.cf360.net.UserLogin;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.Code;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;

/**
 * 登陆界面
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener,
		Observer {
	private TitleBar title;
	private EditText edtUsername, edtPassword;
	private Button btnLogin;
	private TextView txtFind;
	private Resources mResource;

	private ResultUserLoginContentBean bean;
	private String tomain = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_login);
		tomain = getIntent().getStringExtra("tomain");
		initView();
		initData();
		initTopTitle();
	}

	private void initView() {
		ActivityStack stack=ActivityStack.getActivityManage();
		stack.addActivity(this);
		edtUsername = (EditText) findViewById(R.id.login_username);
		edtPassword = (EditText) findViewById(R.id.login_password);
		btnLogin = (Button) findViewById(R.id.login_btn);
		txtFind = (TextView) findViewById(R.id.login_find);
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void initData() {
		mResource = getResources();
		btnLogin.setOnClickListener(this);
		txtFind.setOnClickListener(this);
		btnLogin.setClickable(false);
		btnLogin.setBackgroundResource(R.drawable.shape_button_gray);

		edtUsername.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!TextUtils.isEmpty(edtPassword.getText())) {
					btnLogin.setBackgroundResource(R.drawable.shape_button);
					btnLogin.setClickable(true);
				} else {
					btnLogin.setBackgroundResource(R.drawable.shape_button_gray);
					btnLogin.setClickable(false);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		edtPassword.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!TextUtils.isEmpty(edtUsername.getText())) {
					btnLogin.setBackgroundResource(R.drawable.shape_button);
					btnLogin.setClickable(true);
				} else {
					btnLogin.setBackgroundResource(R.drawable.shape_button_gray);
					btnLogin.setClickable(false);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}

	private void initTopTitle() {
		title = (TitleBar) findViewById(R.id.rl_title);
		title.addAction(new Action(2, 0, R.color.orange), LoginActivity.this
				.getResources().getString(R.string.title_signup));
		if(tomain!=null){
			if(tomain.equals(GestureVerifyActivity.TOMAIN)){
				title.showLeftImg(false);
				title.setLeftImageView(false);
			}else{
				title.setIndicator(R.drawable.back);
				title.showLeftImg(true);
				title.setLeftImageView(true);
			}
		}else{
			title.setIndicator(R.drawable.back);
			title.showLeftImg(true);
			title.setLeftImageView(true);
		}
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(getResources().getString(R.string.title_logintv))
				.setLogo(R.drawable.img_logo, false)
				.showMore(true).setOnActionListener(new OnActionListener() {

					@Override
					public void onMenu(int id) {
					}

					@Override
					public void onBack() {
						if(tomain!=null){
							if(tomain.equals(GestureVerifyActivity.TOMAIN)){
								Intent i_main = new Intent(LoginActivity.this,
										MainActivity.class);
								startActivity(i_main);
							}else{
								finish();
							}
						}else{
							finish();
						}
						finish();
					}

					@Override
					public void onAction(int id) {
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, SignupActivity.class);
						startActivity(intent);
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		UserLogin.getInstance().addObserver(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction("mybroadcast");
		registerReceiver(broadcastReceiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
		UserLogin.getInstance().deleteObserver(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			String user = edtUsername.getText().toString();
			String pass = edtPassword.getText().toString();
			judgeUser(user, pass);
			break;
		case R.id.login_find:
			Intent i = new Intent();
			i.setClass(LoginActivity.this, FindPasswordActivity.class);
			startActivity(i);
			break;
		default:
			break;
		}
	}

	private void judgeUser(String user, String pass) {
		UserLogin.getInstance()
				.userlogining(LoginActivity.this, user, pass, "");
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	};

	@Override
	public void update(Observable observable, Object data) {
		bean = (ResultUserLoginContentBean) data;
		if (bean != null) {
			if (Boolean.parseBoolean(bean.getFlag())) {
				if (PreferenceUtil.isFirstLogin()) {
						PreferenceUtil.setFirstLogin(false);
						PreferenceUtil.setLogin(true);
						Intent i = new Intent(LoginActivity.this,
								GestureEditActivity.class);
						i.putExtra("comeflag", 1);
						if(tomain!=null){
							i.putExtra("tomain", tomain);
						}
						i.putExtra("title", R.string.title_gestureset);
						startActivity(i);
						
				}
//				tomain = getIntent().getStringExtra("tomain");
				
				if(tomain!=null){
					/*if(tomain.equals(GestureVerifyActivity.TOMAIN)){
						Intent i_main = new Intent(LoginActivity.this,
								MainActivity.class);
						startActivity(i_main);
//						PreferenceUtil.setLogin(true);
//						Intent i = new Intent(LoginActivity.this,
//								GestureEditActivity.class);
//						i.putExtra("comeflag", 1);
//						i.putExtra("title", R.string.title_gestureset);
//						startActivity(i);
//						setResult(RESULT_OK);
					}else{
						setResult(RESULT_OK);
					}*/
				}else{
					setResult(RESULT_OK);
				}
				finish();
			} else {
				Toast.makeText(LoginActivity.this, bean.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		
		if(tomain!=null){
			Intent i_main = new Intent(LoginActivity.this,
					MainActivity.class);
			startActivity(i_main);
		}else{
			super.onBackPressed();
		}
	}
	
}
