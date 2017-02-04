package com.cf360.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cf360.R;
import com.cf360.act.ApplyDeclarationActivity;
import com.cf360.adapter.AmountAdapter;
import com.cf360.adapter.AmountAdapter.OnEditListener;
import com.cf360.bean.ResultDeclarationSearchCommissionratioDataBean;
import com.cf360.bean.ResultDeclarationSearchCommissionratioListContentBean;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.StringUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

/**
 * 打款金额
 */
public class AmountDialog extends Dialog implements
		DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {

	private Context mContext;
	private LayoutInflater inflater;
	private LayoutParams lp;
	private int percentageH = 4;
	private int percentageW = 8;
	private TextView txtConfim = null;
	private TextView txtCancel = null;
	private AmountAdapter adapter;
	private ListView listView = null;
	private MouldList<ResultDeclarationSearchCommissionratioDataBean> list = null;
	private List<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

	// private AnimationDrawable loadingAnimation;
	ArrayList<DialogInterface.OnCancelListener> m_arrCancelListeners = new ArrayList<DialogInterface.OnCancelListener>();
	ArrayList<DialogInterface.OnDismissListener> m_arrDismissListeners = new ArrayList<DialogInterface.OnDismissListener>();
	private OnSignContractChanged onChanged = null;

	public AmountDialog(Context context, OnSignContractChanged onChanged,
			MouldList<ResultDeclarationSearchCommissionratioDataBean> list,
			List<Map<String, Object>> data) {
		super(context, R.style.Dialog);
		this.mContext = context;
		this.onChanged = onChanged;
		this.list = list;
		this.data = data;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mView = inflater.inflate(R.layout.dialog_amount, null);
		setContentView(mView);
		// 设置window属性
		lp = getWindow().getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.dimAmount = 0.6f; // 去背景遮盖
		lp.alpha = 1.0f;
		int[] wh = initWithScreenWidthAndHeight(mContext);
		lp.width = wh[0] - wh[0] / percentageW;
		lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(lp);
		setCanceledOnTouchOutside(false);
		setOnDismissListener(this);
		setOnCancelListener(this);
		initView(mView);
	}

	private void initView(View mView) {
		txtConfim = (TextView) mView.findViewById(R.id.dialog_btn_confim);
		txtCancel = (TextView) mView.findViewById(R.id.dialog_btn_cancel);
		listView = (ListView) mView.findViewById(R.id.dialog_amount_lv);
		adapter = new AmountAdapter(mContext, list, data, new OnEditListener() {

			@Override
			public void onEdit(int position, List<Map<String, Object>> Data) {
				mData = Data;
			}
		});
		listView.setAdapter(adapter);
		txtConfim.setOnClickListener(confimListener);
		txtCancel.setOnClickListener(cancelListener);
	}

	private android.view.View.OnClickListener confimListener = new android.view.View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mData.size() == 0) {
				Toast.makeText(mContext, "请输入打款金额", Toast.LENGTH_SHORT).show();
			} else {
				for (int i = 0; i < mData.size(); i++) {
					Object object = mData.get(i).get("value");
					if (TextUtils.isEmpty(object.toString())
							|| Double.valueOf(object.toString())==0) {
						Toast.makeText(mContext, "请输入打款金额", Toast.LENGTH_SHORT)
								.show();
						return;
					} else {
						if (!StringUtil.checkNumber(object.toString())) {
							Toast.makeText(mContext, "请输入正确数字格式",
									Toast.LENGTH_SHORT).show();
							return;
						} else {
							if (!StringUtil.isDoubleForTwoNumber(object
									.toString())) {
								Toast.makeText(mContext, "打款金额只能输入2位小数",
										Toast.LENGTH_SHORT).show();
								return;
							} else {
								if (i == mData.size() - 1) {
									onChanged.onConfim(mData);
									onDismiss();
								}
							}

						}

					}

				}
			}
		}
	};

	private android.view.View.OnClickListener cancelListener = new android.view.View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// mData.add(object)
			for (int i = 0; i < mData.size(); i++) {
				// mData.get(i).put("value", "0");
				Object object = mData.get(i).get("value");
				// mData.get(i).clear();
				if (TextUtils.isEmpty(object.toString()) || Double.valueOf(object.toString())==0) {
					Toast.makeText(mContext, "请输入打款金额", Toast.LENGTH_SHORT)
							.show();
				} else {
					if (i == mData.size() - 1) {
						onDismiss();
					}
				}
			}
			onDismiss();
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
		public void onConfim(List<Map<String, Object>> mData);

		public void onCancel();

	}

}