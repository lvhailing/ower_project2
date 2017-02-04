package com.cf360.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.cf360.R;
import com.cf360.bean.PostTouabodanInfoBean;

/**
 * 合同跟踪Adapter
 * 
 */
public class ToubaodanPostAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<PostTouabodanInfoBean> list;
	private StringBuilder builder=new StringBuilder();

	public ToubaodanPostAdapter(Context context,
			List<PostTouabodanInfoBean> list) {
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
		 final PostTouabodanInfoBean bean = list.get(position);
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.post_toubaodan_item, null);
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.post_toubaodan_checkbox);
			holder.txt_info = (TextView) convertView
					.findViewById(R.id.post_toubaodan_txt_info);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.txt_info.setText(bean.getReceiveInfo());
		holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					bean.setChecked(true);
				}else{
					bean.setChecked(false);
				}
			}
		});
		
		
		
		
		return convertView;
	}

	class Holder {
		TextView txt_info;
		CheckBox checkBox;
	}

}