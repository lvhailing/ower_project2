package com.cf360.dialog;

import java.util.ArrayList;

import com.cf360.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

/**
 * 输入专户邀请码
 */
public class CopyOfCustomerInfoPhoneDialog extends Dialog implements
		DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {

	private Context mContext;
	private LayoutInflater inflater;
	private LayoutParams lp;
	private int percentageH = 4;
	private int percentageW = 8;
	private TextView txtConfim = null;
	private TextView txtCancel = null;
	private TextView tvCancel = null;
	private TextView txtInfo = null;
	private LinearLayout txtRead;
	private EditText editInput;

	ArrayList<DialogInterface.OnCancelListener> m_arrCancelListeners = new ArrayList<DialogInterface.OnCancelListener>();
	ArrayList<DialogInterface.OnDismissListener> m_arrDismissListeners = new ArrayList<DialogInterface.OnDismissListener>();
	private OnSignContractChanged onChanged = null;

	public CopyOfCustomerInfoPhoneDialog(Context context,
			OnSignContractChanged onChanged) {
		super(context, R.style.Dialog);
		this.mContext = context;
		this.onChanged = onChanged;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View mView = inflater.inflate(R.layout.input_special_code, null);
//		setContentView(mView);
//		// 设置window属性
//		lp = getWindow().getAttributes();
//		lp.gravity = Gravity.CENTER;
//		lp.dimAmount = 0.6f; // 去背景遮盖
//		lp.alpha = 1.0f;
//		int[] wh = initWithScreenWidthAndHeight(mContext);
//		lp.width = wh[0] - wh[0] / percentageW;
//		lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
//		getWindow().setAttributes(lp);
//		setCanceledOnTouchOutside(false);
//		setOnDismissListener(this);
//		setOnCancelListener(this);
//		initView(mView);

	}

	private void initView(View mView) {/*
		editInput = (EditText) mView.findViewById(R.id.dialog_edit_input);
		txtInfo = (TextView) mView.findViewById(R.id.input_special_code_info);
		txtRead = (LinearLayout) mView
				.findViewById(R.id.input_special_code_read);
		txtConfim = (TextView) mView.findViewById(R.id.dialog_btn_confim);
		tvCancel = (TextView) mView.findViewById(R.id.dialog_tv_cancel);
		txtCancel = (TextView) mView.findViewById(R.id.dialog_btn_cancel);
		txtConfim.setOnClickListener(confimListener);
		txtCancel.setOnClickListener(cancelListener);
		tvCancel.setOnClickListener(cancelListenerOne);
		txtRead.setOnClickListener(readListener);
		String str1, str2, str3;
		str1 = "温馨提示:";
		str2 = str1 + "该产品只对特定用户开放,如您没有投资顾问的邀请码,请投资其他理财产品。";
		str3 = str2 + "";
		txtInfo.setText(StringUtil.setTextStyle(mContext, str1, str2, str3,
				R.color.txt_orange, R.color.gray_dark, R.color.white, 12, 12,
				12, 0, 0, 0));
	*/}

	private android.view.View.OnClickListener confimListener = new android.view.View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (TextUtils.isEmpty(editInput.getText().toString())) {
				Toast.makeText(mContext, "请输入专户邀请码", Toast.LENGTH_LONG).show();
			} else {
				onChanged.onConfim(editInput.getText().toString());
			}
		}

	};

	private android.view.View.OnClickListener cancelListener = new android.view.View.OnClickListener() {

		@Override
		public void onClick(View v) {
			onDismiss();
		}
	};
	private android.view.View.OnClickListener cancelListenerOne = new android.view.View.OnClickListener() {

		@Override
		public void onClick(View v) {
			onDismiss();
		}
	};
	private android.view.View.OnClickListener readListener = new android.view.View.OnClickListener() {

		@Override
		public void onClick(View v) {
			/*Intent intent = new Intent(mContext, BuyReadActivity.class);
			mContext.startActivity(intent);*/
			/*Intent i_web = new Intent(mContext, WebActivity.class);
			i_web.putExtra("type", WebActivity.WEBTYPE_VJINKE_ZHUANHU);
			mContext.startActivity(i_web);*/
		}
	};

	private void ondismiss() {

	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		if (m_arrDismissListeners != null) {
			for (int x = 0; x < m_arrDismissListeners.size(); x++)
				m_arrDismissListeners.get(x).onDismiss(dialog);
		}
		ondismiss();
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		if (m_arrCancelListeners != null) {
			for (int x = 0; x < m_arrDismissListeners.size(); x++)
				m_arrCancelListeners.get(x).onCancel(dialog);
		}
	}

	public void addListeners(OnCancelListener c, OnDismissListener d) {
		m_arrDismissListeners.add(d);
		m_arrCancelListeners.add(c);
	}

	public void removeListeners(OnCancelListener c, OnDismissListener d) {
		m_arrDismissListeners.remove(d);
		m_arrCancelListeners.remove(c);
	}

	private void onDismiss() {
		if (this.isShowing()) {
			this.dismiss();
		}

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

	public interface OnSignContractChanged {
		public void onConfim(String inputString);

		public void onCancel();

	}

}