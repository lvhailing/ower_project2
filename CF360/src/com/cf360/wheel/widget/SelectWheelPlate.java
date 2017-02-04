package com.cf360.wheel.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cf360.R;
import com.cf360.mould.types.MouldList;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ViewFlipper;

public class SelectWheelPlate extends PopupWindow implements
		OnClickListener, OnWheelChangedListener {

	private Context mContext;
	private View mMenuView;
	private ViewFlipper viewfipper;
	private Button btnSubmit, btnCancel;
	private WheelView_new wheelView;

	private List<String> provinceList = null;
	private ProvinceAdapter provinceAdapter;
	private OnWheelListenerInfo onWheelListener;
	private String info = null;
	public SelectWheelPlate(Context mContext,
			OnWheelListenerInfo onWheelListener,ArrayList<String> list) {
		super(mContext);
		this.mContext = mContext;
		this.onWheelListener = onWheelListener;
		this.provinceList =list;
		initView();

	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.select_wheel_license_plate, null);
		viewfipper = new ViewFlipper(mContext);
		viewfipper.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		btnSubmit = (Button) mMenuView
				.findViewById(R.id.select_wheel_license_plate_submit);
		btnCancel = (Button) mMenuView
				.findViewById(R.id.select_wheel_license_plate_cancel);
		btnSubmit.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		wheelView = (WheelView_new) mMenuView
				.findViewById(R.id.select_wheel_province);
		provinceAdapter = new ProvinceAdapter(mContext, this.provinceList);
		// 省简称和发证机关代码适配器公用一个
		wheelView.setViewAdapter(provinceAdapter);
		wheelView.addChangingListener(this);

		viewfipper.addView(mMenuView);
		viewfipper.setFlipInterval(6000000);
		this.setContentView(viewfipper);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00000000);
		this.setBackgroundDrawable(dw);
		this.update();

	}

	public void show(View parentView) {
		this.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		super.showAtLocation(parent, gravity, x, y);
		viewfipper.startFlipping();
	}

	@Override
	public void onChanged(WheelView_new wheel, int oldValue, int newValue) {
		updateLicense(wheelView);
	}

	private void updateLicense(WheelView_new wheelViewProvince) {
		if (provinceList != null) {
			setInfo(provinceList.get(wheelViewProvince.getCurrentItem()));
		}
	}

	/**
	 * Adapter for countries
	 */
	private class ProvinceAdapter extends AbstractWheelTextAdapter {

		private List<String> provinceList = null;

		/**
		 * Constructor
		 */
		protected ProvinceAdapter(Context context, List<String> provinceList) {
			super(context, R.layout.license_plate_item_layout, NO_RESOURCE);
			this.provinceList = provinceList;
			setItemTextResource(R.id.license_plate_item);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return provinceList.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			String province = provinceList.get(index);
			return province;
		}
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.select_wheel_license_plate_submit:
			if (getInfo() == null) {
				setInfo(provinceList
						.get(wheelView.getCurrentItem()));
			}
			onWheelListener.onWheel(getInfo());
			this.dismiss();
			break;
		case R.id.select_wheel_license_plate_cancel:
			this.dismiss();

			break;

		default:
			break;
		}

	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public interface OnWheelListenerInfo {
		/**
		 * @param selectedId
		 */
		public void onWheel(String info);

	}

}
