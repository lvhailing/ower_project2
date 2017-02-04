package com.cf360.act;

import com.cf360.R;
import com.cf360.adapter.CustomerAdapter;
import com.cf360.bean.ResultMyCustomerContentBean;
import com.cf360.bean.ResultMyCustomerContentItemBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.AssortView;
import com.cf360.view.AssortView.OnTouchAssortListener;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerActivity extends BaseActivity {

	private ExpandableListView eListView;
	private AssortView assortView;
	private MouldList<ResultMyCustomerContentItemBean> customerList;
	private CustomerAdapter adapter;
	private MouldList<ResultMyCustomerContentBean> customerBean;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_myperson_customer);
		initTopTitle();
		initView();
//		requestData();
	}

	private void initData() {
		
		for (int i = 0; i < customerBean.size(); i++) {
			if(customerBean.get(i).getResult().size()==0){
				customerBean.remove(i);
			}
		}
		for (int i = 0; i < customerBean.size(); i++) {
			if(customerBean.get(i).getShortName().equals("*")){
				customerBean.get(i).setShortName("#");
			}
		}
		adapter = new CustomerAdapter(CustomerActivity.this, customerList,customerBean);
		eListView.setAdapter(adapter);

		// 展开所有
		for (int i = 0, length = adapter.getGroupCount(); i < length; i++) {
			eListView.expandGroup(i);
		}

		// 字母按键回调
		assortView.setOnTouchAssortListener(new OnTouchAssortListener() {

			View layoutView = LayoutInflater.from(CustomerActivity.this)
					.inflate(R.layout.alert_dialog_menu_layout, null);
			TextView text = (TextView) layoutView.findViewById(R.id.content);
			PopupWindow popupWindow;

			public void onTouchAssortListener(String str) {
//				int index = adapter.getAssort().getHashList().indexOfKey(str);
				int index = adapter.getIndex(str);
				
				if (index != -1) {
					eListView.setSelectedGroup(index);
					;
				}
				if (popupWindow != null) {
					text.setText(str);
				} else {
					popupWindow = new PopupWindow(layoutView, 80, 80, false);
					// 显示在Activity的根视图中心
					popupWindow.showAtLocation(getWindow().getDecorView(),
							Gravity.CENTER, 0, 0);
				}
				text.setText(str);
			}

			public void onTouchAssortUP() {
				if (popupWindow != null)
					popupWindow.dismiss();
				popupWindow = null;
			}
		});

	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		customerBean = new MouldList<ResultMyCustomerContentBean>();
		
		customerList = new MouldList<ResultMyCustomerContentItemBean>();
		eListView = (ExpandableListView) findViewById(R.id.elist);
		assortView = (AssortView) findViewById(R.id.assort);
		eListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				Intent i_customer = new Intent();
//				i_customer.setClass(CustomerActivity.this,
//						CustomerInfoActivity.class);
//				i_customer.putExtra("id", customerList.get(arg2).getID());
//				startActivity(i_customer);
			}
		});
		
		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
				initData();
//				insuranceBaseAdapter.notifyDataSetChanged();
				requestData();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
	}

	public void requestData() {
		HtmlRequest.getUserCustomerList(CustomerActivity.this,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						MouldList<ResultMyCustomerContentBean> data = (MouldList<ResultMyCustomerContentBean>) params.result;
						if (data != null) {
//							customerList.clear();
//							for (int i = 0; i < data.size(); i++) {
//								customerList.addAll(data.get(i).getResult());
//							}
							customerBean = data;
							initData();
						} else {
							netFail = true;
							Toast.makeText(CustomerActivity.this, "加载失败，请确认网络通畅",
									Toast.LENGTH_LONG).show();
						}
						// setView();
					}
				});
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(new Action(2, 0, R.color.blue_light),CustomerActivity.this.getResources().getString(R.string.customer_add_newcustomer));
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_mycustomer))
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
						Intent i_search = new Intent();
						i_search.setClass(CustomerActivity.this, CustomerAddActivity.class);//
						startActivity(i_search);
						break;

					default:
						break;
						}
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
		requestData();
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
