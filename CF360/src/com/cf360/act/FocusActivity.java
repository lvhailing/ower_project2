package com.cf360.act;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.adapter.FocusAdapter;
import com.cf360.adapter.NewsViewPagerAdapter;
import com.cf360.bean.ResultFocusContentBean;
import com.cf360.bean.ResultFocusContentListBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.NewsTitleTextView;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 我关注的
 */
public class FocusActivity extends BaseActivity implements OnClickListener,
        OnPageChangeListener {

    private int pro_page = 1;
    private int cachePage_pro = pro_page;
    private String category;
    private ResultFocusContentBean data;
    private PullToRefreshListView listView;
    private BaseAdapter adapter;
    private MouldList<ResultFocusContentListBean> list;

    private ViewPager mViewPager;
    private ViewGroup mViewGroup;
    private NewsViewPagerAdapter mAdapter;
    private String[] mTabItems = new String[]{"全部", "信托", "资管", "阳光私募",
            "私募股权", "保险", ""};
    private int mPreSelectItem;
    View view1;
    View view2;
    View view3;
    View view4;
    View view5;
    View view6;

    private ActivityStack stack;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_focus);
        initTopTitle();
        initView();

        addViewPagerView();
        initData();
    }

    public void initView(){

        stack = ActivityStack.getActivityManage();
        stack.addActivity(FocusActivity.this);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewGroup = (ViewGroup) findViewById(R.id.viewgroup);
    }


    @SuppressLint("NewApi")
    private void addViewPagerView() {
        LayoutParams params = new LayoutParams();
        params.width = LayoutParams.WRAP_CONTENT;
        params.height = LayoutParams.WRAP_CONTENT;

        ArrayList<View> newview = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.focus_listview, null);
        view2 = inflater.inflate(R.layout.focus_listview, null);
        view3 = inflater.inflate(R.layout.focus_listview, null);
        view4 = inflater.inflate(R.layout.focus_listview, null);
        view5 = inflater.inflate(R.layout.focus_listview, null);
        view6 = inflater.inflate(R.layout.focus_listview, null);

        newview.add(view1);
        newview.add(view2);
        newview.add(view3);
        newview.add(view4);
        newview.add(view5);
        newview.add(view6);

        for (int i = 0; i < mTabItems.length; i++) {

            String label = mTabItems[i];

            NewsTitleTextView tv = new NewsTitleTextView(this);
            int itemWidth = (int) tv.getPaint().measureText(label);
            tv.setLayoutParams(new LinearLayout.LayoutParams((itemWidth * 2),
                    -1));
            tv.setTextSize(16);
            tv.setText(label);
            tv.setGravity(Gravity.CENTER);
            tv.setOnClickListener(this);
            if (i == 0) {
                tv.setTextColor(getResources().getColor(R.color.orange));
                tv.setIsHorizontaline(true);
            } else {
                tv.setTextColor(getResources().getColor(R.color.gray_d));
                tv.setIsHorizontaline(false);
            }
            tv.setIsVerticalLine(true);
            mViewGroup.addView(tv);
        }

        mAdapter = new NewsViewPagerAdapter(this, newview);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(this);

        btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addViewPagerView();
                initData();
