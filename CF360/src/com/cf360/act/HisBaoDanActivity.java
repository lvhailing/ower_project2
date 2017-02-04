package com.cf360.act;

import com.cf360.R;
import com.cf360.adapter.ContractAdapter;
import com.cf360.adapter.HisBaoDanAdapter;
import com.cf360.bean.ResultBankListMessageContentBean;
import com.cf360.bean.ResultContractListContentBean;
import com.cf360.bean.ResultContractListItemBean;
import com.cf360.bean.ResultDeclarationListItemBean;
import com.cf360.bean.ResultHisBaoDanContentBean;
import com.cf360.bean.ResultHisBaoDanItemBean;
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
 * 他成交的报单
 * 
 */
public class HisBaoDanActivity extends BaseActivity {
	private PullToRefreshListView listView;
	private HisBaoDanAdapter adapter;
	private int pro_page = 1;
	private int cachePage_pro = pro_page;
	private ResultHisBaoDanContentBean data;
	private MouldList<ResultHisBaoDanItemBean> list;
	private String idcard;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_his_baodan);
		initTopTitle();
		initView();
	}

	private void initView() {


		idcard=getIntent().getStringExtra("idcard");
		listView = (PullToRefreshListView) findViewById(R.id.listview);

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);
		
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

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_his_baodan))
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

	private void requestData(String idcard, int page) {
		cachePage_pro = pro_page;
		data = new ResultHisBaoDanContentBean();
		if(TextUtils.isEmpty(idcard)){
			idcard="0";
		}
		HtmlRequest.getHisBaoDan(HisBaoDanActivity.this, idcard, pro_page+"",
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						if (params.result != null) {
							data = (ResultHisBaoDanContentBean) params.result;
							if (data.getOrderList() != null) {
								if (data.getOrderList().size() == 0
										&& pro_page != 1) {
									Toast.makeText(HisBaoDanActivity.this,
											"已经到最后一页", Toast.LENGTH_SHORT)
											.show();
									pro_page = cachePage_pro - 1;
									listView.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													80, 100);
									listView.onRefreshComplete();
								} else {
									list.clear();
									list.addAll(data.getOrderList());
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
							Toast.makeText(HisBaoDanActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
						HisBaoDanActivity.this.stopLoading();

					}
				});
	}

	private void initData() {
		list = new MouldList<ResultHisBaoDanItemBean>();
		requestData(idcard, 1);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHeaderShown()) {
					if (pro_page >= 2) {
						requestData(idcard, pro_page--);
					} else {
						requestData(idcard, 1);
					}

				} else {
					requestData(idcard, pro_page++);
				}

			}
		});
		adapter = new HisBaoDanAdapter(this, list);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				ResultHisBaoDanItemBean declarationListItemBean = list
						.get(position - 1);
				Intent intent = new Intent(HisBaoDanActivity.this,
						DeclarationDetailsActivity.class);
				intent.putExtra("id", declarationListItemBean.getID());
//				intent.putExtra("state", declarationListItemBean.getSTATUS());
				intent.putExtra("type",
						declarationListItemBean.getPRODUCTCATEGORY());
				intent.putExtra("productName",
						declarationListItemBean.getPRODUCTNAME());
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

}
