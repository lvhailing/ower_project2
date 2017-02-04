package com.cf360.act;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.cf360.R;
import com.cf360.act.TrustDetailsActivity.pupClickListener;
import com.cf360.bean.ResultPayDetailsContentBean;
import com.cf360.bean.ResultSunShineItem;
import com.cf360.bean.ResultSunshineObject;
import com.cf360.bean.ResultXinPopContentBean;
import com.cf360.bean.SunShineContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.ButtonView;
import com.cf360.view.MyExpandableListView;
import com.cf360.view.MyListView;
import com.cf360.view.MygridVeiw;
import com.cf360.view.TitleBar;
import com.cf360.view.ButtonView.OnViewClickListener;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;

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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 阳光私募详情
 * @author hasee
 *
 */

public class SunshineActivity extends BaseActivity{

	private MygridVeiw sunGrid;
	private MyListView sunShineList;
	private MyExpandableListView sun_Choicelistview;
	private TextView sunshineTitle;
	private TextView investmentType;
	private TextView recommendations;
	private TextView foundedTime;
	private TextView investmentLimit;
	//private TextView recruitmentProcessDesc;
	private TextView subscriptionRate;
	private TextView redemptionRate;
	private TextView managementRate;
	private TextView mUnitNet;
	private TextView mSumNet;
	private TextView mNetTime;
	private TextView Amount_money;
	private ButtonView mButtonView;
	private PopupWindow popupWindow;
	private TextView content;
	private TextView mSend;
	private EditText mEditText;
	private TextView mCancel;
	private SunShineContentBean data;
	private boolean select;
	private String productName;
	private String minAmount;
	
	private String availableAmount;
	private String canAmount;
	private String hasAmount;
	private String status;
	private ScrollView ScrollView;
	
