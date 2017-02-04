package com.cf360.act;

import com.cf360.R;
import com.cf360.adapter.MypersonInfoAdapter;
import com.cf360.adapter.SearchProductAdapter;
import com.cf360.bean.ResultSearchProductContentBean;
import com.cf360.bean.SearchProductContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchProductActivity extends BaseActivity {
	private EditText et_search_product_name;
	private TextView tv_search_sure,tv_search_result_message;
	private PullToRefreshListView lv_search_product;
	private int searchProductPage = 1;
	private SearchProductAdapter searchAdapter;
	private MouldList<SearchProductContentBean> list;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		baseSetContentView(R.layout.activity_search_product);
		initTopTitle();
		initView();
//		initData();
		
	}
	
	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		list = new MouldList<SearchProductContentBean>();
		et_search_product_name = (EditText) findViewById(R.id.et_search_product_name);
		tv_search_sure = (TextView) findViewById(R.id.tv_search_sure);
		lv_search_product = (PullToRefreshListView) findViewById(R.id.lv_search_product);
		tv_search_result_message = (TextView) findViewById(R.id.tv_search_result_message);
		tv_search_sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				searchProductPage = 1;
				if(TextUtils.isEmpty(et_search_product_name.getText().toString())){
					Toast.makeText(SearchProductActivity.this, "请您输入搜索信息", Toast.LENGTH_LONG).show();
				}else{
					initData();
				}
			}
		});
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void initData() {
		
		searchAdapter = new SearchProductAdapter(SearchProductActivity.this,list);
		lv_search_product.setAdapter(searchAdapter);
		
		requestSearchProduct(et_search_product_name.getText().toString(),"1");
//		requestSearchProduct("信托","1");
		
		lv_search_product.setOnRefreshListener(new OnRefreshListener<ListView>() {
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHeaderShown()) {
					if (searchProductPage >= 2) {
						requestSearchProduct(et_search_product_name.getText().toString(),--searchProductPage+"");
					} else {
						requestSearchProduct(et_search_product_name.getText().toString(),"1");
					}

				} else {
					requestSearchProduct(et_search_product_name.getText().toString(),++searchProductPage+"");
				}
			}
		});
		lv_search_product.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent();
				if(list.get(position-1).getCATEGORY().equals("信托")){
					intent.setClass(SearchProductActivity.this,TrustDetailsActivity.class);
					intent.putExtra("Mtrustid", list.get(position-1).getID());
					
					String progressNum = (int)(Double.parseDouble(list.get(position-1).getRECRUITMENTPROCESS())*100)+"";
					intent.putExtra("ProgressBar", progressNum);
					
					intent.putExtra("Amount", list.get(position-1).getAMOUNT());
				}else if(list.get(position-1).getCATEGORY().equals("资管")){
					intent.setClass(SearchProductActivity.this,ZiGuanItemActivity.class);
					intent.putExtra("Mtrustid", list.get(position-1).getID());
					
					String progressNum = (int)(Double.parseDouble(list.get(position-1).getRECRUITMENTPROCESS())*100)+"";
					intent.putExtra("ProgressBar", progressNum);
					
					intent.putExtra("Amount", list.get(position-1).getAMOUNT());
				}else if(list.get(position-1).getCATEGORY().equals("ygsm")){
					intent.setClass(SearchProductActivity.this,SunshineActivity.class);
					intent.putExtra("Sunshine", list.get(position-1).getID());
				}else if(list.get(position-1).getCATEGORY().equals("smgq")){
					intent.setClass(SearchProductActivity.this,EquityItmeActivity.class);
					intent.putExtra("EquityId", list.get(position-1).getID());
				}else {
					intent.setClass(SearchProductActivity.this,InsuranceItmeActivity.class);
					intent.putExtra("id", list.get(position-1).getID());
				}
				startActivity(intent);

			}
		});
		
	}

	private void requestSearchProduct(String productName,String pageNo) {

		
		HtmlRequest.getSearchProduct(SearchProductActivity.this, productName,pageNo,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						ResultSearchProductContentBean bean = (ResultSearchProductContentBean) params.result;
						if (bean!= null) {

								if(TextUtils.isEmpty(bean.getAuditStatus())){
									PreferenceUtil.setAuditStatus(null);
								}else{
									PreferenceUtil.setAuditStatus(bean.getAuditStatus());
								}
							
							if (bean.getProductList().size()==0&&searchProductPage!=1) {
								Toast.makeText(SearchProductActivity.this, "已经到最后一页",
										Toast.LENGTH_SHORT).show();
								searchProductPage--;
								searchAdapter.notifyDataSetChanged();
								lv_search_product.postDelayed(new Runnable() {
									@Override
									public void run() {
										lv_search_product.onRefreshComplete();
									}
								}, 1000);
								lv_search_product.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
							} else {
								list.clear();
								list.addAll(bean.getProductList());
								searchAdapter.notifyDataSetChanged();
								tv_search_result_message.setText(bean.getSearchRemark());
								lv_search_product.postDelayed(new Runnable() {
									@Override
									public void run() {
										lv_search_product.onRefreshComplete();
									}
								}, 1000);
								lv_search_product.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
							}
							
							
//							System.out.println(bean.getProductName());
							
						} else {
							Toast.makeText(SearchProductActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}

					}
				});
	
		
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_search_product))
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
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
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
