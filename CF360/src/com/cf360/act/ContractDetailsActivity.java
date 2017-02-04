package com.cf360.act;

import org.w3c.dom.Text;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.adapter.ContractTrackAdapter;
import com.cf360.bean.ResultContractTrackAllListBean;
import com.cf360.bean.ResultContractTrackListContentBean;
import com.cf360.bean.ResultContractTrackListItemBean;
import com.cf360.bean.ResultSignContractBean;
import com.cf360.bean.ResultSignContractContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.ListViewForScrollView;
import com.cf360.view.SignContractDialog;
import com.cf360.view.SignContractDialog.OnSignContractChanged;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

/**
 * 合同详情
 * 
 */
public class ContractDetailsActivity extends BaseActivity implements
		OnClickListener {
	private Button btn_next;
	private String status;
	private String contractId;
	private String type;
	private String productName;
	private String productId;

	private TextView txt_product_name;// 产品名称
	// private RelativeLayout product_layout;// 产品名称
	private TextView txt_status;// 申请状态

	private TextView txt_insurance;// 附加保险
	private TextView txt_payDate;// 缴费年期
	private TextView txt_payAge;// 缴费年龄

	private LinearLayout layout_insurance;// 附加保险
	private LinearLayout layout_payDate;// 缴费年期
	private LinearLayout layout_payAge;// 缴费年龄

	private TextView txt_userName;// 客户姓名
	// private TextView txt_amount;// 投资金额
	private TextView txt_acceptor;// 收件人
	private TextView txt_acceptorPhone;// 手机号码
	private TextView txt_lcs;// 理财师
	private TextView txt_lcsPhone;// 理财师电话
	private TextView txt_address;// 收货地址
	private LinearLayout layout_lcs;
	private LinearLayout layout_lcs_phone;

	// 寄快递
	private RelativeLayout sendLayout;
	private TextView txt_express_name;
	private TextView txt_express_code;
	private TextView txt_express_url;

	// 收快递
	private RelativeLayout receiveLayout;
	private TextView txt_express_name_back;
	private TextView txt_express_code_back;
	private TextView txt_express_url_back;

	private MouldList<ResultContractTrackListItemBean> contractListData;
	private ListViewForScrollView listView;
	private ContractTrackAdapter adapter;
	private ScrollView scrollView;
	private TextView txtTitle;
	private ActivityStack stack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_contract_details);
		initView();
		setData();
		initData();
		initTopTitle();
	}

	private void setData() {
		status = getIntent().getStringExtra("state");
		contractId = getIntent().getStringExtra("contractId");
		type = getIntent().getStringExtra("type");
		if (type.equals("保险")) {
			layout_insurance.setVisibility(View.VISIBLE);
			layout_payDate.setVisibility(View.VISIBLE);
			layout_payAge.setVisibility(View.VISIBLE);
		}
		productName = getIntent().getStringExtra("productName");
		if (PreferenceUtil.getUserType().equals("corp")) {
			layout_lcs.setVisibility(View.VISIBLE);
			layout_lcs_phone.setVisibility(View.VISIBLE);
		}

	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setLogo(R.drawable.img_logo, false)
				.setIndicator(R.drawable.back)
				.setCenterText(
						getResources().getString(
								R.string.title_contract_details))
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
		txt_product_name = (TextView) findViewById(R.id.contract_details_product_name);
		// product_layout = (RelativeLayout)
		// findViewById(R.id.contract_details_product_layout);
		scrollView = (ScrollView) findViewById(R.id.contract_details_scrollView);
		scrollView.smoothScrollTo(0, 0);
		txt_status = (TextView) findViewById(R.id.contract_details_status);

		txt_insurance = (TextView) findViewById(R.id.contract_details_insurance);
		txt_payDate = (TextView) findViewById(R.id.contract_details_paydate);
		txt_payAge = (TextView) findViewById(R.id.contract_details_payage);

		layout_insurance = (LinearLayout) findViewById(R.id.contract_details_insurance_layout);
		layout_payDate = (LinearLayout) findViewById(R.id.contract_details_paydate_layout);
		layout_payAge = (LinearLayout) findViewById(R.id.contract_details_payage_layout);

		txt_userName = (TextView) findViewById(R.id.contract_details_username);
		// txt_amount = (TextView) findViewById(R.id.contract_details_amount);
		txt_acceptor = (TextView) findViewById(R.id.contract_details_acceptor);
		txt_acceptorPhone = (TextView) findViewById(R.id.contract_details_acceptor_phones);
		txt_lcs = (TextView) findViewById(R.id.contract_details_licaishi);
		txt_lcsPhone = (TextView) findViewById(R.id.contract_details_licaishi_phone);
		txt_address = (TextView) findViewById(R.id.contract_details_address);
		btn_next = (Button) findViewById(R.id.contract_details_btn_next);
		layout_lcs = (LinearLayout) findViewById(R.id.layout_lcs);
		layout_lcs_phone = (LinearLayout) findViewById(R.id.layout_lcs_phone);

		sendLayout = (RelativeLayout) findViewById(R.id.express_layout);
		txt_express_name = (TextView) findViewById(R.id.express_name);
		txt_express_code = (TextView) findViewById(R.id.express_number);
		txt_express_url = (TextView) findViewById(R.id.check_express);

		receiveLayout = (RelativeLayout) findViewById(R.id.express_back_layout);
		txt_express_name_back = (TextView) findViewById(R.id.express_conpany_back);
		txt_express_code_back = (TextView) findViewById(R.id.express_number_back);
		txt_express_url_back = (TextView) findViewById(R.id.check_express_back);

		listView = (ListViewForScrollView) findViewById(R.id.contract_details_listview);
		listView.setBackgroundColor(getResources().getColor(R.color.gray_light));
		txtTitle = (TextView) findViewById(R.id.contract_title);
		// product_layout.setOnClickListener(this);
		txt_express_url.setOnClickListener(this);
		txt_express_url_back.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		
		
		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
				initData();
//				insuranceBaseAdapter.notifyDataSetChanged();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});

	}

	private void initData() {
		if (status.equals("unsign")) {// 待签收
			btn_next.setText("签收合同");
			scrollView.setPadding(0, 0, 0, 90);
			btn_next.setVisibility(View.VISIBLE);
		} else if (status.equals("unreturn")) {// 待寄回
			btn_next.setText("寄回合同");
			scrollView.setPadding(0, 0, 0, 90);
			btn_next.setVisibility(View.VISIBLE);
		}
//		txt_product_name.setText(productName);
		// product_layout.setClickable(false);
		requestData(contractId, status, type);
	}

	private void requestData(final String contractId, String status, String type) {
		HtmlRequest.getContractDetails(ContractDetailsActivity.this,
				contractId, status, type, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							ResultContractTrackListContentBean data = (ResultContractTrackListContentBean) params.result;
							if (data.getContractList() != null
									&& data.getContractList().size() != 0) {
								setContractListData(data.getContractList());
							}
							// 合同跟踪
							setContractTrackData(data);

						} else {
							netFail = true;
							Toast.makeText(ContractDetailsActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	private void requestSignContractData(final String contractId) {
		HtmlRequest.getSignContract(ContractDetailsActivity.this, contractId,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							ResultSignContractContentBean data = (ResultSignContractContentBean) params.result;
							if (data != null) {
								if (data.getFlag().equals("true")) {
									Intent intent = new Intent(
											ContractDetailsActivity.this,
											ContractActivity.class);
									startActivity(intent);
									stack.removeAllActivity();
								}
							}
						} else {
							Toast.makeText(ContractDetailsActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	protected void setContractTrackData(ResultContractTrackListContentBean data) {
		MouldList<ResultContractTrackAllListBean> allListBeans = new MouldList<ResultContractTrackAllListBean>();

		if (data.getInitList() != null && data.getInitList().size() != 0) {
			ResultContractTrackAllListBean allListBean = new ResultContractTrackAllListBean(
					data.getInitList().get(0).getCreatorName(), data
							.getInitList().get(0).getNoticeDesc(), data
							.getInitList().get(0).getCreateTime());
			allListBeans.add(allListBean);
		}
		if (data.getAuditList() != null && data.getAuditList().size() != 0) {
			ResultContractTrackAllListBean allListBean = new ResultContractTrackAllListBean(
					data.getAuditList().get(0).getCreatorName(), data
							.getAuditList().get(0).getNoticeDesc(), data
							.getAuditList().get(0).getCreateTime());
			allListBeans.add(allListBean);
		}
		if (data.getUnsignList() != null && data.getUnsignList().size() != 0) {
			ResultContractTrackAllListBean allListBean = new ResultContractTrackAllListBean(
					data.getUnsignList().get(0).getCreatorName(), data
							.getUnsignList().get(0).getNoticeDesc(), data
							.getUnsignList().get(0).getCreateTime());
			allListBeans.add(allListBean);
		}
		if (data.getUnreturnList() != null
				&& data.getUnreturnList().size() != 0) {
			ResultContractTrackAllListBean allListBean = new ResultContractTrackAllListBean(
					data.getUnreturnList().get(0).getCreatorName(), data
							.getUnreturnList().get(0).getNoticeDesc(), data
							.getUnreturnList().get(0).getCreateTime());
			allListBeans.add(allListBean);
		}
		if (data.getUncallbackList() != null
				&& data.getUncallbackList().size() != 0) {
			ResultContractTrackAllListBean allListBean = new ResultContractTrackAllListBean(
					data.getUncallbackList().get(0).getCreatorName(), data
							.getUncallbackList().get(0).getNoticeDesc(), data
							.getUncallbackList().get(0).getCreateTime());
			allListBeans.add(allListBean);
		}
		if (data.getCallbackedList() != null
				&& data.getCallbackedList().size() != 0) {
			ResultContractTrackAllListBean allListBean = new ResultContractTrackAllListBean(
					data.getCallbackedList().get(0).getCreatorName(), data
							.getCallbackedList().get(0).getNoticeDesc(), data
							.getCallbackedList().get(0).getCreateTime());
			allListBeans.add(allListBean);
		}
		if (allListBeans.size() != 0) {
			txtTitle.setVisibility(View.VISIBLE);
		}
		adapter = new ContractTrackAdapter(this, allListBeans);
		listView.setAdapter(adapter);
	}

	protected void setContractListData(
			MouldList<ResultContractTrackListItemBean> contractList) {
		contractListData = contractList;
		productId = contractList.get(0).getPRODUCTID();
		// product_layout.setClickable(true);
		txt_product_name.setText(contractList.get(0).getNAME());
		if (contractList.get(0).getSTATUS().equals("init")) {
			txt_status.setText("待确认");
		} else if (contractList.get(0).getSTATUS().equals("audit")) {
			txt_status.setText("待发送");
		} else if (contractList.get(0).getSTATUS().equals("rejected")) {
			txt_status.setText("已驳回");
		} else if (contractList.get(0).getSTATUS().equals("unsign")) {
			txt_status.setText("待签收");
		} else if (contractList.get(0).getSTATUS().equals("unreturn")) {
			txt_status.setText("待寄回");
		} else if (contractList.get(0).getSTATUS().equals("uncallback")) {
			txt_status.setText("待回收确认");
		} else if (contractList.get(0).getSTATUS().equals("callbacked")) {
			txt_status.setText("已回收");
		}

		txt_insurance.setText(contractList.get(0).getDEPUTYINSURANCENAME());
		if (contractList.get(0).getPAYLIMITTIME().equals("趸缴") || contractList.get(0).getPAYLIMITTIME().equals("趸交")) {
			txt_payDate.setText(contractList.get(0).getPAYLIMITTIME());
		} else {
			txt_payDate.setText(contractList.get(0).getPAYLIMITTIME());
		}
		txt_payAge.setText(contractList.get(0).getAGECOVERAGE());
		txt_userName.setText(contractList.get(0).getCUSTOMERNAME());
		// txt_amount.setText(contractList.get(0).getAMOUNT());
		txt_acceptor.setText(contractList.get(0).getACCEPTER());
		txt_acceptorPhone.setText(contractList.get(0).getACCEPTPHONE());

		txt_lcs.setText(contractList.get(0).getUSERNAME());
		txt_lcsPhone.setText(contractList.get(0).getUSERPHONE());

		txt_address.setText(contractList.get(0).getACCEPTADDRESS());
		if (contractList.get(0).getEXPRESSNAME() != null) {
			sendLayout.setVisibility(View.VISIBLE);
			txt_express_name.setText(contractList.get(0).getEXPRESSNAME());
			txt_express_code.setText(contractList.get(0).getEXPRESSCODE());
			txt_express_url.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线
		}
		if (contractList.get(0).getEXPRESSNAMEBACK() != null) {
			receiveLayout.setVisibility(View.VISIBLE);
			txt_express_name_back.setText(contractList.get(0)
					.getEXPRESSNAMEBACK());
			txt_express_code_back.setText(contractList.get(0)
					.getEXPRESSCODEBACK());
			txt_express_url_back.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/*
		 * case R.id.contract_details_product_layout:// 跳转到产品详情
		 * if(type.equals("信托")){ Intent intent=new
		 * Intent(ContractDetailsActivity.this,TrustDetailsActivity.class);
		 * intent.putExtra("Mtrustid", productId); startActivity(intent); }else
		 * if(type.equals("资管")){ Intent intent=new
		 * Intent(ContractDetailsActivity.this,ZiGuanItemActivity.class);
		 * intent.putExtra("Mtrustid", productId); startActivity(intent); }else
		 * if(type.equals("阳光私募")){ Intent intent=new
		 * Intent(ContractDetailsActivity.this,SunshineActivity.class);
		 * intent.putExtra("Sunshine", productId); startActivity(intent); }else
		 * if(type.equals("私募股权")){ Intent intent=new
		 * Intent(ContractDetailsActivity.this,EquityItmeActivity.class);
		 * intent.putExtra("EquityId", productId); startActivity(intent); }else{
		 * Intent intent=new
		 * Intent(ContractDetailsActivity.this,InsuranceItmeActivity.class);
		 * intent.putExtra("id", productId); startActivity(intent); } break;
		 */case R.id.contract_details_btn_next:
			if (status.equals("unsign")) {// 签收合同
				SignContractDialog dialog = new SignContractDialog(this,
						new OnSignContractChanged() {

							@Override
							public void onConfim() {
								requestSignContractData(contractId);
							}

							@Override
							public void onCancel() {

							}
						}, "是否签收合同");
				dialog.show();

			} else if (status.equals("unreturn")) {// 寄回合同
				Intent intent = new Intent(this, PostContractActivity.class);
				intent.putExtra("contractId", contractId);
				startActivity(intent);
			}
			break;
		case R.id.check_express:
			Intent i_express = new Intent();
			i_express.setClass(this, WebActivity.class);
			i_express.putExtra("type", WebActivity.WEBTYPE_EXPRESS);
			i_express.putExtra("url", contractListData.get(0).getEXPRESSURL());
			startActivity(i_express);
			break;
		case R.id.check_express_back:
			Intent i_express_back = new Intent();
			i_express_back.setClass(this, WebActivity.class);
			i_express_back.putExtra("type", WebActivity.WEBTYPE_EXPRESS_BACK);
			i_express_back.putExtra("url", contractListData.get(0)
					.getEXPRESSURLBACK());
			startActivity(i_express_back);
			break;
		default:
			break;
		}
	}

}