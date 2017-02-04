package com.cf360.act;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.cf360.ApplicationConsts;
import com.cf360.R;
import com.cf360.mould.HtmlRequest;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.SystemInfo;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

public class WebActivity extends BaseActivity {

	private WebView mWebview;
	private String type = null;
	private String url = null;
	private TitleBar title;
	public static final String WEBTYPE_EXPRESS = "sendExpress"; // 寄快递
	public static final String WEBTYPE_EXPRESS_BACK = "receiveExpress"; // 收快递
	public static final String WEBTYPE_Notice_Detail = "noticeDetail";
	// public static final String WEBTYPE_HINT = "hesitate"; // 犹豫期
	public static final String WEBTYPE_SIGNUP = "signup"; // 注册页服务协议
	public static final String WEBTYPE_RODUCTADVANTAGE = "productadvantage"; // 信托详情项目亮点
	public static final String WEBTYPE_INSURANCE = "insturance"; // 保险个人代理合同书
	public static final String WEBTYPE_ABOUTUS = "aboutus"; // 关于我们

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = getIntent().getExtras().getString("type");
		baseSetContentView(R.layout.activity_web);
		initTopTitle();
		initView();
	}

	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		mWebview = (WebView) findViewById(R.id.webview_web);
		mWebview.getSettings().setSupportZoom(true);
		// 设置出现缩放工具
		mWebview.getSettings().setBuiltInZoomControls(true);
		mWebview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		mWebview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);

		mWebview.getSettings().setJavaScriptEnabled(true);
		if (type.equals(WEBTYPE_EXPRESS)) {
			url = getIntent().getExtras().getString("url");
			title.setCenterText(getResources().getString(R.string.web_express));
		} else if (type.equals(WEBTYPE_EXPRESS_BACK)) {
			url = getIntent().getExtras().getString("url");
			title.setCenterText(getResources().getString(R.string.web_express));
		} else if (type.equals(WEBTYPE_Notice_Detail)) {
			url = getIntent().getExtras().getString("url")
					+ getIntent().getExtras().getString("messageId");
			String titleName = getIntent().getExtras().getString("title");
			title.setCenterText(titleName);
		} else if (type.equals(WEBTYPE_SIGNUP)) {
			url = ApplicationConsts.URL_REGISTERAGREEMENT;
			title.setCenterText(getResources().getString(R.string.web_sign));
		} else if (type.equals(WEBTYPE_RODUCTADVANTAGE)) {
			url = getIntent().getExtras().getString("url");
			String titleName = getIntent().getExtras().getString("title");
			title.setCenterText(titleName);
		}else if (type.equals(WEBTYPE_INSURANCE)) {
			url = ApplicationConsts.URL_WEB_EQUIT_INSRANCER;

			title.setCenterText("保险个人代理合同书");
		} else if (type.equals(WEBTYPE_ABOUTUS)) {
			url = ApplicationConsts.URL_WEB_EQUIT_ABOUTUS+ "?num=" + SystemInfo.sVersionName;

			title.setCenterText("关于我们");
		}
		/*
		 * else if (type.equals(WEBTYPE_HINT)) { url =
		 * getIntent().getExtras().getString("url");
		 * title.setCenterText(getResources().getString( R.string.web_hint)); }
		 */

		HtmlRequest.synCookies(this, url);

		mWebview.loadUrl(url);

		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
//				initData();

				// insuranceBaseAdapter.notifyDataSetChanged();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
		
	}

	private void initTopTitle() {
		title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setLogo(R.drawable.img_logo, false).setIndicator(R.drawable.back)
				.showMore(false).setOnActionListener(new OnActionListener() {

					@Override
					public void onMenu(int id) {
					}

					@Override
					public void onBack() {
						setResult(RESULT_OK);
						finish();
					}

					@Override
					public void onAction(int id) {

					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
