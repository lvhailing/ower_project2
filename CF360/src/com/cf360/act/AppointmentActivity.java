package com.cf360.act;

import java.math.BigDecimal;

import com.cf360.R;
import com.cf360.bean.ResultAppointContentBean;
import com.cf360.bean.ResultAppointMnetBean;
import com.cf360.bean.ResultPostInsAppoint;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.uitls.StringUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我要预约(非保险)
 * 
 * @author hasee
 * 
 */

public class AppointmentActivity extends BaseActivity {

	private Button mButtonPost;
	private EditText appName;
	private EditText phoneNumber;
	private EditText appMoney;
	private EditText appContent;
	private String mButtonPost1;
	private String appName1;
	private String phoneNumber1;
	private String appMoney1;
	private String appContent1;
	private RelativeLayout Rfinancial;
	private RelativeLayout RfinancialPhone;
	private EditText financial;
	private EditText financialPhone;
	private String userName;
	private String userPhone;
	private BigDecimal IappMoney;
	private BigDecimal canAmount_appoint;
	private BigDecimal minAmount;
	private TextView title;
	private String productName;
	private TextView tv_appointment_hasAmount, tv_appointment_canAmount;
	private String canAmount = null;
	private String status = null;
	private String availableAmount = null;
	private String hasAmount = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.appointment);
		initTopTitle();
		canAmount = getIntent().getStringExtra("canAmount");
		status = getIntent().getStringExtra("status");
		availableAmount = getIntent().getStringExtra("availableAmount");
		hasAmount = getIntent().getStringExtra("hasAmount");
		// Toast.makeText(AppointmentActivity.this,
		// "availableAmount=="+availableAmount, 0).show();
		// Toast.makeText(AppointmentActivity.this, "canAmout=="+canAmount,
		// 0).show();
		// Toast.makeText(AppointmentActivity.this, "status=="+status,
		// 0).show();
		canAmount_appoint = new BigDecimal(canAmount);
		initView();
		initDate();
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		title = (TextView) findViewById(R.id.appointment_title);
		mButtonPost = (Button) findViewById(R.id.btn_post);
		appName = (EditText) findViewById(R.id.appointment_name);
		phoneNumber = (EditText) findViewById(R.id.app_phoneNumber);

		// phoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
		// et.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);邮箱
		phoneNumber.setInputType(InputType.TYPE_CLASS_PHONE);// 电话
		appMoney = (EditText) findViewById(R.id.app_money);
		appMoney.setInputType(InputType.TYPE_CLASS_PHONE);// 电话
		Rfinancial = (RelativeLayout) findViewById(R.id.rFinancial);
		RfinancialPhone = (RelativeLayout) findViewById(R.id.rFinancialPhone);
		financial = (EditText) findViewById(R.id.Financial);
		financialPhone = (EditText) findViewById(R.id.FinancialPhone);
		financialPhone.setInputType(InputType.TYPE_CLASS_PHONE);
		appContent = (EditText) findViewById(R.id.app_content);

		tv_appointment_hasAmount = (TextView) findViewById(R.id.tv_appointment_hasAmount);
		tv_appointment_canAmount = (TextView) findViewById(R.id.tv_appointment_canAmount);

		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
		
	}

	private void initDate() {
		if (PreferenceUtil.getUserType().equals("corp")) {
			Rfinancial.setVisibility(View.GONE);
			RfinancialPhone.setVisibility(View.GONE);
		} else {
			Rfinancial.setVisibility(View.GONE);
			RfinancialPhone.setVisibility(View.GONE);
		}
		productName = getIntent().getStringExtra("productName");
		title.setText(productName);

		tv_appointment_hasAmount.setText("已预约金额" + hasAmount + "万元");
		tv_appointment_canAmount.setText("可预约金额" + canAmount + "万元");

		mButtonPost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mButtonPost1 = mButtonPost.getText().toString();
				userName = appName.getText().toString();
				userPhone = phoneNumber.getText().toString();
				appMoney1 = appMoney.getText().toString();// 得到金额
				appContent1 = appContent.getText().toString();
				// userName = financial.getText().toString();
				// userPhone = financialPhone.getText().toString();
				String productId = getIntent().getStringExtra("productId");
				String productCategory = getIntent().getStringExtra(
						"productCategory");
				String MinAmount = getIntent().getStringExtra("minAmount");

				minAmount = new BigDecimal(MinAmount);
				// String value = appMoney.getText().toString();
				if (!appMoney1.equals("") && checkNumber(appMoney1)) {
					IappMoney = new BigDecimal(appMoney1);
				}

				// Toast.makeText(AppointmentActivity.this, id, 0).show();

				// if(Rfinancial.getVisibility()==View.GONE){
				// userName = "(null)";
				// userPhone = "(null)";
				// }

				/*
				 * boolean isTel = true; //标记位：true-是手机号码；false-不是手机号码
				 * 判断输入的用户名是否是电话号码 if (phoneNumber.getText().toString().length()
				 * == 11) { for (int i = 0; i <
				 * phoneNumber.getText().toString().length(); i++) { char c =
				 * phoneNumber.getText().toString().charAt(i); if
				 * (!Character.isDigit(c)) { isTel = false; break;
				 * //只要有一位不符合要求退出循环 } } } else { isTel = false; } boolean FisTel
				 * = true; //标记位：true-是手机号码；false-不是手机号码 判断输入的用户名是否是电话号码 if
				 * (financialPhone.getText().toString().length() == 11) { for
				 * (int i = 0; i < phoneNumber.getText().toString().length();
				 * i++) { char c = phoneNumber.getText().toString().charAt(i);
				 * if (!Character.isDigit(c)) { FisTel = false; break;
				 * //只要有一位不符合要求退出循环 } } } else { FisTel = false; }
				 */
				/* 只有用户名、密码不为空，并且用户名为11位手机号码才允许登陆 */
				if (TextUtils.isEmpty(appName.getText())) {
					Toast.makeText(AppointmentActivity.this, "理财师姓名不能为空！", Toast.LENGTH_SHORT)
							.show();
				} else if (TextUtils.isEmpty(phoneNumber.getText())) {
					Toast.makeText(AppointmentActivity.this, "理财师号码不能为空", Toast.LENGTH_SHORT)
							.show();
				} else if (!(StringUtil.isMobileNO(phoneNumber.getText()
						.toString()))) {
					Toast.makeText(AppointmentActivity.this, "理财师号码格式错误！", Toast.LENGTH_SHORT)
							.show();
				} else if (TextUtils.isEmpty(appName.getText())) {
					Toast.makeText(AppointmentActivity.this, "理财师姓名不能为空！", Toast.LENGTH_SHORT)
							.show();
				} else if (TextUtils.isEmpty(appMoney.getText())) {
					Toast.makeText(AppointmentActivity.this, "预约金额不能为空！", Toast.LENGTH_SHORT)
							.show();
				} else if (canAmount_appoint.compareTo(new BigDecimal(0.00)) == 0) {
					Toast.makeText(AppointmentActivity.this,
							"抱歉，该产品已预约满额无法再进行预约，如有问题请联系客服！", Toast.LENGTH_SHORT).show();
				} else if (checkNumber(appMoney1)
						&& minAmount.compareTo(IappMoney) == 1) {
					Toast.makeText(AppointmentActivity.this,
							"预约金额不能小于 " + minAmount, Toast.LENGTH_SHORT).show();
				} else if (checkNumber(appMoney1)
						&& canAmount_appoint.compareTo(IappMoney) == -1) {
					Toast.makeText(AppointmentActivity.this,
							"预约金额不能大于可预约金额 " + canAmount_appoint, Toast.LENGTH_SHORT).show();
				} else if (checkNumber(appMoney1)
						&& ((canAmount_appoint.subtract(IappMoney))
								.compareTo(minAmount) == -1 && canAmount_appoint
								.compareTo(IappMoney) != 0)) {
					Toast.makeText(AppointmentActivity.this,
							"剩余金额不能小于最小可预约金额 " + minAmount, Toast.LENGTH_SHORT).show();
				} else if (!(checkNumber(appMoney1))) {
					Toast.makeText(AppointmentActivity.this, "请输入正确的数字格式", Toast.LENGTH_SHORT)
							.show();
				} else if (!checkPoIntNumber(appMoney1)) {
					Toast.makeText(AppointmentActivity.this, "只能输入2位小数", Toast.LENGTH_SHORT)
							.show();
				} else if (TextUtils.isEmpty(financial.getText())
						&& Rfinancial.getVisibility() == View.VISIBLE) {
					Toast.makeText(AppointmentActivity.this, "理财师姓名不能为空！", Toast.LENGTH_SHORT)
							.show();
				} else if (TextUtils.isEmpty(financialPhone.getText())
						&& Rfinancial.getVisibility() == View.VISIBLE) {
					Toast.makeText(AppointmentActivity.this, "理财师电话不能为空！", Toast.LENGTH_SHORT)
							.show();
				} else if (!(StringUtil.isMobileNO(financialPhone.getText()
						.toString()))
						&& Rfinancial.getVisibility() == View.VISIBLE) {
					Toast.makeText(AppointmentActivity.this, "请输入11位手机号码！", Toast.LENGTH_SHORT)
							.show();
				} else {

					if (!status.equals("募集期")) {
						Toast.makeText(AppointmentActivity.this, "非募集期产品不能预约",
								Toast.LENGTH_SHORT).show();
					} else {

						// 执行所需要的操作
						requestPostAppintmentData("(null)", appMoney1, "万元",
								"(null)", "(null)", "(null)", "(null)",
								"(null)", "(null)", productCategory, productId,
								productName, "(null)", appContent1, appMoney1,
								userName, userPhone);
					}
				}

			}
		});
	}

	public static boolean checkNumber(String value) {
		String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
		return value.matches(regex);
	}

	public static boolean checkPoIntNumber(String value) {
		String regex = "^[0-9]+(.[0-9]{1,2})?$";

		return value.matches(regex);
	}

	/**
	 * 提交保险
	 */
	private void requestPostAppintmentData(final String ageCoverage,
			String amount, String currency, String customerName,
			String customerPhone, String deputyInsuranceAmount,
			String deputyInsuranceName, String deputyRebateProportion,
			String payLimitTime, String productCategory, String productId,
			String productName, String rebateProportion, String remark,
			final String totalamount, String userName, String userPhone) {

		HtmlRequest.PostInsuranceYuyue(AppointmentActivity.this, ageCoverage,
				amount, currency, customerName, customerPhone,
				deputyInsuranceAmount, deputyInsuranceName,
				deputyRebateProportion, payLimitTime, productCategory,
				productId, productName, rebateProportion, remark, totalamount,
				userName, userPhone, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							ResultPostInsAppoint data = (ResultPostInsAppoint) params.result;

							if (data.getFlag().equals("true")) {
								Toast.makeText(AppointmentActivity.this,
										"提交成功", Toast.LENGTH_SHORT).show();
								finish();
								requestSmS(data.getProductName(),
										data.getAmount(),
										data.getAppointmentId(),
										"submitappointment");
							} else {
								Toast.makeText(AppointmentActivity.this,
										data.getMessage(), Toast.LENGTH_SHORT).show();
							}

						}
					}
				});

	}

	private void requestSmS(String productName, String amount,
			String appoitmentId, String busiType) {

		HtmlRequest.sentSMSForAppointment(AppointmentActivity.this,
				productName, amount, appoitmentId, busiType,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {/*
													 * // ResultPostInsAppoint
													 * data
													 * =(ResultPostInsAppoint)
													 * params.result;
													 * 
													 * if(data.getFlag().equals(
													 * "true")){ Toast.makeText(
													 * AppointmentActivity.this,
													 * "提交成功", 0).show();
													 * finish(); }else{
													 * Toast.makeText
													 * (AppointmentActivity
													 * .this,data.getMessage(),
													 * 0).show(); }
													 */
						}
					}
				});

	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(new Action(2, 0, R.color.orange));
		title.setCenterText(getResources().getString(R.string.appointment))
				.setLogo(R.drawable.img_logo, false).setIndicator(R.drawable.back)
				.showMore(true).setOnActionListener(new OnActionListener() {
					@Override
					public void onBack() {
						finish();
					}

					@Override
					public void onAction(int id) {

					}

					@Override
					public void onMenu(int id) {

					}

				});

	}

}
