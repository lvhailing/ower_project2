package com.cf360.act;

import com.cf360.ApplicationConsts;
import com.cf360.R;
import com.cf360.adapter.MessageListAdapter;
import com.cf360.adapter.NoticeListAdapter;
import com.cf360.bean.MessageListDataContentBean;
import com.cf360.bean.NoticeListDataContentBean;
import com.cf360.bean.ResultMessageListDataContentBean;
import com.cf360.bean.ResultNoticeListDataContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NoticeMessageActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout rl_notice_message_notice, rl_notice_message_message;
	private TextView tv_notice_name, tv_notice_line, tv_message_name,
			tv_message_line;
	private int messagePageNo = 1;
	private int noticePageNo = 1;
	private PullToRefreshListView lv_notice_message;
	private MouldList<MessageListDataContentBean> messageList;
	private MouldList<NoticeListDataContentBean> noticeList;
	private BaseAdapter messageAdapter,noticeAdapter;
	private String noitceUrl = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_notice_message);
		initTopTitle();
		initView();
//		initMessageData();
		initNoticeData();
//		requestNoticeListData(noticePageNo);
//		defaultView();
	}

	private void initMessageData() {
		
		messageAdapter = new MessageListAdapter(NoticeMessageActivity.this,messageList);
		lv_notice_message.setAdapter(messageAdapter);
		requestMessageListData(1+"");
		lv_notice_message.setOnRefreshListener(new OnRefreshListener<ListView>() {
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHeaderShown()) {
					if (messagePageNo >= 2) {
						requestMessageListData((--messagePageNo)+"");
					} else {
						requestMessageListData(1+"");
					}

				} else {
					requestMessageListData((++messagePageNo)+"");
				}
			}
		});
		
		lv_notice_message.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i_detail = new Intent(NoticeMessageActivity.this,
						NoticeMessageDetailActivity.class);
				MessageListDataContentBean p = messageList.get(position - 1);
				i_detail.putExtra("titleName", p.getTOPIC());
				i_detail.putExtra("messageId", p.getID());
				i_detail.putExtra("time", p.getCREATETIME());
				i_detail.putExtra("content", p.getCONTENT());
				i_detail.putExtra("type", p.getTYPE());
				startActivity(i_detail);
			}
		});
		
	}
	
	private void initNoticeData() {
		
		noticeAdapter = new NoticeListAdapter(NoticeMessageActivity.this,noticeList);
		lv_notice_message.setAdapter(noticeAdapter);
		requestNoticeListData(1);
		lv_notice_message.setOnRefreshListener(new OnRefreshListener<ListView>() {
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHeaderShown()) {
					if (noticePageNo >= 2) {
						requestNoticeListData(--noticePageNo);
					} else {
						requestNoticeListData(1);
					}
					
				} else {
					requestNoticeListData(++noticePageNo);
				}
			}
		});
		
		lv_notice_message.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i_detail = new Intent(NoticeMessageActivity.this,
						WebActivity.class);
				NoticeListDataContentBean p = noticeList.get(position - 1);
				i_detail.putExtra("titleName", p.getTOPIC());
				i_detail.putExtra("type", WebActivity.WEBTYPE_Notice_Detail);
				i_detail.putExtra("title", "公告详情");
