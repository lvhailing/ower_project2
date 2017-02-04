package com.cf360.act;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cf360.R;
import com.cf360.bean.ResultAddScheduleContentBean;
import com.cf360.bean.ResultCustomerInfoContentBean;
import com.cf360.bean.ResultScheduleDetailsContentBean;
import com.cf360.datepicker.DatePickerPopWindow;
import com.cf360.datepicker.DatePickerPopWindow.OnConfirmListener;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.DateUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 日程详情
 * 
 */
public class ScheduleDetailsActivity extends BaseActivity implements
		OnClickListener {
	private String scheduleId;
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
	private ActivityStack stack;
	protected String type_value;
	protected String status_value;
	private boolean isRemind = true;
	private String scheduleAmind = "on";
	protected ResultAddScheduleContentBean data;;
	private ResultScheduleDetailsContentBean bean;
	private ResultCustomerInfoContentBean deleteBean;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_schedule_details);
		initTopTitle();
		initView();
	}

	private void initView() {
		scheduleId = getIntent().getStringExtra("id");

		stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		deleteBean = new ResultCustomerInfoContentBean();
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

//		rl_customer_name.setOnClickListener(this);
		rl_schedule_type.setOnClickListener(this);
		rl_schedule_start_time.setOnClickListener(this);
		rl_schedule_end_time.setOnClickListener(this);
		rl_schedule_remind_time.setOnClickListener(this);
		rl_schedule_state.setOnClickListener(this);
		btn_remind.setOnClickListener(this);
		btn_save.setOnClickListener(this);
		requestData(scheduleId);
		
		
		btn_net_fail_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initView();
//				myEquityBaseAdapter.notifyDataSetChanged();
				netHint_2.setVisibility(netFail ? View.VISIBLE : View.GONE);
				llContent.setVisibility(netFail ? View.GONE : View.VISIBLE);
			}
		});
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(
				new Action(2, 0, R.color.blue_light),
				ScheduleDetailsActivity.this.getResources().getString(
						R.string.schedule_delete));
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_schedule_details))
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
						switch (id) {
						case 2:
							dialogDelete();
							break;

						default:
							break;
						}
					}
				});
	}
	private void dialogDelete() {
		// dialog参数设置
		AlertDialog.Builder builder = new AlertDialog.Builder(this); // 先得到构造器
		builder.setTitle("提示"); // 设置标题
		builder.setMessage("确定要删除该日程吗?"); // 设置内容
		// 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				requestDataDetele(scheduleId);
				// Toast.makeText(CustomerAddActivity.this, "确定",
				// Toast.LENGTH_SHORT).show();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// Toast.makeText(CustomerAddActivity.this, "取消",
				// Toast.LENGTH_SHORT).show();
			}
		});

		builder.create().show();
	}

	private void requestData(String scheduleId) {
		HtmlRequest.getScheduleDetails(ScheduleDetailsActivity.this,
				scheduleId, new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						if (params.result != null) {
							bean = (ResultScheduleDetailsContentBean) params.result;
							setData(bean);
						} else {
							netFail = true;
							Toast.makeText(ScheduleDetailsActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
						ScheduleDetailsActivity.this.stopLoading();

					}
				});
	}
	private void requestDataDetele(String scheduleId) {
		HtmlRequest.getScheduleDetailsDelete(ScheduleDetailsActivity.this,
				scheduleId, new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				
				if (params.result != null) {
					deleteBean = (ResultCustomerInfoContentBean) params.result;
//					setData(bean);
					if(deleteBean.getFlag().equals("true")){
						Toast.makeText(ScheduleDetailsActivity.this,
								deleteBean.getMessage(), Toast.LENGTH_LONG).show();
						finish();
					}else{
						Toast.makeText(ScheduleDetailsActivity.this,
								deleteBean.getMessage(), Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(ScheduleDetailsActivity.this,
							"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
				}
				ScheduleDetailsActivity.this.stopLoading();
				
			}
		});
	}

	protected void setData(ResultScheduleDetailsContentBean bean) {
		txt_customer_name.setText(bean.getUserCustomerSchedule()
				.getCustomerName());
		if (bean.getUserCustomerSchedule().getType().equals("1")) {
			txt_schedule_type.setText("上门");
		} else if (bean.getUserCustomerSchedule().getType().equals("2")) {
			txt_schedule_type.setText("电话");
		} else if (bean.getUserCustomerSchedule().getType().equals("3")) {
			txt_schedule_type.setText("来访接待");
		} else if (bean.getUserCustomerSchedule().getType().equals("4")) {
			txt_schedule_type.setText("会议");
		} else if (bean.getUserCustomerSchedule().getType().equals("5")) {
			txt_schedule_type.setText("培训");
		} else if (bean.getUserCustomerSchedule().getType().equals("6")) {
			txt_schedule_type.setText("商务餐饮");
		} else if (bean.getUserCustomerSchedule().getType().equals("7")) {
			txt_schedule_type.setText("外出活动");
		} else if (bean.getUserCustomerSchedule().getType().equals("8")) {
			txt_schedule_type.setText("其它");
		}
		status_value = bean.getUserCustomerSchedule().getStatus();
		type_value = bean.getUserCustomerSchedule().getType();
		edit_schedule_theme.setText(bean.getUserCustomerSchedule().getTopic());
		edit_schedule_describe.setText(bean.getUserCustomerSchedule()
				.getScheduleDesc());
		txt_schedule_start_time.setText(bean.getUserCustomerSchedule()
				.getStartTime());
		txt_schedule_end_time.setText(bean.getUserCustomerSchedule()
				.getEndTime());
		if (bean.getUserCustomerSchedule().getScheduleAmind().equals("on")) {
			setRemind(true);
			btn_remind.setImageResource(R.drawable.message2);
			scheduleAmind = "on";

		} else {
			setRemind(false);
			btn_remind.setImageResource(R.drawable.message1);
			scheduleAmind = "close";
		}
		txt_schedule_remind_time.setText(bean.getUserCustomerSchedule()
				.getAmindTime());
		if (bean.getUserCustomerSchedule().getStatus().equals("unfinish")) {
			txt_schedule_state.setText("未结束");
		} else if (bean.getUserCustomerSchedule().getStatus().equals("finish")) {
			txt_schedule_state.setText("已结束");
		} else if (bean.getUserCustomerSchedule().getStatus().equals("cancel")) {
			txt_schedule_state.setText("已取消");
		}
	}

	private void selectBuyDateStartTime() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		DatePickerPopWindow popWindow = new DatePickerPopWindow(
				ScheduleDetailsActivity.this, df.format(date),
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
				ScheduleDetailsActivity.this, df.format(date),
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
				ScheduleDetailsActivity.this, df.format(date),
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

	public boolean isRemind() {
		return isRemind;
	}

	public void setRemind(boolean isRemind) {
		this.isRemind = isRemind;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_add_shedule_name:
			Intent intent = new Intent(ScheduleDetailsActivity.this,
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
			String topic = edit_schedule_theme.getText().toString();
			String scheduleDesc = edit_schedule_describe.getText().toString();
			String startTime = txt_schedule_start_time.getText().toString();
			String endTime = txt_schedule_end_time.getText().toString();
			String amindTime = txt_schedule_remind_time.getText().toString();
			String status = status_value;
			if (TextUtils.isEmpty(topic)) {
				Toast.makeText(ScheduleDetailsActivity.this, "请输入日程主题",
						Toast.LENGTH_SHORT).show();
			} else {
				if (scheduleAmind.equals("on")) {
					if(TextUtils.isEmpty(amindTime)){
						Toast.makeText(
								ScheduleDetailsActivity.this,
								"提醒时间不能为空",
								Toast.LENGTH_SHORT)
								.show();
					}else{
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
										ScheduleDetailsActivity.this,
										"结束时间不能早于开始时间",
										Toast.LENGTH_SHORT)
										.show();
							} else {
								if (DateUtil.compare(
										dataRemindTime,
										dataStartTime) > 0) {
									Toast.makeText(
											ScheduleDetailsActivity.this,
											"开始时间不能早于提醒时间",
											Toast.LENGTH_SHORT)
											.show();
								} else {
									requestData(bean.getUserCustomerSchedule().getId(),customerName, bean.getUserCustomerSchedule().getCustomerId(), type, topic, status, endTime,
											startTime, scheduleAmind, amindTime, scheduleDesc);
								}
							}

						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

					
				}else{
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
									ScheduleDetailsActivity.this,
									"结束时间不能早于开始时间",
									Toast.LENGTH_SHORT)
									.show();
						} else {
							requestData(bean.getUserCustomerSchedule().getId(),customerName, bean.getUserCustomerSchedule().getCustomerId(), type, topic, status, endTime,
									startTime, scheduleAmind, amindTime, scheduleDesc);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			break;
		default:
			break;
		}

	}

	private void requestData(final String id,String customerName, String customerId,
			String type, String topic, String status, String endTime,
			String startTime, String scheduleAmind, String amindTime,
			String scheduleDesc) {
		HtmlRequest.getEditSchedule(ScheduleDetailsActivity.this,id,customerName,
				customerId, type, topic, status, endTime, startTime,
				scheduleAmind, amindTime, scheduleDesc,
				new OnRequestListener() {

					@Override
					public void onRequestFinished(BaseParams params) {
						if (params.result != null) {
							data = (ResultAddScheduleContentBean) params.result;
							if (data.isFlag() == true) {
								Toast.makeText(ScheduleDetailsActivity.this,
										data.getMessage(), Toast.LENGTH_LONG)
										.show();
								finish();
							} else {

							}
						} else {
							Toast.makeText(ScheduleDetailsActivity.this,
									"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
						}
					}
				});
	}

}
