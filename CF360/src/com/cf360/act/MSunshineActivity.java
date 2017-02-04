package com.cf360.act;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.bean.ResultSunShineContentBean;
import com.cf360.bean.ResultSunShineListItem;
import com.cf360.mould.BaseParams;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.HtmlRequest;
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
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MSunshineActivity extends BaseActivity {
	private PullToRefreshListView sunshineListview;
	private ResultSunShineContentBean data;
	private MouldList<ResultSunShineListItem> productPrivateAppList;
	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewLeft viewLeft;
	private ViewRight viewRight;
	private ArrayList<String> sTextArray;
	private ArrayList<String> arrayList;
	private MySunBaseAdapter mySunBaseAdapter;
	private int infoPage = 1;
	private String SdefaultType;
	private String filterType = "0";
	private String commission;
	private String issuer;
	private String investmentField;
	private String annualRate;

	private ArrayList<String> companyList;
	private ArrayList<String> fundList;
	private ArrayList<String> investmentTypeList;
	private ArrayList<String> statusList;
	
	private ArrayList<String> companyList_1;
	private ArrayList<String> fundList_1;
	private ArrayList<String> investmentTypeList_1;
	private ArrayList<String> statusList_1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.m_sunshine_activity);
		initTopTitle();

		initView();
		initData();
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		requestPaiXuData("ygsm", "0", "1", SdefaultType);
		arrayList = new ArrayList<String>();
		arrayList.add("默认排序");
		arrayList.add("前端佣金最高");
		arrayList.add("累计净值最高");
		productPrivateAppList = new MouldList<ResultSunShineListItem>();
		expandTabView = (ExpandTabView) findViewById(R.id.title);
		sunshineListview = (PullToRefreshListView) findViewById(R.id.sunshine_listview);
		sunshineListview.setOnItemClickListener(new MyOnClickListener());

		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initView();
				initData();
				mySunBaseAdapter.notifyDataSetChanged();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
	}

	private void initData() {
		viewLeft = new ViewLeft(this, arrayList);
		mySunBaseAdapter = new MySunBaseAdapter(productPrivateAppList,
				MSunshineActivity.this);
		sunshineListview.setAdapter(mySunBaseAdapter);
		sunshineListview
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						if (refreshView.isHeaderShown()) {
							if (filterType.equals("0")) {
								if (infoPage >= 2) {
									requestPaiXuData("ygsm", "0", (--infoPage)
											+ "", SdefaultType);
								} else {
									requestPaiXuData("ygsm", "0", "1",
											SdefaultType);
								}
							} else if (filterType.equals("1")) {
								if (infoPage >= 2) {
									requestSaiXuanData("ygsm", annualRate, "1",
											commission, investmentField,
											"orderNew", issuer, (--infoPage)
													+ "");

								} else {
									requestSaiXuanData("ygsm", annualRate, "1",
											commission, investmentField,
											"orderNew", issuer, "1");
								}
							}
						} else {
							if (filterType.equals("0")) {

								requestPaiXuData("ygsm", "0",
										(++infoPage) + "", SdefaultType);

							} else if (filterType.equals("1")) {

								requestSaiXuanData("ygsm", annualRate, "1",
										commission, investmentField,
										"orderNew", issuer, (++infoPage) + "");

							}
						}
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mySunBaseAdapter.notifyDataSetChanged();
	}

	class MyOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			Intent intent = new Intent(MSunshineActivity.this,
					SunshineActivity.class);
			String id = productPrivateAppList.get(arg2 - 1).getId();
			intent.putExtra("Sunshine", id);
			startActivity(intent);
		}

	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(new Action(2, 0, R.drawable.search));
		title.setCenterText(getResources().getString(R.string.sunshine_title))
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
							Search.setClass(MSunshineActivity.this,
									SearchProductActivity.class);//
							startActivity(Search);
							break;

						default:
							break;
						}
					}

					@Override
					public void onMenu(int id) {
						Toast.makeText(MSunshineActivity.this, "menu", Toast.LENGTH_SHORT)
								.show();

					}

				});
	}

	private void requestPaiXuData(String category, String filterType,
			String pageNo, String sortWay) {
		HtmlRequest.SunShineResult(MSunshineActivity.this, category,
				filterType, pageNo, sortWay, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							data = (ResultSunShineContentBean) params.result;
							companyList = data.getCompanyList();

							if (data != null) {
								if (TextUtils.isEmpty(data.getAuditStatus())) {
									PreferenceUtil.setAuditStatus(null);
								} else {
									PreferenceUtil.setAuditStatus(data
											.getAuditStatus());
								}
							}

							fundList = data.getFundList();
							investmentTypeList = data.getInvestmentTypeList();
							statusList = data.getStatusList();
							if(!(investmentTypeList.equals(investmentTypeList_1)&&statusList.equals(statusList_1)&&fundList.equals(fundList_1)&&companyList.equals(companyList_1))){
								viewRight = new ViewRight(MSunshineActivity.this,
										investmentTypeList, statusList, fundList,
										companyList, "投资类型", "产品状态", "基金公司", "基金经理");
								investmentTypeList_1 = investmentTypeList;
								statusList_1 = statusList;
								fundList_1 = fundList;
								companyList_1 = companyList;
								
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
									Toast.makeText(MSunshineActivity.this,
											"已经到最后一页", Toast.LENGTH_SHORT)
											.show();
									infoPage--;
									mySunBaseAdapter.notifyDataSetChanged();
									expandTabView.postDelayed(new Runnable() {
										@Override
										public void run() {
											sunshineListview
													.onRefreshComplete();
										}
									}, 1000);
									sunshineListview.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													100, 100);
								} else {
									productPrivateAppList.clear();
									productPrivateAppList.addAll(data
											.getProductPrivateAppList());
									mySunBaseAdapter.notifyDataSetChanged();
									expandTabView.postDelayed(new Runnable() {
										@Override
										public void run() {
											sunshineListview
													.onRefreshComplete();
										}
									}, 1000);
									sunshineListview.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													100, 100);
								}

							}

						} else {
							netFail = true;
							sunshineListview.onRefreshComplete();
							Toast.makeText(MSunshineActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	private void requestSaiXuanData(String category, String companyList,
			String filterType, String fundList, String investmentTypeList,
			String sortWay, String statusList, String pageNo) {
		HtmlRequest.SunShineSaiXuanResult(MSunshineActivity.this, category,
				companyList, filterType, fundList, investmentTypeList, sortWay,
				statusList, pageNo, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						data = (ResultSunShineContentBean) params.result;
						if (data != null) {
							if (data.getProductPrivateAppList().size() == 0
									&& infoPage != 1) {
								Toast.makeText(MSunshineActivity.this,
										"已经到最后一页", Toast.LENGTH_SHORT).show();
								infoPage--;
								mySunBaseAdapter.notifyDataSetChanged();
								expandTabView.postDelayed(new Runnable() {
									@Override
									public void run() {
										sunshineListview.onRefreshComplete();
									}
								}, 1000);
								sunshineListview.getRefreshableView()
										.smoothScrollToPositionFromTop(0, 100,
												100);
							} else {
								productPrivateAppList.clear();
								productPrivateAppList.addAll(data
										.getProductPrivateAppList());
								mySunBaseAdapter.notifyDataSetChanged();
								expandTabView.postDelayed(new Runnable() {
									@Override
									public void run() {
										sunshineListview.onRefreshComplete();
									}
								}, 1000);
								sunshineListview.getRefreshableView()
										.smoothScrollToPositionFromTop(0, 100,
												100);
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
				requestSaiXuanData("ygsm", annualRate, filterType, commission,
						investmentField, SdefaultType, issuer, infoPage+"");
				viewLeft.ReturnState();
				onBackPressed();
			}

			@Override
			public void getGridview1(View veiw, int position) {
				investmentField = investmentTypeList.get(position).toString();
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
				annualRate = companyList.get(position).toString();
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
					requestPaiXuData("ygsm", "0", infoPage+"", SdefaultType);
					break;
				case 1:
					SdefaultType = "orderComm";
					filterType = "0";
					infoPage = 1;
					requestPaiXuData("ygsm", "0", infoPage+"", SdefaultType);
					break;
				case 2:
					SdefaultType = "orderNet";
					filterType = "0";
					infoPage = 1;
					requestPaiXuData("ygsm", "0", infoPage+"", SdefaultType);
					break;
				default:
					break;
				}
				onBackPressed();
			}
		});

	}

	class MySunBaseAdapter extends BaseAdapter {

		private Context mContext;
		private MouldList<ResultSunShineListItem> mproductPrivateAppList;

		public MySunBaseAdapter(
				MouldList<ResultSunShineListItem> productPrivateAppList,
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
						R.layout.m_item_sunshineadapter, null, false);
				holder.SunTitleContent = (TextView) convertView
						.findViewById(R.id.sun_title_content);
				holder.SunContentFirst = (TextView) convertView
						.findViewById(R.id.sun_content_first_one);
				holder.SunContentSecond = (TextView) convertView
						.findViewById(R.id.sun_content_Second_one);
				holder.SunContentThird = (TextView) convertView
						.findViewById(R.id.sun_content_Third_one);
				holder.SunBranchImage = (RelativeLayout) convertView
						.findViewById(R.id.SunBranchImage);
				holder.SunIncludeImage = (RelativeLayout) convertView
						.findViewById(R.id.SunIncludeImage);
				holder.SunHotImage = (RelativeLayout) convertView
						.findViewById(R.id.SunHotImage);
				holder.SunTuijianImage = (RelativeLayout) convertView
						.findViewById(R.id.SunTuijianImage);
				holder.mProgressBar = (MyProgressBar) convertView
						.findViewById(R.id.sun_ProgressBar);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.SunTitleContent.setText(mproductPrivateAppList.get(position)
					.getName());
			holder.SunContentFirst.setText(mproductPrivateAppList.get(position)
					.getMinAmount() + "万元起");

			if (mproductPrivateAppList.get(position).getSumNet().equals("0000")) {
				holder.SunContentSecond.setText("--");
			} else {
				holder.SunContentSecond.setText(mproductPrivateAppList.get(
						position).getSumNet());
			}

			holder.mProgressBar.setProgress(new Integer(mproductPrivateAppList
					.get(position).getRecruitmentProcess()));

			if (PreferenceUtil.isAuditStatus().equals("success")) {
				if (PreferenceUtil.isLogin()) {
					holder.SunContentThird.setText(mproductPrivateAppList.get(
							position).getMinFrontcomm()
							+ "%"
							+ "-"
							+ mproductPrivateAppList.get(position)
									.getMaxFrontcomm() + "%");
				} else {
					holder.SunContentThird.setText("认证可见");
				}
			} else {
				holder.SunContentThird.setText("认证可见");
			}

			if (mproductPrivateAppList.get(position).getSaleType().equals("0")) {
				// 包销
				holder.SunBranchImage.setVisibility(View.GONE);
				holder.SunIncludeImage.setVisibility(View.VISIBLE);// 包销
			} else {
				// 分销
				holder.SunIncludeImage.setVisibility(View.GONE);
				holder.SunBranchImage.setVisibility(View.VISIBLE);
			}
			// 是否热销
			if (mproductPrivateAppList.get(position).getSellingStatus()
					.equals("1")) {
				holder.SunHotImage.setVisibility(View.VISIBLE);
			} else {
				holder.SunHotImage.setVisibility(View.GONE);
			}
			// 是否推荐
			if (mproductPrivateAppList.get(position).getRecommendStatus()
					.equals("1")) {
				holder.SunTuijianImage.setVisibility(View.VISIBLE);
			} else {
				holder.SunTuijianImage.setVisibility(View.GONE);
			}
			return convertView;
		}

		private class ViewHolder {
			TextView SunTitleContent;
			TextView SunContentFirst;
			TextView SunContentSecond;
			TextView SunContentThird;
			RelativeLayout SunBranchImage;
			RelativeLayout SunIncludeImage;
			RelativeLayout SunHotImage;
			RelativeLayout SunTuijianImage;
			MyProgressBar mProgressBar;
		}

	}

	@Override
	public void onBackPressed() {

		if (!expandTabView.onPressBack()) {
			finish();
		}
		mySunBaseAdapter.notifyDataSetChanged();

	}

}
