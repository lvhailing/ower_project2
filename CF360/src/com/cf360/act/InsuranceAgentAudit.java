package com.cf360.act;

import com.cf360.R;
import com.cf360.bean.FinancialPictureBean;
import com.cf360.bean.ResultFinancialAgentAuditContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * 机构认证
 * @author hasee
 *
 */
public class InsuranceAgentAudit extends BaseActivity {

	private String orgId = null;
//	private GridView gv_financial_mygridview;
	private MouldList<FinancialPictureBean> list;
	private TextView tv_financial_realName,tv_financial_legalPersonName,tv_financial_address,tv_financial_address_detail;
	private ResultFinancialAgentAuditContentBean bean;
	
	private ImageView iv_bankAccountLicence_img,iv_bankCreditCode_img,iv_busiCode_img,iv_inStuCode_img,iv_registrationCode_img;
	private TextView tv_bankAccountLicence_name,tv_bankCreditCode_name,tv_busiCode_name,tv_inStuCode_name,tv_registrationCode_name;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_financialagentaudit);
		initTopTitle();
		initView();
//		orgId = getIntent().getExtras().getString("orgId");
		initData();
	}
	
	private void initData() {
		requestInsuranceAgentAudit();
		
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		list = new MouldList<FinancialPictureBean>();
		bean = new ResultFinancialAgentAuditContentBean();
		tv_financial_realName  =(TextView) findViewById(R.id.tv_financial_realName);
		tv_financial_legalPersonName = (TextView) findViewById(R.id.tv_financial_legalPersonName);
		tv_financial_address = (TextView) findViewById(R.id.tv_financial_address);
		tv_financial_address_detail = (TextView) findViewById(R.id.tv_financial_address_detail);
		
		iv_bankAccountLicence_img = (ImageView) findViewById(R.id.iv_bankAccountLicence_img);
		iv_bankCreditCode_img = (ImageView) findViewById(R.id.iv_bankCreditCode_img);
		iv_busiCode_img = (ImageView) findViewById(R.id.iv_busiCode_img);
		iv_inStuCode_img = (ImageView) findViewById(R.id.iv_inStuCode_img);
		iv_registrationCode_img = (ImageView) findViewById(R.id.iv_registrationCode_img);
		
		tv_bankAccountLicence_name = (TextView) findViewById(R.id.tv_bankAccountLicence_name);
		tv_bankCreditCode_name = (TextView) findViewById(R.id.tv_bankCreditCode_name);
		tv_busiCode_name = (TextView) findViewById(R.id.tv_busiCode_name);
		tv_inStuCode_name = (TextView) findViewById(R.id.tv_inStuCode_name);
		tv_registrationCode_name = (TextView) findViewById(R.id.tv_registrationCode_name);
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	//机构认证
	public void requestInsuranceAgentAudit(){
		
		HtmlRequest.getInsuranceAgentAudit(InsuranceAgentAudit.this, 
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						ResultFinancialAgentAuditContentBean data = (ResultFinancialAgentAuditContentBean) params.result;
						if (data!= null) {
							bean = data;
							setView();
//							list.clear();
//							FinancialPictureBean bean = new FinancialPictureBean();
//							bean.setImg(data.getBankAccountLicencePath());
//							bean.setTitle("开户许可证");
//							list.add(bean);
//							bean = new FinancialPictureBean();
//							bean.setTitle("机构信用代码证");
//							bean.setImg(data.getBankCreditCodePath());
//							list.add(bean);
//							bean = new FinancialPictureBean();
//							bean.setTitle("营业执照");
//							bean.setImg(data.getBusiCodePath());
//							list.add(bean);
//							bean = new FinancialPictureBean();
//							bean.setTitle("组织机构代码证");
//							bean.setImg(data.getInStuCodePath());
//							list.add(bean);
//							bean = new FinancialPictureBean();
//							bean.setTitle("税务登记证");
//							bean.setImg(data.getRegistrationCodePath());
//							list.add(bean);
//							FinancialAgentAdapter adapter = new FinancialAgentAdapter(InsuranceAgentAudit.this,list);
//							gv_financial_mygridview.setAdapter(adapter);
//							setGridViewHeightBasedOnChildren(gv_financial_mygridview,0);
						} else {
							
							
						}

					}

					
				});
	}
	
	/**
	* 动态设置ListView的高度
	* @param listView
	*/
	public static void setGridViewHeightBasedOnChildren(GridView gridView,int dividerHeight) {
	    if(gridView == null) 
	    	return;
	    ListAdapter listAdapter = gridView.getAdapter();
	    if (listAdapter == null) {
	        // pre-condition
	        return;
	    }
	    int totalHeight = 0;
	    for (int i = 0; i < listAdapter.getCount(); i++) {
	        View listItem = listAdapter.getView(i, null, gridView);
	        listItem.measure(0, 0);
	        totalHeight += (listItem.getMeasuredHeight()+dividerHeight);
	    }
	    ViewGroup.LayoutParams params = gridView.getLayoutParams();
	    params.height = totalHeight + (gridView.getHeight() * (listAdapter.getCount() - 1)/2);
	    gridView.setLayoutParams(params);
	}
	
	protected void setView() {
		tv_financial_realName.setText(bean.getOrgName());
		tv_financial_legalPersonName.setText(bean.getLegalPersonName());
		tv_financial_address.setText(bean.getRegionaProvince()+" "+bean.getRegionaCity());
		tv_financial_address_detail.setText(bean.getMoreAddress());
		
		imageLoader.displayImage(bean.getBankAccountLicencePath(), iv_bankAccountLicence_img);
		imageLoader.displayImage(bean.getBankCreditCodePath(), iv_bankCreditCode_img);
		imageLoader.displayImage(bean.getBusiCodePath(), iv_busiCode_img);
		imageLoader.displayImage(bean.getInStuCodePath(), iv_inStuCode_img);
		imageLoader.displayImage(bean.getRegistrationCodePath(), iv_registrationCode_img);
		
		tv_bankAccountLicence_name.setText("开户许可证");
		tv_bankCreditCode_name.setText("机构信用代码证");
		tv_busiCode_name.setText("营业执照");
		tv_inStuCode_name.setText("组织机构代码证");
		tv_registrationCode_name.setText("税务登记证");
		
		
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_financialagentaudit))
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
