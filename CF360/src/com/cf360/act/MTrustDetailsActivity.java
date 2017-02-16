package com.cf360.act;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.bean.ResultXinTuoItemContentBean;
import com.cf360.bean.ResultXinTuoListViewItem;
import com.cf360.mould.BaseParams;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.types.MouldList;
import com.cf360.popupwindow.ExpandTabView;
import com.cf360.popupwindow.ViewLeft;
import com.cf360.popupwindow.ViewRight;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.MyProgressBar;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 信托产品列表
 *
 * @author hasee
 */
public class MTrustDetailsActivity extends BaseActivity {
    private ExpandTabView expandTabView;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private ViewLeft viewLeft;
    private ViewRight viewRight;
    private PullToRefreshListView mListView;
    private MouldList<ResultXinTuoListViewItem> resultListViewItem;
    private MyBaseAdapter myBaseAdapter;

    //点击 “筛选”按钮 展开页面的数据模块
    private ArrayList<String> investmentFieldLst; //投资领域数组
    private ArrayList<String> issuerLst; //发行机构数组
    private ArrayList<String> commissionLst; //佣金比例数组
    private ArrayList<String> annualRateLst; //预期收益数组

    private ArrayList<String> investmentFieldLst_1 = null;
    private ArrayList<String> issuerLst_1 = null;
    private ArrayList<String> commissionLst_1 = null;
    private ArrayList<String> annualRateLst_1 = null;

