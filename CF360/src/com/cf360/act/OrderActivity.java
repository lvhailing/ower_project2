package com.cf360.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cf360.R;
import com.cf360.adapter.ContractAdapter;
import com.cf360.adapter.OrderAdapter;
import com.cf360.bean.ResultContractListContentBean;
import com.cf360.bean.ResultOrderListContentBean;
import com.cf360.bean.TestBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 我的预约
 * 
 */
public class OrderActivity extends BaseActivity implements OnClickListener {
	private TextView text_orderAll;
	private TextView text_orderState;
	private PopupWindow popuWindow;
	private RelativeLayout relativeLayout, layout_orderAll, layout_orderState;
	private TextView orderAllLines, orderStateLines;
	private PullToRefreshListView listview;
	private OrderAdapter adapter;
	private ListView lv_left;
	private PopUpWindowAdapterLeft popUpWindowAdapterLeft;
	private PopUpWindowAdapterRight popUpWindowAdapterRight;
	private int clickLeftPosition = 0;
	private int clickRightPosition = 0;
	private MouldList<ResultOrderListContentBean> list;
	private int pro_page = 1;
	private int cachePage_pro = pro_page;
	private String category;
	private String status;;
	private MouldList<ResultOrderListContentBean> data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_order);
		initView();
		initData();
		initTopTitle();
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setLogo(R.drawable.img_logo, false).setIndicator(R.drawable.back)
				.setCenterText(getResources().getString(R.string.title_order))
				.showMore(false).setOnActionListener(new OnActionListener() {

					@Override
					public void onMenu(int id) {
					}

					@Override
					public void onBack() {
						finish();
					}

					@Override
					public void onAction(int id) {

					}
				});
	}

	private void initView() {
		ActivityStack stack=ActivityStack.getActivityManage();
		stack.addActivity(this);
		text_orderAll = (TextView) findViewById(R.id.order_txt_orderall);
		text_orderState = (TextView) findViewById(R.id.order_txt_allstate);
		orderAllLines = (TextView) findViewById(R.id.order_txt_lines);
		orderStateLines = (TextView) findViewById(R.id.order_txt_statelines);
		layout_orderAll = (RelativeLayout) findViewById(R.id.order_layout_orderall);
		layout_orderState = (RelativeLayout) findViewById(R.id.order_layout_allstate);
		layout_orderAll.setOnClickListener(this);
		layout_orderState.setOnClickListener(this);
		listview = (PullToRefreshListView) findViewById(R.id.listview);
		
		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
				initData();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
	}

	private void initData() {
		orderAllLines.setBackgroundColor(getResources()
				.getColor(R.color.orange));
		text_orderAll.setTextColor(getResources().getColor(R.color.orange));

		list = new MouldList<ResultOrderListContentBean>();
		requestData("(null)", "(null)", 1+"");
		listview.setOnRefreshListener(new OnRefreshListener<ListView>() {
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHeaderShown()) {
					if (pro_page >= 2) {
						requestData(category, status, (--pro_page)+"");
					} else {
						requestData(category, status, 1+"");
					}

				} else {
					requestData(category, status, (++pro_page)+"");
				}

			}
		});
		adapter = new OrderAdapter(this, list);
		/*try {
			new Thread().sleep(1000);*/
			listview.setAdapter(adapter);
		/*} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ResultOrderListContentBean bean = list.get(position - 1);
				Intent intent = new Intent(OrderActivity.this,
						OrderDetailsActivity.class);
				intent.putExtra("id", bean.getID());
				intent.putExtra("productName", bean.getPRODUCTNAME());
				intent.putExtra("status", bean.getSTATUS());
				intent.putExtra("type", bean.getPRODUCTCATEGORY());
				startActivity(intent);
			}
		});
	}

	private void requestData(final String category, String status, String page) {
		HtmlRequest.getOrderList(OrderActivity.this, category, page, status,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							data = (MouldList<ResultOrderListContentBean>) params.result;
							if (data.size() == 0 && pro_page != 1) {
								Toast.makeText(OrderActivity.this, "已经到最后一页",
										Toast.LENGTH_SHORT).show();
								pro_page = cachePage_pro - 1;
								listview.getRefreshableView()
										.smoothScrollToPositionFromTop(0, 80,
												100);
								listview.onRefreshComplete();
							} else {
								list.clear();
								list.addAll(data);
								adapter.notifyDataSetChanged();
								listview.postDelayed(new Runnable() {
									@Override
									public void run() {
										listview.onRefreshComplete();
									}
								}, 1000);
								listview.getRefreshableView()
										.smoothScrollToPositionFromTop(0, 80,
												100);
							}

						} else {
							netFail = true;
							listview.onRefreshComplete();
							Toast.makeText(OrderActivity.this, "加载失败，请确认网络通畅",
									Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_layout_orderall:
			orderAllLines.setBackgroundColor(getResources().getColor(
					R.color.orange));
			orderStateLines.setBackgroundColor(getResources().getColor(
					R.color.gray));
			text_orderAll.setTextColor(getResources().getColor(R.color.orange));
			text_orderState.setTextColor(getResources().getColor(
					R.color.gray));
			Drawable drawableAll= getResources().getDrawable(R.drawable.up_orange_order);
			drawableAll.setBounds(0, 0, drawableAll.getMinimumWidth(), drawableAll.getMinimumHeight());
			text_orderAll.setCompoundDrawables(null,null,drawableAll,null);
			
			Drawable drawableState= getResources().getDrawable(R.drawable.down_gray_order);
			drawableState.setBounds(0, 0, drawableState.getMinimumWidth(), drawableState.getMinimumHeight());
			text_orderState.setCompoundDrawables(null,null,drawableState,null);
			initPopuWindowLeft(v);
			break;
		case R.id.order_layout_allstate:
			orderAllLines.setBackgroundColor(getResources().getColor(
					R.color.gray));
			orderStateLines.setBackgroundColor(getResources().getColor(
					R.color.orange));
			text_orderAll.setTextColor(getResources().getColor(
					R.color.gray));
			text_orderState.setTextColor(getResources()
					.getColor(R.color.orange));
			Drawable drawableAllOne= getResources().getDrawable(R.drawable.down_gray_order);
			drawableAllOne.setBounds(0, 0, drawableAllOne.getMinimumWidth(), drawableAllOne.getMinimumHeight());
			text_orderAll.setCompoundDrawables(null,null,drawableAllOne,null);
			
			Drawable drawableStateOne= getResources().getDrawable(R.drawable.up_orange_order);
			drawableStateOne.setBounds(0, 0, drawableStateOne.getMinimumWidth(), drawableStateOne.getMinimumHeight());
			text_orderState.setCompoundDrawables(null,null,drawableStateOne,null);
			initPopuWindowRight(v);
			break;

		default:
			break;
		}
	}

	protected void initPopuWindowLeft(View parent) {
		if (popuWindow == null) {
			LayoutInflater mLayoutInflater = LayoutInflater.from(this);
			relativeLayout = (RelativeLayout) mLayoutInflater.inflate(
					R.layout.popuwindow_layuout, null);
			popuWindow = new PopupWindow(relativeLayout,
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			lv_left = (ListView) relativeLayout
					.findViewById(R.id.popuwindow_layuout_left_lv);
		}
		lv_left.setFocusable(true);
		lv_left.setItemsCanFocus(true);
		popUpWindowAdapterLeft = new PopUpWindowAdapterLeft();
		lv_left.setAdapter(popUpWindowAdapterLeft);
		lv_left.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				if (position != clickLeftPosition) {
					clickLeftPosition = position;
					if (clickLeftPosition == 0) {// 全部类型
						category = "(null)";
					} else {
						category = getLeftData().get(position).get("info")
								.toString();
					}
					requestData(category, status, 1+"");
				} else {
					clickLeftPosition = 0;
				}
				popUpWindowAdapterLeft.notifyDataSetChanged();
				popuWindow.dismiss();

			}
		});

		ColorDrawable cd = new ColorDrawable(0x000000);
		popuWindow.setBackgroundDrawable(cd);
		// 产生背景变暗效果
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.7f;
		getWindow().setAttributes(lp);
		popuWindow.showAsDropDown(orderAllLines);
		popuWindow.setOutsideTouchable(true);
		popuWindow.setFocusable(true);
		popuWindow.showAtLocation((View) parent.getParent(), Gravity.TOP
				| Gravity.CENTER_HORIZONTAL, 0, 0);
		popuWindow.update();
		popuWindow.setOnDismissListener(new OnDismissListener() {

			// 在dismiss中恢复透明度
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);

				// requestData(getData().get(clickPosition).get("info").toString());
			}
		});
	}

	protected void initPopuWindowRight(View parent) {
		if (popuWindow == null) {
			LayoutInflater mLayoutInflater = LayoutInflater.from(this);
			relativeLayout = (RelativeLayout) mLayoutInflater.inflate(
					R.layout.popuwindow_layuout, null);
			popuWindow = new PopupWindow(relativeLayout,
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			lv_left = (ListView) relativeLayout
					.findViewById(R.id.popuwindow_layuout_left_lv);
		}
		lv_left.setFocusable(true);
		lv_left.setItemsCanFocus(true);
		popUpWindowAdapterRight = new PopUpWindowAdapterRight();
		lv_left.setAdapter(popUpWindowAdapterRight);
		lv_left.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				if (position != clickRightPosition) {
					clickRightPosition = position;
					if (clickRightPosition == 0) {// 全部类型
						status = "(null)";
					} else {
						status = getRightData().get(position).get("info")
								.toString();
					}
					requestData(category, status, 1+"");
				} else {
					clickRightPosition = 0;
				}
				popUpWindowAdapterRight.notifyDataSetChanged();
				popuWindow.dismiss();

			}
		});

		ColorDrawable cd = new ColorDrawable(0x000000);
		popuWindow.setBackgroundDrawable(cd);
		// 产生背景变暗效果
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.7f;
		getWindow().setAttributes(lp);
		popuWindow.showAsDropDown(orderStateLines);
		popuWindow.setOutsideTouchable(true);
		popuWindow.setFocusable(true);
		popuWindow.showAtLocation((View) parent.getParent(), Gravity.TOP
				| Gravity.CENTER_HORIZONTAL, 0, 0);
		popuWindow.update();
		popuWindow.setOnDismissListener(new OnDismissListener() {

			// 在dismiss中恢复透明度
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);

				// requestData(getData().get(clickPosition).get("info").toString());
			}
		});
	}

	private List<Map<String, Object>> getLeftData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("info", "全部类型");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("info", "信托");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("info", "资管");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("info", "阳光私募");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("info", "私募股权");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("info", "保险");
		list.add(map);
		return list;

	}

	private List<Map<String, Object>> getRightData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("info", "全部状态");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("info", "待确认");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("info", "已确认");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("info", "已驳回");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("info", "已取消");
		list.add(map);
		return list;

	}

	class PopUpWindowAdapterLeft extends BaseAdapter {

		@Override
		public int getCount() {
			return getLeftData().size();
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
			View view = View.inflate(OrderActivity.this,
					R.layout.popupwindow_item, null);
			TextView passfile1 = (TextView) view
					.findViewById(R.id.popupwindow_layout_item_txt);
			passfile1.setText(getLeftData().get(position).get("info")
					.toString());
			if (position == clickLeftPosition) {
				passfile1.setTextColor(getResources().getColor(R.color.orange));
			}
			return view;
		}

	}

	class PopUpWindowAdapterRight extends BaseAdapter {

		@Override
		public int getCount() {
			return getRightData().size();
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
			View view = View.inflate(OrderActivity.this,
					R.layout.popupwindow_item, null);
			TextView passfile1 = (TextView) view
					.findViewById(R.id.popupwindow_layout_item_txt);
			passfile1.setText(getRightData().get(position).get("info")
					.toString());
			if (position == clickRightPosition) {
				passfile1.setTextColor(getResources().getColor(R.color.orange));
			}
			return view;
		}

	}

}
