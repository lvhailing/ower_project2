package com.cf360.act;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.adapter.ContractAdapter;
import com.cf360.bean.ResultContractListContentBean;
import com.cf360.bean.ResultContractListItemBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 我的合同
 * 
 */
public class ContractActivity extends BaseActivity implements OnClickListener {
	private PullToRefreshListView listView;
	private ContractAdapter adapter;
	private MouldList<ResultContractListItemBean> list;
	private ResultContractListContentBean data;
	private int pro_page = 1;
	private int cachePage_pro = pro_page;
	private TextView txtApplyContract;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_contract);
		initTopTitle();
		initView();
		// initData();

	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(new Action(2, 0, R.color.orange), ContractActivity.this
				.getResources().getString(R.string.title_apply_contract));
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_contract))
				.setLogo(R.drawable.img_logo, false).setIndicator(R.drawable.back)
				.showMore(true).setOnActionListener(new OnActionListener() {

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
//		int contractNum = 2;
		methed(2);
	}
	public void methed(int contractNum){
		txtApplyContract = (TextView) findViewById(contractNum);
		txtApplyContract.setOnClickListener(this);
		txtApplyContract.setClickable(false);
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
//				insuranceBaseAdapter.notifyDataSetChanged();
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
		data = new ResultContractListContentBean();
		HtmlRequest.getContractList(ContractActivity.this, pro_page,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						if (params.result != null) {
							data = (ResultContractListContentBean) params.result;
							if (data.getContractAppList() != null) {
								if (data.getContractAppList().size() == 0
										&& pro_page != 1) {
									Toast.makeText(ContractActivity.this,
											"已经到最后一页", Toast.LENGTH_SHORT)
											.show();
									pro_page = cachePage_pro - 1;
									listView.getRefreshableView()
											.smoothScrollToPositionFromTop(0,
													80, 100);
									listView.onRefreshComplete();
								} else {
									list.clear();
									list.addAll(data.getContractAppList());
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
							if (data.getAuditStatus() != null) {
								txtApplyContract.setClickable(true);
							}
						} else {
							netFail = true;
							listView.onRefreshComplete();
							Toast.makeText(ContractActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
						ContractActivity.this.stopLoading();

					}
				});
	}

	private void initData() {
		list = new MouldList<ResultContractListItemBean>();
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
		adapter = new ContractAdapter(this, list);
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
				ResultContractListItemBean contentBean = list.get(position - 1);
				Intent intent = new Intent(ContractActivity.this,
						ContractDetailsActivity.class);
				// 需要传contractid status type
				intent.putExtra("state", contentBean.getSTATUS());
				intent.putExtra("contractId", contentBean.getCONTRACTID());
				intent.putExtra("type", contentBean.getTYPE());
				intent.putExtra("productName", contentBean.getPRODUCTNAME());
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
				i.setClass(ContractActivity.this, ApplyContractActivity.class);
				ContractActivity.this.startActivityForResult(i, 3);
			} else {
				if (data.getAuditStatus() != null) {
					if (data.getAuditStatus().equals("success")) {
						Intent i = new Intent();
						i.setClass(ContractActivity.this,
								ApplyContractActivity.class);
						ContractActivity.this.startActivityForResult(i, 3);
					}else{
						Toast.makeText(ContractActivity.this, "用户还未认证",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(ContractActivity.this, "用户还未认证",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;

		default:
			break;
		}
	}
	/*
	 * @Override protected void onActivityResult(int requestCode, int
	 * resultCode, Intent data) { if (resultCode == 2) { if (data != null) {
	 * requestData(1); } } super.onActivityResult(requestCode, resultCode,
	 * data); }
	 */
}
