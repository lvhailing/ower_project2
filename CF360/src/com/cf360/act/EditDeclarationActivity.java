package com.cf360.act;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.cf360.bean.ResultApplyDeclarationBean;
import com.cf360.bean.ResultDeclarationSearchCommissionratioDataBean;
import com.cf360.bean.ResultDeclarationSearchCommissionratioListContentBean;
import com.cf360.bean.ResultEditDeclarationDeputyBean;
import com.cf360.bean.ResultEditDeclarationDeputyNameBean;
import com.cf360.bean.ResultEditDeclarationListContentBean;
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
import com.cf360.view.AmountDialog.OnSignContractChanged;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;
import com.cf360.wheel.widget.SelectWheeelDate;
import com.cf360.wheel.widget.SelectWheeelDate.OnWheelListener;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 修改报单
 * 
 */
public class EditDeclarationActivity extends BaseActivity implements
		OnClickListener {
	private String activityMark;
	private String id;
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

	private Button btnEdit;

	private String APPOID;
	private String Insurance;
	private String Age;
	private String Limit;
	private String Commission;
	private String investDate;

	private RelativeLayout viewAnimatorLayout;
	private ViewAnimator viewAnimator;
	private EditText edt_amount;// 打款金额
	private TextView txt_amount;// 打款金额
	private TextView txt_currency;// 货币
	private EditText edt_idCard;// 身份证号

	private String deputyInsuranceName;
	private String deputyInsuranceNameId;
	private String deputyInsuranceNameContrast = "";
	private String ageCoverage;
	private String payLimitTime;
	private String payLimitTimeContrast = "";

	private String insuranceAmount;
	private String deputyInsuranceAmount;
	private String commissionRatio;
	private String insuranceRebateProportion;

	private String[] multiChoiceItems;
	private String[] multiChoiceItemsDeputyInsuranceID;
	private String[] singleChoiceItemsAge;
	private String[] singleChoiceItemsLimit;
	private boolean[] defaultSelectedStatus;
	private boolean[] defaultSelectedStatusTemporary;

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

	private boolean isIDprosEdit;
	private boolean isIDconsEdit;
	private boolean isBankEdit;
	private boolean isInvestmoneyEdit;
	private boolean isSignEdit;

	private String sfzzmFileName;
	private String sfzfmFileName;
	private String cardFileName;
	private String dkptFileName;
	private String qzyFileName;

	private int photoType = 1;
	private ResultEditDeclarationListContentBean editDeclarationListContentBean;
	private ResultDeclarationSearchCommissionratioListContentBean commissionratioListContentBeans;
	private MouldList<ResultDeclarationSearchCommissionratioDataBean> searchCommissionratioDataBeans;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	protected double amountSum = 0;
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
	private DisplayImageOptions options;
	/**
	 * 图片保存SD卡位置
	 */
	private final static String IMG_PATH = Environment
			.getExternalStorageDirectory() + "/cf360/imgs/";

	private ImageLoader imageLoader = ImageLoader.getInstance();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_edit_declaration);
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
		id = getIntent().getStringExtra("id");
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setLogo(R.drawable.img_logo, false)
				.setIndicator(R.drawable.back)
				.setCenterText(
						getResources().getString(
								R.string.title_edit_declaration))
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
		stack = ActivityStack.getActivityManage();
		stack.addActivity(this);
		scrollView = (ScrollView) findViewById(R.id.edit_declaration_product_scrollView);
		scrollView.smoothScrollTo(0, 0);
		selectProduct = (TextView) findViewById(R.id.edit_declaration_product_name);
		edt_contractNumber = (EditText) findViewById(R.id.edit_declaration_edit_contract_number);
		edt_insurance = (TextView) findViewById(R.id.edit_declaration_edit_baodan_select_insurance);
		edt_age = (TextView) findViewById(R.id.edit_declaration_edit_select_age);
		edt_date = (TextView) findViewById(R.id.edit_declaration_edit_select_date);
		edt_customerName = (EditText) findViewById(R.id.edit_declaration_edit_name);

		viewAnimator = (ViewAnimator) findViewById(R.id.edit_declaration_viewanimator);
		viewAnimatorLayout = (RelativeLayout) findViewById(R.id.edit_declaration_viewanimator_layout);
		edt_amount = (EditText) findViewById(R.id.edit_declaration_edit_investmoney);
		txt_currency = (TextView) findViewById(R.id.edit_declaration_txt_currency);
		txt_amount = (TextView) findViewById(R.id.edit_declaration_txt_investmoney);
		edt_idCard = (EditText) findViewById(R.id.edit_declaration_edit_IDcard);

		edt_userName = (EditText) findViewById(R.id.edit_declaration_edit_financial_planners);
		edt_userPhone = (EditText) findViewById(R.id.edit_declaration_edit_financial_planners_phone);
		edt_investdate = (TextView) findViewById(R.id.edit_declaration_edit_investdate);
		edt_remark = (EditText) findViewById(R.id.note_edit);
		btnEdit = (Button) findViewById(R.id.btn_edit);

		// 上传图片
		img_IDpros = (ImageView) findViewById(R.id.edit_declaration_img_IDpros);
		img_IDcons = (ImageView) findViewById(R.id.edit_declaration_img_IDcons);
		img_bank = (ImageView) findViewById(R.id.edit_declaration_img_bank);
		img_investmoney = (ImageView) findViewById(R.id.edit_declaration_img_investmoney);
		img_sign = (ImageView) findViewById(R.id.edit_declaration_img_sign);

		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void initData() {
		btnEdit.setOnClickListener(this);
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

		img_IDpros.setClickable(false);
		img_IDcons.setClickable(false);
		img_bank.setClickable(false);
		img_investmoney.setClickable(false);
		img_sign.setClickable(false);
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.cf360_hot)
				.showImageOnFail(R.drawable.cf360_hot)
				.resetViewBeforeLoading(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300)).build();
		requestData(id);
	}

	private void requestData(final String id) {
		HtmlRequest.getEditDeclaration(EditDeclarationActivity.this, id,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							editDeclarationListContentBean = (ResultEditDeclarationListContentBean) params.result;
							setRequestData(editDeclarationListContentBean);
						} else {
							Toast.makeText(EditDeclarationActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	protected void setRequestData(ResultEditDeclarationListContentBean bean) {
		selectProduct.setText(bean.getOrder().get(0).getPRODUCTNAME());
		edt_contractNumber.setText(bean.getOrder().get(0).getCONTRACTID());
		edt_customerName.setText(bean.getOrder().get(0).getCUSTOMERNAME());
		edt_userName.setText(bean.getOrder().get(0).getUSERNAME());
		edt_userPhone.setText(bean.getOrder().get(0).getUSERPHONE());
		edt_investdate.setText(bean.getOrder().get(0).getPAYMONEYTIME());
		edt_remark.setText(bean.getOrder().get(0).getREMARK());
		txt_currency.setText(bean.getCurrency());
		txt_currency.setVisibility(View.VISIBLE);
		edt_idCard.setText(bean.getOrder().get(0).getCUSTOMERIDCARD());

		if (bean.getOrder().get(0).getPRODUCTCATEGORY().equals("BX")) {
			searchCommissionratioDataBeans = new MouldList<ResultDeclarationSearchCommissionratioDataBean>();
			// 获取请求数据后传入打款金额adapter searchCommissionratioDataBeans
			// 以及直接保存获取commissionRatio，insuranceRebateProportion
			for (int j = 0; j < bean.getNameList().size(); j++) {
				ResultDeclarationSearchCommissionratioDataBean beanChild = new ResultDeclarationSearchCommissionratioDataBean();
				beanChild.setPRODUCTNAME(bean.getNameList().get(j));
				beanChild.setCOMMISSIONRATIO(bean.getRationList().get(j));
				searchCommissionratioDataBeans.add(beanChild);
			}

			commissionRatio = searchCommissionratioDataBeans.get(0)
					.getCOMMISSIONRATIO();
			StringBuilder commissionratioSB = new StringBuilder();
			// 返佣比例
			for (int k = 0; k < searchCommissionratioDataBeans.size(); k++) {
				String commissionratio = searchCommissionratioDataBeans.get(k)
						.getCOMMISSIONRATIO();
				commissionratioSB.append(commissionratio + ",");
			}
			commissionratioSB.deleteCharAt(commissionratioSB.length() - 1);
			if (searchCommissionratioDataBeans.size() != 1) {
				insuranceRebateProportion = commissionratioSB.toString()
						.substring(
								commissionratioSB.toString().indexOf(",") + 1,
								commissionratioSB.toString().length());
			} else {
				insuranceRebateProportion = null;
			}
			// 直接保存获取 insuranceAmount, deputyInsuranceAmount
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bean.getAmountList().size(); i++) {
				String value = bean.getAmountList().get(i);
				sb.append(value + ",");
			}
			insuranceAmount = sb.toString().substring(0,
					sb.toString().indexOf(","));
			sb.deleteCharAt(sb.length() - 1);
			if (bean.getAmountList().size() != 1) {
				deputyInsuranceAmount = sb.toString().substring(
						sb.toString().indexOf(",") + 1, sb.toString().length());
			} else {
				deputyInsuranceAmount = null;
			}

			// 获取请求数据后传入打款金额adapter map
			for (int k = 0; k < bean.getAmountList().size(); k++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("value", bean.getAmountList().get(k));
				data.add(map);
			}
			txt_amount.setText(bean.getOrder().get(0).getAMOUNT());
			viewAnimatorLayout.setVisibility(View.VISIBLE);
			viewAnimator.setVisibility(View.VISIBLE);
			viewAnimator.setDisplayedChild(1);
		}
		// 附加险
		if (bean.getDeputyName().size() != 0) {
			if (bean.getOrder().get(0).getDEPUTYINSURANCENAME().equals("无")) {
				edt_insurance.setText("选择附加险");
				edt_insurance.setVisibility(View.VISIBLE);
				MouldList<ResultEditDeclarationDeputyNameBean> childBeans = bean
						.getDeputyName();
				multiChoiceItems = new String[childBeans.size()];
				multiChoiceItemsDeputyInsuranceID = new String[childBeans
						.size()];
				defaultSelectedStatus = new boolean[multiChoiceItems.length];
				defaultSelectedStatusTemporary = new boolean[multiChoiceItems.length];
				for (int i = 0; i < childBeans.size(); i++) {
					multiChoiceItems[i] = String.valueOf(childBeans.get(i)
							.getNAME());
					multiChoiceItemsDeputyInsuranceID[i] = String
							.valueOf(childBeans.get(i).getID());
				}
			} else {
				edt_insurance.setText(bean.getOrder().get(0)
						.getDEPUTYINSURANCENAME());
				edt_insurance.setVisibility(View.VISIBLE);

				MouldList<ResultEditDeclarationDeputyNameBean> childBeans = bean
						.getDeputyName();
				multiChoiceItems = new String[childBeans.size()];
				multiChoiceItemsDeputyInsuranceID = new String[childBeans
						.size()];
				defaultSelectedStatus = new boolean[multiChoiceItems.length];
				defaultSelectedStatusTemporary = new boolean[multiChoiceItems.length];
				String[] name = bean.getOrder().get(0).getDEPUTYINSURANCENAME()

				.split(",");
				for (int i = 0; i < childBeans.size(); i++) {
					multiChoiceItems[i] = String.valueOf(childBeans.get(i)
							.getNAME());
					multiChoiceItemsDeputyInsuranceID[i] = String
							.valueOf(childBeans.get(i).getID());
					for (int j = 0; j < name.length; j++) {
						if (name[j].equals(childBeans.get(i).getNAME())) {
							defaultSelectedStatus[i] = true;
						}
					}
				}
				deputyInsuranceNameId = bean.getOrder().get(0)
						.getDEPUTYINSURANCEID();
			}
		}
		// 缴费年龄
		if (!bean.getOrder().get(0).getAGECOVERAGE().equals("无")) {
			edt_age.setText(bean.getOrder().get(0).getAGECOVERAGE());
			edt_age.setVisibility(View.VISIBLE);

			MouldList<ResultEditDeclarationDeputyBean> ageBeans = editDeclarationListContentBean
					.getAgeCoverageData();
			singleChoiceItemsAge = new String[ageBeans.size()];
			for (int i = 0; i < ageBeans.size(); i++) {
				if (bean.getOrder().get(0).getAGECOVERAGE()
						.equals(ageBeans.get(i).getAGECOVERAGE())) {
					selectWhichAge = i;// 与dialog中的数据相匹配
				}
				singleChoiceItemsAge[i] = String.valueOf(ageBeans.get(i)
						.getAGECOVERAGE());
			}
		}
		// 缴费年期
		if (!bean.getOrder().get(0).getPAYLIMITTIME().equals("无")) {
			edt_date.setText(bean.getOrder().get(0).getPAYLIMITTIME());
			edt_date.setVisibility(View.VISIBLE);

			ArrayList<String> limitBeans = editDeclarationListContentBean
					.getPayLimits();
			singleChoiceItemsLimit = new String[limitBeans.size()];
			for (int i = 0; i < limitBeans.size(); i++) {
				if (bean.getOrder().get(0).getPAYLIMITTIME()
						.equals(limitBeans.get(i))) {
					selectWhichLimit = i;// 与dialog中的数据相匹配
				}
				singleChoiceItemsLimit[i] = String.valueOf(limitBeans.get(i));
			}
		}
		deputyInsuranceName = edt_insurance.getText().toString();
		ageCoverage = edt_age.getText().toString();
		payLimitTime = edt_date.getText().toString();
		// 加载图片数据
		setPhotoData(bean);
	}

	private void setPhotoData(ResultEditDeclarationListContentBean bean) {
		String url_IDpros = bean.getOrder().get(0).getIDCARDPOSITIVE();
		if (!TextUtils.isEmpty(url_IDpros)) {
			// new ImageViewServiceIDpros().execute(url_IDpros);
			imageLoader.displayImage(url_IDpros, img_IDpros,
					options);
		} else {
			img_IDpros.setImageDrawable(getResources().getDrawable(
					R.drawable.add_picture));
		}

		String url_IDcons = bean.getOrder().get(0).getIDCARDNEGATIVE();
		if (!TextUtils.isEmpty(url_IDcons)) {
			// new ImageViewServiceIDcons().execute(url_IDcons);
			imageLoader.displayImage(url_IDcons, img_IDcons,
					options);
		} else {
			img_IDcons.setImageDrawable(getResources().getDrawable(
					R.drawable.add_picture));
		}

		String url_Bank = bean.getOrder().get(0).getBANKCARD();
		if (!TextUtils.isEmpty(url_Bank)) {
			// new ImageViewServiceBank().execute(url_Bank);
			imageLoader.displayImage(url_Bank, img_bank, options);
		} else {
			img_bank.setImageDrawable(getResources().getDrawable(
					R.drawable.add_picture));
		}

		String url_Investmoney = bean.getOrder().get(0).getPAYMENTRECEIPT();
		if (!TextUtils.isEmpty(url_Investmoney)) {
			// new ImageViewServiceInvestmoney().execute(url_Investmoney);
			imageLoader.displayImage(url_Investmoney,
					img_investmoney, options);
		} else {
			img_investmoney.setImageDrawable(getResources().getDrawable(
					R.drawable.add_picture));
		}

		String url_Sign = bean.getOrder().get(0).getSIGNATUREPAGE();
		if (!TextUtils.isEmpty(url_Sign)) {
			// new ImageViewServiceSign().execute(url_Sign);
			imageLoader.displayImage(url_Sign, img_sign, options);
		} else {
			img_sign.setImageDrawable(getResources().getDrawable(
					R.drawable.add_picture));
		}
		img_IDpros.setClickable(true);
		img_IDcons.setClickable(true);
		img_bank.setClickable(true);
		img_investmoney.setClickable(true);
		img_sign.setClickable(true);

	}

	class ImageViewServiceIDpros extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			// Bitmap data = decodeSampledBitmapFromResource(params[0], 60,100,
			// EditDeclarationActivity.this);
			Bitmap data = getImageBitmap(params[0]);
			return data;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);

			/*
			 * Bitmap bitmap = null; try { bitmap=result;
			 * img_IDpros.setImageBitmap(result); } catch (OutOfMemoryError e) {
			 * 
			 * } if (bitmap == null) { // 如果实例化失败 返回默认的Bitmap对象
			 * img_IDpros.setImageDrawable(getResources().getDrawable(
			 * R.drawable.add_picture)); }
			 */
			if (result != null) {
				img_IDpros.setImageBitmap(result);
			} else {
				img_IDpros.setImageDrawable(getResources().getDrawable(
						R.drawable.add_picture));
			}
		}

	}

	class ImageViewServiceIDcons extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			// Bitmap data = decodeSampledBitmapFromResource(params[0], 60,100,
			// EditDeclarationActivity.this);
			Bitmap data = getImageBitmap(params[0]);
			return data;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			/*
			 * Bitmap bitmap = null; try { bitmap=result;
			 * img_IDcons.setImageBitmap(result); } catch (OutOfMemoryError e) {
			 * 
			 * } if (bitmap == null) { // 如果实例化失败 返回默认的Bitmap对象
			 * img_IDcons.setImageDrawable(getResources().getDrawable(
			 * R.drawable.add_picture)); }
			 */
			if (result != null) {
				img_IDcons.setImageBitmap(result);
			} else {
				img_IDcons.setImageDrawable(getResources().getDrawable(
						R.drawable.add_picture));
			}
		}

	}

	class ImageViewServiceBank extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			// Bitmap data = decodeSampledBitmapFromResource(params[0], 60,100,
			// EditDeclarationActivity.this);
			Bitmap data = getImageBitmap(params[0]);
			return data;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			/*
			 * Bitmap bitmap = null; try { bitmap=result;
			 * img_bank.setImageBitmap(result); } catch (OutOfMemoryError e) {
			 * 
			 * } if (bitmap == null) { // 如果实例化失败 返回默认的Bitmap对象
			 * img_bank.setImageDrawable(getResources().getDrawable(
			 * R.drawable.add_picture)); }
			 */
			if (result != null) {
				img_bank.setImageBitmap(result);
			} else {
				img_bank.setImageDrawable(getResources().getDrawable(
						R.drawable.add_picture));
			}
		}

	}

	class ImageViewServiceInvestmoney extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			// Bitmap data = decodeSampledBitmapFromResource(params[0], 60,100,
			// EditDeclarationActivity.this);
			Bitmap data = getImageBitmap(params[0]);
			return data;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			/*
			 * Bitmap bitmap = null; try { bitmap=result;
			 * img_investmoney.setImageBitmap(result); } catch (OutOfMemoryError
			 * e) {
			 * 
			 * } if (bitmap == null) { // 如果实例化失败 返回默认的Bitmap对象
			 * img_investmoney.setImageDrawable(getResources().getDrawable(
			 * R.drawable.add_picture)); }
			 */
			if (result != null) {
				img_investmoney.setImageBitmap(result);
			} else {
				img_investmoney.setImageDrawable(getResources().getDrawable(
						R.drawable.add_picture));
			}
		}

	}

	class ImageViewServiceSign extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			// Bitmap data = decodeSampledBitmapFromResource(params[0], 60,100,
			// EditDeclarationActivity.this);
			Bitmap data = getImageBitmap(params[0]);
			return data;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			/*
			 * Bitmap bitmap = null; try { bitmap=result;
			 * img_sign.setImageBitmap(result); } catch (OutOfMemoryError e) {
			 * 
			 * } if (bitmap == null) { // 如果实例化失败 返回默认的Bitmap对象
			 * img_sign.setImageDrawable(getResources().getDrawable(
			 * R.drawable.add_picture)); }
			 */
			if (result != null) {
				img_sign.setImageBitmap(result);
			} else {
				img_sign.setImageDrawable(getResources().getDrawable(
						R.drawable.add_picture));
			}
		}

	}

	private Bitmap getImageBitmap(String url) {
		URL imgUrl = null;
		Bitmap bitmap = null;
		try {
			imgUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imgUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 计算缩放比例
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromResource(String urlPath,
			int reqWidth, int reqHeight, Context context) {
		URL imgUrl = null;
		Bitmap bitmap = null;
		try {
			imgUrl = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) imgUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
			BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
			onlyBoundsOptions.inJustDecodeBounds = true;
			onlyBoundsOptions.inDither = true;// optional
			onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
			bitmap = BitmapFactory.decodeStream(is, null, onlyBoundsOptions);
			onlyBoundsOptions.inSampleSize = calculateInSampleSize(
					onlyBoundsOptions, reqWidth, reqHeight);
			// 使用获取到的inSampleSize值再次解析图片
			onlyBoundsOptions.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeStream(is, null, onlyBoundsOptions);
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_edit:
			String contractId = edt_contractNumber.getText().toString();
			String deputyInsuranceName = edt_insurance.getText().toString();
			String ageCoverage = edt_age.getText().toString();
			String payLimitTime = edt_date.getText().toString();
			String customerName = edt_customerName.getText().toString();
			String amount = edt_amount.getText().toString();
			String insuranceAmountTxt = txt_amount.getText().toString();
			String investdate = edt_investdate.getText().toString();
			String remark = edt_remark.getText().toString();
			String userName = edt_userName.getText().toString();
			String userPhone = edt_userPhone.getText().toString();
			String idCard = edt_idCard.getText().toString();

			if (editDeclarationListContentBean != null) {
				productId = editDeclarationListContentBean.getOrder().get(0)
						.getPRODUCTID();
				productName = editDeclarationListContentBean.getOrder().get(0)
						.getPRODUCTNAME();
				productCategory = editDeclarationListContentBean.getOrder()
						.get(0).getPRODUCTCATEGORY();
				if (productCategory.equals("BX")) {// 是否保险产品

					if (TextUtils.isEmpty(contractId)) {
						Toast.makeText(EditDeclarationActivity.this,
								"合同编号不能为空", Toast.LENGTH_SHORT).show();
						edt_contractNumber.requestFocusFromTouch();

					} else {
						if (!editDeclarationListContentBean.getOrder().get(0)
								.getAGECOVERAGE().equals("无")) {
							if (ageCoverage.equals("选择年龄")) {
								Toast.makeText(EditDeclarationActivity.this,
										"请选择年龄", Toast.LENGTH_SHORT).show();

							} else {
								if (!editDeclarationListContentBean.getOrder()
										.get(0).getPAYLIMITTIME().equals("无")) {
									if (payLimitTime.equals("选择缴费年期")) {
										Toast.makeText(
												EditDeclarationActivity.this,
												"请选择缴费年期", Toast.LENGTH_SHORT)
												.show();

									} else {
										if (insuranceAmountTxt.equals("打款金额")) {
											Toast.makeText(
													EditDeclarationActivity.this,
													"请输入打款金额",
													Toast.LENGTH_SHORT).show();
										} else {

											IsRight(contractId,
													deputyInsuranceName,
													ageCoverage, payLimitTime,
													commissionRatio,
													customerName, amount,
													investdate, remark,
													userName, userPhone, idCard);
										}
									}
								}

							}
						} else {
							if (!editDeclarationListContentBean.getOrder()
									.get(0).getPAYLIMITTIME().equals("无")) {
								if (payLimitTime.equals("选择缴费年期")) {
									Toast.makeText(
											EditDeclarationActivity.this,
											"请选择缴费年期", Toast.LENGTH_SHORT)
											.show();

								} else {
									if (insuranceAmountTxt.equals("打款金额")) {
										Toast.makeText(
												EditDeclarationActivity.this,
												"请输入打款金额", Toast.LENGTH_SHORT)
												.show();
									} else {

										IsRight(contractId,
												deputyInsuranceName,
												ageCoverage, payLimitTime,
												commissionRatio, customerName,
												amount, investdate, remark,
												userName, userPhone, idCard);
									}
								}
							}
						}

					}
				} else {
					insuranceAmount = amount;
					deputyInsuranceAmount = null;
					commissionRatio = null;
					insuranceRebateProportion = null;
					if (TextUtils.isEmpty(amount)) {
						Toast.makeText(EditDeclarationActivity.this,
								"打款金额不能为空", Toast.LENGTH_SHORT).show();
						edt_amount.requestFocusFromTouch();
					} else {
						if (StringUtil.isDoubleForTwoNumber(amount)) {
							String minAmountSring = editDeclarationListContentBean
									.getMinPayAmount();
							String canOrderAmountString = editDeclarationListContentBean
									.getCanOrderAmount();
							double minAmount = Double
									.parseDouble(minAmountSring);
							double canOrderAmount = Double
									.parseDouble(canOrderAmountString);
							double inputAmount = Double.parseDouble(edt_amount
									.getText().toString());
							if (inputAmount < minAmount) {
								Toast.makeText(EditDeclarationActivity.this,
										"打款金额不得小于最小认购金额" + minAmount + "万元",
										Toast.LENGTH_SHORT).show();
								return;
							}
							if ((canOrderAmount - inputAmount) < minAmount) {
								if ((canOrderAmount - inputAmount) == 0) {
									IsRight(contractId, deputyInsuranceName,
											ageCoverage, payLimitTime,
											commissionRatio, customerName,
											amount, investdate, remark,
											userName, userPhone, idCard);
								} else if ((canOrderAmount - inputAmount) < 0) {
									Toast.makeText(
											EditDeclarationActivity.this,
											"打款金额不得大于剩余金额" + canOrderAmount
													+ "万元", Toast.LENGTH_SHORT)
											.show();

								} else {
									Toast.makeText(
											EditDeclarationActivity.this,
											"剩余金额不得小于最小认购金额" + minAmount + "万元",
											Toast.LENGTH_SHORT).show();
								}
								return;
							} else {
								if ((canOrderAmount - inputAmount) > minAmount
										|| (canOrderAmount - inputAmount) == minAmount) {
									IsRight(contractId, deputyInsuranceName,
											ageCoverage, payLimitTime,
											commissionRatio, customerName,
											amount, investdate, remark,
											userName, userPhone, idCard);
								} else {
									Toast.makeText(
											EditDeclarationActivity.this,
											"剩余金额不得小于最小认购金额" + minAmount + "万元",
											Toast.LENGTH_SHORT).show();
								}
							}

						} else {
							Toast.makeText(EditDeclarationActivity.this,
									"打款金额只能输入2位小数", Toast.LENGTH_SHORT).show();
							edt_amount.requestFocusFromTouch();
						}

					}
				}
			} else {
				Toast.makeText(EditDeclarationActivity.this, "请选择产品",
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.edit_declaration_edit_baodan_select_insurance:
			showDialog(multiChoiceItems, multiChoiceItemsDeputyInsuranceID);
			break;
		case R.id.edit_declaration_edit_select_age:
			showDialogSingleAge(singleChoiceItemsAge, "选择年龄");
			break;
		case R.id.edit_declaration_edit_select_date:
			showDialogSingleLimit(singleChoiceItemsLimit, "选择缴费年期");
			break;
		case R.id.edit_declaration_edit_investdate:
			selectBuyDate();
			break;
		case R.id.edit_declaration_txt_investmoney:// 打款金额
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
		case R.id.edit_declaration_img_IDpros:
			photoType = 1;
			setPhoto();
			break;
		case R.id.edit_declaration_img_IDcons:
			photoType = 2;
			setPhoto();
			break;
		case R.id.edit_declaration_img_bank:
			photoType = 3;
			setPhoto();
			break;
		case R.id.edit_declaration_img_investmoney:
			photoType = 4;
			setPhoto();
			break;
		case R.id.edit_declaration_img_sign:
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
						productId = editDeclarationListContentBean.getOrder()
								.get(0).getPRODUCTID();
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
						productId = editDeclarationListContentBean.getOrder()
								.get(0).getPRODUCTID();
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
						productId = editDeclarationListContentBean.getOrder()
								.get(0).getPRODUCTID();
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
		if (editDeclarationListContentBean.getDeputyName().size() == 0) {
			deputyInsuranceName = "(null)";
		} else {
			if (TextUtils.isEmpty(deputyInsuranceName)) {
				deputyInsuranceName = "(null)";
			}
		}
		if (editDeclarationListContentBean.getOrder().get(0).getAGECOVERAGE()
				.equals("无")) {
			ageCoverage = "(null)";
		}
		/*
		 * if (!(payLimitTimeOne.equals("趸缴") || payLimitTimeOne.equals("趸交")))
		 * { payLimitTimeOne = payLimitTimeOne.substring(0,
		 * payLimitTimeOne.lastIndexOf("年")); }
		 */
		HtmlRequest.getSearchCommissionratio(EditDeclarationActivity.this,
				productId, deputyInsuranceName, ageCoverage, payLimitTimeOne,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							commissionratioListContentBeans = (ResultDeclarationSearchCommissionratioListContentBean) params.result;
							if (commissionratioListContentBeans
									.getResultCommissionData().size() != 0) {
								if (commissionratioListContentBeans
										.getResultCommissionData().size() != 0) {
									searchCommissionratioDataBeans = new MouldList<ResultDeclarationSearchCommissionratioDataBean>();
									for (int i = 0; i < commissionratioListContentBeans
											.getResultCommissionData().size(); i++) {
										MouldList<ResultDeclarationSearchCommissionratioDataBean> listBean = commissionratioListContentBeans
												.getResultCommissionData().get(
														i);
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
												.get(k).getCOMMISSIONRATIO();
										commissionratioSB
												.append(commissionratio + ",");
									}
									commissionratioSB
											.deleteCharAt(commissionratioSB
													.length() - 1);
									if (searchCommissionratioDataBeans.size() != 1) {
										insuranceRebateProportion = commissionratioSB
												.toString()
												.substring(
														commissionratioSB
																.toString()
																.indexOf(",") + 1,
														commissionratioSB
																.toString()
																.length());
									} else {
										insuranceRebateProportion = "";
									}

								}
								viewAnimatorLayout.setVisibility(View.VISIBLE);
								viewAnimator.setVisibility(View.VISIBLE);
								viewAnimator.setDisplayedChild(1);
								data.clear();

								if (TextUtils.isEmpty(deputyInsuranceNameId)) {
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
										deputyInsuranceNameContrast = deputyInsuranceNameId;
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
								Toast.makeText(EditDeclarationActivity.this,
										"此年期选择错误，请重新选择！", Toast.LENGTH_LONG)
										.show();
								viewAnimatorLayout.setVisibility(View.GONE);
								viewAnimator.setVisibility(View.GONE);
							}
						} else {
							Toast.makeText(EditDeclarationActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});

	}

	private void IsRight(String contractId, String deputyInsuranceName,
			String ageCoverage, String payLimitTime, String commissionRatio,
			String customerName, String amount, String investdateValue,
			String remark, String userName, String userPhone, String idCard) {
		if (TextUtils.isEmpty(customerName)) {
			Toast.makeText(EditDeclarationActivity.this, "客户名不能为空",
					Toast.LENGTH_SHORT).show();
			edt_customerName.requestFocusFromTouch();
		} else {

			if (TextUtils.isEmpty(idCard)) {
				Toast.makeText(EditDeclarationActivity.this, "身份证号不能为空",
						Toast.LENGTH_SHORT).show();
				edt_idCard.requestFocusFromTouch();
			} else {
				if (!IdCardCheckUtils.isIdCard((idCard.toUpperCase()))) {
					Toast.makeText(EditDeclarationActivity.this, "请输入正确的身份证号",
							Toast.LENGTH_SHORT).show();
					edt_idCard.requestFocusFromTouch();
				} else {
					if (investdateValue.equals("打款日期")) {
						Toast.makeText(EditDeclarationActivity.this, "请选择打款日期",
								Toast.LENGTH_SHORT).show();

					} else {
						if (PreferenceUtil.getUserType().equals("corp")) {
							if (TextUtils.isEmpty(userName)) {
								Toast.makeText(EditDeclarationActivity.this,
										"理财师姓名不能为空", Toast.LENGTH_SHORT).show();
								edt_userName.requestFocusFromTouch();
							} else {
								if (TextUtils.isEmpty(userPhone)) {
									Toast.makeText(
											EditDeclarationActivity.this,
											"理财师电话不能为空", Toast.LENGTH_SHORT)
											.show();
									edt_userPhone.requestFocusFromTouch();
								} else {
									if (StringUtil.isMobileNO(edt_userPhone
											.getText().toString())) {

										requestSaveEditDeclarationData(id,
												APPOID, productId, contractId,
												productCategory, productName,
												customerName, customerPhone,
												insuranceAmount,
												investdateValue, sfzzmFileName,
												sfzfmFileName, cardFileName,
												dkptFileName, qzyFileName,
												remark, userName, userPhone,
												deputyInsuranceName,
												ageCoverage, payLimitTime,
												commissionRatio,
												deputyInsuranceAmount,
												insuranceRebateProportion,
												idCard.toUpperCase(),
												deputyInsuranceNameId,"");

									} else {
										Toast.makeText(
												EditDeclarationActivity.this,
												"请输入正确的手机号码",
												Toast.LENGTH_SHORT).show();
										edt_userPhone.requestFocusFromTouch();
									}
								}
							}
						} else {

							requestSaveEditDeclarationData(id, APPOID,
									productId, contractId, productCategory,
									productName, customerName, customerPhone,
									insuranceAmount, investdateValue,
									sfzzmFileName, sfzfmFileName, cardFileName,
									dkptFileName, qzyFileName, remark,
									userName, userPhone, deputyInsuranceName,
									ageCoverage, payLimitTime, commissionRatio,
									deputyInsuranceAmount,
									insuranceRebateProportion,
									idCard.toUpperCase(), deputyInsuranceNameId,"");

						}
					}
				}
			}
		}
	}

	private void requestSaveEditDeclarationData(final String id,
			String appoitmentId, String productId, String contractId,
			String productCategory, String productName, String customerName,
			String customerPhone, String insuranceAmount, String paymoneyTime,
			String sfzzmFileNameEdit, String sfzfmFileNameEdit,
			String cardFileNameEdit, String dkptFileNameEdit,
			String qzyFileNameEdit, String remark, String userName,
			String userPhone, String deputyInsuranceName, String ageCoverage,
			String payLimitTimeOne, String rebateProportionone,
			String deputyInsuranceAmount, String insuranceRebateProportion,
			String customerIDcard, String deputyInsuranceId,String costAmount) {
		if (editDeclarationListContentBean.getDeputyName().size() == 0) {
			deputyInsuranceName = "(null)";
		} else {
			if (deputyInsuranceName.equals("选择附加险")) {
				deputyInsuranceName = "(null)";
			}
		}
		if (editDeclarationListContentBean.getOrder().get(0).getAGECOVERAGE()
				.equals("无")) {
			ageCoverage = "(null)";
		}

		if (isIDprosEdit == false) {
			sfzzmFileNameEdit = "(null)";
		} else {
			sfzzmFileNameEdit = sfzzmFileName;
		}

		if (isIDconsEdit == false) {
			sfzfmFileNameEdit = "(null)";
		} else {
			sfzfmFileNameEdit = sfzfmFileName;
		}

		if (isBankEdit == false) {
			cardFileNameEdit = "(null)";
		} else {
			cardFileNameEdit = cardFileName;
		}

		if (isInvestmoneyEdit == false) {
			dkptFileNameEdit = "(null)";
		} else {
			dkptFileNameEdit = dkptFileName;
		}

		if (isBankEdit == false) {
			qzyFileNameEdit = "(null)";
		} else {
			qzyFileNameEdit = qzyFileName;
		}

		/*
		 * payLimitTimeOne = payLimitTimeOne.substring(0,
		 * payLimitTimeOne.lastIndexOf("年"));
		 */
		HtmlRequest.getSaveEditDeclaration(EditDeclarationActivity.this, id,
				appoitmentId, productId, contractId, productCategory,
				productName, customerName, customerPhone, insuranceAmount,
				paymoneyTime, sfzzmFileName, sfzfmFileName, cardFileName,
				dkptFileName, qzyFileName, remark, userName, userPhone,
				deputyInsuranceName, ageCoverage, payLimitTimeOne,
				rebateProportionone, deputyInsuranceAmount,
				insuranceRebateProportion, customerIDcard, deputyInsuranceId,costAmount,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							if (params.result != null) {
								ResultApplyDeclarationBean data = (ResultApplyDeclarationBean) params.result;
								if (data != null) {
									if (data.getCode().equals("0000")) {
										if(data.getData().getFlag().equals("true")){
											Toast.makeText(
													EditDeclarationActivity.this,
													"报单成功", Toast.LENGTH_LONG)
													.show();
											Intent intent = new Intent(
													EditDeclarationActivity.this,
													DeclarationActivity.class);
											startActivity(intent);
											stack.removeAllActivity();
											requestSMS(data.getData().getOrderId(),
													data.getData().getProductName());
										}else{
											Toast.makeText(
													EditDeclarationActivity.this,
													data.getData().getMessage(),
													Toast.LENGTH_LONG).show();
										}
									} else {
										Toast.makeText(
												EditDeclarationActivity.this,
												data.getMsg(),
												Toast.LENGTH_LONG).show();
									}
								}
							}
						} else {
							Toast.makeText(EditDeclarationActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	private void requestSMS(String orderId, String productName) {
		try {
			HtmlRequest.sentSMSForApplyDeclaration(
					EditDeclarationActivity.this,
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
		wheel.show(findViewById(R.id.edit_declaration_product_scrollView));
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
							isIDprosEdit = true;
						} else if (photoType == 2) {
							sfzfmFileName = contentBean.getTmpFileName();
							isIDconsEdit = true;
						} else if (photoType == 3) {
							cardFileName = contentBean.getTmpFileName();
							isBankEdit = true;
						} else if (photoType == 4) {
							dkptFileName = contentBean.getTmpFileName();
							isInvestmoneyEdit = true;
						} else if (photoType == 5) {
							qzyFileName = contentBean.getTmpFileName();
							isSignEdit = true;
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
	@Override
	protected void onResume() {
		super.onResume();
		imageLoader.resume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		imageLoader.pause();
	}
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		imageLoader.stop();
	}

}
