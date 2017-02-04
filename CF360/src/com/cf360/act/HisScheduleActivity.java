package com.cf360.act;

import com.cf360.R;
import com.cf360.adapter.HisBaoDanAdapter;
import com.cf360.adapter.HisScheduledapter;
import com.cf360.bean.ResultBankListMessageContentBean;
import com.cf360.bean.ResultHisBaoDanContentBean;
import com.cf360.bean.ResultHisBaoDanItemBean;
import com.cf360.bean.ResultHisScheduleContentBean;
import com.cf360.bean.ResultHisScheduleItemBean;
import com.cf360.bean.ResultSentSMSContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 他的日程
 * 
 */
public class HisScheduleActivity extends BaseActivity implements
		OnClickListener {
	private PullToRefreshListView listView;
	private HisScheduledapter adapter;
	private int pro_page = 1;
	private int cachePage_pro = pro_page;
	private ResultHisScheduleContentBean data;
	private MouldList<ResultHisScheduleItemBean> list;
	private String customerId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_his_schedule);
		initTopTitle();
		initView();
	}

	private void initView() {
		customerId=getIntent().getStringExtra("id");
		listView = (PullToRefreshListView) findViewById(R.id.listview);

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);
		
		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
				initData();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_his_schedule))
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

					}
				});
	}

	private void requestData(String customerId,int page) {
		cachePage_pro = pro_page;
		data = new ResultHisScheduleContentBean();
		HtmlRequest.getHisSchedule(HisScheduleActivity.this, customerId,pro_page+"",
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						if (params.result != null) {
							data = (ResultHisScheduleContentBean) params.result;
							if (data.getUserCustomerScheduleList() != null) {
								if (data.getUserCustomerScheduleList().size() == 0
										&& pro_page != 1) {
									Toast.makeText(HisScheduleActivity.this,
											"已经到最后一页", Toast.LENGTH_SHORT)
											.show();
									pro_page = cachePage_pro - 1;
									listView.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													80, 100);
									listView.onRefreshComplete();
								} else {
									list.clear();
									list.addAll(data
											.getUserCustomerScheduleList());
									adapter.notifyDataSetChanged();
									listView.postDelayed(new Runnable() {
										@Override
										public void run() {
											listView.onRefreshComplete();
										}
									}, 1000);
									listView.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													80, 100);
								}

							}
						} else {
							netFail = true;
							listView.onRefreshComplete();
							Toast.makeText(HisScheduleActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
						HisScheduleActivity.this.stopLoading();

					}
				});
	}

	private void initData() {
		list = new MouldList<ResultHisScheduleItemBean>();
		requestData(customerId,1);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHeaderShown()) {
					if (pro_page >= 2) {
						requestData(customerId,pro_page--);
					} else {
						requestData(customerId,1);
					}

				} else {
					requestData(customerId,pro_page++);
				}

			}
		});
		adapter = new HisScheduledapter(this, list);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ResultHisScheduleItemBean contentBean = list.get(position - 1);
				
				  Intent intent = new Intent(HisScheduleActivity.this,
						  ScheduleDetailsActivity.class); // 需要传contractid status type
				  intent.putExtra("id", contentBean.getId());
				 startActivity(intent);
				 
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		initData();
		super.onResume();
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
		switch (v.getId()) {
		default:
			break;
		}

	}

}
