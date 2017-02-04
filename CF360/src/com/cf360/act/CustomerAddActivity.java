package com.cf360.act;

import com.cf360.R;
import com.cf360.bean.ResultCustomerInfoContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.IdCardCheckUtils;
import com.cf360.uitls.StringUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerAddActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout rl_add_customer_type, rl_add_customer_sex,
			rl_add_customer_value;
	private TextView tv_add_customer_type, tv_add_customer_sex,
			tv_add_customer_value;
	private EditText et_add_customer_num, et_add_customer_name,
			et_add_customer_idnumer, et_add_customer_phonenumer;
	private Button btn_customer_add;
	private String operation;
	private String customerType;
	private String code;
	private String name;
	private String sex;
	private String idcard;
	private String mobilePhone;
	private String valueAssessment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_myperson_customer_add);
		initTopTitle();

		initView();
		initData();

	}

	private void initData() {

	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		rl_add_customer_type = (RelativeLayout) findViewById(R.id.rl_add_customer_type);
		rl_add_customer_sex = (RelativeLayout) findViewById(R.id.rl_add_customer_sex);
		rl_add_customer_value = (RelativeLayout) findViewById(R.id.rl_add_customer_value);

		tv_add_customer_type = (TextView) findViewById(R.id.tv_add_customer_type);
		tv_add_customer_sex = (TextView) findViewById(R.id.tv_add_customer_sex);
		tv_add_customer_value = (TextView) findViewById(R.id.tv_add_customer_value);

		et_add_customer_num = (EditText) findViewById(R.id.et_add_customer_num);
		et_add_customer_name = (EditText) findViewById(R.id.et_add_customer_name);
		et_add_customer_idnumer = (EditText) findViewById(R.id.et_add_customer_idnumer);
		et_add_customer_phonenumer = (EditText) findViewById(R.id.et_add_customer_phonenumer);

		btn_customer_add = (Button) findViewById(R.id.btn_customer_add);

		rl_add_customer_type.setOnClickListener(this);
		rl_add_customer_sex.setOnClickListener(this);
		rl_add_customer_value.setOnClickListener(this);
		btn_customer_add.setOnClickListener(this);
		
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
						getResources().getString(R.string.title_mycustomer_add))
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_add_customer_type:
			dialog();
			break;
		case R.id.rl_add_customer_sex:
			dialog1();
			break;
		case R.id.rl_add_customer_value:
			dialog2();
			break;
		case R.id.btn_customer_add:
			operation = "add";
			customerType = tv_add_customer_type.getText().toString();
			if (customerType.equals("潜在客户")) {
				customerType = "1";
			} else if (customerType.equals("意向客户")) {
				customerType = "2";
			} else if (customerType.equals("成交客户")) {
				customerType = "3";
			}
			code = et_add_customer_num.getText().toString();
			name = et_add_customer_name.getText().toString();
			sex = tv_add_customer_sex.getText().toString();
			if (sex.equals("男")) {
				sex = "man";
			} else if (sex.equals("女")) {
				sex = "woman";
			}
			idcard = et_add_customer_idnumer.getText().toString();
			mobilePhone = et_add_customer_phonenumer.getText().toString();

			valueAssessment = tv_add_customer_value.getText().toString();
			if (valueAssessment.equals("高")) {
				valueAssessment = "high";
			} else if (valueAssessment.equals("中")) {
				valueAssessment = "middle";
			} else if (valueAssessment.equals("低")) {
				valueAssessment = "low";
			}

			if (TextUtils.isEmpty(customerType)) {
				Toast.makeText(CustomerAddActivity.this, "请选择客户类型",
						Toast.LENGTH_SHORT).show();
			} else {
				if (TextUtils.isEmpty(name)) {
					Toast.makeText(CustomerAddActivity.this, "请输入客户姓名",
							Toast.LENGTH_SHORT).show();
				} else if (TextUtils.isEmpty(mobilePhone)) {
					Toast.makeText(CustomerAddActivity.this, "请输入电话号码",
							Toast.LENGTH_SHORT).show();
				} else {
					if (StringUtil.isMobileNO(mobilePhone)) {
						if (TextUtils.isEmpty(idcard)
								|| IdCardCheckUtils.isIdCard(idcard.toUpperCase())) {
							requestData();
						} else {
							Toast.makeText(CustomerAddActivity.this,
									"身份证号码格式不正确", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(CustomerAddActivity.this, "手机号码格式不正确",
								Toast.LENGTH_SHORT).show();
					}
				}
			}

			break;
		default:
			break;
		}
	}

	public void requestData() {

		HtmlRequest.getCustomerAdd(CustomerAddActivity.this, operation,
				customerType, code, name, sex, idcard.toUpperCase(), mobilePhone,
				valueAssessment, new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						ResultCustomerInfoContentBean data = (ResultCustomerInfoContentBean) params.result;
						if (data != null) {
							if (data.getFlag().equals("true")) {
								Toast.makeText(CustomerAddActivity.this,
										data.getMessage(), Toast.LENGTH_LONG)
										.show();
								finish();
							} else {
								Toast.makeText(CustomerAddActivity.this,
										data.getMessage(), Toast.LENGTH_LONG)
										.show();
							}
							
						}
					}
				});
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
				tv_add_customer_type.setText(items[which]);

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
				tv_add_customer_sex.setText(items[which]);

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

	private void dialog2() {
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
				tv_add_customer_value.setText(items[which]);

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
