package com.cf360.act;

import com.cf360.R;
import com.cf360.bean.ResultAdviceContentBean;
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
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdviceActivity extends BaseActivity {
	private EditText et_advice_input,et_advice_input_email;
	private Button btn_setting_advice;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_advice);
		initTopTitle();
		initView();
		initData();
		
	}
	
	private void initData() {
		
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		et_advice_input = (EditText) findViewById(R.id.et_advice_input);
		et_advice_input_email = (EditText) findViewById(R.id.et_advice_input_email);
		btn_setting_advice = (Button) findViewById(R.id.btn_setting_advice);
		btn_setting_advice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(et_advice_input.getText().toString())){
					Toast.makeText(AdviceActivity.this, "请输入反馈意见", Toast.LENGTH_LONG).show();
				}else{
					requestAdviceData();
				}
			}
		});
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}
	
	//意见反馈
	public void requestAdviceData(){
		String content = et_advice_input.getText().toString();
		String email = et_advice_input_email.getText().toString();
		String mobile = null;
		try {
			mobile = DESUtil.decrypt(PreferenceUtil.getPhone());
		} catch (Exception e) {
			e.printStackTrace();
		}
		HtmlRequest.getAdviceData(AdviceActivity.this,content,mobile,email,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						ResultAdviceContentBean bean = (ResultAdviceContentBean) params.result;
						if (bean!= null) {
							if(Boolean.parseBoolean(bean.getFlag())){
								Toast.makeText(AdviceActivity.this, "意见反馈成功", Toast.LENGTH_LONG).show();
								finish();
							}else{
								Toast.makeText(AdviceActivity.this, "意见反馈失败，请您检查提交信息", Toast.LENGTH_LONG).show();
							}
						} else {
							Toast.makeText(AdviceActivity.this, "加载失败，请确认网络通畅",
									Toast.LENGTH_LONG).show();
						}

					}
				});
	}
	
	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_setting_advice))
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
	
}
