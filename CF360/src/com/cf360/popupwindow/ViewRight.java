package com.cf360.popupwindow;

import java.util.ArrayList;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;
public class ViewRight extends RelativeLayout implements ViewBaseAction,OnClickListener{
	private OnSelectListener mOnSelectListener;
	private String showText = "item1";
	private Context mContext;
	private ArrayList<String> investmentFieldLst;
	private ArrayList<String> issuerLst;
	private ArrayList<String> commissionLst;
	private ArrayList<String> annualRateLst;
	private TextView view1;
	private TextView view2;
	private TextView view3;
	private TextView view4;
	private String Content1;
	private String Content2;
	private String Content3;
	private String Content4;
	private GridView gridView1;
	private GridView gridView2;
	private GridView gridView3;
	private GridView gridView4;
	private RightAdapter adapter;
	private RightAdapter adapter2;
	private RightAdapter adapter3;
	private RightAdapter adapter4;
	private final String[] items = new String[] { "item1", "item2", "item3", "item4", "item5", "item6" };//显示字段
	private final String[] itemsVaule = new String[] { "1", "2", "3", "4", "5", "6" };//隐藏id
	private String mDistance;
	public String getShowText() {
		return showText;
	}
	public ViewRight(Context context, ArrayList<String> investmentFieldLst
			, ArrayList<String> issuerLst, String Content1, String Content2) {
		super(context);
		this.investmentFieldLst=investmentFieldLst;
		this.issuerLst=issuerLst;
		this.Content1=Content1;
		this.Content2=Content2;
		init(context,investmentFieldLst);
	}
	public ViewRight(Context context, ArrayList<String> investmentFieldLst
			, ArrayList<String> issuerLst, ArrayList<String> commissionLst
			, String Content1, String Content2, String Content3) {
		super(context);
		this.investmentFieldLst=investmentFieldLst;
		this.issuerLst=issuerLst;
		this.commissionLst=commissionLst;
		this.Content1=Content1;
		this.Content2=Content2;
		this.Content3=Content3;
		init(context,investmentFieldLst);
	}
	public ViewRight(Context context, ArrayList<String> investmentFieldLst
			, ArrayList<String> issuerLst, ArrayList<String> commissionLst
			, ArrayList<String> annualRateLst, String Content1, String Content2, String Content3, String Content4) {
		super(context);
		this.investmentFieldLst=investmentFieldLst;
		this.issuerLst=issuerLst;
		this.commissionLst=commissionLst;
		this.annualRateLst=annualRateLst;
		this.Content1=Content1;
		this.Content2=Content2;
		this.Content3=Content3;
		this.Content4=Content4;
		init(context,investmentFieldLst);
	}

