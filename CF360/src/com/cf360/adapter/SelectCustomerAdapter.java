package com.cf360.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cf360.R;
import com.cf360.bean.ResultOrderListContentBean;
import com.cf360.bean.ResultSelectCustomerContentItemBean;
import com.cf360.mould.types.MouldList;

/**
 * 
 */
public class SelectCustomerAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private MouldList<ResultSelectCustomerContentItemBean> list;

	public SelectCustomerAdapter(Context context,
			MouldList<ResultSelectCustomerContentItemBean> list) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		} else {
			return list.size();
		}
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
		final ResultSelectCustomerContentItemBean bean = list.get(position);
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(
					R.layout.activity_select_customer_item, null);
			holder.txt_name = (TextView) convertView
					.findViewById(R.id.activity_select_customer_name);
			holder.txt_remark = (TextView) convertView
					.findViewById(R.id.activity_select_customer_remark);
			holder.img = (ImageView) convertView
					.findViewById(R.id.activity_select_customer_img);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.txt_name.setText(bean.getName());
		if (!TextUtils.isEmpty(bean.getRemark())) {
			holder.txt_remark.setText("(" + bean.getRemark() + ")");
		}else{
			holder.txt_remark.setText("");
		}
		if (!TextUtils.isEmpty(bean.getSex())) {
			if (bean.getSex().equals("man")) {
				holder.img.setImageResource(R.drawable.icon_customer_man);
			} else if(bean.getSex().equals("women")){
				holder.img.setImageResource(R.drawable.icon_customer_girl);
			}else {
				holder.img.setImageResource(R.drawable.icon_customer_man);
			}
		} else {
			holder.img.setImageResource(R.drawable.icon_customer_man);
		}

		return convertView;
	}

	class Holder {
		TextView txt_name;
		TextView txt_remark;
		ImageView img;

	}
}