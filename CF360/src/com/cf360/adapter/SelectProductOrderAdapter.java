package com.cf360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cf360.R;
import com.cf360.bean.ResultOrderListContentBean;
import com.cf360.bean.ResultSelectProductOrderBean;
import com.cf360.mould.types.MouldList;
/**
 * 选择产品  已预约产品Adapter
 *
 */
public class SelectProductOrderAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater inflater;
	private MouldList<ResultSelectProductOrderBean> list;

	public SelectProductOrderAdapter(Context context,MouldList<ResultSelectProductOrderBean> list) {
		super();
		this.context = context;
		inflater=LayoutInflater.from(context);
		this.list=list;
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
		ResultSelectProductOrderBean bean=list.get(position);
		Holder holder=null;
		if(convertView==null){
			holder=new Holder();
			convertView = inflater.inflate(R.layout.activity_order_item_select_product, null);
			holder.txt_productname=(TextView) convertView.findViewById(R.id.order_item_order_product);
			holder.txt_issuer=(TextView) convertView.findViewById(R.id.order_item_order_state);
			holder.txt_order_info=(TextView) convertView.findViewById(R.id.order_item_order_info);
			holder.txt_order_date=(TextView) convertView.findViewById(R.id.order_item_order_date);
			convertView.setTag(holder);
		}else{
			holder=(Holder) convertView.getTag();
		}
		
		holder.txt_productname.setText(bean.getNAME());
		holder.txt_issuer.setText(bean.getISSUER());
		holder.txt_issuer.setTextColor(context.getResources().getColor(R.color.gray));
		holder.txt_order_info.setText(bean.getCUSTOMERNAME()+"·"+bean.getTotalAmount()+bean.getCURRENCY());
		holder.txt_order_date.setText(bean.getCREATETIME());
		
		
		return convertView;
	}
	class Holder{
		TextView txt_productname;
		TextView txt_issuer;
		TextView txt_order_info;
		TextView txt_order_date;
	}
}