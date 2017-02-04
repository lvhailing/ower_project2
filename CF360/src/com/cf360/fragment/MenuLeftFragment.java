package com.cf360.fragment;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Observable;
import java.util.Observer;

import com.cf360.R;
import com.cf360.act.ContractActivity;
import com.cf360.act.DeclarationActivity;
import com.cf360.act.FocusActivity;
import com.cf360.act.LoginActivity;
import com.cf360.act.MContentAllActivity;
import com.cf360.act.MainActivity;
import com.cf360.act.MyPersonActivity;
import com.cf360.act.OrderActivity;
import com.cf360.act.SettingActivity;
import com.cf360.act.ToubaodanActivity;
import com.cf360.bean.ResultMyAccountContentBean;
import com.cf360.bean.ResultUserLoginContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.net.UserLogin;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.PreferenceUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuLeftFragment extends Fragment implements OnClickListener, Observer{
	private static final String tag = "MenuLeftFragment";
	private View mView;
	private RelativeLayout layout_main, layout_productcenter, layout_focus,
			layout_declaration, layout_order, layout_contract,
			layout_toubaodan, layout_setting,left_menu_myperson,rl_left_menu_myaccount_login,rl_left_menu_myaccount_unlogin;
	private ImageView btn_left_menu_login;
	private TextView left_menu_name,left_menu_phone,left_menu_money,tv_myperson;
	private ImageView left_menu_auth;
	private Context context;
	private ResultMyAccountContentBean myaccountBean;
	private ResultUserLoginContentBean bean;
	private ImageView left_menu_img;
	private MainActivity activity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		activity = (MainActivity)getActivity();
		UserLogin.getInstance().addObserver(this);
		registerBoradcastReceiver();
		if (mView == null) {
			initView(inflater, container);
		}
//		Log.e(tag, "onCreateView");
		return mView;
	}

	private void initView(LayoutInflater inflater, ViewGroup container) {
		myaccountBean = new ResultMyAccountContentBean();
		mView = inflater.inflate(R.layout.left_menu, container, false);
		layout_main = (RelativeLayout) mView.findViewById(R.id.left_menu_main);
		layout_productcenter = (RelativeLayout) mView
				.findViewById(R.id.left_menu_productcenter);
		layout_focus = (RelativeLayout) mView
				.findViewById(R.id.left_menu_focus);
		layout_declaration = (RelativeLayout) mView
				.findViewById(R.id.left_menu_declaration);
		layout_order = (RelativeLayout) mView
				.findViewById(R.id.left_menu_order);
		layout_contract = (RelativeLayout) mView
				.findViewById(R.id.left_menu_contract);
		layout_toubaodan = (RelativeLayout) mView
				.findViewById(R.id.left_menu_toubaodan);
		layout_setting = (RelativeLayout) mView
				.findViewById(R.id.left_menu_setting);
		btn_left_menu_login = (ImageView) mView.findViewById(R.id.btn_left_menu_login);
		left_menu_myperson = (RelativeLayout) mView.findViewById(R.id.left_menu_myperson);
		left_menu_img = (ImageView) mView.findViewById(R.id.left_menu_img);
		tv_myperson = (TextView) mView.findViewById(R.id.tv_myperson);
		
		left_menu_name = (TextView) mView.findViewById(R.id.left_menu_name);
		left_menu_phone = (TextView) mView.findViewById(R.id.left_menu_phone);
		left_menu_money = (TextView) mView.findViewById(R.id.left_menu_money);
		left_menu_auth = (ImageView) mView.findViewById(R.id.left_menu_auth);
		
		rl_left_menu_myaccount_login = (RelativeLayout) mView.findViewById(R.id.rl_left_menu_myaccount_login);
		rl_left_menu_myaccount_unlogin = (RelativeLayout) mView.findViewById(R.id.rl_left_menu_myaccount_unlogin);
		rl_left_menu_myaccount_login.setVisibility(View.GONE);
		rl_left_menu_myaccount_unlogin.setVisibility(View.GONE);
		setView();
		
//		left_menu_img.setOnClickListener(this);
//		tv_myperson.setOnClickListener(this);
		
		left_menu_myperson.setOnClickListener(this);
		layout_main.setOnClickListener(this);
		layout_productcenter.setOnClickListener(this);
		layout_focus.setOnClickListener(this);
		layout_declaration.setOnClickListener(this);
		layout_order.setOnClickListener(this);
		layout_contract.setOnClickListener(this);
		layout_toubaodan.setOnClickListener(this);
		layout_setting.setOnClickListener(this);
		
		rl_left_menu_myaccount_unlogin.setOnClickListener(this);
	}

	private void setView() {
		boolean islogin = PreferenceUtil.isLogin();
		if(islogin){
			rl_left_menu_myaccount_login.setVisibility(View.VISIBLE);
			rl_left_menu_myaccount_unlogin.setVisibility(View.GONE);
			requestMyAccountData();
			
		}else{
			rl_left_menu_myaccount_login.setVisibility(View.GONE);
			rl_left_menu_myaccount_unlogin.setVisibility(View.VISIBLE);
			btn_left_menu_login.setOnClickListener(this);
		}
	}

	private void initData() {
		
		try {
			left_menu_phone.setText(encryPhone(DESUtil.decrypt(PreferenceUtil.getPhone())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(myaccountBean.getUserAccount()!=null){
			left_menu_name.setText(myaccountBean.getUserAccount().getUsrName());
			DecimalFormat df1 = new DecimalFormat("0.00");
			double bond = Double.parseDouble(myaccountBean.getUserAccount().getAvlBal());
			
			left_menu_money.setText("¥"+df1.format(bond));
		}else{
			left_menu_name.setText("");
			left_menu_money.setText("");
		}
		
		if(myaccountBean.getAuditStatus()!=null){
			
			if(myaccountBean.getAuditStatus().equals("success")){
				left_menu_auth.setImageResource(R.drawable.img_renzhenged);
			}else{
				left_menu_auth.setImageResource(R.drawable.img_renzhenging);
			}
		}else{
			left_menu_auth.setImageResource(R.drawable.img_renzhenging);
		}
		
	}

	@Override
	public void onResume() {
		super.onResume();
		setView();
//		Log.e(tag, "==onResume==");
	}
	
	public String encryPhone(String phone){
		StringBuffer newphone = new StringBuffer();
		newphone.append(phone.subSequence(0, 3));
		newphone.append("****");
		newphone.append(phone.substring(7, 11));
		
		return newphone.toString();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_menu_myperson:	//我的财富
			if(!PreferenceUtil.isLogin()){
				GoActivity(LoginActivity.class);
			}else{
				GoActivity(MyPersonActivity.class);
			}
			break;
		case R.id.left_menu_main:// 首页
			//要在跳转的activity中onCreate方法加  setBehindContentView(R.layout.left_menu);
//			requestMyAccountData();
			context.startActivity(new Intent(context,MainActivity.class));
			activity.finish();
//			activity.getSlidingMenu().showMenu();
//			SlidingMenu menu = ((SlidingFragmentActivity) context).getSlidingMenu();
//			menu.showMenu();
			
			break;

		case R.id.left_menu_productcenter:// 产品中心
			startActivity(new Intent(context,MContentAllActivity.class));
			break;

		case R.id.left_menu_focus:// 我的关注
			if(!PreferenceUtil.isLogin()){
				GoActivity(LoginActivity.class);
			}else{
				GoActivity(FocusActivity.class);
			}
			
			break;

		case R.id.left_menu_declaration:// 我的报单
			
			if(!PreferenceUtil.isLogin()){
				GoActivity(LoginActivity.class);
			}else{
				GoActivity(DeclarationActivity.class);
			}
			break;

		case R.id.left_menu_order:// 我的预约
			if(!PreferenceUtil.isLogin()){
				GoActivity(LoginActivity.class);
			}else{
				GoActivity(OrderActivity.class);
			}
			
			break;

		case R.id.left_menu_contract:// 我的合同
			
			if(!PreferenceUtil.isLogin()){
				GoActivity(LoginActivity.class);
			}else{
				GoActivity(ContractActivity.class);
			}
			break;

		case R.id.left_menu_toubaodan:// 我的投保单
			
			if(!PreferenceUtil.isLogin()){
				GoActivity(LoginActivity.class);
			}else{
				GoActivity(ToubaodanActivity.class);
			}
			break;

		case R.id.left_menu_setting:// 设置
			GoActivity(SettingActivity.class);
			break;
		case R.id.btn_left_menu_login://登录
			GoActivity(LoginActivity.class);
			break;
		default:
			break;
		}
	}
	private void GoActivity(Class<?> activity){
		Intent intent=new Intent(getActivity(),activity);
		startActivity(intent);
	}
	//我的账户
	public void requestMyAccountData(){
		HtmlRequest.getMyAccount(context, 
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
//								PreferenceUtil.setCookie("");
								// i.putExtra("result", "exit");
								// setResult(9, i);
								Intent tent = new Intent("vjinkeexit");// 广播的标签，一定要和需要接受的一致。
								tent.putExtra("result", "exit");
								context.sendBroadcast(tent);// 发送广播
							}else{
								myaccountBean = data;
								initData();
							}
						} else {

							/*PreferenceUtil.setAutoLoginAccount("");
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
							context.sendBroadcast(tent);// 发送广播*/
//							Intent i = new Intent();
//							i.setClass(context, LoginActivity.class);
//							context.startActivity(i);
//							setView();


						}

					}
				});
	}
	
	private ReceiveBroadCast receiveBroadCast; // 广播实例
	String myActionName = "vjinkeexit";

	// 注册广播
	public void registerBoradcastReceiver() {
		receiveBroadCast = new ReceiveBroadCast();
		IntentFilter filter = new IntentFilter();
		filter.addAction(myActionName); // 只有持有相同的action的接受者才能接收此广播
		context.registerReceiver(receiveBroadCast, filter);
//		Log.e(tag, "registerBoradcastReceiver====");
	}

	// 定义一个BroadcastReceiver广播接收类：
	public class ReceiveBroadCast extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent data) {
			String actionName = data.getAction();
//			Log.e(tag, "actionName=="+actionName+"==data.getExtras()"+data.getExtras());
			if (actionName.equals(myActionName)) {
				// 得到广播中得到的数据，并显示出来
				Bundle extras = data.getExtras();
				if (extras != null) {
					setView();
				}
			}
		}
	}
	
	@Override
	public void update(Observable observable, Object data) {
		bean = (ResultUserLoginContentBean) data;
		if (bean != null) {
			if (Boolean.parseBoolean(bean.getFlag())) {
				setView();
			}
		}
	}
	
}