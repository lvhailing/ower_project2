package com.cf360.act;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.cf360.ApplicationConsts;
import com.cf360.R;
import com.cf360.act.TrustDetailsActivity.pupClickListener;
import com.cf360.act.ZiGuanItemActivity.GridViewAdapter;
import com.cf360.act.ZiGuanItemActivity.ListViewAdapter;
import com.cf360.act.ZiGuanItemActivity.MyExpandableList;
import com.cf360.bean.EquityDetialContentBean;
import com.cf360.bean.ProductPrivateDetailList;
import com.cf360.bean.ResultEquityDetialsItem;
import com.cf360.bean.ResultPayDetailsContentBean;
import com.cf360.bean.ResultXinDetailsContentBean;
import com.cf360.bean.ResultXinPopContentBean;
import com.cf360.bean.XinTuoDetailscommissionList;
import com.cf360.bean.XinTuoDetailsproductTrust;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.ButtonView;
import com.cf360.view.ButtonView.OnViewClickListener;
import com.cf360.view.MyExpandableListView;
import com.cf360.view.MyListView;
import com.cf360.view.MygridVeiw;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;

/**
 * 私募股权详情
 * 
 * @author hasee
 * 
 */

public class EquityItmeActivity extends BaseActivity {

	private MygridVeiw equitygridview;
	private MouldList<ResultEquityDetialsItem> commList;
	private ProductPrivateDetailList OCt;
	private MyExpandableListView EquityChoicelistview;
	private MyListView Equitylistview;
	private String[] contents;
	private TextView equity_title;
	private TextView equityInvestmentType;
	private TextView equityRecommendations;
	private TextView equityFoundedTime;
	private TextView equityInvestmentLimit;
	// private TextView equityRecruitmentProcessDesc;
	private TextView equitySubscriptionRate;
	private TextView equityRedemptionRate;
	private TextView equityManagementRate;
	private TextView Amount_money;
	private ButtonView mButtonView;
	private PopupWindow popupWindow;
	private TextView content;
	private TextView mSend;
	private EditText mEditText;
	private TextView mCancel;
	private EquityDetialContentBean data;
	private boolean select;
	private String productName;
	private String minAmount;
	private String canAmount;
	private String hasAmount;
	private String availableAmount;
	private String status;

