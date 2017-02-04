package com.cf360.act;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.cf360.ApplicationConsts;
import com.cf360.R;
import com.cf360.bean.ResultApplyContractContentBean;
import com.cf360.bean.ResultApplyDeclarationContentBean;
import com.cf360.bean.ResultCancelOrderBean;
import com.cf360.bean.ResultDeclarationDetailsListBean;
import com.cf360.bean.ResultDeclarationDetailsListContentBean;
import com.cf360.bean.ResultDeclarationSearchCommissionratioDataBean;
import com.cf360.bean.ResultDeclarationSearchCommissionratioListContentBean;
import com.cf360.bean.ResultSelectProductReturnInsuranceAgecoverAgeBean;
import com.cf360.bean.ResultSelectProductReturnInsuranceChildBean;
import com.cf360.bean.ResultSelectProductReturnInsuranceCommissionRatioBean;
import com.cf360.bean.ResultSelectProductReturnInsuranceLimitBean;
import com.cf360.bean.ResultSelectProductReturnListContentBean;
import com.cf360.bean.ResultSentSMSContentBean;
import com.cf360.bean.ResultUploadListBean;
import com.cf360.bean.ResultUploadListContentBean;
import com.cf360.http.AsyncHttpClient;
import com.cf360.http.AsyncHttpResponseHandler;
import com.cf360.http.RequestParams;
import com.cf360.mould.BaseParams;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.IdCardCheckUtils;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.uitls.StringUtil;
import com.cf360.view.AmountDialog;
import com.cf360.view.TitleBar;
import com.cf360.view.AmountDialog.OnSignContractChanged;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;
import com.cf360.wheel.widget.SelectWheeelDate;
import com.cf360.wheel.widget.SelectWheeelDate.OnWheelListener;
import com.cf360.wheel.widget.SelectWheelPlate;
import com.cf360.wheel.widget.SelectWheelPlate.OnWheelListenerInfo;
import com.google.gson.Gson;

/**
 * 我要报单
 * 
 */
