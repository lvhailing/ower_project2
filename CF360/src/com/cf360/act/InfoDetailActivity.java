package com.cf360.act;

import java.util.ArrayList;

import com.cf360.R;
import com.cf360.adapter.MypersonInfoAdapter;
import com.cf360.bean.InfoDataDetailContentBean;
import com.cf360.bean.ResultInfoDataContentBean;
import com.cf360.bean.ResultInfoDataDetailContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InfoDetailActivity extends BaseActivity {
	
	private TextView tv_info_detail_name,tv_info_detail_name_add,tv_info_detail_num;
	private ImageView iv_info_detail_arrow;
	private TextView tv_info_detail_liushuihao,tv_info_detail_tixianfangshi,tv_info_detail_tixianzhanghu,tv_info_detail_tixianzhanghu_1,tv_info_detail_baodanbianhao,tv_info_detail_kehuname,tv_info_detail_fujiabaoxian,tv_info_detail_jiaofeinianqi,tv_info_detail_fanyongfangshi,tv_info_detail_zhuangtai,tv_info_detail_chuangjianshijian,tv_info_detail_zhangdanriqi;
	private TextView tv_info_detail_liushuihao_value,tv_info_detail_tixianfangshi_value,tv_info_detail_tixianzhanghu_value,tv_info_detail_tixianzhanghu_1_value,tv_info_detail_baodanbianhao_value,tv_info_detail_kehuname_value,tv_info_detail_fujiabaoxian_value,tv_info_detail_jiaofeinianqi_value,tv_info_detail_fanyongfangshi_value,tv_info_detail_zhuangtai_value,tv_info_detail_chuangjianshijian_value,tv_info_detail_zhangdanriqi_value;
	private ListView tv_info_detail_fanyongbili,tv_info_detail_fanyongbili_value,tv_info_detail_dakuanjine,tv_info_detail_dakuanjine_value;
	
	private TextView tv_info_detail_huilv_value,tv_info_detail_hulv;
	private String []styles  = {"product","activity","withdraw","insurance"};
	private String style = "withdraw";
	private String id = null;
	private String incotype = null;
	private String ptype = null;
	private InfoDataDetailContentBean infoDetailBean;
	
	private TextView tv_info_detail_fanyongjine_value,tv_info_detail_fanyongjine;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_myperson_info_detail);
		initTopTitle();
		id = getIntent().getExtras().getString("id");
		incotype = getIntent().getExtras().getString("incotype");
		ptype = getIntent().getExtras().getString("ptype");
		initView();
		initData();
		
	}
	
	private void initData() {
		requestInfoDetailData(id);
	}

	//交易明细详情
	public void requestInfoDetailData(String id){
		
		HtmlRequest.getInfoDetailData(InfoDetailActivity.this, id,ptype,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						ResultInfoDataDetailContentBean bean = (ResultInfoDataDetailContentBean) params.result;
						if (bean!= null) {
							
							infoDetailBean = bean.getIncomeStatement();
							System.out.println("");
							if(infoDetailBean.getTYPE().equals("in")){
								style = "product";
								tv_info_detail_name.setText(infoDetailBean.getPRODUCTNAME2());
								tv_info_detail_num.setText(incotype+infoDetailBean.getANNUALAMOUNT());
								tv_info_detail_liushuihao_value.setText(infoDetailBean.getID());
								tv_info_detail_baodanbianhao_value.setText(infoDetailBean.getORDERID());
								
								tv_info_detail_kehuname_value.setText(infoDetailBean.getCUSTOMERNAME());
								
//								tv_info_detail_dakuanjine_value.setText(infoDetailBean.getAMOUNT());
								
								ArrayList<String> in_dakuanjine = new ArrayList<String>();
								
								if(infoDetailBean.getAMOUNT2()!=null){
									if(infoDetailBean.getAMOUNT2().size()==0){
										in_dakuanjine.add(new String(""));
									}else{
										in_dakuanjine = (ArrayList<String>) infoDetailBean.getAMOUNT2();
									}
								}else{
									in_dakuanjine.add(new String(""));
								}
								
								tv_info_detail_dakuanjine_value.setAdapter(new MyAdapter_value(in_dakuanjine));
								
								ArrayList<String> in_dakuanjine_name = new ArrayList<String>();
								in_dakuanjine_name.add(new String("打款金额:"));
								tv_info_detail_dakuanjine.setAdapter(new MyAdapter(in_dakuanjine_name));
								
								setListViewHeightBasedOnChildren(tv_info_detail_dakuanjine_value,0,tv_info_detail_dakuanjine);

								tv_info_detail_huilv_value.setText(infoDetailBean.getEXCHANGE_RATE());
								
								
//								tv_info_detail_fanyongbili_value.setText(infoDetailBean.getCOMMISSION());
								ArrayList<String> in_fangyongbili = new ArrayList<String>();
								
								if(infoDetailBean.getREBATEPROPORTION2()!=null){
									if(infoDetailBean.getREBATEPROPORTION2().size()==0){
										in_fangyongbili.add(new String(""));
									}else{
										in_fangyongbili = (ArrayList<String>) infoDetailBean.getREBATEPROPORTION2();
									}
								}else{
									in_fangyongbili.add(new String(""));
								}
								
								tv_info_detail_fanyongbili_value.setAdapter(new MyAdapter_value(in_fangyongbili));
								
								ArrayList<String> in_fangyongbili_name = new ArrayList<String>();
								in_fangyongbili_name.add(new String("返佣比例:"));
								tv_info_detail_fanyongbili.setAdapter(new MyAdapter(in_fangyongbili_name));
								
								setListViewHeightBasedOnChildren(tv_info_detail_fanyongbili_value,0,tv_info_detail_fanyongbili);
								
								
								tv_info_detail_fanyongfangshi_value.setText(infoDetailBean.getCOMMISSION());
								tv_info_detail_zhuangtai_value.setText(infoDetailBean.getSTATUS());
//								if(infoDetailBean.getSTATUS().equals("success")){
//									tv_info_detail_zhuangtai_value.setText("已完成");
//								}else if(infoDetailBean.getSTATUS().equals("handling")){
//									tv_info_detail_zhuangtai_value.setText("处理中");
//								}
								
								tv_info_detail_chuangjianshijian_value.setText(infoDetailBean.getCREATETIME());
								
							}else if(infoDetailBean.getTYPE().equals("activity")){
								style = "activity";
								tv_info_detail_name.setText(infoDetailBean.getPRODUCTNAME2()+"("+infoDetailBean.getREMARK()+")");
								tv_info_detail_num.setText(incotype+infoDetailBean.getANNUALAMOUNT());
								tv_info_detail_liushuihao_value.setText(infoDetailBean.getID());
								tv_info_detail_baodanbianhao_value.setText(infoDetailBean.getORDERID());
								tv_info_detail_kehuname_value.setText(infoDetailBean.getCUSTOMERNAME());
								
								tv_info_detail_fanyongjine_value.setText(infoDetailBean.getAMOUNT()+"元");
								
//								tv_info_detail_dakuanjine_value.setText(infoDetailBean.getAMOUNT()+"");
								
								ArrayList<String> in_dakuanjine = new ArrayList<String>();
								
//								if(infoDetailBean.getAMOUNT2()!=null){
//									if(infoDetailBean.getAMOUNT2().size()==0){
//										in_dakuanjine.add(new String(""));
//									}else{
//										in_dakuanjine = (ArrayList<String>) infoDetailBean.getAMOUNT2();
//									}
//								}else{
//									in_dakuanjine.add(new String(""));
//								}
								
								in_dakuanjine.add(new String(" "));
								
								tv_info_detail_dakuanjine_value.setAdapter(new MyAdapter_value(in_dakuanjine));
								
								
								ArrayList<String> in_dakuanjine_name = new ArrayList<String>();
								in_dakuanjine_name.add(new String("打款金额:"));
								tv_info_detail_dakuanjine.setAdapter(new MyAdapter(in_dakuanjine_name));
								
								setListViewHeightBasedOnChildren(tv_info_detail_dakuanjine_value,0,tv_info_detail_dakuanjine);
								
//								tv_info_detail_fanyongbili_value.setText(infoDetailBean.getCOMMISSION());
								ArrayList<String> in_fangyongbili = new ArrayList<String>();
								ArrayList<String> in_fangyongbili_name = new ArrayList<String>();
								
								in_fangyongbili_name.add(new String("返佣金额:"));
								tv_info_detail_fanyongbili.setAdapter(new MyAdapter(in_fangyongbili_name));
								
								
								if(infoDetailBean.getREBATEPROPORTION2()!=null){
									if(infoDetailBean.getREBATEPROPORTION2().size()==0){
										in_fangyongbili.add(new String(""));
									}else{
										in_fangyongbili = (ArrayList<String>) infoDetailBean.getREBATEPROPORTION2();
									}
								}else{
									in_fangyongbili.add(new String(""));
								}
								tv_info_detail_fanyongbili_value.setAdapter(new MyAdapter_value(in_fangyongbili));
								setListViewHeightBasedOnChildren(tv_info_detail_fanyongbili_value,0,tv_info_detail_fanyongbili);
								
								tv_info_detail_zhuangtai_value.setText(infoDetailBean.getSTATUS());
//								if(infoDetailBean.getSTATUS().equals("success")){
//									tv_info_detail_zhuangtai_value.setText("已完成");
//								}else if(infoDetailBean.getSTATUS().equals("handling")){
//									tv_info_detail_zhuangtai_value.setText("处理中");
//								}
								tv_info_detail_chuangjianshijian_value.setText(infoDetailBean.getCREATETIME());
								
							}else if(infoDetailBean.getTYPE().equals("out")){
								style = "withdraw";
								
								tv_info_detail_name.setText(infoDetailBean.getPRODUCTNAME2());
								tv_info_detail_num.setText(incotype+infoDetailBean.getANNUALAMOUNT());
								tv_info_detail_liushuihao_value.setText(infoDetailBean.getID());
								
								tv_info_detail_tixianfangshi_value.setText("银行卡");
								
								tv_info_detail_tixianzhanghu_value.setText(infoDetailBean.getCARDNAME()+"("+infoDetailBean.getCARD_NUMBER()+")");
								tv_info_detail_tixianzhanghu_1_value.setText(infoDetailBean.getUSERNAME());
								
								tv_info_detail_zhuangtai_value.setText(infoDetailBean.getSTATUS());
								
//								if(infoDetailBean.getSTATUS().equals("success")){
//									tv_info_detail_zhuangtai_value.setText("已完成");
//								}else if(infoDetailBean.getSTATUS().equals("handling")){
//									tv_info_detail_zhuangtai_value.setText("处理中");
//								}
								tv_info_detail_chuangjianshijian_value.setText(infoDetailBean.getCREATETIME());
								tv_info_detail_zhangdanriqi_value.setText(infoDetailBean.getORDERDATE());
								
							}else{
								style = "product";
							}
							setView();
						} else {
							netFail = true;
							Toast.makeText(InfoDetailActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}

					}
				});
	}
	
	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		infoDetailBean = new InfoDataDetailContentBean();
		tv_info_detail_name = (TextView) findViewById(R.id.tv_info_detail_name);
		tv_info_detail_name_add = (TextView) findViewById(R.id.tv_info_detail_name_add);
		iv_info_detail_arrow = (ImageView) findViewById(R.id.iv_info_detail_arrow);
		tv_info_detail_num = (TextView) findViewById(R.id.tv_info_detail_num);
		
		tv_info_detail_liushuihao = (TextView) findViewById(R.id.tv_info_detail_liushuihao);
		tv_info_detail_liushuihao_value = (TextView) findViewById(R.id.tv_info_detail_liushuihao_value);
		tv_info_detail_tixianfangshi = (TextView) findViewById(R.id.tv_info_detail_tixianfangshi);
		tv_info_detail_tixianfangshi_value = (TextView) findViewById(R.id.tv_info_detail_tixianfangshi_value);
		tv_info_detail_tixianzhanghu = (TextView) findViewById(R.id.tv_info_detail_tixianzhanghu);
		tv_info_detail_tixianzhanghu_value = (TextView) findViewById(R.id.tv_info_detail_tixianzhanghu_value);
		tv_info_detail_tixianzhanghu_1 = (TextView) findViewById(R.id.tv_info_detail_tixianzhanghu_1);
		tv_info_detail_tixianzhanghu_1_value = (TextView) findViewById(R.id.tv_info_detail_tixianzhanghu_1_value);
		tv_info_detail_baodanbianhao = (TextView) findViewById(R.id.tv_info_detail_baodanbianhao);
		tv_info_detail_baodanbianhao_value = (TextView) findViewById(R.id.tv_info_detail_baodanbianhao_value);
		tv_info_detail_kehuname = (TextView) findViewById(R.id.tv_info_detail_kehuname);
		tv_info_detail_kehuname_value = (TextView) findViewById(R.id.tv_info_detail_kehuname_value);
		
		
		
		tv_info_detail_dakuanjine = (ListView) findViewById(R.id.tv_info_detail_dakuanjine);
		tv_info_detail_dakuanjine_value = (ListView) findViewById(R.id.tv_info_detail_dakuanjine_value);
		
		
		tv_info_detail_fujiabaoxian = (TextView) findViewById(R.id.tv_info_detail_fujiabaoxian);
		tv_info_detail_fujiabaoxian_value = (TextView) findViewById(R.id.tv_info_detail_fujiabaoxian_value);
		tv_info_detail_jiaofeinianqi = (TextView) findViewById(R.id.tv_info_detail_jiaofeinianqi);
		tv_info_detail_jiaofeinianqi_value = (TextView) findViewById(R.id.tv_info_detail_jiaofeinianqi_value);
		
		tv_info_detail_fanyongbili = (ListView) findViewById(R.id.tv_info_detail_fanyongbili);
		tv_info_detail_fanyongbili_value = (ListView) findViewById(R.id.tv_info_detail_fanyongbili_value);
		
		tv_info_detail_fanyongfangshi = (TextView) findViewById(R.id.tv_info_detail_fanyongfangshi);
		tv_info_detail_fanyongfangshi_value = (TextView) findViewById(R.id.tv_info_detail_fanyongfangshi_value);
		tv_info_detail_zhuangtai = (TextView) findViewById(R.id.tv_info_detail_zhuangtai);
		tv_info_detail_zhuangtai_value = (TextView) findViewById(R.id.tv_info_detail_zhuangtai_value);
		tv_info_detail_chuangjianshijian = (TextView) findViewById(R.id.tv_info_detail_chuangjianshijian);
		tv_info_detail_chuangjianshijian_value = (TextView) findViewById(R.id.tv_info_detail_chuangjianshijian_value);
		tv_info_detail_zhangdanriqi = (TextView) findViewById(R.id.tv_info_detail_zhangdanriqi);
		tv_info_detail_zhangdanriqi_value = (TextView) findViewById(R.id.tv_info_detail_zhangdanriqi_value);
		
		tv_info_detail_huilv_value = (TextView) findViewById(R.id.tv_info_detail_huilv_value);
		tv_info_detail_hulv = (TextView) findViewById(R.id.tv_info_detail_hulv);
		
		tv_info_detail_fanyongjine_value= (TextView) findViewById(R.id.tv_info_detail_fanyongjine_value);
		tv_info_detail_fanyongjine = (TextView) findViewById(R.id.tv_info_detail_fanyongjine);
		
