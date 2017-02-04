package com.cf360.act;

import com.cf360.R;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.SystemInfo;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends BaseActivity {
	
	private TextView tv_setting_help_item_version_name;
	private TextView txt;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_setting_about);
		initTopTitle();
		initView();
		initData();

	}
	
	private void initData() {
		tv_setting_help_item_version_name.setText("当前版本"+SystemInfo.sVersionName);
		
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		tv_setting_help_item_version_name = (TextView) findViewById(R.id.tv_setting_help_item_version_name);
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.setting_about_me))
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
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}
