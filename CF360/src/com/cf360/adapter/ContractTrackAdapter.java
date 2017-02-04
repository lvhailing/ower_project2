package com.cf360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cf360.R;
import com.cf360.bean.ResultContractListContentBean;
import com.cf360.bean.ResultContractListItemBean;
import com.cf360.bean.ResultContractTrackAllListBean;
import com.cf360.mould.types.MouldList;

/**
 * 合同跟踪Adapter
 * 
 */
public class ContractTrackAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private MouldList<ResultContractTrackAllListBean> list;

	public ContractTrackAdapter(Context context,
			MouldList<ResultContractTrackAllListBean> list) {
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
		ResultContractTrackAllListBean bean = list.get(position);
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.contract_track_item, null);
			holder.txt_track_creatorname = (TextView) convertView
					.findViewById(R.id.creator_name);
			holder.txt_track_noticeDesc = (TextView) convertView
					.findViewById(R.id.noticeDesc);
			holder.txt_track_createtime = (TextView) convertView
					.findViewById(R.id.create_time);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.txt_track_creatorname.setText("["+bean.getCreatorName()+"]");
		holder.txt_track_noticeDesc.setText(bean.getNoticeDesc());
		holder.txt_track_createtime.setText("["+bean.getCreateTime()+"]");
		return convertView;
	}

	class Holder {
		TextView txt_track_noticeDesc;
		TextView txt_track_creatorname;
		TextView txt_track_createtime;
	}
}