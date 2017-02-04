package com.cf360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cf360.R;
import com.cf360.bean.ResultHisScheduleItemBean;
import com.cf360.mould.types.MouldList;

/**
 * 我的合同Adapter
 * 
 */
public class HisScheduledapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private MouldList<ResultHisScheduleItemBean> list;

	public HisScheduledapter(Context context,
			MouldList<ResultHisScheduleItemBean> list) {
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
		ResultHisScheduleItemBean bean = list.get(position);
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.activity_his_schedule_item,
					null);
			holder.txt_customer_name = (TextView) convertView
					.findViewById(R.id.activity_his_schedule_item_txt_customername);
			holder.txt_schedule_desc = (TextView) convertView
					.findViewById(R.id.activity_his_schedule_item_txt_schedule_desc);
			holder.txt_schedule_date = (TextView) convertView
					.findViewById(R.id.activity_his_schedule_item_txt_schedule_date);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.txt_customer_name.setText(bean.getCustomerName());
		holder.txt_schedule_date.setText(bean.getStartTime()+"至"+bean.getEndTime());
		holder.txt_schedule_desc.setText(bean.getTopic());
	
		
		return convertView;
	}

	class Holder {
		TextView txt_customer_name;
		TextView txt_schedule_desc;
		TextView txt_schedule_date;
		
	}
}