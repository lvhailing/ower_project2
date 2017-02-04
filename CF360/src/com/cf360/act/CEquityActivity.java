package com.cf360.act;

import java.util.ArrayList;
import com.cf360.R;
import com.cf360.bean.ResultEquityContentBean;
import com.cf360.bean.ResultEquityListItem;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.popupwindow.ExpandTabView;
import com.cf360.popupwindow.ViewLeft;
import com.cf360.popupwindow.ViewRight;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.MyProgressBar;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 私募股权列表
 * 
 * @author hasee
 * 
 */
public class CEquityActivity extends BaseActivity {
	private PullToRefreshListView EquityListview;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ExpandTabView expandTabView;
	private ViewLeft viewLeft;
	private ViewRight viewRight;
	private ArrayList<String> sTextArray;
	private ArrayList<String> arrayList;
	private MyEquitBaseAdapter myEquityBaseAdapter;
	private ResultEquityContentBean data;
	private MouldList<ResultEquityListItem> productPrivateAppList;
	private String SdefaultType = "orderNew";
	private String filterType = "0";
	private int infoPage = 1;
	private String commission;
	private String issuer;
	private String investmentField;
	private String annualRate;

	private ArrayList<String> fundList;
	private ArrayList<String> investmentTypeSMList;
	private ArrayList<String> statusList;

