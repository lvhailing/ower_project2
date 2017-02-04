package com.cf360.act;

import com.cf360.R;
import com.cf360.bean.ResultCustomerInfoContentBean;
import com.cf360.bean.ResultMyCustomerInfoDetailContentTwoBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.IdCardCheckUtils;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerBaseInfoActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout rl_base_info_customer_type,
			rl_base_info_customer_sex;
	private TextView tv_base_info_customer_type_value,
			tv_base_info_customer_sex_value;
	private EditText et_base_info_customer_num_value,
			et_base_info_customer_name_value, et_base_info_customer_age_value,
			et_base_info_customer_idcard_value,
			et_base_info_customer_nation_value,
			et_base_info_customer_company_value,
			et_base_info_customer_position_value,
			et_base_info_customer_profession_value;

	private String id = null;
	private String customerType = null; // 1:潜在客户;2意向客户;3成交客户
	private String code = null;
	private String name = null;
	private String sex = null; // 客户性别man:男;women:女
	private String age = null;
	private String idcard = null;
	private String nation = null;
	private String company = null;
	private String position = null;
	private String profession = null;
	private String mobilePhone = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_myperson_customer_base_info);
		initTopTitle();
		initView();

	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		id = getIntent().getExtras().getString("id");
		customerType = getIntent().getExtras().getString("customerType");
		code = getIntent().getExtras().getString("code");
		name = getIntent().getExtras().getString("name");
		sex = getIntent().getExtras().getString("sex");
		age = getIntent().getExtras().getString("age");
		idcard = getIntent().getExtras().getString("idcard");
		nation = getIntent().getExtras().getString("nation");
		company = getIntent().getExtras().getString("company");
		position = getIntent().getExtras().getString("position");
		profession = getIntent().getExtras().getString("profession");
		mobilePhone = getIntent().getExtras().getString("mobilePhone");

		rl_base_info_customer_type = (RelativeLayout) findViewById(R.id.rl_base_info_customer_type);
		rl_base_info_customer_sex = (RelativeLayout) findViewById(R.id.rl_base_info_customer_sex);

		tv_base_info_customer_type_value = (TextView) findViewById(R.id.tv_base_info_customer_type_value);
		tv_base_info_customer_sex_value = (TextView) findViewById(R.id.tv_base_info_customer_sex_value);

		et_base_info_customer_num_value = (EditText) findViewById(R.id.et_base_info_customer_num_value);
		et_base_info_customer_name_value = (EditText) findViewById(R.id.et_base_info_customer_name_value);
		et_base_info_customer_age_value = (EditText) findViewById(R.id.et_base_info_customer_age_value);
		et_base_info_customer_idcard_value = (EditText) findViewById(R.id.et_base_info_customer_idcard_value);
		et_base_info_customer_nation_value = (EditText) findViewById(R.id.et_base_info_customer_nation_value);
		et_base_info_customer_company_value = (EditText) findViewById(R.id.et_base_info_customer_company_value);
		et_base_info_customer_position_value = (EditText) findViewById(R.id.et_base_info_customer_position_value);
		et_base_info_customer_profession_value = (EditText) findViewById(R.id.et_base_info_customer_profession_value);

		rl_base_info_customer_type.setOnClickListener(this);
		rl_base_info_customer_sex.setOnClickListener(this);

		setView();
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void setView() {
		if (customerType.equals("1")) {
			tv_base_info_customer_type_value.setText("潜在客户");
		} else if (customerType.equals("2")) {
			tv_base_info_customer_type_value.setText("意向客户");
		} else if (customerType.equals("3")) {
			tv_base_info_customer_type_value.setText("成交客户");
		}
		if (!TextUtils.isEmpty(sex)) {
			if (sex.equals("man")) {
				tv_base_info_customer_sex_value.setText("男");
			} else if (sex.equals("women")) {
				tv_base_info_customer_sex_value.setText("女");
			}
		}

		et_base_info_customer_num_value.setText(code);
		et_base_info_customer_name_value.setText(name);
		et_base_info_customer_age_value.setText(age);
		et_base_info_customer_idcard_value.setText(idcard);
		et_base_info_customer_nation_value.setText(nation);
		et_base_info_customer_company_value.setText(company);
		et_base_info_customer_position_value.setText(position);
		et_base_info_customer_profession_value.setText(profession);

	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(
				new Action(2, 0, R.color.blue_light),
				CustomerBaseInfoActivity.this.getResources().getString(
						R.string.customer_save_newcustomer));
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(
								R.string.title_mycustomer_base_info))
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
							// dialog();
							requestCustomerInfoSaveData();

							break;

						default:
							break;
						}
					}
				});
	}

	/**
	 * 
	 * 保存客户基本信息
	 * 
	 */

	public void requestCustomerInfoSaveData() {

		String operation = "edit";
		String newcustomerType = tv_base_info_customer_type_value.getText()
				.toString();

		if (newcustomerType.equals("潜在客户")) {
			newcustomerType = "1";
		} else if (newcustomerType.equals("意向客户")) {
			newcustomerType = "2";
		} else if (newcustomerType.equals("成交客户")) {
			newcustomerType = "3";
		}

		String newCode = et_base_info_customer_num_value.getText().toString();
		String newName = et_base_info_customer_name_value.getText().toString();
		String newSex = tv_base_info_customer_sex_value.getText().toString(); // 客户性别man:男;women:女
		if (newSex.equals("男")) {
			newSex = "man";
		} else if (newSex.equals("女")) {
			newSex = "women";
		}
		String newAge = et_base_info_customer_age_value.getText().toString();
		String newIdcard = et_base_info_customer_idcard_value.getText()
				.toString();
		String newNation = et_base_info_customer_nation_value.getText()
				.toString();
		String newCompany = et_base_info_customer_company_value.getText()
				.toString();
		String newPosition = et_base_info_customer_position_value.getText()
				.toString();
		String newProfession = et_base_info_customer_profession_value.getText()
				.toString();

		if (TextUtils.isEmpty(newcustomerType)) {
			Toast.makeText(CustomerBaseInfoActivity.this, "客户类型不能为空",
					Toast.LENGTH_SHORT).show();
		} else if (TextUtils.isEmpty(newName)) {
			Toast.makeText(CustomerBaseInfoActivity.this, "客户姓名不能为空",
					Toast.LENGTH_SHORT).show();
		} else if (TextUtils.isEmpty(newIdcard)
				|| (IdCardCheckUtils.isIdCard(newIdcard.toUpperCase()))) {

			HtmlRequest.getCustomerInfoSave(CustomerBaseInfoActivity.this, id,
					operation, newcustomerType, newCode, newName, newSex,
					newAge, newIdcard.toUpperCase(), newNation, newCompany, newPosition,
					newProfession,mobilePhone, new OnRequestListener() {
						@Override
						public void onRequestFinished(BaseParams params) {

							ResultCustomerInfoContentBean data = (ResultCustomerInfoContentBean) params.result;

							if (data != null) {
								// infoBean = data.getUserCustomer();

								if (data.getFlag().equals("true")) {
									Toast.makeText(
											CustomerBaseInfoActivity.this,
											"保存成功", Toast.LENGTH_SHORT).show();
									finish();
								}else{
									Toast.makeText(
											CustomerBaseInfoActivity.this,
											data.getMessage(), Toast.LENGTH_SHORT).show();
								}

							} else {

							}

						}
					});
		
		} else {
			Toast.makeText(CustomerBaseInfoActivity.this,
					"身份证号码格式不正确", Toast.LENGTH_SHORT).show();
		}

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
		case R.id.rl_base_info_customer_sex:
			dialog1();
			break;
		case R.id.rl_base_info_customer_type:
			dialog();
			break;

		default:
			break;
		}
	}

	private void dialog() {
		final String items[] = { "潜在客户", "意向客户", "成交客户" };
		// dialog参数设置
		AlertDialog.Builder builder = new AlertDialog.Builder(this); // 先得到构造器
		builder.setTitle("选择客户类型"); // 设置标题
		// builder.setMessage("是否确认退出?"); //设置内容
		// 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// Toast.makeText(CustomerAddActivity.this, items[which],
				// Toast.LENGTH_SHORT).show();
				tv_base_info_customer_type_value.setText(items[which]);

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

	private void dialog1() {
		final String items[] = { "男", "女" };
		// dialog参数设置
		AlertDialog.Builder builder = new AlertDialog.Builder(this); // 先得到构造器
		builder.setTitle("选择客户性别"); // 设置标题
		// builder.setMessage("是否确认退出?"); //设置内容
		// 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// Toast.makeText(CustomerAddActivity.this, items[which],
				// Toast.LENGTH_SHORT).show();
				tv_base_info_customer_sex_value.setText(items[which]);

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