//				myEquityBaseAdapter.notifyDataSetChanged();
                netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
                llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
            }
        });

    }

    private void initData() {
        switch (mPreSelectItem) {
            case 0:
                category = "全部";
                listView = (PullToRefreshListView) view1
                        .findViewById(R.id.listview_focus);
                pro_page = 1;
                break;
            case 1:
                category = "信托";
                listView = (PullToRefreshListView) view2
                        .findViewById(R.id.listview_focus);
                pro_page = 1;
                break;
            case 2:
                category = "资管";
                listView = (PullToRefreshListView) view3
                        .findViewById(R.id.listview_focus);
                pro_page = 1;
                break;
            case 3:
                category = "阳光私募";
                listView = (PullToRefreshListView) view4
                        .findViewById(R.id.listview_focus);
                pro_page = 1;
                break;
            case 4:
                category = "私募股权";
                listView = (PullToRefreshListView) view5
                        .findViewById(R.id.listview_focus);
                pro_page = 1;
                break;
            case 5:
                category = "保险";
                listView = (PullToRefreshListView) view6
                        .findViewById(R.id.listview_focus);
                pro_page = 1;
                break;

            default:
                break;
        }
        list = new MouldList<ResultFocusContentListBean>();
        requestData(category, 1);
        listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    if (pro_page >= 2) {
                        requestData(category, pro_page--);
                    } else {
                        requestData(category, 1);
                    }

                } else {
                    requestData(category, pro_page++);
                }

            }
        });
        adapter = new FocusAdapter(this, list);
        /*
         * try { new Thread().sleep(1000);
		 */
        listView.setAdapter(adapter);
		/*
		 * } catch (InterruptedException e) { e.printStackTrace(); }
		 */

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ResultFocusContentListBean bean = list.get(position - 1);
                String category = bean.getCATEGORY();
                if ("0".equals(bean.getUPPER_AND_LOWER_FRAME())) {

                    if ("valid".equals(bean.getDATA_STATUS())) {
                        if (category.equals("信托")) {
                            Intent intent = new Intent(FocusActivity.this,
                                    TrustDetailsActivity.class);
                            intent.putExtra("Mtrustid", bean.getID());
                            intent.putExtra("ProgressBar", bean.getRECRUITMENTPROCESS());
                            intent.putExtra("Amount", bean.getAMOUNT());
                            // availableAmount
                            intent.putExtra("availableAmount", bean.getCREDITLEVEL());
                            // intent.putExtra("creditLevel",creditLevel);
                            startActivity(intent);
                        } else if (category.equals("资管")) {
                            Intent intent = new Intent(FocusActivity.this,
                                    ZiGuanItemActivity.class);
                            intent.putExtra("Mtrustid", bean.getID());
                            intent.putExtra("ProgressBar", bean.getRECRUITMENTPROCESS());
                            intent.putExtra("Amount", bean.getAMOUNT());
                            startActivity(intent);
                        } else if (category.equals("ygsm")) {
                            Intent intent = new Intent(FocusActivity.this,
                                    SunshineActivity.class);
                            intent.putExtra("Sunshine", bean.getID());
                            startActivity(intent);
                        } else if (category.equals("smgq")) {
                            Intent intent = new Intent(FocusActivity.this,
                                    EquityItmeActivity.class);
                            intent.putExtra("EquityId", bean.getID());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(FocusActivity.this,
                                    InsuranceItmeActivity.class);
                            intent.putExtra("id", bean.getID());
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(FocusActivity.this,
                                "该产品已删除", Toast.LENGTH_SHORT)
                                .show();
                    }
                } else {
                    Toast.makeText(FocusActivity.this,
                            "该产品已下架", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        // 点击tabbar
        for (int i = 0; i < mViewGroup.getChildCount(); i++) {
            NewsTitleTextView child = (NewsTitleTextView) mViewGroup
                    .getChildAt(i);
            if (child == v) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int selectPosition) {
        moveTitleLabel(selectPosition);
    }

    /*
     * 点击新闻分类的tabbar，使点击的bar居中显示到屏幕中间
     */
    @SuppressLint("NewApi")
    private void moveTitleLabel(int position) {

        // 点击当前按钮所有左边按钮的总宽度
        int visiableWidth = 0;
        // HorizontalScrollView的宽度
        int scrollViewWidth = 0;

        mViewGroup.measure(mViewGroup.getMeasuredWidth(), -1);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                mViewGroup.getMeasuredWidth(), -1);
        params.gravity = Gravity.CENTER_VERTICAL;
        mViewGroup.setLayoutParams(params);
        for (int i = 0; i < mViewGroup.getChildCount(); i++) {
            NewsTitleTextView itemView = (NewsTitleTextView) mViewGroup
                    .getChildAt(i);
            int width = itemView.getMeasuredWidth();
            if (i < position) {
                visiableWidth += width;
            }
            scrollViewWidth += width;

            if (i == mViewGroup.getChildCount() - 1) {
                break;
            }
            if (position != i) {
                itemView.setTextColor(getResources().getColor(R.color.gray_d));
                itemView.setIsHorizontaline(false);
            } else {
                itemView.setTextColor(getResources().getColor(R.color.orange));
                itemView.setIsHorizontaline(true);
            }
        }
        // 当前点击按钮的宽度
        int titleWidth = mViewGroup.getChildAt(position).getMeasuredWidth();
        int nextTitleWidth = 0;
        if (position > 0) {
            // 当前点击按钮相邻右边按钮的宽度
            nextTitleWidth = position == mViewGroup.getChildCount() - 1 ? 0
                    : mViewGroup.getChildAt(position - 1).getMeasuredWidth();
        }
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        final int move = visiableWidth - (screenWidth - titleWidth) / 2;
        if (mPreSelectItem < position) {// 向屏幕右边移动
            if ((visiableWidth + titleWidth + nextTitleWidth) >= (screenWidth / 2)) {
                // new Handler().post(new Runnable() {
                //
                // @Override
                // public void run() {
                ((HorizontalScrollView) mViewGroup.getParent())
                        .setScrollX(move);
                // }
                // });

            }
        } else {// 向屏幕左边移动
            if ((scrollViewWidth - visiableWidth) >= (screenWidth / 2)) {
                ((HorizontalScrollView) mViewGroup.getParent())
                        .setScrollX(move);
            }
        }
        mPreSelectItem = position;
        switch (mPreSelectItem) {
            case 0:
                category = "全部";
                initData();
                // requestData(category, 1);
                break;
            case 1:
                category = "信托";
                initData();
                // requestData(category, 1);
                break;
            case 2:
                category = "资管";
                initData();
                // requestData(category, 1);
                break;
            case 3:
                category = "阳光私募";
                initData();
                // requestData(category, 1);
                break;
            case 4:
                category = "私募股权";
                initData();
                // requestData(category, 1);
                break;
            case 5:
                category = "保险";
                initData();
                // requestData(category, 1);
                break;

            default:
                break;
        }
    }

    private void initTopTitle() {
        TitleBar title = (TitleBar) findViewById(R.id.rl_title);
        title.showLeftImg(true);
        title.setTitle(getResources().getString(R.string.title_no))
                .setCenterText(getResources().getString(R.string.title_focus))
                .setLogo(R.drawable.img_logo, false).setIndicator(R.drawable.back)
                .showMore(true).setOnActionListener(new OnActionListener() {

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

    public void requestData(String category, int pageNo) {
        cachePage_pro = pro_page;
        data = new ResultFocusContentBean();
        HtmlRequest.getFocusProduct(FocusActivity.this, category, pro_page,
                new OnRequestListener() {
                    @Override
                    public void onRequestFinished(BaseParams params) {
                        if (params.result != null) {

                            data = (ResultFocusContentBean) params.result;

                            if (data != null) {
                                if (TextUtils.isEmpty(data.getAuditStatus())) {
                                    PreferenceUtil.setAuditStatus(null);
                                } else {
                                    PreferenceUtil.setAuditStatus(data.getAuditStatus());
                                }
                            }
                            if (data.getProductList() != null) {


                                if (data.getProductList().size() == 0
                                        && pro_page != 1) {
                                    Toast.makeText(FocusActivity.this,
                                            "已经到最后一页", Toast.LENGTH_SHORT)
                                            .show();
                                    pro_page = cachePage_pro - 1;
                                    listView.getRefreshableView()
                                            .smoothScrollToPositionFromTop(0,
                                                    80, 100);
                                    listView.onRefreshComplete();
                                } else {
                                    list.clear();
                                    list.addAll(data.getProductList());
                                    adapter.notifyDataSetChanged();
                                    listView.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            listView.onRefreshComplete();
                                        }
                                    }, 1000);
                                    listView.getRefreshableView()
                                            .smoothScrollToPositionFromTop(0,
                                                    80, 100);
                                }

                            }
                        } else {
                            netFail = true;
                            adapter.notifyDataSetChanged();
                            listView.onRefreshComplete();
                            Toast.makeText(FocusActivity.this, "加载失败，请确认网络通畅",
                                    Toast.LENGTH_LONG).show();
                        }
                        FocusActivity.this.stopLoading();
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
