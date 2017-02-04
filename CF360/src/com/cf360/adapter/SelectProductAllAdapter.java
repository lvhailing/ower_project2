package com.cf360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cf360.R;
import com.cf360.bean.ResultOrderListContentBean;
import com.cf360.bean.ResultSelectProductAllBean;
import com.cf360.bean.ResultSelectProductOrderBean;
import com.cf360.mould.types.MouldList;
/**
 * 选择产品  全部产品Adapter
 *
 */
public class SelectProductAllAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater inflater;
	private MouldList<ResultSelectProductAllBean> list;

	public SelectProductAllAdapter(Context context,MouldList<ResultSelectProductAllBean> list) {
		super();
		this.context = context;
		inflater=LayoutInflater.from(context);
		this.list=list;
	}

	@Override
	public int getCount() {
		if(list==null){
			return 0;
		}else{
		return list.size();
		}
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
		ResultSelectProductAllBean bean=list.get(position);
		Holder holder=null;
		if(convertView==null){
			holder=new Holder();
			convertView = inflater.inflate(R.layout.activity_select_product_all, null);
			holder.txt_productname=(TextView) convertView.findViewById(R.id.select_product_all_name);
			holder.txt_issuer=(TextView) convertView.findViewById(R.id.select_product_all_issuer);
			convertView.setTag(holder);
		}else{
			holder=(Holder) convertView.getTag();
		}
		
		holder.txt_productname.setText(bean.getNAME());
		holder.txt_issuer.setText(bean.getISSUER());
		
		
		return convertView;
	}
	class Holder{
		TextView txt_productname;
		TextView txt_issuer;
	}
}