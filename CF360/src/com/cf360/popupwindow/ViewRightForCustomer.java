package com.cf360.popupwindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.wheel.widget.SelectWheeelDate;
import com.cf360.wheel.widget.SelectWheeelDate.OnWheelListener;
public class ViewRightForCustomer extends RelativeLayout implements ViewBaseAction,OnClickListener{
	private OnSelectListener mOnSelectListener;
	private String showText = "item1";
	private Context mContext;
	private ArrayList<String> customerLst;
	private GridView gridView;
	private RightAdapter adapter;
	private RelativeLayout layout_time;
	private TextView txt_time;
	private String investDate;
	private View view;
	private View gridview;
	public String getShowText() {
		return showText;
	}
	public ViewRightForCustomer(Context context, ArrayList<String> customerLst,View view) {
		super(context);
		this.customerLst=customerLst;
		this.view=view;
		init(context,customerLst);
	}

	public void init(Context context, final ArrayList<String> investmentFieldLst) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.gridview_customer, this, true);
		layout_time=(RelativeLayout) view.findViewById(R.id.gridview_customer_layout_select_time);
		txt_time=(TextView) view.findViewById(R.id.gridview_customer_txt_select_time);
		Button  Cancle= (Button)view.findViewById(R.id.Cancle);
		Button  Confirm= (Button)view.findViewById(R.id.Confirm);
		Cancle.setOnClickListener(this);
		Confirm.setOnClickListener(this);
		layout_time.setOnClickListener(this);
		gridView = (GridView)view.findViewById(R.id.gridview_customer);
		if(investmentFieldLst!=null){
			adapter = new RightAdapter(context, investmentFieldLst, R.drawable.shape_button_orange, R.drawable.choose_eara_item_selector1);
			adapter.setSelectedPositionNoNotify(0);
			adapter.setTextSize(14);
		/*	if (mDistance != null) {
				for (int i = 0; i < itemsVaule.length; i++) {
					if (itemsVaule[i].equals(mDistance)) {
						adapter.setSelectedPositionNoNotify(i);
						showText = items[i];
						break;
					}
				}
			}*/
			gridView.setAdapter(adapter);
			adapter.setOnItemClickListener(new RightAdapter.OnItemClickListener() {

				@Override
				public void onItemClick(View view, int position) {
					    gridview=view;
					if (mOnSelectListener != null) {
						mOnSelectListener.getGridview(view, position);
						mOnSelectListener.getSelectTime("指定时间");
						txt_time.setText("指定时间");
					}
				}
			});
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Cancle:
//			adapter.setSelectedPosition(0);
			mOnSelectListener.getCancle();
			break;
		case R.id.Confirm:
			mOnSelectListener.getConfirm();
			break;
		case R.id.gridview_customer_layout_select_time:
			selectBuyDate();
			break;
		default:
			break;
		}

	}
	public void ReturnState1(){
		//Toast.makeText(mContext,"1", 0).show();
		adapter.setSelectedPosition(0);
	}
	public void setPreviousData(){
		txt_time.setText("指定时间");
	}
	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}
	private void selectBuyDate() {
		SelectWheeelDate wheel = new SelectWheeelDate(mContext,
				new OnWheelListener() {

					@Override
					public void onWheel(Boolean isSubmit, String year,
							String month, String day) {
						investDate = year + "-" + month + "-" + day;
						txt_time.setText(investDate);
						if(!(txt_time.getText().toString().equals("指定时间"))){
							adapter.setSelectedPosition(0);
						}
						mOnSelectListener.getSelectTime(investDate);
						mOnSelectListener.getGridview(gridview, 0);
					}
				}, true, new SimpleDateFormat("yyyy").format(new Date()),
				new SimpleDateFormat("M").format(new Date()),
				new SimpleDateFormat("dd").format(new Date()));
		wheel.show(view);
	}
	public interface OnSelectListener {
		public void getConfirm();
		public void getValue(String distance, String showText);
		public void getCancle();
		public void getGridview(View veiw, int position);
		public void getSelectTime(String time);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void show() {
		
	}





}
