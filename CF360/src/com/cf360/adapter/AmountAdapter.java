package com.cf360.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.cf360.R;
import com.cf360.bean.ResultDeclarationSearchCommissionratioDataBean;
import com.cf360.mould.types.MouldList;

/**
 * 打款金额Adapter
 * 
 */
public class AmountAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private MouldList<ResultDeclarationSearchCommissionratioDataBean> list;
	private OnEditListener listener;
	private List<Map<String, Object>> mData;// 存储的EditText值
	public Map<String, String> editorValue = new HashMap<String, String>();//

	public AmountAdapter(Context context,
			MouldList<ResultDeclarationSearchCommissionratioDataBean> list,
			List<Map<String, Object>> data, OnEditListener listener) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.list = list;
		this.mData = data;
		this.listener = listener;
		init();
	}

	// 初始化
	private void init() {
		editorValue.clear();
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

	private Integer index = -1;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ResultDeclarationSearchCommissionratioDataBean bean = list
				.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.amount_app_item, null);
			holder.txt_name = (TextView) convertView
					.findViewById(R.id.amount_item_name);
			holder.txt_commissionratio = (TextView) convertView
					.findViewById(R.id.amount_item_commissionratio);
			holder.value = (EditText) convertView
					.findViewById(R.id.amount_item_amount);
			holder.value.setTag(position);
			holder.value.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_UP) {
						index = (Integer) v.getTag();
					}
					return false;
				}
			});
			holder.tv_amout_item_current = (TextView) convertView.findViewById(R.id.tv_amout_item_current);
			
			class MyTextWatcher implements TextWatcher {
				public MyTextWatcher(ViewHolder holder) {
					mHolder = holder;
				}

				private ViewHolder mHolder;

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
				}

				@Override
				public void afterTextChanged(Editable s) {
					if (!TextUtils.isEmpty(s.toString())){
						int position = (Integer) mHolder.value.getTag();
						mData.get(position).put("value", s.toString());// 当EditText数据发生改变的时候存到data变量中
						listener.onEdit(position, mData);
					}
				}
			}
			holder.value.addTextChangedListener(new MyTextWatcher(holder));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			holder.value.setTag(position);
		}
		Object  value = mData.get(position).get("value");
		if (value != null && !"".equals(value)) {
			holder.value.setText(value.toString());
		}
		holder.value.clearFocus();
		if (index != -1 && index == position) {
			holder.value.requestFocus();
		}
		holder.txt_name.setText(bean.getPRODUCTNAME());
		holder.txt_commissionratio.setText("返佣比例" + bean.getCOMMISSIONRATIO()
				+ "%");
		holder.tv_amout_item_current.setText(bean.getCurrency());
		return convertView;
	}

	class ViewHolder {
		TextView txt_name;
		TextView txt_commissionratio;
		EditText value;
		TextView tv_amout_item_current;
		
	}

	public interface OnEditListener {
		public void onEdit(int position, List<Map<String, Object>> mData);
	}
}