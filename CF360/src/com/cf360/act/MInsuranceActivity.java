package com.cf360.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cf360.R;
import com.cf360.act.MEquityActivity.MyEquityBaseAdapter;
import com.cf360.act.MEquityActivity.MyEquityClickListener;
import com.cf360.act.MSunshineActivity.MySunBaseAdapter;
import com.cf360.bean.InsuranceListBean;
import com.cf360.bean.ResultEquityContentBean;
import com.cf360.bean.ResultInsuranceContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.popupwindow.ExpandTabView;
import com.cf360.popupwindow.ViewLeft;
import com.cf360.popupwindow.ViewRight;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MInsuranceActivity extends BaseActivity {
	private PullToRefreshListView InsuranceListview;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ExpandTabView expandTabView;
	private ViewLeft viewLeft;
	private ViewRight viewRight;
	private ArrayList<String> sTextArray;
	private ArrayList<String> arrayList;
	private MouldList<InsuranceListBean> productsList;
	private ResultInsuranceContentBean data;
	private int infoPage = 1;
	private String SdefaultType = "defaultType";
	private String filterType = "0";
	private String InsuranceCategorys;
	private String CompanyShortNames;
	private MyInsuranceAdapter insuranceBaseAdapter;
	
	private ArrayList<String> companyShortNames;
	private ArrayList<String> insuranceCategorys;
	
	private ArrayList<String> companyShortNames_1;
	private ArrayList<String> insuranceCategorys_1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.m_insurance_activity);
		initTopTitle();
		initView();
		initData();
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		arrayList = new ArrayList<String>();
		arrayList.add("默认排序");
		arrayList.add("佣金最高");
		productsList = new MouldList<InsuranceListBean>();
		expandTabView = (ExpandTabView) findViewById(R.id.title);
		InsuranceListview = (PullToRefreshListView) findViewById(R.id.Insurance_listview);

		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
				initData();

				insuranceBaseAdapter.notifyDataSetChanged();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
	}

	private void initData() {
		viewLeft = new ViewLeft(this, arrayList);
		insuranceBaseAdapter = new MyInsuranceAdapter(productsList,
				MInsuranceActivity.this);
		InsuranceListview.setAdapter(insuranceBaseAdapter);
		requestPaiXuData("0", "1", SdefaultType);
		InsuranceListview
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {

						if (refreshView.isHeaderShown()) {
							if (filterType.equals("0")) {
								if (infoPage >= 2) {
									requestPaiXuData("0", (--infoPage) + "",
											SdefaultType);
								} else {
									requestPaiXuData("0", "1", SdefaultType);
								}
							} else if (filterType.equals("1")) {
								if (infoPage >= 2) {
									InsuranceResult(InsuranceCategorys,
											CompanyShortNames, "1",
											(--infoPage) + "", "defaultType");
								} else {

									InsuranceResult(InsuranceCategorys,
											CompanyShortNames, "1",
											1 + "", "defaultType");
								}
							}

						} else {
							if (filterType.equals("0")) {

								requestPaiXuData("0", (++infoPage) + "",
										SdefaultType);

							} else if (filterType.equals("1")) {
								InsuranceResult(InsuranceCategorys,
										CompanyShortNames, "1",
										(++infoPage) + "", "defaultType");
							}
						}
					}
				});
		InsuranceListview.setOnItemClickListener(new MyEquityClickListener());

	}

	@Override
	protected void onResume() {
		super.onResume();
		insuranceBaseAdapter.notifyDataSetChanged();
	}

	class MyEquityClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Intent intent = new Intent(MInsuranceActivity.this,
					InsuranceItmeActivity.class);
			InsuranceListBean i = productsList.get(position - 1);
			String commissionratio = productsList.get(position - 1)
					.getCOMMISSIONRATIO();
			intent.putExtra("id", i.getID());
			intent.putExtra("commissionratio", commissionratio);
			startActivity(intent);

		}

	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(new Action(2, 0, R.drawable.search));
		title.setCenterText(
				getResources().getString(R.string.ins_Details_title))
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
								Search.setClass(MInsuranceActivity.this,
										SearchProductActivity.class);//
								startActivity(Search);
								break;

							default:
								break;
						}
					}

					@Override
					public void onMenu(int id) {
						Toast.makeText(MInsuranceActivity.this, "menu", Toast.LENGTH_SHORT)
								.show();

					}

				});
	}

	private void requestPaiXuData(String filterType, String pageNo,
			String sortType) {
		HtmlRequest.InsuranceResult(MInsuranceActivity.this, filterType,
				pageNo, sortType, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							data = (ResultInsuranceContentBean) params.result;
							companyShortNames = data.getCompanyShortNames();

							if (data != null) {
								if (TextUtils.isEmpty(data.getAuditStatus())) {
									PreferenceUtil.setAuditStatus(null);
								} else {
									PreferenceUtil.setAuditStatus(data
											.getAuditStatus());
								}
								insuranceCategorys = data.getInsuranceCategorys();

								if (!(insuranceCategorys.equals(insuranceCategorys_1) && companyShortNames.equals(companyShortNames_1))) {
									viewRight = new ViewRight(MInsuranceActivity.this,
											insuranceCategorys, companyShortNames,
											"保险类型", "保险公司");
									insuranceCategorys_1 = insuranceCategorys;
									companyShortNames_1 = companyShortNames;

								}

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
								if (data.getProductsList().size() == 0
										&& infoPage != 1) {
									Toast.makeText(MInsuranceActivity.this,
											"已经到最后一页", Toast.LENGTH_SHORT)
											.show();
									infoPage--;
									insuranceBaseAdapter.notifyDataSetChanged();
									expandTabView.postDelayed(new Runnable() {
										@Override
										public void run() {
											InsuranceListview
													.onRefreshComplete();
										}
									}, 1000);
									InsuranceListview.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													100, 100);
								} else {
									productsList.clear();
									productsList.addAll(data.getProductsList());
									insuranceBaseAdapter.notifyDataSetChanged();
									expandTabView.postDelayed(new Runnable() {
										@Override
										public void run() {
											InsuranceListview
													.onRefreshComplete();
										}
									}, 1000);
									InsuranceListview.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													100, 100);
								}

							} else {
								InsuranceListview.onRefreshComplete();
							}
						} else {
							netFail = true;
							InsuranceListview.onRefreshComplete();
							Toast.makeText(MInsuranceActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	private void InsuranceResult(String category, String companyShortName,
			String filterType, String pageNo, String sortType) {
		HtmlRequest.InsuranceShaiXuanResult(MInsuranceActivity.this, category,
				companyShortName, filterType, pageNo, sortType,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							data = (ResultInsuranceContentBean) params.result;
							if (data != null) {
								if (data.getProductsList().size() == 0
										&& infoPage != 1) {
									Toast.makeText(MInsuranceActivity.this,
											"已经到最后一页", Toast.LENGTH_SHORT)
											.show();
									infoPage--;
									insuranceBaseAdapter.notifyDataSetChanged();
									expandTabView.postDelayed(new Runnable() {
										@Override
										public void run() {
											InsuranceListview
													.onRefreshComplete();
										}
									}, 1000);
									InsuranceListview.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													100, 100);
								} else {
									productsList.clear();
									productsList.addAll(data.getProductsList());
									insuranceBaseAdapter.notifyDataSetChanged();
									expandTabView.postDelayed(new Runnable() {
										@Override
										public void run() {
											InsuranceListview
													.onRefreshComplete();
										}
									}, 1000);
									InsuranceListview.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													100, 100);
								}

							}
						} else {
							InsuranceListview.onRefreshComplete();
//							Toast.makeText(MInsuranceActivity.this,
//									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	class MyInsuranceAdapter extends BaseAdapter {

		private Context mContext;
		private MouldList<InsuranceListBean> productsList;

		public MyInsuranceAdapter(MouldList<InsuranceListBean> productsList,
				Context mContext) {
			super();
			this.productsList = productsList;
			this.mContext = mContext;
		}

		@Override
		public int getCount() {
			if (productsList == null) {
				return 0;
			} else {
				return this.productsList.size();
			}
		}

		@Override
		public Object getItem(int position) {
			if (productsList == null) {
				return null;
			} else {
				return this.productsList.get(position);
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
						R.layout.m_item_insurance_adapter, null, false);
				holder.InsTitle = (TextView) convertView
						.findViewById(R.id.ins_title_content);
				holder.InsContentfirst = (TextView) convertView
						.findViewById(R.id.ins_content_first_one);
				holder.InsContentsecond = (TextView) convertView
						.findViewById(R.id.ins_content_Second_one);
				holder.InsContentthird = (TextView) convertView
						.findViewById(R.id.ins_content_Third_one);
				holder.InsuranceHotImage = (RelativeLayout) convertView
						.findViewById(R.id.InsuranceHotImage);
				holder.InsuranceTuijianImage = (RelativeLayout) convertView
						.findViewById(R.id.InsuranceTuijianImage);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.InsTitle.setText(productsList.get(position).getSHORT_NAME());
			holder.InsContentfirst.setText(productsList.get(position)
					.getCOMPANY_SHORT_NAME());
			holder.InsContentsecond.setText(productsList.get(position)
					.getTIME_LIMIT());
			String commissionratio = productsList.get(position)
					.getCOMMISSIONRATIO();

			if (PreferenceUtil.isAuditStatus().equals("success")) {
				if (PreferenceUtil.isLogin()) {
					holder.InsContentthird.setText(commissionratio);
				} else {
					holder.InsContentthird.setText("认证可见");
				}
			} else {
				holder.InsContentthird.setText("认证可见");
			}

			// 是否热销
			if (productsList.get(position).getSELLING_STATUS().equals("1")) {
				holder.InsuranceHotImage.setVisibility(View.VISIBLE);
			} else {
				holder.InsuranceHotImage.setVisibility(View.GONE);
			}
			// 是否推荐
			if (productsList.get(position).getRECOMMEND_STATUS().equals("1")) {
				holder.InsuranceTuijianImage.setVisibility(View.VISIBLE);
			} else {
				holder.InsuranceTuijianImage.setVisibility(View.GONE);
			}
			return convertView;
		}

		private class ViewHolder {
			TextView InsTitle;
			TextView InsContentfirst;
			TextView InsContentsecond;
			TextView InsContentthird;
			RelativeLayout InsuranceHotImage;
			RelativeLayout InsuranceTuijianImage;
		}

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
				if (TextUtils.isEmpty(InsuranceCategorys)) {
					InsuranceCategorys = insuranceCategorys.get(0).toString();
					// Toast.makeText(MInsuranceActivity.this,
					// InsuranceCategorys+"", 0).show();
				}
				if (TextUtils.isEmpty(CompanyShortNames)) {
					CompanyShortNames = "不限";
				}
				filterType = "1";
				infoPage = 1;
				InsuranceResult(InsuranceCategorys, CompanyShortNames,
						filterType,infoPage+"", SdefaultType);
				viewLeft.ReturnState();
				insuranceBaseAdapter.notifyDataSetChanged();
				onBackPressed();
			}

			@Override
			public void getGridview1(View veiw, int position) {
				InsuranceCategorys = insuranceCategorys.get(position)
						.toString();
			}

			@Override
			public void getGridview2(View veiw, int position) {
				CompanyShortNames = companyShortNames.get(position).toString();
			}

			@Override
			public void getGridview3(View veiw, int position) {
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

					SdefaultType = "defaultType";
					filterType = "0";
					infoPage = 1;
					requestPaiXuData("0", infoPage+"", "defaultType");
					break;
				case 1:
					filterType = "0";
					SdefaultType = "commissionMax";
					infoPage = 1;
					requestPaiXuData("0", infoPage+"", "commissionMax");
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

	}

}
