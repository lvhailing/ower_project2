package com.cf360.act;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.bean.ResultAddScheduleContentBean;
import com.cf360.datepicker.DatePickerPopWindow;
import com.cf360.datepicker.DatePickerPopWindow.OnConfirmListener;
import com.cf360.mould.BaseParams;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.HtmlRequest;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.DateUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;

/**
 * 新建日程
 * 
 */
public class AddScheduleActivity extends BaseActivity implements
		OnClickListener {
	private RelativeLayout rl_customer_name, rl_schedule_type,
			rl_schedule_start_time, rl_schedule_end_time,
			rl_schedule_remind_time, rl_schedule_state;
	private TextView txt_customer_name, txt_schedule_type,
			txt_schedule_start_time, txt_schedule_end_time,
			txt_schedule_remind_time, txt_schedule_state;
	private EditText edit_schedule_theme, edit_schedule_describe;
	private ImageButton btn_remind;
	private Button btn_save;
	private int requestCode = 5;
	private String startTime, endTime, remindTime;
	protected ResultAddScheduleContentBean data;
	private ActivityStack stack;
	protected String type_value;
	protected String status_value;
	private String customerId;
	private boolean isRemind = true;
	private String scheduleAmind = "on";
	private View mTimeView2 = null;
	private String name;
	private ImageView img_name;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_add_schedule);
		initTopTitle();
		initView();
	}

	private void initView() {
		name = getIntent().getStringExtra("name");
		customerId = getIntent().getStringExtra("customerId");

		stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		rl_customer_name = (RelativeLayout) findViewById(R.id.rl_add_shedule_name);
		rl_schedule_type = (RelativeLayout) findViewById(R.id.rl_add_shedule_type);
		rl_schedule_start_time = (RelativeLayout) findViewById(R.id.rl_add_shedule_starttime);
		rl_schedule_end_time = (RelativeLayout) findViewById(R.id.rl_add_shedule_endtime);
		rl_schedule_remind_time = (RelativeLayout) findViewById(R.id.rl_add_shedule_remind_time);
		rl_schedule_state = (RelativeLayout) findViewById(R.id.rl_add_schedule_state);

		txt_customer_name = (TextView) findViewById(R.id.tv_add_shedule_name_value);
		txt_schedule_type = (TextView) findViewById(R.id.tv_add_shedule_type_value);
		txt_schedule_start_time = (TextView) findViewById(R.id.tv_add_shedule_starttime_value);
		txt_schedule_end_time = (TextView) findViewById(R.id.tv_add_shedule_endtime_value);
		txt_schedule_remind_time = (TextView) findViewById(R.id.tv_add_shedule_remind_time_value);
		txt_schedule_state = (TextView) findViewById(R.id.tv_add_schedule_state_value);

		edit_schedule_theme = (EditText) findViewById(R.id.add_shedule_theme_value);
		edit_schedule_describe = (EditText) findViewById(R.id.et_add_shedule_describe_value);
		btn_remind = (ImageButton) findViewById(R.id.imageButton_remind);
		btn_save = (Button) findViewById(R.id.btn_add_schedule_save);
		img_name = (ImageView) findViewById(R.id.iv_add_shedule_name_arrow);

		if (TextUtils.isEmpty(name)) {
			rl_customer_name.setOnClickListener(this);
		} else {
			txt_customer_name.setText(name);
			img_name.setVisibility(View.INVISIBLE);

		}
		rl_schedule_type.setOnClickListener(this);
		rl_schedule_start_time.setOnClickListener(this);
		rl_schedule_end_time.setOnClickListener(this);
		rl_schedule_remind_time.setOnClickListener(this);
		rl_schedule_state.setOnClickListener(this);
		btn_remind.setOnClickListener(this);
		btn_save.setOnClickListener(this);

		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_add_schedule))
				.setLogo(R.drawable.img_logo, false).setIndicator(R.drawable.back)
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

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_add_shedule_name:
			Intent intent = new Intent(AddScheduleActivity.this,
					SelectCustomerActivity.class);
			startActivityForResult(intent, requestCode);
			break;
		case R.id.rl_add_shedule_type:
			dialog();
			break;
		case R.id.rl_add_shedule_starttime:
			selectBuyDateStartTime();
			break;
		case R.id.rl_add_shedule_endtime:
			selectBuyDateEndTime();
			break;
		case R.id.rl_add_shedule_remind_time:
			selectBuyDateRemindTime();
			break;
		case R.id.rl_add_schedule_state:
			dialog1();
			break;
		case R.id.imageButton_remind:
			if (isRemind) {
				setRemind(false);
				btn_remind.setImageResource(R.drawable.message1);
				scheduleAmind = "close";
			} else {
				setRemind(true);
				btn_remind.setImageResource(R.drawable.message2);
				scheduleAmind = "on";
			}
			break;
		case R.id.btn_add_schedule_save:
			String customerName = txt_customer_name.getText().toString();
			String type = type_value;
			String type_value = txt_schedule_type.getText().toString();
			String topic = edit_schedule_theme.getText().toString();
			String scheduleDesc = edit_schedule_describe.getText().toString();
			String startTime = txt_schedule_start_time.getText().toString();
			String endTime = txt_schedule_end_time.getText().toString();
			String amindTime = txt_schedule_remind_time.getText().toString();
			String status = status_value;
			if (TextUtils.isEmpty(customerName)) {
				Toast.makeText(AddScheduleActivity.this, "客户姓名不能为空",
						Toast.LENGTH_SHORT).show();
			} else {
				if (TextUtils.isEmpty(type_value)) {
					Toast.makeText(AddScheduleActivity.this, "日程类型不能为空",
							Toast.LENGTH_SHORT).show();
				} else {
					if (TextUtils.isEmpty(topic)) {
						Toast.makeText(AddScheduleActivity.this, "请输入日程主题",
								Toast.LENGTH_SHORT).show();
					} else {
						if (TextUtils.isEmpty(startTime)) {
							Toast.makeText(AddScheduleActivity.this,
									"开始时间不能为空", Toast.LENGTH_SHORT).show();
						} else {
							if (TextUtils.isEmpty(endTime)) {
								Toast.makeText(AddScheduleActivity.this,
										"结束时间不能为空", Toast.LENGTH_SHORT).show();
							} else {
								if (scheduleAmind.equals("on")) {
									if (TextUtils.isEmpty(amindTime)) {
										Toast.makeText(
												AddScheduleActivity.this,
												"提醒时间不能为空", Toast.LENGTH_SHORT)
												.show();
									} else {
										if (TextUtils.isEmpty(status)) {
											Toast.makeText(
													AddScheduleActivity.this,
													"日程状态不能为空",
													Toast.LENGTH_SHORT).show();
										} else {

											SimpleDateFormat format = new SimpleDateFormat(
													"yyyy-MM-dd HH:mm");
											try {
												Date dataStartTime = format
														.parse(startTime);
												Date dataEndTime = format
														.parse(endTime);
												Date dataRemindTime = format
														.parse(amindTime);
												if (DateUtil.compare(
														dataStartTime,
														dataEndTime) > 0) {
													Toast.makeText(
															AddScheduleActivity.this,
															"结束时间不能早于开始时间",
															Toast.LENGTH_SHORT)
															.show();
												} else {
													if (DateUtil.compare(
															dataRemindTime,
															dataStartTime) > 0) {
														Toast.makeText(
																AddScheduleActivity.this,
																"开始时间不能早于提醒时间",
																Toast.LENGTH_SHORT)
																.show();
													} else {
														requestData(
																customerName,
																customerId,
																type, topic,
																status,
																endTime,
																startTime,
																scheduleAmind,
																amindTime,
																scheduleDesc);

													}
												}

											} catch (ParseException e) {
												e.printStackTrace();
											}
										}
									}
								} else {
									if (TextUtils.isEmpty(status)) {
										Toast.makeText(
												AddScheduleActivity.this,
												"日程状态不能为空", Toast.LENGTH_SHORT)
												.show();
									} else {
										SimpleDateFormat format = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm");
										try {
											Date dataStartTime = format
													.parse(startTime);
											Date dataEndTime = format
													.parse(endTime);
											if (DateUtil.compare(
													dataStartTime,
													dataEndTime) > 0) {
												Toast.makeText(
														AddScheduleActivity.this,
														"结束时间不能早于开始时间",
														Toast.LENGTH_SHORT)
														.show();
											} else {
												requestData(customerName, customerId,
														type, topic, status, endTime,
														startTime, scheduleAmind,
														amindTime, scheduleDesc);
											}
										} catch (ParseException e) {
											e.printStackTrace();
										}
									}
								}
							}
						}
					}
				}
			}
			break;
		default:
			break;
		}

	}

	private void selectBuyDateStartTime() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		DatePickerPopWindow popWindow = new DatePickerPopWindow(
				AddScheduleActivity.this, df.format(date),
				new OnConfirmListener() {

					@Override
					public void onConfirm(String date) {
						txt_schedule_start_time.setText(date);
					}
				});
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.5f;
		getWindow().setAttributes(lp);
		popWindow.showAtLocation(findViewById(R.id.root), Gravity.CENTER, 0, 0);
		popWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
	}

	private void selectBuyDateEndTime() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		DatePickerPopWindow popWindow = new DatePickerPopWindow(
				AddScheduleActivity.this, df.format(date),
				new OnConfirmListener() {

					@Override
					public void onConfirm(String date) {
						txt_schedule_end_time.setText(date);
					}
				});
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.5f;
		getWindow().setAttributes(lp);
		popWindow.showAtLocation(findViewById(R.id.root), Gravity.CENTER, 0, 0);
		popWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
	}

	private void selectBuyDateRemindTime() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		DatePickerPopWindow popWindow = new DatePickerPopWindow(
				AddScheduleActivity.this, df.format(date),
				new OnConfirmListener() {

					@Override
					public void onConfirm(String date) {
						txt_schedule_remind_time.setText(date);
					}
				});
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.5f;
		getWindow().setAttributes(lp);
		popWindow.showAtLocation(findViewById(R.id.root), Gravity.CENTER, 0, 0);
		popWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
	}

	private void dialog() {
		final String items[] = { "上门", "电话", "来访接待", "会议", "培训", "商务餐饮",
				"外出活动", "其它" };
		// dialog参数设置
		AlertDialog.Builder builder = new AlertDialog.Builder(this); // 先得到构造器
		builder.setTitle("日程类型"); // 设置标题
		// builder.setMessage("是否确认退出?"); //设置内容
		// 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				txt_schedule_type.setText(items[which]);
				if (items[which].equals("上门")) {
					type_value = "1";
				} else if (items[which].equals("电话")) {
					type_value = "2";
				} else if (items[which].equals("来访接待")) {
					type_value = "3";
				} else if (items[which].equals("会议")) {
					type_value = "4";
				} else if (items[which].equals("培训")) {
					type_value = "5";
				} else if (items[which].equals("商务餐饮")) {
					type_value = "6";
				} else if (items[which].equals("外出活动")) {
					type_value = "7";
				} else if (items[which].equals("其它")) {
					type_value = "8";
				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	private void dialog1() {
		final String items[] = { "未结束", "已结束", "已取消" };
		// dialog参数设置
		AlertDialog.Builder builder = new AlertDialog.Builder(this); // 先得到构造器
		builder.setTitle("日程状态"); // 设置标题
		// builder.setMessage("是否确认退出?"); //设置内容
		// 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				txt_schedule_state.setText(items[which]);
				if (items[which].equals("未结束")) {
					status_value = "unfinish";
				} else if (items[which].equals("已结束")) {
					status_value = "finish";
				} else if (items[which].equals("已取消")) {
					status_value = "cancel";
				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	// 新建日程
	private void requestData(final String customerName, String customerId,
			String type, String topic, String status, String endTime,
			String startTime, String scheduleAmind, String amindTime,
			String scheduleDesc) {
		HtmlRequest.getAddSchedule(AddScheduleActivity.this, customerName,
				customerId, type, topic, status, endTime, startTime,
				scheduleAmind, amindTime, scheduleDesc,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							data = (ResultAddScheduleContentBean) params.result;
							if (data.isFlag() == true) {
								Toast.makeText(AddScheduleActivity.this,
										data.getMessage(), Toast.LENGTH_LONG)
										.show();
								if (TextUtils.isEmpty(name)) {
									Intent intent = new Intent(
											AddScheduleActivity.this,
											CustomerFollowActivity.class);
									startActivity(intent);
									stack.removeAllActivity();
								} else {
									finish();
								}
							}
						} else {
							Toast.makeText(AddScheduleActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			String name = data.getExtras().getString("customer_name");
			customerId = data.getExtras().getString("id");
			txt_customer_name.setText(name);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public boolean isRemind() {
		return isRemind;
	}

	public void setRemind(boolean isRemind) {
		this.isRemind = isRemind;
	}
}
