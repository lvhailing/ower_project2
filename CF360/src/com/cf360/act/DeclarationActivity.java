package com.cf360.act;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.cf360.R;
import com.cf360.adapter.ContractAdapter;
import com.cf360.adapter.DeclarationAdapter;
import com.cf360.bean.ResultContractListContentBean;
import com.cf360.bean.ResultContractListItemBean;
import com.cf360.bean.ResultDeclarationListContentBean;
import com.cf360.bean.ResultDeclarationListItemBean;
import com.cf360.bean.ResultOrderListContentBean;
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

/**
 * 我的报单
 * 
 */
public class DeclarationActivity extends BaseActivity implements
		OnClickListener {
	private PullToRefreshListView listView;
	private int pro_page = 1;
	private int cachePage_pro = pro_page;
	private MouldList<ResultDeclarationListItemBean> list;
	private ResultDeclarationListContentBean data;
	private DeclarationAdapter adapter;
	private TextView txtApplyDeclaration;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_declaration);
		initView();
//		initData();
		initTopTitle();
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(
				new Action(2, 0, R.color.orange),
				DeclarationActivity.this.getResources().getString(
						R.string.title_apply_declaration));
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_declaration))
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
		method(2);

	}

	public void method(int declaration){
		txtApplyDeclaration = (TextView) findViewById(declaration);
		txtApplyDeclaration.setOnClickListener(this);
		txtApplyDeclaration.setClickable(false);
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

	private void initData() {
		list = new MouldList<ResultDeclarationListItemBean>();
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
		adapter = new DeclarationAdapter(this, list);
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
				ResultDeclarationListItemBean declarationListItemBean = list
						.get(position - 1);
				Intent intent = new Intent(DeclarationActivity.this,
						DeclarationDetailsActivity.class);
				intent.putExtra("id", declarationListItemBean.getID());
				intent.putExtra("state", declarationListItemBean.getSTATUS());
				intent.putExtra("type",
						declarationListItemBean.getPRODUCTCATEGORY());
				intent.putExtra("productName",
						declarationListItemBean.getPRODUCTNAME());
				startActivity(intent);
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
		data = new ResultDeclarationListContentBean();
		HtmlRequest.getDeclarationList(DeclarationActivity.this, pro_page,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						if (params.result != null) {
							data = (ResultDeclarationListContentBean) params.result;
							if (data != null && data.getOrderList() != null) {
								if (data.getOrderList().size() == 0
										&& pro_page != 1) {
									Toast.makeText(DeclarationActivity.this,
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
							if (data.getAuditStatus() != null) {
								txtApplyDeclaration.setClickable(true);
							}
						} else {
							netFail = true;
							listView.onRefreshComplete();
							Toast.makeText(DeclarationActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
						DeclarationActivity.this.stopLoading();

					}
				});
	}
	private void selectData() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setNegativeButton("非保险报单", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent();
				i.setClass(DeclarationActivity.this,
						ApplyDeclarationNotInsuranceActivity.class);
				DeclarationActivity.this.startActivityForResult(i, 3);
			}
		});
		builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.setNeutralButton("保险报单", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent();
				i.setClass(DeclarationActivity.this,
						ApplyDeclarationActivity.class);
				DeclarationActivity.this.startActivityForResult(i, 3);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case 2:
			if (PreferenceUtil.getUserType().equals("corp")) {
				selectData();
				/*Intent i = new Intent();
				i.setClass(DeclarationActivity.this,
						ApplyDeclarationActivity.class);
				DeclarationActivity.this.startActivityForResult(i, 3);*/
			} else {
				if (data.getAuditStatus() != null) {
					if (data.getAuditStatus().equals("success")) {
						selectData();
						/*Intent i = new Intent();
						i.setClass(DeclarationActivity.this,
								ApplyDeclarationActivity.class);
						DeclarationActivity.this.startActivityForResult(i, 3);*/
					} else {
						Toast.makeText(DeclarationActivity.this, "用户还未认证",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(DeclarationActivity.this, "用户还未认证",
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
	 * data);
	 * 
	 * }
	 */
}
