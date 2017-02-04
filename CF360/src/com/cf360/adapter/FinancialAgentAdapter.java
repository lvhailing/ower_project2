package com.cf360.adapter;

import com.cf360.R;
import com.cf360.bean.FinancialPictureBean;
import com.cf360.mould.types.MouldList;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FinancialAgentAdapter extends BaseAdapter {

	private Context context ; 
	private LayoutInflater inflater;
	private MouldList<FinancialPictureBean> list;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	
	public FinancialAgentAdapter(Context context,MouldList<FinancialPictureBean> list) {
		this.context = context;
		this.list = list;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if(convertView==null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.financialagent_picture_item, null);
			holder.image_financial_item = (ImageView) convertView.findViewById(R.id.image_financial_item);
			holder.title_financial_item = (TextView) convertView.findViewById(R.id.title_financial_item);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		
		holder.title_financial_item.setText(list.get(position).getTitle());
		imageLoader.displayImage(list.get(position).getImg(), holder.image_financial_item);
		return convertView;
	}
	
	
	
	class Holder{
		ImageView image_financial_item;
		TextView title_financial_item;
	}

}
