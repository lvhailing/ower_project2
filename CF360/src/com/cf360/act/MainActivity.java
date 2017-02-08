package com.cf360.act;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.VjinkeApplication;
import com.cf360.adapter.CycleAdapter;
import com.cf360.adapter.HotProductAdapter;
import com.cf360.adapter.CycleAdapter.ImageCycleViewListener;
import com.cf360.adapter.RecommendProductBxAdapter;
import com.cf360.adapter.RecommendProductSmgqAdapter;
import com.cf360.adapter.RecommendProductXtAdapter;
import com.cf360.adapter.RecommendProductYgsmAdapter;
import com.cf360.adapter.RecommendProductZgAdapter;
import com.cf360.bean.ImageBean;
import com.cf360.bean.ResultAdvertiseContentBean;
import com.cf360.bean.ResultCheckVersionContentBean;
import com.cf360.bean.ResultHotProductContentBean;
import com.cf360.bean.ResultHotProductContentTwoBean;
import com.cf360.bean.ResultMyMessageCountContentBean;
import com.cf360.bean.ResultRecommendProductContentBean;
import com.cf360.bean.ResultRecommendProductContentTwo;
import com.cf360.bean.ResultUserLoginContentBean;
import com.cf360.fragment.MenuLeftFragment;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.net.UserLogin;
import com.cf360.service.AppUpgradeService;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.uitls.StringUtil;
import com.cf360.uitls.SystemInfo;
import com.cf360.view.MyListView;
import com.cf360.view.TitleBar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 首页
 */
public class MainActivity extends BaseMainActivity implements OnClickListener, Observer {
    private TitleBar title;
    private MyListView lv;
    private LinearLayout mViewpager;
    private LinearLayout mLinearLayout;
    private CycleAdapter cycleAdapter;
    private DisplayImageOptions options;
    private Resources mResource;
    private int productPage = 1;
    private Button btn_hot, btn_recommend;
    private TextView txt_trust, txt_management, txt_private_placement, txt_all, id_iv_right;