	private ProgressBar progressnuber;
	private TextView progresstext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.m_sunshine_item_activity);
		initView();
		mButtonView.setValue();
		initData();
		initTopTitle();
	}

	@Override
	protected void onResume() {
		super.onResume();
		requestData();
	}
	private void requestData() {
		String id = getIntent().getStringExtra("Sunshine");
		HtmlRequest.SunShineItemResult(SunshineActivity.this,id,new OnRequestListener() {


			private MouldList<ResultSunShineItem> commList;
			private String[] arrayLists;
			private String[][] choiceLists;
			private String[] oCTs;

			@Override
			public void onRequestFinished(BaseParams params) {
				if(params.result!=null){
					data = (SunShineContentBean) params.result;
					
					if(TextUtils.isEmpty(data.getAuditStatus())){
						PreferenceUtil.setAuditStatus(null);
					}else{
						PreferenceUtil.setAuditStatus(data.getAuditStatus());
					}
					
					commList = data.getCommList();
					ResultSunshineObject ppd = data.getProductPrivateDetailList();
					productName = ppd.getShortName();
					minAmount = ppd.getMinAmount();
					canAmount = ppd.getCanAmount();
					hasAmount = ppd.getHasAmount();
					availableAmount = ppd.getAvailableAmount();
					status = ppd.getStatus();
					
					progressnuber.setProgress(new Integer(ppd.getRecruitmentProcess()));
					
					progresstext.setText(ppd.getRecruitmentProcess()+"%");
					Amount_money.setText(ppd.getCanOrderAmount()+"万元");
					/*产品简称：shortName
					投资类型：investmentType
					推荐说明：recommendations
					成立日期：foundedTime
					产品期限：改成投资期限investmentLimit
					募集进度说明：recruitmentProcessDesc
					单位净值：unitNet为空--
					累计净值：sumNet为空--
					净值日期：netTime为空--
					认购费率：subscriptionRate为空显示--%
					赎回费率：redemptionRate --加上%
					管理费率：managementRate --%
					产品全称：name
					认购金额：minAmount最小
					返佣方式:commissionWay0一次性返佣，1按年返佣
					投资类型：investmentType
					发行机构：issuer
					托管银行：改为托管机构custodianBank
					基金公司：fundCompany为空--
					币种：currency0人民币，1港币，2美元
					封闭期：timeLimit拼接个月，年，天
					期限说明：timeLimitRemark 没有显示--
					开放日说明：openDayDesc
					投资方式：改为投资方向：regionaWay
					基金经理：改为产品经理projectSummary空--
					风控措施：controlMeasures
					投研团队：investmentResearchTeam
					投资理念：investmentConcept*/
					
					
					oCTs = new String[]{
							ppd.getShortName(),
							ppd.getInvestmentType(),
							ppd.getRecommendations(),
							ppd.getPublishTime(),
							ppd.getTimeLimit(),
							ppd.getRecruitmentProcessDesc(),
							ppd.getUnitNet(),			//单位净值
							ppd.getSumNet(),				//累计净值
							ppd.getNetTime(),
							ppd.getSubscriptionRate(),
							ppd.getRedemptionRate(),
							ppd.getManagementRate(),
					};

					arrayLists = new String[]{
							ppd.getName(),
							ppd.getMinAmount(),
							ppd.getCommissionWay(),
							ppd.getStatus(),
							ppd.getAmount(),//莫有数据
							ppd.getAvailableAmount(),		//可售规模
							ppd.getRecruitmentProcessDesc(),
							ppd.getInvestmentType(),
							ppd.getIssuer(),
							ppd.getCustodianBank(),
							ppd.getFundCompany(),
							ppd.getCurrency(),
							ppd.getTimeLimit(),
							ppd.getTimeLimitRemark(),
							ppd.getOpenDayDesc(),
							ppd.getRegionaWay()
					};

					choiceLists = new String[][]{
							{ppd.getProjectSummary()},
							{ppd.getControlMeasures()},
							{ppd.getInvestmentResearchTeam()},
							{ppd.getInvestmentConcept()}

					};
					
					if(data.getAttentionStatus().equals("1")){
						//Toast.makeText(SunshineActivity.this, data.getAttentionStatus()+"", 0).show();
						mButtonView.setTitle("取消关注", 2);
						select=true;
					}else {
						mButtonView.setTitle("加入关注", 2);
						//select=false;
					}
					sunshineTitle.setText(oCTs[0]);
					investmentType.setText(oCTs[1]);
					recommendations.setText(oCTs[2]);
					foundedTime.setText(oCTs[3]);
					investmentLimit.setText(oCTs[4]);
					//recruitmentProcessDesc.setText(oCTs[5]);
					
					if(oCTs[6].equals("0000")){
						mUnitNet.setText("--");
					}else{
						mUnitNet.setText(oCTs[6]);			//单位净值
					}
					if(oCTs[7].equals("0000")){
						mSumNet.setText("--");
					}else{
						mSumNet.setText(oCTs[7]);		//累计净值
					}
					
//					mSumNet.setText(oCTs[7]);
					mNetTime.setText(oCTs[8]); 
					subscriptionRate.setText(oCTs[9]);
					redemptionRate.setText(oCTs[10]);
					managementRate.setText(oCTs[11]);
					GridViewAdapter GridView = new GridViewAdapter(SunshineActivity.this, commList);
					sunGrid.setAdapter(GridView);


					ListViewAdapter listViewAdapter = new ListViewAdapter(SunshineActivity.this, arrayLists);
					sunShineList.setAdapter(listViewAdapter);

					Choicelistview choicelistview = new Choicelistview(choiceLists);
					sun_Choicelistview.setAdapter(choicelistview);
					
					
					ScrollView.smoothScrollBy(0, 0);
				}else{
					netFail = true;
					Toast.makeText(SunshineActivity.this, "加载失败，请确认网络通畅",
							Toast.LENGTH_LONG).show();
				}
				
			}
		});

	}


	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		ScrollView = (android.widget.ScrollView) findViewById(R.id.ScrollView);
		sunshineTitle = (TextView) findViewById(R.id.Sunshine_title);
		investmentType = (TextView) findViewById(R.id.s_investmentType);
		recommendations = (TextView) findViewById(R.id.s_recommendations);
		progressnuber = (ProgressBar) findViewById(R.id.progressnuber);
		progresstext = (TextView) findViewById(R.id.progress_text);
		foundedTime = (TextView) findViewById(R.id.s_foundedTime);
		investmentLimit = (TextView) findViewById(R.id.s_investmentLimit);
	//	recruitmentProcessDesc = (TextView) findViewById(R.id.s_recruitmentProcessDesc);
		subscriptionRate = (TextView) findViewById(R.id.s_subscriptionRate);
		redemptionRate = (TextView) findViewById(R.id.s_redemptionRate);
		managementRate = (TextView) findViewById(R.id.s_managementRate);
		mUnitNet = (TextView) findViewById(R.id.unitNet);
		mSumNet = (TextView) findViewById(R.id.sumNet);
		mNetTime = (TextView) findViewById(R.id.netTime);
		sunGrid = (MygridVeiw) findViewById(R.id.Sunshine_gridview);
		sunShineList = (MyListView) findViewById(R.id.SunShine_listview);
		sun_Choicelistview = (MyExpandableListView) findViewById(R.id.Sun_Choicelistview);
		mButtonView = (ButtonView) findViewById(R.id.ButtonView);
		Amount_money = (TextView) findViewById(R.id.Amount_money);
		
		
		
		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				requestData();
				initView();
				initData();
				
