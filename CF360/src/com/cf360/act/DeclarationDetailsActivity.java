package com.cf360.act;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.adapter.CommissionAdapter;
import com.cf360.adapter.DeclarationUserPaymentAdapter;
import com.cf360.bean.ResultDeclarationDetailsListContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.HtmlRequest;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.ListViewForScrollView;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 报单详情
 * 
 */
public class DeclarationDetailsActivity extends BaseActivity implements
		OnClickListener {
	private TitleBar title;
	private Button btn_next;
	private String state;
	private String productName;
	private String id;// 报单编号
	private String type;// 产品类型
	private String productId;// 产品id

	private TextView txt_product_name;// 产品名称
	// private RelativeLayout product_layout;// 产品名称
	private TextView txt_status;// 申请状态
	private TextView txt_declaration_number;// 报单编号
	private TextView txt_contract_number;// 合同编号
	private TextView txt_customername;// 客户姓名
	private TextView txt_paymoneytime;// 打款日期
	private TextView txt_costamount;// 认购费

	private TextView txt_insurance;// 附加保险
	private TextView txt_payDate;// 缴费年期
	private TextView txt_payAge;// 缴费年龄

	private LinearLayout layout_insurance;// 附加保险
	private LinearLayout layout_payDate;// 缴费年期
	private LinearLayout layout_payAge;// 缴费年龄

	private TextView txt_commissionway;// 返佣方式
	private TextView txt_lcs;// 理财师
	private TextView txt_lcsPhone;// 理财师电话
	private TextView txt_createtime;// 报单时间
	private TextView txt_remark;// 备注
	private LinearLayout layout_lcs;
	private LinearLayout layout_lcs_phone;
	// private LinearLayout layout_return_amount;
	private LinearLayout layout_contractNumber;
	private LinearLayout layout_costamount;

	// private ListViewForScrollView listView;
	// private DeclarationUserPaymentAdapter adapter;
	private ScrollView scrollView;

	private ListViewForScrollView listViewCommission;
	private ListViewForScrollView listViewAmount;
	private CommissionAdapter adapterCommission;
	private ResultDeclarationDetailsListContentBean data;

	private ImageView img_IDpros;
	private ImageView img_IDcons;
	private ImageView img_bank;
	private ImageView img_investmoney;
	private ImageView img_sign;

	private RelativeLayout layoutHint;
	private ImageView imgDelete;
	private TextView txtInfo;
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_declaration_details);
		initView();
		setData();
		initData();
		initTopTitle();
	}

	private void setData() {
		state = getIntent().getStringExtra("state");
		id = getIntent().getStringExtra("id");
		productName = getIntent().getStringExtra("productName");
		if (PreferenceUtil.getUserType().equals("corp")) {
			layout_lcs.setVisibility(View.VISIBLE);
			layout_lcs_phone.setVisibility(View.VISIBLE);
		}

	}

	private void initTopTitle() {
		title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setLogo(R.drawable.img_logo, false)
				.setIndicator(R.drawable.back)
				.setCenterText(
						getResources().getString(
								R.string.title_declaration_details))
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
		scrollView = (ScrollView) findViewById(R.id.declaration_details_scrollview);
		scrollView.smoothScrollTo(0, 0);
		txt_product_name = (TextView) findViewById(R.id.declaration_details_product_name);
		// product_layout = (RelativeLayout)
		// findViewById(R.id.declaration_details_product_layout);
		txt_status = (TextView) findViewById(R.id.declaration_details_status);
		txt_declaration_number = (TextView) findViewById(R.id.declaration_details_orderNumber);
		txt_contract_number = (TextView) findViewById(R.id.declaration_details_contractNumber);
		txt_customername = (TextView) findViewById(R.id.declaration_details_customername);
		txt_paymoneytime = (TextView) findViewById(R.id.declaration_details_paymoneytime);
		txt_costamount = (TextView) findViewById(R.id.declaration_details_costamount);

		txt_insurance = (TextView) findViewById(R.id.declaration_details_insuranceName);
		txt_payDate = (TextView) findViewById(R.id.declaration_details_payDate);
		txt_payAge = (TextView) findViewById(R.id.declaration_details_payAge);

		layout_insurance = (LinearLayout) findViewById(R.id.declaration_details_insuranceName_layout);
		layout_payDate = (LinearLayout) findViewById(R.id.declaration_details_payDate_layout);
		layout_payAge = (LinearLayout) findViewById(R.id.declaration_details_payAge_layout);

		txt_commissionway = (TextView) findViewById(R.id.declaration_details_commissionway);
		txt_lcs = (TextView) findViewById(R.id.declaration_details_lcs);
		txt_lcsPhone = (TextView) findViewById(R.id.declaration_details_lcs_phone);
		txt_createtime = (TextView) findViewById(R.id.declaration_details_createtime);
		layout_lcs = (LinearLayout) findViewById(R.id.declaration_details_lcs_layout);
		layout_lcs_phone = (LinearLayout) findViewById(R.id.declaration_details_lcs_phone_layout);
		txt_remark = (TextView) findViewById(R.id.declaration_details_remark);
		// layout_return_amount = (LinearLayout)
		// findViewById(R.id.declaration_details_return_amount_layout);
		layout_contractNumber = (LinearLayout) findViewById(R.id.declaration_details_contractNumber_layout);
		layout_costamount = (LinearLayout) findViewById(R.id.declaration_details_costamount_layout);

		// listView = (ListViewForScrollView)
		// findViewById(R.id.declaration_details_listview);
		listViewCommission = (ListViewForScrollView) findViewById(R.id.declaration_details_listview_commission);
		listViewAmount = (ListViewForScrollView) findViewById(R.id.declaration_details_listview_amount);

		img_IDpros = (ImageView) findViewById(R.id.declaration_details_img_IDpros);
		img_IDcons = (ImageView) findViewById(R.id.declaration_details_img_IDcons);
		img_bank = (ImageView) findViewById(R.id.declaration_details_img_bank);
		img_investmoney = (ImageView) findViewById(R.id.declaration_details_img_investmoney);
		img_sign = (ImageView) findViewById(R.id.declaration_details_img_sign);

		btn_next = (Button) findViewById(R.id.declaration_details_btn_next);

		layoutHint = (RelativeLayout) findViewById(R.id.declaration_details_layout_hint);
		imgDelete = (ImageView) findViewById(R.id.pop_delete);
		
		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
				initData();
//				myEquityBaseAdapter.notifyDataSetChanged();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
		
	}

	private void initData() {
		btn_next.setOnClickListener(this);
		imgDelete.setOnClickListener(this);
		// layoutHint.setOnClickListener(this);
		// product_layout.setOnClickListener(this);
		state = getIntent().getStringExtra("state");
//		txt_product_name.setText(productName);
		// product_layout.setClickable(false);
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
		HtmlRequest.getDeclarationDetails(DeclarationDetailsActivity.this, id,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							data = (ResultDeclarationDetailsListContentBean) params.result;
							if (data != null) {
								setDeclarationDetailsData(data);
							}
						} else {
							netFail = true;
							Toast.makeText(DeclarationDetailsActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	protected void setDeclarationDetailsData(
			ResultDeclarationDetailsListContentBean bean) {
		state=bean.getOrder().get(0).getSTATUS();
		productId = bean.getOrder().get(0).getPRODUCTID();
		type = bean.getOrder().get(0).getPRODUCTCATEGORY();
		// product_layout.setClickable(true);
		txt_product_name.setText(bean.getOrder().get(0).getPRODUCTNAME());
		String declaration_number = bean.getOrder().get(0).getID();
		txt_declaration_number.setText(declaration_number.substring(
				declaration_number.length() - 8, declaration_number.length()));
		txt_customername.setText(bean.getOrder().get(0).getCUSTOMERNAME());
		txt_paymoneytime.setText(bean.getOrder().get(0).getPAYMONEYTIME());
		txt_lcs.setText(bean.getOrder().get(0).getUSERNAME());
		txt_lcsPhone.setText(bean.getOrder().get(0).getUSERPHONE());
		txt_createtime.setText(bean.getOrder().get(0).getCREATETIME());
		adapterCommission = new CommissionAdapter(
				DeclarationDetailsActivity.this, data.getOrder().get(0)
						.getAMOUNT2());
		listViewAmount.setAdapter(adapterCommission);
		if (bean.getOrder().get(0).getREMARK().equals("0")) {
			txt_remark.setText("");
		} else {
			txt_remark.setText(bean.getOrder().get(0).getREMARK());
		}
		if (bean.getOrder().get(0).getPRODUCTCATEGORY().equals("BX")) {
			txt_contract_number.setText(bean.getOrder().get(0).getCONTRACTID());
			if ((bean.getOrder().get(0).getDEPUTYINSURANCENAME()
					.equals("(null)"))) {
				txt_insurance.setText("无");
			} else {
				txt_insurance.setText(bean.getOrder().get(0)
						.getDEPUTYINSURANCENAME());
			}
			if (bean.getOrder().get(0).getAGECOVERAGE() == null
					|| bean.getOrder().get(0).getAGECOVERAGE().equals("(null)")) {
				txt_payAge.setText("无");
			} else {
				txt_payAge.setText(bean.getOrder().get(0).getAGECOVERAGE());
			}
			if ((bean.getOrder().get(0).getPAYLIMITTIME().equals("(null)"))) {
				txt_payDate.setText("无");
			} else {
				/*
				 * if (bean.getOrder().get(0).getPAYLIMITTIME().equals("趸缴") ||
				 * bean.getOrder().get(0).getPAYLIMITTIME() .equals("趸交")) {
				 */
				txt_payDate.setText(bean.getOrder().get(0).getPAYLIMITTIME());
				/*
				 * } else { txt_payDate.setText(bean.getOrder().get(0)
				 * .getPAYLIMITTIME() + "年"); }
				 */
			}

			if (bean.getOrder().get(0).getSTATUS().equals("pendingaudit")) {
				txt_status.setText("待审核");
			} else if (bean.getOrder().get(0).getSTATUS().equals("orderreject")) {
				txt_status.setText("报单驳回");
				btn_next.setText("修改报单");
				btn_next.setVisibility(View.VISIBLE);
				scrollView.setPadding(0, 0, 0, 90);
			} else if (bean.getOrder().get(0).getSTATUS().equals("pay")) {
				txt_status.setText("打款到账");
			} else if (bean.getOrder().get(0).getSTATUS().equals("repaymoney")) {
				txt_status.setText("返佣中");
				/*
				 * layout_return_amount.setVisibility(View.VISIBLE); adapter =
				 * new DeclarationUserPaymentAdapter(this,
				 * bean.getUserPayment(), bean.getOrder().get(0)
				 * .getCOMMISSIONWAY()); listView.setAdapter(adapter);
				 */

			} else if (bean.getOrder().get(0).getSTATUS()
					.equals("financialreject")) {
				txt_status.setText("财务驳回");
			} else if (bean.getOrder().get(0).getSTATUS().equals("repayed")) {
				txt_status.setText("完成返佣");
				if (bean.getUserPayment() != null) {
					/*
					 * layout_return_amount.setVisibility(View.VISIBLE); adapter
					 * = new DeclarationUserPaymentAdapter(this,
					 * bean.getUserPayment(), bean.getOrder().get(0)
					 * .getCOMMISSIONWAY()); listView.setAdapter(adapter);
					 */
				}
			}
			layoutHint.setVisibility(View.VISIBLE);
			layout_insurance.setVisibility(View.VISIBLE);
			layout_payDate.setVisibility(View.VISIBLE);
			layout_payAge.setVisibility(View.VISIBLE);
			// layout_return_amount.setVisibility(View.VISIBLE);
		} else {
			if (bean.getOrder().get(0).getSTATUS().equals("pendingaudit")) {
				txt_status.setText("待审核");
			} else if (bean.getOrder().get(0).getSTATUS().equals("orderreject")) {
				txt_status.setText("报单驳回");
				btn_next.setText("修改报单");
				btn_next.setVisibility(View.VISIBLE);
				scrollView.setPadding(0, 0, 0, 90);
			} else if (bean.getOrder().get(0).getSTATUS().equals("pay")) {
				txt_status.setText("打款到账");
				btn_next.setText("补充凭证");
				btn_next.setVisibility(View.VISIBLE);
				scrollView.setPadding(0, 0, 0, 90);
			} else if (bean.getOrder().get(0).getSTATUS()
					.equals("buchongorder")) {
				txt_status.setText("补充待审核");
			} else if (bean.getOrder().get(0).getSTATUS()
					.equals("materialreject")) {
				txt_status.setText("补充驳回");
				btn_next.setText("修改补充");
				btn_next.setVisibility(View.VISIBLE);
				scrollView.setPadding(0, 0, 0, 90);
			} else if (bean.getOrder().get(0).getSTATUS().equals("repaymoney")) {
				txt_status.setText("返佣中");
				/*
				 * layout_return_amount.setVisibility(View.VISIBLE); adapter =
				 * new DeclarationUserPaymentAdapter(this,
				 * bean.getUserPayment(), bean.getOrder().get(0)
				 * .getCOMMISSIONWAY()); listView.setAdapter(adapter);
				 */
			} else if (bean.getOrder().get(0).getSTATUS()
					.equals("financialreject")) {
				txt_status.setText("财务驳回");
			} else if (bean.getOrder().get(0).getSTATUS().equals("repayed")) {
				txt_status.setText("完成返佣");
				/*
				 * if (bean.getUserPayment() != null) {
				 * layout_return_amount.setVisibility(View.VISIBLE); adapter =
				 * new DeclarationUserPaymentAdapter(this,
				 * bean.getUserPayment(), bean.getOrder().get(0)
				 * .getCOMMISSIONWAY()); listView.setAdapter(adapter); }
				 */
			}
			//认购费
			txt_costamount.setText(data.getOrder().get(0)
					.getCOSTAMOUNT());
			layout_costamount.setVisibility(View.VISIBLE);
		}

		adapterCommission = new CommissionAdapter(
				DeclarationDetailsActivity.this, data.getOrder().get(0)
						.getREBATEPROPORTION2());
		listViewCommission.setAdapter(adapterCommission);
		if (!TextUtils.isEmpty(bean.getOrder().get(0).getCOMMISSIONWAY())) {
			if (bean.getOrder().get(0).getCOMMISSIONWAY().equals("0")) {

				txt_commissionway.setText("一次性返佣");

			} else if (bean.getOrder().get(0).getCOMMISSIONWAY().equals("1")) {
				txt_commissionway.setText("按年返佣");

			}
		}

		String url_IDpros = bean.getOrder().get(0).getIDCARDPOSITIVE();
		if (!TextUtils.isEmpty(url_IDpros)) {
		//	new ImageViewServiceIDpros().execute(url_IDpros);
			imageLoader.displayImage(url_IDpros,img_IDpros,options);
			
		} else {
			img_IDpros.setImageDrawable(getResources().getDrawable(
					R.drawable.add_picture));
		}

		String url_IDcons = bean.getOrder().get(0).getIDCARDNEGATIVE();
		if (!TextUtils.isEmpty(url_IDcons)) {
		//	new ImageViewServiceIDcons().execute(url_IDcons);
			imageLoader.displayImage(url_IDcons,img_IDcons,options);
		} else {
			img_IDcons.setImageDrawable(getResources().getDrawable(
					R.drawable.add_picture));
		}

		String url_Bank = bean.getOrder().get(0).getBANKCARD();
		if (!TextUtils.isEmpty(url_Bank)) {
		//	new ImageViewServiceBank().execute(url_Bank);
			imageLoader.displayImage(url_Bank,img_bank,options);
		} else {
			img_bank.setImageDrawable(getResources().getDrawable(
					R.drawable.add_picture));
		}

		String url_Investmoney = bean.getOrder().get(0).getPAYMENTRECEIPT();
		if (!TextUtils.isEmpty(url_Investmoney)) {
		//	new ImageViewServiceInvestmoney().execute(url_Investmoney);
			imageLoader.displayImage(url_Investmoney,img_investmoney,options);
		} else {
			img_investmoney.setImageDrawable(getResources().getDrawable(
					R.drawable.add_picture));
		}

		String url_Sign = bean.getOrder().get(0).getSIGNATUREPAGE();
		if (!TextUtils.isEmpty(url_Sign)) {
		//	new ImageViewServiceSign().execute(url_Sign);
			imageLoader.displayImage(url_Sign,img_sign,options);
		} else {
			img_sign.setImageDrawable(getResources().getDrawable(
					R.drawable.add_picture));
		}

	}

	class ImageViewServiceIDpros extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
		//	Bitmap data = decodeSampledBitmapFromResource(params[0], 60,100, DeclarationDetailsActivity.this);
			Bitmap data = getImageBitmap(params[0]);
			return data;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);

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
		//	Bitmap data = decodeSampledBitmapFromResource(params[0], 60,100, DeclarationDetailsActivity.this);
			Bitmap data = getImageBitmap(params[0]);
			return data;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);

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
		//	Bitmap data = decodeSampledBitmapFromResource(params[0], 60,100, DeclarationDetailsActivity.this);
			Bitmap data = getImageBitmap(params[0]);
			return data;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);

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
		//	Bitmap data = decodeSampledBitmapFromResource(params[0], 60,100, DeclarationDetailsActivity.this);
			Bitmap data = getImageBitmap(params[0]);
			return data;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);

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
		//	Bitmap data = decodeSampledBitmapFromResource(params[0], 60,100, DeclarationDetailsActivity.this);
			Bitmap data = getImageBitmap(params[0]);
			return data;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);

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
            final int heightRatio = Math.round((float) height / (float) reqHeight);    
            final int widthRatio = Math.round((float) width / (float) reqWidth);   
            //计算缩放比例  
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;    
        }    
        return inSampleSize;    
    }   
    public static Bitmap decodeSampledBitmapFromResource(String urlPath,   
            int reqWidth, int reqHeight,Context context) {    
    	URL imgUrl = null;
    	Bitmap bitmap=null;
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
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        bitmap = BitmapFactory.decodeStream(is);
        onlyBoundsOptions.inSampleSize = calculateInSampleSize(onlyBoundsOptions, reqWidth, reqHeight);    
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
		/*
		 * case R.id.declaration_details_product_layout: if(type.equals("信托")){
		 * Intent intent=new
		 * Intent(DeclarationDetailsActivity.this,TrustDetailsActivity.class);
		 * intent.putExtra("Mtrustid", productId); startActivity(intent); }else
		 * if(type.equals("资管")){ Intent intent=new
		 * Intent(DeclarationDetailsActivity.this,ZiGuanItemActivity.class);
		 * intent.putExtra("Mtrustid", productId); startActivity(intent); }else
		 * if(type.equals("ygsm")){ Intent intent=new
		 * Intent(DeclarationDetailsActivity.this,SunshineActivity.class);
		 * intent.putExtra("Sunshine", productId); startActivity(intent); }else
		 * if(type.equals("smgq")){ Intent intent=new
		 * Intent(DeclarationDetailsActivity.this,EquityItmeActivity.class);
		 * intent.putExtra("EquityId", productId); startActivity(intent); }else{
		 * Intent intent=new
		 * Intent(DeclarationDetailsActivity.this,InsuranceItmeActivity.class);
		 * intent.putExtra("id", productId); startActivity(intent); } break;
		 */
		case R.id.declaration_details_btn_next:
			if (type.equals("BX")) {
				if (state.equals("orderreject")) {// 修改报单
					Intent intent = new Intent(this,
							EditDeclarationActivity.class);
					intent.putExtra("id", id);
					startActivity(intent);
				}
			} else {
				if (state.equals("orderreject")) {// 报单驳回---------修改报单
					Intent intent = new Intent(this,
							EditDeclarationNotInsuranceActivity.class);
					intent.putExtra("type", "orderreject");
					intent.putExtra("id", id);
					startActivity(intent);
				} else if (state.equals("pay")) {// 打款到账---------补充凭证
					Intent intent = new Intent(this,
							EditDeclarationNotInsuranceActivity.class);
					intent.putExtra("type", "pay");
					intent.putExtra("id", id);
					startActivity(intent);
				} else if (state.equals("materialreject")) {// 信息驳回---------修改补充
					Intent intent = new Intent(this,
							EditDeclarationNotInsuranceActivity.class);
					intent.putExtra("type", "materialreject");
					intent.putExtra("id", id);
					startActivity(intent);
				}
			}
			break;
		case R.id.pop_delete:
			layoutHint.setVisibility(View.INVISIBLE);
			break;
		/*
		 * case R.id.declaration_details_layout_hint: Intent intent=new
		 * Intent(DeclarationDetailsActivity.this,WebActivity.class);
		 * intent.putExtra("type", WebActivity.WEBTYPE_HINT);
		 * intent.putExtra("url", ""); startActivity(intent); break;
		 */
		default:
			break;
		}
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