    private ArrayList<ImageBean> images;
    private ImageBean imgbean = null;
    private LinearLayout ll_home_recommend_product;
    private ScrollView scroll_main;
    private MyListView lv_home_product_trustxt, lv_home_product_trustzg, lv_home_product_trustygsm, lv_home_product_trustsmgq, lv_home_product_trustbx;
    private BaseAdapter lv_home_product_trustxtAdapter, lv_home_product_trustzgAdapter, lv_home_product_trustygsmAdapter, lv_home_product_trustsmgqAdapter, lv_home_product_trustbxAdapter;
    private ResultUserLoginContentBean bean;
    private LinearLayout ll_home_product_name_trustxt, ll_home_product_name_trustzg, ll_home_product_name_trustygsm, ll_home_product_name_trustsmgq, ll_home_product_name_bx;

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private WindowManager.LayoutParams wl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserLogin.getInstance().addObserver(this);
        wl = getWindow().getAttributes();
        registerBoradcastReceiver();
//		setTheme(R.style.Dialog);
        // 初始化SlidingMenu
        initView();
        initLeftMenu();
        initData();
        requestData();
    }

    private void initView() {
        lv_home_product_trustxt = (MyListView) findViewById(R.id.lv_home_product_trustxt);
        lv_home_product_trustzg = (MyListView) findViewById(R.id.lv_home_product_trustzg);
        lv_home_product_trustygsm = (MyListView) findViewById(R.id.lv_home_product_trustygsm);
        lv_home_product_trustsmgq = (MyListView) findViewById(R.id.lv_home_product_trustsmgq);
        lv_home_product_trustbx = (MyListView) findViewById(R.id.lv_home_product_trustbx);

        ll_home_product_name_trustxt = (LinearLayout) findViewById(R.id.ll_home_product_name_trustxt);
        ll_home_product_name_trustzg = (LinearLayout) findViewById(R.id.ll_home_product_name_trustzg);
        ll_home_product_name_trustygsm = (LinearLayout) findViewById(R.id.ll_home_product_name_trustygsm);
        ll_home_product_name_trustsmgq = (LinearLayout) findViewById(R.id.ll_home_product_name_trustsmgq);
        ll_home_product_name_bx = (LinearLayout) findViewById(R.id.ll_home_product_name_bx);

        scroll_main = (ScrollView) findViewById(R.id.scroll_main);
        mViewpager = (LinearLayout) findViewById(R.id.viewpager);
        mLinearLayout = (LinearLayout) findViewById(R.id.down_dots_ll);
        ll_home_recommend_product = (LinearLayout) findViewById(R.id.ll_home_recommend_product);
        mResource = getResources();
        lv = (MyListView) findViewById(R.id.lv);
        txt_trust = (TextView) findViewById(R.id.main_trust);
        txt_management = (TextView) findViewById(R.id.main_management);
        txt_private_placement = (TextView) findViewById(R.id.main_private_placement);
        txt_all = (TextView) findViewById(R.id.main_all);
        id_iv_right = (TextView) findViewById(R.id.id_iv_right);
        btn_hot = (Button) findViewById(R.id.btn_hot_product);
        btn_recommend = (Button) findViewById(R.id.btn_recommend_product);


        txt_trust.setOnClickListener(this);
        txt_management.setOnClickListener(this);
        txt_private_placement.setOnClickListener(this);
        txt_all.setOnClickListener(this);
        btn_hot.setOnClickListener(this);
        btn_recommend.setOnClickListener(this);
        id_iv_right.setOnClickListener(this);

        options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.cf360_hot).showImageOnFail(R.drawable.cf360_hot).resetViewBeforeLoading(true).cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).displayer(new FadeInBitmapDisplayer(300)).build();

    }


    private void initData() {
        images = new ArrayList<ImageBean>();
        requestInPageData();
    }

    public void requestInPageData() {
        HtmlRequest.getAdvertise(MainActivity.this, new OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params.result != null) {
                    @SuppressWarnings("unchecked") MouldList<ResultAdvertiseContentBean> b = (MouldList<ResultAdvertiseContentBean>) params.result;
                    if (b != null) {
                        images.clear();
                        for (final ResultAdvertiseContentBean resultAdvertiseContentBean : b) {
                            imgbean = new ImageBean();
                            imgbean.setPicture(resultAdvertiseContentBean.getPicture());
                            imgbean.setUrl(resultAdvertiseContentBean.getUrl());
                            imgbean.setDescription(resultAdvertiseContentBean.getDescription());
                            images.add(imgbean);
                        }

                    } else {
                    }
                } else {

                }

                //轮播图
                cycleAdapter = new CycleAdapter(MainActivity.this, images, options);
                cycleAdapter.setNetAndLinearLayoutMethod(mLinearLayout);
                cycleAdapter.setOnImageListener(new ImageCycleViewListener() {
                    @Override
                    public void onImageClick(int postion, View imageView) {//图片点击事件

                    }
                });
                cycleAdapter.setCycle(true);
                cycleAdapter.startRoll();
                mViewpager.addView(cycleAdapter);
            }
        });

    }


    private MouldList<ResultHotProductContentBean> hotProductBean;

    //热销
    public void requestHotProductData() {
        HtmlRequest.getHotProduct(MainActivity.this, new OnRequestListener() {

            @Override
            public void onRequestFinished(BaseParams params) {

                @SuppressWarnings("unchecked") final ResultHotProductContentTwoBean data = (ResultHotProductContentTwoBean) params.result;

                if (data != null) {
                    hotProductBean = data.getHotProduct();
                    if (data.getAuditStatus() != null) {
                        PreferenceUtil.setAuditStatus(data.getAuditStatus());
                    } else {
                        PreferenceUtil.setAuditStatus(null);
                    }
                    if (hotProductBean != null) {
                        HotProductAdapter hotAdapter = new HotProductAdapter(MainActivity.this, hotProductBean, options);
                        lv.setAdapter(hotAdapter);
                        lv.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                Intent intent = new Intent();
                                if (hotProductBean.get(position).getCATEGORY().equals("信托")) {
                                    intent.setClass(MainActivity.this, TrustDetailsActivity.class);
                                    intent.putExtra("Mtrustid", hotProductBean.get(position).getID());

                                    String progressNum = (int) (Double.parseDouble(hotProductBean.get(position).getRECRUITMENTPROCESS()) * 100) + "";
                                    intent.putExtra("ProgressBar", progressNum);

                                    intent.putExtra("Amount", hotProductBean.get(position).getNAME3KEY());
                                } else if (hotProductBean.get(position).getCATEGORY().equals("资管")) {
                                    intent.setClass(MainActivity.this, ZiGuanItemActivity.class);
                                    intent.putExtra("Mtrustid", hotProductBean.get(position).getID());

                                    String progressNum = (int) (Double.parseDouble(hotProductBean.get(position).getRECRUITMENTPROCESS()) * 100) + "";
                                    intent.putExtra("ProgressBar", progressNum);

                                    intent.putExtra("Amount", hotProductBean.get(position).getNAME3KEY());
                                } else if (hotProductBean.get(position).getCATEGORY().equals("ygsm")) {
                                    intent.setClass(MainActivity.this, SunshineActivity.class);
                                    intent.putExtra("Sunshine", hotProductBean.get(position).getID());
                                } else if (hotProductBean.get(position).getCATEGORY().equals("smgq")) {
                                    intent.setClass(MainActivity.this, EquityItmeActivity.class);
                                    intent.putExtra("EquityId", hotProductBean.get(position).getID());
                                } else {
                                    intent.setClass(MainActivity.this, InsuranceItmeActivity.class);
                                    intent.putExtra("id", hotProductBean.get(position).getID());
                                    intent.putExtra("commissionratio", hotProductBean.get(position).getNAME5KEY());
                                }
                                startActivity(intent);

                            }
                        });
//								setListViewHeightBasedOnChildren(MainActivity.this,lv,10);
                    } else {

                    }
                } else {
                    Toast.makeText(MainActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    //我的消息数量
    public void requestMyMessageCountData() {
        String Mobile = null;
        try {
            Mobile = DESUtil.decrypt(PreferenceUtil.getPhone());
        } catch (Exception e) {
            e.printStackTrace();
        }
        HtmlRequest.getMyMessageCount(MainActivity.this, Mobile, new OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {

                ResultMyMessageCountContentBean bean = (ResultMyMessageCountContentBean) params.result;
                if (bean != null) {
                    if (bean.getUnReadyCount().equals("0")) {
                        id_iv_right.setText("");
                    } else {
                        id_iv_right.setText(bean.getUnReadyCount());
                    }

                } else {

                }

            }
        });
    }


    private MouldList<MouldList<ResultRecommendProductContentBean>> recommendBean;

    //推荐产品
    public void requestRecommendProductData() {
        HtmlRequest.getRecommendProduct(MainActivity.this, new OnRequestListener() {

            @Override
            public void onRequestFinished(BaseParams params) {

                ResultRecommendProductContentTwo data = (ResultRecommendProductContentTwo) params.result;

                if (data != null) {
                    recommendBean = data.getRecommendProduct();

                    if (TextUtils.isEmpty(data.getAuditStatus())) {
                        PreferenceUtil.setAuditStatus(null);
                    } else {
                        PreferenceUtil.setAuditStatus(data.getAuditStatus());
                    }
                    if (recommendBean != null) {
                        ll_home_recommend_product.setVisibility(View.VISIBLE);
                        lv_home_product_trustxtAdapter = new RecommendProductXtAdapter(MainActivity.this, recommendBean.get(0));
                        if (recommendBean.get(0).size() == 0) {
                            ll_home_product_name_trustxt.setVisibility(View.GONE);
                        } else {
                            ll_home_product_name_trustxt.setVisibility(View.VISIBLE);
                        }
                        lv_home_product_trustxt.setAdapter(lv_home_product_trustxtAdapter);
                        lv_home_product_trustxt.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, TrustDetailsActivity.class);
                                intent.putExtra("Mtrustid", recommendBean.get(0).get(position).getID());

                                String progressNum = (int) (Double.parseDouble(recommendBean.get(0).get(position).getRECRUITMENTPROCESS()) * 100) + "";
                                intent.putExtra("ProgressBar", progressNum);

                                intent.putExtra("Amount", recommendBean.get(0).get(position).getAMOUNT());
                                startActivity(intent);

                            }
                        });
                        lv_home_product_trustzgAdapter = new RecommendProductZgAdapter(MainActivity.this, recommendBean.get(1));
                        if (recommendBean.get(1).size() == 0) {
                            ll_home_product_name_trustzg.setVisibility(View.GONE);
                        } else {
                            ll_home_product_name_trustzg.setVisibility(View.VISIBLE);
                        }
                        lv_home_product_trustzg.setAdapter(lv_home_product_trustzgAdapter);
                        lv_home_product_trustzg.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, ZiGuanItemActivity.class);
                                intent.putExtra("Mtrustid", recommendBean.get(1).get(position).getID());

                                String progressNum = (int) (Double.parseDouble(recommendBean.get(0).get(position).getRECRUITMENTPROCESS()) * 100) + "";
                                intent.putExtra("ProgressBar", progressNum);

                                intent.putExtra("Amount", recommendBean.get(1).get(position).getAMOUNT());
                                startActivity(intent);

                            }
                        });
                        lv_home_product_trustygsmAdapter = new RecommendProductYgsmAdapter(MainActivity.this, recommendBean.get(2));
                        if (recommendBean.get(2).size() == 0) {
                            ll_home_product_name_trustygsm.setVisibility(View.GONE);
                        } else {
                            ll_home_product_name_trustygsm.setVisibility(View.VISIBLE);
                        }
                        lv_home_product_trustygsm.setAdapter(lv_home_product_trustygsmAdapter);
                        lv_home_product_trustygsm.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, SunshineActivity.class);
                                intent.putExtra("Sunshine", recommendBean.get(2).get(position).getID());
                                startActivity(intent);

                            }
                        });
                        lv_home_product_trustsmgqAdapter = new RecommendProductSmgqAdapter(MainActivity.this, recommendBean.get(3));
                        if (recommendBean.get(3).size() == 0) {
                            ll_home_product_name_trustsmgq.setVisibility(View.GONE);
                        } else {
                            ll_home_product_name_trustsmgq.setVisibility(View.VISIBLE);
                        }
                        lv_home_product_trustsmgq.setAdapter(lv_home_product_trustsmgqAdapter);
                        lv_home_product_trustsmgq.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, EquityItmeActivity.class);
                                intent.putExtra("EquityId", recommendBean.get(3).get(position).getID());

                                startActivity(intent);

                            }
                        });
                        lv_home_product_trustbxAdapter = new RecommendProductBxAdapter(MainActivity.this, recommendBean.get(4));
                        if (recommendBean.get(4).size() == 0) {
                            ll_home_product_name_bx.setVisibility(View.GONE);
                        } else {
                            ll_home_product_name_bx.setVisibility(View.VISIBLE);
                        }
                        lv_home_product_trustbx.setAdapter(lv_home_product_trustbxAdapter);
                        lv_home_product_trustbx.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, InsuranceItmeActivity.class);
                                intent.putExtra("commissionratio", recommendBean.get(4).get(position).getCOMMISSION());
                                intent.putExtra("id", recommendBean.get(4).get(position).getID());
                                startActivity(intent);

                            }
                        });

                        setListViewHeightBasedOnChildren(MainActivity.this, lv_home_product_trustxt, 0);
                        setListViewHeightBasedOnChildren(MainActivity.this, lv_home_product_trustzg, 0);
                        setListViewHeightBasedOnChildren(MainActivity.this, lv_home_product_trustygsm, 0);
                        setListViewHeightBasedOnChildren(MainActivity.this, lv_home_product_trustsmgq, 0);
                        setListViewHeightBasedOnChildren(MainActivity.this, lv_home_product_trustbx, 0);

