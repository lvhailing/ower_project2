package com.cf360.act;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.cf360.ApplicationConsts;
import com.cf360.R;
import com.cf360.act.TrustDetailsActivity.pupClickListener;
import com.cf360.bean.ResultPayDetailsContentBean;
import com.cf360.bean.ResultXinDetailsContentBean;
import com.cf360.bean.ResultXinPopContentBean;
import com.cf360.bean.XinTuoDetailscommissionList;
import com.cf360.bean.XinTuoDetailsproductTrust;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 资管详情
 * @author hasee
 *
 */

public class ZiGuanItemActivity extends BaseActivity{

	private MygridVeiw gridview;
	private MyListView listview;
	private MyExpandableListView choicelistview;
	private TextView productAuthority;
	private TextView paymentType;
	private TextView dateofSale;
	private TextView serviceFeerate;
	private TextView titleText;
	private ProgressBar ziGuanProgress;
	private TextView progressNumber;
	private TextView progressMoney;
	private ButtonView mButtonView;
	private PopupWindow popupWindow;
	private TextView mSend;
	private EditText mEditText;
	private TextView mCancel;
	private TextView content;
	private boolean select;
	private XinTuoDetailsproductTrust productTrust;
	private ResultXinDetailsContentBean data;
	private TextView title;
	private String productName;
	private String productId;
	private String productCategory;
	private int minAmount;
	private String canAmount;
	private String hasAmount;
	private String availableAmount_detail;
	private String status;
	private ImageView ziguanImage;
	private ScrollView ScrollView;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.m_ziguan_item_activity);
		requestData();
		initTopTitle();
		initView();
		mButtonView.setValue();
		initData();
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		titleText = (TextView) findViewById(R.id.ZiGuan_title_text);
		ziGuanProgress = (ProgressBar) findViewById(R.id.ZiGuan_item_progress);
		progressNumber = (TextView) findViewById(R.id.ZiGuan_progress_number);
		progressMoney = (TextView) findViewById(R.id.ZiGuan_progress_money);
		productAuthority = (TextView) findViewById(R.id.ZiGuan_Product_authority);
		paymentType = (TextView) findViewById(R.id.ZiGuan_PaymentType);
		dateofSale = (TextView) findViewById(R.id.ZiGuan_DateofSale);
		serviceFeerate = (TextView) findViewById(R.id.ZiGuan_Servicefeerate);
		gridview = (MygridVeiw) findViewById(R.id.ZiGuan_gridview);
		listview = (MyListView) findViewById(R.id.ZiGuan_listview);
		choicelistview = (MyExpandableListView)findViewById(R.id.ZiGuan_Expandable);
		mButtonView = (ButtonView) findViewById(R.id.ButtonView);
		title = (TextView) findViewById(R.id.ZiGuan_title_text);
		content = (TextView) findViewById(R.id.ziguan_content);
		ziguanImage = (ImageView) findViewById(R.id.Ziguan_type_image);
		ScrollView = (android.widget.ScrollView) findViewById(R.id.ScrollView);

		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
				initData();
				requestData();
