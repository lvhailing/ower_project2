package com.cf360.dialog;

import java.util.List;
import java.util.Map;

import com.cf360.R;
import com.cf360.view.AmountDialog.OnSignContractChanged;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CustomerInstestSportDialog extends Dialog {

	private LayoutInflater inflater;
	private Context context;
	private LayoutParams lp;
	private int percentageH = 4;
	private int percentageW = 8;
	private String items[];
	public boolean selected[];
	public boolean newselected[];
	private String title;
	private MyAdapter myAdapter;
	private OnSureListener onSure= null;

	public CustomerInstestSportDialog(Context context, String items[],
			boolean selected[], String title,OnSureListener onSure) {
		super(context, R.style.Dialog);
		this.context = context;
		this.items = items;
		this.title = title;
		this.selected = selected;
		this.newselected = selected;
		this.onSure=onSure;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(context);
		View view = inflater.inflate(
				R.layout.activity_customer_instest_sport_item, null);
		GridView gv_customer_instest = (GridView) view
				.findViewById(R.id.gv_customer_instest);

		TextView tv_customer_instest_sure = (TextView) view
				.findViewById(R.id.tv_customer_instest_sure);
		TextView tv_customer_instest_sport_title = (TextView) view
				.findViewById(R.id.tv_customer_instest_sport_title);

		tv_customer_instest_sport_title.setText(title);

		// tv_customer_call_phone_number.setText(items[0]);

		// tv_customer_call_phone_number.setOnClickListener(new
		// View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Uri uri = Uri.parse("smsto:" + phone);
		//
		// Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		//
		// intent.putExtra("sms_body", "");
		//
		// context.startActivity(intent);
		// dismiss();
		// }
		// });

		myAdapter = new MyAdapter(context, selected);
		gv_customer_instest.setAdapter(myAdapter);
		gv_customer_instest
				.setOnItemClickListener(new CustomerSportItemClickListener());

		tv_customer_instest_sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				newselected = selected;
				onSure.onConfim(newselected);
				dismiss();
			}
		});
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(view);
		lp = getWindow().getAttributes();
		lp.gravity = Gravity.BOTTOM;
		lp.dimAmount = 0.6f; // 去背景遮盖
		lp.alpha = 1.0f;
		int[] wh = initWithScreenWidthAndHeight(context);
		lp.width = wh[0] - wh[0] / percentageW;
		lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(lp);
		setCanceledOnTouchOutside(true);

	}

	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	public class CustomerSportItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
													// click happened
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {
			// 在本例中arg2=arg3
			// HashMap<String, Object> item = (HashMap<String, Object>) arg0
			// .getItemAtPosition(arg2);
			// 显示所选Item的ItemText
			// setTitle((String) item.get("ItemText"));

			if (selected[arg2] == true) {
				selected[arg2] = false;
			} else {
				selected[arg2] = true;
			}

//			for (int i = 0; i < selected.length; i++) {
//				System.out.println("ischeck[" + i + "]====" + selected[i]);
//			}
			myAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void setTitle(int titleId) {
		super.setTitle(titleId);
	}

	@Override
	public void dismiss() {
		super.dismiss();
		
	}

	
	/**
	 * 获取当前window width,height
	 * 
	 * @param context
	 * @return
	 */
	private static int[] initWithScreenWidthAndHeight(Context context) {
		int[] wh = new int[2];
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		wh[0] = dm.widthPixels;
		wh[1] = dm.heightPixels;
		return wh;
	}

	class MyAdapter extends BaseAdapter {

		private Context context;
		private boolean[] ischeck;
		private LayoutInflater inflater;

		public MyAdapter(Context context, boolean[] ischeck) {
			this.context = context;
			this.ischeck = ischeck;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return ischeck.length;
		}

		@Override
		public Object getItem(int position) {
			return ischeck[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv_customer_instest_sport_item = null;
			// if(convertView==null){
			convertView = inflater.inflate(
					R.layout.activity_customer_instest_sport, null);
			tv_customer_instest_sport_item = (TextView) convertView
					.findViewById(R.id.tv_customer_instest_sport_item);

			// }

			tv_customer_instest_sport_item.setText(items[position]);
			if (ischeck[position] == false) {
				tv_customer_instest_sport_item.setBackground(context
						.getResources().getDrawable(
								R.drawable.shape_center_gray_gray_d_instest));
				tv_customer_instest_sport_item.setTextColor(context
						.getResources().getColor(R.color.txt_gray_d));
			} else {
				tv_customer_instest_sport_item.setBackground(context
						.getResources().getDrawable(
								R.drawable.shape_button_orange));
				tv_customer_instest_sport_item.setTextColor(context
						.getResources().getColor(R.color.txt_white));
			}

			return convertView;
		}

	}
	public interface OnSureListener {
		public void onConfim(boolean[] ischeck);
	}

}