	public ViewRight(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context,investmentFieldLst);
	}

	public ViewRight(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context,investmentFieldLst);
	}


	public void init(Context context, final ArrayList<String> investmentFieldLst) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.gridview_distance, this, true);
		Button  Cancle= (Button)view.findViewById(R.id.Cancle);
		Button  Confirm= (Button)view.findViewById(R.id.Confirm);
		Cancle.setOnClickListener(this);
		Confirm.setOnClickListener(this);
		view1 = (TextView)view.findViewById(R.id.text1);
		view2 = (TextView)view.findViewById(R.id.text2);
		view3 = (TextView)view.findViewById(R.id.text3);
		view4 = (TextView)view.findViewById(R.id.text4);
		view1.setText(Content1);
		view2.setText(Content2);
		view3.setText(Content3);
		view4.setText(Content4);
		gridView1 = (GridView)view.findViewById(R.id.Sai_gridview_one);
		gridView2 = (GridView)view.findViewById(R.id.Sai_gridview_two);
		gridView3 = (GridView)view.findViewById(R.id.Sai_gridview_three);
		gridView4 = (GridView)view.findViewById(R.id.Sai_gridview_four);
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
			gridView1.setAdapter(adapter);
			adapter.setOnItemClickListener(new RightAdapter.OnItemClickListener() {

				@Override
				public void onItemClick(View view, int position) {

					if (mOnSelectListener != null) {
						mOnSelectListener.getGridview1(view, position);
					}
				}
			});
		}
		if(issuerLst!=null){
		adapter2 = new RightAdapter(context, issuerLst, R.drawable.shape_button_orange, R.drawable.choose_eara_item_selector1);
			adapter2.setSelectedPositionNoNotify(0);
			adapter2.setTextSize(14);
			gridView2.setAdapter(adapter2);
			
			adapter2.setOnItemClickListener(new RightAdapter.OnItemClickListener() {

				@Override
				public void onItemClick(View view, int position) {

					if (mOnSelectListener != null) {
						mOnSelectListener.getGridview2(view, position);
					}
				}
			});
		}
		if(commissionLst!=null){
			adapter3 = new RightAdapter(context, commissionLst, R.drawable.shape_button_orange, R.drawable.choose_eara_item_selector1);
			adapter3.setSelectedPositionNoNotify(0);
			adapter3.setTextSize(14);
			gridView3.setAdapter(adapter3);
			
			adapter3.setOnItemClickListener(new RightAdapter.OnItemClickListener() {

				@Override
				public void onItemClick(View view, int position) {

					if (mOnSelectListener != null) {
						mOnSelectListener.getGridview3(view, position);
					}
				}
			});

		}
		if(annualRateLst!=null){
			adapter4 = new RightAdapter(context, annualRateLst, R.drawable.shape_button_orange, R.drawable.choose_eara_item_selector1);
			adapter4.setSelectedPositionNoNotify(0);
			adapter4.setTextSize(14);
			gridView4.setAdapter(adapter4);
			
			adapter4.setOnItemClickListener(new RightAdapter.OnItemClickListener() {

				@Override
				public void onItemClick(View view, int position) {

					if (mOnSelectListener != null) {
						mOnSelectListener.getGridview4(view, position);
					}
				}
			});
		}
		

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Cancle:
			adapter.setSelectedPosition(0);
			if(adapter2!=null){
				adapter2.setSelectedPosition(0);
			}
			if(adapter3!=null){
				adapter3.setSelectedPosition(0);
			}
			if(adapter4!=null){
				adapter4.setSelectedPosition(0);
			}
			mOnSelectListener.getCancle();
			if (mOnSelectListener != null) {
				mOnSelectListener.getGridview1(gridView1, 0);
				mOnSelectListener.getGridview2(gridView2, 0);
				mOnSelectListener.getGridview3(gridView3, 0);
				mOnSelectListener.getGridview4(gridView4, 0);
			}
//			Toast.makeText(mContext,"1======="+adapter.getSelectedPosition(), 0).show();
			break;
		case R.id.Confirm:
			mOnSelectListener.getConfirm();
			break;
		default:
			break;
		}

	}
	public void ReturnState1(){
		adapter.setSelectedPosition(0);
		if(adapter2!=null){
			adapter2.setSelectedPosition(0);
		}
		if(adapter3!=null){
			adapter3.setSelectedPosition(0);
		}
		if(adapter4!=null){
			adapter4.setSelectedPosition(0);
		}
		if (mOnSelectListener != null) {
			mOnSelectListener.getGridview1(gridView1, 0);
			mOnSelectListener.getGridview2(gridView2, 0);
			mOnSelectListener.getGridview3(gridView3, 0);
			mOnSelectListener.getGridview4(gridView4, 0);
		}
//		Toast.makeText(mContext,"1======="+adapter.getSelectedPosition(), 0).show();
		
	}
	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getConfirm();
		public void getValue(String distance, String showText);
		public void getCancle();
		public void getGridview1(View veiw, int position);
		public void getGridview2(View veiw, int position);
		public void getGridview3(View veiw, int position);
		public void getGridview4(View veiw, int position);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void show() {
		
	}

}
