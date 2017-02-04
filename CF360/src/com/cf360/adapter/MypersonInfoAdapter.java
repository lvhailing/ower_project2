package com.cf360.adapter;

import java.util.ArrayList;

import com.cf360.R;
import com.cf360.bean.InfoDataContentBean;
import com.cf360.bean.MyInfoItemBean;
import com.cf360.bean.ResultRecommendProductContentBean;
import com.cf360.mould.types.MouldList;
import com.cf360.view.MyProgressBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MypersonInfoAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private MouldList<InfoDataContentBean> list;
	public MypersonInfoAdapter(Context context,
			MouldList<InfoDataContentBean> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
//		Log.e(tag, "list=="+list.size());
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return getItem(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		Log.e(tag, "getView=="+list.get(position).getAMOUNT());
		Holder holder= null;
		if(convertView==null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.activity_myperson_info_item, null);
			holder.tv_myperson_info_name = (TextView) convertView.findViewById(R.id.tv_myperson_info_name);
			holder.tv_myperson_info_money = (TextView) convertView.findViewById(R.id.tv_myperson_info_money);
			holder.tv_myperson_info_date = (TextView) convertView.findViewById(R.id.tv_myperson_info_date);
			holder.tv_myperson_info_state = (TextView) convertView.findViewById(R.id.tv_myperson_info_state);
			
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		
		holder.tv_myperson_info_name.setText(list.get(position).getPRODUCT_NAME());
		holder.tv_myperson_info_money.setText(list.get(position).getINCOTYPE()+list.get(position).getANNUAL_AMOUNT());
		holder.tv_myperson_info_date.setText(list.get(position).getCREATE_TIME());
		
//		if(list.get(position).getSTATUS().equals("success")){
//			holder.tv_myperson_info_state.setText("已完成");
//		}else if(list.get(position).getSTATUS().equals("handling")){
//			holder.tv_myperson_info_state.setText("处理中");
//		}
		
		holder.tv_myperson_info_state.setText(list.get(position).getSTATUS());
		return convertView;
	}
	class Holder{
		TextView tv_myperson_info_name;
		TextView tv_myperson_info_money;
		TextView tv_myperson_info_date;
		TextView tv_myperson_info_state;
		
	}
}
