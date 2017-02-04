package com.cf360.act;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cf360.ApplicationConsts;
import com.cf360.R;
import com.cf360.adapter.FinancialCardAdapter;
import com.cf360.bean.CityBean;
import com.cf360.bean.ResultFinancialAuditContentBean;
import com.cf360.bean.ResultFinancialCardBean;
import com.cf360.bean.ResultFinancialCardContentBean;
import com.cf360.bean.ResultFinancialToUserAuditContentBean;
import com.cf360.http.AsyncHttpClient;
import com.cf360.http.AsyncHttpResponseHandler;
import com.cf360.http.RequestParams;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.uitls.StringUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.OnActionListener;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

/**
 * 理财师认证
 * 
 * @author hasee
 * 
 */
public class FinancialActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout rl_financial_financial_card, rl_financial_city;
	private Context context;
	// private TextView tv_financial_city;
	private ListView listview, listview1;
	private FinancialCardAdapter financialCardAdapter, myAdapter2;
	private PopupWindow citypopupWindow;
	private String aa, bb;
	public static final String ENCODING = "UTF-8";
	private CityBean ar;
	private ArrayList<CityBean> list;
	private ArrayList<CityBean> firstname;
	private ArrayList<CityBean> secondname;
	private EditText et_financial_realName, et_financial_workaddress,
			et_financial_email;
	private TextView tv_financial_city;
	private Button btn_financial;
	private ResultFinancialToUserAuditContentBean infoBean;
	private ResultFinancialCardContentBean bean;
	private String regionaProvince = null;
	private String regionaCity = null;
	private ImageView iv_financial_card_value;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	/**
	 * 表示选择的是相机--1
	 */
	private static int CAMERA_REQUEST_CODE = 1;
	/**
	 * 表示选择的是相册--2
	 */
	private static int GALLERY_REQUEST_CODE = 2;
	/**
	 * 表示选择的是裁剪--3
	 */
	private static int CROP_REQUEST_CODE = 3;
	/**
	 * 图片保存SD卡位置
	 */
	private final static String IMG_PATH = Environment
			.getExternalStorageDirectory() + "/cf360/imgs/";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_financial);
		context = this;
		initTopTitle();
		initView();
		initData();
	}

	private void initData() {
		requestFinancialToUserAudit();

	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		infoBean = new ResultFinancialToUserAuditContentBean();
		firstname = new ArrayList<CityBean>();
		list = new ArrayList<CityBean>();
		secondname = new ArrayList<CityBean>();
		bean = new ResultFinancialCardContentBean();
		rl_financial_financial_card = (RelativeLayout) findViewById(R.id.rl_financial_financial_card);
		rl_financial_city = (RelativeLayout) findViewById(R.id.rl_financial_city);
		et_financial_realName = (EditText) findViewById(R.id.et_financial_realName);
		tv_financial_city = (TextView) findViewById(R.id.tv_financial_city);
		et_financial_workaddress = (EditText) findViewById(R.id.et_financial_workaddress);
		et_financial_email = (EditText) findViewById(R.id.et_financial_email);
		btn_financial = (Button) findViewById(R.id.btn_financial);
		iv_financial_card_value = (ImageView) findViewById(R.id.iv_financial_card_value);
		et_financial_email
				.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

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
						getResources().getString(R.string.title_financial))
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
		imageLoader.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		imageLoader.pause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		imageLoader.stop();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_financial_financial_card:
			setPhoto();
			break;
		case R.id.rl_financial_city:
			try {
				initPopWindowForCitys();
				citypopupWindow.setAnimationStyle(R.style.PopupAnimation);
				citypopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);
				citypopupWindow.update();

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.btn_financial:
			if (TextUtils.isEmpty(et_financial_realName.getText().toString())) {
				Toast.makeText(context, "真实姓名不能为空", Toast.LENGTH_LONG).show();
			} else {
				if (TextUtils.isEmpty(tv_financial_city.getText().toString())) {
					Toast.makeText(context, "所在地不能为空", Toast.LENGTH_LONG)
							.show();
				} else {
					if (TextUtils.isEmpty(et_financial_workaddress.getText()
							.toString())) {
						Toast.makeText(context, "工作单位不能为空", Toast.LENGTH_LONG)
								.show();
					} else {
						if (TextUtils.isEmpty(et_financial_email.getText()
								.toString())) {
							Toast.makeText(context, "电子邮箱不能为空",
									Toast.LENGTH_LONG).show();
						} else if (!StringUtil.isEmail(et_financial_email
								.getText().toString())) {
							Toast.makeText(context, "电子邮箱格式不正确",
									Toast.LENGTH_LONG).show();
						} else {
							dialog();
						}
					}
				}
			}

			break;
		default:
			break;
		}

	}

	public void dialog() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage("确定要进行理财师认证审核吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				requestFinancialAudit();
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

	// 理财师认证
	public void requestFinancialAudit() {

		String mobile = null;
		try {
			mobile = DESUtil.decrypt(PreferenceUtil.getPhone());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String realName = et_financial_realName.getText().toString();
		// String regionaProvince = "北京市";
		// String regionaCity = "北京市";
		String companyName = et_financial_workaddress.getText().toString();
		String email = et_financial_email.getText().toString();
		// String businessCardName = "abc.jpg";
		String businessCardName = null;
		if (TextUtils.isEmpty(bean.getTmpFileName())) {
			businessCardName = infoBean.getBusinessCardName();
		} else {
			businessCardName = bean.getTmpFileName();
		}

		HtmlRequest.getFinancialAudit(context, mobile, realName,
				regionaProvince, regionaCity, companyName, email,
				businessCardName, new OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						ResultFinancialAuditContentBean financialbean = (ResultFinancialAuditContentBean) params.result;
						if (financialbean != null) {
							if (Boolean.parseBoolean(financialbean.getFlag())) {

								Toast.makeText(context,
										financialbean.getMessage(),
										Toast.LENGTH_LONG).show();
								Intent i_myperson = new Intent();
								i_myperson.setClass(context,
										MyPersonActivity.class);
								// startActivity(i_myperson);
								setResult(0, i_myperson);
								finish();
							} else {
								Toast.makeText(context,
										financialbean.getMessage(),
										Toast.LENGTH_LONG).show();
							}

						} else {

						}

					}
				});
	}

	// 理财师认证状态显示判断
	public void requestFinancialToUserAudit() {

		HtmlRequest.getFinancialToUserAudit(context, new OnRequestListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onRequestFinished(BaseParams params) {

				ResultFinancialToUserAuditContentBean bean = (ResultFinancialToUserAuditContentBean) params.result;
				if (bean != null) {
					infoBean = bean;
					if (bean.getAuditStatus().equals("noAudit")) {

						rl_financial_financial_card
								.setOnClickListener(FinancialActivity.this);
						rl_financial_city
								.setOnClickListener(FinancialActivity.this);
						btn_financial
								.setOnClickListener(FinancialActivity.this);

					} else if (bean.getAuditStatus().equals("unAudit")) {
						rl_financial_financial_card
								.setOnClickListener(FinancialActivity.this);
						rl_financial_financial_card.setClickable(false);
						rl_financial_city
								.setOnClickListener(FinancialActivity.this);
						rl_financial_city.setClickable(false);
						btn_financial
								.setOnClickListener(FinancialActivity.this);
						btn_financial.setClickable(false);
						btn_financial.setText("认证中");
						btn_financial
								.setBackgroundResource(R.drawable.shape_button_gray_d);
						setView();
					} else if (bean.getAuditStatus().equals("fail")) {
						rl_financial_financial_card
								.setOnClickListener(FinancialActivity.this);
						rl_financial_city
								.setOnClickListener(FinancialActivity.this);
						btn_financial
								.setOnClickListener(FinancialActivity.this);
						setView();
					} else if (bean.getAuditStatus().equals("success")) {
						rl_financial_financial_card
								.setOnClickListener(FinancialActivity.this);
						rl_financial_city
								.setOnClickListener(FinancialActivity.this);
						btn_financial
								.setOnClickListener(FinancialActivity.this);
						setView();
					}

				} else {

				}

			}
		});
	}

	protected void setView() {

		et_financial_realName.setText(infoBean.getUser().getRealName());
		tv_financial_city.setText(infoBean.getUser().getRegionaProvince() + " "
				+ infoBean.getUser().getRegionaCity());
		et_financial_workaddress.setText(infoBean.getUser().getCompanyName());
		et_financial_email.setText(infoBean.getUser().getEmail());
		imageLoader.displayImage(infoBean.getBusineseCardUrl(),
				iv_financial_card_value);

	}

	private void initPopWindowForCitys() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View layout = inflater.inflate(R.layout.wheelpopupforcity, null);
		listview = (ListView) layout.findViewById(R.id.list);
		listview1 = (ListView) layout.findViewById(R.id.list1);
		layout.getBackground().setAlpha(130);
		layout.invalidate();

		DisplayMetrics dm = new DisplayMetrics();
		FinancialActivity.this.getWindowManager().getDefaultDisplay()
				.getMetrics(dm);

		int iWidth = dm.widthPixels;
		int iHeight = dm.heightPixels;

		citypopupWindow = new PopupWindow(layout, iWidth * 5 / 6, iHeight / 2);

		// citypopupWindow = new PopupWindow(layout,
		// LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		// citypopupWindow.showAsDropDown(btn_financial);

		citypopupWindow.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0, 0);
		// Button btn_city_canle = (Button)
		// layout.findViewById(R.id.pickcitycancle);
		// Button pickcityconfirm = (Button)
		// layout.findViewById(R.id.pickcityconfirm);
		// btn_city_canle.getPaint().setFakeBoldText(true);
		// btn_city_canle.setOnClickListener(new OnClickListener() {
		// public void onClick(View v) {
		// citypopupWindow.dismiss();
		// citypopupWindow.setFocusable(false);
		// }
		// });
		// pickcityconfirm.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		// String cc = aa+bb;
		// citypopupWindow.dismiss();
		// citypopupWindow.setFocusable(false);
		// // Toast.makeText(MainActivity.this, cc, Toast.LENGTH_LONG).show();
		// }
		// });
		WindowManager.LayoutParams params = FinancialActivity.this.getWindow()
				.getAttributes();
		params.alpha = 0.7f;

		FinancialActivity.this.getWindow().setAttributes(params);

		layout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (citypopupWindow.isShowing()) {
					citypopupWindow.dismiss();
					WindowManager.LayoutParams params = FinancialActivity.this
							.getWindow().getAttributes();
					params.alpha = 1f;
					FinancialActivity.this.getWindow().setAttributes(params);
				}
				return false;
			}
		});
		String str1 = getFromAssets("leibie.json");
		JSONObject js;
		try {
			js = new JSONObject(str1);
			String areaBeans = js.getString("areaBeans");
			JSONArray areaBean = new JSONArray(areaBeans);
			list.clear();
			for (int i = 0; i < areaBean.length(); i++) {
				ar = new CityBean();
				JSONObject json = (JSONObject) areaBean.get(i);
				ar.setAreaid(json.optString("areaid"));
				ar.setName(json.optString("name"));
				ar.setPinyin(json.optString("pinyin"));
				ar.setShortpinyin(json.optString("shortpinyin"));
				ar.setType(json.optString("type"));
				ar.setParentId(json.optString("parentId"));
				list.add(i, ar);
			}
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getType().equals("s")) {
					firstname.add(list.get(i));
				}
			}

			citypopupWindow.setFocusable(true);
			financialCardAdapter = new FinancialCardAdapter(context, firstname);
			myAdapter2 = new FinancialCardAdapter(context, secondname);
			listview.setAdapter(financialCardAdapter);
			listview1.setAdapter(myAdapter2);

			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					secondname.clear();
					for (int i = 0; i < list.size(); i++) {
						String id = firstname.get(arg2).getAreaid();
						aa = firstname.get(arg2).getName();
						if (list.get(i).getType().equals("c")) {
							if (list.get(i).getParentId().equals(id)) {
								secondname.add(list.get(i));

							}
						}
					}

					myAdapter2.notifyDataSetChanged();
				}
			});

			listview1
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							bb = secondname.get(arg2).getName();
							String cc = aa + bb;
							citypopupWindow.dismiss();
							citypopupWindow.setFocusable(false);

							// Toast.makeText(context, cc,
							// Toast.LENGTH_LONG).show();
							tv_financial_city.setText(aa + " " + bb);
							regionaProvince = aa;
							regionaCity = bb;
							WindowManager.LayoutParams params = FinancialActivity.this
									.getWindow().getAttributes();
							params.alpha = 1f;
							FinancialActivity.this.getWindow().setAttributes(
									params);
						}
					});

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// 从assets 文件夹中获取文件并读取数据
	public String getFromAssets(String fileName) {
		String result = "";
		try {
			InputStream in = getResources().getAssets().open(fileName);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, ENCODING);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private Bitmap newZoomImage;

	private void setPhoto() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("选择图片");
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.setPositiveButton("相机", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(intent, CAMERA_REQUEST_CODE);
			}
		});
		builder.setNeutralButton("相册", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, GALLERY_REQUEST_CODE);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}

	// 根据用户选择，返回图片资源
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == CAMERA_REQUEST_CODE) {
			if (data == null) {
				return;
			} else {
				Bundle extras = data.getExtras();
				Uri path = data.getData();
				if (extras != null) {
					Bitmap bm = extras.getParcelable("data");
					Uri uri = saveBitmap(bm);
					startImageZoom(uri);
				}
			}
		} else if (requestCode == GALLERY_REQUEST_CODE) {
			if (data == null) {
				return;
			}
			Uri uri;
			uri = data.getData();
			Uri fileUri = convertUri(uri);
			startImageZoom(fileUri);
		} else if (requestCode == CROP_REQUEST_CODE) {
			if (data == null) {
				return;
			}
			Bundle extras = data.getExtras();
			if (extras == null) {
				return;
			}
			Bitmap bm = extras.getParcelable("data");
			newZoomImage = zoomImage(bm, 90, 90);
			sendImage(newZoomImage);
		}

	}

	private void startImageZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 110);
		intent.putExtra("outputY", 110);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_REQUEST_CODE);
	}

	private Uri convertUri(Uri uri) {
		InputStream is = null;
		try {
			is = context.getContentResolver().openInputStream(uri);
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			Bitmap zoomImage = zoomImage(bitmap, 500, 500);
			is.close();
			return saveBitmap(zoomImage);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
			double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}

	private void sendImage(final Bitmap bm) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] bytes = stream.toByteArray();
		String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));

		try {
			// String id = DESUtil.decrypt(PreferenceUtil.getUserId());

			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			// String img = GetImageStr(IMG_PATH+"abc.jpg");
			params.add("uploadFile", img);
			params.add("fileName", "abc.jpg");
			// params.add("id", id);
			String url = ApplicationConsts.URL_FINANCIALCART;
			client.post(url, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String content) {
					super.onSuccess(statusCode, headers, content);
					try {
						String result = DESUtil.decrypt(content);
						Gson gson = new Gson();
						ResultFinancialCardBean data = new ResultFinancialCardBean();
						data = gson.fromJson(result,
								ResultFinancialCardBean.class);
						bean = data.getData();
						// imageLoader.displayImage(ApplicationConsts.EC_HOST+bean.getTmpFilePath(),
						// iv_financial_card_value);
						iv_financial_card_value.setImageBitmap(bm);
						// System.out.println(result);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// requestPhotoData();
					// mypersonal_img.setImageBitmap(bm);
					// mthread = new Thread(myRunnable);
					// mthread.start();
					// System.out.println("success cart finalcaial");

				}

				@Override
				public void onFailure(Throwable error, String content) {
					super.onFailure(error, content);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Uri saveBitmap(Bitmap bm) {
		File tmpDir = new File(IMG_PATH);
		if (!tmpDir.exists()) {
			tmpDir.mkdirs();
		}
		File img = new File(IMG_PATH + "Cart.png");
		try {
			FileOutputStream fos = new FileOutputStream(img);
			bm.compress(Bitmap.CompressFormat.PNG, 70, fos);
			fos.flush();
			fos.close();
			return Uri.fromFile(img);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}