public class ApplyDeclarationForOrderActivity extends BaseActivity implements
		OnClickListener {
	private ScrollView scrollView;
	private TextView selectProduct;
	private TextView edt_contractNumber;
	private TextView edt_insurance;// 选择附加险
	private TextView edt_age;// 选择年龄
	private TextView edt_date;// 选择缴费年期
	private EditText edt_customerName;// 客户姓名
	private EditText edt_userName;// 理财师
	private EditText edt_userPhone;// 理财师电话
	private TextView edt_investdate;// 打款日期
	private EditText edt_remark;// 备注

	private Button btnApply;

	private String APPOID;
	private ResultSelectProductReturnListContentBean returnListContentBean;
	private ResultDeclarationSearchCommissionratioListContentBean commissionratioListContentBeans;
	private MouldList<ResultDeclarationSearchCommissionratioDataBean> searchCommissionratioDataBeans;
	private String Insurance;
	private String Age;
	private String Limit;
	private String investDate;

	private String[] multiChoiceItems;
	private String[] multiChoiceItemsDeputyInsuranceID;
	private String[] singleChoiceItemsAge;
	private String[] singleChoiceItemsLimit;

	private String deputyInsuranceName_d;
	private String deputyInsuranceNameId;
	private String deputyInsuranceNameContrast = "";
	private String ageCoverage;
	private String payLimitTime;
	private String payLimitTimeContrast = "";
	private String insuranceAmount;
	private String deputyInsuranceAmount;
	private String commissionRatio;
	private String insuranceRebateProportion;

	private int selectWhichAge = -1;
	private int selectWhichLimit = -1;

	private int selectWhichAgeTemp;
	private int selectWhichLimitTemp;

	private String productId;
	private String contractId;
	private String productCategory;
	private String productName;
	private String customerPhone;

	private ImageView img_IDpros;// 身份证正面
	private ImageView img_IDcons;// 身份证反面
	private ImageView img_bank;// 银行卡
	private ImageView img_investmoney;// 打款凭条
	private ImageView img_sign;// 签字页

	private boolean isIDpros;
	private boolean isIDcons;
	private boolean isBank;
	private boolean isInvestmoney;
	private boolean isSign;

	private RelativeLayout viewAnimatorLayout;
	private ViewAnimator viewAnimator;
	private EditText edt_amount;// 打款金额
	private TextView txt_amount;// 打款金额
	private TextView txt_currency;// 币种
	private EditText edt_idCard;// 身份证号

	private String sfzzmFileName;
	private String sfzfmFileName;
	private String cardFileName;
	private String dkptFileName;
	private String qzyFileName;

	private boolean[] defaultSelectedStatus;
	private boolean[] defaultSelectedStatusTemporary;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	protected double amountSum = 0;

	private int photoType = 1;
	/**
	 * 表示选择的是相机--1
	 */
	private static int CAMERA_REQUEST_CODE = 1;
	/**
	 * 表示选择的是相册--2
	 */
	private static int GALLERY_REQUEST_CODE = 2;
	/**
	 * 表示选择的是裁剪--3
	 */
	private static int CROP_REQUEST_CODE = 3;
	private Bitmap newZoomImage;
	private MyHandler mHandler;
	private Thread mthread;
	/**
	 * 图片保存SD卡位置
	 */
	private final static String IMG_PATH = Environment
			.getExternalStorageDirectory() + "/cf360/imgs/";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_apply_declaration_for_order);
		mHandler = new MyHandler();
		mthread = new Thread(myRunnable);
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
		title.addAction(
				new Action(2, 0, R.color.orange),
				ApplyDeclarationForOrderActivity.this.getResources().getString(
						R.string.title_apply_declaration_info));
		title.setTitle(getResources().getString(R.string.title_no))
				.setLogo(R.drawable.img_logo, false)
				.setIndicator(R.drawable.back)
				.setCenterText(
						getResources().getString(
								R.string.title_apply_declaration))
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
						Intent intent = new Intent(
								ApplyDeclarationForOrderActivity.this,
								DeclarationInfoActivity.class);
						startActivity(intent);
					}
				});
	}

	private void initView() {
		stack = ActivityStack.getActivityManage();
		stack.addActivity(this);
		scrollView = (ScrollView) findViewById(R.id.apply_declaration_product_scrollView);
		selectProduct = (TextView) findViewById(R.id.apply_declaration_product_name);
		edt_contractNumber = (EditText) findViewById(R.id.apply_declaration_edit_contract_number);
		edt_insurance = (TextView) findViewById(R.id.apply_declaration_edit_baodan_select_insurance);
		edt_age = (TextView) findViewById(R.id.apply_declaration_edit_select_age);
		edt_date = (TextView) findViewById(R.id.apply_declaration_edit_select_date);
		edt_customerName = (EditText) findViewById(R.id.apply_declaration_edit_name);

		viewAnimator = (ViewAnimator) findViewById(R.id.apply_declaration_viewanimator);
		viewAnimatorLayout = (RelativeLayout) findViewById(R.id.apply_declaration_viewanimator_layout);
		edt_amount = (EditText) findViewById(R.id.apply_declaration_edit_investmoney);
		txt_amount = (TextView) findViewById(R.id.apply_declaration_txt_investmoney);
		txt_currency = (TextView) findViewById(R.id.apply_declaration_txt_currency);
		edt_idCard = (EditText) findViewById(R.id.apply_declaration_edit_IDcard);

		edt_userName = (EditText) findViewById(R.id.apply_declaration_edit_financial_planners);
		edt_userPhone = (EditText) findViewById(R.id.apply_declaration_edit_financial_planners_phone);
		edt_investdate = (TextView) findViewById(R.id.apply_declaration_edit_investdate);
		edt_remark = (EditText) findViewById(R.id.note_edit);
		btnApply = (Button) findViewById(R.id.btn_save);

		deputyInsuranceName_d = edt_insurance.getText().toString();
		ageCoverage = edt_age.getText().toString();
		payLimitTime = edt_date.getText().toString();

		// 上传图片
		img_IDpros = (ImageView) findViewById(R.id.apply_declaration_img_IDpros);
		img_IDcons = (ImageView) findViewById(R.id.apply_declaration_img_IDcons);
		img_bank = (ImageView) findViewById(R.id.apply_declaration_img_bank);
		img_investmoney = (ImageView) findViewById(R.id.apply_declaration_img_investmoney);
		img_sign = (ImageView) findViewById(R.id.apply_declaration_img_sign);

		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
		
	}

	private void initData() {
		btnApply.setOnClickListener(this);
		edt_insurance.setOnClickListener(this);
		edt_age.setOnClickListener(this);
		edt_date.setOnClickListener(this);
		edt_investdate.setOnClickListener(this);
		txt_amount.setOnClickListener(this);

		img_IDpros.setOnClickListener(this);
		img_IDcons.setOnClickListener(this);
		img_bank.setOnClickListener(this);
		img_investmoney.setOnClickListener(this);
		img_sign.setOnClickListener(this);

		String ID = getIntent().getStringExtra("PRODUCTID");
		String CAREGORY = getIntent().getStringExtra("type");
		APPOID = getIntent().getStringExtra("APPOID");

		requestDataSelectProduct(ID, CAREGORY, APPOID);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save:
			String contractId = edt_contractNumber.getText().toString();
			deputyInsuranceName_d = edt_insurance.getText().toString();
			ageCoverage = edt_age.getText().toString();
			payLimitTime = edt_date.getText().toString();
			String customerName = edt_customerName.getText().toString();
			String insuranceAmountTxt = txt_amount.getText().toString();
			String investdate = edt_investdate.getText().toString();
			String remark = edt_remark.getText().toString();
			String userName = edt_userName.getText().toString();
			String userPhone = edt_userPhone.getText().toString();
			String idCard = edt_idCard.getText().toString();

			if (returnListContentBean != null
					&& returnListContentBean.getId() != null) {
				productId = returnListContentBean.getId();
				productName = returnListContentBean.getName();
				productCategory = returnListContentBean.getCategory();
				if (productCategory.equals("bx")) {
					productCategory = "BX";
				}
				if (returnListContentBean.isFlag()) {// 是否保险产品

					if (returnListContentBean.getInsuranceAgecoverAge().size() != 0) {
						if (ageCoverage.equals("选择年龄")) {
							Toast.makeText(
									ApplyDeclarationForOrderActivity.this,
									"请选择年龄", Toast.LENGTH_SHORT).show();

						} else {
							if (returnListContentBean.getInsuranceLimit()
									.size() != 0) {
								if (payLimitTime.equals("选择缴费年期")) {
									Toast.makeText(
											ApplyDeclarationForOrderActivity.this,
											"请选择缴费年期", Toast.LENGTH_SHORT)
											.show();

								} else {
									if (insuranceAmountTxt.equals("打款金额")) {
										Toast.makeText(
												ApplyDeclarationForOrderActivity.this,
												"请输入打款金额", Toast.LENGTH_SHORT)
												.show();

									} else {
										IsRight(contractId,
												deputyInsuranceName_d,
												ageCoverage, payLimitTime,
												customerName, investdate,
												remark, userName, userPhone,
												idCard);

									}
								}
							}

						}

					} else {
						if (returnListContentBean.getInsuranceLimit().size() != 0) {
							if (payLimitTime.equals("选择缴费年期")) {
								Toast.makeText(
										ApplyDeclarationForOrderActivity.this,
										"请选择缴费年期", Toast.LENGTH_SHORT).show();

							} else {
								if (insuranceAmountTxt.equals("打款金额")) {
									Toast.makeText(
											ApplyDeclarationForOrderActivity.this,
											"请输入打款金额", Toast.LENGTH_SHORT)
											.show();

								} else {
									IsRight(contractId, deputyInsuranceName_d,
											ageCoverage, payLimitTime,
											customerName, investdate, remark,
											userName, userPhone, idCard);
								}
							}
						}
					}

				}
			}
			break;

		case R.id.apply_declaration_edit_baodan_select_insurance:
			showDialog(multiChoiceItems, multiChoiceItemsDeputyInsuranceID);
			break;
		case R.id.apply_declaration_edit_select_age:
			showDialogSingleAge(singleChoiceItemsAge, "选择年龄");
			break;
		case R.id.apply_declaration_edit_select_date:
			showDialogSingleLimit(singleChoiceItemsLimit, "选择缴费年期");
			break;
		case R.id.apply_declaration_edit_investdate:
			selectBuyDate();
			break;
		case R.id.apply_declaration_txt_investmoney:// 打款金额
			AmountDialog dialog = new AmountDialog(this,
					new OnSignContractChanged() {

						@Override
						public void onConfim(List<Map<String, Object>> mData) {
							data = mData;
							final StringBuilder deputyInsuranceAmountSB = new StringBuilder();
							// 投资金额
							amountSum = 0;
							for (int i = 0; i < data.size(); i++) {
								double amountValue = Double
										.parseDouble((String) mData.get(i).get(
												"value"));
								deputyInsuranceAmountSB.append(amountValue
										+ ",");
								amountSum += amountValue;
							}
							insuranceAmount = deputyInsuranceAmountSB
									.toString().substring(
											0,
											deputyInsuranceAmountSB.toString()
													.indexOf(","));
							deputyInsuranceAmountSB
									.deleteCharAt(deputyInsuranceAmountSB
											.length() - 1);
							if (data.size() != 1) {
								deputyInsuranceAmount = deputyInsuranceAmountSB
										.toString().substring(
												deputyInsuranceAmountSB
														.toString()
														.indexOf(",") + 1,
												deputyInsuranceAmountSB
														.toString().length());
							} else {
								deputyInsuranceAmount = null;
							}
							DecimalFormat df = new DecimalFormat("0.00 ");
							txt_amount.setText(df.format(amountSum) + "");
						}

						@Override
						public void onCancel() {

						}
					}, searchCommissionratioDataBeans, data);
			dialog.show();
			break;

		// 上传凭证
		case R.id.apply_declaration_img_IDpros:
			photoType = 1;
			setPhoto();
			break;
		case R.id.apply_declaration_img_IDcons:
			photoType = 2;
			setPhoto();
			break;
		case R.id.apply_declaration_img_bank:
			photoType = 3;
			setPhoto();
			break;
		case R.id.apply_declaration_img_investmoney:
			photoType = 4;
			setPhoto();
			break;
		case R.id.apply_declaration_img_sign:
			photoType = 5;
			setPhoto();
			break;

		default:
			break;
		}

	}

	public void showDialog(final String[] multiChoiceItems,
			final String[] multiChoiceItemsDeputyInsuranceID) {
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
//								Age = singleChoiceItems[which];
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
						ageCoverage=edt_age.getText().toString();
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
//								Limit = singleChoiceItems[which];
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
			Toast.makeText(ApplyDeclarationForOrderActivity.this, "请选择缴费年期",
					Toast.LENGTH_SHORT).show();
		} else {
			if (returnListContentBean.getInsuranceAgecoverAge().size() != 0
					&& ageCoverage.equals("选择年龄")) {
				Toast.makeText(ApplyDeclarationForOrderActivity.this, "请选择年龄",
						Toast.LENGTH_SHORT).show();

			} else {
				if (ageCoverage.equals("选择年龄")) {
					ageCoverage = "(null)";
				}
				if (TextUtils.isEmpty(deputyInsuranceName)) {
					deputyInsuranceName = "(null)";
				}
				/*
				 * if (!(payLimitTimeOne.equals("趸缴")
				 * ||payLimitTimeOne.equals("趸交"))) { payLimitTimeOne =
				 * payLimitTimeOne.substring(0,
				 * payLimitTimeOne.lastIndexOf("年")); }
				 */
				HtmlRequest.getSearchCommissionratio(
						ApplyDeclarationForOrderActivity.this, productId,
						deputyInsuranceName, ageCoverage, payLimitTimeOne,
						new OnRequestListener() {

							@Override
							public void onRequestFinished(BaseParams params) {
								if (params.result != null) {
									commissionratioListContentBeans = (ResultDeclarationSearchCommissionratioListContentBean) params.result;
									if (commissionratioListContentBeans
											.getResultCommissionData().size() != 0) {
										searchCommissionratioDataBeans = new MouldList<ResultDeclarationSearchCommissionratioDataBean>();
										for (int i = 0; i < commissionratioListContentBeans
												.getResultCommissionData()
												.size(); i++) {
											MouldList<ResultDeclarationSearchCommissionratioDataBean> listBean = commissionratioListContentBeans
													.getResultCommissionData()
													.get(i);
											ResultDeclarationSearchCommissionratioDataBean bean = new ResultDeclarationSearchCommissionratioDataBean();
											for (int j = 0; j < listBean.size(); j++) {
												bean = listBean.get(0);
												searchCommissionratioDataBeans
														.add(bean);
											}
										}
										commissionRatio = searchCommissionratioDataBeans
												.get(0).getCOMMISSIONRATIO();
										StringBuilder commissionratioSB = new StringBuilder();
										// 返佣比例
										for (int k = 0; k < searchCommissionratioDataBeans
												.size(); k++) {
											String commissionratio = searchCommissionratioDataBeans
													.get(k)
													.getCOMMISSIONRATIO();
											commissionratioSB
													.append(commissionratio
															+ ",");
										}
										commissionratioSB
												.deleteCharAt(commissionratioSB
														.length() - 1);
										if (searchCommissionratioDataBeans
												.size() != 1) {
											insuranceRebateProportion = commissionratioSB
													.toString()
													.substring(
															commissionratioSB
																	.toString()
																	.indexOf(
																			",") + 1,
															commissionratioSB
																	.toString()
																	.length());
										} else {
											insuranceRebateProportion = null;
										}

										viewAnimatorLayout
												.setVisibility(View.VISIBLE);
										viewAnimator
												.setVisibility(View.VISIBLE);
										viewAnimator.setDisplayedChild(1);
										data.clear();
										if (TextUtils
												.isEmpty(deputyInsuranceNameId)) {
											for (int i = 0; i < searchCommissionratioDataBeans
													.size(); i++) {
												Map<String, Object> map = new HashMap<String, Object>();
												map.put("value", "");
												data.add(map);
											}
											txt_amount.setText("打款金额");
										} else {
											if (!deputyInsuranceNameId
													.equals(deputyInsuranceNameContrast)) {
												deputyInsuranceNameContrast = deputyInsuranceName_d;
												for (int i = 0; i < searchCommissionratioDataBeans
														.size(); i++) {
													Map<String, Object> map = new HashMap<String, Object>();
													map.put("value", "");
													data.add(map);

												}
												txt_amount.setText("打款金额");
											} else {
												if (!payLimitTime
														.equals(payLimitTimeContrast)) {
													payLimitTimeContrast = payLimitTime;
													for (int i = 0; i < searchCommissionratioDataBeans
															.size(); i++) {
														Map<String, Object> map = new HashMap<String, Object>();
														map.put("value", "");
														data.add(map);

													}
													txt_amount.setText("打款金额");
												}
											}
										}
									} else {
										edt_date.setText("选择缴费年期");
										Toast.makeText(
												ApplyDeclarationForOrderActivity.this,
												"此年期选择错误，请重新选择！",
												Toast.LENGTH_LONG).show();
										viewAnimatorLayout
												.setVisibility(View.GONE);
										viewAnimator.setVisibility(View.GONE);
									}
								} else {
									Toast.makeText(
											ApplyDeclarationForOrderActivity.this,
											"加载失败，请确认网络通畅", Toast.LENGTH_LONG)
											.show();
								}
							}
						});
			}
		}
	}

	private void IsRight(String contractId, String deputyInsuranceName,
			String ageCoverage, String payLimitTime, String customerName,
			String investdate, String remark, String userName,
			String userPhone, String idCard) {
		if (TextUtils.isEmpty(contractId)) {
			Toast.makeText(ApplyDeclarationForOrderActivity.this, "合同编号不能为空",
					Toast.LENGTH_SHORT).show();
			edt_contractNumber.requestFocusFromTouch();

		} else {
			if (TextUtils.isEmpty(customerName)) {
				Toast.makeText(ApplyDeclarationForOrderActivity.this,
						"客户名不能为空", Toast.LENGTH_SHORT).show();
				edt_customerName.requestFocusFromTouch();
			} else {
				if (TextUtils.isEmpty(idCard)) {
					Toast.makeText(ApplyDeclarationForOrderActivity.this,
							"身份证号不能为空", Toast.LENGTH_SHORT).show();
					edt_idCard.requestFocusFromTouch();
				} else {
					if (!IdCardCheckUtils.isIdCard((idCard.toUpperCase()))) {
						Toast.makeText(ApplyDeclarationForOrderActivity.this,
								"请输入正确的身份证号", Toast.LENGTH_SHORT).show();
						edt_idCard.requestFocusFromTouch();
					} else {
						if (investdate.equals("打款日期")) {
							Toast.makeText(
									ApplyDeclarationForOrderActivity.this,
									"请选择打款日期", Toast.LENGTH_SHORT).show();

						} else {
							if (PreferenceUtil.getUserType().equals("corp")) {
								if (TextUtils.isEmpty(userName)) {
									Toast.makeText(
											ApplyDeclarationForOrderActivity.this,
											"理财师姓名不能为空", Toast.LENGTH_SHORT)
											.show();
									edt_userName.requestFocusFromTouch();
								} else {
									if (TextUtils.isEmpty(userPhone)) {
										Toast.makeText(
												ApplyDeclarationForOrderActivity.this,
												"理财师电话不能为空", Toast.LENGTH_SHORT)
												.show();
										edt_userPhone.requestFocusFromTouch();
									} else {
										if (StringUtil.isMobileNO(edt_userPhone
												.getText().toString())) {
											if (isIDpros == false) {
												Toast.makeText(
														ApplyDeclarationForOrderActivity.this,
														"请上传身份证正面",
														Toast.LENGTH_SHORT)
														.show();
											} else {
												if (isIDcons == false) {
													Toast.makeText(
															ApplyDeclarationForOrderActivity.this,
															"请上传身份证反面",
															Toast.LENGTH_SHORT)
															.show();
												} else {
													if (isBank == false) {
														Toast.makeText(
																ApplyDeclarationForOrderActivity.this,
																"请上传银行卡照片",
																Toast.LENGTH_SHORT)
																.show();
													} else {
														if (isInvestmoney == false) {
															Toast.makeText(
																	ApplyDeclarationForOrderActivity.this,
																	"请上传打款凭条",
																	Toast.LENGTH_SHORT)
																	.show();
														} else {
															if (isSign == false) {
																Toast.makeText(
																		ApplyDeclarationForOrderActivity.this,
																		"请上传签字页",
																		Toast.LENGTH_SHORT)
																		.show();
															} else {
																requestApplyDeclarationData(
																		APPOID,
																		productId,
																		contractId,
																		productCategory,
																		productName,
																		customerName,
																		customerPhone,
																		insuranceAmount,
																		investDate,
																		sfzzmFileName,
																		sfzfmFileName,
																		cardFileName,
																		dkptFileName,
																		qzyFileName,
																		remark,
																		userName,
																		userPhone,
																		deputyInsuranceName,
																		ageCoverage,
																		payLimitTime,
																		commissionRatio,
																		deputyInsuranceAmount,
																		insuranceRebateProportion,
																		idCard.toUpperCase(),
																		deputyInsuranceNameId,"");
															}
														}
													}
												}
											}

										} else {
											Toast.makeText(
													ApplyDeclarationForOrderActivity.this,
													"请输入正确的手机号码",
													Toast.LENGTH_SHORT).show();
											edt_userPhone
													.requestFocusFromTouch();
										}
									}
								}
							} else {

								if (isIDpros == false) {
									Toast.makeText(
											ApplyDeclarationForOrderActivity.this,
											"请上传身份证正面", Toast.LENGTH_SHORT)
											.show();
								} else {
									if (isIDcons == false) {
										Toast.makeText(
												ApplyDeclarationForOrderActivity.this,
												"请上传身份证反面", Toast.LENGTH_SHORT)
												.show();
									} else {
										if (isBank == false) {
											Toast.makeText(
													ApplyDeclarationForOrderActivity.this,
													"请上传银行卡照片",
													Toast.LENGTH_SHORT).show();
										} else {
											if (isInvestmoney == false) {
												Toast.makeText(
														ApplyDeclarationForOrderActivity.this,
														"请上传打款凭条",
														Toast.LENGTH_SHORT)
														.show();
											} else {
												if (isSign == false) {
													Toast.makeText(
															ApplyDeclarationForOrderActivity.this,
															"请上传签字页",
															Toast.LENGTH_SHORT)
															.show();
												} else {
													requestApplyDeclarationData(
															APPOID,
															productId,
															contractId,
															productCategory,
															productName,
															customerName,
															customerPhone,
															insuranceAmount,
															investDate,
															sfzzmFileName,
															sfzfmFileName,
															cardFileName,
															dkptFileName,
															qzyFileName,
															remark,
															userName,
															userPhone,
															deputyInsuranceName,
															ageCoverage,
															payLimitTime,
															commissionRatio,
															deputyInsuranceAmount,
															insuranceRebateProportion,
															idCard.toUpperCase(),
															deputyInsuranceNameId,"");
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void requestApplyDeclarationData(final String appoitmentId,
			String productId, String contractId, String productCategory,
			String productName, String customerName, String customerPhone,
			String insuranceAmount, String paymoneyTime, String sfzzmFileName,
			String sfzfmFileName, String cardFileName, String dkptFileName,
			String qzyFileName, String remark, String userName,
			String userPhone, String deputyInsuranceName, String ageCoverage,
			String payLimitTimeone, String rebateProportionone,
			String deputyInsuranceAmount, String insuranceRebateProportion,
			String customerIDcard, String deputyInsuranceId,String costAmount) {
		if (deputyInsuranceName.equals("选择附加险")) {
			deputyInsuranceName = "(null)";
		}
		if (ageCoverage.equals("选择年龄")) {
			ageCoverage = "(null)";
		}
		if (ageCoverage.equals("选择缴费年期")) {
			payLimitTimeone = "(null)";
		}
		/*
		 * else { if (!(payLimitTimeone.equals("趸缴") ||
		 * payLimitTimeone.equals("趸交"))) { payLimitTimeone =
		 * payLimitTimeone.substring(0, payLimitTimeone.lastIndexOf("年")); } }
		 */

		HtmlRequest.getApplyDeclaration(ApplyDeclarationForOrderActivity.this,
				appoitmentId, productId, contractId, productCategory,
				productName, customerName, customerPhone, insuranceAmount,
				paymoneyTime, sfzzmFileName, sfzfmFileName, cardFileName,
				dkptFileName, qzyFileName, remark, userName, userPhone,
				deputyInsuranceName, ageCoverage, payLimitTimeone,
				rebateProportionone, deputyInsuranceAmount,
				insuranceRebateProportion, customerIDcard, deputyInsuranceId,costAmount,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							ResultApplyDeclarationContentBean data = (ResultApplyDeclarationContentBean) params.result;
							if (data != null) {
								if (data.getFlag().equals("true")) {
									Toast.makeText(
											ApplyDeclarationForOrderActivity.this,
											"申请报单成功", Toast.LENGTH_LONG).show();
									Intent intent = new Intent(
											ApplyDeclarationForOrderActivity.this,
											DeclarationActivity.class);
									startActivity(intent);
									stack.removeAllActivity();
									requestSMS(data.getOrderId(),data.getProductName());
								} else {
									Toast.makeText(
											ApplyDeclarationForOrderActivity.this,
											data.getMessage(),
											Toast.LENGTH_LONG).show();
								}
							}
						} else {
							Toast.makeText(
									ApplyDeclarationForOrderActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});

	}
	private void requestSMS(String orderId,String productName) {
		try {
			HtmlRequest.sentSMSForApplyDeclaration(ApplyDeclarationForOrderActivity.this,ApplicationConsts.APPLY_DECLARATION, orderId,
					productName, new OnRequestListener() {

						@Override
						public void onRequestFinished(BaseParams params) {
							ResultSentSMSContentBean b = (ResultSentSMSContentBean) params.result;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void selectBuyDate() {
		SelectWheeelDate wheel = new SelectWheeelDate(this,
				new OnWheelListener() {

					@Override
					public void onWheel(Boolean isSubmit, String year,
							String month, String day) {
						investDate = year + "-" + month + "-" + day;
						edt_investdate.setText(investDate);
					}
				}, true, new SimpleDateFormat("yyyy").format(new Date()),
				new SimpleDateFormat("M").format(new Date()),
				new SimpleDateFormat("dd").format(new Date()));
		wheel.show(findViewById(R.id.apply_declaration_product_scrollView));
	}

	private void requestDataSelectProduct(final String id, String category,
			String appoId) {
		HtmlRequest.getSelectProductReturn(
				ApplyDeclarationForOrderActivity.this, id, category, appoId,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							returnListContentBean = (ResultSelectProductReturnListContentBean) params.result;

							setRequestData(returnListContentBean);

						} else {
							Toast.makeText(
									ApplyDeclarationForOrderActivity.this,
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
		viewAnimatorLayout.setVisibility(View.GONE);
		viewAnimator.setVisibility(View.GONE);
		edt_insurance.setText("选择附加险");
		edt_age.setText("选择年龄");
		edt_date.setText("选择缴费年期");
		selectWhichLimit = -1;
		selectWhichAge = -1;
		selectProduct.setText(returnListContentBean.getName());
		txt_currency.setText(returnListContentBean.getCurrency());
		if (returnListContentBean.isFlag()) {// 是否保险产品
			if (returnListContentBean.getInsuranceChild().size() != 0) {
				edt_insurance.setVisibility(View.VISIBLE);

				MouldList<ResultSelectProductReturnInsuranceChildBean> childBeans = returnListContentBean
						.getInsuranceChild();
				multiChoiceItems = new String[childBeans.size()];
				multiChoiceItemsDeputyInsuranceID = new String[childBeans
						.size()];
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
		} else {
			viewAnimatorLayout.setVisibility(View.VISIBLE);
			viewAnimator.setVisibility(View.VISIBLE);
			viewAnimator.setDisplayedChild(0);
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
				 * edt_amount.setText(returnListContentBean.getAppolist().get(0)
				 * .getAMOUNT());
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

	private void setPhoto() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("选择图片");
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.setPositiveButton("相机", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(intent, CAMERA_REQUEST_CODE);
			}
		});
		builder.setNeutralButton("相册", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, GALLERY_REQUEST_CODE);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}

	// 根据用户选择，返回图片资源
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST_CODE) {
			if (data == null) {
				return;
			} else {
				Bundle extras = data.getExtras();
				Uri path = data.getData();
				if (extras != null) {
					Bitmap bm = extras.getParcelable("data");
					Uri uri = saveBitmap(bm);
					startImageZoom(uri);
				}
			}
		} else if (requestCode == GALLERY_REQUEST_CODE) {
			if (data == null) {
				return;
			}
			Uri uri;
			uri = data.getData();
			Uri fileUri = convertUri(uri);
			startImageZoom(fileUri);
		} else if (requestCode == CROP_REQUEST_CODE) {
			if (data == null) {
				return;
			}
			Bundle extras = data.getExtras();
			if (extras == null) {
				return;
			}
			Bitmap bm = extras.getParcelable("data");
			newZoomImage = zoomImage(bm, 130, 130);
			sendImage(newZoomImage);
		}

	}

	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
			double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}

	private void sendImage(Bitmap bm) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] bytes = stream.toByteArray();
		String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));

		try {
			String uploadType = "";
			if (photoType == 1) {
				uploadType = "sfzzm";
			} else if (photoType == 2) {
				uploadType = "sfzfm";
			} else if (photoType == 3) {
				uploadType = "card";
			} else if (photoType == 4) {
				uploadType = "dkpt";
			} else if (photoType == 5) {
				uploadType = "qzy";
			}
			String fileName = "";
			if (photoType == 1) {
				fileName = "sfzzm.jpg";
			} else if (photoType == 2) {
				fileName = "sfzfm.jpg";
			} else if (photoType == 3) {
				fileName = "card.jpg";
			} else if (photoType == 4) {
				fileName = "dkpt.jpg";
			} else if (photoType == 5) {
				fileName = "qzy.jpg";
			}
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			params.add("uploadFile", img);
			params.add("uploadType", uploadType);
			params.add("fileName", fileName);

			String url = ApplicationConsts.URL_GETUPDATEPHOTOSTATE;
			client.post(url, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String content) {
					super.onSuccess(statusCode, headers, content);
					try {
						String data = DESUtil.decrypt(content);
						Gson json = new Gson();
						ResultUploadListBean bean = json.fromJson(data,
								ResultUploadListBean.class);
						ResultUploadListContentBean contentBean = bean
								.getData();
						if (photoType == 1) {
							sfzzmFileName = contentBean.getTmpFileName();
						} else if (photoType == 2) {
							sfzfmFileName = contentBean.getTmpFileName();
						} else if (photoType == 3) {
							cardFileName = contentBean.getTmpFileName();
						} else if (photoType == 4) {
							dkptFileName = contentBean.getTmpFileName();
						} else if (photoType == 5) {
							qzyFileName = contentBean.getTmpFileName();
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					mthread = new Thread(myRunnable);
					mthread.start();

				}

				@Override
				public void onFailure(Throwable error, String content) {
					super.onFailure(error, content);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				if (photoType == 1) {
					img_IDpros.setImageBitmap(newZoomImage);
					isIDpros = true;
				} else if (photoType == 2) {
					img_IDcons.setImageBitmap(newZoomImage);
					isIDcons = true;
				} else if (photoType == 3) {
					img_bank.setImageBitmap(newZoomImage);
					isBank = true;
				} else if (photoType == 4) {
					img_investmoney.setImageBitmap(newZoomImage);
					isInvestmoney = true;
				} else if (photoType == 5) {
					img_sign.setImageBitmap(newZoomImage);
					isSign = true;
				}
			} else {
			}
		}

	}

	Runnable myRunnable = new Runnable() {
		@Override
		public void run() {
			Message msg = mHandler.obtainMessage();
			msg.what = 1;
			mHandler.sendMessage(msg);
		}
	};
	private ActivityStack stack;

	private Uri saveBitmap(Bitmap bm) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File tmpDir = new File(IMG_PATH);
			if (!tmpDir.exists()) {
				tmpDir.mkdirs();
			}
			File img = new File(IMG_PATH + "Test.png");
			try {
				FileOutputStream fos = new FileOutputStream(img);
				bm.compress(Bitmap.CompressFormat.PNG, 70, fos);
				fos.flush();
				fos.close();
				return Uri.fromFile(img);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}

	}

	private Uri convertUri(Uri uri) {
		InputStream is = null;
		try {
			is = this.getContentResolver().openInputStream(uri);
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			is.close();
			return saveBitmap(bitmap);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void startImageZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 110);
		intent.putExtra("outputY", 110);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_REQUEST_CODE);
	}

}
