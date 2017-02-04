package com.cf360.act;

import com.cf360.R;
import com.cf360.bean.ResultCustomerInfoContentBean;
import com.cf360.bean.ResultMyCustomerInfoDetailContentBean;
import com.cf360.bean.ResultMyCustomerInfoDetailContentTwoBean;
import com.cf360.dialog.CustomerInfoPhoneDialog;
import com.cf360.dialog.CustomerInfoSmsDialog;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerInfoActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout rl_customer_has_baodan, rl_customer_day_load,
			rl_customer_base_info, rl_customer_phone,
			rl_customer_touzi_yixiang, rl_customer_money, rl_customer_instest;

	private TextView tv_his_end_baodan;
	private Button btn_sendSms, btn_call_phone;
	private String id = null;
	private String turnnumber = null;
	private ResultMyCustomerInfoDetailContentBean infoBean;

	private String phoneItems[] = { "" };
	private String smsItems[] = { "" };

	private Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_myperson_customer_info);
		initTopTitle();
		context = getApplicationContext();
		id = getIntent().getExtras().getString("id");
		turnnumber = getIntent().getExtras().getString("turnnumber");
		initView();

	}

	private void initData() {

		tv_his_end_baodan.setText("Ta成交的报单(" + turnnumber + ")");
		requestCustomerInfoData();
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		infoBean = new ResultMyCustomerInfoDetailContentBean();

		rl_customer_has_baodan = (RelativeLayout) findViewById(R.id.rl_customer_has_baodan);
		rl_customer_day_load = (RelativeLayout) findViewById(R.id.rl_customer_day_load);
		rl_customer_base_info = (RelativeLayout) findViewById(R.id.rl_customer_base_info);
		rl_customer_phone = (RelativeLayout) findViewById(R.id.rl_customer_phone);
		rl_customer_touzi_yixiang = (RelativeLayout) findViewById(R.id.rl_customer_touzi_yixiang);
		rl_customer_money = (RelativeLayout) findViewById(R.id.rl_customer_money);
		rl_customer_instest = (RelativeLayout) findViewById(R.id.rl_customer_instest);
		btn_sendSms = (Button) findViewById(R.id.btn_sendSms);
		btn_call_phone = (Button) findViewById(R.id.btn_call_phone);

		tv_his_end_baodan = (TextView) findViewById(R.id.tv_his_end_baodan);

		rl_customer_has_baodan.setOnClickListener(this);
		rl_customer_day_load.setOnClickListener(this);
		rl_customer_base_info.setOnClickListener(this);
		rl_customer_phone.setOnClickListener(this);
		rl_customer_touzi_yixiang.setOnClickListener(this);
		rl_customer_money.setOnClickListener(this);
		rl_customer_instest.setOnClickListener(this);
		btn_sendSms.setOnClickListener(this);
		btn_call_phone.setOnClickListener(this);
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;

	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(
				new Action(2, 0, R.color.blue_light),
				CustomerInfoActivity.this.getResources().getString(
						R.string.customer_delete_newcustomer));
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources()
								.getString(R.string.title_mycustomer_info))
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
						switch (id) {
						case 2:
							dialog();
							break;

						default:
							break;
						}
					}
				});
	}

	private void dialog() {
		// dialog参数设置
		AlertDialog.Builder builder = new AlertDialog.Builder(this); // 先得到构造器
		builder.setTitle("提示"); // 设置标题
		builder.setMessage("确定要删除该客户吗?"); // 设置内容
		// 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				requestData();
				// Toast.makeText(CustomerAddActivity.this, "确定",
				// Toast.LENGTH_SHORT).show();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// Toast.makeText(CustomerAddActivity.this, "取消",
				// Toast.LENGTH_SHORT).show();
			}
		});

		builder.create().show();
	}

	public void requestData() {

		HtmlRequest.getCustomerDetele(CustomerInfoActivity.this, id,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						ResultCustomerInfoContentBean data = (ResultCustomerInfoContentBean) params.result;
						if (data != null) {
							if (data.getFlag().equals("true")) {
								Toast.makeText(CustomerInfoActivity.this,
										"删除成功", Toast.LENGTH_SHORT).show();
								finish();
							}

						} else {

						}

					}
				});
	}

	/**
	 * 
	 * 获取客户信息
	 * 
	 */

	public void requestCustomerInfoData() {

		HtmlRequest.getCustomerInfo(CustomerInfoActivity.this, id,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						ResultMyCustomerInfoDetailContentTwoBean data = (ResultMyCustomerInfoDetailContentTwoBean) params.result;

						if (data != null) {
							infoBean = data.getUserCustomer();
							/*
							 * if(data.getFlag().equals("true")){
							 * Toast.makeText(CustomerInfoActivity.this, "删除成功",
							 * Toast.LENGTH_SHORT).show(); finish(); }
							 */
						} else {

						}

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_customer_has_baodan:

			Intent i_baodan = new Intent();
			i_baodan.setClass(CustomerInfoActivity.this,
					HisBaoDanActivity.class);
			i_baodan.putExtra("idcard", infoBean.getIdcard());
			startActivity(i_baodan);

			break;
		case R.id.rl_customer_day_load:

			Intent i_day_load = new Intent();
			i_day_load.setClass(CustomerInfoActivity.this,
					HisScheduleActivity.class);
			i_day_load.putExtra("id", infoBean.getId());
			startActivity(i_day_load);

			break;
		case R.id.rl_customer_base_info:

			Intent i_customer_base_info = new Intent();
			i_customer_base_info.setClass(CustomerInfoActivity.this,
					CustomerBaseInfoActivity.class);
			i_customer_base_info.putExtra("id", infoBean.getId());
			i_customer_base_info.putExtra("customerType",
					infoBean.getCustomerType());
			i_customer_base_info.putExtra("code", infoBean.getCode());
			i_customer_base_info.putExtra("name", infoBean.getName());
			i_customer_base_info.putExtra("sex", infoBean.getSex());
			i_customer_base_info.putExtra("age", infoBean.getAge());
			i_customer_base_info.putExtra("idcard", infoBean.getIdcard());
			i_customer_base_info.putExtra("nation", infoBean.getNation());
			i_customer_base_info.putExtra("company", infoBean.getCompany());
			i_customer_base_info.putExtra("position", infoBean.getPosition());
			i_customer_base_info.putExtra("profession",
					infoBean.getProfession());
			i_customer_base_info.putExtra("mobilePhone",
					infoBean.getMobilePhone());
			startActivity(i_customer_base_info);

			break;
		case R.id.rl_customer_phone:

			Intent i_customer_base_phone = new Intent();
			i_customer_base_phone.setClass(CustomerInfoActivity.this,
					CustomerPhoneActivity.class);
			i_customer_base_phone.putExtra("id", infoBean.getId());
			i_customer_base_phone
					.putExtra("homePhone", infoBean.getHomePhone());
			i_customer_base_phone.putExtra("mobilePhone",
					infoBean.getMobilePhone());
			i_customer_base_phone.putExtra("prePhone", infoBean.getPrePhone());
			i_customer_base_phone.putExtra("qqNumber", infoBean.getQqNumber());
			i_customer_base_phone.putExtra("fax", infoBean.getFax());
			i_customer_base_phone.putExtra("email", infoBean.getEmail());
			i_customer_base_phone.putExtra("birthProvince",
					infoBean.getBirthProvince());
			i_customer_base_phone
					.putExtra("birthCity", infoBean.getBirthCity());
			i_customer_base_phone.putExtra("liveProvince",
					infoBean.getLiveProvince());
			i_customer_base_phone.putExtra("liveCity", infoBean.getLiveCity());

			startActivity(i_customer_base_phone);

			break;

		case R.id.rl_customer_touzi_yixiang:

			Intent i_customer_touzi_yixiang = new Intent();
			i_customer_touzi_yixiang.setClass(CustomerInfoActivity.this,
					CustomerIntentionActivity.class);
			i_customer_touzi_yixiang.putExtra("id", infoBean.getId());
			i_customer_touzi_yixiang.putExtra("investType",
					infoBean.getInvestType());
			i_customer_touzi_yixiang.putExtra("investTrade",
					infoBean.getInvestTrade());
			i_customer_touzi_yixiang.putExtra("investMoney",
					infoBean.getInvestMoney());
			i_customer_touzi_yixiang.putExtra("investYear",
					infoBean.getInvestYear());
			i_customer_touzi_yixiang.putExtra("investIncome",
					infoBean.getInvestIncome());

			startActivity(i_customer_touzi_yixiang);

			break;
		case R.id.rl_customer_money:

			Intent i_customer_financial = new Intent();
			i_customer_financial.setClass(CustomerInfoActivity.this,
					CustomerFinancialActivity.class);
			i_customer_financial.putExtra("id", infoBean.getId());
			i_customer_financial.putExtra("car", infoBean.getCar());
			i_customer_financial.putExtra("house", infoBean.getHouse());
			i_customer_financial.putExtra("valueAssessment",
					infoBean.getValueAssessment());
			i_customer_financial.putExtra("yearSalary",
					infoBean.getYearSalary());

			startActivity(i_customer_financial);

			break;
		case R.id.rl_customer_instest:

			Intent i_customer_instest = new Intent();
			i_customer_instest.setClass(CustomerInfoActivity.this,
					CustomerInstestActivity.class);
			i_customer_instest.putExtra("id", infoBean.getId());
			i_customer_instest.putExtra("interestSport",
					infoBean.getInterestSport());
			i_customer_instest.putExtra("interestArt",
					infoBean.getInterestArt());
			i_customer_instest.putExtra("interestArder",
					infoBean.getInterestArder());

			startActivity(i_customer_instest);

			break;
		case R.id.btn_sendSms:
			/*
			 * String content ="1111111111111111111111111";//短信内容
			 * 
			 * String phone ="15666666666666666";//电话号码 SmsManager smsManager =
			 * SmsManager.getDefault(); ArrayList<String> texts =
			 * smsManager.divideMessage(content);
			 * 
			 * for(int i = 0;i<texts.size();i++){ String text =texts.get(i);
			 * smsManager.sendTextMessage(phone,null, content, null, null); }
			 */

			smsItems[0] = infoBean.getName() + ":" + infoBean.getMobilePhone();

			dialog1("发送短信",smsItems);
			break;
		case R.id.btn_call_phone:

			phoneItems[0] = infoBean.getName() + ":"
					+ infoBean.getMobilePhone();
			dialog2("拨打电话",phoneItems);
			break;

		default:
			break;
		}
	}
	private void dialog1(String title,String items[]){
		CustomerInfoSmsDialog dialog = new CustomerInfoSmsDialog(CustomerInfoActivity.this, title,items,infoBean.getMobilePhone());
		dialog.show();
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		android.view.WindowManager.LayoutParams p = dialog.getWindow()
				.getAttributes(); // 获取对话框当前的参数值
		// p.height = (int) (d.getHeight() * 0.3); //高度设置为屏幕的0.3
		p.width = (int) (d.getWidth()); // 宽度设置为全屏
		dialog.getWindow().setAttributes(p); // 设置生效

	}
	private void dialog2(String title,String items[]){
		CustomerInfoPhoneDialog dialog = new CustomerInfoPhoneDialog(CustomerInfoActivity.this, title,items,infoBean.getMobilePhone());
		dialog.show();
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		android.view.WindowManager.LayoutParams p = dialog.getWindow()
				.getAttributes(); // 获取对话框当前的参数值
		// p.height = (int) (d.getHeight() * 0.3); //高度设置为屏幕的0.3
		p.width = (int) (d.getWidth()); // 宽度设置为全屏
		dialog.getWindow().setAttributes(p); // 设置生效

	}

}