//		setView();
		
		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
				initData();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
	}

	private void setView() {
		if(style.equals(styles[0])){
			tv_info_detail_name_add.setVisibility(View.GONE);
			tv_info_detail_tixianfangshi.setVisibility(View.GONE);
			tv_info_detail_tixianfangshi_value.setVisibility(View.GONE);
			tv_info_detail_tixianzhanghu.setVisibility(View.GONE);
			tv_info_detail_tixianzhanghu_value.setVisibility(View.GONE);
			tv_info_detail_tixianzhanghu_1.setVisibility(View.GONE);
			tv_info_detail_tixianzhanghu_1_value.setVisibility(View.GONE);
			tv_info_detail_fujiabaoxian.setVisibility(View.GONE);
			tv_info_detail_fujiabaoxian_value.setVisibility(View.GONE);
			tv_info_detail_jiaofeinianqi.setVisibility(View.GONE);
			tv_info_detail_jiaofeinianqi_value.setVisibility(View.GONE);
			tv_info_detail_zhangdanriqi.setVisibility(View.GONE);
			tv_info_detail_zhangdanriqi_value.setVisibility(View.GONE);
			
			tv_info_detail_fanyongjine_value.setVisibility(View.GONE);
			tv_info_detail_fanyongjine.setVisibility(View.GONE);
			
		}else if(style.equals(styles[1])){
//			tv_info_detail_name.setText("活动");
			iv_info_detail_arrow.setVisibility(View.GONE);
			
			tv_info_detail_tixianfangshi.setVisibility(View.GONE);
			tv_info_detail_tixianfangshi_value.setVisibility(View.GONE);
			tv_info_detail_tixianzhanghu.setVisibility(View.GONE);
			tv_info_detail_tixianzhanghu_value.setVisibility(View.GONE);
			tv_info_detail_tixianzhanghu_1.setVisibility(View.GONE);
			tv_info_detail_tixianzhanghu_1_value.setVisibility(View.GONE);
			tv_info_detail_fujiabaoxian.setVisibility(View.GONE);
			tv_info_detail_fujiabaoxian_value.setVisibility(View.GONE);
			tv_info_detail_jiaofeinianqi.setVisibility(View.GONE);
			tv_info_detail_jiaofeinianqi_value.setVisibility(View.GONE);
			tv_info_detail_fanyongfangshi.setVisibility(View.GONE);
			tv_info_detail_fanyongfangshi_value.setVisibility(View.GONE);
			tv_info_detail_zhangdanriqi.setVisibility(View.GONE);
			tv_info_detail_zhangdanriqi_value.setVisibility(View.GONE);
			tv_info_detail_huilv_value.setVisibility(View.GONE);
			tv_info_detail_hulv.setVisibility(View.GONE);
		}else if(style.equals(styles[2])){
//			tv_info_detail_name.setText("佣金提现");
			tv_info_detail_name_add.setVisibility(View.GONE);
			iv_info_detail_arrow.setVisibility(View.GONE);
			
			tv_info_detail_baodanbianhao.setVisibility(View.GONE);
			tv_info_detail_baodanbianhao_value.setVisibility(View.GONE);
			tv_info_detail_kehuname.setVisibility(View.GONE);
			tv_info_detail_kehuname_value.setVisibility(View.GONE);
			tv_info_detail_dakuanjine.setVisibility(View.GONE);
			tv_info_detail_dakuanjine_value.setVisibility(View.GONE);
			tv_info_detail_fujiabaoxian.setVisibility(View.GONE);
			tv_info_detail_fujiabaoxian_value.setVisibility(View.GONE);
			tv_info_detail_jiaofeinianqi.setVisibility(View.GONE);
			tv_info_detail_jiaofeinianqi_value.setVisibility(View.GONE);
			tv_info_detail_fanyongbili.setVisibility(View.GONE);
			tv_info_detail_fanyongbili_value.setVisibility(View.GONE);
			tv_info_detail_fanyongfangshi.setVisibility(View.GONE);
			tv_info_detail_fanyongfangshi_value.setVisibility(View.GONE);
			tv_info_detail_zhangdanriqi.setVisibility(View.GONE);
			tv_info_detail_zhangdanriqi_value.setVisibility(View.GONE);
			tv_info_detail_huilv_value.setVisibility(View.GONE);
			tv_info_detail_hulv.setVisibility(View.GONE);
			tv_info_detail_fanyongjine_value.setVisibility(View.GONE);
			tv_info_detail_fanyongjine.setVisibility(View.GONE);
		}else if(style.equals(styles[3])){
//			tv_info_detail_name.setText("百万身价惠民两全保险");
			tv_info_detail_name_add.setVisibility(View.GONE);
			
			tv_info_detail_tixianfangshi.setVisibility(View.GONE);
			tv_info_detail_tixianfangshi_value.setVisibility(View.GONE);
			tv_info_detail_tixianzhanghu.setVisibility(View.GONE);
			tv_info_detail_tixianzhanghu_value.setVisibility(View.GONE);
			tv_info_detail_tixianzhanghu_1.setVisibility(View.GONE);
			tv_info_detail_tixianzhanghu_1_value.setVisibility(View.GONE);
			tv_info_detail_zhangdanriqi.setVisibility(View.GONE);
			tv_info_detail_zhangdanriqi_value.setVisibility(View.GONE);
			tv_info_detail_fanyongjine_value.setVisibility(View.GONE);
			tv_info_detail_fanyongjine.setVisibility(View.GONE);
		}
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_myperson_info_detail))
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
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	public class MyAdapter extends BaseAdapter{
		private ArrayList<String> in_fangyongbiliList;
		public MyAdapter(ArrayList<String> in_fangyongbili) {
			this.in_fangyongbiliList = in_fangyongbili;
		}

		@Override
		public int getCount() {
			if(in_fangyongbiliList!=null){
				return in_fangyongbiliList.size();
			}else{
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			return in_fangyongbiliList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView view;
			
			if(convertView==null){
				view = new TextView(InfoDetailActivity.this);
				convertView = view;
			}else{
				view = (TextView) convertView;
			}
			view.setText(in_fangyongbiliList.get(position));
			view.setTextSize(10);
			view.setTextColor(getResources().getColor(R.color.txt_gray_d));
			return convertView;
		}
		
	}
	public class MyAdapter_value extends BaseAdapter{
		private ArrayList<String> in_fangyongbiliList;
		public MyAdapter_value(ArrayList<String> in_fangyongbili) {
			this.in_fangyongbiliList = in_fangyongbili;
		}
		
		@Override
		public int getCount() {
			if(in_fangyongbiliList!=null){
				return in_fangyongbiliList.size();
			}else{
				return 0;
			}
		}
		
		@Override
		public Object getItem(int position) {
			return in_fangyongbiliList.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView view;
			
			if(convertView==null){
				view = new TextView(InfoDetailActivity.this);
				convertView = view;
			}else{
				view = (TextView) convertView;
			}
			view.setText(in_fangyongbiliList.get(position));
			view.setTextSize(10);
			return convertView;
		}
		
	}
	
	/**
	* 动态设置ListView的高度
	* @param listView
	*/
	public static void setListViewHeightBasedOnChildren(ListView listView,int dividerHeight,ListView listView2) {
	    if(listView == null) 
	    	return;
	    ListAdapter listAdapter = listView.getAdapter();
	    if (listAdapter == null) {
	        // pre-condition
	        return;
	    }
	    int totalHeight = 0;
	    for (int i = 0; i < listAdapter.getCount(); i++) {
	        View listItem = listAdapter.getView(i, null, listView);
	        listItem.measure(0, 0);
	        totalHeight += (listItem.getMeasuredHeight()+dividerHeight);
	    }
	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))+5;
	    listView.setLayoutParams(params);
	    listView2.setLayoutParams(params);
	}
	
}
