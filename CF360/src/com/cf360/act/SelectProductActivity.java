package com.cf360.act;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.adapter.SelectProductAllAdapter;
import com.cf360.adapter.SelectProductOrderAdapter;
import com.cf360.bean.ResultSelectProductAllBean;
import com.cf360.bean.ResultSelectProductListContentBean;
import com.cf360.bean.ResultSelectProductOrderBean;
import com.cf360.bean.ResultSelectProductReturnListContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.ListViewForScrollView;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

/**
 * 选择产品
 * 
 */
public class SelectProductActivity extends BaseActivity {
	private TextView txtOrderProduct;
	private TextView txtAllProduct;
	private EditText edt_content;
	private TextView btnConfirm;
	private ListViewForScrollView lvOrderProduct;
	private ListViewForScrollView lvAllProduct;
	private ResultSelectProductListContentBean data;
	private String remark;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_select_product);
		initView();
		initData();
		initTopTitle();
		remark = getIntent().getStringExtra("remark");
		if (remark.equals("ApplyContractActivity")) {
			requestData("","","ht");
		}else if(remark.equals("ApplyToubaodanActivity")){
			requestData("","bx","");
		}else if(remark.equals("ApplyDeclarationActivity")){
			requestData("","bx","");
		}else if(remark.equals("ApplyDeclarationNotInsuranceActivity")){
			requestData("","fbx","");
		}
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_select_product))
				.setLogo(R.drawable.img_logo, false).setIndicator(R.drawable.back)
				.showMore(true).setOnActionListener(new OnActionListener() {

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

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		txtOrderProduct = (TextView) findViewById(R.id.select_product_orderProduct);
		txtAllProduct = (TextView) findViewById(R.id.select_product_allProduct);
		edt_content = (EditText) findViewById(R.id.select_product_content);
		btnConfirm = (TextView) findViewById(R.id.select_product_confirm);
		btnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = edt_content.getText().toString();
					if (remark.equals("ApplyContractActivity")) {
						requestData(content,"","ht");
					}else if(remark.equals("ApplyToubaodanActivity")){
						requestData(content,"bx","");
					}else if(remark.equals("ApplyDeclarationActivity")){
						requestData(content,"bx","");
					}else if(remark.equals("ApplyDeclarationNotInsuranceActivity")){
						requestData(content,"fbx","");
					}
				}
		});
		lvOrderProduct = (ListViewForScrollView) findViewById(R.id.select_product_orderListview);
		lvOrderProduct.setBackgroundColor(getResources().getColor(
				R.color.gray_light));
		lvAllProduct = (ListViewForScrollView) findViewById(R.id.select_product_allListview);
		lvAllProduct.setBackgroundColor(getResources().getColor(
				R.color.gray_light));
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;

	}

	private void initData() {
		lvOrderProduct.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ResultSelectProductOrderBean orderBean = data
						.getAppProductList().get(position);

				returnDataOrder(orderBean);

			}
		});
		lvAllProduct.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ResultSelectProductAllBean allBean = data.getProductList().get(
						position);

				returnDataAll(allBean);

			}
		});
	}

	protected void returnDataOrder(ResultSelectProductOrderBean orderBean) {
		Intent intent = new Intent();
		intent.setClass(SelectProductActivity.this, ApplyContractActivity.class);
		intent.putExtra("ID", orderBean.getID());
		intent.putExtra("CAREGORY", orderBean.getCATEGORY());
		intent.putExtra("APPOID", orderBean.getAPPOID());
		this.setResult(2000, intent);
		this.finish();
	}

	protected void returnDataAll(ResultSelectProductAllBean allBean) {
		Intent intent = new Intent();
		intent.setClass(SelectProductActivity.this, ApplyContractActivity.class);
		intent.putExtra("ID", allBean.getID());
		intent.putExtra("CAREGORY", allBean.getCATEGORY());
		intent.putExtra("APPOID", allBean.getAPPOID());
		this.setResult(2000, intent);
		this.finish();
	}

	private void requestData(final String productName,String category,String selectType) {
		HtmlRequest.getSelectProduct(SelectProductActivity.this, productName,category,selectType,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							data = (ResultSelectProductListContentBean) params.result;
							// 预约产品
							setOrderProductData(data);
							// 全部产品
							setAllProductData(data);

						} else {
							Toast.makeText(SelectProductActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	protected void setAllProductData(ResultSelectProductListContentBean data) {
		if (data.getProductList().size() != 0) {
			txtAllProduct.setVisibility(View.VISIBLE);
		}
		SelectProductAllAdapter allAdapter = new SelectProductAllAdapter(this,
				data.getProductList());
		lvAllProduct.setAdapter(allAdapter);
	}

	protected void setOrderProductData(ResultSelectProductListContentBean data) {
		if (data.getAppProductList().size() != 0) {
			txtOrderProduct.setVisibility(View.VISIBLE);
		}
		SelectProductOrderAdapter orderAdapter = new SelectProductOrderAdapter(
				this, data.getAppProductList());
		lvOrderProduct.setAdapter(orderAdapter);

	}

}
