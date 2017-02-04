package com.cf360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cf360.R;
import com.cf360.bean.ResultDeclarationListItemBean;
import com.cf360.mould.types.MouldList;
/**
 * 我的报单Adapter
 *
 */
public class DeclarationAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater inflater;
	private MouldList<ResultDeclarationListItemBean> list;
	
	public DeclarationAdapter(Context context,MouldList<ResultDeclarationListItemBean> list) {
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
		ResultDeclarationListItemBean bean=list.get(position);
		Holder holder=null;
		if(convertView==null){
			holder=new Holder();
			convertView = inflater.inflate(R.layout.activity_declaration_item, null);
			holder.txt_productname=(TextView) convertView.findViewById(R.id.declaration_item_product);
			holder.txt_status=(TextView) convertView.findViewById(R.id.declaration_item_state);
			holder.txt_order_date=(TextView) convertView.findViewById(R.id.declaration_item_date);
			holder.txt_order_info=(TextView) convertView.findViewById(R.id.declaration_item_name);
			convertView.setTag(holder);
		}else{
			holder=(Holder) convertView.getTag();
		}
		holder.txt_productname.setText(bean.getPRODUCTNAME());
		if(bean.getPRODUCTCATEGORY().equals("BX")){
			if(bean.getSTATUS().equals("pendingaudit")){
				holder.txt_status.setText("待审核");
			}else if(bean.getSTATUS().equals("orderreject")){
				holder.txt_status.setText("报单驳回");
			}else if(bean.getSTATUS().equals("repaymoney")){
				holder.txt_status.setText("返佣中");
			}else if(bean.getSTATUS().equals("financialreject")){
				holder.txt_status.setText("财务驳回");
			}else if(bean.getSTATUS().equals("repayed")){
				holder.txt_status.setText("完成返佣");
			}
		}else{
			if(bean.getSTATUS().equals("pendingaudit")){
				holder.txt_status.setText("待审核");
			}else if(bean.getSTATUS().equals("orderreject")){
				holder.txt_status.setText("报单驳回");
			}else if(bean.getSTATUS().equals("pay")){
				holder.txt_status.setText("打款到账");
			}else if(bean.getSTATUS().equals("buchongorder")){
				holder.txt_status.setText("补充待审核");
			}else if(bean.getSTATUS().equals("materialreject")){
				holder.txt_status.setText("补充驳回");
			}else if(bean.getSTATUS().equals("repaymoney")){
				holder.txt_status.setText("返佣中");
			}else if(bean.getSTATUS().equals("financialreject")){
				holder.txt_status.setText("财务驳回");
			}else if(bean.getSTATUS().equals("repayed")){
				holder.txt_status.setText("完成返佣");
			}
		}
		holder.txt_order_info.setText(bean.getCUSTOMERNAME()+"·"+bean.getTotalAmount());
		holder.txt_order_date.setText(bean.getCREATETIME());
		
		return convertView;
	}
	class Holder{
		TextView txt_productname;
		TextView txt_status;
		TextView txt_order_info;
		TextView txt_order_date;
	}
}