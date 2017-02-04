package com.cf360.act;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.cf360.R;
import com.cf360.bean.InsuranceAgecoverBean;
import com.cf360.bean.InsuranceChildBean;
import com.cf360.bean.InsuranceDetailsListBean;
import com.cf360.bean.InsuranceLimitBean;
import com.cf360.bean.ResultInsuranceDetailsContentBean;
import com.cf360.bean.ResultPayDetailsContentBean;
import com.cf360.bean.ResultXinPopContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.ButtonView;
import com.cf360.view.MyListView;
import com.cf360.view.TitleBar;
import com.cf360.view.ButtonView.OnViewClickListener;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 保险详情
 * 
 * @author hasee
 * 
 */

public class InsuranceItmeActivity extends BaseActivity {
	private MyListView insurancelistview;
	private TextView title;
	private TextView bili;
	private TextView contnet;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageView title_image;
	private ButtonView mButtonView;
	private PopupWindow popupWindow;
	private TextView content;
	private TextView mSend;
	private EditText mEditText;
	private TextView mCancel;
	private RelativeLayout graphicdetails;
	private String id;
	private boolean select;
	private ResultInsuranceDetailsContentBean date;
	private MouldList<InsuranceDetailsListBean> productsList;
	private String productId;
	private String infoAttachmentsPath;
	private String[] arrayChildIds;
	private String[] arrayChildNames;
	private String[] arrayLimits;
	private String[] arrayAgecovers;
	private String currency;
	private String photosAttachmentsPath;
	private String name;
	private String recommendations;
	private String category;
	private String short_NAME;

	private InsuranceDetailsListBean mDate;
	private String commissionratio;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.m_ins_item_activity);
		initTopTitle();
		initView();
		mButtonView.setValue();
		inttData();
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		title_image = (ImageView) findViewById(R.id.title_image);
		title = (TextView) findViewById(R.id.Insurance_title);
		bili = (TextView) findViewById(R.id.Insurance_bili);
		contnet = (TextView) findViewById(R.id.mInsurance_contnet);
		mButtonView = (ButtonView) findViewById(R.id.ButtonView);
		
		insurancelistview = (MyListView) findViewById(R.id.Insurance_listview);
		graphicdetails = (RelativeLayout) findViewById(R.id.Graphicdetails);
		
		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
				inttData();
				
