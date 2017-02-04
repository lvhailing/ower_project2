package com.cf360.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cf360.R;
import com.cf360.bean.ResultXinTuoItemContentBean;
import com.cf360.bean.ResultXinTuoListViewItem;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MZiGuanActivity extends BaseActivity {
	private ArrayList<ArrayList<HashMap<String, Object>>> mArrayList;
	private PullToRefreshListView mListView;
	private ExpandTabView expandTabView;
	private ViewLeft viewLeft;
	private ArrayList<String> arrayList;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ResultXinTuoItemContentBean data;
	private MouldList<ResultXinTuoListViewItem> resultListViewItem;
	private MyZiGuanBaseAdapter myBaseAdapter;
	private ViewRight viewRight;

	private ArrayList<String> investmentFieldLst;
	private ArrayList<String> issuerLst;
	private ArrayList<String> commissionLst;
	private ArrayList<String> annualRateLst;

	private ArrayList<String> investmentFieldLst_1 = null;
	private ArrayList<String> issuerLst_1 = null;
	private ArrayList<String> commissionLst_1 = null;
	private ArrayList<String> annualRateLst_1 = null;

	private ArrayList<String> mTextArray;
	private int infoPage = 1;
	private String filterType = "0";
	private String SdefaultType = "defaultType";
	private String commission;
	private String issuer;
	private String investmentField;
	private String annualRate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.m_ziguan_activity);
		initTopTitle();
		initView();
		initData();
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		requestData("资管", SdefaultType, filterType, infoPage);
		resultListViewItem = new MouldList<ResultXinTuoListViewItem>();
		expandTabView = (ExpandTabView) findViewById(R.id.title);
		mListView = (PullToRefreshListView) findViewById(R.id.Ziguan_listview);

		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initView();
				initData();
				myBaseAdapter.notifyDataSetChanged();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
	}

	private void initData() {
		arrayList = new ArrayList<String>();
		arrayList.add("默认排序");
		arrayList.add("佣金最高");
		arrayList.add("评级最优");
		arrayList.add("抵/质押率最高");
		viewLeft = new ViewLeft(this, arrayList);
		myBaseAdapter = new MyZiGuanBaseAdapter(resultListViewItem,
				MZiGuanActivity.this);
		mListView.setAdapter(myBaseAdapter);
		mListView.setOnItemClickListener(new MyItemClickListener());
		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {

				if (refreshView.isHeaderShown()) {
					if (filterType.equals("0")) {
						if (infoPage >= 2) {
							requestData("资管", SdefaultType, "0", (--infoPage));
						} else {
							requestData("资管", SdefaultType, "0", 1);
						}
					} else if (filterType.equals("1")) {
						if (infoPage >= 2) {
							requestSaiXuanData("资管", "defaultType", "1",
									(--infoPage), annualRate, commission,
									investmentField, issuer);
						} else {
							requestSaiXuanData("资管", "defaultType", "1", 1,
									annualRate, commission, investmentField,
									issuer);
						}
					}
				} else {

					if (filterType.equals("0")) {
						requestData("资管", SdefaultType, "0", (++infoPage));
					} else if (filterType.equals("1")) {
						requestSaiXuanData("资管", "defaultType", "1",
								(++infoPage), annualRate, commission,
								investmentField, issuer);

					}

				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		myBaseAdapter.notifyDataSetChanged();
	}

	private void requestData(String category, String defaultType,
			String filterType, int pageNo) {
		HtmlRequest.xinItemResult(MZiGuanActivity.this, category, defaultType,
				filterType, pageNo, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							data = (ResultXinTuoItemContentBean) params.result;

							if (data != null) {
								if (TextUtils.isEmpty(data.getAuditStatus())) {
									PreferenceUtil.setAuditStatus(null);
								} else {
									PreferenceUtil.setAuditStatus(data
											.getAuditStatus());
								}
							}

							investmentFieldLst = data.getInvestmentFieldLst();
							issuerLst = data.getIssuerLst();
							commissionLst = data.getCommissionLst();
							annualRateLst = data.getAnnualRateLst();

							if(!(investmentFieldLst.equals(investmentFieldLst_1)&&issuerLst.equals(issuerLst_1)&&commissionLst.equals(commissionLst_1)&&annualRateLst.equals(annualRateLst_1))){
								viewRight = new ViewRight(MZiGuanActivity.this,
										investmentFieldLst, issuerLst,
										commissionLst, annualRateLst, "投资领域",
										"发行机构", "佣金比例", "预期收益");
								investmentFieldLst_1 = investmentFieldLst;
								issuerLst_1 = issuerLst;
								commissionLst_1 = commissionLst;
								annualRateLst_1 = annualRateLst;
							}
							
//							viewRight = new ViewRight(MZiGuanActivity.this,
//									investmentFieldLst, issuerLst,
//									commissionLst, annualRateLst, "投资领域",
//									"发行机构", "佣金比例", "预期收益");

							mTextArray = new ArrayList<String>();
							if (mViewArray.size() <= 0) {
								mTextArray.add("排序");
								mTextArray.add("筛选");
								mViewArray.add(viewLeft);
								mViewArray.add(viewRight);
								expandTabView.setValue(mTextArray, mViewArray);
							}
							initListener();

							if (data != null) {
								if (data.getTrustList().size() == 0
										&& infoPage != 1) {
									Toast.makeText(MZiGuanActivity.this,
											"已经到最后一页", Toast.LENGTH_SHORT)
											.show();
									infoPage--;
									myBaseAdapter.notifyDataSetChanged();
									expandTabView.postDelayed(new Runnable() {
										@Override
										public void run() {
											mListView.onRefreshComplete();
										}
									}, 1000);
									mListView.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													100, 100);
								} else {
									resultListViewItem.clear();
									resultListViewItem.addAll(data
											.getTrustList());
									myBaseAdapter.notifyDataSetChanged();
									expandTabView.postDelayed(new Runnable() {
										@Override
										public void run() {
											mListView.onRefreshComplete();
										}
									}, 1000);
									mListView.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													100, 100);
								}

							}
						} else {
							netFail = true;
							mListView.onRefreshComplete();
							Toast.makeText(MZiGuanActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	private void requestSaiXuanData(String category, String defaultType,
			String filterType, int pageNo, String annualRate,
			String commission, String investmentField, String issuer) {
		HtmlRequest.xinSaiItemResult(MZiGuanActivity.this, category,
				defaultType, filterType, pageNo, annualRate, commission,
				investmentField, issuer, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {

						if (params.result != null) {
							data = (ResultXinTuoItemContentBean) params.result;
							if (data != null) {
								if (data.getTrustList().size() == 0
										&& infoPage != 1) {
									Toast.makeText(MZiGuanActivity.this,
											"已经到最后一页", Toast.LENGTH_SHORT)
											.show();
									infoPage--;
									myBaseAdapter.notifyDataSetChanged();
									mListView.postDelayed(new Runnable() {
										@Override
										public void run() {
											mListView.onRefreshComplete();
										}
									}, 1000);
									mListView.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													100, 100);
								} else {
									resultListViewItem.clear();
									resultListViewItem.addAll(data
											.getTrustList());
									myBaseAdapter.notifyDataSetChanged();
									mListView.postDelayed(new Runnable() {
										@Override
										public void run() {
											mListView.onRefreshComplete();
										}
									}, 1000);
									mListView.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													100, 100);
								}

							}

						}

					}
				});
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(new Action(2, 0, R.drawable.search));
		title.setCenterText(
				getResources().getString(R.string.ziguan_Details_title))
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
							Search.setClass(MZiGuanActivity.this,
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

	class MyItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			String id = resultListViewItem.get(position - 1).getId();
			String recruitmentProcess = resultListViewItem.get(position - 1)
					.getRecruitmentProcess();
			String Amount = resultListViewItem.get(position - 1).getAmount();
			String creditLevel = resultListViewItem.get(position - 1)
					.getCreditLevel();
			String availableAmount = resultListViewItem.get(position - 1)
					.getAvailableAmount();
			Intent intent = new Intent(MZiGuanActivity.this,
					ZiGuanItemActivity.class);
			intent.putExtra("Mtrustid", id);
			intent.putExtra("ProgressBar", recruitmentProcess);
			intent.putExtra("Amount", Amount);
			intent.putExtra("availableAmount", availableAmount);
			intent.putExtra("creditLevel", creditLevel);
			startActivity(intent);

		}

	}

	class MyZiGuanBaseAdapter extends BaseAdapter {

		private Context mContext;
		private MouldList<ResultXinTuoListViewItem> resultListViewItem;

		public MyZiGuanBaseAdapter(
				MouldList<ResultXinTuoListViewItem> resultListViewItem,
				Context mContext) {
			super();
			this.resultListViewItem = resultListViewItem;
			this.mContext = mContext;
		}

		@Override
		public int getCount() {
			if (resultListViewItem == null) {
				return 0;
			} else {
				return this.resultListViewItem.size();
			}
		}

		@Override
		public Object getItem(int position) {
			if (resultListViewItem == null) {
				return null;
			} else {
				return this.resultListViewItem.get(position);
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
						R.layout.m_item_ziguanadapter, null, false);
				holder.Title_content_text = (TextView) convertView
						.findViewById(R.id.ziguan_title_content);
				holder.Ziguan_TypeImage = (ImageView) convertView
						.findViewById(R.id.Ziguan_type_image);
				holder.Type_Include_image = (RelativeLayout) convertView
						.findViewById(R.id.ziguan_type_Include_image);
				holder.Type_Hot_image = (RelativeLayout) convertView
						.findViewById(R.id.ziguan_type_Hot_image);
				holder.Type_Branch_image = (RelativeLayout) convertView
						.findViewById(R.id.ziguan_type_Branch_image);
				holder.TypeTuijianImage = (RelativeLayout) convertView
						.findViewById(R.id.ziguan_type_tuijian_image);

				holder.Content_Money = (TextView) convertView
						.findViewById(R.id.ziguan_content_first_one);
				holder.Content_Month = (TextView) convertView
						.findViewById(R.id.ziguan_content_Second_one);
				holder.Content_Proportion = (TextView) convertView
						.findViewById(R.id.ziguan_content_Third_one);
				holder.mProgressBar = (MyProgressBar) convertView
						.findViewById(R.id.ziguan_ProgressBar);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			resultListViewItem.get(position).getName();
			String creditLevel = resultListViewItem.get(position)
					.getCreditLevel();
			Creditlevel(creditLevel, holder.Ziguan_TypeImage, position);
			holder.Title_content_text.setText(resultListViewItem.get(position)
					.getName());
			holder.Content_Money.setText(resultListViewItem.get(position)
					.getAmount() + "万元");
			holder.Content_Month.setText(resultListViewItem.get(position)
					.getTimeLimit());

			if (PreferenceUtil.isAuditStatus().equals("success")) {
				if (PreferenceUtil.isLogin()) {
					if (PreferenceUtil.getUserType().equals("corp")) {
						holder.Content_Proportion.setText(resultListViewItem
								.get(position).getBackCommission());
					} else {
						holder.Content_Proportion.setText(resultListViewItem
								.get(position).getFormCommission());
					}
				} else {
					holder.Content_Proportion.setText("认证可见");
				}
			} else {
				holder.Content_Proportion.setText("认证可见");
			}

			holder.mProgressBar.setProgress(new Integer(resultListViewItem.get(
					position).getRecruitmentProcess()));
			if (resultListViewItem.get(position).getSaleType().equals("0")) {
				// 包销
				holder.Type_Branch_image.setVisibility(View.GONE);
				holder.Type_Include_image.setVisibility(View.VISIBLE);// 包销
			} else {
				// 分销
				holder.Type_Include_image.setVisibility(View.GONE);
				holder.Type_Branch_image.setVisibility(View.VISIBLE);
			}
			// 是否热销
			if (resultListViewItem.get(position).getSellingStatus().equals("1")) {
				holder.Type_Hot_image.setVisibility(View.VISIBLE);
			} else {
				holder.Type_Hot_image.setVisibility(View.GONE);
			}
			// 是否推荐
			if (resultListViewItem.get(position).getRecommendStatus()
					.equals("1")) {
				holder.TypeTuijianImage.setVisibility(View.VISIBLE);
			} else {
				holder.TypeTuijianImage.setVisibility(View.GONE);
			}
			return convertView;
		}

		private class ViewHolder {
			TextView Title_content_text;
			TextView Type_Hot_text;
			TextView Type_Branch_text;
			TextView Type_Include_text;
			RelativeLayout Type_Include_image;
			RelativeLayout Type_Hot_image;
			RelativeLayout Type_Branch_image;
			TextView Content_Money;
			TextView Content_Month;
			TextView Content_Proportion;
			MyProgressBar mProgressBar;
			ImageView Ziguan_TypeImage;
			RelativeLayout TypeTuijianImage;
		}

	}

	public void Creditlevel(String creditLevel, ImageView A, int position) {
		if (!TextUtils.isEmpty(creditLevel)) {
			if (creditLevel.equals("1")) {
				A.setBackgroundResource(R.drawable.img_no);
			} else if (creditLevel.equals("2")) {
				A.setBackgroundResource(R.drawable.img_aaa);
			} else if (creditLevel.equals("3")) {
				A.setBackgroundResource(R.drawable.img_aa);
				A.setVisibility(View.VISIBLE);
			} else if (creditLevel.equals("4")) {
				A.setBackgroundResource(R.drawable.img_a);
			} else if (creditLevel.equals("7")) {
				A.setBackgroundResource(R.drawable.img_b);
				A.setVisibility(View.VISIBLE);
			} else if (creditLevel.equals("6")) {
				A.setBackgroundResource(R.drawable.img_bb);
				A.setVisibility(View.VISIBLE);
			} else if (creditLevel.equals("5")) {
				A.setBackgroundResource(R.drawable.img_bb);
				A.setVisibility(View.VISIBLE);
			} else if (creditLevel.equals("10")) {
				A.setBackgroundResource(R.drawable.img_c);
				A.setVisibility(View.VISIBLE);
			} else if (creditLevel.equals("9")) {
				A.setBackgroundResource(R.drawable.img_cc);
				A.setVisibility(View.VISIBLE);
			} else if (creditLevel.equals("8")) {
				A.setBackgroundResource(R.drawable.img_ccc);
				A.setVisibility(View.VISIBLE);
			} else {
				A.setBackgroundResource(R.drawable.img_d);
			}

		}
	}

	private void initListener() {

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
				requestSaiXuanData("资管", "defaultType", filterType, infoPage,
						annualRate, commission, investmentField, issuer);
				viewLeft.ReturnState();
				myBaseAdapter.notifyDataSetChanged();
				onBackPressed();
			}

			@Override
			public void getGridview1(View veiw, int position) {
				investmentField = investmentFieldLst.get(position).toString();

			}

			@Override
			public void getGridview2(View veiw, int position) {
				issuer = issuerLst.get(position).toString();
			}

			@Override
			public void getGridview3(View veiw, int position) {
				commission = commissionLst.get(position).toString();
			}

			@Override
			public void getGridview4(View veiw, int position) {
				annualRate = annualRateLst.get(position).toString();
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
					requestData("资管", SdefaultType, filterType, infoPage);
					myBaseAdapter.notifyDataSetChanged();
					break;
				case 1:
					filterType = "0";
					SdefaultType = "commissionMax";
					infoPage = 1;
					requestData("资管", SdefaultType, filterType, infoPage);
					myBaseAdapter.notifyDataSetChanged();
					break;
				case 2:
					filterType = "0";
					SdefaultType = "creditLevelMax";
					infoPage = 1;
					requestData("资管", SdefaultType, filterType, infoPage);
					myBaseAdapter.notifyDataSetChanged();
					break;
				case 3:
					filterType = "0";
					SdefaultType = "serviceFeeRateMax";
					infoPage = 1;
					requestData("资管", SdefaultType, filterType, infoPage);
					myBaseAdapter.notifyDataSetChanged();
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
