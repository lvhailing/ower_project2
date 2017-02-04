package com.cf360.act;


import com.cf360.R;
import com.cf360.bean.ResultBankListMessageContentBean;
import com.cf360.bean.ResultSentSMSContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddBankActivity extends BaseActivity implements OnClickListener{

	private RelativeLayout rl_add_mybank_chose;
	public static final int REQUESTCODE = 1;			//选择银行
	public static final int RESULTCODE = 101;			//
	private TextView tv_add_mybank_name;
	private String bankName = null;
	private String bankNum = null;
	private Button btn_getsms_verifycode;
	private Button btn_mybank_add;
	private EditText et_add_mybank_verifycode_number,et_add_mybank_bank_number,tv_add_mybank_username;
	private TextView tv_add_mybank_phone;
	private String btnString;
	private boolean smsflag = false;
	private int time = 60;
	private int button = 0;
	private MyHandler mHandler;
	private boolean flag = true;
	private String realName;
	private ActivityStack stack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		baseSetContentView(R.layout.activity_add_mybank);
		initTopTitle();
		initView();
		
	}
	
	private void initData() {
		mHandler = new MyHandler();
		btnString = getResources().getString(R.string.signup_time);
		if(bankName!=null){
			tv_add_mybank_name.setText(bankName);
		}
		String phone = null;
//		String realName = null;
		try {
			phone = DESUtil.decrypt(PreferenceUtil.getPhone());
//			realName = DESUtil.decrypt(PreferenceUtil.getUserRealName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		tv_add_mybank_phone.setText(encryPhone(phone));
		
		tv_add_mybank_username.setText(realName);
		
	}

	public String encryPhone(String phone){
		StringBuffer newphone = new StringBuffer();
		newphone.append(phone.subSequence(0, 3));
		newphone.append("****");
		newphone.append(phone.substring(7, 11));
		
		return newphone.toString();
	}
	
	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_addmybank))
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

		realName = getIntent().getStringExtra("realName");
		rl_add_mybank_chose = (RelativeLayout) findViewById(R.id.rl_add_mybank_chose);
		tv_add_mybank_name = (TextView) findViewById(R.id.tv_add_mybank_name);
		
		btn_getsms_verifycode = (Button) findViewById(R.id.btn_getsms_verifycode);
		btn_mybank_add = (Button) findViewById(R.id.btn_mybank_add);
		et_add_mybank_verifycode_number = (EditText) findViewById(R.id.et_add_mybank_verifycode_number);
		et_add_mybank_bank_number = (EditText) findViewById(R.id.et_add_mybank_bank_number);
		tv_add_mybank_phone = (TextView) findViewById(R.id.tv_add_mybank_phone);
		tv_add_mybank_username = (EditText) findViewById(R.id.tv_add_mybank_username);
		tv_add_mybank_username.setEnabled(false);
		rl_add_mybank_chose.setOnClickListener(this);
		btn_getsms_verifycode.setOnClickListener(this);
		btn_mybank_add.setOnClickListener(this);
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	@Override
	protected void onStart() {
		super.onStart();
	}
	
	
	@Override
	protected void onResume() {
		initData();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(myRunnable);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_add_mybank_chose:
			Intent i_mybank_chose = new Intent();
			
			i_mybank_chose.setClass(AddBankActivity.this, AddBankChoseActivity.class);
			
			i_mybank_chose.putExtra("bankName", bankName);
			startActivityForResult(i_mybank_chose, REQUESTCODE);
			break;

		case R.id.btn_getsms_verifycode:
			
			requestSMSData();
			
			break;
		case R.id.btn_mybank_add:
			if(TextUtils.isEmpty(btn_getsms_verifycode.getText().toString())){
				Toast.makeText(AddBankActivity.this,
						"请输入验证码", Toast.LENGTH_LONG).show();
			}else if(TextUtils.isEmpty(tv_add_mybank_username.getText().toString())){
				Toast.makeText(AddBankActivity.this,
						"请输入账户姓名", Toast.LENGTH_LONG).show();
			}else if(TextUtils.isEmpty(bankName)){
				Toast.makeText(AddBankActivity.this,
						"请选择银行", Toast.LENGTH_LONG).show();
			}else if(TextUtils.isEmpty(et_add_mybank_bank_number.getText().toString())){
				Toast.makeText(AddBankActivity.this,
						"请输入银行卡号", Toast.LENGTH_LONG).show();
			}else if(et_add_mybank_bank_number.getText().toString().length()<8){
				Toast.makeText(AddBankActivity.this,
						"银行卡号不正确，请重新输入", Toast.LENGTH_LONG).show();
			}else {
				requestBindBankCard();
			}
			break;
		default:
			break;
		}
		
	}
	
	private void setButtonStyle(int time) {
		if (time == 0) {
			btn_getsms_verifycode.setText(getResources().getString(
					R.string.signup_getphoneauth));
			btn_getsms_verifycode.setClickable(true);
			btn_getsms_verifycode.setTextColor(getResources().getColor(R.color.txt_white));
			btn_getsms_verifycode.setBackgroundResource(R.drawable.shape_button);
		} else {
			if (time <= 59) {
				btn_getsms_verifycode.setClickable(false);
				btn_getsms_verifycode.setBackgroundResource(R.drawable.shape_button_gray_verify);
				btn_getsms_verifycode.setTextColor(getResources()
						.getColor(R.color.txt_gray));
				btn_getsms_verifycode.setText(time + btnString);
			}
		}
	}
	
	//获取短信验证码
	public void requestSMSData(){
		String userMobile = null;
		try {
			userMobile = DESUtil.decrypt(PreferenceUtil.getPhone());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String mathRandom = (int) Math.random()*100+1+"";
		HtmlRequest.sentSMS(AddBankActivity.this, userMobile,
				"bindbank", mathRandom,
				new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				
				ResultSentSMSContentBean bean = (ResultSentSMSContentBean) params.result;
				if (bean!= null) {
					if(Boolean.parseBoolean(bean.getFlag())){
						Toast.makeText(AddBankActivity.this,
								"短信发送成功", Toast.LENGTH_LONG).show();
						smsflag = true;
						startThread();
					}else{
						smsflag = false;
						Toast.makeText(AddBankActivity.this,
								bean.getMessage(), Toast.LENGTH_LONG)
								.show();
					}
//					System.out.println(bean.toString());
					
				} else {
					Toast.makeText(AddBankActivity.this, "加载失败，请确认网络通畅",
							Toast.LENGTH_LONG).show();
				}
				
			}
		});
				
	}
	
	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			setButtonStyle(msg.arg1);
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

	
	//绑定银行卡
	public void requestBindBankCard(){
		String verifcode  = et_add_mybank_verifycode_number.getText().toString();
		String bankNumber = et_add_mybank_bank_number.getText().toString();
		String userName ="";
		userName = tv_add_mybank_username.getText().toString();
		HtmlRequest.getBindBankCard(AddBankActivity.this,verifcode,bankNum,bankName,bankNumber, userName,new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				
				ResultSentSMSContentBean bean = (ResultSentSMSContentBean) params.result;
				if (bean!= null) {
					if(Boolean.parseBoolean(bean.getFlag())){
						Toast.makeText(AddBankActivity.this,
								"银行卡绑定成功", Toast.LENGTH_LONG).show();
						Intent i_mybank = new Intent();
						i_mybank.setClass(AddBankActivity.this, MyBankActivity.class);
//						startActivity(i_mybank);
						setResult(0, i_mybank);
						finish();
					}else{
						Toast.makeText(AddBankActivity.this,
								bean.getMessage(), Toast.LENGTH_LONG)
								.show();
					}
					
				} else {
					Toast.makeText(AddBankActivity.this, "加载失败，请确认网络通畅",
							Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		
		if(arg0==REQUESTCODE){
			if(arg1==RESULTCODE){
				if(arg2!=null){
					bankName = arg2.getExtras().getString("bankName");
					bankNum = arg2.getExtras().getString("bankNum");
				}
			}
		}
		
		super.onActivityResult(arg0, arg1, arg2);
	}
	
	
}