//				insuranceBaseAdapter.notifyDataSetChanged();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
		
	}
	private void initData() {
		String PBnumber = getIntent().getStringExtra("ProgressBar");
		String Amount = getIntent().getStringExtra("Amount");
		String 	availableAmount = getIntent().getStringExtra("availableAmount");
		/*String 	creditLevel = getIntent().getStringExtra("creditLevel");
		Creditlevel(creditLevel);*/
		
//		ziGuanProgress.setProgress(new Integer(PBnumber));
//		progressNumber.setText(PBnumber+"%");
		
		
		
		mButtonView.setOnViewClickListener(new OnViewClickListener() {

			private Intent intent;

			@Override
			public void onClick(int selectPosition) {
				switch (selectPosition) {
				case 0:
					intent = new Intent(ZiGuanItemActivity.this,AppointmentActivity.class);
					intent.putExtra("productId", productId);
					intent.putExtra("minAmount", minAmount+"");
					intent.putExtra("productName", productName);
					intent.putExtra("productCategory", productCategory);
					intent.putExtra("canAmount", canAmount);
					intent.putExtra("hasAmount", hasAmount);
					intent.putExtra("availableAmount", availableAmount_detail);
					intent.putExtra("status", status);
					if(PreferenceUtil.isAuditStatus().equals("success")){
						startActivity(intent);
					}else{
						Toast.makeText(ZiGuanItemActivity.this, "请通过理财师认证后再进行相关操作"
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
	public static boolean isEmail(String strEmail) {
		String strPattern = "\\w+@\\w+\\.\\w+";

		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}
	private void Creditlevel(String creditLevel) {
		if(!TextUtils.isEmpty(creditLevel)){
			if(creditLevel.equals("1")){
				ziguanImage.setBackgroundResource(R.drawable.img_no);
			}else if(creditLevel.equals("2")){
				ziguanImage.setBackgroundResource(R.drawable.img_aaa);
			}else if(creditLevel.equals("3")){
				ziguanImage.setBackgroundResource(R.drawable.img_aa);
				ziguanImage.setVisibility(View.VISIBLE);
			}else if(creditLevel.equals("4")){
				ziguanImage.setBackgroundResource(R.drawable.img_a);
			}else if(creditLevel.equals("7")){
				ziguanImage.setBackgroundResource(R.drawable.img_b);
				ziguanImage.setVisibility(View.VISIBLE);
			}else if(creditLevel.equals("6")){
				ziguanImage.setBackgroundResource(R.drawable.img_bb);
				ziguanImage.setVisibility(View.VISIBLE);
			}else if(creditLevel.equals("5")){
				ziguanImage.setBackgroundResource(R.drawable.img_bb);
				ziguanImage.setVisibility(View.VISIBLE);
			}else if(creditLevel.equals("10")){
				ziguanImage.setBackgroundResource(R.drawable.img_c);
				ziguanImage.setVisibility(View.VISIBLE);
			}else if(creditLevel.equals("9")){
				ziguanImage.setBackgroundResource(R.drawable.img_cc);
				ziguanImage.setVisibility(View.VISIBLE);
			}else if(creditLevel.equals("8")){
				ziguanImage.setBackgroundResource(R.drawable.img_ccc);
				ziguanImage.setVisibility(View.VISIBLE);
			}else{
				ziguanImage.setBackgroundResource(R.drawable.img_d);
			}
		}
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		requestData();
		
	}
	private void requestPayAttentionTo() {

		String id = getIntent().getStringExtra("Mtrustid");
		HtmlRequest.mPayAttentionTo(ZiGuanItemActivity.this,"资管",id, new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				if(params.result!=null){
					ResultPayDetailsContentBean data= (ResultPayDetailsContentBean) params.result;
					Toast.makeText(ZiGuanItemActivity.this, data.getMessage()
							, Toast.LENGTH_SHORT).show();



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
		popupWindow.showAtLocation(content,0,60,500);
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
					Toast.makeText(ZiGuanItemActivity.this, "邮箱格式有错误请重新填写", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.pup_cancel:
				popupWindow.dismiss();
				mEditText.setText("");
				break;

			default:
				break;
			}
		}

	}
	private void PupRequestData() {
		String email = mEditText.getText().toString();
		String id = getIntent().getStringExtra("Mtrustid");
		HtmlRequest.PupXinDetails(ZiGuanItemActivity.this,0, email,id,new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				if(params.result!=null){
					ResultXinPopContentBean mResultPopContent=(ResultXinPopContentBean) params.result;
					if(mResultPopContent.getFlag()){
						Toast.makeText(ZiGuanItemActivity.this, mResultPopContent.getMessage() , Toast.LENGTH_SHORT).show();
						popupWindow.dismiss();
					}else{
						Toast.makeText(ZiGuanItemActivity.this, mResultPopContent.getMessage() , Toast.LENGTH_SHORT).show();
					}

				}else{
					netFail = true;
					Toast.makeText(ZiGuanItemActivity.this,
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
				getResources().getString(R.string.ziguan_title_item))
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
							
//							ShareSDK.initSDK(ZiGuanItemActivity.this);
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
		String id = getIntent().getStringExtra("Mtrustid");
		HtmlRequest.xinItemDetailsResult(ZiGuanItemActivity.this,id,new OnRequestListener() {
			private String[] commissionListContents;
			@Override
			public void onRequestFinished(BaseParams params) {
				if(params.result!=null){
					data = (ResultXinDetailsContentBean) params.result;
					productTrust = data.getProductTrust();
					
					if(TextUtils.isEmpty(data.getAuditStatus())){
						PreferenceUtil.setAuditStatus(null);
					}else{
						PreferenceUtil.setAuditStatus(data.getAuditStatus());
					}
					
					MouldList<XinTuoDetailscommissionList> commissionList = data.getCommissionList();
					//Toast.makeText(TrustDetailsActivity.this,  commissionList.size()+"", 0).show();
					String creditLevel = productTrust.getCreditLevel();
					Creditlevel(creditLevel);
					;
					ziGuanProgress.setProgress(new Integer(productTrust.getRecruitmentProcess()));
					progressNumber.setText(productTrust.getRecruitmentProcess()+"%");
					
					progressMoney.setText(productTrust.getCanOrderAmount()+"万元");
					
					
					title.setText(productTrust.getShortName());
					content.setText(productTrust.getRecommendations());
					productAuthority.setText(productTrust.getTimeLimit());
					
					String InterestSeltct= productTrust.getInterestPaymentType();
					if(InterestSeltct.equals("0")){
						paymentType.setText("年付");
					}else{
						paymentType.setText("月付");
					}
					
					
					dateofSale.setText(productTrust.getPublishTime());
					serviceFeerate.setText(productTrust.getServiceFeeRate()+"%");
					productName = productTrust.getShortName();
					productId = productTrust.getId();
					productCategory = productTrust.getCategory();
					minAmount = data.getMinAmount();
					
					canAmount = productTrust.getCanAmount();
					hasAmount = productTrust.getHasAmount();
					availableAmount_detail = productTrust.getAvailableAmount();
					status = productTrust.getStatus();
					
					gridview.setAdapter(new GridViewAdapter(ZiGuanItemActivity.this,commissionList));
					commissionListContents = new String[] {productTrust.getName(),
							data.getMinAmount()+"",productTrust.getCommissionWay(),productTrust.getStatus(),
							productTrust.getAmount()+"",productTrust.getAvailableAmount(),productTrust.getIncomeType(),productTrust.getAltersSize(),
							productTrust.getCategory(),productTrust.getIssuer(),productTrust.getInvestmentField()
					};
					listview.setAdapter(new ListViewAdapter(ZiGuanItemActivity.this,commissionListContents));
					String[][] generals=new String[][]{
							{productTrust.getRaiseAccount()}
							,{productTrust.getProjectFundsPurpose()}
							,{productTrust.getProjectRepaySource()}
							,{productTrust.getProductAdvantage()}
							,{productTrust.getControlMeasures()}
							,{productTrust.getFinancingParty()}
							,{productTrust.getGuarantyParty()}
					};
					MyExpandableList mExpandableList = new MyExpandableList(generals);
					choicelistview.setAdapter(mExpandableList);

					if(data.getAttentionStatus().equals("1")){
						mButtonView.setTitle("取消关注", 2);
						select=true;
					}else {
						mButtonView.setTitle("加入关注", 2);
						//select=false;
					}
					ScrollView.smoothScrollTo(0, 0);
				}else{
					netFail = true;
					Toast.makeText(ZiGuanItemActivity.this, "加载失败，请确认网络通畅",
							Toast.LENGTH_LONG).show();
				}

			}
		});

	}

	class GridViewAdapter extends BaseAdapter{
		private Context mContext;
		private MouldList<XinTuoDetailscommissionList> commissionList;
		public GridViewAdapter(Context mContext, MouldList<XinTuoDetailscommissionList> commissionList) {
			super();
			this.mContext = mContext;
			this.commissionList=commissionList;
		}



		@Override
		public int getCount() {

			return commissionList.size();

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
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();			
				convertView = LayoutInflater.from(this.mContext).inflate(R.layout.m_ziguan_item_grid, null, false);		
				convertView.setTag(holder);
				holder.ZiGuan_Datails_one_item = (TextView) convertView.findViewById(R.id.ZiGuan_Datails_one_item);
				holder.ZiGuan_Datails_two_item = (TextView) convertView.findViewById(R.id.ZiGuan_Datails_two_item);
				holder.ZiGuan_Datails_three_item = (TextView) convertView.findViewById(R.id.ZiGuan_Datails_three_item);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.ZiGuan_Datails_one_item.setText(commissionList.get(position).getAmount());
			holder.ZiGuan_Datails_two_item.setText(commissionList.get(position).getAnnualRate());
			if(PreferenceUtil.isAuditStatus().equals("success")){
				if(PreferenceUtil.isLogin()){
					holder.ZiGuan_Datails_three_item.setText(commissionList.get(position).getCommission()+"%");
					}else{
						holder.ZiGuan_Datails_three_item.setText("认证可见");
					}
			}else{
				holder.ZiGuan_Datails_three_item.setText("认证可见");
			}
			
			return convertView;
		}

		private class ViewHolder {
			TextView ZiGuan_Datails_one_item;
			TextView ZiGuan_Datails_two_item;
			TextView ZiGuan_Datails_three_item;
		}

	}

	class ListViewAdapter extends BaseAdapter{
		private Context mContext;
		private String[] commissionListContents;
		private String[] ListviewContent = new String[] { "产品全称:", "认购金额:", "返佣方式:","产品状态:" 
				,"产品规模:","可售规模:","收益类型:","大小配比:","产品类别:","发行机构:","投资领域:"		 
		};

		public ListViewAdapter(Context mContext, String[] commissionListContents) {
			super();
			this.mContext = mContext;
			this.commissionListContents=commissionListContents;
		}

		@Override
		public int getCount() {

			return commissionListContents.length;

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


			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.m_ziguan_item_lsit, null, false);		

			TextView mItemName = (TextView) convertView.findViewById(R.id.Ziguan_listview_name);
			TextView mItemContent = (TextView) convertView.findViewById(R.id.Ziguan_listview_content);
			mItemName.setText(ListviewContent[position].toString());
			//mItemContent.setText(commissionListContents[position].toString());
			if(position==1){
				mItemContent.setText(commissionListContents[position].toString()+"万元");
			}else if(position==2){
				String CommissionSelect = commissionListContents[position].toString();
				if(CommissionSelect.equals("0")){
					mItemContent.setText("一次性返佣");
				}else{
					mItemContent.setText("按年返佣");
				}
			}else if(position==4){
				mItemContent.setText(commissionListContents[position].toString()+"万元");
			}else if(position==5){
				mItemContent.setText(commissionListContents[position].toString()+"万元");
			}else if(position==10){
				String Tselect = commissionListContents[position].toString();
				if(Tselect.equals("1")){
					mItemContent.setText("不限");
				}else if(Tselect.equals("2")){
					mItemContent.setText("金融");
				}else if(Tselect.equals("3")){
					mItemContent.setText("保险");
				}else if(Tselect.equals("4")){
					mItemContent.setText("电信");
				}else if(Tselect.equals("5")){
					mItemContent.setText("互联网");
				}
			}else{
				mItemContent.setText(commissionListContents[position].toString());
			}
			return convertView;
		}


	}



	class MyExpandableList extends BaseExpandableListAdapter{


		//设置组视图的显示文字  
		private String[] generalsTypes = new String[] { "募集账户", "资金用途", "还款来源"
				,"项目亮点","风控措施","融资本体","担保方" };
		private String[][] generals;  
		public MyExpandableList(String[][] generals) {
			super();
			this.generals=generals;
		}
		//自己定义一个获得文字信息的方法  
		TextView getTextView() {  
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(  
					ViewGroup.LayoutParams.FILL_PARENT, 64);  
			TextView textView = new TextView(  
					ZiGuanItemActivity.this);  
			textView.setLayoutParams(lp);  
			textView.setGravity(Gravity.CENTER_VERTICAL);  
			textView.setPadding(36, 0, 0, 0);  
			textView.setTextSize(20);  
			textView.setTextColor(Color.BLACK);  
			return textView;  
		}
		//重写ExpandableListAdapter中的各个方法  
		@Override  
		public int getGroupCount() {  
			return generalsTypes.length;  
		}  

		@Override  
		public Object getGroup(int groupPosition) {  
			return generalsTypes[groupPosition];  
		}  

		@Override  
		public long getGroupId(int groupPosition) {  
			return groupPosition;  
		}  

		@Override  
		public int getChildrenCount(int groupPosition) {  
			return generals[groupPosition].length;  
		}  

		@Override  
		public Object getChild(int groupPosition, int childPosition) {  
			return generals[groupPosition][childPosition];  
		}  

		@Override  
		public long getChildId(int groupPosition, int childPosition) {  
			return childPosition;  
		}  

		@Override  
		public boolean hasStableIds() {  
			return true;  
		}  

		@Override  
		public View getGroupView(int groupPosition, boolean isExpanded,  
				View convertView, ViewGroup parent) {
			View GroupView = LayoutInflater.from(ZiGuanItemActivity.this).inflate(R.layout.m_ziguan_item_choice, null, false);
			TextView Group_text_content=(TextView) GroupView.findViewById(R.id.ziguan_text_content);
			ImageView group_ImaView_content=(ImageView) GroupView.findViewById(R.id.ziguan_image_content);
			RelativeLayout rl_ziguan_item = (RelativeLayout) GroupView.findViewById(R.id.rl_ziguan_item);

			
			if(groupPosition==3){
				
				rl_ziguan_item.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
//						Toast.makeText(TrustDetailsActivity.this, "show", Toast.LENGTH_LONG).show();
						Intent i_web = new Intent(ZiGuanItemActivity.this, WebActivity.class);
						i_web.putExtra("type", WebActivity.WEBTYPE_RODUCTADVANTAGE);
						i_web.putExtra("title", getResources().getString(R.string.web_product_advager));
						i_web.putExtra("url", ApplicationConsts.URL_WEB_RODUCT_ADVANTAGE+"?productId="+productId);
						
						startActivity(i_web);
					}
				});
			}
			
			
			
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

			View Child = LayoutInflater.from(ZiGuanItemActivity.this).inflate(R.layout.m_ziguan_item_child, null, false);
			TextView ChildView = (TextView) Child.findViewById(R.id.ziguanChildView_content);
			if(TextUtils.isEmpty(generals[groupPosition][childPosition])){
				ChildView.setText("");
			}else{
				ChildView.setText(generals[groupPosition][childPosition].toString());
			}
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
