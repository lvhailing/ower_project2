package com.cf360.act;

import com.cf360.R;
import com.cf360.act.SettingHelpActivity.MyAdapter;
import com.cf360.bean.ResultHelpDetailContentBean;
import com.cf360.bean.ResultHelpListContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class SettingHelpDetailActivity extends BaseActivity {

	private TextView tv_setting_help_item_title, tv_setting_help_item_time,
			tv_setting_help_item_content;
	private String pId = null;
	private MouldList<ResultHelpDetailContentBean> data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_setting_help_title_item);
		initTopTitle();
		pId = getIntent().getExtras().getString("pId");
		initView();
		initData();
	}

	private void initData() {

		requestHelpDetailData();

	}

	// 帮助详情
	public void requestHelpDetailData() {

		HtmlRequest.getHelpDetail(SettingHelpDetailActivity.this, pId,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						@SuppressWarnings("unchecked")
						MouldList<ResultHelpDetailContentBean> bean = (MouldList<ResultHelpDetailContentBean>) params.result;

						if (bean != null) {
							data = bean;
							setView();
						} else {
							netFail = true;
							Toast.makeText(SettingHelpDetailActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}

					}
				});
	}

	protected void setView() {

		tv_setting_help_item_title.setText(data.get(0).getTOPIC());
		tv_setting_help_item_time.setText(data.get(0).getCREATETIME());
		tv_setting_help_item_content.setText(data.get(0).getPROBLEMANSWER());

	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		data = new MouldList<ResultHelpDetailContentBean>();
		tv_setting_help_item_title = (TextView) findViewById(R.id.tv_setting_help_item_title);
		tv_setting_help_item_time = (TextView) findViewById(R.id.tv_setting_help_item_time);
		tv_setting_help_item_content = (TextView) findViewById(R.id.tv_setting_help_item_content);

		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
				initData();

				// insuranceBaseAdapter.notifyDataSetChanged();
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
						getResources().getString(R.string.setting_help_item))
				.setLogo(R.drawable.img_logo, false)
				.setIndicator(R.drawable.back).showMore(false)
				.setOnActionListener(new OnActionListener() {

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
