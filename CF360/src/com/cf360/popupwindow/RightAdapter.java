package com.cf360.popupwindow;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;

public class RightAdapter extends ArrayAdapter<String> {

	private Context mContext;
	private List<String> mListData;
	private String[] mArrayData;
	private int selectedPos = -1;
	private String selectedText = "";
	private int normalDrawbleId;
	private Drawable selectedDrawble;
	private float textSize;
	private OnClickListener onClickListener;
	private OnItemClickListener mOnItemClickListener;

	public RightAdapter(Context context, List<String> listData, int sId, int nId) {
		super(context, R.string.action_settings, listData);
		mContext = context;
		mListData = listData;
		selectedDrawble = mContext.getResources().getDrawable(sId);
		normalDrawbleId = nId;

		init();
	}

	private void init() {
		onClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				selectedPos = (Integer) view.getTag();
				setSelectedPosition(selectedPos);
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(view, selectedPos);
				}
			}
		};
	}

	public RightAdapter(Context context, String[] arrayData, int sId, int nId) {
		super(context, R.string.action_settings, arrayData);
		mContext = context;
		mArrayData = arrayData;
		selectedDrawble = mContext.getResources().getDrawable(sId);
		normalDrawbleId = nId;
		init();
	}

	public void setSelectedPosition(int pos) {
		if (mListData != null && pos < mListData.size()) {
			selectedPos = pos;
			selectedText = mListData.get(pos);
			
			notifyDataSetChanged();
		} else if (mListData != null && pos < mListData.size()) {
			selectedPos = pos;
			selectedText = mListData.get(pos);
			notifyDataSetChanged();
		}

	}
	public void setSelectedPos(int selectedPos) {
		this.selectedPos = selectedPos;
	}
	
	public void setSelectedPositionNoNotify(int pos) {
		selectedPos = pos;
		if (mListData != null && pos < mListData.size()) {
			selectedText = mListData.get(pos);
		} else if (mListData != null && pos < mListData.size()) {
			selectedText = mListData.get(pos);
		}
	}
	public int getSelectedPosition() {
		if (mListData != null && selectedPos < mListData.size()) {
			return selectedPos;
		}
		if (mListData != null && selectedPos < mListData.size()) {
			return selectedPos;
		}

		return -1;
	}

	public void setTextSize(float tSize) {
		textSize = tSize;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view;
		if (convertView == null) {
			view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.right_choose_item, parent, false);
		} else {
			view = (TextView) convertView;
		}
		view.setTag(position);
		//view.setText(mListData.get(position));
		String mString = "";
		if (mListData != null) {
			if (position < mListData.size()) {
				mString = mListData.get(position);
			}
		} else if (mListData != null) {
			if (position < mListData.size()) {
				mString = mListData.get(position);
			}
		}
		if (mString.contains(""))
			view.setText("");
		//else
			
			
			view.setText(mString);
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);

		if (selectedText != null && selectedText.equals(mString)) {
			view.setBackgroundDrawable(selectedDrawble);
			view.setTextColor(getContext().getResources().getColor(R.color.white));
		} else {
			view.setBackgroundDrawable(mContext.getResources().getDrawable(normalDrawbleId));
			view.setTextColor(getContext().getResources().getColor(R.color.bg_black));
		}
//		view.setPadding(20, 0, 0, 0);
		view.setOnClickListener(onClickListener);
		return view;
	}

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	public interface OnItemClickListener {
		public void onItemClick(View view, int position);
	}

}
