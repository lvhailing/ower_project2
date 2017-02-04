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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.cf360.R;
import com.cf360.adapter.HisScheduledapter;
import com.cf360.bean.ResultAllCustomerContentBean;
import com.cf360.bean.ResultGenZongCustomerContentBean;
import com.cf360.bean.ResultGenZongCustomerContentItemBean;
import com.cf360.bean.ResultHisScheduleContentBean;
import com.cf360.bean.ResultHisScheduleItemBean;
import com.cf360.bean.ResultOrderListContentBean;
import com.cf360.bean.ResultSelectCustomerContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.types.MouldList;
import com.cf360.popupwindow.ExpandTabView;
import com.cf360.popupwindow.ExpandTabViewOne;
import com.cf360.popupwindow.ViewLeft;
import com.cf360.popupwindow.ViewRightForCustomer;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

/**
 * 客户跟踪
 * 
 */
public class CustomerFollowActivity extends BaseActivity implements
		OnClickListener {
	private ExpandTabViewOne expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewLeft viewLeft, viewRight;
	private ViewRightForCustomer viewCenter;
	private ArrayList<String> timeLst;
	private PullToRefreshListView listview;
	private ArrayList<String> mTextArray;
	private MyBaseAdapter adapter;
	protected String time;
	private RelativeLayout layout_all;
	protected ResultSelectCustomerContentBean dataAll;
	protected ResultGenZongCustomerContentBean data;
	protected String status;
	protected String customerId;
	private String timeType;
	private String startTime;
	private int pro_page = 1;
	private int cachePage_pro = pro_page;
	private MouldList<ResultGenZongCustomerContentItemBean> list;
	private String selectTimeValue;
	private ActivityStack stack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_customer_follow);
		initView();
		requestData();
		initTopTitle();
	}

	private void initRequestData() {
		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
//				requestData();
				pro_page = 1;
				initRequestData();
//				insuranceBaseAdapter.notifyDataSetChanged();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
		
		stack = ActivityStack.getActivityManage();
		stack.addActivity(this);
		list = new MouldList<ResultGenZongCustomerContentItemBean>();
		requestSelectData("", "", "", "", pro_page);
		listview.setOnRefreshListener(new OnRefreshListener<ListView>() {
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHeaderShown()) {
					if (pro_page >= 2) {
						requestSelectData(customerId, status, timeType,
								startTime, --pro_page);
					} else {
						requestSelectData(customerId, status, timeType,
								startTime, 1);
					}

				} else {
					requestSelectData(customerId, status, timeType, startTime,
							++pro_page);
				}

			}
		});
		adapter = new MyBaseAdapter(list, this);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ResultGenZongCustomerContentItemBean contentBean = list
						.get(position - 1);

				Intent intent = new Intent(CustomerFollowActivity.this,
						ScheduleDetailsActivity.class); // 需要传contractid status
														// type
				intent.putExtra("id", contentBean.getId());
				startActivity(intent);
			}
		});
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		// requestData("信托",SdefaultType,filterType,infoPage);
		expandTabView = (ExpandTabViewOne) findViewById(R.id.title);
		listview = (PullToRefreshListView) findViewById(R.id.listview);
		layout_all = (RelativeLayout) findViewById(R.id.activity_customer_follow_layout);
		
		
	}

	private void initData() {
		mTextArray = new ArrayList<String>();
		mTextArray.add("全部状态");
		mTextArray.add("时间不限");
		mTextArray.add("全部客户");

		ArrayList<String> arrayListState = new ArrayList<String>();
		arrayListState.add("全部状态");
		arrayListState.add("未结束");
		arrayListState.add("已结束");
		arrayListState.add("取消");
		viewLeft = new ViewLeft(this, arrayListState);

		timeLst = new ArrayList<String>();
		timeLst.add("不限");
		timeLst.add("今天");
		timeLst.add("明天");
		timeLst.add("后天");
		timeLst.add("一周内");

		viewCenter = new ViewRightForCustomer(CustomerFollowActivity.this,
				timeLst, layout_all);
		mViewArray.add(viewLeft);
		mViewArray.add(viewCenter);
		mViewArray.add(viewRight);
		expandTabView.setValue(mTextArray, mViewArray);
		initListener();
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(
				new Action(2, 0, R.color.orange),
				CustomerFollowActivity.this.getResources().getString(
						R.string.title_add_schedule_action));
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(
								R.string.title_mycustomer_follow))
				.setLogo(R.drawable.img_logo, false).setIndicator(R.drawable.back)
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
						switch (id) {
						case 2:
							Intent intent = new Intent(
									CustomerFollowActivity.this,
									AddScheduleActivity.class);
							startActivity(intent);
							break;

						default:
							break;
						}
					}
				});
	}

	private void initListener() {
		viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {
			@Override
			public void getValue(View view, int position) {
				viewCenter.ReturnState1();
				viewCenter.setPreviousData();
				viewRight.ReturnState();
				switch (position) {
				case 0:
					status = "allStatus";
					customerId = "";
					timeType = "";
					startTime = "";
					pro_page = 1;
					requestSelectData(customerId, status, timeType, startTime,
							pro_page);
					viewCenter.ReturnState1();
					viewCenter.setPreviousData();
					viewRight.ReturnState();
					adapter.notifyDataSetChanged();
					onBackPressed();
					break;
				case 1:
					status = "unfinish";
					customerId = "";
					timeType = "";
					startTime = "";
					pro_page = 1;
					requestSelectData(customerId, status, timeType, startTime,
							pro_page);
					viewCenter.ReturnState1();
					viewCenter.setPreviousData();
					viewRight.ReturnState();
					adapter.notifyDataSetChanged();
					onBackPressed();
					break;
				case 2:
					status = "finish";
					customerId = "";
					timeType = "";
					startTime = "";
					pro_page = 1;
					requestSelectData(customerId, status, timeType, startTime,
							pro_page);
					viewCenter.ReturnState1();
					viewCenter.setPreviousData();
					viewRight.ReturnState();
					adapter.notifyDataSetChanged();
					onBackPressed();
					break;
				case 3:
					status = "cancel";
					customerId = "";
					timeType = "";
					startTime = "";
					pro_page = 1;
					requestSelectData(customerId, status, timeType, startTime,
							pro_page);
					viewCenter.ReturnState1();
					viewCenter.setPreviousData();
					viewRight.ReturnState();
					adapter.notifyDataSetChanged();
					onBackPressed();
					break;
				default:
					break;
				}
			}
		});
		viewRight.setOnSelectListener(new ViewLeft.OnSelectListener() {
			@Override
			public void getValue(View view, int position) {
				viewCenter.ReturnState1();
				viewCenter.setPreviousData();
				viewLeft.ReturnState();
				if (position == 0) {
					customerId = "allCustomer";
					status = "";
					timeType = "";
					startTime = "";
					pro_page = 1;
					requestSelectData(customerId, status, timeType, startTime,
							pro_page);
				} else {
					customerId = dataAll.getUserCustomerList()
							.get(position - 1).getId();
					status = "";
					timeType = "";
					startTime = "";
					pro_page = 1;
					requestSelectData(customerId, status, timeType, startTime,
							pro_page);
				}
				viewCenter.ReturnState1();
				viewCenter.setPreviousData();
				viewLeft.ReturnState();
				adapter.notifyDataSetChanged();
				onBackPressed();
			}
		});
		viewCenter
				.setOnSelectListener(new ViewRightForCustomer.OnSelectListener() {

					@Override
					public void getValue(String distance, String showText) {
					}

					@Override
					public void getCancle() {
						onBackPressed();

					}

					@Override
					public void getConfirm() {
						if ("指定时间".equals(selectTimeValue)) {
							customerId = "";
							status = "";
							startTime = "";
							pro_page = 1;
							requestSelectData(customerId, status, timeType,
									startTime, pro_page);
						} else {
							customerId = "";
							status = "";
							timeType = "";
							startTime = selectTimeValue;
							pro_page = 1;
							requestSelectData(customerId, status, timeType,
									startTime, pro_page);
						}
						viewLeft.ReturnState();
						viewRight.ReturnState();
						onBackPressed();
					}

					@Override
					public void getGridview(View veiw, int position) {
						time = timeLst.get(position).toString();
						if (time.equals("不限")) {
							timeType = "0";
						} else if (time.equals("今天")) {
							timeType = "1";
						} else if (time.equals("明天")) {
							timeType = "2";
						} else if (time.equals("后天")) {
							timeType = "3";
						} else if (time.equals("一周内")) {
							timeType = "4";
						}
					}

					@Override
					public void getSelectTime(String selectTimeValue) {
						CustomerFollowActivity.this.selectTimeValue = selectTimeValue;
					}

				});

	}

	private void requestData() {
		HtmlRequest.getAllCustomer(CustomerFollowActivity.this,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							dataAll = (ResultSelectCustomerContentBean) params.result;
							if (dataAll != null) {
								ArrayList<String> arrayListCustomer = new ArrayList<String>();
								if (dataAll.getUserCustomerList() != null) {
									if (dataAll.getUserCustomerList().size() != 0) {
										arrayListCustomer.add("全部客户");
										for (int i = 0; i < dataAll
												.getUserCustomerList().size(); i++) {
											if (TextUtils.isEmpty(dataAll
													.getUserCustomerList()
													.get(i).getRemark())) {
												arrayListCustomer.add(dataAll
														.getUserCustomerList()
														.get(i).getName());
											} else {
												arrayListCustomer
														.add(dataAll
																.getUserCustomerList()
																.get(i)
																.getName()
																+ "("
																+ dataAll
																		.getUserCustomerList()
																		.get(i)
																		.getRemark()
																+ ")");
											}
										}
										viewRight = new ViewLeft(
												CustomerFollowActivity.this,
												arrayListCustomer);
									} else {
										arrayListCustomer.add("全部客户");
										viewRight = new ViewLeft(
												CustomerFollowActivity.this,
												arrayListCustomer);
									}
								} else {
									arrayListCustomer.add("全部客户");
									viewRight = new ViewLeft(
											CustomerFollowActivity.this,
											arrayListCustomer);
								}
							}
							initData();
						} else {
							Toast.makeText(CustomerFollowActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	private void requestSelectData(final String customerId, String status,
			String timeType, String startTime, int page) {
		cachePage_pro = pro_page;
		data = new ResultGenZongCustomerContentBean();
		HtmlRequest.getGenzongCustomer(CustomerFollowActivity.this, customerId,
				status, timeType, startTime, page + "",
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							data = (ResultGenZongCustomerContentBean) params.result;
							if (data.getGenZongCustomerList().size() == 0
									&& pro_page != 1) {
								Toast.makeText(CustomerFollowActivity.this,
										"已经到最后一页", Toast.LENGTH_SHORT).show();
								pro_page = cachePage_pro - 1;
								listview.getRefreshableView()
										.smoothScrollToPositionFromTop(0, 80,
												100);
								listview.onRefreshComplete();
							} else {
								list.clear();
								list.addAll(data.getGenZongCustomerList());
								adapter.notifyDataSetChanged();
								listview.postDelayed(new Runnable() {
									@Override
									public void run() {
										listview.onRefreshComplete();
									}
								}, 1000);
								listview.getRefreshableView()
										.smoothScrollToPositionFromTop(0, 80,
												100);
							}

						} else {
							netFail = true;
							listview.onRefreshComplete();
							Toast.makeText(CustomerFollowActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}

					}
				});
	}

	@Override
	public void onBackPressed() {

		if (!expandTabView.onPressBack()) {
			finish();
		}

	}

	class MyBaseAdapter extends BaseAdapter {
		private Context mContext;
		private MouldList<ResultGenZongCustomerContentItemBean> resultListViewItem;

		public MyBaseAdapter(
				MouldList<ResultGenZongCustomerContentItemBean> resultListViewItem,
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
				return resultListViewItem.size();
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
			ResultGenZongCustomerContentItemBean bean = resultListViewItem
					.get(position);
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(this.mContext).inflate(
						R.layout.customer_follow_item, null, false);
				holder.txt_do_what = (TextView) convertView
						.findViewById(R.id.customer_follow_txt_do_what);
				holder.txt_time = (TextView) convertView
						.findViewById(R.id.customer_follow_txt_time);
				holder.txt_name = (TextView) convertView
						.findViewById(R.id.customer_follow_txt_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.txt_do_what.setText(bean.getTopic());
			holder.txt_time.setText(bean.getStartTime() + "至"
					+ bean.getEndTime());
			holder.txt_name.setText(bean.getCustomerName());

			return convertView;
		}

		private class ViewHolder {
			TextView txt_do_what;
			TextView txt_time;
			TextView txt_name;

		}

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		pro_page = 1;
		initRequestData();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {

	}

}