    private ResultXinTuoItemContentBean data;
    private ArrayList<String> mTextArray; //排序、筛选 数组
    private int infoPage = 1;
    private String filterType = "0"; // 0代表排序，1筛选
    private String SdefaultType;
    private String commission;
    private String issuer;
    private String investmentField;
    private String annualRate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.m_trust_details_activity);
        initTopTitle();

    }

    private void initTopTitle() {
        TitleBar title = (TitleBar) findViewById(R.id.rl_title);
        title.showLeftImg(true);
        title.addAction(new Action(2, 0, R.drawable.search));
        title.setCenterText(getResources().getString(R.string.xintuo_Details_title)).setLogo(R.drawable.img_logo, false).setIndicator(R.drawable.back).showMore(true).setOnActionListener(new OnActionListener() {
            @Override
            public void onBack() {
                finish();
            }

            @Override
            public void onAction(int id) {
                switch (id) {
                    case 2:
                        Intent Search = new Intent();
                        Search.setClass(MTrustDetailsActivity.this, SearchProductActivity.class);//搜索图标  点击监听
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

    private void initView() {
        ActivityStack stack = ActivityStack.getActivityManage();
        stack.addActivity(this);

        requestData("信托", SdefaultType, filterType, infoPage);
//		viewRight = new ViewRight(
//				MTrustDetailsActivity.this,
//				investmentFieldLst, issuerLst,
//				commissionLst, annualRateLst, "投资领域",
//				"发行机构", "佣金比例", "预期收益");
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("默认排序");
        arrayList.add("佣金最高");
        arrayList.add("评级最优");
        arrayList.add("抵/质押率最高");
        // investmentFieldLst =new MouldList<>();
        resultListViewItem = new MouldList<ResultXinTuoListViewItem>();
        expandTabView = (ExpandTabView) findViewById(R.id.title);
        viewLeft = new ViewLeft(this, arrayList);
        mListView = (PullToRefreshListView) findViewById(R.id.listview);

        btn_net_fail_refresh.setOnClickListener(new OnClickListener() { // “重新加载按钮” 的点击监听
            @Override
            public void onClick(View v) {
                initView();
                initData();
                myBaseAdapter.notifyDataSetChanged();
                netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
                llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
            }
        });

    }

    private void initData() {
        // Toast.makeText(MTrustDetailsActivity.this, "initdadta", 0).show();
        mTextArray = new ArrayList<String>();
        mTextArray.add("排序");
        mTextArray.add("筛选");
        myBaseAdapter = new MyBaseAdapter(resultListViewItem, MTrustDetailsActivity.this);
        mListView.setAdapter(myBaseAdapter);
        mListView.setOnItemClickListener(new MyItemClickListener());
        mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    if (filterType.equals("0")) {
                        if (infoPage >= 2) {
                            requestData("信托", SdefaultType, "0", (--infoPage));
                        } else {
                            requestData("信托", SdefaultType, "0", 1);
                        }
                    } else if (filterType.equals("1")) {
                        if (infoPage >= 2) {
                            requestSaiXuanData("信托", "defaultType", "1", (--infoPage), annualRate, commission, investmentField, issuer);
                        } else {
                            requestSaiXuanData("信托", "defaultType", "1", 1, annualRate, commission, investmentField, issuer);
                        }
                    }
                } else {
                    if (filterType.equals("0")) {
                        requestData("信托", SdefaultType, "0", (++infoPage));
                    } else if (filterType.equals("1")) {
                        requestSaiXuanData("信托", "defaultType", "1", (++infoPage), annualRate, commission, investmentField, issuer);

                    }
                }
            }
        });
    }

    class MyItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            String recruitmentProcess = resultListViewItem.get(position - 1).getRecruitmentProcess();
            String id = resultListViewItem.get(position - 1).getId();
            String Amount = resultListViewItem.get(position - 1).getAmount();
            String availableAmount = resultListViewItem.get(position - 1).getAvailableAmount();
            String creditLevel = resultListViewItem.get(position - 1).getCreditLevel();
            Intent intent = new Intent(MTrustDetailsActivity.this, TrustDetailsActivity.class);
            intent.putExtra("Mtrustid", id);
            intent.putExtra("ProgressBar", recruitmentProcess);
            intent.putExtra("Amount", Amount);
            intent.putExtra("availableAmount", availableAmount);
            intent.putExtra("creditLevel", creditLevel);
            startActivity(intent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        initData();
        myBaseAdapter.notifyDataSetChanged();
    }

    class MyBaseAdapter extends BaseAdapter {
        private Context mContext;
        private MouldList<ResultXinTuoListViewItem> resultListViewItem;

        public MyBaseAdapter(MouldList<ResultXinTuoListViewItem> resultListViewItem, Context mContext) {
            super();
            this.resultListViewItem = resultListViewItem;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return resultListViewItem == null ? 0 : resultListViewItem.size();
        }

        @Override
        public Object getItem(int position) {
            return resultListViewItem == null ? null : resultListViewItem.get(position);
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
                convertView = LayoutInflater.from(this.mContext).inflate(R.layout.m_item_xintuoadapter, null, false);
                holder.Title_content_text = (TextView) convertView.findViewById(R.id.xintuo_title_content);
                holder.ATypeImage = (ImageView) convertView.findViewById(R.id.xintuo_type_image);
                holder.btn_xintuo_type_Include = (Button) convertView.findViewById(R.id.btn_xintuo_type_Include);
                holder.btn_xintuo_type_Hot = (Button) convertView.findViewById(R.id.btn_xintuo_type_Hot);
                holder.btn_xintuo_type_Branch = (Button) convertView.findViewById(R.id.btn_xintuo_type_Branch);
                holder.Content_Money = (TextView) convertView.findViewById(R.id.xintuo_content_first_one);
                holder.Content_Month = (TextView) convertView.findViewById(R.id.xintuo_content_Second_one);
                holder.btn_xintuo_type_tuijian = (Button) convertView.findViewById(R.id.btn_xintuo_type_tuijian);
                holder.Content_Proportion = (TextView) convertView.findViewById(R.id.xintuo_content_Third_one);
                holder.mProgressBar = (MyProgressBar) convertView.findViewById(R.id.xintuo_ProgressBar);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String creditLevel = resultListViewItem.get(position).getCreditLevel();
            // Toast.makeText(MTrustDetailsActivity.this, creditLevel+"",
            // 0).show();
            Creditlevel(creditLevel, holder.ATypeImage, position);
            holder.Title_content_text.setText(resultListViewItem.get(position).getName());
            holder.Content_Money.setText(resultListViewItem.get(position).getAmount());
            holder.Content_Month.setText(resultListViewItem.get(position).getTimeLimit());

            if (PreferenceUtil.isAuditStatus().equals("success")) {
                if (PreferenceUtil.isLogin()) {
                    if (PreferenceUtil.getUserType().equals("corp")) {
                        holder.Content_Proportion.setText(resultListViewItem.get(position).getBackCommission());
                    } else {
                        holder.Content_Proportion.setText(resultListViewItem.get(position).getFormCommission());
                    }
                } else {
                    holder.Content_Proportion.setText("认证可见");
                }
            } else {
                holder.Content_Proportion.setText("认证可见");
            }

            holder.mProgressBar.setProgress(new Integer(resultListViewItem.get(position).getRecruitmentProcess()));
            if (resultListViewItem.get(position).getSaleType().equals("0")) {
                // 包销
                holder.btn_xintuo_type_Branch.setVisibility(View.GONE); //分销
                holder.btn_xintuo_type_Include.setVisibility(View.VISIBLE);// 包销
            } else {
                // 分销
                holder.btn_xintuo_type_Include.setVisibility(View.GONE);
                holder.btn_xintuo_type_Branch.setVisibility(View.VISIBLE);
            }
            // 是否热销
            if (resultListViewItem.get(position).getSellingStatus().equals("1")) {
                holder.btn_xintuo_type_Hot.setVisibility(View.VISIBLE);
            } else {
                holder.btn_xintuo_type_Hot.setVisibility(View.GONE);
            }
            // 是否推荐
            if (resultListViewItem.get(position).getRecommendStatus().equals("1")) {
                holder.btn_xintuo_type_tuijian.setVisibility(View.VISIBLE);
            } else {
                holder.btn_xintuo_type_tuijian.setVisibility(View.GONE);
            }
            return convertView;
        }

        private class ViewHolder {
            TextView Title_content_text;
            Button btn_xintuo_type_Branch; //分销
            Button btn_xintuo_type_Include; //包销
            Button btn_xintuo_type_Hot; //热销
            TextView Content_Money;
            TextView Content_Month;
            TextView Content_Proportion;
            MyProgressBar mProgressBar;
            Button btn_xintuo_type_tuijian;
            ImageView ATypeImage;

        }

    }

    private void requestData(String category, String defaultType, String filterType, int pageNo) {
        HtmlRequest.xinItemResult(MTrustDetailsActivity.this, category, defaultType, filterType, pageNo, new OnRequestListener() {
            private ResultXinTuoItemContentBean data;

            @Override
            public void onRequestFinished(BaseParams params) {
                if (params.result != null) {
                    data = (ResultXinTuoItemContentBean) params.result;
                    // resultListViewItem = data.getTrustList();
                    if (data != null) {
                        if (TextUtils.isEmpty(data.getAuditStatus())) { //判断用户是否认证;
                            PreferenceUtil.setAuditStatus(null);
                        } else {
                            PreferenceUtil.setAuditStatus(data.getAuditStatus());
                        }
                    }

                    investmentFieldLst = data.getInvestmentFieldLst();
                    issuerLst = data.getIssuerLst();
                    commissionLst = data.getCommissionLst();
                    annualRateLst = data.getAnnualRateLst();
                    if (!(investmentFieldLst.equals(investmentFieldLst_1) && issuerLst.equals(issuerLst_1) && commissionLst.equals(commissionLst_1) && annualRateLst.equals(annualRateLst_1))) {
                        viewRight = new ViewRight(MTrustDetailsActivity.this, investmentFieldLst, issuerLst, commissionLst, annualRateLst, "投资领域", "发行机构", "佣金比例", "预期收益");
                        investmentFieldLst_1 = investmentFieldLst;
                        issuerLst_1 = issuerLst;
                        commissionLst_1 = commissionLst;
                        annualRateLst_1 = annualRateLst;
                    }

                    if (mViewArray.size() <= 0) {
                        mViewArray.add(viewLeft);
                        mViewArray.add(viewRight);
                        expandTabView.setValue(mTextArray, mViewArray);
                    }
                    initListener();

                    if (data != null) {
                        if (data.getTrustList().size() == 0 && infoPage != 1) {
                            Toast.makeText(MTrustDetailsActivity.this, "已经到最后一页", Toast.LENGTH_SHORT).show();
                            infoPage--;
                            myBaseAdapter.notifyDataSetChanged();
                            mListView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mListView.onRefreshComplete();
                                }
                            }, 1000);
                            mListView.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
                        } else {
                            resultListViewItem.clear();
                            resultListViewItem.addAll(data.getTrustList());
                            myBaseAdapter.notifyDataSetChanged();
                            mListView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mListView.onRefreshComplete();
                                }
                            }, 1000);
                            mListView.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
                        }
                    }
                } else {
                    netFail = true;
                    mListView.onRefreshComplete();
                    Toast.makeText(MTrustDetailsActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    //请求信托筛选数据
    private void requestSaiXuanData(String category, String defaultType, String filterType, int pageNo, String annualRate, String commission, String investmentField, String issuer) {
        HtmlRequest.xinSaiItemResult(MTrustDetailsActivity.this, category, defaultType, filterType, pageNo, annualRate, commission, investmentField, issuer, new OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params.result != null) {
                    data = (ResultXinTuoItemContentBean) params.result;
                            /*
                             * investmentFieldLst =
							 * data.getInvestmentFieldLst(); issuerLst =
							 * data.getIssuerLst(); commissionLst =
							 * data.getCommissionLst(); annualRateLst =
							 * data.getAnnualRateLst(); viewRight = new
							 * ViewRight
							 * (MTrustDetailsActivity.this,investmentFieldLst
							 * ,issuerLst,commissionLst,annualRateLst, "投资领域",
							 * "发行机构", "佣金比例", "预期收益");
							 * if(mViewArray.size()<=0){
							 * mViewArray.add(viewLeft);
							 * mViewArray.add(viewRight);
							 * expandTabView.setValue(mTextArray, mViewArray); }
							 * initListener();
							 */
                    if (data != null) {
                        if (data.getTrustList().size() == 0 && infoPage != 1) {
                            Toast.makeText(MTrustDetailsActivity.this, "已经到最后一页", Toast.LENGTH_SHORT).show();
                            infoPage--;
                            myBaseAdapter.notifyDataSetChanged();
                            mListView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mListView.onRefreshComplete();
                                }
                            }, 1000);
                            mListView.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
                        } else {
                            resultListViewItem.clear();
                            resultListViewItem.addAll(data.getTrustList());
                            myBaseAdapter.notifyDataSetChanged();
                            mListView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mListView.onRefreshComplete();
                                }
                            }, 1000);
                            mListView.getRefreshableView().smoothScrollToPositionFromTop(0, 100, 100);
                        }

                    }
                }

            }
        });
    }

    public void Creditlevel(String creditLevel, ImageView A, int position) {
        if (!TextUtils.isEmpty(creditLevel)) {
            if (creditLevel.equals("1")) {
                A.setBackgroundResource(R.drawable.img_no);
            } else if (creditLevel.equals("2")) {
                A.setBackgroundResource(R.drawable.img_aaa);
            } else if (creditLevel.equals("3")) {
                A.setBackgroundResource(R.drawable.img_aa);
                A.setVisibility(View.VISIBLE);
            } else if (creditLevel.equals("4")) {
                A.setBackgroundResource(R.drawable.img_a);
            } else if (creditLevel.equals("7")) {
                A.setBackgroundResource(R.drawable.img_b);
                A.setVisibility(View.VISIBLE);
            } else if (creditLevel.equals("6")) {
                A.setBackgroundResource(R.drawable.img_bb);
                A.setVisibility(View.VISIBLE);
            } else if (creditLevel.equals("5")) {
                A.setBackgroundResource(R.drawable.img_bb);
                A.setVisibility(View.VISIBLE);
            } else if (creditLevel.equals("10")) {
                A.setBackgroundResource(R.drawable.img_c);
                A.setVisibility(View.VISIBLE);
            } else if (creditLevel.equals("9")) {
                A.setBackgroundResource(R.drawable.img_cc);
                A.setVisibility(View.VISIBLE);
            } else if (creditLevel.equals("8")) {
                A.setBackgroundResource(R.drawable.img_ccc);
                A.setVisibility(View.VISIBLE);
            } else {
                A.setBackgroundResource(R.drawable.img_d);
            }

        }

    }

    private void initListener() {
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
                if (TextUtils.isEmpty(annualRate)) {
                    annualRate = "不限";
                }
                if (TextUtils.isEmpty(commission)) {
                    commission = "不限";
                }
                if (TextUtils.isEmpty(investmentField)) {
                    investmentField = "不限";
                }
                if (TextUtils.isEmpty(issuer)) {
                    issuer = "不限";
                }
                filterType = "1";
                infoPage = 1;
                requestSaiXuanData("信托", "defaultType", filterType, infoPage, annualRate, commission, investmentField, issuer);
                viewLeft.ReturnState();
                // Toast.makeText(MTrustDetailsActivity.this,commission+investmentField+issuer+annualRate,
                // 0).show();
                myBaseAdapter.notifyDataSetChanged();
                onBackPressed();
            }

            @Override
            public void getGridview1(View veiw, int position) {
                // Toast.makeText(MTrustDetailsActivity.this,
                // investmentFieldLst.get(position).toString(), 0).show();
                investmentField = investmentFieldLst.get(position).toString();
            }

            @Override
            public void getGridview2(View veiw, int position) {
                issuer = issuerLst.get(position).toString();
                // Toast.makeText(MTrustDetailsActivity.this,issuer, 0).show();

            }

            @Override
            public void getGridview3(View veiw, int position) {
                commission = commissionLst.get(position).toString();
                // Toast.makeText(MTrustDetailsActivity.this,commission,
                // 0).show();

            }

            @Override
            public void getGridview4(View veiw, int position) {
                annualRate = annualRateLst.get(position).toString();
                // Toast.makeText(MTrustDetailsActivity.this,issuer, 0).show();

            }

        });

        viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {
            @Override
            public void getValue(View view, int position) {
                filterType = "0";
                // flag=false;
                // viewRight
                viewRight.ReturnState1();
                switch (position) {
                    case 0:
                        SdefaultType = "defaultType";
                        infoPage = 1;
                        requestData("信托", SdefaultType, filterType, infoPage);
                        viewRight.ReturnState1();
                        myBaseAdapter.notifyDataSetChanged();
                        onBackPressed();
                        break;
                    case 1:
                        SdefaultType = "commissionMax";
                        infoPage = 1;
                        requestData("信托", "commissionMax", filterType, infoPage);
                        viewRight.ReturnState1();
                        myBaseAdapter.notifyDataSetChanged();
                        onBackPressed();
                        break;
                    case 2:
                        SdefaultType = "creditLevelMax";
                        infoPage = 1;
                        requestData("信托", "creditLevelMax", filterType, infoPage);
                        viewRight.ReturnState1();
                        myBaseAdapter.notifyDataSetChanged();
                        onBackPressed();
                        break;
                    case 3:
                        SdefaultType = "serviceFeeRateMax";
                        infoPage = 1;
                        requestData("信托", "serviceFeeRateMax", filterType, infoPage);
                        viewRight.ReturnState1();
                        myBaseAdapter.notifyDataSetChanged();
                        onBackPressed();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (!expandTabView.onPressBack()) {
            finish();
        }
    }

}
