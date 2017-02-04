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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CustomerIntentionActivity extends BaseActivity implements OnClickListener {
	
	private String id =null;
	private String investType =null;
	private String investTrade =null;
	private String investMoney =null;
	private String investYear =null;
	private String investIncome =null;
	private RelativeLayout rl_intention_customer_investtype;
	private TextView tv_intention_customer_investtype_value;
	private EditText et_intention_customer_investtrade_value,et_intention_customer_investmoney_value,et_intention_customer_investyear_value,et_intention_customer_investincome_value;
	
	private String intentionItems[]={"信托产品","资管计划","阳光私募","私募股权","保险产品"};
	
	private boolean intentionSelected[]={false,false,false,false,false};
	private String newInvestType = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_myperson_customer_intention);
		initTopTitle();
		initView();
	}
	
	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		id = getIntent().getExtras().getString("id");
		investType = getIntent().getExtras().getString("investType");
		initTypeData();
		investTrade = getIntent().getExtras().getString("investTrade");
		investMoney = getIntent().getExtras().getString("investMoney");
		investYear = getIntent().getExtras().getString("investYear");
		investIncome = getIntent().getExtras().getString("investIncome");
		
		rl_intention_customer_investtype = (RelativeLayout) findViewById(R.id.rl_intention_customer_investtype);
		
		tv_intention_customer_investtype_value = (TextView) findViewById(R.id.tv_intention_customer_investtype_value);
		et_intention_customer_investtrade_value = (EditText) findViewById(R.id.et_intention_customer_investtrade_value);
		et_intention_customer_investmoney_value = (EditText) findViewById(R.id.et_intention_customer_investmoney_value);
		et_intention_customer_investyear_value = (EditText) findViewById(R.id.et_intention_customer_investyear_value);
		et_intention_customer_investincome_value = (EditText) findViewById(R.id.et_intention_customer_investincome_value);
		
		rl_intention_customer_investtype.setOnClickListener(this);
		
		setView();
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}
	private void initTypeData() {
		StringBuffer sb = new StringBuffer();
		
		if(!TextUtils.isEmpty(investType)){
			String[] split = investType.split(",");
			
			for (int i = 0; i < split.length; i++) {
				sb.append(intentionItems[Integer.parseInt(split[i])-1]+",");
			}
			if(sb.length()>0){
				sb.deleteCharAt(sb.length()-1);
			}
		}
		investType = sb.toString();
	}
	private void initTypeBackData() {
		StringBuffer sb = new StringBuffer();
		
		if(!TextUtils.isEmpty(newInvestType)){
			String[] split = newInvestType.split(",");
			for (int i = 0; i < split.length; i++) {
				
				for (int j = 0; j < intentionItems.length; j++) {
					if(split[i].equals(intentionItems[j])){
						sb.append(j+1+",");
						break;
					}
				}
				
			}
			if(sb.length()>0){
				sb.deleteCharAt(sb.length()-1);
			}
		}
		newInvestType = sb.toString();
	}

	private void initType(){
		for(int i=0;i<intentionSelected.length;i++){
			intentionSelected[i] = false;
		}
		if(!TextUtils.isEmpty(investType)){
			String[] split = investType.split(",");
			
			for (int i = 0; i < split.length; i++) {
				int j = 0;
				for(j=0;j<intentionItems.length;j++){
					if(split[i].equals(intentionItems[j])){
						intentionSelected[j] = true;
						break;
					}else{
						continue;
					}
				}
				if(j>=intentionItems.length){
					intentionSelected[j] = false;
				}
			}
		}
	}

	private void setView() {
		
		tv_intention_customer_investtype_value.setText(investType);
		
		
		et_intention_customer_investtrade_value.setText(investTrade);
		et_intention_customer_investmoney_value.setText(investMoney);
		et_intention_customer_investyear_value.setText(investYear);
		et_intention_customer_investincome_value.setText(investIncome);
		
		
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(new Action(2, 0, R.color.blue_light),CustomerIntentionActivity.this.getResources().getString(R.string.customer_save_newcustomer));
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_mycustomer_intention))
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
							requestCustomerIntentionSaveData();
							
							break;

						default:
							break;
						}
					}
				});
	}
	
	/**
	 * 
	 * 保存客户意向
	 * 
	 */
	
	public void requestCustomerIntentionSaveData() {
		
		String operation = "edit";
		
		newInvestType  = tv_intention_customer_investtype_value.getText().toString();
//		if(newInvestType.equals("潜在客户")){
//			newInvestType = "1";
//		}else if(newInvestType.equals("意向客户")){
//			newInvestType = "2";
//		}else if(newInvestType.equals("成交客户")){
//			newInvestType = "3";
//		}
		initTypeBackData();
		
		String newInvestTrade = et_intention_customer_investtrade_value.getText().toString();
		String newInvestMoney = et_intention_customer_investmoney_value.getText().toString();
		String newInvestYear = et_intention_customer_investyear_value.getText().toString();
		String newInvestIncome = et_intention_customer_investincome_value.getText().toString();
		
		
		HtmlRequest.getCustomerIntentionSave(CustomerIntentionActivity.this,id,operation,newInvestType ,newInvestTrade,newInvestMoney,newInvestYear,newInvestIncome,
				new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				
				ResultCustomerInfoContentBean data = (ResultCustomerInfoContentBean) params.result;
				
				if (data != null) {
//					infoBean = data.getUserCustomer();
					
					if(data.getFlag().equals("true")){
						Toast.makeText(CustomerIntentionActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
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
		case R.id.rl_intention_customer_investtype:
			initType();
			dialog(intentionItems,intentionSelected);
			break;

		default:
			break;
		}
		
	}
	
	private void dialog(final String items[],final boolean selected[]){
		//篮球;2:足球;3:网球;4:赛车;5:游泳;6:健身;7:滑雪;8:羽毛球;9:乒乓球;10:保龄球;11:高尔夫;12:水上运动;13:其他
//        final String items[]={};
       // final boolean selected[]={true,false,true,false,false,false,false,false,false,false,false,false,false};
		
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器  
        builder.setTitle("选择投资类型"); //设置标题  
        builder.setMultiChoiceItems(items,selected,new DialogInterface.OnMultiChoiceClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {  
               // dialog.dismiss();  
//                Toast.makeText(CustomerInstestActivity.this, items[which]+isChecked, Toast.LENGTH_SHORT).show();
            }  
        });
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();  
//                Toast.makeText(CustomerInstestActivity.this, "确定", Toast.LENGTH_SHORT).show();
                //android会自动根据你选择的改变selected数组的值。
                for (int i=0;i<selected.length;i++){
                	
            		intentionSelected = selected;
            		
            		StringBuilder sb = new StringBuilder();
            		for(int j=0;j<intentionSelected.length;j++){
            			if(intentionSelected[j]){
            				sb.append(intentionItems[j]);
            				sb.append(",");
            			}
            		}
            		if(sb.length()>1){
            			sb.deleteCharAt(sb.length()-1);
            		}
            		tv_intention_customer_investtype_value.setText(sb.toString());
            		investType = sb.toString();
                }  
//                setView();
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
	
	
}
