package com.cf360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cf360.R;
import com.cf360.bean.ResultOrderListContentBean;
import com.cf360.mould.types.MouldList;
/**
 * 我的预约Adapter
 *
 */
public class OrderAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater inflater;
	private MouldList<ResultOrderListContentBean> list;

	public OrderAdapter(Context context,MouldList<ResultOrderListContentBean> list) {
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
		ResultOrderListContentBean bean=list.get(position);
		Holder holder=null;
		if(convertView==null){
			holder=new Holder();
			convertView = inflater.inflate(R.layout.activity_order_item, null);
			holder.txt_productname=(TextView) convertView.findViewById(R.id.order_item_order_product);
			holder.txt_status=(TextView) convertView.findViewById(R.id.order_item_order_state);
			holder.txt_order_info=(TextView) convertView.findViewById(R.id.order_item_order_info);
			holder.txt_order_date=(TextView) convertView.findViewById(R.id.order_item_order_date);
			convertView.setTag(holder);
		}else{
			holder=(Holder) convertView.getTag();
		}
		
		holder.txt_productname.setText(bean.getPRODUCTNAME());
		if(bean.getSTATUS().equals("init")){
			holder.txt_status.setText("待确认");
		}else if(bean.getSTATUS().equals("audit")){
			holder.txt_status.setText("已确认");
		}else if(bean.getSTATUS().equals("rejected")){
			holder.txt_status.setText("已驳回");
		}else if(bean.getSTATUS().equals("unsign")){
			holder.txt_status.setText("待签收");
		}else if(bean.getSTATUS().equals("unreturn")){
			holder.txt_status.setText("待寄回");
		}else if(bean.getSTATUS().equals("uncallback")){
			holder.txt_status.setText("待回收确认");
		}else if(bean.getSTATUS().equals("callbacked")){
			holder.txt_status.setText("已回收");
		}else if(bean.getSTATUS().equals("cancel")){
			holder.txt_status.setText("已取消");
		}
		holder.txt_order_info.setText(bean.getUSERNAME()+"·"+bean.getAMOUNT()+bean.getCURRENCY());
		holder.txt_order_date.setText(bean.getAPPOITMENTTIME());
		
		
		return convertView;
	}
	class Holder{
		TextView txt_productname;
		TextView txt_status;
		TextView txt_order_info;
		TextView txt_order_date;
	}
}