package com.cf360.act;

import java.util.ArrayList;
import java.util.List;

import com.cf360.R;
import com.cf360.adapter.ToubaodanPostAdapter;
import com.cf360.bean.PostTouabodanInfoBean;
import com.cf360.bean.ResultPostContractContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.ListViewForScrollView;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * 寄回保单
 * 
 */
public class PostToubaodanActivity extends BaseActivity implements
		OnClickListener {
	private EditText edt_express_name;
	private EditText edt_express_number;
	private EditText edt_express_address;
	private Button btnPost;
	//private CheckBox checkboxTbd,checkboxTbdHint,checkboxInsurancePlan,checkboxApplicantID,checkboxInsuredID,checkboxBank,checkboxHKB,checkboxCSZM;

	private String policyOrderId;
	private StringBuilder expressFiles;
	private String expressFilesAll;
	private ActivityStack stack;
	private ScrollView scrollView;
	private ListViewForScrollView listView;
	private ToubaodanPostAdapter adapter;
	private ArrayList<String> arrayList;
	private List<PostTouabodanInfoBean> listBeans;
	private PostTouabodanInfoBean infoBeans;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_post_toubaodan);
		initView();
		initData();
		initTopTitle();
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_post_toubaodan))
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
		edt_express_name = (EditText) findViewById(R.id.post_toubaodan_edit_express_name);
		edt_express_number = (EditText) findViewById(R.id.post_toubaodan_edit_express_number);
		edt_express_address = (EditText) findViewById(R.id.post_toubaodan_edit_express_address);
		/*checkboxTbd=(CheckBox) findViewById(R.id.post_toubaodan_checkbox_tbd);
		checkboxTbdHint=(CheckBox) findViewById(R.id.post_toubaodan_checkbox_tbd_hint);
		checkboxInsurancePlan=(CheckBox) findViewById(R.id.post_toubaodan_checkbox_insurance_plan);
		checkboxApplicantID=(CheckBox) findViewById(R.id.post_toubaodan_checkbox_applicantID);
		checkboxInsuredID=(CheckBox) findViewById(R.id.post_toubaodan_checkbox_insuredID);
		checkboxBank=(CheckBox) findViewById(R.id.post_toubaodan_checkbox_bank);
		checkboxHKB=(CheckBox) findViewById(R.id.post_toubaodan_checkbox_hkb);
		checkboxCSZM=(CheckBox) findViewById(R.id.post_toubaodan_checkbox_cszm);*/
		btnPost = (Button) findViewById(R.id.btn_post);
		
		scrollView = (ScrollView) findViewById(R.id.post_toubaodan_scrollView);
		scrollView.smoothScrollTo(0, 0);
		listView = (ListViewForScrollView) findViewById(R.id.toubaodan_listview);
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void initData() {
		policyOrderId = getIntent().getStringExtra("policyOrderId");
		arrayList=getIntent().getExtras().getStringArrayList("postMsg");
		
		listBeans=new ArrayList<PostTouabodanInfoBean>();
		for(int i=0;i<arrayList.size();i++){
			infoBeans=new PostTouabodanInfoBean();
			infoBeans.setReceiveInfo(arrayList.get(i));
			listBeans.add(infoBeans);
		}
		btnPost.setOnClickListener(this);
		adapter=new ToubaodanPostAdapter(PostToubaodanActivity.this, listBeans);
		listView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_post:
			String expressName = edt_express_name.getText().toString();
			String expressCode = edt_express_number.getText().toString();
			String expressIp = edt_express_address.getText().toString();
			if (TextUtils.isEmpty(expressName)) {
				Toast.makeText(PostToubaodanActivity.this, "请输入快递公司名称",
						Toast.LENGTH_LONG).show();
			} else {
				if (TextUtils.isEmpty(expressCode)) {
					Toast.makeText(PostToubaodanActivity.this, "请输入运单号",
							Toast.LENGTH_LONG).show();
				} else {
					if (TextUtils.isEmpty(expressIp)) {
						Toast.makeText(PostToubaodanActivity.this, "请输入快递公司网址",
								Toast.LENGTH_LONG).show();
					} else {
						if(expressIp.startsWith("www.")){
							expressFiles=new StringBuilder();
							/*if (checkboxTbd.isChecked()) {
								expressFiles.append("投保单,");
							}
							if(checkboxTbdHint.isChecked()){
								expressFiles.append("投保提示,");
							}
							if(checkboxInsurancePlan.isChecked()){
								expressFiles.append("保险计划书,");
							}
							if(checkboxApplicantID.isChecked()){
								expressFiles.append("投保人身份证复印件,");
							}
							if(checkboxInsuredID.isChecked()){
								expressFiles.append("被保人身份证复印件,");
							}
							if(checkboxBank.isChecked()){
								expressFiles.append("扣款银行卡复印件,");
							}
							if(checkboxHKB.isChecked()){
								expressFiles.append("户口本复印件,");
							}
							if(checkboxCSZM.isChecked()){
								expressFiles.append("出生证明复印件");
							}
							if(expressFiles.length()==0){
								expressFilesAll="";
							}else{
								expressFilesAll=expressFiles.deleteCharAt(expressFiles.length()-1).toString();
							}*/
							for(int i=0;i<listBeans.size();i++){
								if(listBeans.get(i).isChecked()){
									expressFiles.append(listBeans.get(i).getReceiveInfo());
									expressFiles.append(",");
								}
							}
							if(expressFiles.length()==0){
								expressFilesAll="";
							}else{
								expressFilesAll=expressFiles.deleteCharAt(expressFiles.length()-1).toString();
							}
							requestPostToubaodanData(policyOrderId,expressFilesAll,
									expressName, expressCode, expressIp);
						}else{
							Toast.makeText(PostToubaodanActivity.this,
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

	private void requestPostToubaodanData(final String policyOrderId,
			String expressFiles, String expressName, String expressCode,
			String expressIP) {
		HtmlRequest.getPostToubaodan(PostToubaodanActivity.this, policyOrderId,
				expressFiles, expressName, expressCode, expressIP,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							ResultPostContractContentBean data = (ResultPostContractContentBean) params.result;
							if (data != null) {
								if (data.getFlag().equals("true")) {
									Toast.makeText(PostToubaodanActivity.this,
											"寄回成功", Toast.LENGTH_LONG).show();
									Intent intent = new Intent(
											PostToubaodanActivity.this,
											ToubaodanActivity.class);
									startActivity(intent);
									stack.removeAllActivity();
								}
							}
						} else {
							Toast.makeText(PostToubaodanActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}
}
