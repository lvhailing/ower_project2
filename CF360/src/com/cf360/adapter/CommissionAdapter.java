package com.cf360.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cf360.R;
import com.cf360.bean.ResultContractListContentBean;
import com.cf360.bean.ResultContractListItemBean;
import com.cf360.bean.ResultContractTrackAllListBean;
import com.cf360.bean.ResultDeclarationDetailsUserPaymentBean;
import com.cf360.mould.types.MouldList;

/**
 * 报单详情返佣比例Adapter
 * 
 */
public class CommissionAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<String> list;

	public CommissionAdapter(Context context,
			ArrayList<String> list) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String value = list.get(position);
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.declaration_userpayment_item, null);
			holder.txt_info = (TextView) convertView
					.findViewById(R.id.declaration_userpayment_info);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
			holder.txt_info.setText(value);
		return convertView;
	}

	class Holder {
		TextView txt_info;
	}
}