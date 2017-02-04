package com.cf360.act;


import com.cf360.R;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;




/**
 * 信托管理全部页面
 *
 */
public class MContentAllActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout mTrustDetails_one;
	private RelativeLayout zhiguan_two;
	private RelativeLayout mSunShine_three;
	private RelativeLayout equity_four;
	private RelativeLayout insurance_five;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		baseSetContentView(R.layout.m_content_all_activity);
		initTopTitle();
		initView();
		initDate();

	}


	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		mTrustDetails_one = (RelativeLayout) findViewById(R.id.TrustDetails_one);
		zhiguan_two = (RelativeLayout) findViewById(R.id.zhiguan_two);
		mSunShine_three = (RelativeLayout) findViewById(R.id.Sunshine_shimu);
		equity_four = (RelativeLayout) findViewById(R.id.Equity_four);
		insurance_five = (RelativeLayout) findViewById(R.id.Insurance_five);
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}
	private void initDate() {
		mTrustDetails_one.setOnClickListener(this);
		zhiguan_two.setOnClickListener(this);
		mSunShine_three.setOnClickListener(this);
		equity_four.setOnClickListener(this);
		insurance_five.setOnClickListener(this);
	}

	
	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(new Action(2, 0, R.drawable.search));
		title.setCenterText(
						getResources().getString(R.string.xintuo_content_title))
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
							Intent i_search = new Intent();
							i_search.setClass(MContentAllActivity.this, SearchProductActivity.class);//
							startActivity(i_search);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.TrustDetails_one:
			startActivity(new Intent(MContentAllActivity.this,MTrustDetailsActivity.class));
			break;
		case R.id.Sunshine_shimu:
			startActivity(new Intent(MContentAllActivity.this,MSunshineActivity.class));
			break;
		case R.id.Equity_four:
			startActivity(new Intent(MContentAllActivity.this,CEquityActivity.class));
			break;
		case R.id.zhiguan_two:
			startActivity(new Intent(MContentAllActivity.this,MZiGuanActivity.class));
			break;
		case R.id.Insurance_five:
			startActivity(new Intent(MContentAllActivity.this,MInsuranceActivity.class));
			break;
		default:
			break;
		}
		
	}



}