//				insuranceBaseAdapter.notifyDataSetChanged();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
		
	}
	private void initData() {
		mButtonView.setOnViewClickListener(new OnViewClickListener() {

			private Intent intent;

			@Override
			public void onClick(int selectPosition) {
				switch (selectPosition) {
				case 0:
						intent = new Intent(SunshineActivity.this,AppointmentActivity.class);
						String productId=getIntent().getStringExtra("Sunshine");
						intent.putExtra("productId", productId);
						intent.putExtra("minAmount", minAmount+"");
						intent.putExtra("productName", productName);
						intent.putExtra("productCategory", "ygsm");
						intent.putExtra("canAmount", canAmount);
						intent.putExtra("hasAmount", hasAmount);
						intent.putExtra("availableAmount", availableAmount);
						intent.putExtra("status", status);
						if(PreferenceUtil.isAuditStatus().equals("success")){
							startActivity(intent);
						}else{
							Toast.makeText(SunshineActivity.this, "请通过理财师认证后再进行相关操作"
									, Toast.LENGTH_SHORT).show();
						}
					break;
				case 1:
					SendMail();
					break;
				case 2:
					if(select){
						mButtonView.setTitle("加入关注", 2);
						select=false;
					}else { 
						mButtonView.setTitle("取消关注", 2);
					select=true;
					}
					requestPayAttentionTo();
					break;
					
				default:
					break;
				}

			}
		});
	}
	
	private void requestPayAttentionTo() {
		String id = getIntent().getStringExtra("Sunshine");
		//Toast.makeText(TrustDetailsActivity.this,  id+"", 0).show();
		HtmlRequest.mPayAttentionTo(SunshineActivity.this,"ygsm",id, new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				if(params.result!=null){
					ResultPayDetailsContentBean data=(ResultPayDetailsContentBean) params.result;
					if(data.getFlag().toString().equals("true")){
						Toast.makeText(SunshineActivity.this,data.getMessage(), Toast.LENGTH_SHORT).show();
					}
					
					
				}
			}
		});

	}
	protected void SendMail() {

		if(popupWindow==null){
			LayoutInflater mLayoutInflater = LayoutInflater.from(this);
			View contentView = mLayoutInflater.inflate(R.layout.popuwindow1, null);
			popupWindow = new PopupWindow(contentView,ViewGroup.LayoutParams.WRAP_CONTENT,  
					ViewGroup.LayoutParams.WRAP_CONTENT);
			mEditText = (EditText) contentView.findViewById(R.id.pup_EditText);
			mSend = (TextView) contentView.findViewById(R.id.pup_send);
			mCancel = (TextView) contentView.findViewById(R.id.pup_cancel);
			mSend.setOnClickListener(new pupClickListener());
			mCancel.setOnClickListener(new pupClickListener());
		}
		ColorDrawable cd = new ColorDrawable(0x000000);
		popupWindow.setBackgroundDrawable(cd); 
		WindowManager.LayoutParams lp=getWindow().getAttributes();
		lp.alpha = 0.4f;
		getWindow().setAttributes(lp);

		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.showAtLocation(recommendations,0,60,500);
		popupWindow.update();
		popupWindow.setOnDismissListener(new OnDismissListener(){

			//在dismiss中恢复透明度
			public void onDismiss(){
				WindowManager.LayoutParams lp=getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);	
			}			
		});

	
		
	}
	class pupClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.pup_send:
				String strEmail = mEditText.getText().toString();
				if(isEmail(strEmail)){
					PupRequestData();
				}else{
					Toast.makeText(SunshineActivity.this, "邮箱格式有错误请重新填写", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.pup_cancel:
				//Toast.makeText(TrustDetailsActivity.this, "pup_cancel", 0).show();
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
		String id = getIntent().getStringExtra("Sunshine");
		HtmlRequest.PupXinDetails(SunshineActivity.this,2, email,id,new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				if(params.result!=null){
					ResultXinPopContentBean mResultPopContent=(ResultXinPopContentBean) params.result;
					if(mResultPopContent.getFlag()){
						Toast.makeText(SunshineActivity.this, mResultPopContent.getMessage() , Toast.LENGTH_SHORT).show();
						popupWindow.dismiss();
						mEditText.setText("");
					}else{
						Toast.makeText(SunshineActivity.this, mResultPopContent.getMessage() , Toast.LENGTH_SHORT).show();
						mEditText.setText("");
					}
					
				}else{
					netFail = true;
					Toast.makeText(SunshineActivity.this,
							"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
				}
			}
		});

	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(new Action(2, 0, R.color.orange));
//		title.addAction(new Action(2, 0, R.drawable.search));
		title.setCenterText(
				getResources().getString(R.string.sunDetails_title))
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
							
//							Intent Search = new Intent();
//							Search.setClass(TrustDetailsActivity.this, SearchProductActivity.class);//
//							startActivity(Search);
							
//							ShareSDK.initSDK(SunshineActivity.this);
//							sharedSDK();
							break;

						default:
							break;
						}
					}

					@Override
					public void onMenu(int id) {
						Toast.makeText(SunshineActivity.this ,"menu", Toast.LENGTH_SHORT).show();

					}


				});

	}


	class GridViewAdapter extends BaseAdapter{
		private Context mContext;
		private MouldList<ResultSunShineItem> commList;
		public GridViewAdapter(Context mContext, MouldList<ResultSunShineItem> commList) {
			super();
			this.mContext = mContext;
			this.commList=commList;
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();			
				convertView = LayoutInflater.from(this.mContext).inflate(R.layout.m_sunshine_item_grid, null, false);
				holder.SunDatailsone = (TextView) convertView.findViewById(R.id.Sun_Datails_one_item);
				holder.SunDatailstwo = (TextView) convertView.findViewById(R.id.Sun_Datails_two_item);
				holder.SunDatailsthree = (TextView) convertView.findViewById(R.id.Sun_Datails_three_item);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.SunDatailsone.setText(commList.get(position).getMinAmount()+"≤"+"X"+"<"+commList.get(position).getMaxAmount());
			
			if(PreferenceUtil.isAuditStatus().equals("success")){
				
				if(PreferenceUtil.isLogin()){
					holder.SunDatailstwo.setText(commList.get(position).getFrontCommission()+"%");
				}else{
					holder.SunDatailstwo.setText("认证可见");
				}
			}else{
				
				holder.SunDatailstwo.setText("认证可见");
			}
			if(PreferenceUtil.isAuditStatus().equals("success")){
				if(PreferenceUtil.isLogin()){
					holder.SunDatailsthree.setText(commList.get(position).getBackCommission()+"%");
				}else{
					holder.SunDatailsthree.setText("认证可见");
				}
				
			}else{
				holder.SunDatailsthree.setText("认证可见");
				
			}
			
			return convertView;
		}

		private class ViewHolder {
			TextView SunDatailsone;
			TextView SunDatailstwo;
			TextView SunDatailsthree;
		}

	}


	class ListViewAdapter extends BaseAdapter{
		private Context mContext;
		private String[] ListviewContent = new String[] { "产品全称:", "认购金额:", "返佣方式:","产品状态:","产品规模:","可售规模:","募集进度:","投资类型:" 
				,"发行机构:","托管机构:","基金公司:","币种:","封闭期:","期限说明:","开放日:","投资方向:"};
		private String[] generals; 
		public ListViewAdapter(Context mContext, String[] generals) {
			super();
			this.mContext = mContext;
			this.generals=generals;
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
		public View getView(final int position, View convertView, ViewGroup parent) {


			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.m_sunshine_item_lsit, null, false);		

			TextView mItemName = (TextView) convertView.findViewById(R.id.Sunshine_name);
			TextView mItemContent = (TextView) convertView.findViewById(R.id.Sunshine_content);
			mItemName.setText(ListviewContent[position]);
			if(position==1){
				mItemContent.setText(generals[position]+"万元");
			}else if(position==4){
				mItemContent.setText(generals[position]+"万元");
			}else if(position==5){
				mItemContent.setText(generals[position]+"万元");
			}else{
				mItemContent.setText(generals[position]);	
			}
			return convertView;
		}


	}

	class Choicelistview extends BaseExpandableListAdapter{
		//设置组视图的显示文字  
		private String[] generalsTypes = new String[] { "产品经理", "风控措施", "投研团队","投资理念" };
		private String[][] generals1;  
		//自己定义一个获得文字信息的方法  
		TextView getTextView() {  
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(  
					ViewGroup.LayoutParams.FILL_PARENT, 64);  
			TextView textView = new TextView(  
					SunshineActivity.this);  
			textView.setLayoutParams(lp);  
			textView.setGravity(Gravity.CENTER_VERTICAL);  
			textView.setPadding(36, 0, 0, 0);  
			textView.setTextSize(20);  
			textView.setTextColor(Color.BLACK);  
			return textView;  
		}
		//重写ExpandableListAdapter中的各个方法  

		public Choicelistview(String[][] generals1) {
			super();
			this.generals1=generals1;
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
			View GroupView = LayoutInflater.from(SunshineActivity.this).inflate(R.layout.m_sun_item_group, null, false);
			TextView Group_text_content=(TextView) GroupView.findViewById(R.id.Sun_text_content);
			ImageView group_ImaView_content=(ImageView) GroupView.findViewById(R.id.Sun_image_content);

			Group_text_content.setText(generalsTypes[groupPosition]);
			if(isExpanded){
				group_ImaView_content.setBackgroundResource(R.drawable.groupup);
			}else{
				group_ImaView_content.setBackgroundResource(R.drawable.groupdown);
			}
			return GroupView;  
		}  

		@Override  
		public View getChildView(int groupPosition, int childPosition,  
				boolean isLastChild, View convertView, ViewGroup parent) {  
			// TODO Auto-generated method stub  
			View Child = LayoutInflater.from(SunshineActivity.this).inflate(R.layout.m_sun_item_child, null, false);
			TextView EchildView = (TextView) Child.findViewById(R.id.S_childView_text);
			EchildView.setText(generals1[groupPosition][childPosition]);
			return Child;  
		}  

		@Override  
		public boolean isChildSelectable(int groupPosition,  
				int childPosition) {  

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
//		oks.setImagePath("/sdcard/vjinke/imgs/test.jpg");//确保SDcard下面存在此张图片
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用

		// oks.setTitleUrl("http://www.vjinke.com");

		// text是分享文本，所有平台都需要这个字段
		
		  String url="http://www.cf360.com/";
		  oks.setText("..........."+url);
		  // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数 //
//		  oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片 //
//		  url仅在微信（包括好友和朋友圈）中使用 oks.setUrl(url); //
//		  comment是我对这条分享的评论，仅在人人网和QQ空间使用 // oks.setComment("我是测试评论文本"); //
//		  site是分享此内容的网站名称，仅在QQ空间使用 oks.setSite(getString(R.string.app_name));
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用 oks.setSiteUrl(url);
		 

		// 启动分享GUI
		oks.show(this);

	}

}
