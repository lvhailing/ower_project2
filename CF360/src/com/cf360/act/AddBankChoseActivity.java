package com.cf360.act;

import com.cf360.R;
import com.cf360.bean.ChoseMyBankBean;
import com.cf360.bean.ResultBankListMessageContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddBankChoseActivity extends BaseActivity {
	
	private ListView lv_mybank_chose;
	private MouldList<ChoseMyBankBean> list;
	private String[] bankName = {"工商银行","农业银行","建设银行","工商银行","农业银行","建设银行","工商银行","农业银行","建设银行"};
	private String bankNameChose = null;
	private int[] bankLogo = {R.drawable.yang01,R.drawable.yang02,R.drawable.yang03,R.drawable.yang01,R.drawable.yang02,R.drawable.yang03,R.drawable.yang01,R.drawable.yang02,R.drawable.yang03};
	private LayoutInflater inflater;
	private ResultBankListMessageContentBean bankList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		baseSetContentView(R.layout.activity_mybank_chose);
		bankNameChose = getIntent().getExtras().getString("bankName");
		initTopTitle();
		initView();
		initData();
	}
	
	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_addmybankchose))
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
	
	private void initData() {
		requestBankMessage();
		
		
	}

	//获取银行信息
	public void requestBankMessage(){
		
		HtmlRequest.getBankListMessage(AddBankChoseActivity.this, new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				
				ResultBankListMessageContentBean bean = (ResultBankListMessageContentBean) params.result;
				if (bean!= null) {
					bankList = bean;
					lv_mybank_chose.setAdapter(new ChoseMyBankAdapter());
					
//					getBankData();
				} else {
					
				}
				
			}
		});
		
	}
		
	private void getBankData() {
		list = new MouldList<ChoseMyBankBean>();
		for(int i=0;i<bankList.getKeys().size();i++){
			ChoseMyBankBean bean = new ChoseMyBankBean();
			bean.setBankName(bankName[i]);
			bean.setBanklogo(bankLogo[i]);
			if(!TextUtils.isEmpty(bankNameChose)){
				if(bankNameChose.equals(bankList.getKeys().get(i))){
					bean.setState(true);
				}else{
					bean.setState(false);
				}		
			}
			list.add(bean);
		}
		
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		inflater = LayoutInflater.from(this);
		lv_mybank_chose = (ListView) findViewById(R.id.lv_mybank_chose);
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}
	
	class ChoseMyBankAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return bankList.getKeys().size();
		}

		@Override
		public Object getItem(int position) {
			return bankList.getKeys().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			Holder holder =null;
			if(convertView==null){
				holder = new Holder();
				convertView = inflater.inflate(R.layout.activity_mybank_chose_item, null);
				holder.rl_add_mybank_layout = (RelativeLayout) convertView.findViewById(R.id.rl_add_mybank_layout);
				holder.tv_mybank_chose_bankname = (TextView) convertView.findViewById(R.id.tv_mybank_chose_bankname);
				holder.iv_mybank_chose_banklogo = (ImageView) convertView.findViewById(R.id.iv_mybank_chose_banklogo);
				holder.iv_mybank_chose_state = (ImageView) convertView.findViewById(R.id.iv_mybank_chose_state);
				convertView.setTag(holder);
				
			}else {
				holder = (Holder) convertView.getTag();
			}
			holder.tv_mybank_chose_bankname.setText((String)bankList.getKeys().get(position));
			
//			holder.iv_mybank_chose_banklogo.setImageResource(list.get(position).getBanklogo());
			if(bankList.getValues().get(position).equals("ICBC")){
				holder.iv_mybank_chose_banklogo.setImageResource(R.drawable.bank_icbc);
			}else if(bankList.getValues().get(position).equals("CBC")){
				holder.iv_mybank_chose_banklogo.setImageResource(R.drawable.bank_ccb);
			}else if(bankList.getValues().get(position).equals("ABC")){
				holder.iv_mybank_chose_banklogo.setImageResource(R.drawable.bank_abc);
			}else if(bankList.getValues().get(position).equals("CMBC")){
				holder.iv_mybank_chose_banklogo.setImageResource(R.drawable.bank_cmb);
			}else if(bankList.getValues().get(position).equals("BOC")){
				holder.iv_mybank_chose_banklogo.setImageResource(R.drawable.bank_boc);
			}else if(bankList.getValues().get(position).equals("BCM")){
				holder.iv_mybank_chose_banklogo.setImageResource(R.drawable.bank_comm);
			}else if(bankList.getValues().get(position).equals("")){
				
			}else if(bankList.getValues().get(position).equals("")){
				
			}else if(bankList.getValues().get(position).equals("")){
				
			}else if(bankList.getValues().get(position).equals("")){
				
			}else if(bankList.getValues().get(position).equals("")){
				
			}else if(bankList.getValues().get(position).equals("")){
				
			}
			if(!TextUtils.isEmpty(bankNameChose)){
				if(bankNameChose.equals((String)bankList.getKeys().get(position))){
					holder.iv_mybank_chose_state.setVisibility(View.VISIBLE);
				}else {
					holder.iv_mybank_chose_state.setVisibility(View.GONE);
				}
			}else{
				holder.iv_mybank_chose_state.setVisibility(View.GONE);
			}
			
			holder.rl_add_mybank_layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i_result = new Intent();
					i_result.setClass(AddBankChoseActivity.this, AddBankActivity.class);
//					i_result.putExtra("second", list.get(position).getBankName());
					i_result.putExtra("bankName", (String)bankList.getKeys().get(position));
					i_result.putExtra("bankNum", (String)bankList.getValues().get(position));
					setResult(AddBankActivity.RESULTCODE,i_result);
					finish();
					
				}
			});
			
			return convertView;
		}
		
	}
	class Holder{
		RelativeLayout rl_add_mybank_layout;
		ImageView iv_mybank_chose_banklogo;
		TextView tv_mybank_chose_bankname;
		ImageView iv_mybank_chose_state;
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
