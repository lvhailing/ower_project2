package com.cf360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cf360.R;
import com.cf360.bean.ResultToubaodanListItemBean;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.PreferenceUtil;

/**
 * 我的投保单Adapter
 * 
 */
public class ToubaodanAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private MouldList<ResultToubaodanListItemBean> list;

	public ToubaodanAdapter(Context context,
			MouldList<ResultToubaodanListItemBean> list) {
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
		ResultToubaodanListItemBean bean = list.get(position);
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.activity_toubaodan_item,
					null);
			holder.txt_Productname = (TextView) convertView
					.findViewById(R.id.toubaodan_item_product);
			holder.txt_toubaodan_status = (TextView) convertView
					.findViewById(R.id.toubaodan_item_state);
			holder.txt_toubaodan_noticedesc = (TextView) convertView
					.findViewById(R.id.toubaodan_item_record);
			holder.txt_toubaodan_createtime = (TextView) convertView
					.findViewById(R.id.toubaodan_item_date);
			holder.txt_toubaodan_creatorname = (TextView) convertView
					.findViewById(R.id.toubaodan_item_tag);
			holder.txt_toubaodan_customername = (TextView) convertView
					.findViewById(R.id.toubaodan_item_tagname);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.txt_Productname.setText(bean.getPRODUCTNAME());
		if (bean.getSTATUS().equals("init")) {
			holder.txt_toubaodan_status.setText("待确认");
		} else if (bean.getSTATUS().equals("pass")) {
			holder.txt_toubaodan_status.setText("待发送");
		} else if (bean.getSTATUS().equals("nopass")) {
			holder.txt_toubaodan_status.setText("已驳回");
		} else if (bean.getSTATUS().equals("unsign")) {
			holder.txt_toubaodan_status.setText("待签收");
		} else if (bean.getSTATUS().equals("unreturn")) {
			holder.txt_toubaodan_status.setText("待寄回");
		} else if (bean.getSTATUS().equals("uncallback")) {
			holder.txt_toubaodan_status.setText("待回收确认");
		} else if (bean.getSTATUS().equals("callbacked")) {
			holder.txt_toubaodan_status.setText("已回收");
		}
		if (bean.getNOTICEDESC().contains("<")) {
			String noticeDesc=bean.getNOTICEDESC().substring(0, bean.getNOTICEDESC().indexOf("<"))+bean.getNOTICEDESC().substring(bean.getNOTICEDESC().lastIndexOf(">")+1, bean.getNOTICEDESC().length());
			holder.txt_toubaodan_noticedesc.setText(noticeDesc);
		} else {
			holder.txt_toubaodan_noticedesc.setText(bean.getNOTICEDESC());
		}
		holder.txt_toubaodan_createtime.setText("[" + bean.getCREATETIME()
				+ "]");
		if (PreferenceUtil.getUserType().equals("corp")) {// 机构
			holder.txt_toubaodan_creatorname.setText("[机构理财师]");
		} else {
			holder.txt_toubaodan_creatorname.setText("[理财师]");
		}
		holder.txt_toubaodan_customername.setText(bean.getCUSTOMERNAME());

		return convertView;
	}

	class Holder {
		TextView txt_Productname;
		TextView txt_toubaodan_status;
		TextView txt_toubaodan_noticedesc;
		TextView txt_toubaodan_createtime;
		TextView txt_toubaodan_creatorname;
		TextView txt_toubaodan_customername;
	}
}