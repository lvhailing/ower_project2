package com.cf360.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.cf360.R;
import com.cf360.adapter.WithdrawBankAdapter;
import com.cf360.bean.ResultChoseBankListContentBean;
import com.cf360.bean.ResultMyBankListContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

/**
 * 确认提现
 * @author hasee
 *
 */
public class WithdrawActivity extends BaseActivity{

	private ListView lv_withdraw_mybank;
	private Button btn_withdraw_mybank_add;
	private String avlBal = "0";
	private String realName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_withdraw);
		initTopTitle();
		avlBal = getIntent().getExtras().getString("avlBal");
		realName = getIntent().getExtras().getString("realName");
		initView();
		
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		lv_withdraw_mybank = (ListView) findViewById(R.id.lv_withdraw_mybank);
		btn_withdraw_mybank_add = (Button) findViewById(R.id.btn_withdraw_mybank_add);
		
		btn_withdraw_mybank_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i_addBank = new Intent();
				
				i_addBank.setClass(WithdrawActivity.this, AddBankActivity.class);
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

	private void initData() {
		requestChoseBankData();
	}
	//选择银行卡
	public void requestChoseBankData(){
		HtmlRequest.getChoseBankList(WithdrawActivity.this, 
				new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				
				final ResultChoseBankListContentBean bean = (ResultChoseBankListContentBean) params.result;
				if (bean!= null) {
					
					WithdrawBankAdapter adapter = new WithdrawBankAdapter(WithdrawActivity.this,bean.getBankList());
					
					lv_withdraw_mybank.setAdapter(adapter);
					lv_withdraw_mybank.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							Intent i_withdraw = new Intent();
							i_withdraw.setClass(WithdrawActivity.this, WithdrawVerifyActivity.class);
							i_withdraw.putExtra("bankName", bean.getBankList().get(arg2).getUsrCustId());
							i_withdraw.putExtra("bankNum", bean.getBankList().get(arg2).getOpenAcctId());
							i_withdraw.putExtra("bankId", bean.getBankList().get(arg2).getId());
							i_withdraw.putExtra("avlNum", bean.getAvlBal());
							i_withdraw.putExtra("userName", bean.getBankList().get(arg2).getUserName());
							
							startActivity(i_withdraw);
						}
					});
					
				} else {
					
				}
				
			}
		});
		
	}
	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_withdraw))
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
		initData();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