	private ArrayList<String> fundList_1;
	private ArrayList<String> investmentTypeSMList_1;
	private ArrayList<String> statusList_1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.m_equity_activity);
		initTopTitle();

		initView();
		initData();
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		requestPaiXuData("smgq", "0", "1", SdefaultType);
		arrayList = new ArrayList<String>();
		arrayList.add("默认排序");
		arrayList.add("前端佣金最高");
		productPrivateAppList = new MouldList<ResultEquityListItem>();
		expandTabView = (ExpandTabView) findViewById(R.id.title);
		EquityListview = (PullToRefreshListView) findViewById(R.id.equity_listview);

		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
				initData();
				myEquityBaseAdapter.notifyDataSetChanged();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
	}

	private void initData() {
		viewLeft = new ViewLeft(this, arrayList);

		myEquityBaseAdapter = new MyEquitBaseAdapter(productPrivateAppList,
				CEquityActivity.this);
		/*
		 * myEquityBaseAdapter=new MyCommonAdapter<ResultEquityListItem>(
		 * CEquityActivity
		 * .this,productPrivateAppList,R.layout.m_item_equityadapter) {
		 * 
		 * @Override public void convert(ViewHolder helper, ResultEquityListItem
		 * item) {
		 * 
		 * helper.setText(R.id.Equity_title_content,
		 * productPrivateAppList.get(helper.getPosition()).getName());
		 * helper.setText(R.id.Equity_content_first_one,
		 * productPrivateAppList.get
		 * (helper.getPosition()).getMinAmount()+"万元起");
		 * helper.setText(R.id.Equity_content_Second_one,
		 * productPrivateAppList.get
		 * (helper.getPosition()).getInvestmentLimit());
		 * helper.setProgress(R.id.Equite_ProgressBar,
		 * productPrivateAppList.get(
		 * helper.getPosition()).getRecruitmentProcess());
		 * 
		 * if(PreferenceUtil.isLogin()){
		 * helper.setText(R.id.Equity_content_Third_one,
		 * productPrivateAppList.get
		 * (helper.getPosition()).getMinFrontcomm()+"%"+
		 * "-"+productPrivateAppList
		 * .get(helper.getPosition()).getMaxFrontcomm()+"%");
		 * 
		 * }else{ helper.setText(R.id.Equity_content_Third_one, "登录可见"); }
		 * if(productPrivateAppList
		 * .get(helper.getPosition()).getSaleType().equals("0")){ //包销
		 * helper.setVisibility(R.id.EquityBranchImage, false);
		 * helper.setVisibility(R.id.EquityIncludeImage, true);//包销 }else{ //分销
		 * helper.setVisibility(R.id.EquityIncludeImage, false);
		 * helper.setVisibility(R.id.EquityBranchImage, true); } //是否热销
		 * if(productPrivateAppList
		 * .get(helper.getPosition()).getSellingStatus().equals("1")){
		 * helper.setVisibility(R.id.EquityHotImage, true); }else{
		 * helper.setVisibility(R.id.EquityHotImage, false); }
		 * 
		 * //是否推荐
		 * if(productPrivateAppList.get(helper.getPosition()).getRecommendStatus
		 * ().equals("1")){ helper.setVisibility(R.id.EquityTuijianImage, true);
		 * }else{ helper.setVisibility(R.id.EquityTuijianImage, false); }
		 * 
		 * } };
		 */
		EquityListview.setAdapter(myEquityBaseAdapter);
		EquityListview.setOnItemClickListener(new MyEquityClickListener());
		EquityListview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {

				if (refreshView.isHeaderShown()) {
					if (filterType.equals("0")) {
						if (infoPage >= 2) {
							requestPaiXuData("smgq", "0", (--infoPage) + "",
									SdefaultType);
						} else {
							requestPaiXuData("smgq", "0", "1", SdefaultType);
						}
					} else if (filterType.equals("1")) {
						if (infoPage >= 2) {
							requestShaiXuanData("smgq", annualRate, "1",
									commission, investmentField, "orderNew",
									issuer, (--infoPage) + "");

						} else {
							requestShaiXuanData("smgq", annualRate, "1",
									commission, investmentField, "orderNew",
									issuer, "1");

						}
					}

				} else {
					if (filterType.equals("0")) {
						requestPaiXuData("smgq", "0", (++infoPage) + "",
								SdefaultType);
					} else if (filterType.equals("1")) {
						requestShaiXuanData("smgq", annualRate, "1",
								commission, investmentField, "orderNew",
								issuer, (++infoPage) + "");

					}
				}
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		myEquityBaseAdapter.notifyDataSetChanged();
	}

	class MyEquityClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String id = productPrivateAppList.get(arg2 - 1).getId();
			Intent intent = new Intent(CEquityActivity.this,
					EquityItmeActivity.class);
			intent.putExtra("EquityId", id);
			startActivity(intent);

		}

	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(new Action(2, 0, R.drawable.search));
		title.setCenterText(
				getResources().getString(R.string.equity_Details_title))
				.setLogo(R.drawable.img_logo, false)
				.setIndicator(R.drawable.back).showMore(true)
				.setOnActionListener(new OnActionListener() {
					@Override
					public void onBack() {
						finish();
					}

					@Override
					public void onAction(int id) {
						switch (id) {
						case 2:
							Intent Search = new Intent();
							Search.setClass(CEquityActivity.this,
									SearchProductActivity.class);//
							startActivity(Search);
							break;

						default:
							break;
						}
					}

					@Override
					public void onMenu(int id) {

					}

				});
	}

	private void requestPaiXuData(String category, String filterType,
			String pageNo, String sortWay) {
		HtmlRequest.EquityPaiXuResult(CEquityActivity.this, category,
				filterType, pageNo, sortWay, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							data = (ResultEquityContentBean) params.result;
							fundList = data.getFundList();

							if (data != null) {
								if (TextUtils.isEmpty(data.getAuditStatus())) {
									PreferenceUtil.setAuditStatus(null);
								} else {
									PreferenceUtil.setAuditStatus(data
											.getAuditStatus());
								}
							}

							investmentTypeSMList = data
									.getInvestmentTypeSMList();
							statusList = data.getStatusList();
							if(!(investmentTypeSMList.equals(investmentTypeSMList_1)&&statusList.equals(statusList_1)&&fundList.equals(fundList_1))){
								
								viewRight = new ViewRight(CEquityActivity.this,
										investmentTypeSMList, statusList, fundList,
										"投资类型", "产品状态", "基金管理人");
								investmentTypeSMList_1 = investmentTypeSMList;
								statusList_1 = statusList;
								fundList_1 = fundList;
							}
							
							sTextArray = new ArrayList<String>();
							if (mViewArray.size() <= 0) {
								sTextArray.add("排序");
								sTextArray.add("筛选");
								mViewArray.add(viewLeft);
								mViewArray.add(viewRight);
								expandTabView.setValue(sTextArray, mViewArray);
							}
							initListener();
							if (data != null) {
								if (data.getProductPrivateAppList().size() == 0
										&& infoPage != 1) {
									Toast.makeText(CEquityActivity.this,
											"已经到最后一页", Toast.LENGTH_SHORT)
											.show();
									infoPage--;
									myEquityBaseAdapter.notifyDataSetChanged();
									expandTabView.postDelayed(new Runnable() {
										@Override
										public void run() {
											EquityListview.onRefreshComplete();
										}
									}, 1000);
									EquityListview.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													100, 100);
								} else {
									productPrivateAppList.clear();
									productPrivateAppList.addAll(data
											.getProductPrivateAppList());
									myEquityBaseAdapter.notifyDataSetChanged();
									expandTabView.postDelayed(new Runnable() {
										@Override
										public void run() {
											EquityListview.onRefreshComplete();
										}
									}, 1000);
									EquityListview.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													100, 100);
								}

							}
						} else {
							netFail = true;
							EquityListview.onRefreshComplete();
							Toast.makeText(CEquityActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}

					}
				});
	}

	private void requestShaiXuanData(String category, String companyList,
			String filterType, String fundList, String investmentTypeSMList,
			String sortWay, String statusList, String pageNo) {
		HtmlRequest.EquiteSaiXuanResult(CEquityActivity.this, category,
				companyList, filterType, fundList, investmentTypeSMList,
				sortWay, statusList, pageNo, new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							data = (ResultEquityContentBean) params.result;
							if (data != null) {
								if (data.getProductPrivateAppList().size() == 0
										&& infoPage != 1) {
									Toast.makeText(CEquityActivity.this,
											"已经到最后一页", Toast.LENGTH_SHORT)
											.show();
									infoPage--;
									myEquityBaseAdapter.notifyDataSetChanged();
									expandTabView.postDelayed(new Runnable() {
										@Override
										public void run() {
											EquityListview.onRefreshComplete();
										}
									}, 1000);
									EquityListview.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													100, 100);
								} else {
									productPrivateAppList.clear();
									productPrivateAppList.addAll(data
											.getProductPrivateAppList());
									myEquityBaseAdapter.notifyDataSetChanged();
									expandTabView.postDelayed(new Runnable() {
										@Override
										public void run() {
											EquityListview.onRefreshComplete();
										}
									}, 1000);
									EquityListview.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													100, 100);
								}

							}
						}

					}
				});
	}

	protected void initListener() {

		viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
			}

			@Override
			public void getCancle() {
				onBackPressed();

			}

			@Override
			public void getConfirm() {
				if (TextUtils.isEmpty(annualRate)) {
					annualRate = "不限";
				}
				if (TextUtils.isEmpty(commission)) {
					commission = "不限";
				}
				if (TextUtils.isEmpty(investmentField)) {
					investmentField = "不限";
				}
				if (TextUtils.isEmpty(issuer)) {
					issuer = "不限";
				}

				filterType = "1";
				infoPage = 1;
				requestShaiXuanData("smgq", annualRate, filterType, commission,
						investmentField, "orderNew", issuer, infoPage+"");
				viewLeft.ReturnState();
				myEquityBaseAdapter.notifyDataSetChanged();
				onBackPressed();
			}

			@Override
			public void getGridview1(View veiw, int position) {
				investmentField = investmentTypeSMList.get(position).toString();
			}

			@Override
			public void getGridview2(View veiw, int position) {
				issuer = statusList.get(position).toString();
			}

			@Override
			public void getGridview3(View veiw, int position) {
				commission = fundList.get(position).toString();
			}

			@Override
			public void getGridview4(View veiw, int position) {

			}

		});

		viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {
			@Override
			public void getValue(View view, int position) {
				viewRight.ReturnState1();
				switch (position) {
				case 0:
					SdefaultType = "orderNew";
					filterType = "0";
					infoPage = 1;
					requestPaiXuData("smgq", filterType, infoPage+"", SdefaultType);
					break;
				case 1:
					SdefaultType = "orderComm";
					filterType = "0";
					infoPage = 1;
					requestPaiXuData("smgq", filterType, infoPage+"", SdefaultType);
					break;
				case 2:
					SdefaultType = "orderNet";
					filterType = "0";
					infoPage = 1;
					requestPaiXuData("smgq", filterType, infoPage+"", SdefaultType);
					break;
				default:
					break;
				}
				onBackPressed();
			}
		});
	}

	@Override
	public void onBackPressed() {

		if (!expandTabView.onPressBack()) {
			finish();
		}
		myEquityBaseAdapter.notifyDataSetChanged();

	}

	class MyEquitBaseAdapter extends BaseAdapter {

		private Context mContext;
		private MouldList<ResultEquityListItem> mproductPrivateAppList;

		public MyEquitBaseAdapter(
				MouldList<ResultEquityListItem> productPrivateAppList,
				Context mContext) {
			super();
			this.mproductPrivateAppList = productPrivateAppList;
			this.mContext = mContext;
		}

		@Override
		public int getCount() {
			if (mproductPrivateAppList == null) {
				return 0;
			} else {
				return mproductPrivateAppList.size();
			}
		}

		@Override
		public Object getItem(int position) {
			if (mproductPrivateAppList == null) {
				return null;
			} else {
				return this.mproductPrivateAppList.get(position);
			}
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(this.mContext).inflate(
						R.layout.m_item_equityadapter, null, false);
				holder.Equity_title_content = (TextView) convertView
						.findViewById(R.id.Equity_title_content);
				holder.Equity_content_first_one = (TextView) convertView
						.findViewById(R.id.Equity_content_first_one);
				holder.Equity_content_Second_one = (TextView) convertView
						.findViewById(R.id.Equity_content_Second_one);
				holder.Equity_content_Third_one = (TextView) convertView
						.findViewById(R.id.Equity_content_Third_one);
				holder.EquityBranchImage = (RelativeLayout) convertView
						.findViewById(R.id.EquityBranchImage);
				holder.EquityIncludeImage = (RelativeLayout) convertView
						.findViewById(R.id.EquityIncludeImage);
				holder.EquityHotImage = (RelativeLayout) convertView
						.findViewById(R.id.EquityHotImage);
				holder.EquityTuijianImage = (RelativeLayout) convertView
						.findViewById(R.id.EquityTuijianImage);
				holder.Equite_ProgressBar = (MyProgressBar) convertView
						.findViewById(R.id.Equite_ProgressBar);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.Equity_title_content.setText(mproductPrivateAppList.get(
					position).getName());
			holder.Equity_content_first_one.setText(mproductPrivateAppList.get(
					position).getMinAmount()
					+ "万元起");

			if (mproductPrivateAppList.get(position).getInvestmentLimit()
					.equals("0000")) {

				holder.Equity_content_Second_one.setText("--");
			} else {

				holder.Equity_content_Second_one.setText(mproductPrivateAppList
						.get(position).getInvestmentLimit());
			}

			holder.Equite_ProgressBar.setProgress(new Integer(
					mproductPrivateAppList.get(position)
							.getRecruitmentProcess()));

			if (PreferenceUtil.isAuditStatus().equals("success")) {
				if (PreferenceUtil.isLogin()) {
					holder.Equity_content_Third_one
							.setText(mproductPrivateAppList.get(position)
									.getMinFrontcomm()
									+ "%"
									+ "-"
									+ mproductPrivateAppList.get(position)
											.getMaxFrontcomm() + "%");
				} else {
					holder.Equity_content_Third_one.setText("认证可见");
				}
			} else {
				holder.Equity_content_Third_one.setText("认证可见");
			}

			if (mproductPrivateAppList.get(position).getSaleType().equals("0")) {
				// 包销
				holder.EquityBranchImage.setVisibility(View.GONE);
				holder.EquityIncludeImage.setVisibility(View.VISIBLE);// 包销
			} else {
				// 分销
				holder.EquityIncludeImage.setVisibility(View.GONE);
				holder.EquityBranchImage.setVisibility(View.VISIBLE);
			}
			// 是否热销
			if (mproductPrivateAppList.get(position).getSellingStatus()
					.equals("1")) {
				holder.EquityHotImage.setVisibility(View.VISIBLE);
			} else {
				holder.EquityHotImage.setVisibility(View.GONE);
			}
			// 是否推荐
			if (mproductPrivateAppList.get(position).getRecommendStatus()
					.equals("1")) {
				holder.EquityTuijianImage.setVisibility(View.VISIBLE);
			} else {
				holder.EquityTuijianImage.setVisibility(View.GONE);
			}
			return convertView;
		}

		private class ViewHolder {
			TextView Equity_title_content;
			TextView Equity_content_first_one;
			TextView Equity_content_Second_one;
			TextView Equity_content_Third_one;
			RelativeLayout EquityBranchImage;
			RelativeLayout EquityIncludeImage;
			RelativeLayout EquityHotImage;
			RelativeLayout EquityTuijianImage;
			MyProgressBar Equite_ProgressBar;
		}

	}

}
