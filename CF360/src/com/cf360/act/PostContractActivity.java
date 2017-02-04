package com.cf360.act;

import com.cf360.R;
import com.cf360.bean.ResultPostContractBean;
import com.cf360.bean.ResultPostContractContentBean;
import com.cf360.bean.ResultSignContractContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 寄回合同
 * 
 */
public class PostContractActivity extends BaseActivity implements OnClickListener{
	private EditText edt_express_name;
	private EditText edt_express_number;
	private EditText edt_express_address;
	private Button btnPost;
	
	private String contractId;
	private ActivityStack stack;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_post_contract);
		initView();
		initData();
		initTopTitle();
	}
	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_post_contract))
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
	private void initView() {
		stack = ActivityStack.getActivityManage();
		stack.addActivity(this);
		edt_express_name=(EditText) findViewById(R.id.post_contract_edit_express_name);
		edt_express_number=(EditText) findViewById(R.id.post_contract_edit_express_number);
		edt_express_address=(EditText) findViewById(R.id.post_contract_edit_express_address);
		btnPost=(Button) findViewById(R.id.btn_post);
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void initData() {
		contractId=getIntent().getStringExtra("contractId");
		btnPost.setOnClickListener(this);
	}
	private void requestPostContractData(final String contractId,String expressNameBack,String expressCodeBack,String expressUrlBack) {
		HtmlRequest.getPostContract(PostContractActivity.this, contractId,expressNameBack,expressCodeBack,expressUrlBack,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							ResultPostContractContentBean data = (ResultPostContractContentBean) params.result;
							if (data != null) {
								if (data.getFlag().equals("true")) {
									Toast.makeText(PostContractActivity.this,
											"寄回成功", Toast.LENGTH_LONG).show();
									Intent intent = new Intent(PostContractActivity.this,ContractActivity.class);
									startActivity(intent);
									stack.removeAllActivity();
								}
							}
						} else {
							Toast.makeText(PostContractActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_post:
			String express_name=edt_express_name.getText().toString();
			String express_number=edt_express_number.getText().toString();
			String express_address=edt_express_address.getText().toString();
			if(TextUtils.isEmpty(express_name)){
				Toast.makeText(PostContractActivity.this,
						"请输入快递公司名称", Toast.LENGTH_LONG).show();
			}else{
				if(TextUtils.isEmpty(express_number)){
					Toast.makeText(PostContractActivity.this,
							"请输入运单号", Toast.LENGTH_LONG).show();
				}else{
					if(TextUtils.isEmpty(express_address)){
						Toast.makeText(PostContractActivity.this,
								"请输入快递公司网址", Toast.LENGTH_LONG).show();
					}else{
						if(express_address.startsWith("www.")){
							requestPostContractData(contractId, express_name, express_number, "http://"+express_address);
						}else{
							Toast.makeText(PostContractActivity.this,
									"请输入正确的网址地址  如www.baidu.com", Toast.LENGTH_LONG).show();
						}
					}
				}
			}
			break;

		default:
			break;
		}
	}
}
