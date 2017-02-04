package com.cf360.adapter;

import com.cf360.R;
import com.cf360.bean.MyBankListItemBean;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.PreferenceUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WithdrawBankAdapter extends BaseAdapter {

	private MouldList<MyBankListItemBean> list = null;
	private Context context;
	private LayoutInflater inflater;
	
	public WithdrawBankAdapter(Context context, MouldList<MyBankListItemBean> list) {
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
			
			holder = new Holder();
			convertView = inflater.inflate(R.layout.activity_mybank_item_withdraw, null);
			holder.mybank_name = (TextView) convertView.findViewById(R.id.mybank_name);
			holder.mybank_bank_name = (TextView) convertView.findViewById(R.id.mybank_bank_name);
			holder.mybank_number = (TextView) convertView.findViewById(R.id.mybank_number);
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
		holder.mybank_number.setText(encryBankNum(p.getOpenAcctId()));
		
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
	}
}
