package com.cf360.act;

import java.util.ArrayList;

import com.cf360.R;
import com.cf360.adapter.CommissionAdapter;
import com.cf360.bean.ResultCancelOrderBean;
import com.cf360.bean.ResultContractTrackListContentBean;
import com.cf360.bean.ResultOrderDetailContentBean;
import com.cf360.bean.ResultOrderDetailListDataBean;
import com.cf360.bean.ResultSignContractBean;
import com.cf360.bean.ResultSignContractContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.ListViewForScrollView;
import com.cf360.view.SignContractDialog;
import com.cf360.view.TitleBar;
import com.cf360.view.SignContractDialog.OnSignContractChanged;
import com.cf360.view.TitleBar.OnActionListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 预约详情
 * 
 */
public class OrderDetailsActivity extends BaseActivity implements
		OnClickListener {
	private String id;
	private String productName;
	private String status;
	private String type;
	private String productId;

	private TextView txt_product_name;// 产品名称
	// private RelativeLayout product_layout;// 产品名称

	private TextView txt_amount;// 投资金额

	private TextView txt_appointmentId;// 预约编号
	// private TextView txt_customer_name;// 客户姓名
	// private TextView txt_customer_phone;// 客户电话

	private TextView txt_insurance;// 附加保险
	private TextView txt_payAge;// 缴费年龄
	private TextView txt_payDate;// 缴费年期

	private LinearLayout layout_insurance;// 附加保险
	private LinearLayout layout_payAge;// 缴费年龄
	private LinearLayout layout_payDate;// 缴费年期

	private TextView txt_status;// 预约状态

	private TextView txt_lcs;// 理财师
	private TextView txt_lcsPhone;// 理财师电话
	private LinearLayout layout_lcs;
	private LinearLayout layout_lcs_phone;
	private TextView txt_appointmentTime;// 预约时间
	private TextView txt_remark;// 备注说明

	private Button btnNext;
	private ResultOrderDetailListDataBean data;
	private ActivityStack stack;
	private ListViewForScrollView listViewCommission;
	private CommissionAdapter adapterCommission;
	private ScrollView scrollView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_order_details);
		initView();
		setData();
		initData();
		initTopTitle();
	}

	private void setData() {
		id = getIntent().getStringExtra("id");
		productName = getIntent().getStringExtra("productName");
		status = getIntent().getStringExtra("status");
		type = getIntent().getStringExtra("type");
		productId = getIntent().getStringExtra("productId");
		if (type.equals("保险")) {
			layout_insurance.setVisibility(View.VISIBLE);
			layout_payDate.setVisibility(View.VISIBLE);
			layout_payAge.setVisibility(View.VISIBLE);
		}
		/*
		 * if (PreferenceUtil.getUserType().equals("corp")) {
		 * layout_lcs.setVisibility(View.VISIBLE);
		 * layout_lcs_phone.setVisibility(View.VISIBLE); }
		 */
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setLogo(R.drawable.img_logo, false)
				.setIndicator(R.drawable.back)
				.setCenterText(
						getResources().getString(R.string.title_order_details))
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
		scrollView = (ScrollView) findViewById(R.id.order_details_scrollView);
		scrollView.smoothScrollTo(0, 0);
		txt_product_name = (TextView) findViewById(R.id.order_details_product_name);
		// product_layout = (RelativeLayout)
		// findViewById(R.id.order_details_product_layout);

		txt_amount = (TextView) findViewById(R.id.order_details_money);

		txt_appointmentId = (TextView) findViewById(R.id.order_details_id);
		// txt_customer_name = (TextView)
		// findViewById(R.id.order_details_cutomerName);
		// txt_customer_phone = (TextView)
		// findViewById(R.id.order_details_cutomerPhone);

		txt_insurance = (TextView) findViewById(R.id.order_details_insurance);
		txt_payDate = (TextView) findViewById(R.id.order_details_paydate);
		txt_payAge = (TextView) findViewById(R.id.order_details_payage);

		layout_insurance = (LinearLayout) findViewById(R.id.order_details_insurance_layout);
		layout_payAge = (LinearLayout) findViewById(R.id.order_details_payage_layout);
		layout_payDate = (LinearLayout) findViewById(R.id.order_details_paydate_layout);

		txt_status = (TextView) findViewById(R.id.order_details_status);

		listViewCommission = (ListViewForScrollView) findViewById(R.id.order_details_listview_commission);

		txt_lcs = (TextView) findViewById(R.id.order_details_lcsName);
		txt_lcsPhone = (TextView) findViewById(R.id.order_details_lcsPhone);
		layout_lcs = (LinearLayout) findViewById(R.id.order_details_lcsName_layout);
		layout_lcs_phone = (LinearLayout) findViewById(R.id.order_details_lcsPhone_layout);
		txt_appointmentTime = (TextView) findViewById(R.id.order_details_time);

		txt_remark = (TextView) findViewById(R.id.order_details_remark);
		btnNext = (Button) findViewById(R.id.btn_next);
		
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
		if (status.equals("init")) {// 取消预约
			btnNext.setText("取消预约");
			scrollView.setPadding(0, 0, 0, 90);
			btnNext.setVisibility(View.VISIBLE);
		} else if (status.equals("audit")) {// 前往报单
			btnNext.setText("前往报单");
			scrollView.setPadding(0, 0, 0, 90);
			btnNext.setVisibility(View.VISIBLE);
		}
		/*
		 * else if (status.equals("order")) {// 查看报单 btnNext.setText("查看报单");
		 * btnNext.setVisibility(View.VISIBLE); }
		 */
		btnNext.setOnClickListener(this);
		// product_layout.setOnClickListener(this);
		// txt_product_name.setText(productName);
		// product_layout.setClickable(false);
		requestData(id);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/*
		 * case R.id.order_details_product_layout:// 跳转到产品详情
		 * if(type.equals("信托")){ Intent intent=new
		 * Intent(OrderDetailsActivity.this,TrustDetailsActivity.class);
		 * intent.putExtra("Mtrustid", productId); startActivity(intent); }else
		 * if(type.equals("资管")){ Intent intent=new
		 * Intent(OrderDetailsActivity.this,ZiGuanItemActivity.class);
		 * intent.putExtra("Mtrustid", productId); startActivity(intent); }else
		 * if(type.equals("阳光私募")){ Intent intent=new
		 * Intent(OrderDetailsActivity.this,SunshineActivity.class);
		 * intent.putExtra("Sunshine", productId); startActivity(intent); }else
		 * if(type.equals("私募股权")){ Intent intent=new
		 * Intent(OrderDetailsActivity.this,EquityItmeActivity.class);
		 * intent.putExtra("EquityId", productId); startActivity(intent); }else{
		 * Intent intent=new
		 * Intent(OrderDetailsActivity.this,InsuranceItmeActivity.class);
		 * intent.putExtra("id", productId); startActivity(intent); }
		 * 
		 * break;
		 */
		case R.id.btn_next:
			if (status.equals("init")) {// 取消预约
				SignContractDialog dialog = new SignContractDialog(this,
						new OnSignContractChanged() {

							@Override
							public void onConfim() {
								requestCancelOrderData(id);
							}

							@Override
							public void onCancel() {

							}
						}, "是否取消预约");
				dialog.show();

			} else if (status.equals("audit")) {// 前往报单
				String category = "";
				if (type.equals("保险")) {

					if ("0".equals(data.getAppoitment().get(0)
							.getUPPERANDLOWERFRAME())) {

						Intent intent = new Intent(OrderDetailsActivity.this,
								ApplyDeclarationForOrderActivity.class);
						intent.putExtra("APPOID", data.getAppoitment().get(0)
								.getId());
						intent.putExtra("PRODUCTID", data.getAppoitment()
								.get(0).getPRODUCTID());
						category = "bx";
						intent.putExtra("type", category);
						startActivity(intent);

					} else {
						Toast.makeText(OrderDetailsActivity.this,
								"该产品已下架，无法报单", Toast.LENGTH_LONG).show();
					}

				} else {

					if ("0".equals(data.getAppoitment().get(0)
							.getUPPERANDLOWERFRAME())) {
						if ("success".equals(data.getAppoitment().get(0)
								.getPRODUCTSTATUS())) {

							Intent intent = new Intent(
									OrderDetailsActivity.this,
									ApplyDeclarationNotInsuranceForOrderActivity.class);
							intent.putExtra("APPOID",
									data.getAppoitment().get(0).getId());
							intent.putExtra("PRODUCTID", data.getAppoitment()
									.get(0).getPRODUCTID());
							if (type.equals("信托")) {
								category = "xtzg";
							} else if (type.equals("资管")) {
								category = "xtzg";
							} else if (type.equals("阳光私募")) {
								category = "ygsm";
							} else if (type.equals("私募股权")) {
								category = "ygsm";
							}
							intent.putExtra("type", category);
							startActivity(intent);
						} else {
							Toast.makeText(OrderDetailsActivity.this,
									"该产品非募集期状态，无法报单", Toast.LENGTH_LONG).show();
						}
					} else {
						Toast.makeText(OrderDetailsActivity.this,
								"该产品已下架，无法报单", Toast.LENGTH_LONG).show();
					}

				}

			}
			/*
			 * else if (status.equals("order")) {// 查看报单
			 * 
			 * }
			 */
			break;
		default:
			break;
		}
	}

	private void requestCancelOrderData(final String Id) {
		HtmlRequest.getCancelOrder(OrderDetailsActivity.this, Id,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							ResultCancelOrderBean data = (ResultCancelOrderBean) params.result;
							if (data != null) {
								if (data.getCode().equals("0000")) {
									Toast.makeText(OrderDetailsActivity.this,
											"已取消预约", Toast.LENGTH_LONG).show();
									txt_status.setText("已取消");
									btnNext.setVisibility(View.GONE);
									Intent intent = new Intent(
											OrderDetailsActivity.this,
											OrderActivity.class);
									startActivity(intent);
									stack.removeAllActivity();
								}
							}
						} else {
							Toast.makeText(OrderDetailsActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	private void requestData(final String id) {
		HtmlRequest.getOrdertDetails(OrderDetailsActivity.this, id,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							data = (ResultOrderDetailListDataBean) params.result;
							if (data != null) {
								setOrderDetailData(data);
							}
						} else {
							netFail = true;
							Toast.makeText(OrderDetailsActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	protected void setOrderDetailData(ResultOrderDetailListDataBean data) {
		txt_product_name.setText(data.getAppoitment().get(0).getPRODUCTNAME());
		if (data.getAppoitment().get(0).getSTATUS().equals("init")) {
			txt_status.setText("待确认");
		} else if (data.getAppoitment().get(0).getSTATUS().equals("audit")) {
			txt_status.setText("已确认");
		} else if (data.getAppoitment().get(0).getSTATUS().equals("rejected")) {
			txt_status.setText("已驳回");
		} else if (data.getAppoitment().get(0).getSTATUS().equals("order")) {
			txt_status.setText("已报单");
		} else if (data.getAppoitment().get(0).getSTATUS().equals("cancel")) {
			txt_status.setText("已取消");
		}

		txt_amount.setText(data.getAppoitment().get(0).getAMOUNT()
				+ data.getAppoitment().get(0).getCURRENCY());
		String appointmentId = data.getAppoitment().get(0).getId();
		txt_appointmentId.setText(appointmentId.substring(
				appointmentId.length() - 8, appointmentId.length()));
		// txt_customer_name.setText(data.getAppoitment().get(0).getCUSTOMERNAME());
		// txt_customer_phone.setText(data.getAppoitment().get(0).getCUSTOMERPHONE());

		txt_lcs.setText(data.getAppoitment().get(0).getUSERNAME());
		txt_lcsPhone.setText(data.getAppoitment().get(0).getUSERPHONE());

		txt_appointmentTime.setText(data.getAppoitment().get(0)
				.getAPPOITMENTTIME());
		txt_remark.setText(data.getAppoitment().get(0).getREMARK());
		if (data.getAppoitment().get(0).getPRODUCTCATEGORY().equals("保险")) {
			layout_insurance.setVisibility(View.VISIBLE);
			layout_payDate.setVisibility(View.VISIBLE);
			layout_payAge.setVisibility(View.VISIBLE);
			txt_insurance.setText(data.getAppoitment().get(0)
					.getDEPUTYINSURANCENAME());
			if (data.getAppoitment().get(0).getPAYLIMITTIME().equals("趸缴")
					|| data.getAppoitment().get(0).getPAYLIMITTIME()
							.equals("趸交")) {
				txt_payDate.setText(data.getAppoitment().get(0)
						.getPAYLIMITTIME());
			} else {
				txt_payDate.setText(data.getAppoitment().get(0)
						.getPAYLIMITTIME());
			}
			txt_payAge.setText(data.getAppoitment().get(0).getAGECOVERAGE());

			adapterCommission = new CommissionAdapter(
					OrderDetailsActivity.this, data.getDeputyList());
			listViewCommission.setAdapter(adapterCommission);
		} else {
			String commission = data.getAppoitment().get(0)
					.getREBATEPROPORTION()
					+ "%";
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(commission);
			adapterCommission = new CommissionAdapter(
					OrderDetailsActivity.this, arrayList);
			listViewCommission.setAdapter(adapterCommission);
		}

	}
}
