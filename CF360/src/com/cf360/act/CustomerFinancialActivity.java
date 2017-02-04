package com.cf360.act;

import com.cf360.R;
import com.cf360.bean.ResultCustomerInfoContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CustomerFinancialActivity extends BaseActivity implements OnClickListener{
	
	private String id = null;
	private String car = null;
	private String house = null;
	private String valueAssessment = null;
	private String yearSalary = null;
	
	private RelativeLayout rl_financial_customer_valueassessment;
	private TextView tv_financial_customer_valueassessment_value;
	private EditText et_financial_customer_car_value,et_financial_customer_house_value,et_financial_customer_yearsalary_value;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_myperson_customer_financial);
		initTopTitle();
		
		initView();
	}
	
	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		id = getIntent().getExtras().getString("id");
		car = getIntent().getExtras().getString("car");
		house = getIntent().getExtras().getString("house");
		valueAssessment = getIntent().getExtras().getString("valueAssessment");
		yearSalary = getIntent().getExtras().getString("yearSalary");
		
		rl_financial_customer_valueassessment = (RelativeLayout) findViewById(R.id.rl_financial_customer_valueassessment);
		tv_financial_customer_valueassessment_value = (TextView) findViewById(R.id.tv_financial_customer_valueassessment_value);
		et_financial_customer_car_value = (EditText) findViewById(R.id.et_financial_customer_car_value);
		et_financial_customer_house_value = (EditText) findViewById(R.id.et_financial_customer_house_value);
		et_financial_customer_yearsalary_value = (EditText) findViewById(R.id.et_financial_customer_yearsalary_value);
		
		rl_financial_customer_valueassessment.setOnClickListener(this);
		
		setView();
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void setView() {
		et_financial_customer_car_value.setText(car);
		et_financial_customer_house_value.setText(house);
		if(valueAssessment.equals("high")){
			tv_financial_customer_valueassessment_value.setText("高");
		}else if(valueAssessment.equals("middle")){
			tv_financial_customer_valueassessment_value.setText("中");
		}else if(valueAssessment.equals("low")){
			tv_financial_customer_valueassessment_value.setText("低");
			
		}
		et_financial_customer_yearsalary_value.setText(yearSalary);
		
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(new Action(2, 0, R.color.blue_light),CustomerFinancialActivity.this.getResources().getString(R.string.customer_save_newcustomer));
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_mycustomer_financial))
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
						switch (id) {
						case 2:
//							dialog();
							requestCustomerFinancialSaveData();
							
							break;

						default:
							break;
						}
					}
				});
	}
	
	/**
	 * 
	 * 保存客户财务状况
	 * 
	 */
	
	public void requestCustomerFinancialSaveData() {
		
		String operation = "edit";
		
		String newCar = et_financial_customer_car_value.getText().toString();
		String newHouse = et_financial_customer_house_value.getText().toString();
		String newValueAssessment = tv_financial_customer_valueassessment_value.getText().toString();
		if(newValueAssessment.equals("高")){
			newValueAssessment = "high";
		}else if(newValueAssessment.equals("中")){
			newValueAssessment = "middle";
		}else if(newValueAssessment.equals("低")){
			newValueAssessment = "low";
		}
		String newYearSalary = et_financial_customer_yearsalary_value.getText().toString();
		
		
		HtmlRequest.getCustomerFinancialSave(CustomerFinancialActivity.this,id,operation,newCar,newHouse,newValueAssessment,newYearSalary,
				new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				
				ResultCustomerInfoContentBean data = (ResultCustomerInfoContentBean) params.result;
				
				if (data != null) {
//					infoBean = data.getUserCustomer();
					
					if(data.getFlag().equals("true")){
						Toast.makeText(CustomerFinancialActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
						finish();
					}
					
				
					} else {
					
				}
				
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_financial_customer_valueassessment:
			dialog();
			break;

		default:
			break;
		}
		
	}
	private void dialog() {
		final String items[] = { "高", "中", "低" };
		// dialog参数设置
		AlertDialog.Builder builder = new AlertDialog.Builder(this); // 先得到构造器
		builder.setTitle("价值评估"); // 设置标题
		// builder.setMessage("是否确认退出?"); //设置内容
		// 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// Toast.makeText(CustomerAddActivity.this, items[which],
				// Toast.LENGTH_SHORT).show();
				tv_financial_customer_valueassessment_value.setText(items[which]);

			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// Toast.makeText(CustomerAddActivity.this, "取消",
				// Toast.LENGTH_SHORT).show();
			}
		});

		builder.create().show();
	}
	
}
