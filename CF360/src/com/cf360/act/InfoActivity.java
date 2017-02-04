package com.cf360.act;

import java.util.ArrayList;

import com.cf360.R;
import com.cf360.adapter.MypersonInfoAdapter;
import com.cf360.bean.InfoDataContentBean;
import com.cf360.bean.MyInfoItemBean;
import com.cf360.bean.ResultInfoDataContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout rl_myperson_info_all, rl_myperson_info_ing,
			rl_myperson_info_ed;
	private TextView tv_myperson_info_all_name, tv_myperson_info_all_line,
			tv_myperson_info_ing_name, tv_myperson_info_ing_line,
			tv_myperson_info_ed_name, tv_myperson_info_ed_line;
	private PullToRefreshListView lv_myperson_info;
	private ArrayList<MyInfoItemBean> list;
	private MouldList<InfoDataContentBean> infolist;
	private MypersonInfoAdapter infoAdapter;
	private int infoPage = 1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		baseSetContentView(R.layout.activity_myperson_info);
		initTopTitle();
		initView();
		initDefault();
	}

	private void initDefault() {
		setTitleStyle(R.id.rl_myperson_info_all);
		initData("0");
	}

	private void initData(final String CATEGORY) {

		infoAdapter = new MypersonInfoAdapter(InfoActivity.this, infolist);
		lv_myperson_info.setAdapter(infoAdapter);
		requestInfoData(CATEGORY,"1");
		lv_myperson_info.setOnRefreshListener(new OnRefreshListener<ListView>() {
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHeaderShown()) {
					if (infoPage >= 2) {
						requestInfoData(CATEGORY,(--infoPage)+"");
					} else {
						requestInfoData(CATEGORY,1+"");
					}

				} else {
					requestInfoData(CATEGORY,(++infoPage)+"");
				}
			}
		});
		
		lv_myperson_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,final int arg2,
					long arg3) {
				Intent i_info_detail = new Intent();
				i_info_detail.setClass(InfoActivity.this, InfoDetailActivity.class);
				i_info_detail.putExtra("incotype", infolist.get(arg2-1).getINCOTYPE());
				i_info_detail.putExtra("id", infolist.get(arg2-1).getID());
				i_info_detail.putExtra("ptype", infolist.get(arg2-1).getPTYPE());
				
				startActivity(i_info_detail);
			}
			
		});
		
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		infolist = new MouldList<InfoDataContentBean>();
		rl_myperson_info_all = (RelativeLayout) findViewById(R.id.rl_myperson_info_all);
		rl_myperson_info_ing = (RelativeLayout) findViewById(R.id.rl_myperson_info_ing);
		rl_myperson_info_ed = (RelativeLayout) findViewById(R.id.rl_myperson_info_ed);
		tv_myperson_info_all_name = (TextView) findViewById(R.id.tv_myperson_info_all_name);
		tv_myperson_info_all_line = (TextView) findViewById(R.id.tv_myperson_info_all_line);
		tv_myperson_info_ing_name = (TextView) findViewById(R.id.tv_myperson_info_ing_name);
		tv_myperson_info_ing_line = (TextView) findViewById(R.id.tv_myperson_info_ing_line);
		tv_myperson_info_ed_name = (TextView) findViewById(R.id.tv_myperson_info_ed_name);
		tv_myperson_info_ed_line = (TextView) findViewById(R.id.tv_myperson_info_ed_line);
		lv_myperson_info = (PullToRefreshListView) findViewById(R.id.lv_myperson_info);
		
		rl_myperson_info_all.setOnClickListener(this);
		rl_myperson_info_ing.setOnClickListener(this);
		rl_myperson_info_ed.setOnClickListener(this);
		
		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
				initDefault();
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
						getResources().getString(R.string.title_myperson_info))
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_myperson_info_all:
			setTitleStyle(R.id.rl_myperson_info_all);
//			requestInfoData("0","1");
			infoPage = 1;
			initData("0");
			break;
		case R.id.rl_myperson_info_ing:
			setTitleStyle(R.id.rl_myperson_info_ing);
//			requestInfoData("1","1");
			infoPage = 1;
			initData("1");
			break;
		case R.id.rl_myperson_info_ed:
			setTitleStyle(R.id.rl_myperson_info_ed);
