package com.cf360.dialog;

import com.cf360.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class CustomerInfoPhoneDialog extends Dialog {

	private LayoutInflater inflater;
	private Context context;
	private String items[];
	private LayoutParams lp;
	private int percentageH = 4;
	private int percentageW = 8;
	private String phone;
	private String title;
	public CustomerInfoPhoneDialog(Context context,String title,String items[],String phone) {
		super(context, R.style.Dialog);
		this.context = context;
		this.items = items;
		this.phone = phone;
		this.title = title;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.activity_customer_call_phone, null);
		TextView tv_customer_call_phone_number = (TextView) view.findViewById(R.id.tv_customer_call_phone_number);
		TextView tv_customer_call_phone_cancel = (TextView) view.findViewById(R.id.tv_customer_call_phone_cancel);
		TextView tv_customer_beizhu_title = (TextView) view.findViewById(R.id.tv_customer_beizhu_title);
		tv_customer_beizhu_title.setText(title);
		tv_customer_call_phone_number.setText(items[0]);
		tv_customer_call_phone_number.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i_phone = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:" + phone));
				context.startActivity(i_phone);
				dismiss();
			}
		});
		
		tv_customer_call_phone_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
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

}