//				i_detail.putExtra("url", "http://192.168.1.82:8080/websiteBulletin/toDetailForApp?bulletinId=");
				i_detail.putExtra("url", ApplicationConsts.EC_HOST+noitceUrl);
				i_detail.putExtra("messageId", p.getID());
				startActivity(i_detail);
			}
		});
		
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		messageList = new MouldList<MessageListDataContentBean>();
		noticeList = new MouldList<NoticeListDataContentBean>();
		rl_notice_message_notice = (RelativeLayout) findViewById(R.id.rl_notice_message_notice);
		rl_notice_message_message = (RelativeLayout) findViewById(R.id.rl_notice_message_message);
		tv_notice_name = (TextView) findViewById(R.id.tv_notice_name);
		tv_notice_line = (TextView) findViewById(R.id.tv_notice_line);
		tv_message_name = (TextView) findViewById(R.id.tv_message_name);
		tv_message_line = (TextView) findViewById(R.id.tv_message_line);
		lv_notice_message = (PullToRefreshListView) findViewById(R.id.lv_notice_message);
		
		rl_notice_message_notice.setOnClickListener(this);
		rl_notice_message_message.setOnClickListener(this);
		
		setTitleStyle(R.id.rl_notice_message_notice);
		
		
		
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(getResources().getString(R.string.title_notice))
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
		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
				initNoticeData();
				if (tv_notice_line.getVisibility() == View.GONE){
					initMessageData();
				}
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
		if (tv_notice_line.getVisibility() == View.GONE){
			initMessageData();
		}
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

	private void setTitleStyle(int rlMypersonInfoAll) {

		if (rlMypersonInfoAll == R.id.rl_notice_message_notice) {
			tv_notice_name
					.setTextColor(getResources().getColor(R.color.orange));
			tv_notice_line.setVisibility(View.VISIBLE);
//			tv_notice_line.setBackgroundColor(getResources().getColor(
//					R.color.orange));
			tv_message_name.setTextColor(getResources().getColor(
					R.color.txt_black));
//			tv_message_line.setBackgroundColor(getResources().getColor(
//					R.color.txt_black));
			tv_message_line.setVisibility(View.GONE);
		} else if (rlMypersonInfoAll == R.id.rl_notice_message_message) {
			tv_notice_name.setTextColor(getResources().getColor(
					R.color.txt_black));
//			tv_notice_line.setBackgroundColor(getResources().getColor(
//					R.color.txt_black));
			tv_notice_line.setVisibility(View.GONE);
			tv_message_name.setTextColor(getResources()
					.getColor(R.color.orange));
//			tv_message_line.setBackgroundColor(getResources().getColor(
//					R.color.orange));
			tv_message_line.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_notice_message_notice:
			setTitleStyle(R.id.rl_notice_message_notice);
			noticePageNo = 1;
			initNoticeData();
			break;
		case R.id.rl_notice_message_message:
			setTitleStyle(R.id.rl_notice_message_message);
			messagePageNo = 1;
			if(PreferenceUtil.isLogin()){
				initMessageData();
			}else{
				messageAdapter = new MessageListAdapter(NoticeMessageActivity.this,messageList);
				lv_notice_message.setAdapter(messageAdapter);
				Intent i_login = new Intent();
				i_login.setClass(NoticeMessageActivity.this, LoginActivity.class);
				startActivity(i_login);
			}
			
			break;

		default:
			break;
		}

	}

	//我的消息列表
	public void requestMessageListData(String pageNo){
		
		HtmlRequest.getMessageListData(NoticeMessageActivity.this, pageNo,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						ResultMessageListDataContentBean bean = (ResultMessageListDataContentBean) params.result;
						
						if (bean!= null&&bean.getMessageList()!=null) {
							
							if (bean.getMessageList().size()==0&&messagePageNo!=1) {
								Toast.makeText(NoticeMessageActivity.this, "已经到最后一页",
										Toast.LENGTH_SHORT).show();
								messagePageNo--;
								lv_notice_message.postDelayed(new Runnable() {
									@Override
									public void run() {
										lv_notice_message.onRefreshComplete();
									}
								}, 1000);
								lv_notice_message.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
							} else {
								messageList.clear();
								messageList.addAll(bean.getMessageList());
								messageAdapter.notifyDataSetChanged();
								lv_notice_message.postDelayed(new Runnable() {
									@Override
									public void run() {
										lv_notice_message.onRefreshComplete();
									}
								}, 1000);
								lv_notice_message.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
							}
							
//							NoticeListAdapter adapter = new NoticeListAdapter(NoticeMessageActivity.this,bean.getMessageList());
//							
//							lv_notice_message.setAdapter(adapter);
							
						} else {
							netFail = true;
							lv_notice_message.onRefreshComplete();
							Toast.makeText(NoticeMessageActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}

					}
				});
	}
	//公告列表
	public void requestNoticeListData(int page){
		
		HtmlRequest.getNoticeListData(NoticeMessageActivity.this, page,
				new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				
				ResultNoticeListDataContentBean bean = (ResultNoticeListDataContentBean) params.result;
				
				if (bean!= null) {
					noitceUrl = bean.getUrl();
					if (bean.getBulletin().size()==0&&noticePageNo!=1) {
						Toast.makeText(NoticeMessageActivity.this, "已经到最后一页",
								Toast.LENGTH_SHORT).show();
						noticePageNo--;
						lv_notice_message.postDelayed(new Runnable() {
							@Override
							public void run() {
								lv_notice_message.onRefreshComplete();
							}
						}, 1000);
						lv_notice_message.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
					} else {
						noticeList.clear();
						noticeList.addAll(bean.getBulletin());
						noticeAdapter.notifyDataSetChanged();
						lv_notice_message.postDelayed(new Runnable() {
							@Override
							public void run() {
								lv_notice_message.onRefreshComplete();
							}
						}, 1000);
						lv_notice_message.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
					}
					
//							NoticeListAdapter adapter = new NoticeListAdapter(NoticeMessageActivity.this,bean.getMessageList());
//							
//							lv_notice_message.setAdapter(adapter);
					
				} else {
					netFail = true;
					lv_notice_message.onRefreshComplete();
					Toast.makeText(NoticeMessageActivity.this,
							"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
				}
				
			}
		});
	}
	
}
