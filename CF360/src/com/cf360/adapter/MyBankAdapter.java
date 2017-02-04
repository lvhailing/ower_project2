package com.cf360.adapter;

import com.cf360.R;
import com.cf360.act.UnBandActivity;
import com.cf360.bean.MyBankListItemBean;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.PreferenceUtil;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MyBankAdapter extends BaseAdapter {

	private MouldList<MyBankListItemBean> list = null;
	private Context context;
	private LayoutInflater inflater;
	
	public MyBankAdapter(Context context, MouldList<MyBankListItemBean> list) {
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		MyBankListItemBean p = list.get(position);
		Holder holder = null;
		if(convertView==null){
			
			convertView = inflater.inflate(R.layout.activity_mybank_item, null);
			holder = new Holder();
			holder.mybank_name = (TextView) convertView.findViewById(R.id.mybank_name);
			holder.mybank_bank_name = (TextView) convertView.findViewById(R.id.mybank_bank_name);
			holder.mybank_number = (TextView) convertView.findViewById(R.id.mybank_number);
			holder.btn_unband = (Button) convertView.findViewById(R.id.btn_unband);
			convertView.setTag(holder);
			
		}else {
			holder = (Holder) convertView.getTag();
		}
//		String realName = null;
//		try {
//			realName = DESUtil.decrypt(PreferenceUtil.getUserRealName());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		holder.mybank_name.setText(p.getUserName());
		holder.mybank_bank_name.setText(p.getUsrCustId()+": ");
		
		if(p.getOpenAcctId().length()>4){
			holder.mybank_number.setText(encryBankNum(p.getOpenAcctId()));
		}else{
			holder.mybank_number.setText(p.getOpenAcctId());
		}
		
		holder.btn_unband.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i_unband = new Intent();
				i_unband.setClass(context, UnBandActivity.class);
				i_unband.putExtra("bankName", list.get(position).getUsrCustId());
				i_unband.putExtra("bankNumber", list.get(position).getOpenAcctId());
				String realName = null;
				try {
					realName = DESUtil.decrypt(PreferenceUtil.getUserRealName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				i_unband.putExtra("realName", realName);
				i_unband.putExtra("bankId", list.get(position).getId());
				i_unband.putExtra("userName", list.get(position).getUserName());
				context.startActivity(i_unband);
				
			}
		});
		return convertView;
	}
	
	public String encryBankNum(String bankNum){
		StringBuffer sb = new StringBuffer();
		sb.append(bankNum.substring(0, 4));
		sb.append(" **** **** ");
		sb.append(bankNum.substring(bankNum.length()-4, bankNum.length()));
		
		return sb.toString();
	}
	
	class Holder{
		TextView mybank_name;
		TextView mybank_bank_name;
		TextView mybank_number;
		Button btn_unband;
	}
}
