package com.cf360.act;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.ApplicationConsts;
import com.cf360.R;
import com.cf360.bean.ResultApplyDeclarationContentBean;
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
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.IdCardCheckUtils;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.uitls.StringUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;
import com.cf360.wheel.widget.SelectWheeelDate;
import com.cf360.wheel.widget.SelectWheeelDate.OnWheelListener;
import com.google.gson.Gson;

/**
 * (非保险)我要报单
 * 
 */
public class ApplyDeclarationNotInsuranceActivity extends BaseActivity
		implements OnClickListener {
	private ScrollView scrollView;
	private RelativeLayout layout_selectProduct;
	private TextView selectProduct;
	// private TextView edt_contractNumber;
	private EditText edt_customerName;// 客户姓名
	private EditText edt_userName;// 理财师
	private EditText edt_userPhone;// 理财师电话

	private EditText edt_amount;// 打款金额
	private EditText edt_costAmount;// 认购费
	private TextView txt_amountInfo;// 打款金额提示
	private EditText edt_idCard;// 身份证号
	private String investDate;

	private TextView edt_investdate;// 打款日期
	private EditText edt_remark;// 备注

	private Button btnApply;

	private String APPOID;
	private ResultSelectProductReturnListContentBean returnListContentBean;

	private String productId;
	// private String contractId;
	private String productCategory;
	private String productName;
	private String customerPhone;

	private ImageView img_IDpros;// 身份证正面
	private ImageView img_IDcons;// 身份证反面
	private ImageView img_bank;// 银行卡
	private ImageView img_investmoney;// 打款凭条
	private ImageView img_sign;// 签字页
	private RelativeLayout layoutInvestmoney;

	private boolean isInvestmoney;

	private String sfzzmFileName;
	private String sfzfmFileName;
	private String cardFileName;
	private String dkptFileName;
	private String qzyFileName;

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
	private Bitmap newZoomImageInvestMoney;
	private MyHandler mHandler;
	private Thread mthread;

	/**
	 * 图片保存SD卡位置
	 */
	private final static String IMG_PATH = Environment
			.getExternalStorageDirectory() + "/cf360/imgs/";

	private String deputyInsuranceName = "(null)";
	private String ageCoverage = "(null)";
	private String commissionRatio = "(null)";
	private String deputyInsuranceAmount = "(null)";
	private String insuranceRebateProportion = "(null)";
	private String payLimitTime = "(null)";
	private String deputyInsuranceNameId = "(null)";

	// private Spinner spinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_apply_declaration_not_insurance);
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
		title.addAction(new Action(2, 0, R.color.orange),
				ApplyDeclarationNotInsuranceActivity.this.getResources()
						.getString(R.string.title_apply_declaration_info));
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
								ApplyDeclarationNotInsuranceActivity.this,
								DeclarationInfoActivity.class);
						startActivity(intent);
					}
				});
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		scrollView = (ScrollView) findViewById(R.id.apply_declaration_product_scrollView);
		layout_selectProduct = (RelativeLayout) findViewById(R.id.apply_declaration_product_layout);
		selectProduct = (TextView) findViewById(R.id.apply_declaration_product_name);
		// edt_contractNumber = (EditText)
		// findViewById(R.id.apply_declaration_edit_contract_number);
		edt_customerName = (EditText) findViewById(R.id.apply_declaration_edit_name);

		edt_amount = (EditText) findViewById(R.id.apply_declaration_edit_investmoney);
		edt_costAmount = (EditText) findViewById(R.id.apply_declaration_edit_costAmount);
		txt_amountInfo = (TextView) findViewById(R.id.apply_declaration_txt_amount_info);
		// txt_currency = (TextView)
		// findViewById(R.id.apply_declaration_txt_currency);
		edt_idCard = (EditText) findViewById(R.id.apply_declaration_edit_IDcard);

		edt_userName = (EditText) findViewById(R.id.apply_declaration_edit_financial_planners);
		edt_userPhone = (EditText) findViewById(R.id.apply_declaration_edit_financial_planners_phone);
		edt_investdate = (TextView) findViewById(R.id.apply_declaration_edit_investdate);
		edt_remark = (EditText) findViewById(R.id.note_edit);
		btnApply = (Button) findViewById(R.id.btn_save);

		// 上传图片
		img_IDpros = (ImageView) findViewById(R.id.apply_declaration_img_IDpros);
		img_IDcons = (ImageView) findViewById(R.id.apply_declaration_img_IDcons);
		img_bank = (ImageView) findViewById(R.id.apply_declaration_img_bank);
		img_investmoney = (ImageView) findViewById(R.id.apply_declaration_img_investmoney_receipt);
		layoutInvestmoney = (RelativeLayout) findViewById(R.id.apply_declaration_edit_investmoney_receipt_layout);
		img_sign = (ImageView) findViewById(R.id.apply_declaration_img_sign);
		/*
		 * //根据id获取对象 spinner=(Spinner) findViewById(R.id.spinner); //显示的数组
		 * final String arr[]=new String[]{ "星期一", "星期二", "星期三", "星期四", "星期五",
		 * "星期六", "星期日" };
		 * 
		 * ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_spinner_item, arr); //设置显示的数据
		 * spinner.setAdapter(arrayAdapter);
		 * Toast.makeText(getApplicationContext(),
		 * "main Thread"+spinner.getItemIdAtPosition
		 * (spinner.getSelectedItemPosition()), Toast.LENGTH_LONG).show();
		 * 
		 * //注册事件 spinner.setOnItemSelectedListener(new
		 * AdapterView.OnItemSelectedListener() {
		 * 
		 * @Override public void onItemSelected(AdapterView<?> parent, View
		 * view, int position, long id) { Spinner spinner=(Spinner) parent;
		 * Toast.makeText(getApplicationContext(),
		 * "xxxx"+spinner.getItemAtPosition(position),
		 * Toast.LENGTH_LONG).show(); }
		 * 
		 * @Override public void onNothingSelected(AdapterView<?> parent) {
		 * Toast.makeText(getApplicationContext(), "没有改变的处理",
		 * Toast.LENGTH_LONG).show(); }
		 * 
		 * });
		 */

		if (netHint_2 != null) {
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;

	}

	private void initData() {
		btnApply.setOnClickListener(this);
		layout_selectProduct.setOnClickListener(this);
		edt_investdate.setOnClickListener(this);

		img_IDpros.setOnClickListener(this);
		img_IDcons.setOnClickListener(this);
		img_bank.setOnClickListener(this);
		img_investmoney.setOnClickListener(this);
		layoutInvestmoney.setOnClickListener(this);
		img_sign.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.apply_declaration_product_layout:
			Intent intent = new Intent(this, SelectProductActivity.class);
			intent.putExtra("remark", "ApplyDeclarationNotInsuranceActivity");
			startActivityForResult(intent, 1000);
			break;
		case R.id.btn_save:
			String contractId = "(null)";
			String customerName = edt_customerName.getText().toString();
			String amount = edt_amount.getText().toString();
			String costAmount = edt_costAmount.getText().toString();
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
				if (TextUtils.isEmpty(amount)) {
					Toast.makeText(ApplyDeclarationNotInsuranceActivity.this,
							"打款金额不能为空", Toast.LENGTH_SHORT).show();
					edt_amount.requestFocusFromTouch();
				} else {
					if (!StringUtil.checkNumber(amount)) {
						Toast.makeText(
								ApplyDeclarationNotInsuranceActivity.this,
								"打款金额请输入正确数字格式", Toast.LENGTH_SHORT).show();
						edt_amount.requestFocusFromTouch();
					} else {
						if (StringUtil.isDoubleForTwoNumber(amount)) {
							String minAmountSring = returnListContentBean
									.getMinPayAmount();
							String canOrderAmountString = returnListContentBean
									.getCanOrderAmount();
							double minAmount = Double
									.parseDouble(minAmountSring);
							double canOrderAmount = Double
									.parseDouble(canOrderAmountString);
							double inputAmount = Double.parseDouble(edt_amount
									.getText().toString());
							if (inputAmount < minAmount) {
								Toast.makeText(
										ApplyDeclarationNotInsuranceActivity.this,
										"打款金额不得小于最小认购金额" + minAmount + "万元",
										Toast.LENGTH_SHORT).show();
								edt_amount.requestFocusFromTouch();
								return;
							}
							if ((canOrderAmount - inputAmount) < minAmount) {
								if ((canOrderAmount - inputAmount) == 0) {

									IsRight(contractId, customerName, idCard,
											investdate, remark, userName,
											userPhone, amount, costAmount);

								} else if ((canOrderAmount - inputAmount) < 0) {
									Toast.makeText(
											ApplyDeclarationNotInsuranceActivity.this,
											"打款金额不得大于剩余金额" + canOrderAmount
													+ "万元", Toast.LENGTH_SHORT)
											.show();
									edt_amount.requestFocusFromTouch();

								} else {
									Toast.makeText(
											ApplyDeclarationNotInsuranceActivity.this,
											"剩余金额不得小于最小认购金额" + minAmount + "万元",
											Toast.LENGTH_SHORT).show();
									edt_amount.requestFocusFromTouch();
								}
								return;
							} else {
								if ((canOrderAmount - inputAmount) > minAmount
										|| (canOrderAmount - inputAmount) == minAmount) {

									IsRight(contractId, customerName, idCard,
											investdate, remark, userName,
											userPhone, amount, costAmount);

								} else {
									Toast.makeText(
											ApplyDeclarationNotInsuranceActivity.this,
											"剩余金额不得小于最小认购金额" + minAmount + "万元",
											Toast.LENGTH_SHORT).show();
									edt_amount.requestFocusFromTouch();
								}
							}

						} else {
							Toast.makeText(
									ApplyDeclarationNotInsuranceActivity.this,
									"打款金额只能输入2位小数", Toast.LENGTH_SHORT).show();
							edt_amount.requestFocusFromTouch();
						}
					}
				}
			} else {
				Toast.makeText(ApplyDeclarationNotInsuranceActivity.this,
						"请选择产品", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.apply_declaration_edit_investdate:
			selectBuyDate();
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
		case R.id.apply_declaration_img_investmoney_receipt:
			Intent i_photo = new Intent(this, ImageShower.class);
			i_photo.putExtra("bitmap", newZoomImageInvestMoney);
			startActivity(i_photo);
			break;
		case R.id.apply_declaration_edit_investmoney_receipt_layout:
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

	private void IsRight(String contractId, String customerName, String idCard,
			String investdate, String remark, String userName,
			String userPhone, String amount, String costAmount) {
		if (TextUtils.isEmpty(customerName)) {
			Toast.makeText(ApplyDeclarationNotInsuranceActivity.this,
					"客户名不能为空", Toast.LENGTH_SHORT).show();
			edt_customerName.requestFocusFromTouch();
		} else {
			if (TextUtils.isEmpty(idCard)) {
				Toast.makeText(ApplyDeclarationNotInsuranceActivity.this,
						"身份证号不能为空", Toast.LENGTH_SHORT).show();
				edt_idCard.requestFocusFromTouch();
			} else {
				if (!IdCardCheckUtils.isIdCard((idCard.toUpperCase()))) {
					Toast.makeText(ApplyDeclarationNotInsuranceActivity.this,
							"请输入正确的身份证号", Toast.LENGTH_SHORT).show();
					edt_idCard.requestFocusFromTouch();
				} else {
					if (TextUtils.isEmpty(costAmount)) {
						Toast.makeText(ApplyDeclarationNotInsuranceActivity.this,
								"认购费不能为空", Toast.LENGTH_SHORT).show();
						edt_costAmount.requestFocusFromTouch();
					} else {
					if (!StringUtil.checkNumber(costAmount)) {
						Toast.makeText(
								ApplyDeclarationNotInsuranceActivity.this,
								"认购费请输入正确数字格式", Toast.LENGTH_SHORT).show();
						edt_costAmount.requestFocusFromTouch();
					} else {
						if (StringUtil.isDoubleForTwoNumber(costAmount)) {

							if (investdate.equals("打款日期")) {
								Toast.makeText(
										ApplyDeclarationNotInsuranceActivity.this,
										"请选择打款日期", Toast.LENGTH_SHORT).show();

							} else {
								if (PreferenceUtil.getUserType().equals("corp")) {
									if (TextUtils.isEmpty(userName)) {
										Toast.makeText(
												ApplyDeclarationNotInsuranceActivity.this,
												"理财师姓名不能为空", Toast.LENGTH_SHORT)
												.show();
										edt_userName.requestFocusFromTouch();
									} else {
										if (TextUtils.isEmpty(userPhone)) {
											Toast.makeText(
													ApplyDeclarationNotInsuranceActivity.this,
													"理财师电话不能为空",
													Toast.LENGTH_SHORT).show();
											edt_userPhone
													.requestFocusFromTouch();
										} else {
											if (StringUtil
													.isMobileNO(edt_userPhone
															.getText()
															.toString())) {
												if (isInvestmoney == false) {
													Toast.makeText(
															ApplyDeclarationNotInsuranceActivity.this,
															"请上传打款凭条",
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
															amount,
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
															deputyInsuranceNameId,costAmount);

												}

											} else {
												Toast.makeText(
														ApplyDeclarationNotInsuranceActivity.this,
														"请输入正确的手机号码",
														Toast.LENGTH_SHORT)
														.show();
												edt_userPhone
														.requestFocusFromTouch();
											}
										}
									}
								} else {
									if (isInvestmoney == false) {
										Toast.makeText(
												ApplyDeclarationNotInsuranceActivity.this,
												"请上传打款凭条", Toast.LENGTH_SHORT)
												.show();
									} else {

										requestApplyDeclarationData(APPOID,
												productId, contractId,
												productCategory, productName,
												customerName, customerPhone,
												amount, investDate,
												sfzzmFileName, sfzfmFileName,
												cardFileName, dkptFileName,
												qzyFileName, remark, userName,
												userPhone, deputyInsuranceName,
												ageCoverage, payLimitTime,
												commissionRatio,
												deputyInsuranceAmount,
												insuranceRebateProportion,
												idCard.toUpperCase(),
												deputyInsuranceNameId,costAmount);

									}
								}
							}

						}else{
							Toast.makeText(
									ApplyDeclarationNotInsuranceActivity.this,
									"认购费只能输入2位小数", Toast.LENGTH_SHORT).show();
							edt_costAmount.requestFocusFromTouch();
						}
					}}

				}
			}
		}
	}

	private void requestApplyDeclarationData(final String appoitmentId,
			String productId, String contractId, String productCategory,
			String productName, String customerName, String customerPhone,
			String amount, String paymoneyTime, String sfzzmFileName,
			String sfzfmFileName, String cardFileName, String dkptFileName,
			String qzyFileName, String remark, String userName,
			String userPhone, String deputyInsuranceName, String ageCoverage,
			String payLimitTimeone, String rebateProportionone,
			String deputyInsuranceAmount, String insuranceRebateProportion,
			String customerIDcard, String deputyInsuranceId,String costAmount) {
		HtmlRequest.getApplyDeclaration(
				ApplyDeclarationNotInsuranceActivity.this, appoitmentId,
				productId, contractId, productCategory, productName,
				customerName, customerPhone, amount, paymoneyTime,
				sfzzmFileName, sfzfmFileName, cardFileName, dkptFileName,
				qzyFileName, remark, userName, userPhone, deputyInsuranceName,
				ageCoverage, payLimitTimeone, rebateProportionone,
				deputyInsuranceAmount, insuranceRebateProportion,
				customerIDcard, deputyInsuranceId,costAmount, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							ResultApplyDeclarationContentBean data = (ResultApplyDeclarationContentBean) params.result;
							if (data != null) {
								if (data.getFlag().equals("true")) {
									Toast.makeText(
											ApplyDeclarationNotInsuranceActivity.this,
											"申请报单成功", Toast.LENGTH_LONG).show();
									Intent intent = new Intent(
											ApplyDeclarationNotInsuranceActivity.this,
											DeclarationActivity.class);
									ApplyDeclarationNotInsuranceActivity.this
											.setResult(2, intent);
									ApplyDeclarationNotInsuranceActivity.this
											.finish();
									requestSMS(data.getOrderId(),
											data.getProductName());
								} else {
									Toast.makeText(
											ApplyDeclarationNotInsuranceActivity.this,
											data.getMessage(),
											Toast.LENGTH_LONG).show();
								}
							}
						} else {
							Toast.makeText(
									ApplyDeclarationNotInsuranceActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	private void requestSMS(String orderId, String productName) {
		try {
			HtmlRequest.sentSMSForApplyDeclaration(
					ApplyDeclarationNotInsuranceActivity.this,
					ApplicationConsts.APPLY_DECLARATION, orderId, productName,
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
				ApplyDeclarationNotInsuranceActivity.this, id, category,
				appoId, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							returnListContentBean = null;
							returnListContentBean = (ResultSelectProductReturnListContentBean) params.result;

							setRequestData(returnListContentBean);

						} else {
							Toast.makeText(
									ApplyDeclarationNotInsuranceActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	protected void setRequestData(
			ResultSelectProductReturnListContentBean returnListContentBean) {
		edt_amount.setText("");
		// edt_contractNumber.setText("");
		edt_investdate.setText("打款日期");
		edt_userName.setText("");
		edt_userPhone.setText("");
		edt_customerName.setText("");
		edt_remark.setText("");
		edt_idCard.setText("");
		selectProduct.setText(returnListContentBean.getName());
		txt_amountInfo.setText("已募集金额："
				+ returnListContentBean.getOrderAmount() + "万元  还可报单："
				+ returnListContentBean.getCanOrderAmount() + "万元");
		txt_amountInfo.setVisibility(View.VISIBLE);
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
		if (resultCode == 2000) {
			String ID = data.getExtras().getString("ID");
			String CAREGORY = data.getExtras().getString("CAREGORY");
			APPOID = data.getExtras().getString("APPOID");

			requestDataSelectProduct(ID, CAREGORY, APPOID);

		}
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
			if (photoType == 4) {
				newZoomImageInvestMoney = newZoomImage;
			}
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
				} else if (photoType == 2) {
					img_IDcons.setImageBitmap(newZoomImage);
				} else if (photoType == 3) {
					img_bank.setImageBitmap(newZoomImage);
				} else if (photoType == 4) {
					img_investmoney.setImageBitmap(newZoomImageInvestMoney);
					img_investmoney.setVisibility(View.VISIBLE);
					isInvestmoney = true;
				} else if (photoType == 5) {
					img_sign.setImageBitmap(newZoomImage);
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
