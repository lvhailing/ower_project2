package com.cf360.act;

import java.text.DecimalFormat;

import com.cf360.R;
import com.cf360.bean.ResultMyAccountContentBean;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyPersonActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout rl_myperson_financial, rl_myperson_insurance,
			rl_myperson_info, rl_myperson_bank;
	
	private Button btn_myperson_withdraw;
	private TextView tv_person_hasmoney,tv_myperson_totalincome,tv_myperson_acctbal;
	private TextView tv_myperson_financial,tv_myperson_financial_state,tv_myperson_insurance_state,tv_myperson_phone;
	private ResultMyAccountContentBean bean;
	private String type = null;
	private String phone = null;
	private RelativeLayout rl_myperson_mycustomer,rl_myperson_customer_follow;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_myperson);
		initTopTitle();
		initView();
		

	}

	private void initData() {
		requestMyPersonData();
	}

	//我的账户
	
	public void requestMyPersonData(){
		HtmlRequest.getMyAccount(MyPersonActivity.this, 
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						ResultMyAccountContentBean data = (ResultMyAccountContentBean) params.result;
						if (data!= null) {
							
							if(data.getAuditStatus()==null&&data.getInsuranceAgentAuditStatus()==null&&data.getTotalIncome()==null&&data.getUserAccount()==null){
								PreferenceUtil.setAutoLoginAccount("");
								PreferenceUtil.setAutoLoginPwd("");
								PreferenceUtil.setLogin(false);
								PreferenceUtil.setPhone("");
								PreferenceUtil.setUserId("");
								PreferenceUtil.setUserNickName("");
								PreferenceUtil.setCookie("");
								// i.putExtra("result", "exit");
								// setResult(9, i);
								Intent tent = new Intent("vjinkeexit");// 广播的标签，一定要和需要接受的一致。
								tent.putExtra("result", "exit");
								MyPersonActivity.this.sendBroadcast(tent);// 发送广播
								Intent intent=new Intent(MyPersonActivity.this,LoginActivity.class);
								startActivity(intent);
								finish();
							}else{
								bean = data;
							}
						} else {
							PreferenceUtil.setAutoLoginAccount("");
							PreferenceUtil.setAutoLoginPwd("");
							PreferenceUtil.setLogin(false);
							PreferenceUtil.setPhone("");
							PreferenceUtil.setUserId("");
							PreferenceUtil.setUserNickName("");
							PreferenceUtil.setCookie("");
							// i.putExtra("result", "exit");
							// setResult(9, i);
							Intent tent = new Intent("vjinkeexit");// 广播的标签，一定要和需要接受的一致。
							tent.putExtra("result", "exit");
							MyPersonActivity.this.sendBroadcast(tent);// 发送广播
//							Toast.makeText(context, "退出成功", Toast.LENGTH_LONG)
//									.show();
//							Intent i = new Intent();
//							i.setClass(MyPersonActivity.this, LoginActivity.class);
//							MyPersonActivity.this.startActivity(i);
							Intent intent=new Intent(MyPersonActivity.this,LoginActivity.class);
							startActivity(intent);
							finish();
						}
						setView();
					}
				});
	}
	
	private void setView() {
		if(bean.getUserAccount()!=null){
			DecimalFormat df1 = new DecimalFormat("0.00");
			double bond = Double.parseDouble(bean.getUserAccount().getAvlBal());
			tv_person_hasmoney.setText(df1.format(bond));
			bond = Double.parseDouble(bean.getUserAccount().getAcctBal());
			tv_myperson_acctbal.setText(df1.format(bond));
		}
		if(bean.getTotalIncome()!=null){
			DecimalFormat df1 = new DecimalFormat("0.00");
			double bond = Double.parseDouble(bean.getTotalIncome());
			tv_myperson_totalincome.setText(df1.format(bond));
		}
		if(bean.getAuditStatus()!=null){
			if(bean.getAuditStatus().equals("success")){
				tv_myperson_financial_state.setText("(已认证)");
			}else if(bean.getAuditStatus().equals("noAudit")){
				tv_myperson_financial_state.setText("(未认证)");
			}else if(bean.getAuditStatus().equals("fail")){
				tv_myperson_financial_state.setText("(认证失败)");
			}else if(bean.getAuditStatus().equals("unAudit")){
				tv_myperson_financial_state.setText("(认证中)");
			}else {
				tv_myperson_financial_state.setText("(未认证)");
			}
		}else{
			tv_myperson_financial_state.setText("(未认证)");
		}
		
		if(bean.getInsuranceAgentAuditStatus()!=null){
			if(bean.getInsuranceAgentAuditStatus().equals("success")){
				tv_myperson_insurance_state.setText("(已认证)");
			}else if(bean.getInsuranceAgentAuditStatus().equals("noAudit")){
				tv_myperson_insurance_state.setText("(未认证)");
			}else if(bean.getInsuranceAgentAuditStatus().equals("fail")){
				tv_myperson_insurance_state.setText("(认证失败)");
			}else if(bean.getInsuranceAgentAuditStatus().equals("unAudit")){
				tv_myperson_insurance_state.setText("(认证中)");
			}else {
				tv_myperson_insurance_state.setText("(未认证)");
			}
		}else{
			tv_myperson_insurance_state.setText("(未认证)");
		}
		
	}
	
	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		bean = new ResultMyAccountContentBean();
		rl_myperson_financial = (RelativeLayout) findViewById(R.id.rl_myperson_financial);
		rl_myperson_insurance = (RelativeLayout) findViewById(R.id.rl_myperson_insurance);
		rl_myperson_info = (RelativeLayout) findViewById(R.id.rl_myperson_info);
		rl_myperson_bank = (RelativeLayout) findViewById(R.id.rl_myperson_bank);
		btn_myperson_withdraw = (Button) findViewById(R.id.btn_myperson_withdraw);
		tv_person_hasmoney = (TextView) findViewById(R.id.tv_person_hasmoney);
		tv_myperson_totalincome = (TextView) findViewById(R.id.tv_myperson_totalincome);
		tv_myperson_acctbal = (TextView) findViewById(R.id.tv_myperson_acctbal);
		tv_myperson_financial = (TextView) findViewById(R.id.tv_myperson_financial);
		tv_myperson_financial_state = (TextView) findViewById(R.id.tv_myperson_financial_state);
		tv_myperson_insurance_state = (TextView) findViewById(R.id.tv_myperson_insurance_state);
		tv_myperson_phone = (TextView) findViewById(R.id.tv_myperson_phone);
		
		rl_myperson_mycustomer = (RelativeLayout) findViewById(R.id.rl_myperson_mycustomer);
		rl_myperson_customer_follow = (RelativeLayout) findViewById(R.id.rl_myperson_customer_follow);
		rl_myperson_mycustomer.setOnClickListener(this);
		rl_myperson_customer_follow.setOnClickListener(this);
		
		rl_myperson_financial.setOnClickListener(this);
		rl_myperson_insurance.setOnClickListener(this);
		rl_myperson_info.setOnClickListener(this);
		rl_myperson_bank.setOnClickListener(this);
		btn_myperson_withdraw.setOnClickListener(this);
		
		
		try {
			type = PreferenceUtil.getUserType();
			if(type.equals("corp")){
				tv_myperson_financial.setText("机构认证");
				rl_myperson_insurance.setVisibility(View.GONE);
			}else if(type.equals("person")){
				tv_myperson_financial.setText("理财师认证");
				rl_myperson_insurance.setVisibility(View.VISIBLE);
			}else{
				tv_myperson_financial.setText("理财师认证");
				rl_myperson_insurance.setVisibility(View.VISIBLE);
			}
			phone = DESUtil.decrypt(PreferenceUtil.getPhone());
			tv_myperson_phone.setText(encryPhone(phone));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
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
						getResources().getString(R.string.title_myperson))
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
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.rl_myperson_financial:
			Intent i_financial = new Intent();
			if(type.equals("corp")){
				i_financial
				.setClass(MyPersonActivity.this, InsuranceAgentAudit.class);
//				i_financial.putExtra("orgId", bean.getUserAccount().getUsrInfoId());
//				i_financial.putExtra("orgId", "15092910065441986154");	//15091916423295573298
			}else if(type.equals("person")){
				i_financial
				.setClass(MyPersonActivity.this, FinancialActivity.class);
			}else{
				i_financial
				.setClass(MyPersonActivity.this, FinancialActivity.class);
			}
			
			startActivity(i_financial);
			break;
		case R.id.rl_myperson_insurance:
			Intent i_insurance = new Intent();
			i_insurance
					.setClass(MyPersonActivity.this, InsuranceActivity.class);
			if(bean.getAuditStatus().equals("success")){
				startActivity(i_insurance);
			}else{
				Toast.makeText(MyPersonActivity.this, "理财师未认证成功", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.rl_myperson_info:
			Intent i_info = new Intent();
			i_info.setClass(MyPersonActivity.this, InfoActivity.class);
			startActivity(i_info);
			break;
		case R.id.rl_myperson_bank:
			Intent i_bank = new Intent();
			i_bank.setClass(MyPersonActivity.this, MyBankActivity.class);
			i_bank.putExtra("realName", bean.getRealName());
			
			if(bean.getAuditStatus().equals("success")){
				startActivity(i_bank);
			}else{
				Toast.makeText(MyPersonActivity.this, "请您在通过理财师认证后再进行银行卡操作！", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.btn_myperson_withdraw:
			Intent i_withdraw = new Intent();
			i_withdraw.putExtra("avlBal", bean.getUserAccount().getAvlBal());
			i_withdraw.putExtra("realName", bean.getRealName());
			i_withdraw.setClass(MyPersonActivity.this, WithdrawActivity.class);
			
			if(bean.getAuditStatus().equals("success")){
				startActivity(i_withdraw);
			}else{
				Toast.makeText(MyPersonActivity.this, "请您在通过理财师认证后再进行提现！", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.rl_myperson_mycustomer:
			Intent i_customer = new Intent();
//			i_customer.putExtra("avlBal", bean.getUserAccount().getAvlBal());
			i_customer.setClass(MyPersonActivity.this, CustomerActivity.class);
			startActivity(i_customer);
			
			break;
		case R.id.rl_myperson_customer_follow:
			Intent i_customer_follow = new Intent();
//			i_withdraw.putExtra("avlBal", bean.getUserAccount().getAvlBal());
			i_customer_follow.setClass(MyPersonActivity.this, CustomerFollowActivity.class);
			startActivity(i_customer_follow);
//			requestData();
			break;
			
		default:
			break;
		}

	}
	
	
	
	
}
