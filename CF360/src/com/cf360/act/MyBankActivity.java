package com.cf360.act;

import com.cf360.R;
import com.cf360.adapter.MyBankAdapter;
import com.cf360.bean.MyBankItemBean;
import com.cf360.bean.ResultHotProductContentBean;
import com.cf360.bean.ResultMyBankListContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MyBankActivity extends BaseActivity {
	
	private ListView lv_mybank;
	private MouldList<MyBankItemBean> list;
	private Button btn_mybank_add;
	private String realName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_mybank);
		initTopTitle();
		initView();
		
	}
	
	private void initData() {
//		test();
		requestMyBankData();
		
		
	}

	//我的银行卡
	public void requestMyBankData(){
		HtmlRequest.getMyBankList(MyBankActivity.this, 
				new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				
				ResultMyBankListContentBean bean = (ResultMyBankListContentBean) params.result;
				if (bean!= null) {
					
					MyBankAdapter adapter = new MyBankAdapter(MyBankActivity.this,bean.getBankList());
					
					lv_mybank.setAdapter(adapter);
					
				} else {
					
				}
				
			}
		});
		
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		realName = getIntent().getExtras().getString("realName");
				
		lv_mybank = (ListView) findViewById(R.id.lv_mybank);
		btn_mybank_add = (Button) findViewById(R.id.btn_mybank_add);
		
		btn_mybank_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i_addBank = new Intent();
				i_addBank.setClass(MyBankActivity.this, AddBankActivity.class);
				i_addBank.putExtra("realName", realName);
				
				
				startActivity(i_addBank);
				
			}
		});
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
						getResources().getString(R.string.title_mybank))
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
		initData();
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