	private ProgressBar progressBarnumber;
	private TextView progressText;
	private String productId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.m_equity_itemactivity);
		initTopTitle();
		productId = getIntent().getStringExtra("EquityId");
		InitView();
		mButtonView.setValue();
		InItData();
		requestData();
	}

	private void InItData() {

		
		mButtonView.setOnViewClickListener(new OnViewClickListener() {
			private Intent intent;

			@Override
			public void onClick(int selectPosition) {
				switch (selectPosition) {
				case 0:
					
					intent = new Intent(EquityItmeActivity.this,
							AppointmentActivity.class);
					intent.putExtra("productId", productId);
					intent.putExtra("minAmount", minAmount + "");
					intent.putExtra("productName", productName);
					intent.putExtra("productCategory", "smgq");
					intent.putExtra("canAmount", canAmount);
					intent.putExtra("hasAmount", hasAmount);
					intent.putExtra("availableAmount", availableAmount);
					intent.putExtra("status", status);
					if (PreferenceUtil.isAuditStatus().equals("success")) {
						startActivity(intent);
					} else {
						Toast.makeText(EquityItmeActivity.this,
								"请通过理财师认证后再进行相关操作", Toast.LENGTH_SHORT).show();
					}
					break;
				case 1:
					SendMail();
					break;

				case 2:
					if (select) {
						mButtonView.setTitle("加入关注", 2);
						select = false;
					} else {
						mButtonView.setTitle("取消关注", 2);
						select = true;
					}
					requestPayAttentionTo();
					break;
				default:
					break;
				}
			}
		});

		EquityChoicelistview
				.setOnGroupExpandListener(new OnGroupExpandListener() {

					@Override
					public void onGroupExpand(int groupPosition) {
						// Toast.makeText(EquityItmeActivity.this,
						// "groupPosition"+groupPosition, 0).show();

					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		requestData();
	}

	private void requestPayAttentionTo() {

		String id = getIntent().getStringExtra("EquityId");
		// Toast.makeText(TrustDetailsActivity.this, id+"", 0).show();
		HtmlRequest.mPayAttentionTo(EquityItmeActivity.this, "smgq", id,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							ResultPayDetailsContentBean data = (ResultPayDetailsContentBean) params.result;
							Toast.makeText(EquityItmeActivity.this,
									data.getMessage(), Toast.LENGTH_SHORT).show();

						}
					}
				});

	}

	protected void SendMail() {

		if (popupWindow == null) {
			LayoutInflater mLayoutInflater = LayoutInflater.from(this);
			View contentView = mLayoutInflater.inflate(R.layout.popuwindow1,
					null);
			popupWindow = new PopupWindow(contentView,
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			mEditText = (EditText) contentView.findViewById(R.id.pup_EditText);
			mSend = (TextView) contentView.findViewById(R.id.pup_send);
			mCancel = (TextView) contentView.findViewById(R.id.pup_cancel);
			mSend.setOnClickListener(new pupClickListener());
			mCancel.setOnClickListener(new pupClickListener());
		}
		ColorDrawable cd = new ColorDrawable(0x000000);
		popupWindow.setBackgroundDrawable(cd);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.4f;
		getWindow().setAttributes(lp);

		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.showAtLocation(equityRecommendations, 0, 60, 500);
		popupWindow.update();
		popupWindow.setOnDismissListener(new OnDismissListener() {

			// 在dismiss中恢复透明度
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});

	}

	class pupClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.pup_send:
				String strEmail = mEditText.getText().toString();
				if (isEmail(strEmail)) {
					PupRequestData();
				} else {
					Toast.makeText(EquityItmeActivity.this, "邮箱格式有错误请重新填写", Toast.LENGTH_SHORT)
							.show();
				}
				break;
			case R.id.pup_cancel:
				// Toast.makeText(TrustDetailsActivity.this, "pup_cancel",
				// 0).show();
				popupWindow.dismiss();
				mEditText.setText("");
				break;

			default:
				break;
			}
		}

	}

	public static boolean isEmail(String strEmail) {
		String strPattern = "\\w+@\\w+\\.\\w+";

		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}

	private void PupRequestData() {
		String email = mEditText.getText().toString();
		String id = getIntent().getStringExtra("EquityId");
		HtmlRequest.PupXinDetails(EquityItmeActivity.this, 3, email, id,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							ResultXinPopContentBean mResultPopContent = (ResultXinPopContentBean) params.result;
							if (mResultPopContent.getFlag()) {
								Toast.makeText(EquityItmeActivity.this,
										mResultPopContent.getMessage(), Toast.LENGTH_LONG)
										.show();
								mEditText.setText("");
								popupWindow.dismiss();
							} else {
								Toast.makeText(EquityItmeActivity.this,
										mResultPopContent.getMessage(), Toast.LENGTH_LONG)
										.show();
							}

						}else{
							netFail = true;
							Toast.makeText(EquityItmeActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});

	}

	private void InitView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		ScrollView ScrollView = (android.widget.ScrollView) findViewById(R.id.ScrollView);
		// ScrollView.fullScroll(ScrollView.FOCUS_UP);
		equity_title = (TextView) findViewById(R.id.Equity_title);
		equityInvestmentType = (TextView) findViewById(R.id.e_investmentType);
		equityRecommendations = (TextView) findViewById(R.id.e_recommendations);
		equityFoundedTime = (TextView) findViewById(R.id.e_foundedTime);
		equityInvestmentLimit = (TextView) findViewById(R.id.e_investmentLimit);
		// equityRecruitmentProcessDesc = (TextView)
		// findViewById(R.id.e_recruitmentProcessDesc);
		equitySubscriptionRate = (TextView) findViewById(R.id.e_subscriptionRate);
		equityRedemptionRate = (TextView) findViewById(R.id.e_redemptionRate);
		equityManagementRate = (TextView) findViewById(R.id.e_managementRate);
		equitygridview = (MygridVeiw) findViewById(R.id.equity_gridview);
		Equitylistview = (MyListView) findViewById(R.id.equity_listview);
		EquityChoicelistview = (MyExpandableListView) findViewById(R.id.equity_Choicelistview);
		progressBarnumber = (ProgressBar) findViewById(R.id.progressnuber);
		progressText = (TextView) findViewById(R.id.progress_text);
		mButtonView = (ButtonView) findViewById(R.id.ButtonView);
		Amount_money = (TextView) findViewById(R.id.Amount_money);
		// mButtonView.setValue();
		
		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				InitView();
				InItData();
				requestData();
				
