package com.cf360.act;

import com.cf360.ApplicationConsts;
import com.cf360.R;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class InsDetailsActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initTopTitle();
		baseSetContentView(R.layout.activity_iamge_text);
		initView();
		
		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
	}
	public void initView(){
		WebView webViwe=(WebView) findViewById(R.id.webview);
		webViwe.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
		String id = getIntent().getStringExtra("id");
		webViwe.loadUrl(ApplicationConsts.EC_HOST+"productInsurance/toGraphicDetailForApp?productId="+id);
	}
	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(new Action(2, 0, R.color.orange));
		title.setCenterText(
				getResources().getString(R.string.insurance_text))
				.setLogo(R.drawable.img_logo, false).setIndicator(R.drawable.back)
				.showMore(true).setOnActionListener(new OnActionListener() {
					@Override
					public void onBack() {
						finish();
					}

					@Override
					public void onAction(int id) {

					}

					@Override
					public void onMenu(int id) {

					}


				});
	}

}