//						System.out.println(bean.toString());

                    } else {

                    }
                } else {
                    Toast.makeText(MainActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_trust:// 信托
                Intent intent = new Intent(this, MTrustDetailsActivity.class);
//			Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.main_management:// 资管
                Intent i_financial = new Intent();
                i_financial.setClass(MainActivity.this, MZiGuanActivity.class);
                startActivity(i_financial);
                break;
            case R.id.main_private_placement:// 阳光私募
                Intent i_search = new Intent();
                i_search.setClass(MainActivity.this, MSunshineActivity.class);//MSunshineActivity
                startActivity(i_search);
                break;
            case R.id.main_all:// 全部
                startActivity(new Intent(MainActivity.this, MContentAllActivity.class));

                break;

            case R.id.btn_hot_product:// 热销产品
                productPage = 1;
                initPageData("hotProduct");
                StringUtil.changeButtonStyle(btn_hot, btn_recommend, R.id.btn_hot_product, mResource);

                break;

            case R.id.btn_recommend_product:// 产品推荐
                productPage = 1;
                initPageData("recommendProduct");
                StringUtil.changeButtonStyle(btn_hot, btn_recommend, R.id.btn_recommend_product, mResource);
                break;
            case R.id.id_iv_right:
                startActivity(new Intent(this, NoticeMessageActivity.class));

                break;
            default:
                break;
        }
    }

    private void initPageData(String status) {
        if (status.equals("hotProduct")) {
            ll_home_recommend_product.setVisibility(View.GONE);
            lv.setVisibility(View.VISIBLE);
            requestHotProductData();
            scroll_main.smoothScrollTo(0, 0);
        } else if (status.equals("recommendProduct")) {
            lv.setVisibility(View.GONE);
            requestRecommendProductData();
            scroll_main.smoothScrollTo(0, 0);
        }
    }

    private void DefaultView() {
        initPageData("hotProduct");
        StringUtil.changeButtonStyle(btn_hot, btn_recommend, R.id.btn_hot_product, mResource);
    }

    public void showLeftMenu(View view) {
        if (getSlidingMenu().isMenuShowing()) {
            getSlidingMenu().showMenu(false);
        } else {
            getSlidingMenu().showMenu(true);
        }
//		getSlidingMenu().clearIgnoredViews();
    }

    private void initLeftMenu() {
        SlidingMenu menu = getSlidingMenu();
        menu.setMenu(R.layout.left_menu_frame);

        Fragment leftMenuFragment = new MenuLeftFragment();
//		setBehindContentView(R.layout.left_menu_frame);
        getSupportFragmentManager().beginTransaction().replace(R.id.id_left_menu_frame, leftMenuFragment).commit();

        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        menu.setShadowWidthRes(R.dimen.shadow_width);
//		 menu.setShadowDrawable(R.color.white);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // menu.setBehindWidth()
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.9f);
        menu.setSecondaryShadowDrawable(R.color.white);
        menu.setShadowDrawable(R.drawable.left_shadow);

        //设置滑动时拖拽效果
        menu.setBehindScrollScale(0.0f);

        menu.addIgnoredView(mViewpager);
