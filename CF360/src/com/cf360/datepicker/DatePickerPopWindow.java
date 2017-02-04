package com.cf360.datepicker;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.cf360.R;

public class DatePickerPopWindow extends PopupWindow {
	private Context context;
	private String startTime;
	private Date date;
	private int curYear, curMonth;
	private LayoutInflater mInflater;
	private View dateView;
	private WheelView yearView;
	private WheelView monthView;
	private WheelView dayView;
	private WheelView hourView;
	private WheelView minView;
	private WheelView secView;
	private int[] timeInt;
	private OnConfirmListener listener;
	private Button btnConfirm, btnCancel;
	private String startDate;
	private String selectDate;
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			selectDate = (String) msg.obj;
		};
	};
	private boolean isFirst;

	public DatePickerPopWindow(Context context, String startTime,
			OnConfirmListener listener) {
		this.context = context;
		this.startTime = startTime;
		this.listener = listener;
		setStartTime();
		initWindow();
	}

	private void setStartTime() {
		timeInt = new int[6];
		timeInt[0] = Integer.valueOf(startTime.substring(0, 4));
		timeInt[1] = Integer.valueOf(startTime.substring(4, 6));
		timeInt[2] = Integer.valueOf(startTime.substring(6, 8));
		timeInt[3] = Integer.valueOf(startTime.substring(8, 10));
		timeInt[4] = Integer.valueOf(startTime.substring(10, 12));
		timeInt[5] = Integer.valueOf(startTime.substring(12, 14));
	}

	private void initWindow() {
		mInflater = LayoutInflater.from(context);
		dateView = mInflater.inflate(R.layout.wheel_date_picker, null);
		btnConfirm = (Button) dateView.findViewById(R.id.okBtn);
		btnCancel = (Button) dateView.findViewById(R.id.cancelBtn);
		yearView = (WheelView) dateView.findViewById(R.id.year);
		monthView = (WheelView) dateView.findViewById(R.id.month);
		dayView = (WheelView) dateView.findViewById(R.id.day);
		hourView = (WheelView) dateView.findViewById(R.id.time);
		minView = (WheelView) dateView.findViewById(R.id.min);
		secView = (WheelView) dateView.findViewById(R.id.sec);
		secView.setVisibility(View.GONE);
		startDate = new StringBuilder().append(timeInt[0])
				.append("-")
				.append((timeInt[1]) < 10 ? "0"
						+ (timeInt[1]) : (timeInt[1]))
				.append("-")
				.append((timeInt[2]) < 10 ? "0"
						+ (timeInt[2]) : (timeInt[2]))
				.append(" ")
				.append((timeInt[3]) < 10 ? "0"
						+ (timeInt[3]) : (timeInt[3]))
				.append(":")
				.append((timeInt[4]) < 10 ? "0"
						+ (timeInt[4]) : (timeInt[4])).toString();
		btnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isFirst) {
					listener.onConfirm(selectDate);
				} else {
					listener.onConfirm(startDate);
				}
				dismiss();
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		initWheel();
	}

	private void initWheel() {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		curYear = calendar.get(Calendar.YEAR);
		curMonth = calendar.get(Calendar.MONTH) + 1;

		NumericWheelAdapter numericWheelAdapter1 = new NumericWheelAdapter(
				context, curYear, curYear + 10);
		numericWheelAdapter1.setLabel("年");
		yearView.setViewAdapter(numericWheelAdapter1);
		yearView.setCyclic(true);
		yearView.addScrollingListener(scrollListener);

		NumericWheelAdapter numericWheelAdapter2 = new NumericWheelAdapter(
				context, 1, 12, "%02d");
		numericWheelAdapter2.setLabel("月");
		monthView.setViewAdapter(numericWheelAdapter2);
		monthView.setCyclic(true);
		monthView.addScrollingListener(scrollListener);

		NumericWheelAdapter numericWheelAdapter3 = new NumericWheelAdapter(
				context, 1, getDay(curYear, curMonth), "%02d");
		numericWheelAdapter3.setLabel("日");
		dayView.setViewAdapter(numericWheelAdapter3);
		dayView.setCyclic(true);
		dayView.addScrollingListener(scrollListener);

		NumericWheelAdapter numericWheelAdapter4 = new NumericWheelAdapter(
				context, 0, 23, "%02d");
		numericWheelAdapter4.setLabel("时");
		hourView.setViewAdapter(numericWheelAdapter4);
		hourView.setCyclic(true);
		hourView.addScrollingListener(scrollListener);

		NumericWheelAdapter numericWheelAdapter5 = new NumericWheelAdapter(
				context, 0, 59, "%02d");
		numericWheelAdapter5.setLabel("分");
		minView.setViewAdapter(numericWheelAdapter5);
		minView.setCyclic(true);
		minView.addScrollingListener(scrollListener);

		yearView.setCurrentItem(timeInt[0] - curYear);
		monthView.setCurrentItem(timeInt[1] - 1);
		dayView.setCurrentItem(timeInt[2] - 1);
		hourView.setCurrentItem(timeInt[3]);
		minView.setCurrentItem(timeInt[4]);
		yearView.setVisibleItems(7);// ������ʾ����
		monthView.setVisibleItems(7);
		dayView.setVisibleItems(7);
		hourView.setVisibleItems(7);
		minView.setVisibleItems(7);
		setContentView(dateView);
		setWidth(LayoutParams.FILL_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
		setBackgroundDrawable(dw);
		setFocusable(true);
	}

	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			isFirst = true;
			int n_year = yearView.getCurrentItem() + curYear;
			int n_month = monthView.getCurrentItem() + 1;

			initDay(n_year, n_month);

			String birthday = new StringBuilder()
					.append((yearView.getCurrentItem() + curYear))
					.append("-")
					.append((monthView.getCurrentItem() + 1) < 10 ? "0"
							+ (monthView.getCurrentItem() + 1) : (monthView
							.getCurrentItem() + 1))
					.append("-")
					.append(((dayView.getCurrentItem() + 1) < 10) ? "0"
							+ (dayView.getCurrentItem() + 1) : (dayView
							.getCurrentItem() + 1)).toString();
			String date = new StringBuilder()
					.append(((hourView.getCurrentItem()) < 10) ? "0"
							+ (hourView.getCurrentItem()) : (hourView
							.getCurrentItem()))
					.append(":")
					.append(((minView.getCurrentItem()) < 10) ? "0"
							+ (minView.getCurrentItem()) : (minView
							.getCurrentItem())).toString();
			birthday += " " + date;
			Message message = new Message();
			message.obj = birthday;
			handler.sendMessage(message);
			// listener.onConfirm(birthday);
			// Toast.makeText(context, birthday, Toast.LENGTH_SHORT).show();
		}
	};

	private void initDay(int arg1, int arg2) {
		NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(
				context, 1, getDay(arg1, arg2), "%02d");
		numericWheelAdapter.setLabel("日");
		dayView.setViewAdapter(numericWheelAdapter);
	}

	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}

	public interface OnConfirmListener {
		public void onConfirm(String date);
	}
}
