package com.cf360.act;

import com.cf360.R;
import com.cf360.bean.ResultFinancialToUserAuditContentBean;
import com.cf360.bean.ResultSentSMSContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class NoticeMessageDetailActivity extends BaseActivity {

	private TextView tv_notice_item_title, tv_notice_item_type,
			tv_notice_item_time, tv_notice_item_content;
	private String messageId = null;
	private String titleName = null;
	private String time = null;
	private String content = null;
	private String type =null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_notice_message_detail);
		initTopTitle();
		messageId = getIntent().getExtras().getString("messageId");
		titleName = getIntent().getExtras().getString("titleName");
		time = getIntent().getExtras().getString("time");
		content = getIntent().getExtras().getString("content");
		type = getIntent().getExtras().getString("type");
		initView();
		initData();
		
	}

	private void initData() {
		requestNoticeMessageDetail();

	}

	public void requestNoticeMessageDetail() {

		HtmlRequest.getNoticeMessageDetail(NoticeMessageDetailActivity.this,
				messageId, new OnRequestListener() {
					public void onRequestFinished(BaseParams params) {

						ResultSentSMSContentBean bean = (ResultSentSMSContentBean) params.result;
						if (bean != null) {
							if(Boolean.parseBoolean(bean.getFlag())){
								setView();
							}else{
								Toast.makeText(NoticeMessageDetailActivity.this, bean.getMessage(),
										Toast.LENGTH_SHORT).show();
							}
						} else {
							netFail = true;
							Toast.makeText(NoticeMessageDetailActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}

					}
				});
	}

	protected void setView() {
		tv_notice_item_title.setText(titleName);
		tv_notice_item_type.setText("消息类型:"+type);
		tv_notice_item_time.setText(time);
		tv_notice_item_content.setText(content);
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		tv_notice_item_title = (TextView) findViewById(R.id.tv_notice_item_title);
		tv_notice_item_type = (TextView) findViewById(R.id.tv_notice_item_type);
		tv_notice_item_time = (TextView) findViewById(R.id.tv_notice_item_time);
		tv_notice_item_content = (TextView) findViewById(R.id.tv_notice_item_content);
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
						getResources().getString(R.string.title_messgae_detail))
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
