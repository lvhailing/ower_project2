package com.cf360.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cf360.R;
import com.cf360.bean.ResultContractListContentBean;
import com.cf360.bean.ResultContractListItemBean;
import com.cf360.bean.ResultHisBaoDanItemBean;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.PreferenceUtil;

/**
 * 我的合同Adapter
 * 
 */
public class HisBaoDanAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private MouldList<ResultHisBaoDanItemBean> list;

	public HisBaoDanAdapter(Context context,
			MouldList<ResultHisBaoDanItemBean> list) {
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
		ResultHisBaoDanItemBean bean = list.get(position);
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.activity_his_baodan_item,
					null);
			holder.activity_his_baodan_txt_productname = (TextView) convertView
					.findViewById(R.id.activity_his_baodan_txt_productname);
			holder.activity_his_baodan_txt_producttime = (TextView) convertView
					.findViewById(R.id.activity_his_baodan_txt_producttime);
			
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.activity_his_baodan_txt_productname.setText(bean.getPRODUCTNAME());
		holder.activity_his_baodan_txt_producttime.setText(bean.getCREATETIME());
		
		
		
		return convertView;
	}

	class Holder {
		TextView activity_his_baodan_txt_productname;
		TextView activity_his_baodan_txt_producttime;
		
	}
}