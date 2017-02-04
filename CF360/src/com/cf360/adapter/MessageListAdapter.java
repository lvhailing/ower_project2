package com.cf360.adapter;

import com.cf360.R;
import com.cf360.bean.MessageListDataContentBean;
import com.cf360.mould.types.MouldList;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageListAdapter extends BaseAdapter {

	
	private Context context;
	private MouldList<MessageListDataContentBean> list = new MouldList<MessageListDataContentBean>();
	private LayoutInflater inflater;
	public MessageListAdapter(Context context,MouldList<MessageListDataContentBean> messageList) {
		this.context = context;
		this.list = messageList;
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
			convertView = inflater.inflate(R.layout.activity_notice_item, null);
			holder.tv_notice_item_title = (TextView) convertView.findViewById(R.id.tv_notice_item_title);
			holder.tv_notice_item_time = (TextView) convertView.findViewById(R.id.tv_notice_item_time);
			holder.tv_notice_item_context = (TextView) convertView.findViewById(R.id.tv_notice_item_context);
			convertView.setTag(holder);
			
		}else{
			holder = (Holder) convertView.getTag();
			
		}
		holder.tv_notice_item_title.setText(list.get(position).getTYPE()+"·"+list.get(position).getTOPIC());
		TextPaint tp = holder.tv_notice_item_title.getPaint(); 
		if(list.get(position).getSTATUS().equals("read")){
			tp.setFakeBoldText(false);
		}else if(list.get(position).getSTATUS().equals("unread")){
			tp.setFakeBoldText(true);
		}
		holder.tv_notice_item_time.setText(list.get(position).getCREATETIME());
		holder.tv_notice_item_context.setText(list.get(position).getCONTENT());
		
		return convertView;
	}

	class Holder{
		TextView tv_notice_item_title;
		TextView tv_notice_item_time;
		TextView tv_notice_item_context;
		
	}
	
}
