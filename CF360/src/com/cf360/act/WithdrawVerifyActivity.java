package com.cf360.act;

import java.text.DecimalFormat;

import com.cf360.R;
import com.cf360.act.UnBandActivity.MyHandler;
import com.cf360.bean.ResultSentSMSContentBean;
import com.cf360.bean.ResultUnBankMessageContentBean;
import com.cf360.bean.ResultWithdrawVerifyContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.uitls.StringUtil;
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
import android.widget.TextView;
import android.widget.Toast;

public class WithdrawVerifyActivity extends BaseActivity implements OnClickListener{
	
	private String bankName = null;
	private String bankNum = null;
	private String bankId = null;
	private String avlNum = null;
	private String userName = null;
	private TextView mybank_withdraw_name,mybank_withdraw_bank_name,mybank_withdraw_number,tv_withdraw_phone,tv_withdraw_has_num;
	private Button btn_withdraw_bank_sms,btn_mybank_withdrawVerify;
	private EditText et_withdraw_verifcode,et_withdraw_to_number;
	
	private String btnString;
	private boolean smsflag = false;
	private int time = 60;
	private int button = 0;
	private MyHandler mHandler;
	private boolean flag = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_withdraw_verify);
		initTopTitle();
		bankName = getIntent().getExtras().getString("bankName");
		bankNum = getIntent().getExtras().getString("bankNum");
		bankId = getIntent().getExtras().getString("bankId");
		userName = getIntent().getExtras().getString("userName");
		avlNum = getIntent().getExtras().getString("avlNum");
		initView();
		initData();
		
		
	}
	
	
	private void initData() {
		mHandler = new MyHandler();
		btnString = getResources().getString(R.string.signup_time);
		setView();
	}
	
	private void setView() {
		String phone = null;
//		String realName = null;
		
		mybank_withdraw_bank_name.setText(bankName+": ");
		mybank_withdraw_number.setText(encryBankNum(bankNum));
		try {
			phone = DESUtil.decrypt(PreferenceUtil.getPhone());
//			realName = DESUtil.decrypt(PreferenceUtil.getUserRealName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mybank_withdraw_name.setText(userName);
		tv_withdraw_phone.setText(encryPhone(phone));
		
		DecimalFormat df1 = new DecimalFormat("0.00");
		double bond = Double.parseDouble(avlNum);
		tv_withdraw_has_num.setText(df1.format(bond)+"元。");
		
	}

	public String encryBankNum(String bankNum){
		StringBuffer sb = new StringBuffer();
		sb.append(bankNum.substring(0, 4));
		sb.append(" **** **** ");
		sb.append(bankNum.substring(bankNum.length()-4, bankNum.length()));
		
		return sb.toString();
	}
	
	public String encryPhone(String phone){
		StringBuffer newphone = new StringBuffer();
		newphone.append(phone.subSequence(0, 3));
		newphone.append("****");
		newphone.append(phone.substring(7, 11));
		
		return newphone.toString();
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		mybank_withdraw_name = (TextView) findViewById(R.id.mybank_withdraw_name);
		mybank_withdraw_bank_name = (TextView) findViewById(R.id.mybank_withdraw_bank_name);
		mybank_withdraw_number = (TextView) findViewById(R.id.mybank_withdraw_number);
		tv_withdraw_phone = (TextView) findViewById(R.id.tv_withdraw_phone);
		
		tv_withdraw_has_num = (TextView) findViewById(R.id.tv_withdraw_has_num);
		btn_withdraw_bank_sms = (Button) findViewById(R.id.btn_withdraw_bank_sms);
		btn_mybank_withdrawVerify = (Button) findViewById(R.id.btn_mybank_withdrawVerify);
		
		et_withdraw_verifcode = (EditText) findViewById(R.id.et_withdraw_verifcode);
		et_withdraw_to_number = (EditText) findViewById(R.id.et_withdraw_to_number);
		btn_withdraw_bank_sms.setOnClickListener(this);
		btn_mybank_withdrawVerify.setOnClickListener(this);
		
		btn_mybank_withdrawVerify.setBackgroundResource(R.drawable.shape_button);
		btn_mybank_withdrawVerify.setClickable(true);
		
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
				.setCenterText(
						getResources().getString(R.string.title_withdraw_verify))
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
	protected void onStart() {
		super.onStart();
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
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_withdraw_bank_sms:
			if(TextUtils.isEmpty(et_withdraw_to_number.getText().toString())){
				Toast.makeText(WithdrawVerifyActivity.this,
						"请输入转出金额", Toast.LENGTH_LONG).show();
			} else {
				requestSMSData();
			}
			break;
		case R.id.btn_mybank_withdrawVerify:
			
			if(TextUtils.isEmpty(et_withdraw_to_number.getText().toString())){
				Toast.makeText(WithdrawVerifyActivity.this,
						"请输入转出金额", Toast.LENGTH_LONG).show();
			} else if(TextUtils.isEmpty(et_withdraw_verifcode.getText().toString())){
				Toast.makeText(WithdrawVerifyActivity.this,
						"请输入验证码", Toast.LENGTH_LONG).show();
			}else if(!StringUtil.isDoubleForTwoNumber(et_withdraw_to_number.getText().toString())){
				Toast.makeText(WithdrawVerifyActivity.this,
						"输入金额格式不正确", Toast.LENGTH_LONG).show();
			}else if(Double.parseDouble(et_withdraw_to_number.getText().toString())<=5){
				Toast.makeText(WithdrawVerifyActivity.this,
						"转出金额必须高于5元", Toast.LENGTH_LONG).show();
			}else if(Double.parseDouble(avlNum)<Double.parseDouble(et_withdraw_to_number.getText().toString())){
				Toast.makeText(WithdrawVerifyActivity.this,
						"账户余额不足", Toast.LENGTH_LONG).show();
			}else{
					btn_mybank_withdrawVerify.setBackgroundResource(R.drawable.shape_button_gray);
					btn_mybank_withdrawVerify.setClickable(false);
				requestWithdrawVerify();
			}
			break;
		default:
			break;
		}
		
	}
	//确认提现
	public void requestWithdrawVerify(){
		String validCode  = et_withdraw_verifcode.getText().toString();
		String transAmount  = et_withdraw_to_number.getText().toString();
		
		HtmlRequest.getWithdrawVerify(WithdrawVerifyActivity.this,bankId,validCode,transAmount, new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				
				ResultWithdrawVerifyContentBean bean = (ResultWithdrawVerifyContentBean) params.result;
				if (bean!= null) {
					if(Boolean.parseBoolean(bean.getFlag())){
						Toast.makeText(WithdrawVerifyActivity.this,
								bean.getResultmp().getResult(), Toast.LENGTH_LONG).show();
						Intent i_mybank = new Intent();
						i_mybank.setClass(WithdrawVerifyActivity.this, WithdrawActivity.class);
						setResult(0, i_mybank);
						finish();
					}else{
						Toast.makeText(WithdrawVerifyActivity.this,
								bean.getResultmp().getResult(), Toast.LENGTH_LONG)
								.show();
					}
				} else {
					Toast.makeText(WithdrawVerifyActivity.this, "加载失败，请确认网络通畅",
							Toast.LENGTH_LONG).show();
				}
				btn_mybank_withdrawVerify.setBackgroundResource(R.drawable.shape_button);
				btn_mybank_withdrawVerify.setClickable(true);
			}
		});
		
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
		HtmlRequest.sentSMS(WithdrawVerifyActivity.this, userMobile,
				"withdraw", mathRandom,
				new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				
				ResultSentSMSContentBean bean = (ResultSentSMSContentBean) params.result;
				if (bean!= null) {
					if(Boolean.parseBoolean(bean.getFlag())){
						Toast.makeText(WithdrawVerifyActivity.this,
								"短信发送成功", Toast.LENGTH_LONG).show();
						smsflag = true;
						startThread();
					}else{
						smsflag = false;
						Toast.makeText(WithdrawVerifyActivity.this,
								bean.getMessage(), Toast.LENGTH_LONG)
								.show();
					}
//						System.out.println(bean.toString());
					
				} else {
					Toast.makeText(WithdrawVerifyActivity.this, "加载失败，请确认网络通畅",
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
	
	private void setButtonStyle(int time) {
		if (time == 0) {
			btn_withdraw_bank_sms.setText(getResources().getString(
					R.string.signup_getphoneauth));
			btn_withdraw_bank_sms.setClickable(true);
			btn_withdraw_bank_sms.setTextColor(getResources().getColor(R.color.txt_white));
			btn_withdraw_bank_sms.setBackgroundResource(R.drawable.shape_button);
		} else {
			if (time <= 59) {
				btn_withdraw_bank_sms.setClickable(false);
				btn_withdraw_bank_sms.setBackgroundResource(R.drawable.shape_button_gray_verify);
				btn_withdraw_bank_sms.setTextColor(getResources()
						.getColor(R.color.txt_gray));
				btn_withdraw_bank_sms.setText(time + btnString);
			}
		}
	}
	
	
}
