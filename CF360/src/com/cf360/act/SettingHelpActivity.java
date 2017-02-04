package com.cf360.act;

import java.util.ArrayList;

import com.cf360.R;
import com.cf360.adapter.HotProductAdapter;
import com.cf360.bean.ResultHelpListContentBean;
import com.cf360.bean.ResultHotProductContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingHelpActivity extends BaseActivity{
	
	private ListView lv_setting_help;
//	private ArrayList<String> list;
	private MouldList<ResultHelpListContentBean> list;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_setting_help);
		initTopTitle();
		initView();
		initData();
		
	}
	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.setting_help))
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
	private void initData() {
//		test();
		requestHelpListData();
		
		lv_setting_help.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i_helpDetail = new Intent();
				i_helpDetail.setClass(SettingHelpActivity.this, SettingHelpDetailActivity.class);
				i_helpDetail.putExtra("pId", list.get(arg2).getPID());
				startActivity(i_helpDetail);
			}
			
		});
	}
	
	//帮助中心列表
	public void requestHelpListData(){
		int page = 1;
		HtmlRequest.getHelpList(SettingHelpActivity.this, page,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						MouldList<ResultHelpListContentBean> bean = (MouldList<ResultHelpListContentBean>) params.result;
						if (bean!= null) {
							list = bean;
							lv_setting_help.setAdapter(new MyAdapter());
						} else {
							netFail = true;
							Toast.makeText(SettingHelpActivity.this, "加载失败，请确认网络通畅",
									Toast.LENGTH_LONG).show();
						}

					}
				});
	}

//	private void test() {
//		
//		list = new ArrayList<String>();
//		for(int i=0;i<5;i++){
//			list.add("help"+i);
//		}
//	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		lv_setting_help = (ListView) findViewById(R.id.lv_setting_help);
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

	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return getItem(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if(convertView==null){
				holder = new Holder();
				LayoutInflater inflater = LayoutInflater.from(SettingHelpActivity.this);
				convertView = inflater.inflate(R.layout.activity_setting_help_title, null);
				holder.tv_setting_help_title = (TextView) convertView.findViewById(R.id.tv_setting_help_title);
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			holder.tv_setting_help_title.setText(list.get(position).getPTOPIC());
			
			return convertView;
		}
		class Holder{
			TextView tv_setting_help_title;
		}
		
	}
}
