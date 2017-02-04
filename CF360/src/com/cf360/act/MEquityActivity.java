package com.cf360.act;

import java.util.ArrayList;
import java.util.List;
import com.cf360.R;
import com.cf360.bean.ResultEquityContentBean;
import com.cf360.bean.ResultEquityListItem;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.popupwindow.ExpandTabView;
import com.cf360.popupwindow.ViewLeft;
import com.cf360.popupwindow.ViewRight;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.MyProgressBar;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 私募股权列表(已废弃)
 * @author hasee
 *
 */

public class MEquityActivity extends BaseActivity {
	private PullToRefreshListView EquityListview;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ExpandTabView expandTabView;
	private ViewLeft viewLeft;
	private ViewRight viewRight;
	private ArrayList<String> sTextArray;
	private ArrayList<String> arrayList;
	private MyEquityBaseAdapter myEquityBaseAdapter;
	private ResultEquityContentBean data;
	private MouldList<ResultEquityListItem> productPrivateAppList;
	private String SdefaultType="orderNew";
	private String filterType="0";
	private int infoPage = 1;
	private String commission;
	private String issuer;
	private String investmentField;
	private String annualRate;
	private ArrayList<String> fundList;
	private ArrayList<String> investmentTypeSMList;
	private ArrayList<String> statusList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.m_equity_activity);
		initTopTitle();
		requestPaiXuData("smgq", "0", "1", SdefaultType);
		initView();
		initData();
	}
	private void initView() {
		arrayList = new ArrayList<String>();
		arrayList.add("默认排序");
		arrayList.add("前端佣金最高");
		productPrivateAppList=new MouldList<ResultEquityListItem>();
		expandTabView = (ExpandTabView) findViewById(R.id.title);
		EquityListview = (PullToRefreshListView) findViewById(R.id.equity_listview);

	}
	private void initData() {
		viewLeft = new ViewLeft(this,arrayList);
		myEquityBaseAdapter = new MyEquityBaseAdapter(productPrivateAppList, MEquityActivity.this);
		EquityListview.setAdapter(myEquityBaseAdapter);
		EquityListview.setOnItemClickListener(new MyEquityClickListener());
		EquityListview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {

				if (refreshView.isHeaderShown()) {
					if(filterType.equals("0")){
						if (infoPage >= 2) {
							requestPaiXuData("smgq", "0", (--infoPage)+"", SdefaultType);
						} else {
							requestPaiXuData("smgq", "0", "1", SdefaultType);
						}
					}else if(filterType.equals("1")){
						if (infoPage >= 2) {
							requestShaiXuanData("smgq", annualRate, "1", commission, investmentField, "orderNew", issuer, (--infoPage)+"");

						} else {
							requestShaiXuanData("smgq", annualRate, "1", commission, investmentField, "orderNew", issuer, "1");

						}
					}

				} else {
					if(filterType.equals("0")){
						requestPaiXuData("smgq", "0", (++infoPage)+"", SdefaultType);
					}else if(filterType.equals("1")){
						requestShaiXuanData("smgq", annualRate, "1", commission, investmentField, "orderNew", issuer, (++infoPage)+"");

					}
				}
			}
		});


	}

	@Override
	protected void onResume() {
		super.onResume();
		myEquityBaseAdapter.notifyDataSetChanged();
	}
	class MyEquityClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String id = productPrivateAppList.get(arg2-1).getId();
			Intent intent = new Intent(MEquityActivity.this,EquityItmeActivity.class);
			intent.putExtra("EquityId", id);
			startActivity(intent);

		}

	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(new Action(2, 0, R.drawable.search));
		title.setCenterText(
				getResources().getString(R.string.equity_Details_title))
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
							Intent Search = new Intent();
							Search.setClass(MEquityActivity.this, SearchProductActivity.class);//
							startActivity(Search);
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
	private void requestPaiXuData(String category,String filterType,String pageNo,String sortWay) {
		HtmlRequest.EquityPaiXuResult(MEquityActivity.this,category, filterType, pageNo, sortWay, new OnRequestListener() {

			@Override
			public void onRequestFinished(BaseParams params) {
				if(params.result!=null){
					data = (ResultEquityContentBean) params.result;
					fundList = data.getFundList();
					investmentTypeSMList = data.getInvestmentTypeSMList();
					statusList = data.getStatusList();
					viewRight = new ViewRight(MEquityActivity.this,investmentTypeSMList,statusList,fundList,"投资类型","产品状态","基金管理人");
					sTextArray = new ArrayList<String>();
					if(mViewArray.size()<=0){
						sTextArray.add("排序");
						sTextArray.add("筛选");
						mViewArray.add(viewLeft);
						mViewArray.add(viewRight);
						expandTabView.setValue(sTextArray, mViewArray);
					}
					initListener();
					if (data!= null) {
						if (data.getProductPrivateAppList().size()==0&&infoPage!=1) {
							Toast.makeText(MEquityActivity.this, "已经到最后一页",
									Toast.LENGTH_SHORT).show();
							infoPage--;
							myEquityBaseAdapter.notifyDataSetChanged();
							expandTabView.postDelayed(new Runnable() {
								@Override
								public void run() {
									EquityListview.onRefreshComplete();
								}
							}, 1000);
							EquityListview.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
						} else {
							productPrivateAppList.clear();
							productPrivateAppList.addAll(data.getProductPrivateAppList());
							myEquityBaseAdapter.notifyDataSetChanged();
							expandTabView.postDelayed(new Runnable() {
								@Override
								public void run() {
									EquityListview.onRefreshComplete();
								}
							}, 1000);
							EquityListview.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
						}


					}
				}else{
					Toast.makeText(MEquityActivity.this, "加载失败，请确认网络通畅",
							Toast.LENGTH_LONG).show();
				}

			}
		});
	}

	private void requestShaiXuanData(String category,String companyList
			,String filterType
			,String fundList,String investmentTypeSMList
			,String sortWay,String statusList
			,String pageNo) {
		HtmlRequest.EquiteSaiXuanResult(MEquityActivity.this,category, companyList
				, filterType,fundList,investmentTypeSMList,
				sortWay,statusList,pageNo,
				new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				if(params.result!=null){
					data = (ResultEquityContentBean) params.result;
					if (data!= null) {
						if (data.getProductPrivateAppList().size()==0&&infoPage!=1) {
							Toast.makeText(MEquityActivity.this, "已经到最后一页",
									Toast.LENGTH_SHORT).show();
							infoPage--;
							myEquityBaseAdapter.notifyDataSetChanged();
							expandTabView.postDelayed(new Runnable() {
								@Override
								public void run() {
									EquityListview.onRefreshComplete();
								}
							}, 1000);
							EquityListview.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
						} else {
							productPrivateAppList.clear();
							productPrivateAppList.addAll(data.getProductPrivateAppList());
							myEquityBaseAdapter.notifyDataSetChanged();
							expandTabView.postDelayed(new Runnable() {
								@Override
								public void run() {
									EquityListview.onRefreshComplete();
								}
							}, 1000);
							EquityListview.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
						}


					}
				}

			}
		});
	}


	class MyEquityBaseAdapter extends BaseAdapter{

		private Context mContext;
		private MouldList<ResultEquityListItem> productPrivateAppList;
		public MyEquityBaseAdapter(MouldList<ResultEquityListItem> productPrivateAppList, Context mContext) {
			super();
			this.productPrivateAppList = productPrivateAppList;
			this.mContext = mContext;
		}

		@Override
		public int getCount() {
			if (productPrivateAppList == null) {
				return 0;
			} else {
				return this.productPrivateAppList.size();
			}
		}
		@Override
		public Object getItem(int position) {
			if (productPrivateAppList == null) {
				return null;
			} else {
				return this.productPrivateAppList.get(position);
			}
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
				convertView = LayoutInflater.from(this.mContext).inflate(R.layout.m_item_equityadapter, null, false);
				holder.EquityTitle=(TextView) convertView.findViewById(R.id.Equity_title_content);
				holder.EquityContentfirst=(TextView)convertView.findViewById(R.id.Equity_content_first_one);
				holder.EquityContentsecond=(TextView)convertView.findViewById(R.id.Equity_content_Second_one);
				holder.EquityContentthird=(TextView)convertView.findViewById(R.id.Equity_content_Third_one);
				holder.EquityBranchImage=(RelativeLayout)convertView.findViewById(R.id.EquityBranchImage);
				holder.EquityIncludeImage=(RelativeLayout)convertView.findViewById(R.id.EquityIncludeImage);
				holder.EquityHotImage=(RelativeLayout)convertView.findViewById(R.id.EquityHotImage);
				holder.EquityTuijianImage=(RelativeLayout)convertView.findViewById(R.id.EquityTuijianImage);
				holder.EProgressBar=(MyProgressBar) convertView.findViewById(R.id.Equite_ProgressBar);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.EquityTitle.setText(productPrivateAppList.get(position).getName());
			holder.EquityContentfirst.setText(productPrivateAppList.get(position).getMinAmount()+"万元起");
			if(productPrivateAppList.get(position).getInvestmentLimit().equals("0000")){
				holder.EquityContentsecond.setText("--");
			}else{
				holder.EquityContentsecond.setText(productPrivateAppList.get(position).getInvestmentLimit());
			}
			
			productPrivateAppList.get(position).getRecruitmentProcess();
			holder.EProgressBar.setProgress(20);
			if(PreferenceUtil.isLogin()){
				holder.EquityContentthird.setText(productPrivateAppList.get(position).getMinFrontcomm()+"%"
						+"-"+productPrivateAppList.get(position).getMaxFrontcomm()+"%");
			}else{
				holder.EquityContentthird.setText("登录可见");
			}
			if(productPrivateAppList.get(position).getSaleType().equals("0")){
				//包销
				holder.EquityBranchImage.setVisibility(View.GONE);
				holder.EquityIncludeImage.setVisibility(View.VISIBLE);//包销
			}else{
				//分销
				holder.EquityIncludeImage.setVisibility(View.GONE);
				holder.EquityBranchImage.setVisibility(View.VISIBLE);
			}
			//是否热销
			if(productPrivateAppList.get(position).getSellingStatus().equals("1")){
				holder.EquityHotImage.setVisibility(View.VISIBLE);
			}else{
				holder.EquityHotImage.setVisibility(View.GONE);
			}
			//是否推荐
			if(productPrivateAppList.get(position).getRecommendStatus().equals("1")){
				holder.EquityTuijianImage.setVisibility(View.VISIBLE);
			}else{
				holder.EquityTuijianImage.setVisibility(View.GONE);
			}
			return convertView;
		}

		private class ViewHolder {
			TextView EquityTitle;
			TextView EquityContentfirst;
			TextView EquityContentsecond;
			TextView EquityContentthird;
			RelativeLayout EquityBranchImage;
			RelativeLayout EquityIncludeImage;
			RelativeLayout EquityHotImage;
			RelativeLayout EquityTuijianImage;
			MyProgressBar EProgressBar;

		}

	}

	protected void initListener() {


		viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {



			@Override
			public void getValue(String distance, String showText) {
			}

			@Override
			public void getCancle() {
				onBackPressed();

			}
			@Override
			public void getConfirm() {
				if(TextUtils.isEmpty(annualRate)){
					annualRate="不限";
				}
				if(TextUtils.isEmpty(commission)){
					commission="不限";
				}
				if(TextUtils.isEmpty(investmentField)){
					investmentField="不限";
				}
				if(TextUtils.isEmpty(issuer)){
					issuer="不限";
				}
				
				filterType="1";
				requestShaiXuanData("smgq", annualRate, filterType, commission, investmentField, "orderNew", issuer, "1");
				viewLeft.ReturnState();
				myEquityBaseAdapter.notifyDataSetChanged();
				onBackPressed();
			}

			@Override
			public void getGridview1(View veiw, int position) {
				investmentField=investmentTypeSMList.get(position).toString();
			}

			@Override
			public void getGridview2(View veiw, int position) {
				issuer=statusList.get(position).toString();
			}

			@Override
			public void getGridview3(View veiw, int position) {
				commission=fundList.get(position).toString();
			}

			@Override
			public void getGridview4(View veiw, int position) {

			}

		});



		viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {
			@Override
			public void getValue(View view, int position) {
				viewRight.ReturnState1();
				switch (position) {
				case 0:
					SdefaultType="orderNew";
					filterType="0";
					requestPaiXuData("smgq", filterType, "1", SdefaultType);
					break;
				case 1:
					SdefaultType="orderComm";
					filterType="0";
					requestPaiXuData("smgq", filterType, "1", SdefaultType);
					break;
				case 2:
					SdefaultType="orderNet";
					filterType="0";
					requestPaiXuData("smgq",filterType, "1", SdefaultType);
					break;
				default:
					break;
				}
				onBackPressed();
			}
		});
	}
	@Override
	public void onBackPressed() {

		if (!expandTabView.onPressBack()) {
			finish();
		}
		myEquityBaseAdapter.notifyDataSetChanged();

	}

}
