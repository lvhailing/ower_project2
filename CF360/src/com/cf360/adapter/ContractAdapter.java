package com.cf360.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cf360.R;
import com.cf360.bean.ResultContractListContentBean;
import com.cf360.bean.ResultContractListItemBean;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.PreferenceUtil;

/**
 * 我的合同Adapter
 * 
 */
public class ContractAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private MouldList<ResultContractListItemBean> list;

	public ContractAdapter(Context context,
			MouldList<ResultContractListItemBean> list) {
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
		ResultContractListItemBean bean = list.get(position);
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.activity_contract_item,
					null);
			holder.txt_Productname = (TextView) convertView
					.findViewById(R.id.contract_item_product);
			holder.txt_contract_status = (TextView) convertView
					.findViewById(R.id.contract_item_state);
			holder.txt_contract_noticedesc = (TextView) convertView
					.findViewById(R.id.contract_item_record);
			holder.txt_contract_createtime = (TextView) convertView
					.findViewById(R.id.contract_item_date);
			holder.txt_contract_creatorname = (TextView) convertView
					.findViewById(R.id.contract_item_tag);
			holder.txt_contract_customername = (TextView) convertView
					.findViewById(R.id.contract_item_tagname);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.txt_Productname.setText(bean.getPRODUCTNAME());
		if (bean.getSTATUS().equals("init")) {
			holder.txt_contract_status.setText("待确认");
		} else if (bean.getSTATUS().equals("audit")) {
			holder.txt_contract_status.setText("待发送");
		} else if (bean.getSTATUS().equals("rejected")) {
			holder.txt_contract_status.setText("已驳回");
		} else if (bean.getSTATUS().equals("unsign")) {
			holder.txt_contract_status.setText("待签收");
		} else if (bean.getSTATUS().equals("unreturn")) {
			holder.txt_contract_status.setText("待寄回");
		} else if (bean.getSTATUS().equals("uncallback")) {
			holder.txt_contract_status.setText("待回收确认");
		} else if (bean.getSTATUS().equals("callbacked")) {
			holder.txt_contract_status.setText("已回收");
		}
		if (!TextUtils.isEmpty(bean.getNOTICEDESC())) {
			if (bean.getNOTICEDESC().contains(bean.getNICKNAME())) {
				String noticeDesc=bean.getNOTICEDESC().replace(bean.getNICKNAME(), "您");
				if (noticeDesc.contains("<")) {
					String noticeDescOne=noticeDesc.substring(0, noticeDesc.indexOf("<"))+noticeDesc.substring(noticeDesc.lastIndexOf(">"), noticeDesc.length());
					holder.txt_contract_noticedesc.setText(noticeDescOne);
				} else {
					holder.txt_contract_noticedesc.setText(bean.getNOTICEDESC());
				}
			} else {
				if (bean.getNOTICEDESC().contains("<")) {
					String noticeDescTwo=bean.getNOTICEDESC().substring(0, bean.getNOTICEDESC().indexOf("<"))+bean.getNOTICEDESC().substring(bean.getNOTICEDESC().lastIndexOf(">")+1, bean.getNOTICEDESC().length());
					holder.txt_contract_noticedesc.setText(noticeDescTwo);
				} else {
					holder.txt_contract_noticedesc.setText(bean.getNOTICEDESC());
				}
			}
			
			
			if (bean.getNOTICEDESC().contains("<")) {
				bean.getNOTICEDESC().substring(bean.getNOTICEDESC().indexOf("<"), bean.getNOTICEDESC().lastIndexOf(">"));
			} else {
				holder.txt_contract_noticedesc.setText(bean.getNOTICEDESC());
			}
		}
		if(!TextUtils.isEmpty(bean.getCREATETIME())){
			holder.txt_contract_createtime.setText("["+bean.getCREATETIME()+"]");
		}
		if (PreferenceUtil.getUserType().equals("corp")) {// 机构
			holder.txt_contract_creatorname.setText("[机构理财师]");
		} else {
			holder.txt_contract_creatorname.setText("[理财师]");
		}
		holder.txt_contract_customername.setText(bean.getCUSTOMERNAME());
		return convertView;
	}

	class Holder {
		TextView txt_Productname;
		TextView txt_contract_status;
		TextView txt_contract_noticedesc;
		TextView txt_contract_createtime;
		TextView txt_contract_creatorname;
		TextView txt_contract_customername;
	}
}