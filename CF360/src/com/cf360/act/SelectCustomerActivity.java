package com.cf360.act;

import com.cf360.R;
import com.cf360.adapter.SelectCustomerAdapter;
import com.cf360.bean.ResultSelectCustomerContentBean;
import com.cf360.bean.ResultSelectCustomerContentItemBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.ListViewForScrollView;
import com.cf360.view.MyListView;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 选择客户
 * 
 */
public class SelectCustomerActivity extends BaseActivity implements
		OnClickListener {
	private ListViewForScrollView lv;
	private EditText edt_content;
	private TextView btnConfirm;
	protected ResultSelectCustomerContentBean data;
	private MouldList<ResultSelectCustomerContentItemBean> list;
	private SelectCustomerAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_select_customer);
		initTopTitle();
		initView();
		requestData("");
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		list = new MouldList<ResultSelectCustomerContentItemBean>();
		edt_content = (EditText) findViewById(R.id.select_customer_content);
		btnConfirm = (TextView) findViewById(R.id.select_customer_confirm);
		lv = (ListViewForScrollView) findViewById(R.id.select_customer_scrollview);
//		lv.setBackgroundColor(getResources().getColor(R.color.gray_light));
		adapter = new SelectCustomerAdapter(
				SelectCustomerActivity.this,list);
		lv.setAdapter(adapter);
		btnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = edt_content.getText().toString();
				requestData(content);
			}
		});
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(SelectCustomerActivity.this,
						AddScheduleActivity.class);
				if (!TextUtils.isEmpty(data.getUserCustomerList().get(position)
						.getRemark())) {
					intent.putExtra("customer_name", data.getUserCustomerList().get(position)
							.getName());
					intent.putExtra("id", data.getUserCustomerList().get(position)
							.getId());
				}else{
					intent.putExtra("customer_name", data.getUserCustomerList().get(position)
							.getName());
					intent.putExtra("id", data.getUserCustomerList().get(position)
							.getId());
				}
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources()
								.getString(R.string.title_select_customer))
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

	private void requestData(final String customerName) {
		HtmlRequest.getSelectCustomer(SelectCustomerActivity.this,
				customerName, new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							list.clear();
							data = (ResultSelectCustomerContentBean) params.result;
							list.addAll(data.getUserCustomerList());
							adapter.notifyDataSetChanged();
							setListViewHeightBasedOnChildren(SelectCustomerActivity.this, lv, 0);
						} else {
							Toast.makeText(SelectCustomerActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
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

	/**
	* 动态设置ListView的高度
	* @param listView
	*/
	public static void setListViewHeightBasedOnChildren(Context context,ListView listView,int dividerHeight) {
	    if(listView == null) 
	    	return;
	    ListAdapter listAdapter = listView.getAdapter();
	    if (listAdapter == null) {
	        // pre-condition
	        return;
	    }
	    int totalHeight = 0;
	    for (int i = 0; i < listAdapter.getCount(); i++) {
	        View listItem = listAdapter.getView(i, null, listView);
	        listItem.measure(0, 0);
	        totalHeight += (listItem.getMeasuredHeight()+dividerHeight);
	        //System.out.println("main++++++++++===listItem.getMeasuredHeight()=="+listItem.getMeasuredHeight()+"====dividerHeight===="+dividerHeight);
	    }
	   // System.out.println("main++++++++++===totalHeight=="+totalHeight);
	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight + (listView.getDividerHeight() * listAdapter.getCount())+5;
	    
	    listView.setLayoutParams(params);
	}
	
}
