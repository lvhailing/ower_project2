package com.cf360.act;

import com.cf360.R;
import com.cf360.bean.ResultFinancialAuditContentBean;
import com.cf360.bean.ResultIsInsuranceContentBean;
import com.cf360.bean.ResultMyPersonContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.IdCardCheckUtils;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 保险代理认证
 * @author xuweiwei
 *
 */
public class InsuranceActivity extends BaseActivity {

	private EditText et_insurance_number;
	private Button btn_insurance;
	private CheckBox insurance_checkbox;
	private TextView insurance_web;
	private TextView tv_add_insurance_type;
	private TextView et_insurance_idcardnum;
	private String items[] = { "保险代理证", "保险经纪证", "保险公估证" ,"分级证"};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_insurance);
		initTopTitle();
		initView();
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		et_insurance_number = (EditText) findViewById(R.id.et_insurance_number);
		btn_insurance = (Button) findViewById(R.id.btn_insurance);
		insurance_checkbox = (CheckBox)findViewById(R.id.insurance_checkbox);
		insurance_web = (TextView)findViewById(R.id.insurance_web);
		tv_add_insurance_type = (TextView)findViewById(R.id.tv_add_insurance_type);
		et_insurance_idcardnum = (TextView)findViewById(R.id.et_insurance_idcardnum);

		tv_add_insurance_type.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog_type();
			}
		});
		insurance_web.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i_web = new Intent(InsuranceActivity.this, WebActivity.class);
				i_web.putExtra("type", WebActivity.WEBTYPE_INSURANCE);

				startActivity(i_web);
			}
		});

		requestIsInsuranceAudit();
		btn_insurance.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(et_insurance_number.getText().toString())||et_insurance_number.getText().toString().equals("证书编号")) {
					Toast.makeText(InsuranceActivity.this, "证书编号不能为空", Toast.LENGTH_LONG).show();
				} else if (TextUtils.isEmpty(et_insurance_idcardnum.getText().toString())||et_insurance_idcardnum.getText().toString().equals("身份证号码")) {
					Toast.makeText(InsuranceActivity.this, "身份证号码不能为空", Toast.LENGTH_LONG).show();
				} else if (TextUtils.isEmpty(tv_add_insurance_type.getText().toString())||tv_add_insurance_type.getText().toString().equals("证书类型")) {
					Toast.makeText(InsuranceActivity.this, "证书类型不能为空", Toast.LENGTH_LONG).show();
				} else if (!IdCardCheckUtils.isIdCard(et_insurance_idcardnum.getText().toString().toUpperCase())) {
					Toast.makeText(InsuranceActivity.this, "身份证号码格式不正确，请重新输入", Toast.LENGTH_LONG).show();
				} else if (!insurance_checkbox.isChecked()) {
					Toast.makeText(InsuranceActivity.this, "请您仔细阅读协议，并点击同意", Toast.LENGTH_LONG).show();
				} else if(et_insurance_number.getText().toString().length()<10){
					Toast.makeText(InsuranceActivity.this, "证书编号不能小于10个字符", Toast.LENGTH_LONG).show();
				}else if(et_insurance_number.getText().toString().length()>20){
					Toast.makeText(InsuranceActivity.this, "证书编号不能大于20个字符", Toast.LENGTH_LONG).show();
				}else{
					dialog();
				}
			}
		});

		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	public void dialog() {
		AlertDialog.Builder builder = new Builder(InsuranceActivity.this);
		builder.setMessage("确定要进行保险代理认证审核吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				requestInsuranceAudit();
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	//保险代理认证
	public void requestInsuranceAudit(){
		String type = null;
		String insuranceAgentType = tv_add_insurance_type.getText().toString();
		String idNo = et_insurance_idcardnum.getText().toString();
		if(insuranceAgentType.equals("保险代理证")){
			type = "1";
		}else if(insuranceAgentType.equals("保险经纪证")){
			type = "2";
		}else if(insuranceAgentType.equals("保险公估证")){
			type = "3";
		}else if(insuranceAgentType.equals("分级证")){
			type = "4";
		}else{
			type = "0";
		}
		HtmlRequest.getInsuranceAudit(InsuranceActivity.this, et_insurance_number.getText().toString(),idNo,type,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						ResultFinancialAuditContentBean data = (ResultFinancialAuditContentBean) params.result;
						if (data != null) {
							if (Boolean.parseBoolean(data.getFlag())) {

								Toast.makeText(InsuranceActivity.this,
										data.getMessage(), Toast.LENGTH_LONG)
										.show();
								Intent i_myperson = new Intent();
								i_myperson.setClass(InsuranceActivity.this,
										MyPersonActivity.class);
								// startActivity(i_myperson);
								setResult(0, i_myperson);
								finish();
							} else {
								Toast.makeText(InsuranceActivity.this,
										data.getMessage(), Toast.LENGTH_LONG)
										.show();
							}

						} else {

						}

					}


				});
	}
	//保险代理信息验证
	public void requestIsInsuranceAudit(){
		String userId = null;
		try {
			userId = DESUtil.decrypt(PreferenceUtil.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		HtmlRequest.getIsInsuranceAudit(InsuranceActivity.this, userId,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						ResultIsInsuranceContentBean data = (ResultIsInsuranceContentBean) params.result;
						if (data != null) {
							if (data.getIsInsuranceAgentAudited().equals("success")) {

								btn_insurance.setText("已认证");
								btn_insurance.setClickable(false);
								btn_insurance.setBackgroundResource(R.drawable.shape_button_gray_d);
								et_insurance_number.setText(data.getInsuranceAgentCode());
								if(!TextUtils.isEmpty(data.getIdNo())){
									et_insurance_idcardnum.setText(data.getIdNo());
								}else{

								}
								if(!TextUtils.isEmpty(data.getInsuranceAgentType())){
									if(Integer.parseInt(data.getInsuranceAgentType())==0){
										tv_add_insurance_type.setText("");
									}else{
										tv_add_insurance_type.setText(items[Integer.parseInt(data.getInsuranceAgentType())-1]);
									}

								}
								insurance_checkbox.setChecked(true);
								insurance_checkbox.setClickable(false);
							} else if (data.getIsInsuranceAgentAudited().equals("unAudit")) {
								btn_insurance.setText("认证中");
								btn_insurance.setClickable(false);
								btn_insurance.setBackgroundResource(R.drawable.shape_button_gray_d);
								et_insurance_number.setText(data.getInsuranceAgentCode());
								if(!TextUtils.isEmpty(data.getIdNo())){
									et_insurance_idcardnum.setText(data.getIdNo());
								}else{

								}
								if(!TextUtils.isEmpty(data.getInsuranceAgentType())){
									if(Integer.parseInt(data.getInsuranceAgentType())==0){
										tv_add_insurance_type.setText("");
									}else{
										tv_add_insurance_type.setText(items[Integer.parseInt(data.getInsuranceAgentType())-1]);
									}
								}
								insurance_checkbox.setChecked(true);
								insurance_checkbox.setClickable(false);
							} else if (data.getIsInsuranceAgentAudited().equals("fail")) {
								btn_insurance.setText("提交认证");
								btn_insurance.setClickable(true);
								et_insurance_number.setText(data.getInsuranceAgentCode());
								if(!TextUtils.isEmpty(data.getIdNo())){
									et_insurance_idcardnum.setText(data.getIdNo());
								}else{

								}
								if(!TextUtils.isEmpty(data.getInsuranceAgentType())){
									if(Integer.parseInt(data.getInsuranceAgentType())==0){
										tv_add_insurance_type.setText("");
									}else{
										tv_add_insurance_type.setText(items[Integer.parseInt(data.getInsuranceAgentType())-1]);
									}
								}
								insurance_checkbox.setChecked(true);
							} else {
								btn_insurance.setClickable(true);

							}

						} else {
							btn_insurance.setClickable(true);
						}

					}


				});
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_insurance))
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

	private void dialog_type() {

		// dialog参数设置
		AlertDialog.Builder builder = new AlertDialog.Builder(this); // 先得到构造器
		builder.setTitle("选择证书类型"); // 设置标题
		// builder.setMessage("是否确认退出?"); //设置内容
		// 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// Toast.makeText(CustomerAddActivity.this, items[which],
				// Toast.LENGTH_SHORT).show();
				tv_add_insurance_type.setText(items[which]);

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