//		menu.addIgnoredView(scroll_main);
        menu.setOnOpenedListener(new OnOpenedListener() {

            @Override
            public void onOpened() {
//				menu.showMenu(false);
            }
        });
        menu.setOnClosedListener(new OnClosedListener() {

            @Override
            public void onClosed() {
//				menu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);//菜单栏划回主屏幕
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); // 调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DefaultView();
        requestMyMessageCountData();
        VjinkeApplication apl = (VjinkeApplication) getApplication();
        apl.addNetListener(this);
        onNetWorkChange(apl.netType);
        imageLoader.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        imageLoader.pause();
    }

    @Override
    protected void onDestroy() {
//		UserLogin.getInstance().deleteObserver(this);
        super.onDestroy();
        imageLoader.stop();
    }

    private static final String TYPE = "android";
    protected static final String tag = "MainActivity";
    private File destDir = null;
    private File destFile = null;

    private void requestData() {
        HtmlRequest.checkVersion(MainActivity.this, TYPE, new OnRequestListener() {

            @Override
            public void onRequestFinished(BaseParams params) {
                if (params.result != null) {
                    final ResultCheckVersionContentBean b = (ResultCheckVersionContentBean) params.result;
                    if (!b.getVersion().equals(SystemInfo.sVersionName)) {

                        AlertDialog.Builder builder = new Builder(MainActivity.this);
                        builder.setMessage("点击确认下载更新");
                        builder.setTitle("发现新版本");
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent updateIntent = new Intent(MainActivity.this, AppUpgradeService.class);
                                updateIntent.putExtra("titleId", R.string.app_chinesename);
                                updateIntent.putExtra("downloadUrl",
                                        // "http://114.113.238.90:40080/upload/app/vjinke.apk");
                                        b.getUrl());
                                startService(updateIntent);
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    } else {
                        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                            if (destDir == null) {
                                destDir = new File(Environment.getExternalStorageDirectory().getPath() + VjinkeApplication.mDownloadPath);
                            }
                            if (destDir.exists() || destDir.mkdirs()) {
                                destFile = new File(destDir.getPath() + "/" + URLEncoder.encode("http://114.113.238.90:40080/upload/app/vjinke.apk"));
                                if (destFile.exists() && destFile.isFile() && checkApkFile(destFile.getPath())) {
                                    destFile.delete();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public boolean checkApkFile(String apkFilePath) {
        boolean result = false;
        try {
            PackageManager pManager = getPackageManager();
            PackageInfo pInfo = pManager.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
            if (pInfo == null) {
                result = false;
            } else {
                result = true;
            }
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(Context context, ListView listView, int dividerHeight) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += (listItem.getMeasuredHeight() + dividerHeight);
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * listAdapter.getCount()) + 5;

        listView.setLayoutParams(params);
    }

    private ReceiveBroadCast receiveBroadCast; // 广播实例
    String myActionName = "vjinkeexit";


    // 注册广播
    public void registerBoradcastReceiver() {
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(myActionName); // 只有持有相同的action的接受者才能接收此广播
        this.registerReceiver(receiveBroadCast, filter);
    }

    // 定义一个BroadcastReceiver广播接收类：
    public class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent data) {
            String actionName = data.getAction();
            if (actionName.equals(myActionName)) {
                // 得到广播中得到的数据，并显示出来
                Bundle extras = data.getExtras();
                if (extras != null) {
//					initLeftMenu();
                    DefaultView();
                }
            }
        }
    }


    @Override
    public void update(Observable observable, Object data) {
        bean = (ResultUserLoginContentBean) data;
        if (bean != null) {
            if (Boolean.parseBoolean(bean.getFlag())) {
//				initLeftMenu();
                DefaultView();
            }
        }
    }

    @Override
    public void onNetWorkChange(String netType) {
        View netHint = findViewById(R.id.netfail_hint);
        netHint.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        });

        if (netHint != null) {
            boolean netFail = TextUtils.isEmpty(netType);
            netHint.setVisibility(netFail ? View.VISIBLE : View.GONE);

        }
    }
}