//				insuranceBaseAdapter.notifyDataSetChanged();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
		
	}

	private void inttData() {
		id = getIntent().getStringExtra("id");
		// String commissionratio =
		// getIntent().getStringExtra("commissionratio");
		requestData(id);
		mButtonView.setOnViewClickListener(new OnViewClickListener() {

			private Intent intent;

			@Override
			public void onClick(int selectPosition) {
				switch (selectPosition) {
				case 0:
					if (PreferenceUtil.isLogin()) {
						intent = new Intent(InsuranceItmeActivity.this,
								InsAppointmentActivity.class);
						intent.putExtra("productId", productId);
						intent.putExtra("name", name);
						intent.putExtra("category", category);
						intent.putExtra("arrayChildId", arrayChildIds);
						intent.putExtra("arrayChildName", arrayChildNames);
						intent.putExtra("arrayLimit", arrayLimits);
						intent.putExtra("arrayAgecover", arrayAgecovers);
						intent.putExtra("short_NAME", short_NAME);
						intent.putExtra("currency", currency);
						if(PreferenceUtil.isAuditStatus().equals("success")){
							startActivity(intent);
						}else{
							Toast.makeText(InsuranceItmeActivity.this, "请通过理财师认证后再进行相关操作", Toast.LENGTH_LONG)
							.show();
						}
					} else {
						Toast.makeText(InsuranceItmeActivity.this, "请登录", Toast.LENGTH_LONG)
								.show();
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
					// Toast.makeText(InsuranceItmeActivity.this, "send",
					// 0).show();
					requestPayAttentionTo();
					break;
				default:
					break;
				}

			}
		});

		graphicdetails.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(InsuranceItmeActivity.this,
						InsDetailsActivity.class);
				String id = getIntent().getStringExtra("id");
				intent.putExtra("id", id);
				startActivity(intent);

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		inttData();
		imageLoader.resume();
	}
	@Override
	protected void onPause() {
		
		super.onPause();
		imageLoader.pause();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		imageLoader.stop();
	}

	/**
	 * 关注
	 */
	private void requestPayAttentionTo() {

		String id = getIntent().getStringExtra("id");
		HtmlRequest.mPayAttentionTo(InsuranceItmeActivity.this, "bx", id,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {
						ResultPayDetailsContentBean data = (ResultPayDetailsContentBean) params.result;
						if (params.result != null) {

							Toast.makeText(InsuranceItmeActivity.this,
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
		popupWindow.showAtLocation(title_image, 0, 60, 500);
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
					Toast.makeText(InsuranceItmeActivity.this, "邮箱格式有错误请重新填写",
							Toast.LENGTH_SHORT).show();
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

	public static boolean isEmail(String strEmail) {
		String strPattern = "\\w+@\\w+\\.\\w+";

		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}

	/**
	 * 发送邮件
	 */
	private void PupRequestData() {
		String email = mEditText.getText().toString();
		String id = getIntent().getStringExtra("id");
		HtmlRequest.PupXinDetails(InsuranceItmeActivity.this, 4, email, id,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							ResultXinPopContentBean mResultPopContent = (ResultXinPopContentBean) params.result;
							if (mResultPopContent.getFlag()) {
								Toast.makeText(InsuranceItmeActivity.this,
										mResultPopContent.getMessage(), Toast.LENGTH_SHORT)
										.show();
								mEditText.setText("");
								popupWindow.dismiss();
							} else {
								Toast.makeText(InsuranceItmeActivity.this,
										mResultPopContent.getMessage(), Toast.LENGTH_SHORT)
										.show();
							}

						}else{
							netFail = true;
							Toast.makeText(InsuranceItmeActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});

	}

	private void requestData(String id) {
		HtmlRequest.InSuranceDetailsResult(InsuranceItmeActivity.this, id,
				new OnRequestListener() {
					private MouldList<InsuranceLimitBean> insuranceLimit;
					private MouldList<InsuranceAgecoverBean> insuranceAgecoverAge;
					private MouldList<InsuranceChildBean> insuranceChild;

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							date = (ResultInsuranceDetailsContentBean) params.result;
							photosAttachmentsPath = date
									.getBannerAttachmentsPath();
							
							if(TextUtils.isEmpty(date.getAuditStatus())){
								PreferenceUtil.setAuditStatus(null);
							}else{
								PreferenceUtil.setAuditStatus(date.getAuditStatus());
							}
							
							infoAttachmentsPath = date.getInfoAttachmentsPath();
							productsList = date.getProductsList();
							insuranceLimit = date.getInsuranceLimit();
							insuranceAgecoverAge = date
									.getInsuranceAgecoverAge();
							insuranceChild = date.getInsuranceChild();
							currency = date.getCurrency();
							arrayChildIds = new String[insuranceChild.size()];
							// productId = productsList.get(0).getID();

							mDate = productsList.get(0);
							commissionratio = mDate.getCOMMISSIONRATIO();
							
							if (PreferenceUtil.isAuditStatus()
									.equals("success")) {
								if (PreferenceUtil.isLogin()) {
									bili.setText(commissionratio);
								} else {
									bili.setText("认证可见");
								}
							} else {
								bili.setText("认证可见");
							}

							for (int i = 0; i < insuranceChild.size(); i++) {
								arrayChildIds[i] = String
										.valueOf(insuranceChild.get(i).getID());
							}
							arrayChildNames = new String[insuranceChild.size()];
							for (int i = 0; i < insuranceChild.size(); i++) {
								arrayChildNames[i] = String
										.valueOf(insuranceChild.get(i)
												.getNAME());
							}
							arrayLimits = new String[insuranceLimit.size()];
							for (int i = 0; i < insuranceLimit.size(); i++) {
								arrayLimits[i] = String.valueOf(insuranceLimit
										.get(i).getLIMITE());
							}
							arrayAgecovers = new String[insuranceAgecoverAge
									.size()];
							for (int i = 0; i < insuranceAgecoverAge.size(); i++) {
								arrayAgecovers[i] = String
										.valueOf(insuranceAgecoverAge.get(i)
												.getAGECOVERAGE());
							}
							if (date.getAttentionStatus().equals("1")) {
								// Toast.makeText(InsuranceItmeActivity.this,
								// date.getAttentionStatus()+"", 0).show();
								mButtonView.setTitle("取消关注", 2);
								select = true;
							} else {
								mButtonView.setTitle("加入关注", 2);
								// select=false;
							}
							productId = mDate.getID();
							name = mDate.getNAME();
							short_NAME = mDate.getSHORT_NAME();
							recommendations = mDate.getRECOMMENDATIONS();
							// category = mDate.getCATEGORY();
							title.setText(short_NAME);
							// bili.setText(category);
							contnet.setText(recommendations);
							String[] ListviewContent1 = new String[] {
									mDate.getCOMPANYNAME(),
									mDate.getCATEGORY(),
									mDate.getGUARANTEETYPE(),
									mDate.getINSURANCECOVERAGE(),
									mDate.getTIMELIMIT(), mDate.getPAYTYPE(),
									mDate.getRISKTIPS(), date.getCurrencyShow() };
							insurancelistview.setAdapter(new ListViewAdapter(
									InsuranceItmeActivity.this,
									ListviewContent1));
							imageLoader.displayImage(photosAttachmentsPath,
									title_image);
						}else{
							netFail = true;
							Toast.makeText(InsuranceItmeActivity.this, "加载失败，请确认网络通畅",
									Toast.LENGTH_LONG).show();
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
				getResources().getString(R.string.insurance_title_item))
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
							
//							ShareSDK.initSDK(InsuranceItmeActivity.this);
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

	class ListViewAdapter extends BaseAdapter {
		private Context mContext;
		private String[] listviewContent1;
		private String[] ListviewContent = new String[] { "保险公司:", "投资类型:",
				"投保方式:", "投保范围:", "保险期间:", "交费方式:", "风险提醒:", "币种:" };

		public ListViewAdapter(Context mContext, String[] listviewContent1) {
			super();
			this.mContext = mContext;
			this.listviewContent1 = listviewContent1;
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
					R.layout.m_ins_item_lsit, null, false);

			TextView mItemName = (TextView) convertView
					.findViewById(R.id.Insurance_name);
			TextView mItemContent = (TextView) convertView
					.findViewById(R.id.Insurance_content);
			mItemName.setText(ListviewContent[position]);
			mItemContent.setText(listviewContent1[position]);

			return convertView;
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
