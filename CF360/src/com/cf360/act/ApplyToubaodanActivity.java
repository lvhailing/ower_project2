package com.cf360.act;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.ApplicationConsts;
import com.cf360.R;
import com.cf360.adapter.FinancialCardAdapter;
import com.cf360.bean.CityBean;
import com.cf360.bean.ResultApplyToubaodanContentBean;
import com.cf360.bean.ResultDeclarationSearchCommissionratioListContentBean;
import com.cf360.bean.ResultSelectProductReturnInsuranceAgecoverAgeBean;
import com.cf360.bean.ResultSelectProductReturnInsuranceChildBean;
import com.cf360.bean.ResultSelectProductReturnInsuranceLimitBean;
import com.cf360.bean.ResultSelectProductReturnListContentBean;
import com.cf360.bean.ResultSentSMSContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.uitls.StringUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

/**
 * 申请投保单
 * 
 */
public class ApplyToubaodanActivity extends BaseActivity implements
		OnClickListener {
	private ScrollView scrollView;
	private RelativeLayout layout_selectProduct;
	private TextView selectProduct;
	private TextView edt_insurance;// 选择附加险
	private TextView edt_age;// 选择年龄
	private TextView edt_date;// 选择缴费年期
	private TextView edt_insuredAge;// 被保人年龄
	private EditText edt_customerName;// 客户姓名
	// private EditText edt_amount;// 打款金额
	private EditText edt_userName;// 理财师
	private EditText edt_userPhone;// 理财师电话
	private EditText edt_accepter;// 收件人
	private EditText edt_acceptPhone;// 收件人手机号码
	private EditText edt_acceptAddress;// 收件人地址

	private String productId;
	private String productName;
	private String productCategory;

	private Button btnApply;

	private String APPOID;
	private ResultSelectProductReturnListContentBean returnListContentBean;
	private ResultDeclarationSearchCommissionratioListContentBean commissionratioListContentBeans;
	private String Insurance;
	private String Age;
	private String Limit;
	private String insuredAge;

	private boolean[] defaultSelectedStatus;
	private boolean[] defaultSelectedStatusTemporary;
	private String[] multiChoiceItems;
	private String[] multiChoiceItemsDeputyInsuranceID;
	private String[] singleChoiceItemsAge;
	private String[] singleChoiceItemsInsuredAge = { "＜1周岁", "≥1周岁" };
	private String[] singleChoiceItemsLimit;

	private String deputyInsuranceName;
	private String deputyInsuranceNameId;
	private String ageCoverage;
	private String payLimitTime;

	private int selectWhichAge = -1;
	private int selectWhichLimit = -1;
	private int selectWhichInsuredAge = -1;

	private int selectWhichAgeTemp;
	private int selectWhichLimitTemp;
	private int selectWhichInsureAgeTemp;

	private RelativeLayout layout_city;
	private TextView txt_city;
	private ListView listview, listview1;
	private FinancialCardAdapter financialCardAdapter, myAdapter2;
	private PopupWindow citypopupWindow;
	private String aa, bb;
	public static final String ENCODING = "UTF-8";
	private CityBean ar;
	private ArrayList<CityBean> list;
	private ArrayList<CityBean> firstname;
	private ArrayList<CityBean> secondname;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_apply_toubaodan);
		initView();
		setData();
		initData();
		initTopTitle();
	}

	private void setData() {
		if (PreferenceUtil.getUserType().equals("corp")) {
			edt_userName.setVisibility(View.VISIBLE);
			edt_userPhone.setVisibility(View.VISIBLE);
		}
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setLogo(R.drawable.img_logo, false)
				.setIndicator(R.drawable.back)
				.setCenterText(
						getResources()
								.getString(R.string.title_apply_toubaodan))
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

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		firstname = new ArrayList<CityBean>();
		list = new ArrayList<CityBean>();
		secondname = new ArrayList<CityBean>();
		scrollView = (ScrollView) findViewById(R.id.apply_toubaodan_product_scrollView);
		layout_selectProduct = (RelativeLayout) findViewById(R.id.apply_toubaodan_product_layout);
		selectProduct = (TextView) findViewById(R.id.apply_toubaodan_product_name);
		edt_insurance = (TextView) findViewById(R.id.apply_toubaodan_edit_baodan_select_insurance);
		edt_age = (TextView) findViewById(R.id.apply_toubaodan_edit_select_age);
		edt_insuredAge = (TextView) findViewById(R.id.apply_toubaodan_edit_select_insuredAge);
		edt_insuredAge.setText("被保人年龄");
		edt_insuredAge.setTextColor(getResources().getColor(R.color.gray_d_d));
		edt_date = (TextView) findViewById(R.id.apply_toubaodan_edit_select_date);
		edt_customerName = (EditText) findViewById(R.id.apply_toubaodan_edit_name);
		// edt_amount = (EditText)
		// findViewById(R.id.apply_toubaodan_edit_investmoney);
		edt_userName = (EditText) findViewById(R.id.apply_toubaodan_edit_financial_planners);
		edt_userPhone = (EditText) findViewById(R.id.apply_toubaodan_edit_financial_planners_phone);
		edt_accepter = (EditText) findViewById(R.id.apply_toubaodan_edit_receiver_name);
		edt_acceptPhone = (EditText) findViewById(R.id.apply_toubaodan_edit_receiver_phone);
		edt_acceptAddress = (EditText) findViewById(R.id.apply_toubaodan_edit_receiver_address);
		btnApply = (Button) findViewById(R.id.btn_apply_toubaodan);
		layout_city = (RelativeLayout) findViewById(R.id.rl_toubaodan_city);
		txt_city = (TextView) findViewById(R.id.tv_toubaodan_city);
		txt_city.setText("所在地");
		txt_city.setTextColor(getResources().getColor(R.color.gray_d_d));
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
		
	}

	private void initData() {
		btnApply.setOnClickListener(this);
		layout_selectProduct.setOnClickListener(this);
		edt_insurance.setOnClickListener(this);
		edt_age.setOnClickListener(this);
		edt_insuredAge.setOnClickListener(this);
		edt_date.setOnClickListener(this);
		layout_city.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.apply_toubaodan_product_layout:
			Intent intent = new Intent(this, SelectProductActivity.class);
			intent.putExtra("remark", "ApplyToubaodanActivity");
			startActivityForResult(intent, 1000);
			break;
		case R.id.btn_apply_toubaodan:
			String customerName = edt_customerName.getText().toString();
			// String amount = edt_amount.getText().toString();
			String userName = edt_userName.getText().toString();
			String userPhone = edt_userPhone.getText().toString();
			String accepter = edt_accepter.getText().toString();
			String acceptPhone = edt_acceptPhone.getText().toString();
			String acceptAddress = edt_acceptAddress.getText().toString();
			String deputyInsuranceName = edt_insurance.getText().toString();
			String ageCoverage = edt_age.getText().toString();
			String payLimitTime = edt_date.getText().toString();
			String insureAge = edt_insuredAge.getText().toString();
			String city = txt_city.getText().toString();
			if (returnListContentBean != null
					&& returnListContentBean.getId() != null) {
				productId = returnListContentBean.getId();
				productName = returnListContentBean.getName();
				productCategory = returnListContentBean.getCategory();

				if (returnListContentBean.isFlag()) {// 是否保险产品
					if (returnListContentBean.getInsuranceLimit().size() != 0) {

						if (payLimitTime.equals("选择缴费年期")) {
							Toast.makeText(ApplyToubaodanActivity.this,
									"请选择缴费年期", Toast.LENGTH_SHORT).show();

						} else {
							if (insureAge.equals("被保人年龄")) {
								Toast.makeText(ApplyToubaodanActivity.this,
										"请选择被保人年龄", Toast.LENGTH_SHORT).show();

							} else {
								if (returnListContentBean
										.getInsuranceAgecoverAge().size() != 0) {
									if (ageCoverage.equals("选择年龄")) {
										Toast.makeText(
												ApplyToubaodanActivity.this,
												"请选择年龄", Toast.LENGTH_SHORT)
												.show();
									} else {
										IsRight(customerName, userName,
												userPhone, accepter,
												acceptPhone, acceptAddress,
												deputyInsuranceName,
												ageCoverage, payLimitTime, city);
									}
								} else {
									IsRight(customerName, userName, userPhone,
											accepter, acceptPhone,
											acceptAddress, deputyInsuranceName,
											ageCoverage, payLimitTime, city);
								}

							}
						}

					}
				}
			} else {
				Toast.makeText(ApplyToubaodanActivity.this, "请选择产品",
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.apply_toubaodan_edit_baodan_select_insurance:
			showDialog(multiChoiceItems,multiChoiceItemsDeputyInsuranceID);
			break;
		case R.id.apply_toubaodan_edit_select_age:
			showDialogSingleAge(singleChoiceItemsAge, "选择年龄");
			break;
		case R.id.apply_toubaodan_edit_select_date:
			showDialogSingleLimit(singleChoiceItemsLimit, "选择缴费年期");
			break;
		case R.id.apply_toubaodan_edit_select_insuredAge:
			showDialogSingleInsuredAge(singleChoiceItemsInsuredAge, "被保人年龄");
			break;
		case R.id.rl_toubaodan_city:
			try {
				initPopWindowForCitys();
				citypopupWindow.setAnimationStyle(R.style.PopupAnimation);
				citypopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);
				citypopupWindow.update();

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	public void showDialog(final String[] multiChoiceItems,final String[] multiChoiceItemsDeputyInsuranceID) {
		// 复选框默认值：false=未选;true=选中 ,各自对应items[i]
		final StringBuilder sb = new StringBuilder();
		final StringBuilder sbID = new StringBuilder();
		for (int i = 0; i < defaultSelectedStatus.length; i++) {
			defaultSelectedStatusTemporary[i] = defaultSelectedStatus[i];
		}
		// 创建对话框
		AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle("选择附加险")

				// 设置对话框标题
				.setMultiChoiceItems(multiChoiceItems, defaultSelectedStatusTemporary,
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
								sbID.append(multiChoiceItemsDeputyInsuranceID[i]
										+ ",");
							} else {
								if (sb.length() == 0) {
									edt_insurance.setText("选择附加险");
									sbID.append("");
								}
							}
						}

						if (sb.length() == 0) {
							edt_insurance.setText("选择附加险");
							sbID.append("");
						} else {
							sb.deleteCharAt(sb.length() - 1);
							sbID.deleteCharAt(sbID.length() - 1);
							edt_insurance.setText(sb.toString());
						}
						deputyInsuranceName = edt_insurance.getText()
								.toString();
						deputyInsuranceNameId = sbID.toString();
						if (returnListContentBean != null
								&& returnListContentBean.getId() != null) {
							productId = returnListContentBean.getId();
						}
						requestSearchCommissionratioData(productId,
								deputyInsuranceNameId, ageCoverage,
								payLimitTime);
						
					}
				}).setNegativeButton("取消", null)// 设置对话框[否定]按钮
				.show();
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
								// Age = singleChoiceItems[which];
							}
						})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectWhichAgeTemp = selectWhichAge;
						if (selectWhichAge == -1) {
							edt_age.setText("选择年龄");
						} else {
							Age = singleChoiceItems[selectWhichAgeTemp];
							edt_age.setText(Age);
						}
						ageCoverage = edt_age.getText().toString();
						if (returnListContentBean != null
								&& returnListContentBean.getId() != null) {
							productId = returnListContentBean.getId();
						}
						requestSearchCommissionratioData(productId,
								deputyInsuranceNameId, ageCoverage,
								payLimitTime);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectWhichAge = selectWhichAgeTemp;
					}
				}).show();
	}

	public void showDialogSingleInsuredAge(final String[] singleChoiceItems,
			String title) {
		selectWhichInsureAgeTemp = selectWhichInsuredAge;
		// 创建对话框
		new AlertDialog.Builder(this)
				.setTitle(title)
				.setSingleChoiceItems(singleChoiceItems, selectWhichInsuredAge,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								selectWhichInsuredAge = which;
								// insuredAge = singleChoiceItems[which];

							}
						})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectWhichInsureAgeTemp = selectWhichInsuredAge;
						if (selectWhichInsuredAge == -1) {
							edt_insuredAge.setText("被保人年龄");
						} else {
							insuredAge = singleChoiceItems[selectWhichInsureAgeTemp];
							edt_insuredAge.setText(insuredAge);
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectWhichInsuredAge = selectWhichInsureAgeTemp;
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
								// Limit = singleChoiceItems[which];
							}
						})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectWhichLimitTemp = selectWhichLimit;
						if (selectWhichLimit == -1) {
							edt_date.setText("选择缴费年期");
						} else {
							Limit = singleChoiceItems[selectWhichLimitTemp];
							edt_date.setText(Limit);
						}
						payLimitTime = edt_date.getText().toString();
						if (returnListContentBean != null
								&& returnListContentBean.getId() != null) {
							productId = returnListContentBean.getId();
						}
						requestSearchCommissionratioData(productId,
								deputyInsuranceNameId, ageCoverage,
								payLimitTime);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectWhichLimit = selectWhichLimitTemp;
					}
				}).show();
	}
	/**
	 * 获取保险产品的返佣比例
	 */
	private void requestSearchCommissionratioData(final String productId,
			String deputyInsuranceName, String ageCoverage,
			String payLimitTimeOne) {
		if (payLimitTime.equals("选择缴费年期")) {
			Toast.makeText(ApplyToubaodanActivity.this, "请选择缴费年期",
					Toast.LENGTH_SHORT).show();
		} else {
			if (returnListContentBean.getInsuranceAgecoverAge().size() != 0
					&& ageCoverage.equals("选择年龄")) {
				Toast.makeText(ApplyToubaodanActivity.this, "请选择年龄",
						Toast.LENGTH_SHORT).show();

			} else {
				if (ageCoverage.equals("选择年龄")) {
					ageCoverage = "(null)";
				}
				if (TextUtils.isEmpty(deputyInsuranceName)) {
					deputyInsuranceName = "(null)";
				}
				/*
				 * if (!(payLimitTimeOne.equals("趸缴") ||
				 * payLimitTimeOne.equals("趸交"))) { payLimitTimeOne =
				 * payLimitTimeOne.substring(0,
				 * payLimitTimeOne.lastIndexOf("年")); }
				 */HtmlRequest.getSearchCommissionratio(
						ApplyToubaodanActivity.this, productId,
						deputyInsuranceName, ageCoverage, payLimitTimeOne,
						new OnRequestListener() {

							@Override
							public void onRequestFinished(BaseParams params) {
								if (params.result != null) {
									commissionratioListContentBeans = (ResultDeclarationSearchCommissionratioListContentBean) params.result;
									if (commissionratioListContentBeans
											.getResultCommissionData().size() == 0) {
										edt_date.setText("选择缴费年期");
										Toast.makeText(
												ApplyToubaodanActivity.this,
												"此年期选择错误，请重新选择！",
												Toast.LENGTH_LONG).show();
									} 
								} else {
									Toast.makeText(
											ApplyToubaodanActivity.this,
											"加载失败，请确认网络通畅", Toast.LENGTH_LONG)
											.show();
								}
							}
						});
			}
		}
	}
	private void IsRight(String customerName, String userName,
			String userPhone, String accepter, String acceptPhone,
			String acceptAddress, String deputyInsuranceName,
			String ageCoverage, String payLimitTime, String city) {
		if (TextUtils.isEmpty(customerName)) {
			Toast.makeText(ApplyToubaodanActivity.this, "客户名不能为空",
					Toast.LENGTH_SHORT).show();
			edt_customerName.requestFocusFromTouch();
		} else {
			if (TextUtils.isEmpty(accepter)) {
				Toast.makeText(ApplyToubaodanActivity.this, "收件人不能为空",
						Toast.LENGTH_SHORT).show();
				edt_accepter.requestFocusFromTouch();
			} else {
				if (TextUtils.isEmpty(acceptPhone)) {
					Toast.makeText(ApplyToubaodanActivity.this, "收件人手机号码不能为空",
							Toast.LENGTH_SHORT).show();
					edt_acceptPhone.requestFocusFromTouch();
				} else {
					if (StringUtil.isMobileNO(edt_acceptPhone.getText()
							.toString())) {
						if (city.equals("所在地")) {
							Toast.makeText(ApplyToubaodanActivity.this,
									"请选择所在地", Toast.LENGTH_SHORT).show();
						} else {
							if (TextUtils.isEmpty(acceptAddress)) {
								Toast.makeText(ApplyToubaodanActivity.this,
										"详细收货地址不能为空", Toast.LENGTH_SHORT)
										.show();
								edt_acceptAddress.requestFocusFromTouch();
							} else {
								if (PreferenceUtil.getUserType().equals("corp")) {
									if (TextUtils.isEmpty(userName)) {
										Toast.makeText(
												ApplyToubaodanActivity.this,
												"理财师姓名不能为空", Toast.LENGTH_SHORT)
												.show();
										edt_userName.requestFocusFromTouch();
									} else {
										if (TextUtils.isEmpty(userPhone)) {
											Toast.makeText(
													ApplyToubaodanActivity.this,
													"理财师电话不能为空",
													Toast.LENGTH_SHORT).show();
											edt_userPhone
													.requestFocusFromTouch();
										} else {
											if (StringUtil
													.isMobileNO(edt_userPhone
															.getText()
															.toString())) {
												requestApplyToubaodanData(
														APPOID, productId,
														productName,
														customerName, userName,
														userPhone, accepter,
														acceptPhone,
														city + acceptAddress,
														deputyInsuranceName,
														payLimitTime,
														ageCoverage, insuredAge);

											} else {
												Toast.makeText(
														ApplyToubaodanActivity.this,
														"请输入正确的手机号码",
														Toast.LENGTH_SHORT)
														.show();
												edt_userPhone
														.requestFocusFromTouch();
											}
										}
									}
								} else {

									requestApplyToubaodanData(APPOID,
											productId, productName,
											customerName, userName, userPhone,
											accepter, acceptPhone,
											city + acceptAddress, deputyInsuranceName,
											payLimitTime, ageCoverage,
											insuredAge);

								}
							}
						}
					} else {
						Toast.makeText(ApplyToubaodanActivity.this,
								"请输入正确的手机号码", Toast.LENGTH_SHORT).show();
						edt_acceptPhone.requestFocusFromTouch();
					}
				}
			}

		}
	}

	private void requestApplyToubaodanData(final String appId,
			String productId, String productName, String customerName,
			String userName, String userPhone, String accepter,
			String acceptPhone, String acceptAddress,
			String deputyInsuranceName, String payLimitTimeone,
			String ageCoverage, String age) {
		if (returnListContentBean.getInsuranceChild().size() == 0
				|| deputyInsuranceName.equals("选择附加险")) {
			deputyInsuranceName = "(null)";
		}
		if (returnListContentBean.getInsuranceAgecoverAge().size() == 0
				|| ageCoverage.equals("选择年龄")) {
			ageCoverage = "(null)";
		}
		if (returnListContentBean.getInsuranceLimit().size() == 0
				|| ageCoverage.equals("选择缴费年期")) {
			payLimitTimeone = "(null)";
		}
		/*
		 * else { payLimitTimeone = payLimitTimeone.substring(0,
		 * payLimitTimeone.lastIndexOf("年")); }
		 */
		HtmlRequest.getApplyToubaodan(ApplyToubaodanActivity.this, appId,
				productId, productName, customerName, userName, userPhone,
				accepter, acceptPhone, acceptAddress, deputyInsuranceName,
				payLimitTimeone, ageCoverage, age, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							ResultApplyToubaodanContentBean data = (ResultApplyToubaodanContentBean) params.result;
							if (data != null) {
								if (data.getFlag().equals("true")) {
									Toast.makeText(ApplyToubaodanActivity.this,
											"投保单申请成功", Toast.LENGTH_LONG)
											.show();
									Intent intent = new Intent(
											ApplyToubaodanActivity.this,
											ToubaodanActivity.class);
									ApplyToubaodanActivity.this.setResult(2,
											intent);
									ApplyToubaodanActivity.this.finish();
									requestSMS(data.getPolicyId(),
											data.getProductName());
								} else {
									Toast.makeText(ApplyToubaodanActivity.this,
											data.getMessage(),
											Toast.LENGTH_LONG).show();
								}
							}
						} else {
							Toast.makeText(ApplyToubaodanActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	private void requestSMS(String policyId, String productName) {
		try {
			HtmlRequest.sentSMSForApplyTouBaoDan(ApplyToubaodanActivity.this,
					ApplicationConsts.APPLY_TOUBAODAN, policyId, productName,
					new OnRequestListener() {

						@Override
						public void onRequestFinished(BaseParams params) {
							ResultSentSMSContentBean b = (ResultSentSMSContentBean) params.result;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 2000) {
			String ID = data.getExtras().getString("ID");
			String CAREGORY = data.getExtras().getString("CAREGORY");
			APPOID = data.getExtras().getString("APPOID");

			requestDataSelectProduct(ID, CAREGORY, APPOID);

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void requestDataSelectProduct(final String id, String category,
			String appoId) {
		HtmlRequest.getSelectProductReturn(ApplyToubaodanActivity.this, id,
				category, appoId, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							returnListContentBean = (ResultSelectProductReturnListContentBean) params.result;

							setRequestData(returnListContentBean);

						} else {
							Toast.makeText(ApplyToubaodanActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	protected void setRequestData(
			ResultSelectProductReturnListContentBean returnListContentBean) {
		edt_insurance.setVisibility(View.GONE);
		edt_age.setVisibility(View.GONE);
		edt_date.setVisibility(View.GONE);
		edt_insurance.setText("选择附加险");
		edt_age.setText("选择年龄");
		edt_date.setText("选择缴费年期");
		selectWhichLimit = -1;
		selectWhichAge = -1;
		payLimitTime = "选择缴费年期";
		ageCoverage = "选择年龄";
		deputyInsuranceName = "选择附加险";
		edt_accepter.setText("");
		edt_acceptPhone.setText("");
		edt_acceptAddress.setText("");
		edt_userName.setText("");
		edt_userPhone.setText("");
		edt_customerName.setText("");
		selectProduct.setText(returnListContentBean.getName());
		if (returnListContentBean.isFlag()) {// 是否保险产品
			if (returnListContentBean.getInsuranceChild().size() != 0) {
				edt_insurance.setVisibility(View.VISIBLE);

				MouldList<ResultSelectProductReturnInsuranceChildBean> childBeans = returnListContentBean
						.getInsuranceChild();
				multiChoiceItems = new String[childBeans.size()];
				multiChoiceItemsDeputyInsuranceID = new String[childBeans.size()];
				for (int i = 0; i < childBeans.size(); i++) {
					multiChoiceItems[i] = String.valueOf(childBeans.get(i)
							.getNAME());
					multiChoiceItemsDeputyInsuranceID[i] = String
							.valueOf(childBeans.get(i).getID());
				}
				defaultSelectedStatus = new boolean[multiChoiceItems.length];
				defaultSelectedStatusTemporary = new boolean[multiChoiceItems.length];

			}
			if (returnListContentBean.getInsuranceAgecoverAge().size() != 0) {
				edt_age.setVisibility(View.VISIBLE);

				MouldList<ResultSelectProductReturnInsuranceAgecoverAgeBean> ageBeans = returnListContentBean
						.getInsuranceAgecoverAge();
				singleChoiceItemsAge = new String[ageBeans.size()];
				for (int i = 0; i < ageBeans.size(); i++) {
					singleChoiceItemsAge[i] = String.valueOf(ageBeans.get(i)
							.getAGECOVERAGE());
				}
			}
			if (returnListContentBean.getInsuranceLimit().size() != 0) {
				edt_date.setVisibility(View.VISIBLE);

				MouldList<ResultSelectProductReturnInsuranceLimitBean> limitBeans = returnListContentBean
						.getInsuranceLimit();
				singleChoiceItemsLimit = new String[limitBeans.size()];
				for (int i = 0; i < limitBeans.size(); i++) {
					singleChoiceItemsLimit[i] = String.valueOf(limitBeans
							.get(i).getLIMITE());
				}
			}
		}

		if (APPOID.equals("无")) {// 没有预约
			if (PreferenceUtil.getUserType().equals("corp")) {// 是否机构
				edt_userName.setVisibility(View.VISIBLE);
				edt_userPhone.setVisibility(View.VISIBLE);
			}

		} else {// 已预约
			if (PreferenceUtil.getUserType().equals("corp")) {// 是否机构
				edt_userName.setVisibility(View.VISIBLE);
				edt_userPhone.setVisibility(View.VISIBLE);
				edt_userName.setText(returnListContentBean.getAppolist().get(0)
						.getCUSTOMERNAME());
				edt_userPhone.setText(returnListContentBean.getAppolist()
						.get(0).getCUSTOMERPHONE());
				/*
				 * edt_customerName.setText(returnListContentBean.getAppolist()
				 * .get(0).getCUSTOMERNAME());
				 */
			} else {
				/*
				 * edt_customerName.setText(returnListContentBean.getAppolist()
				 * .get(0).getCUSTOMERNAME());
				 */
				/*
				 * edt_amount.setText(returnListContentBean.getAppolist().get(0)
				 * .getAMOUNT());
				 */
			}
		}
	}

	private void initPopWindowForCitys() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.wheelpopupforcity, null);
		listview = (ListView) layout.findViewById(R.id.list);
		listview1 = (ListView) layout.findViewById(R.id.list1);
		layout.getBackground().setAlpha(130);
		layout.invalidate();

		DisplayMetrics dm = new DisplayMetrics();
		ApplyToubaodanActivity.this.getWindowManager().getDefaultDisplay()
				.getMetrics(dm);

		int iWidth = dm.widthPixels;
		int iHeight = dm.heightPixels;

		citypopupWindow = new PopupWindow(layout, iWidth * 5 / 6, iHeight / 2);

		// citypopupWindow = new PopupWindow(layout,
		// LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		// citypopupWindow.showAsDropDown(btn_financial);

		citypopupWindow.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0, 0);
		// Button btn_city_canle = (Button)
		// layout.findViewById(R.id.pickcitycancle);
		// Button pickcityconfirm = (Button)
		// layout.findViewById(R.id.pickcityconfirm);
		// btn_city_canle.getPaint().setFakeBoldText(true);
		// btn_city_canle.setOnClickListener(new OnClickListener() {
		// public void onClick(View v) {
		// citypopupWindow.dismiss();
		// citypopupWindow.setFocusable(false);
		// }
		// });
		// pickcityconfirm.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		// String cc = aa+bb;
		// citypopupWindow.dismiss();
		// citypopupWindow.setFocusable(false);
		// // Toast.makeText(MainActivity.this, cc, Toast.LENGTH_LONG).show();
		// }
		// });
		WindowManager.LayoutParams params = ApplyToubaodanActivity.this
				.getWindow().getAttributes();
		params.alpha = 0.7f;

		ApplyToubaodanActivity.this.getWindow().setAttributes(params);

		layout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (citypopupWindow.isShowing()) {
					citypopupWindow.dismiss();
					WindowManager.LayoutParams params = ApplyToubaodanActivity.this
							.getWindow().getAttributes();
					params.alpha = 1f;
					ApplyToubaodanActivity.this.getWindow().setAttributes(
							params);
				}
				return false;
			}
		});
		String str1 = getFromAssets("leibie.json");
		JSONObject js;
		try {
			js = new JSONObject(str1);
			String areaBeans = js.getString("areaBeans");
			JSONArray areaBean = new JSONArray(areaBeans);
			list.clear();
			for (int i = 0; i < areaBean.length(); i++) {
				ar = new CityBean();
				JSONObject json = (JSONObject) areaBean.get(i);
				ar.setAreaid(json.optString("areaid"));
				ar.setName(json.optString("name"));
				ar.setPinyin(json.optString("pinyin"));
				ar.setShortpinyin(json.optString("shortpinyin"));
				ar.setType(json.optString("type"));
				ar.setParentId(json.optString("parentId"));
				list.add(i, ar);
			}
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getType().equals("s")) {
					firstname.add(list.get(i));
				}
			}

			citypopupWindow.setFocusable(true);
			financialCardAdapter = new FinancialCardAdapter(this, firstname);
			myAdapter2 = new FinancialCardAdapter(this, secondname);
			listview.setAdapter(financialCardAdapter);
			listview1.setAdapter(myAdapter2);
			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					secondname.clear();
					for (int i = 0; i < list.size(); i++) {
						String id = firstname.get(arg2).getAreaid();
						aa = firstname.get(arg2).getName();
						if (list.get(i).getType().equals("c")) {
							if (list.get(i).getParentId().equals(id)) {
								secondname.add(list.get(i));

							}
						}
					}

					myAdapter2.notifyDataSetChanged();
				}
			});

			listview1
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							bb = secondname.get(arg2).getName();
							String cc = aa + bb;
							citypopupWindow.dismiss();
							citypopupWindow.setFocusable(false);

							// Toast.makeText(context, cc,
							// Toast.LENGTH_LONG).show();
							txt_city.setText(aa + bb);
							WindowManager.LayoutParams params = ApplyToubaodanActivity.this
									.getWindow().getAttributes();
							params.alpha = 1f;
							ApplyToubaodanActivity.this.getWindow()
									.setAttributes(params);
						}
					});

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// 从assets 文件夹中获取文件并读取数据
	public String getFromAssets(String fileName) {
		String result = "";
		try {
			InputStream in = getResources().getAssets().open(fileName);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, ENCODING);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
