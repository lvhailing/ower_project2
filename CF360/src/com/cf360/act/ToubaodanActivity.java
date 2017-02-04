package com.cf360.act;

import java.util.ArrayList;

import com.cf360.R;
import com.cf360.adapter.ContractAdapter;
import com.cf360.adapter.ToubaodanAdapter;
import com.cf360.bean.ResultContractListContentBean;
import com.cf360.bean.ResultContractListItemBean;
import com.cf360.bean.ResultToubaodanListContentBean;
import com.cf360.bean.ResultToubaodanListItemBean;
import com.cf360.bean.TestBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 我的投保单
 * 
 */
public class ToubaodanActivity extends BaseActivity implements OnClickListener {
	private PullToRefreshListView listView;
	private ToubaodanAdapter adapter;
	private MouldList<ResultToubaodanListItemBean> list;
	private ResultToubaodanListContentBean data;
	private int pro_page = 1;
	private int cachePage_pro = pro_page;
	private TextView txtApplyToubaodan;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_toubaodan);
		initView();
		//initData();
		initTopTitle();
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(
				new Action(2, 0, R.color.orange),
				ToubaodanActivity.this.getResources().getString(
						R.string.title_apply_toubaodan));
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_toubaodan))
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
		method(2);

	}
	public void method(int num){
		txtApplyToubaodan = (TextView) findViewById(num);
		txtApplyToubaodan.setOnClickListener(this);
		txtApplyToubaodan.setClickable(false);
	}

	private void initView() {
		listView = (PullToRefreshListView) findViewById(R.id.listview);
		ActivityStack stack=ActivityStack.getActivityManage();
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

	@Override
	protected void onResume() {
		initData();
		super.onResume();
	}

	private void requestData(int page) {
		cachePage_pro = pro_page;
		data = new ResultToubaodanListContentBean();
		HtmlRequest.getToubaodanList(ToubaodanActivity.this, pro_page,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						if (params.result != null) {
							data = (ResultToubaodanListContentBean) params.result;
							if (data.getPolicyListForApp() != null) {
								if (data.getPolicyListForApp().size() == 0
										&& pro_page != 1) {
									Toast.makeText(ToubaodanActivity.this,
											"已经到最后一页", Toast.LENGTH_SHORT)
											.show();
									pro_page = cachePage_pro - 1;
									listView.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													80, 100);
									listView.onRefreshComplete();
								} else {
									list.clear();
									list.addAll(data.getPolicyListForApp());
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
							if (data.getAuditStatus() != null
									&& data.getInsuranceAgentAuditStatus() != null) {
								txtApplyToubaodan.setClickable(true);
							}
						} else {
							netFail = true;
							listView.onRefreshComplete();
							Toast.makeText(ToubaodanActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
						ToubaodanActivity.this.stopLoading();

					}
				});
	}

	private void initData() {
		list = new MouldList<ResultToubaodanListItemBean>();
		requestData(1);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHeaderShown()) {
					if (pro_page >= 2) {
						requestData(pro_page--);
					} else {
						requestData(1);
					}

				} else {
					requestData(pro_page++);
				}

			}
		});
		adapter = new ToubaodanAdapter(this, list);
		/*try {
			new Thread().sleep(1000);*/
			listView.setAdapter(adapter);
		/*} catch (InterruptedException e) {
			e.printStackTrace();
		}*/

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ResultToubaodanListItemBean contentBean = list
						.get(position - 1);
				Intent intent = new Intent(ToubaodanActivity.this,
						ToubaodanDetailsActivity.class);
				// 需要传contractid status type
				intent.putExtra("policyOrderId", contentBean.getPOLICYORDERID());
				intent.putExtra("productName", contentBean.getPRODUCTNAME());
				intent.putExtra("status", contentBean.getSTATUS());
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case 2:
			if (PreferenceUtil.getUserType().equals("corp")) {
				Intent i = new Intent();
				i.setClass(ToubaodanActivity.this, ApplyToubaodanActivity.class);
				ToubaodanActivity.this.startActivityForResult(i, 3);
			} else {
				if (data.getAuditStatus() != null
						&& data.getInsuranceAgentAuditStatus() != null) {
					if (data.getAuditStatus().equals("success")
							&& data.getInsuranceAgentAuditStatus().equals(
									"success")) {
						Intent i = new Intent();
						i.setClass(ToubaodanActivity.this,
								ApplyToubaodanActivity.class);
						ToubaodanActivity.this.startActivityForResult(i, 3);
					} else {
						Toast.makeText(ToubaodanActivity.this,
								"用户还未认证或者保险代理未认证", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(ToubaodanActivity.this, "用户还未认证或者保险代理未认证",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;

		default:
			break;
		}
	}

	/*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 2) {
			if (data != null) {
				requestData(1);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}*/
}