//			requestInfoData("2","1");
			infoPage = 1;
			initData("2");
			break;
		default:
			break;
		}

	}
	
	//交易明细
	public void requestInfoData(String CATEGORY,String pageNo){
		
		HtmlRequest.getInfoData(InfoActivity.this, CATEGORY,pageNo,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						ResultInfoDataContentBean bean = (ResultInfoDataContentBean) params.result;
						if (bean!= null) {
							if (bean.getIncomeStatementList().size()==0&&infoPage!=1) {
								Toast.makeText(InfoActivity.this, "已经到最后一页",
										Toast.LENGTH_SHORT).show();
								infoPage--;
								infoAdapter.notifyDataSetChanged();
								lv_myperson_info.postDelayed(new Runnable() {
									@Override
									public void run() {
										lv_myperson_info.onRefreshComplete();
									}
								}, 1000);
								lv_myperson_info.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
							} else {
								infolist.clear();
								infolist.addAll(bean.getIncomeStatementList());
								infoAdapter.notifyDataSetChanged();
								lv_myperson_info.postDelayed(new Runnable() {
									@Override
									public void run() {
										lv_myperson_info.onRefreshComplete();
									}
								}, 1000);
								lv_myperson_info.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
							}
							
							
						} else {
							netFail = true;
							lv_myperson_info.onRefreshComplete();
							Toast.makeText(InfoActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}

					}
				});
	}
	
	private void setTitleStyle(int rlMypersonInfoAll) {
		
		if(rlMypersonInfoAll == R.id.rl_myperson_info_all){
			tv_myperson_info_all_name.setTextColor(getResources().getColor(R.color.orange));
//			tv_myperson_info_all_line.setBackgroundColor(getResources().getColor(R.color.orange));
			tv_myperson_info_all_line.setVisibility(View.VISIBLE);
			tv_myperson_info_ing_name.setTextColor(getResources().getColor(R.color.txt_black));
//			tv_myperson_info_ing_line.setBackgroundColor(getResources().getColor(R.color.txt_black));
			tv_myperson_info_ing_line.setVisibility(View.GONE);
			tv_myperson_info_ed_name.setTextColor(getResources().getColor(R.color.txt_black));
//			tv_myperson_info_ed_line.setBackgroundColor(getResources().getColor(R.color.txt_black));
			tv_myperson_info_ed_line.setVisibility(View.GONE);
		}else if(rlMypersonInfoAll == R.id.rl_myperson_info_ing){
			tv_myperson_info_all_name.setTextColor(getResources().getColor(R.color.txt_black));
//			tv_myperson_info_all_line.setBackgroundColor(getResources().getColor(R.color.txt_black));
			tv_myperson_info_all_line.setVisibility(View.GONE);
			tv_myperson_info_ing_name.setTextColor(getResources().getColor(R.color.orange));
//			tv_myperson_info_ing_line.setBackgroundColor(getResources().getColor(R.color.orange));
			tv_myperson_info_ing_line.setVisibility(View.VISIBLE);
			tv_myperson_info_ed_name.setTextColor(getResources().getColor(R.color.txt_black));
//			tv_myperson_info_ed_line.setBackgroundColor(getResources().getColor(R.color.txt_black));
			tv_myperson_info_ed_line.setVisibility(View.GONE);
		}else if(rlMypersonInfoAll == R.id.rl_myperson_info_ed){
			tv_myperson_info_all_name.setTextColor(getResources().getColor(R.color.txt_black));
//			tv_myperson_info_all_line.setBackgroundColor(getResources().getColor(R.color.txt_black));
			tv_myperson_info_all_line.setVisibility(View.GONE);
			tv_myperson_info_ing_name.setTextColor(getResources().getColor(R.color.txt_black));
//			tv_myperson_info_ing_line.setBackgroundColor(getResources().getColor(R.color.txt_black));
			tv_myperson_info_ing_line.setVisibility(View.GONE);
			tv_myperson_info_ed_name.setTextColor(getResources().getColor(R.color.orange));
//			tv_myperson_info_ed_line.setBackgroundColor(getResources().getColor(R.color.orange));
			tv_myperson_info_ed_line.setVisibility(View.VISIBLE);
		}
		
		
	}

}