//				insuranceBaseAdapter.notifyDataSetChanged();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
		
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(new Action(2, 0, R.color.orange));
//		title.addAction(new Action(2, 0, R.drawable.search));
		title.setCenterText(
				getResources().getString(R.string.equity_title_item))
				.setLogo(R.drawable.img_logo, false).setIndicator(R.drawable.back)
				.showMore(true).setOnActionListener(new OnActionListener() {
					@Override
					public void onBack() {
						finish();
					}

					@Override
					public void onAction(int id) {
						switch (id) {
						case 2:

							// Intent Search = new Intent();
							// Search.setClass(TrustDetailsActivity.this,
							// SearchProductActivity.class);//
							// startActivity(Search);

//							ShareSDK.initSDK(EquityItmeActivity.this);
//							sharedSDK();
							break;

						default:
							break;
						}
					}

					@Override
					public void onMenu(int id) {

					}

				});
	}

	private void requestData() {
		String id = getIntent().getStringExtra("EquityId");
		HtmlRequest.EquityItemResult(EquityItmeActivity.this, id,
				new OnRequestListener() {
					private String[] generals;
					private String[][] generals1;

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							data = (EquityDetialContentBean) params.result;
							commList = data.getCommList();

							if (TextUtils.isEmpty(data.getAuditStatus())) {
								PreferenceUtil.setAuditStatus(null);
							} else {
								PreferenceUtil.setAuditStatus(data
										.getAuditStatus());
							}

							OCt = data.getProductPrivateDetailList();
							productName = OCt.getShortName();
							minAmount = OCt.getMinAmount();

							canAmount = OCt.getCanAmount();
							hasAmount = OCt.getHasAmount();
							availableAmount = OCt.getAvailableAmount();
							status = OCt.getStatus();

							progressBarnumber.setProgress(new Integer(OCt
									.getRecruitmentProcess()));
							progressText.setText(OCt.getRecruitmentProcess()
									+ "%");
							Amount_money.setText(OCt.getCanOrderAmount() + "万元");

							generals = new String[] {
									OCt.getName()// 产品全称
									,
									OCt.getMinAmount()// 认购金额
									,
									OCt.getCommissionWay()// 返佣方式
									, OCt.getStatus(),
									OCt.getAmount(),
									OCt.getAvailableAmount() // 可售规模
									, OCt.getRecruitmentProcessDesc(),
									OCt.getInvestmentType()// 投资类型
									, OCt.getAnnualRate()// 预期收益
									, OCt.getQuitType()// 退出方式
									, OCt.getCurrency()// 币种
									, OCt.getCustodianBank()// 托管银行
									, OCt.getTimeLimit()// 封闭期
									, OCt.getTimeLimitRemark()// 期限说明
									, OCt.getOpenDayDesc()// 开放日说明
									, OCt.getRegionaWay()// 收益分配
									, OCt.getInvestmentField() // 投资方向
							};

							generals1 = new String[][] {
									{ OCt.getProjectSummary() },
									{ OCt.getProjectFundsPurpose() },
									{ OCt.getProjectRepaySource() },
									{ OCt.getControlMeasures() },
									{ OCt.getManagementOrganization() }

							};
							/*
							 * 认购费率：subscriptionRate为空显示--% 赎回费率：redemptionRate
							 * --加上% 管理费率：managementRate --%
							 */

							contents = new String[] {
									OCt.getShortName(),// 产品简称、
									OCt.getInvestmentType(),
									OCt.getRecommendations(),
									OCt.getPublishTime(),// 成立日期 改起售日期
									OCt.getInvestmentLimit(),// 投資期限
									OCt.getRecruitmentProcessDesc(),// 募集进度说明
									OCt.getSubscriptionRate(),
									OCt.getRedemptionRate(),
									OCt.getManagementRate() };
							if (data.getAttentionStatus().equals("1")) {
								mButtonView.setTitle("取消关注", 2);
								select = true;
							} else {
								mButtonView.setTitle("加入关注", 2);
								// select=false;
							}
							equity_title.setText(contents[0]);
							equityInvestmentType.setText(contents[1]);
							equityRecommendations.setText(contents[2]);
							equityFoundedTime.setText(contents[3]);
							equityInvestmentLimit.setText(contents[4]);
							// equityRecruitmentProcessDesc.setText(contents[5]);
							equitySubscriptionRate.setText(contents[6]);
							equityRedemptionRate.setText(contents[7]);
							equityManagementRate.setText(contents[8]);
							ListViewAdapter ListViewAdapter = new ListViewAdapter(
									EquityItmeActivity.this, generals);
							Equitylistview.setAdapter(ListViewAdapter);
							GridViewAdapter mGridViewAdapter = new GridViewAdapter(
									EquityItmeActivity.this, commList);
							equitygridview.setAdapter(mGridViewAdapter);
							Choicelistview choicelistview = new Choicelistview(
									generals1);
							EquityChoicelistview.setAdapter(choicelistview);
						}else{
							netFail = true;
							Toast.makeText(EquityItmeActivity.this, "加载失败，请确认网络通畅",
									Toast.LENGTH_LONG).show();
						}

						
					}
				});

	}

	class GridViewAdapter extends BaseAdapter {
		private Context mContext;
		private MouldList<ResultEquityDetialsItem> commList;

		public GridViewAdapter(Context mContext,
				MouldList<ResultEquityDetialsItem> commList) {
			super();
			this.mContext = mContext;
			this.commList = commList;
		}

		@Override
		public int getCount() {

			return commList.size();

		}

		@Override
		public Object getItem(int position) {

			return commList.get(position);

		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(this.mContext).inflate(
						R.layout.m_equity_item_grid, null, false);
				holder.EquityDatailsone = (TextView) convertView
						.findViewById(R.id.Equity_Datails_one_item);
				holder.EquityDatailstwo = (TextView) convertView
						.findViewById(R.id.Equity_Datails_two_item);
				holder.EquityDatailsthree = (TextView) convertView
						.findViewById(R.id.Equity_Datails_three_item);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.EquityDatailsone.setText(commList.get(position)
					.getMinAmount()
					+ "≤"
					+ "X"
					+ "<"
					+ commList.get(position).getMaxAmount());

			if (PreferenceUtil.isAuditStatus().equals("success")) {
				if (PreferenceUtil.isLogin()) {
					holder.EquityDatailstwo.setText(commList.get(position)
							.getFrontCommission() + "%");
				} else {
					holder.EquityDatailstwo.setText("认证可见");
				}
			} else {
				holder.EquityDatailstwo.setText("认证可见");

			}

			if (PreferenceUtil.isAuditStatus().equals("success")) {

				if (PreferenceUtil.isLogin()) {
					holder.EquityDatailsthree.setText(commList.get(position)
							.getBackCommission() + "%");
				} else {
					holder.EquityDatailsthree.setText("认证可见");
				}
			} else {
				holder.EquityDatailsthree.setText("认证可见");

			}
			return convertView;
		}

		private class ViewHolder {
			TextView EquityDatailsone;
			TextView EquityDatailstwo;
			TextView EquityDatailsthree;
		}

	}

	class ListViewAdapter extends BaseAdapter {
		private Context mContext;
		private String[] ListviewContent = new String[] { "产品全称:", "认购金额:",
				"返佣方式:", "产品状态:", "产品规模:", "可售规模:", "募集进度:", "投资类型:", "预期收益:",
				"退出方式:", "币种:", "托管机构:", "封闭期:", "期限说明:", "开放日:", "分配方式:",
				"投资领域:" };
		private String[] generals;

		public ListViewAdapter(Context mContext, String[] generals) {
			super();
			this.mContext = mContext;
			this.generals = generals;
		}

		@Override
		public int getCount() {

			return ListviewContent.length;

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
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			convertView = LayoutInflater.from(this.mContext).inflate(
					R.layout.m_equity_item_lsit, null, false);

			TextView mItemName = (TextView) convertView
					.findViewById(R.id.Equity_name);
			TextView mItemContent = (TextView) convertView
					.findViewById(R.id.Equity_content);
			mItemName.setText(ListviewContent[position]);
			if (position == 1) {
				mItemContent.setText(generals[position] + "万元");
			} else if (position == 4) {
				mItemContent.setText(generals[position] + "万元");
			} else if (position == 5) {
				mItemContent.setText(generals[position] + "万元");
			} else {
				mItemContent.setText(generals[position]);
			}
			return convertView;
		}

	}

	class Choicelistview extends BaseExpandableListAdapter {
		// 设置组视图的显示文字
		private String[] generalsTypes = new String[] { "产品介绍", "资金用途", "还款来源",
				"风控措施", "管理机构" };
		private String[][] generals1;

		// 自己定义一个获得文字信息的方法
		TextView getTextView() {
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 64);
			TextView textView = new TextView(EquityItmeActivity.this);
			textView.setLayoutParams(lp);
			textView.setGravity(Gravity.CENTER_VERTICAL);
			textView.setPadding(36, 0, 0, 0);
			textView.setTextSize(20);
			textView.setTextColor(Color.BLACK);
			return textView;
		}

		// 重写ExpandableListAdapter中的各个方法

		public Choicelistview(String[][] generals1) {
			super();
			this.generals1 = generals1;
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return generalsTypes.length;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return generalsTypes[groupPosition];
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return generals1[groupPosition].length;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return generals1[groupPosition][childPosition];
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			View GroupView = LayoutInflater.from(EquityItmeActivity.this)
					.inflate(R.layout.m_euuity_item_group, null, false);
			TextView Group_text_content = (TextView) GroupView
					.findViewById(R.id.eGroup_text_content);
			ImageView group_ImaView_content = (ImageView) GroupView
					.findViewById(R.id.eGroup_image_content);
			RelativeLayout rl_equity_item = (RelativeLayout) GroupView
					.findViewById(R.id.rl_equity_item);

			if (groupPosition == 0) {

				rl_equity_item.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// Toast.makeText(TrustDetailsActivity.this, "show",
						// Toast.LENGTH_LONG).show();
						Intent i_web = new Intent(EquityItmeActivity.this,
								WebActivity.class);
						i_web.putExtra("type",
								WebActivity.WEBTYPE_RODUCTADVANTAGE);
						i_web.putExtra("title", getResources().getString(R.string.web_equitproduct_advager));
						i_web.putExtra(
								"url",
								ApplicationConsts.URL_WEB_EQUIT_RODUCT_ADVANTAGE
										+ "?productId=" + productId);

						startActivity(i_web);
					}
				});
			}
			Group_text_content.setText(generalsTypes[groupPosition]);
			if (isExpanded) {
				group_ImaView_content.setBackgroundResource(R.drawable.groupup);
			} else {
				group_ImaView_content
						.setBackgroundResource(R.drawable.groupdown);
			}
			return GroupView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View Child = LayoutInflater.from(EquityItmeActivity.this).inflate(
					R.layout.m_equity_item_child, null, false);
			TextView EchildView = (TextView) Child
					.findViewById(R.id.E_childView_text);
			EchildView.setText(generals1[groupPosition][childPosition]);
			return Child;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {

			return true;
		}

	}

	private void sharedSDK() {

		// OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		// oks.disableSSOWhenAuthorize();
		// 分享时Notification的图标和文字
		// oks.setNotification(R.drawable.ic_launcher,
		// this.getString(R.string.app_name));
		// // address是接收人地址，仅在信息和邮件使用
		// oks.setAddress("12345678901");
		// // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		// oks.setTitle(this.getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		// oks.setTitleUrl("http://www.vjinke.com");

		// text是分享文本，所有平台都需要这个字段
		// oks.setText("预期年化收益8% -无限畅想，无微不至的投资体验。稳盈创收，点金筑梦。精彩尽在“微金客”！>>http://www.vjinke.com");
		// imaingePath是图片的本地路径，Lked-In以外的平台都支持此参数
		// oks.setImagePath("mnt/sdcard/1.jpg");

		// imageUrl是图片的网络路径，新浪微博、人人网、QQ空间、
		// // 微信的两个平台、Linked-In支持此字段
		// oks.setImageUrl("http://img.appgo.cn/imgs/sharesdk/content/2013/07/25/1374723172663.jpg");
		// // url仅在微信（包括好友和朋友圈）中使用
		// oks.setUrl("http://www.vjinke.com");
		// // appPath是待分享应用程序的本地路劲，仅在微信中使用
		// // oks.setAppPath("");
		// // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		// oks.setComment(this.getString(R.string.share));
		// // site是分享此内容的网站名称，仅在QQ空间使用
		// oks.setSite(this.getString(R.string.app_name));
		// // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		// oks.setSiteUrl("http://www.vjinke.com");
		// // venueName是分享社区名称，仅在Foursquare使用
		// oks.setVenueName("Southeast in China");
		// // venueDescription是分享社区描述，仅在Foursquare使用
		// oks.setVenueDescription("This is a beautiful place!");
		// // latitude是维度数据，仅在新浪微博、腾讯微博和Foursquare使用
		// oks.setLatitude(23.122619f);
		// // longitude是经度数据，仅在新浪微博、腾讯微博和Foursquare使用
		// oks.setLongitude(113.372338f);
		// 是否直接分享（true则直接分享）
		// oks.setSilent(false);
		// 指定分享平台，和slient一起使用可以直接分享到指定的平台
		// if (platform != null) {
		// oks.setPlatform(platform);
		// }
		// 去除注释可通过OneKeyShareCallback来捕获快捷分享的处理结果
		// oks.setCallback(new OneKeyShareCallback());
		// 通过OneKeyShareCallback来修改不同平台分享的内容
		// oks.setShareContentCustomizeCallback(
		// new ShareContentCustomizeDemo());
		// site是分享此内容的网站名称，仅在QQ空间使用
		// oks.setSite(getActivity().getString(R.string.app_name));
		// oks.show(activity);

		final OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用

		oks.setTitle(getString(R.string.share));
		// oks.setImagePath("/sdcard/vjinke/imgs/test.jpg");//确保SDcard下面存在此张图片
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用

		// oks.setTitleUrl("http://www.vjinke.com");

		// text是分享文本，所有平台都需要这个字段

		String url = "http://www.cf360.com/";
		oks.setText("..........." + url);
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数 //
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片 //
		// url仅在微信（包括好友和朋友圈）中使用 oks.setUrl(url); //
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用 // oks.setComment("我是测试评论文本"); //
		// site是分享此内容的网站名称，仅在QQ空间使用 oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用 oks.setSiteUrl(url);

		// 启动分享GUI
		oks.show(this);

	}

}
