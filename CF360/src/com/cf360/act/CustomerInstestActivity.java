package com.cf360.act;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.bean.ResultCustomerInfoContentBean;
import com.cf360.dialog.CustomerInstestSportDialog;
import com.cf360.dialog.CustomerInstestSportDialog.OnSureListener;
import com.cf360.mould.BaseParams;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.HtmlRequest;
import com.cf360.uitls.ActivityStack;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;

public class CustomerInstestActivity extends BaseActivity implements
		OnClickListener {

	private String id = null;
	private String interestSport = null; // 体育爱好 复选以“，“分隔
											// 1:篮球;2:足球;3:网球;4:赛车;5:游泳;6:健身;7:滑雪;8:羽毛球;9:乒乓球;10:保龄球;11:高尔夫;12:水上运动;13:其他
	private String interestArt = null; // 艺术爱好
										// 复选以“，“分隔1:戏曲;2:歌剧;3:话剧;4:电影;5:文学;6:书法绘画;7:古典音乐;8:流行音乐;9:其他
	private String interestArder = null; // 休闲爱好
											// 复选以“，“分隔1:棋牌;2:登山;3:旅游;4:摄影;5:茗茶;6:骑马;7:钓鱼;8:园艺;9:古董收藏;10:博物展览;11:其他

	private String sportItems[] = { "篮球", "足球", "网球", "赛车", "游泳", "健身", "滑雪",
			"羽毛球", "乒乓球", "保龄球", "高尔夫", "水上运动", "其他" };
	private String artItems[] = { "戏曲", "歌剧", "话剧", "电影", "文学", "书法绘画", "古典音乐",
			"流行音乐", "其他" };
	private String ArderItems[] = { "棋牌", "登山", "旅游", "摄影", "茗茶", "骑马", "钓鱼",
			"园艺", "古董收藏", "博物展览", "其他" };

	private boolean selected[];
//	private boolean selected_flag_sport[],selected_flag_art[],selected_flag_arder[];

	private boolean sportSelected[] = { false, false, false, false, false,
			false, false, false, false, false, false, false, false };
	private boolean artSelected[] = { false, false, false, false, false, false,
			false, false, false };
	private boolean arderSelected[] = { false, false, false, false, false,
			false, false, false, false, false, false };
	
	
	private RelativeLayout rl_instest_customer_interestsport,
			rl_instest_customer_interestArt, rl_instest_customer_interestarder;
	private TextView tv_instest_customer_interestsport_value,
			tv_instest_customer_interestArt_value,
			tv_instest_customer_interestArder_value;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_myperson_customer_instest);
		initTopTitle();
		initView();

	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		id = getIntent().getExtras().getString("id");
		interestSport = getIntent().getExtras().getString("interestSport");
		interestArt = getIntent().getExtras().getString("interestArt");
		interestArder = getIntent().getExtras().getString("interestArder");

		rl_instest_customer_interestsport = (RelativeLayout) findViewById(R.id.rl_instest_customer_interestsport);
		rl_instest_customer_interestArt = (RelativeLayout) findViewById(R.id.rl_instest_customer_interestArt);
		rl_instest_customer_interestarder = (RelativeLayout) findViewById(R.id.rl_instest_customer_interestarder);

		tv_instest_customer_interestsport_value = (TextView) findViewById(R.id.tv_instest_customer_interestsport_value);
		tv_instest_customer_interestArt_value = (TextView) findViewById(R.id.tv_instest_customer_interestArt_value);
		tv_instest_customer_interestArder_value = (TextView) findViewById(R.id.tv_instest_customer_interestArder_value);

		rl_instest_customer_interestsport.setOnClickListener(this);
		rl_instest_customer_interestArt.setOnClickListener(this);
		rl_instest_customer_interestarder.setOnClickListener(this);

		setView();
		initDiglog();
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void initDiglog() {
		for(int i = 0;i<sportSelected.length;i++){
			sportSelected[i]  =false;
		}
		if (!(TextUtils.isEmpty(interestSport) || interestSport.equals("null"))) {
			String[] strings = interestSport.split(",");
			for (String string : strings) {
				sportSelected[Integer.parseInt(string) - 1] = true;
			}
		}
		for(int i = 0;i<artSelected.length;i++){
			artSelected[i]  =false;
		}
		if (!(TextUtils.isEmpty(interestArt) || interestArt.equals("null"))) {
			String[] artStrings = interestArt.split(",");
			for (String string : artStrings) {
				artSelected[Integer.parseInt(string) - 1] = true;
			}
		}
		for(int i = 0;i<arderSelected.length;i++){
			arderSelected[i]  =false;
		}
		if (!(TextUtils.isEmpty(interestArder) || interestArder.equals("null"))) {
			String[] arderStrings = interestArder.split(",");
			for (String string : arderStrings) {
				arderSelected[Integer.parseInt(string) - 1] = true;
			}
		}
		

	}

	private void setView() {
		StringBuilder interestSports = new StringBuilder();
		if (!(TextUtils.isEmpty(interestSport) || interestSport.equals("null"))) {
			String[] strings = interestSport.split(",");
			for (String string : strings) {
				interestSports.append(NumToSport(string));
				interestSports.append(",");
			}
			interestSports.deleteCharAt(interestSports.length() - 1);
			tv_instest_customer_interestsport_value.setText(interestSports);
		} else {
			tv_instest_customer_interestsport_value.setText("");
		}

		StringBuilder interestArts = new StringBuilder();
		if (!(TextUtils.isEmpty(interestArt) || interestArt.equals("null"))) {
			String[] artStrings = interestArt.split(",");
			for (String string : artStrings) {
				interestArts.append(NumToArt(string));
				interestArts.append(",");
			}
			interestArts.deleteCharAt(interestArts.length() - 1);
			tv_instest_customer_interestArt_value.setText(interestArts);
		} else {
			tv_instest_customer_interestArt_value.setText("");
		}

		StringBuilder interestArders = new StringBuilder();
		if (!(TextUtils.isEmpty(interestArder) || interestArder.equals("null"))) {
			String[] arderStrings = interestArder.split(",");

			for (String string : arderStrings) {
				interestArders.append(NumToArder(string));
				interestArders.append(",");
			}
			interestArders.deleteCharAt(interestArders.length() - 1);
			tv_instest_customer_interestArder_value.setText(interestArders);
		} else {
			tv_instest_customer_interestArder_value.setText("");
		}
//		selected_flag_sport = sportSelected;
//		selected_flag_art = artSelected;
//		selected_flag_arder = arderSelected;
		
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(
				new Action(2, 0, R.color.blue_light),
				CustomerInstestActivity.this.getResources().getString(
						R.string.customer_save_newcustomer));
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(
								R.string.title_mycustomer_instest))
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
							// dialog();
							requestCustomerInstestSaveData();

							break;

						default:
							break;
						}
					}
				});
	}

	/**
	 * 
	 * 保存客户兴趣爱好
	 * 
	 */

	public void requestCustomerInstestSaveData() {

		String operation = "edit";
		String newInterestSport = tv_instest_customer_interestsport_value
				.getText().toString();

		String[] sportStrings = newInterestSport.split(",");
		StringBuilder sportInterest = new StringBuilder();
		for (String string : sportStrings) {
			sportInterest.append(SportToNum(string));
			sportInterest.append(",");
		}
		sportInterest.deleteCharAt(sportInterest.length() - 1);
		newInterestSport = sportInterest.toString();

		String newInterestArt = tv_instest_customer_interestArt_value.getText()
				.toString();
		String[] artStrings = newInterestArt.split(",");
		StringBuilder artInterest = new StringBuilder();
		for (String string : artStrings) {
			artInterest.append(ArtToNum(string));
			artInterest.append(",");
		}
		artInterest.deleteCharAt(artInterest.length() - 1);
		newInterestArt = artInterest.toString();

		String newInterestArder = tv_instest_customer_interestArder_value
				.getText().toString();
		String[] arderStrings = newInterestArder.split(",");
		StringBuilder arderInterest = new StringBuilder();
		for (String string : arderStrings) {
			arderInterest.append(ArderToNum(string));
			arderInterest.append(",");
		}
		arderInterest.deleteCharAt(arderInterest.length() - 1);
		newInterestArder = arderInterest.toString();

		HtmlRequest.getCustomerInstestSave(CustomerInstestActivity.this, id,
				operation, newInterestSport, newInterestArt, newInterestArder,
				new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						ResultCustomerInfoContentBean data = (ResultCustomerInfoContentBean) params.result;

						if (data != null) {
							// infoBean = data.getUserCustomer();

							if (data.getFlag().equals("true")) {
								Toast.makeText(CustomerInstestActivity.this,
										"保存成功", Toast.LENGTH_SHORT).show();
								finish();
							}

						} else {

						}

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
		case R.id.rl_instest_customer_interestsport:
			// dialog("选择体育类",sportItems,sportSelected);
			selected = sportSelected;
			initDiglog();
//			selected_flag = sportSelected;
			dialog1("选择体育类", sportItems);
			break;
		case R.id.rl_instest_customer_interestArt:
			selected = artSelected;
			initDiglog();
//			selected_flag = artSelected;
			dialog1("选择文艺类", artItems);
			break;
		case R.id.rl_instest_customer_interestarder:
			selected = arderSelected;
			initDiglog();
//			selected_flag = arderSelected;
			dialog1("选择休闲类", ArderItems);
			break;
		default:
			break;
		}

	}

	private void dialog1(String title, final String items[]) {
		CustomerInstestSportDialog dialog1 = null;
		// AlertDialog.Builder builder=new AlertDialog.Builder(this); //先得到构造器
		// builder.setTitle(title); //设置标题
		boolean newSelected[] = selected;;
		if(dialog1!=null){
			dialog1=null;
		}
		dialog1 = new CustomerInstestSportDialog(
				CustomerInstestActivity.this, items, newSelected, title,
				new OnSureListener() {

					@Override
					public void onConfim(boolean[] ischeck) {
						selected = ischeck;

						for (int i = 0; i < selected.length; i++) {
							if (items.length == 13) {
								sportSelected = selected;
								StringBuilder sb = new StringBuilder();
								for (int j = 0; j < sportSelected.length; j++) {
									if (sportSelected[j]) {
										sb.append(j + 1);
										sb.append(",");
									}
								}
								if (sb.length() > 1) {
									sb.deleteCharAt(sb.length() - 1);
								}
								interestSport = sb.toString();

							} else if (items.length == 9) {
								artSelected = selected;

								StringBuilder sb = new StringBuilder();
								for (int j = 0; j < artSelected.length; j++) {
									if (artSelected[j]) {
										sb.append(j + 1);
										sb.append(",");
									}
								}
								if (sb.length() > 1) {
									sb.deleteCharAt(sb.length() - 1);
								}
								interestArt = sb.toString();

							} else if (items.length == 11) {
								arderSelected = selected;

								StringBuilder sb = new StringBuilder();
								for (int j = 0; j < arderSelected.length; j++) {
									if (arderSelected[j]) {
										sb.append(j + 1);
										sb.append(",");
									}
								}
								if (sb.length() > 1) {
									sb.deleteCharAt(sb.length() - 1);
								}
								interestArder = sb.toString();

							}
//							Log.e("hongliang", "" + selected[i]);
						}
						setView();
					}
				});
		
		dialog1.show();
		dialog1.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
//				selected = dialog1.selected;
//				System.out.println("111111111111111111111111111111");
//				for (int j = 0; j < sportSelected.length; j++) {
//					System.out.print(sportSelected[j]+"===");
//				}
//				if(items.length==13){
//					sportSelected = selected_flag_sport;
//				}else if(items.length == 9){
//					artSelected = selected_flag_art;
//				}else if(items.length == 11){
//					arderSelected = selected_flag_arder;
//				}
			}
		});

		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		android.view.WindowManager.LayoutParams p = dialog1.getWindow()
				.getAttributes(); // 获取对话框当前的参数值
		// p.height = (int) (d.getHeight() * 0.3); //高度设置为屏幕的0.3
		p.width = (int) (d.getWidth()); // 宽度设置为全屏
		dialog1.getWindow().setAttributes(p); // 设置生效
	}


	/**
	 * 数字转化为对应的运动 体育爱好 复选以“，“分隔
	 * 1:篮球;2:足球;3:网球;4:赛车;5:游泳;6:健身;7:滑雪;8:羽毛球;9:乒乓球;10
	 * :保龄球;11:高尔夫;12:水上运动;13:其他
	 * 
	 * @param num
	 * @return
	 */

	public String NumToSport(String num) {
		String name = "";
		if (num.equals("1")) {
			name = "篮球";
		} else if (num.equals("2")) {
			name = "足球";
		} else if (num.equals("3")) {
			name = "网球";
		} else if (num.equals("4")) {
			name = "赛车";
		} else if (num.equals("5")) {
			name = "游泳";
		} else if (num.equals("6")) {
			name = "健身";
		} else if (num.equals("7")) {
			name = "滑雪";
		} else if (num.equals("8")) {
			name = "羽毛球";
		} else if (num.equals("9")) {
			name = "乒乓球";
		} else if (num.equals("10")) {
			name = "保龄球";
		} else if (num.equals("11")) {
			name = "高尔夫";
		} else if (num.equals("12")) {
			name = "水上运动";
		} else if (num.equals("13")) {
			name = "其他";
		}
		return name;
	}

	/**
	 * 
	 * 数字转化为对应的艺术 艺术爱好
	 * 复选以“，“分隔1:戏曲;2:歌剧;3:话剧;4:电影;5:文学;6:书法绘画;7:古典音乐;8:流行音乐;9:其他
	 * 
	 * @param num
	 * @return
	 */
	public String NumToArt(String num) {
		String name = "";
		if (num.equals("1")) {
			name = "戏曲";
		} else if (num.equals("2")) {
			name = "歌剧";
		} else if (num.equals("3")) {
			name = "话剧";
		} else if (num.equals("4")) {
			name = "电影";
		} else if (num.equals("5")) {
			name = "文学";
		} else if (num.equals("6")) {
			name = "书法绘画";
		} else if (num.equals("7")) {
			name = "古典音乐";
		} else if (num.equals("8")) {
			name = "流行音乐";
		} else if (num.equals("9")) {
			name = "其他";
		}
		return name;
	}

	/**
	 * 数字转化对应的休闲爱好 休闲爱好
	 * 复选以“，“分隔1:棋牌;2:登山;3:旅游;4:摄影;5:茗茶;6:骑马;7:钓鱼;8:园艺;9:古董收藏;10:博物展览;11:其他
	 * 
	 * @param num
	 * @return
	 */

	public String NumToArder(String num) {
		String name = "";
		if (num.equals("1")) {
			name = "棋牌";
		} else if (num.equals("2")) {
			name = "登山";
		} else if (num.equals("3")) {
			name = "旅游";
		} else if (num.equals("4")) {
			name = "摄影";
		} else if (num.equals("5")) {
			name = "茗茶";
		} else if (num.equals("6")) {
			name = "骑马";
		} else if (num.equals("7")) {
			name = "钓鱼";
		} else if (num.equals("8")) {
			name = "园艺";
		} else if (num.equals("9")) {
			name = "古董收藏";
		} else if (num.equals("10")) {
			name = "博物展览";
		} else if (num.equals("11")) {
			name = "其他";
		}
		return name;
	}

	/**
	 * 运动转化为对应的数字 体育爱好 复选以“，“分隔
	 * 1:篮球;2:足球;3:网球;4:赛车;5:游泳;6:健身;7:滑雪;8:羽毛球;9:乒乓球;10
	 * :保龄球;11:高尔夫;12:水上运动;13:其他
	 * 
	 * @param name
	 * @return
	 */
	public String SportToNum(String name) {
		String num = "";
		if (name.equals("篮球")) {
			num = "1";
		} else if (name.equals("足球")) {
			num = "2";
		} else if (name.equals("网球")) {
			num = "3";
		} else if (name.equals("赛车")) {
			num = "4";
		} else if (name.equals("游泳")) {
			num = "5";
		} else if (name.equals("健身")) {
			num = "6";
		} else if (name.equals("滑雪")) {
			num = "7";
		} else if (name.equals("羽毛球")) {
			num = "8";
		} else if (name.equals("乒乓球")) {
			num = "9";
		} else if (name.equals("保龄球")) {
			num = "10";
		} else if (name.equals("高尔夫")) {
			num = "11";
		} else if (name.equals("水上运动")) {
			num = "12";
		} else if (name.equals("其他")) {
			num = "13";
		}
		return num;
	}

	/**
	 * 艺术转为对应的数字 艺术爱好 复选以“，“分隔1:戏曲;2:歌剧;3:话剧;4:电影;5:文学;6:书法绘画;7:古典音乐;8:流行音乐;9:其他
	 * 
	 * @param name
	 * @return
	 */
	public String ArtToNum(String name) {

		String num = "";
		if (name.equals("戏曲")) {
			num = "1";
		} else if (name.equals("歌剧")) {
			num = "2";
		} else if (name.equals("话剧")) {
			num = "3";
		} else if (name.equals("电影")) {
			num = "4";
		} else if (name.equals("文学")) {
			num = "5";
		} else if (name.equals("书法绘画")) {
			num = "6";
		} else if (name.equals("古典音乐")) {
			num = "7";
		} else if (name.equals("流行音乐")) {
			num = "8";
		} else if (name.equals("其他")) {
			num = "9";
		}
		return num;
	}

	/**
	 * 休闲运动转化为对应的数字 休闲爱好
	 * 复选以“，“分隔1:棋牌;2:登山;3:旅游;4:摄影;5:茗茶;6:骑马;7:钓鱼;8:园艺;9:古董收藏;10:博物展览;11:其他
	 * 
	 * @param name
	 * @return
	 */

	public String ArderToNum(String name) {
		String num = "";
		if (name.equals("棋牌")) {
			num = "1";
		} else if (name.equals("登山")) {
			num = "2";
		} else if (name.equals("旅游")) {
			num = "3";
		} else if (name.equals("摄影")) {
			num = "4";
		} else if (name.equals("茗茶")) {
			num = "5";
		} else if (name.equals("骑马")) {
			num = "6";
		} else if (name.equals("钓鱼")) {
			num = "7";
		} else if (name.equals("园艺")) {
			num = "8";
		} else if (name.equals("古董收藏")) {
			num = "9";
		} else if (name.equals("博物展览")) {
			num = "10";
		} else if (name.equals("其他")) {
			num = "11";
		}
		return num;
	}
}
