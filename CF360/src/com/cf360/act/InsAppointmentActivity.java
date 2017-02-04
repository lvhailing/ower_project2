package com.cf360.act;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.bean.ResultDeclarationSearchCommissionratioDataBean;
import com.cf360.bean.ResultInsAppoint;
import com.cf360.bean.ResultPostInsAppoint;
import com.cf360.mould.BaseParams;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.StringUtil;
import com.cf360.view.AmountDialog;
import com.cf360.view.AmountDialog.OnSignContractChanged;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;

/**
 * 我要预约(保险)
 * 
 * @author hasee
 * 
 */

public class InsAppointmentActivity extends BaseActivity implements
		OnClickListener {
	private RelativeLayout InsuranceChild;
	private RelativeLayout InsuranceLimit;
	private RelativeLayout InsuranceAgecoverAge;
	private String[] insuranceChildIds;
	private String[] insuranceChildNames;
	private String[] insuranceLimits;
	private String[] insuranceAgecoverAges;
	private boolean[] defaultSelectedStatus;
	private TextView insuranceText;
	private TextView limitText;
	private TextView agecoverText;
	private int selectWhichAge = -1;
	private int selectWhichLimit = -1;
	private String commissionRatio;
	private String ageCoverage;
	private String payLimitTime;
	private String productId;
	private RelativeLayout financial;
	private RelativeLayout financialPhone;
	private String fuXianId;
	private TextView appointmentName;
	private TextView apphoneNumber;
	// private EditText eFinancial;
	// private EditText eFinancialPhone;
	private RelativeLayout fujiaxian;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private MouldList<MouldList<ResultDeclarationSearchCommissionratioDataBean>> content;
	private MouldList<ResultDeclarationSearchCommissionratioDataBean> listBean;
	private String insuranceAmount;
	private double amountSum;
	private String deputyInsuranceAmount;
	private TextView appMoney;
	private Button btn_post;
	private EditText appcontent;
	private String deputyInsuranceName;
	private EditText app_content;
	private String deputyRebateProportion;
	private String rebateProportion;
	private StringBuilder deputyInsuranceAmountSB1 = new StringBuilder();
	private ResultInsAppoint fdata;
	private MouldList<ResultDeclarationSearchCommissionratioDataBean> mouldList;
	private TextView tiTle;
	private ResultPostInsAppoint rdata;
	private boolean[] defaultSelectedStatusTemporary;
	private String currency = null;
	private String short_NAME;
	private int selectWhichAgeTemp;
	private int selectWhichLimitTemp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.ins_appointment);
		initTopTitle();
		initView();
		initDate();
	}

	private void initDate() {
		short_NAME = getIntent().getStringExtra("short_NAME");
		productId = getIntent().getStringExtra("productId");
		// Toast.makeText(InsAppointmentActivity.this, productId+"", 0).show();
		insuranceChildIds = getIntent().getStringArrayExtra("arrayChildId");
		// requestSearchCommissionratioData(productId, insuranceChildIds[0],
		// "(null)", "2");
		insuranceChildNames = getIntent().getStringArrayExtra("arrayChildName");
		insuranceLimits = getIntent().getStringArrayExtra("arrayLimit");
		insuranceAgecoverAges = getIntent()
				.getStringArrayExtra("arrayAgecover");
		currency = getIntent().getStringExtra("currency");

		defaultSelectedStatus = new boolean[insuranceChildNames.length];
		defaultSelectedStatusTemporary = new boolean[insuranceChildNames.length];

		// if (PreferenceUtil.getUserType().equals("corp")) {
		// financial.setVisibility(View.VISIBLE);
		// financialPhone.setVisibility(View.GONE);
		// }else{
		// financial.setVisibility(View.VISIBLE);
		// financialPhone.setVisibility(View.GONE);
		// }

		if (insuranceChildNames.length > 0) {
			InsuranceChild.setVisibility(View.VISIBLE);
		} else {
			InsuranceChild.setVisibility(View.GONE);
		}

		if (insuranceLimits.length > 0) {
			InsuranceLimit.setVisibility(View.VISIBLE);
		} else {
			InsuranceLimit.setVisibility(View.GONE);
		}

		if (insuranceAgecoverAges.length > 0) {
			InsuranceAgecoverAge.setVisibility(View.VISIBLE);
		} else {
			InsuranceAgecoverAge.setVisibility(View.GONE);
		}

		tiTle.setText(short_NAME);
		InsuranceChild.setOnClickListener(this);
		InsuranceLimit.setOnClickListener(this);
		InsuranceAgecoverAge.setOnClickListener(this);
		fujiaxian.setOnClickListener(this);
		btn_post.setOnClickListener(this);

	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		InsuranceChild = (RelativeLayout) findViewById(R.id.insuranceChild);
		InsuranceLimit = (RelativeLayout) findViewById(R.id.insuranceLimit);
		tiTle = (TextView) findViewById(R.id.appointment_title);
		InsuranceAgecoverAge = (RelativeLayout) findViewById(R.id.insuranceAgecoverAge);
		fujiaxian = (RelativeLayout) findViewById(R.id.ins_fujiaxian);
		insuranceText = (TextView) findViewById(R.id.insuranceChild_text);
		appointmentName = (TextView) findViewById(R.id.appointment_name);// 客户姓名，
		apphoneNumber = (TextView) findViewById(R.id.app_phoneNumber);
		apphoneNumber.setInputType(InputType.TYPE_CLASS_PHONE);// 电话
		appMoney = (android.widget.TextView) findViewById(R.id.app_money);
		limitText = (TextView) findViewById(R.id.insuranceLimit_text);
		agecoverText = (TextView) findViewById(R.id.insuranceAgecoverAge_text);
		btn_post = (Button) findViewById(R.id.btn_post);
		app_content = (EditText) findViewById(R.id.app_content);
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
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

	//

	public void showDialog(final String[] multiChoiceItems) {

		// 复选框默认值：false=未选;true=选中 ,各自对应items[i]
		final StringBuilder sb = new StringBuilder();
		final StringBuilder Ids = new StringBuilder();
		for (int i = 0; i < defaultSelectedStatus.length; i++) {
			defaultSelectedStatusTemporary[i] = defaultSelectedStatus[i];
		}
		// 创建对话框
		AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle("选择附加险")

				// 设置对话框标题
				.setMultiChoiceItems(multiChoiceItems,
						defaultSelectedStatusTemporary,
						new OnMultiChoiceClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked) {
								// 来回重复选择取消，得相应去改变item对应的bool值，点击确定时，根据这个bool[],得到选择的内容
								defaultSelectedStatusTemporary[which] = isChecked;
							}
						}) // 设置对话框[肯定]按钮
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sb.setLength(0);
						for (int i = 0; i < defaultSelectedStatus.length; i++) {
							defaultSelectedStatus[i] = defaultSelectedStatusTemporary[i];
							((AlertDialog) dialog).getListView()
									.setItemChecked(i,
											defaultSelectedStatusTemporary[i]);
						}
						for (int i = 0; i < defaultSelectedStatus.length; i++) {
							if (defaultSelectedStatus[i]) {
								sb.append(multiChoiceItems[i] + ",");
								Ids.append(insuranceChildIds[i] + ",");
							} else {
								if (sb.length() == 0) {
									insuranceText.setText("选择附加险");
								}
							}
						}

						if (sb.length() == 0) {
							insuranceText.setText("选择附加险");
						} else {
							StringBuilder deleteCharAt = Ids.deleteCharAt(Ids
									.length() - 1);
							fuXianId = deleteCharAt.toString();
							sb.deleteCharAt(sb.length() - 1);
							deputyInsuranceName = sb.toString();
							insuranceText.setText(sb.toString());
						}
						requestSearchCommissionratioData(productId, fuXianId,
								ageCoverage, payLimitTime);

					}
				}).setNegativeButton("取消", null)// 设置对话框[否定]按钮
				.show();
		/*
		 * for (int i = 0; i < defaultSelectedStatus.length; i++) {
		 * dialog.getListView().setItemChecked(i, defaultSelectedStatus[i]); }
		 */
	}

	public void showDialogSingleAge(final String[] singleChoiceItems,
			String title) {
		selectWhichAgeTemp = selectWhichAge;
		// 创建对话框
		new AlertDialog.Builder(this)
				.setTitle(title)
				.setSingleChoiceItems(singleChoiceItems, selectWhichAge,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								selectWhichAge = which;

								/*
								 * if (insuranceChildIds!= null) { productId =
								 * insuranceChildIds[which];
								 * 
								 * }
								 */

							}
						})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectWhichAgeTemp = selectWhichAge;
						if (selectWhichAge == -1) {
							agecoverText.setText("选择年龄");
						} else {
							String Age = singleChoiceItems[selectWhichAgeTemp];
							agecoverText.setText(Age);
						}
						ageCoverage = agecoverText.getText().toString();
						requestSearchCommissionratioData(productId, fuXianId,
								ageCoverage, payLimitTime);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectWhichAge = selectWhichAgeTemp;
					}
				}).show();
	}

	public void showDialogSingleLimit(final String[] singleChoiceItems,
			String title) {
		selectWhichLimitTemp = selectWhichLimit;
		// 创建对话框
		new AlertDialog.Builder(this)
				.setTitle(title)
				.setSingleChoiceItems(singleChoiceItems, selectWhichLimit,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								selectWhichLimit = which;

								// payLimitTime =
								// limitText.getText().toString();
								/*
								 * if (insuranceChildIds!= null) { productId =
								 * insuranceChildIds[which];
								 * 
								 * }
								 */

							}
						})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectWhichLimitTemp = selectWhichLimit;
						if (selectWhichLimit == -1) {
							limitText.setText("选择缴费年期");
						} else {
							String Limit = singleChoiceItems[selectWhichLimitTemp];
							limitText.setText(Limit);
						}
						payLimitTime = limitText.getText().toString();
						requestSearchCommissionratioData(productId, fuXianId,
								ageCoverage, payLimitTime);
					}
					// ageCoverage.equals("选择年龄")?"(null)"
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectWhichLimit = selectWhichLimitTemp;
					}
				}).show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.insuranceChild:
			showDialog(insuranceChildNames);
			break;

		case R.id.insuranceLimit:
			showDialogSingleLimit(insuranceLimits, "选择缴费年期");
			break;
		case R.id.insuranceAgecoverAge:
			showDialogSingleAge(insuranceAgecoverAges, "选择年龄");
			break;
		case R.id.ins_fujiaxian:
			// InsuranceChild InsuranceLimit insuranceAgecoverAges
			/*
			 * if(insuranceText.getText().toString().equals("选择附加险")&&InsuranceChild
			 * .getVisibility()==View.VISIBLE){
			 * Toast.makeText(InsAppointmentActivity.this, "请选择选择附加险",
			 * 0).show(); }else
			 */if (limitText.getText().toString().equals("选择缴费年期")
					&& InsuranceLimit.getVisibility() == View.VISIBLE) {
				Toast.makeText(InsAppointmentActivity.this, "请选择缴费年期", Toast.LENGTH_SHORT)
						.show();
			} else if (agecoverText.getText().toString().equals("选择年龄")
					&& InsuranceAgecoverAge.getVisibility() == View.VISIBLE) {
				Toast.makeText(InsAppointmentActivity.this, "请选择年龄", Toast.LENGTH_SHORT).show();
			} else {
				SelectIns();
			}
			break;
		case R.id.btn_post:
			// ageCoverage 投保年龄
			// amountSum amount 主险预约金额
			// customerName 客户姓名
			// customerPhone 客户手机号
			// deputyInsuranceAmount 副险预约金额
			// deputyInsuranceName 副险名称
			// deputyRebateProportion 副险返佣比例
			// payLimitTime 投保年限
			// productCategory 产品类型
			// private String deputyRebateProportion;
			// private String productCategory;

			String productName = getIntent().getStringExtra("name");
			String productCategory = getIntent().getStringExtra("category");
			String userName = appointmentName.getText().toString();
			String userPhone = apphoneNumber.getText().toString();
			String remark = app_content.getText().toString();
			// String userName=eFinancial.getText().toString();
			// String userPhone=eFinancialPhone.getText().toString();
			String b = deputyInsuranceAmountSB1.toString();
			if (b.length() > 0) {
				deputyRebateProportion = b.substring(0, b.length() - 1);
			} else {
				deputyRebateProportion = b.substring(0, b.length());
			}

			/* 只有用户名、密码不为空，并且用户名为11位手机号码才允许登陆 */

			/*
			 * if(insuranceText.getText().toString().equals("选择附加险")&&InsuranceChild
			 * .getVisibility()==View.VISIBLE){
			 * Toast.makeText(InsAppointmentActivity.this, "请选择选择附加险",
			 * 0).show(); }else
			 */if (limitText.getText().toString().equals("选择缴费年期")
					&& InsuranceLimit.getVisibility() == View.VISIBLE) {
				Toast.makeText(InsAppointmentActivity.this, "请选择缴费年期", Toast.LENGTH_SHORT)
						.show();
			} else if (agecoverText.getText().toString().equals("选择年龄")
					&& InsuranceAgecoverAge.getVisibility() == View.VISIBLE) {
				Toast.makeText(InsAppointmentActivity.this, "请选择年龄", Toast.LENGTH_SHORT).show();
			} else if (TextUtils.isEmpty(userName)) {
				Toast.makeText(InsAppointmentActivity.this, "理财师姓名不能为空！", Toast.LENGTH_SHORT)
						.show();
			} else if (TextUtils.isEmpty(userName)) {
				Toast.makeText(InsAppointmentActivity.this, "理财师号码不能为空", Toast.LENGTH_SHORT)
						.show();
			} else if (!(StringUtil.isMobileNO(userPhone))) {
				Toast.makeText(InsAppointmentActivity.this, "手机号格式错误！", Toast.LENGTH_SHORT)
						.show();
			} else if (appMoney.getText().toString().equals("预约金额")) {
				Toast.makeText(InsAppointmentActivity.this, "预约金额不能为空！", Toast.LENGTH_SHORT)
						.show();
			} else if (TextUtils.isEmpty(userName)) {
				Toast.makeText(InsAppointmentActivity.this, "理财师姓名不能为空！", Toast.LENGTH_SHORT)
						.show();
			} else if (TextUtils.isEmpty(userName)
					&& financial.getVisibility() == View.VISIBLE) {
				Toast.makeText(InsAppointmentActivity.this, "理财师姓名不能为空！", Toast.LENGTH_SHORT)
						.show();
			} else if (TextUtils.isEmpty(userPhone)
					&& financial.getVisibility() == View.VISIBLE) {
				Toast.makeText(InsAppointmentActivity.this, "理财师电话不能为空！", Toast.LENGTH_SHORT)
						.show();
			} else if (!(StringUtil.isMobileNO(userPhone))
					&& financial.getVisibility() == View.VISIBLE) {
				Toast.makeText(InsAppointmentActivity.this, "理财师手机号格式错误！", Toast.LENGTH_SHORT)
						.show();
			} else {
				if (mouldList.size() > 0) {
					requestPostAppintmentData(ageCoverage, insuranceAmount,
							currency, "(null)", "(null)",
							deputyInsuranceAmount, deputyInsuranceName,
							deputyRebateProportion, payLimitTime,
							productCategory, productId, short_NAME,
							rebateProportion, remark, appMoney.getText()
									.toString(), userName, userPhone);
				} else {
					Toast.makeText(InsAppointmentActivity.this, "请重新选择有效年龄", Toast.LENGTH_SHORT)
							.show();

				}
			}
		default:
			break;
		}
	}

	private void SelectIns() {

		new AmountDialog(InsAppointmentActivity.this,
				new OnSignContractChanged() {

					@Override
					public void onConfim(List<Map<String, Object>> mData) {
						data = mData;
						final StringBuilder deputyInsuranceAmountSB = new StringBuilder();
						// 投资金额
						amountSum = 0;
						for (int i = 0; i < data.size(); i++) {

							double amountValue = Double.valueOf((String) mData
									.get(i).get("value"));
							// double
							// a=(double)(Math.round(amountValue*100)/100.0);
							/*
							 * BigDecimal amountValue = new BigDecimal((String)
							 * mData.get(i).get( "value"));
							 */
							deputyInsuranceAmountSB.append(amountValue + ",");
							amountSum += amountValue;
							// amountSum=amountValue.add(amountValue).floatValue();
						}
						insuranceAmount = deputyInsuranceAmountSB.toString()
								.substring(
										0,
										deputyInsuranceAmountSB.toString()
												.indexOf(","));
						deputyInsuranceAmountSB
								.deleteCharAt(deputyInsuranceAmountSB.length() - 1);
						if (data.size() != 1) {
							deputyInsuranceAmount = deputyInsuranceAmountSB
									.toString().substring(
											deputyInsuranceAmountSB.toString()
													.indexOf(",") + 1,
											deputyInsuranceAmountSB.toString()
													.length());
						} else {
							deputyInsuranceAmount = "";
						}
						DecimalFormat df = new DecimalFormat("0.00 ");
						appMoney.setText(df.format(amountSum) + "");

					}

					@Override
					public void onCancel() {
						// TODO Auto-generated method stub

					}
				}, listBean, data).show();

	}

	/**
	 * 获取保险产品的返佣比例
	 */
	private void requestSearchCommissionratioData(final String productId,
			String deputyInsuranceName, String ageCoverage,
			String payLimitTimeOne) {
		if (insuranceText.getText().toString().equals("选择附加险")
				|| insuranceChildNames.length == 0) {
			deputyInsuranceName = "(null)";
		}
		if (limitText.getText().toString().equals("选择缴费年期")
				|| insuranceLimits.length == 0) {
			payLimitTimeOne = "(null)";
		}
		if (agecoverText.getText().toString().equals("选择年龄")
				|| insuranceAgecoverAges.length == 0) {
			ageCoverage = "(null)";
		}

		HtmlRequest.getAppointment(InsAppointmentActivity.this, productId,
				deputyInsuranceName, ageCoverage, payLimitTimeOne,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							fdata = (ResultInsAppoint) params.result;
							if (fdata.getResultCommissionData() != null) {
								content = fdata.getResultCommissionData();
								if (content.size() > 0) {
									// searchCommissionratioDataBeans = new
									// MouldList<ResultDeclarationSearchCommissionratioDataBean>();
									listBean = new MouldList<ResultDeclarationSearchCommissionratioDataBean>();
									deputyInsuranceAmountSB1.delete(0,
											deputyInsuranceAmountSB1.length());
									for (int i = 0; i < content.size(); i++) {
										mouldList = content.get(i);
										if (mouldList.size() > 0) {
											mouldList.get(0).setCurrency(
													currency);
											ResultDeclarationSearchCommissionratioDataBean databean = mouldList
													.get(0);
											listBean.add(databean);
											if (i == 0) {
												rebateProportion = listBean
														.get(0)
														.getCOMMISSIONRATIO();
											}
											if (i != 0
													&& (insuranceLimits != null || insuranceAgecoverAges != null)) {
												deputyRebateProportion = listBean
														.get(i)
														.getCOMMISSIONRATIO();
												deputyInsuranceAmountSB1
														.append(deputyRebateProportion
																+ ",");
											}

										} else {
											Toast.makeText(
													InsAppointmentActivity.this,
													"无法应比例请重新选择年龄", Toast.LENGTH_SHORT).show();
										}
									}
									data.clear();
									for (int i = 0; i < listBean.size(); i++) {
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("value", "");
										data.add(map);
									}
									appMoney.setText("预约金额");
									// appMoney.setText("预约金额");
								} else {
									limitText.setText("选择缴费年期");
									Toast.makeText(InsAppointmentActivity.this,
											"请选择缴费年期", Toast.LENGTH_SHORT).show();
								}
							}
						}
					}
				});
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

		HtmlRequest.PostInsuranceYuyue(InsAppointmentActivity.this,
				ageCoverage, amount, currency, customerName, customerPhone,
				deputyInsuranceAmount, deputyInsuranceName,
				deputyRebateProportion, payLimitTime, productCategory,
				productId, productName, rebateProportion, remark, totalamount,
				userName, userPhone, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						rdata = (ResultPostInsAppoint) params.result;
						if (rdata.getFlag().equals("true")) {
							Toast.makeText(InsAppointmentActivity.this, "提交成功",
									Toast.LENGTH_SHORT).show();
							finish();
							requestSmS(rdata.getProductName(),
									rdata.getAmount(),
									rdata.getAppointmentId(),
									"submitappointment");
						} else {
							Toast.makeText(InsAppointmentActivity.this,
									rdata.getMessage(), Toast.LENGTH_SHORT).show();
						}

					}
				});
	}

	private void requestSmS(String productName, String amount,
			String appoitmentId, String busiType) {

		HtmlRequest.sentSMSForAppointment(InsAppointmentActivity.this,
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

}
