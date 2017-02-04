package com.cf360.act;

import com.cf360.R;
import com.cf360.bean.ResultSentSMSContentBean;
import com.cf360.bean.ResultUnBankMessageContentBean;
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
import android.widget.TextView;
import android.widget.Toast;

public class UnBandActivity extends BaseActivity implements OnClickListener{
	
	private String bankName = null;
	private String bankNumber = null;
	private String userName = null;
	private String bankId = null;
	private TextView mybank_name,mybank_bank_name,mybank_number,tv_unband_phone;
	private Button btn_unband_bank_sms,btn_mybank_unband;
	private EditText et_unband_verifcode;
	private String btnString;
	private boolean smsflag = false;
	private int time = 60;
	private int button = 0;
	private MyHandler mHandler;
	private boolean flag = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_unband_mybank);
		initTopTitle();
		bankName = getIntent().getExtras().getString("bankName");
		bankNumber = getIntent().getExtras().getString("bankNumber");
		userName = getIntent().getExtras().getString("userName");
		bankId = getIntent().getExtras().getString("bankId");
		
		initView();
		initData();
	}
	
	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_unbindmybank))
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
	
	private void initData() {
		mHandler = new MyHandler();
		btnString = getResources().getString(R.string.signup_time);
		setView();
	}

	private void setView() {
		mybank_name.setText(userName);
		mybank_bank_name.setText(bankName+": ");
		
		if(bankNumber.length()>4){
			mybank_number.setText(encryBankNum(bankNumber));
		}else{
			mybank_number.setText(bankNumber);
		}
		
		String phone = null;
		try {
			phone = DESUtil.decrypt(PreferenceUtil.getPhone());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		tv_unband_phone.setText(encryPhone(phone));
		
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

		mybank_name = (TextView) findViewById(R.id.mybank_name);
		mybank_bank_name = (TextView) findViewById(R.id.mybank_bank_name);
		mybank_number = (TextView) findViewById(R.id.mybank_number);
		btn_unband_bank_sms = (Button) findViewById(R.id.btn_unband_bank_sms);
		btn_mybank_unband = (Button) findViewById(R.id.btn_mybank_unband);
		et_unband_verifcode = (EditText) findViewById(R.id.et_unband_verifcode);
		tv_unband_phone = (TextView) findViewById(R.id.tv_unband_phone);
		
		btn_unband_bank_sms.setOnClickListener(this);
		btn_mybank_unband.setOnClickListener(this);
		
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
		case R.id.btn_unband_bank_sms:
			requestSMSData();
			break;
		case R.id.btn_mybank_unband:
			if(TextUtils.isEmpty(et_unband_verifcode.getText().toString())){
				Toast.makeText(UnBandActivity.this,
						"请输入验证码", Toast.LENGTH_LONG).show();
			}else{
				requestUnBindBankCard();
			}
			break;

		default:
			break;
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
		HtmlRequest.sentSMS(UnBandActivity.this, userMobile,
				"unbindbank", mathRandom,
				new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				
				ResultSentSMSContentBean bean = (ResultSentSMSContentBean) params.result;
				if (bean!= null) {
					if(Boolean.parseBoolean(bean.getFlag())){
						Toast.makeText(UnBandActivity.this,
								"短信发送成功", Toast.LENGTH_LONG).show();
						smsflag = true;
						startThread();
					}else{
						smsflag = false;
						Toast.makeText(UnBandActivity.this,
								bean.getMessage(), Toast.LENGTH_LONG)
								.show();
					}
//					System.out.println(bean.toString());
					
				} else {
					Toast.makeText(UnBandActivity.this, "加载失败，请确认网络通畅",
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
			btn_unband_bank_sms.setText(getResources().getString(
					R.string.signup_getphoneauth));
			btn_unband_bank_sms.setClickable(true);
			btn_unband_bank_sms.setTextColor(getResources().getColor(R.color.txt_white));
			btn_unband_bank_sms.setBackgroundResource(R.drawable.shape_button);
		} else {
			if (time <= 59) {
				btn_unband_bank_sms.setClickable(false);
				btn_unband_bank_sms.setBackgroundResource(R.drawable.shape_button_gray_verify);
				btn_unband_bank_sms.setTextColor(getResources()
						.getColor(R.color.txt_gray));
				btn_unband_bank_sms.setText(time + btnString);
			}
		}
	}
	
	
	//解绑银行卡
	public void requestUnBindBankCard(){
		String remoteValidateCode  = et_unband_verifcode.getText().toString();
		
		HtmlRequest.getUnBindBankCard(UnBandActivity.this,remoteValidateCode,bankId, new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				
				ResultUnBankMessageContentBean bean = (ResultUnBankMessageContentBean) params.result;
				if (bean!= null) {
					if(Boolean.parseBoolean(bean.getFlag())){
						Toast.makeText(UnBandActivity.this,
								"银行卡解绑成功", Toast.LENGTH_LONG).show();
						Intent i_mybank = new Intent();
						i_mybank.setClass(UnBandActivity.this, MyBankActivity.class);
//						startActivity(i_mybank);
						setResult(0, i_mybank);
						finish();
					}else{
						Toast.makeText(UnBandActivity.this,
								bean.getMessage(), Toast.LENGTH_LONG)
								.show();
					}
				} else {
					Toast.makeText(UnBandActivity.this, "加载失败，请确认网络通畅",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
	
}
