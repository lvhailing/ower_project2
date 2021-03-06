package com.cf360.act;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.VjinkeApplication;
import com.cf360.VjinkeApplication.NetListener;
import com.cf360.bean.ResultIsLoginContentBean;
import com.cf360.bean.ResultMyAccountContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.BaseRequester;
import com.cf360.mould.HtmlRequest;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.TitleBar;
import com.cf360.widget.CustomProgressDialog;

import java.util.Timer;
import java.util.TimerTask;

public class BaseMainActivity extends AbsBaseMainActivity implements NetListener {

	private TitleBar title;
	// private MyProgressDialog progressDialog;
	private CustomProgressDialog dialog;
	private ActivityStack stack;
	private Timer timer;
	private ResultIsLoginContentBean data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.base);

		setBehindContentView(R.layout.left_menu);
		stack = ActivityStack.getActivityManage();
		VjinkeApplication apl = (VjinkeApplication) getApplicationContext();
		apl.registReceiver();
		data = new ResultIsLoginContentBean();
	}
	int i_num = 0;
	public void timer1() {


		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				if(PreferenceUtil.isLogin()){
//					System.out.println("BaseMainActivity" + i_num);
					Log.e("BaseMainActivity", "i_num" + i_num++);
					//	requestMyAccountData();
					requestIsLogin();
				}

			}
		}, 2000);// 设定指定的时间time,此处为2000毫秒
	}

	//是否登录
	public  void requestIsLogin(){

		String userId = null;
		try {
			userId = DESUtil.decrypt(PreferenceUtil.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		String token = null;
		try {
			token = DESUtil.decrypt(PreferenceUtil.getToken());
		} catch (Exception e) {
			e.printStackTrace();
		}

		HtmlRequest.getIsLogin(this,userId,token,
				new BaseRequester.OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {
						data = (ResultIsLoginContentBean) params.result;
						if (data != null) {
							if (data.getFlag().equals("false")) {
								if (PreferenceUtil.isLogin()) {

									dialog2(BaseMainActivity.this);

//									stack.removeAllActivityExceptOne("MainActivity");
//									Toast.makeText(BaseActivity.this,"登录信息有误，请重新登录", Toast.LENGTH_LONG).show();
								}
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
								BaseMainActivity.this.sendBroadcast(tent);// 发送广播
							} else {
							}
						} else {
							/*if (PreferenceUtil.isLogin()) {

								dialog2(BaseMainActivity.this);

//								stack.removeAllActivityExceptOne("MainActivity");
//								Toast.makeText(BaseActivity.this,"登录信息有误，请重新登录", Toast.LENGTH_LONG).show();
							}
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
							BaseMainActivity.this.sendBroadcast(tent);// 发送广播
//							Intent i = new Intent();
//							i.setClass(context, LoginActivity.class);
//							context.startActivity(i);
//							setView();*/
						}
						timer1();
					}
				});
	}
	public void baseSetContentView(int layoutResId) {
		LinearLayout llContent = (LinearLayout) findViewById(R.id.content);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// progressDialog = new MyProgressDialog(BaseActivity.this);
		// progressDialog.setMessage(getResources().getString(
		// R.string.order_load_toast));
		// progressDialog.setCanceledOnTouchOutside(false);
		dialog = new CustomProgressDialog(this, "", R.anim.frame_loading);
		View v = inflater.inflate(layoutResId, null);
		llContent.addView(v);
	}

	public void startLoading() {
		if (dialog != null && !dialog.isShowing()) {
			dialog.show();
		}
	}

	public void stopLoading() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
//		timer1();
		VjinkeApplication apl = (VjinkeApplication) getApplication();
		apl.addNetListener(this);
		onNetWorkChange(apl.netType);
	}

	@Override
	protected void onPause() {
		super.onPause();
//		timer.cancel();
		VjinkeApplication apl = (VjinkeApplication) getApplication();
		apl.removeNetListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		VjinkeApplication apl = (VjinkeApplication) getApplicationContext();
		apl.unRegisterNetListener();

	}

	@Override
	public void onNetWorkChange(String netType) {
		LayoutInflater inflater= (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
		View view=inflater.inflate(R.layout.net_fail,null);
		View netHint = view.findViewById(R.id.netfail_hint);
//		View netHint = findViewById(R.id.netfail_hint);
		netHint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Settings.ACTION_SETTINGS);
				startActivity(intent);
			}
		});
		if (netHint != null) {
			boolean netFail = TextUtils.isEmpty(netType);
			netHint.setVisibility(netFail ? View.VISIBLE : View.GONE);
//			netHint.setVisibility(View.VISIBLE);
		}
	}
	private void dialog2(Context context) {
		// dialog参数设置
		AlertDialog.Builder builder = new AlertDialog.Builder(context); // 先得到构造器
		builder.setTitle("提示"); // 设置标题
		if(data!=null){
			if(!TextUtils.isEmpty(data.getMessage())){
				builder.setMessage(data.getMessage()); //设置内容
			}else{

			}
		}


		// 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int i) {
				dialog.dismiss();

				stack.removeAllActivityExceptOne("MainActivity");
			}
		});
//		builder.create().show();
		AlertDialog ad = builder.create();
		ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		ad.setCanceledOnTouchOutside(false);
		ad.show();
	}
}
