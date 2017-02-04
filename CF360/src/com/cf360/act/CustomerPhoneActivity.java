package com.cf360.act;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cf360.R;
import com.cf360.adapter.FinancialCardAdapter;
import com.cf360.bean.CityBean;
import com.cf360.bean.ResultCustomerInfoContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.ActivityStack;
import com.cf360.uitls.StringUtil;
import com.cf360.view.TitleBar;
import com.cf360.view.TitleBar.Action;
import com.cf360.view.TitleBar.OnActionListener;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerPhoneActivity extends BaseActivity implements OnClickListener{
	
	private RelativeLayout rl_phone_customer_birthaddress,rl_phone_customer_liveaddress;
	private TextView tv_phone_customer_birthaddress_value,tv_phone_customer_liveaddress_value;
	private EditText et_phone_customer_home_phone_value,et_phone_customer_mobile_phone_value,et_phone_customer_prephone_value,et_phone_customer_qq_value,et_phone_customer_fax_value,et_phone_customer_email_value;
	
	private String id= null;
	private String homePhone= null;
	private String mobilePhone= null;
	private String prePhone= null;
	private String qqNumber= null;
	private String fax= null;
	private String email= null;
	private String birthProvince= null;
	private String birthCity= null;
	private String liveProvince= null;
	private String liveCity= null;
	public static final String ENCODING = "UTF-8";
	
	private PopupWindow citypopupWindow;
	private ListView listview,listview1;
	private ArrayList<CityBean> list;
	private ArrayList<CityBean> firstname;
    private ArrayList<CityBean> secondname;
    private CityBean ar;
    private String aa,bb;
    private FinancialCardAdapter financialCardAdapter,myAdapter2;
    private String regionaProvince = null;
	private String regionaCity = null;
    
	
	private String operation;
	private String newHomePhone;
	private String newMobilePhone;
	private String newPrePhone;
	private String newQqNumber;
	private String newFax;
	private String newEmail;
	private String newBirthProvince;
	private String newBirthCity;
	private String newLiveProvince;
	private String newLiveCity;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_myperson_customer_phone);
		initTopTitle();
		initView();
	}
	
	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		firstname = new ArrayList<CityBean>();
		list = new ArrayList<CityBean>();
		secondname = new ArrayList<CityBean>();
		
		id = getIntent().getExtras().getString("id");
		homePhone = getIntent().getExtras().getString("homePhone");
		mobilePhone = getIntent().getExtras().getString("mobilePhone");
		prePhone = getIntent().getExtras().getString("prePhone");
		qqNumber = getIntent().getExtras().getString("qqNumber");
		fax = getIntent().getExtras().getString("fax");
		email = getIntent().getExtras().getString("email");
		birthProvince = getIntent().getExtras().getString("birthProvince");
		birthCity = getIntent().getExtras().getString("birthCity");
		liveProvince = getIntent().getExtras().getString("liveProvince");
		liveCity = getIntent().getExtras().getString("liveCity");
		
		
		
		rl_phone_customer_birthaddress = (RelativeLayout) findViewById(R.id.rl_phone_customer_birthaddress);
		rl_phone_customer_liveaddress = (RelativeLayout) findViewById(R.id.rl_phone_customer_liveaddress);
		
		tv_phone_customer_birthaddress_value = (TextView) findViewById(R.id.tv_phone_customer_birthaddress_value);
		tv_phone_customer_liveaddress_value = (TextView) findViewById(R.id.tv_phone_customer_liveaddress_value);
		
		et_phone_customer_home_phone_value = (EditText) findViewById(R.id.et_phone_customer_home_phone_value);
		et_phone_customer_mobile_phone_value = (EditText) findViewById(R.id.et_phone_customer_mobile_phone_value);
		et_phone_customer_prephone_value = (EditText) findViewById(R.id.et_phone_customer_prephone_value);
		et_phone_customer_qq_value = (EditText) findViewById(R.id.et_phone_customer_qq_value);
		et_phone_customer_fax_value = (EditText) findViewById(R.id.et_phone_customer_fax_value);
		et_phone_customer_email_value = (EditText) findViewById(R.id.et_phone_customer_email_value);
		
		
		
		rl_phone_customer_birthaddress.setOnClickListener(this);
		rl_phone_customer_liveaddress.setOnClickListener(this);
		
		setView();
		
		if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;
	}

	private void setView() {
		if(!(TextUtils.isEmpty(birthCity)&&TextUtils.isEmpty(birthProvince))){
			tv_phone_customer_birthaddress_value.setText(birthProvince+birthCity);
		}else{
			tv_phone_customer_birthaddress_value.setText("");
		}
		if(!(TextUtils.isEmpty(liveProvince)&&TextUtils.isEmpty(liveCity))){
			tv_phone_customer_liveaddress_value.setText(liveProvince+liveCity);
		}else{
			tv_phone_customer_liveaddress_value.setText("");
		}
		
		et_phone_customer_home_phone_value.setText(homePhone);
		et_phone_customer_mobile_phone_value.setText(mobilePhone);
		et_phone_customer_prephone_value.setText(prePhone);
		et_phone_customer_qq_value.setText(qqNumber);
		et_phone_customer_fax_value.setText(fax);
		et_phone_customer_email_value.setText(email);
		
	}

	private void initTopTitle() {
		TitleBar title = (TitleBar) findViewById(R.id.rl_title);
		title.showLeftImg(true);
		title.addAction(new Action(2, 0, R.color.blue_light),CustomerPhoneActivity.this.getResources().getString(R.string.customer_save_newcustomer));
		title.setTitle(getResources().getString(R.string.title_no))
				.setCenterText(
						getResources().getString(R.string.title_mycustomer_phone))
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
							operation = "edit";
							newHomePhone = et_phone_customer_home_phone_value.getText().toString();
							newMobilePhone = et_phone_customer_mobile_phone_value.getText().toString();
							newPrePhone = et_phone_customer_prephone_value.getText().toString();
							newQqNumber = et_phone_customer_qq_value.getText().toString();
							newFax = et_phone_customer_fax_value.getText().toString();
							newEmail = et_phone_customer_email_value.getText().toString();
							newBirthProvince = birthProvince;
							newBirthCity = birthCity;
							newLiveProvince = liveProvince;
							newLiveCity = liveCity;
							if(TextUtils.isEmpty(newMobilePhone)){
								Toast.makeText(CustomerPhoneActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
							}else {
								if(!StringUtil.isMobileNO(newMobilePhone)){
									Toast.makeText(CustomerPhoneActivity.this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
								}else if((!TextUtils.isEmpty(newEmail))&&(!StringUtil.isEmail(newEmail))){
									Toast.makeText(CustomerPhoneActivity.this, "电子邮箱格式不正确", Toast.LENGTH_SHORT).show();
								}else{
									requestCustomerPhoneSaveData();
								}
							}
							
							break;

						default:
							break;
						}
					}
				});
	}
	
	/**
	 * 
	 * 保存客户基本信息
	 * 
	 */
	
	public void requestCustomerPhoneSaveData() {
		
		
		
		HtmlRequest.getCustomerPhoneSave(CustomerPhoneActivity.this,id,operation,newHomePhone,newMobilePhone,newPrePhone,newQqNumber,newFax,newEmail,newBirthProvince,newBirthCity,newLiveProvince,newLiveCity,
				new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				
				ResultCustomerInfoContentBean data = (ResultCustomerInfoContentBean) params.result;
				
				if (data != null) {
//					infoBean = data.getUserCustomer();
					
					if(data.getFlag().equals("true")){
						Toast.makeText(CustomerPhoneActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
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
		case R.id.rl_phone_customer_birthaddress:
			try {
				initPopWindowForCitys(tv_phone_customer_birthaddress_value);
				citypopupWindow.setAnimationStyle(R.style.PopupAnimation);
				citypopupWindow.showAtLocation(v, Gravity.NO_GRAVITY,0, 0);
				citypopupWindow.update();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.rl_phone_customer_liveaddress:
			try {
				initPopWindowForCitys(tv_phone_customer_liveaddress_value);
				citypopupWindow.setAnimationStyle(R.style.PopupAnimation);
				citypopupWindow.showAtLocation(v, Gravity.NO_GRAVITY,0, 0);
				citypopupWindow.update();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
	private void initPopWindowForCitys(final TextView tv_phone_customer_value) {
		LayoutInflater inflater = LayoutInflater.from(CustomerPhoneActivity.this);
		View layout = inflater.inflate(R.layout.wheelpopupforcity, null);
		 listview = (ListView)layout.findViewById(R.id.list);
	     listview1  = (ListView)layout.findViewById(R.id.list1);
		layout.getBackground().setAlpha(130);
		layout.invalidate();
		
		DisplayMetrics dm = new DisplayMetrics();
        CustomerPhoneActivity.this.getWindowManager().getDefaultDisplay().getMetrics(dm);
 
        int iWidth  = dm.widthPixels;
        int iHeight = dm.heightPixels;
		
		citypopupWindow = new PopupWindow(layout, iWidth*5/6,iHeight/2);
		
//		citypopupWindow = new PopupWindow(layout, LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//		citypopupWindow.showAsDropDown(btn_financial);
		
		citypopupWindow.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0, 0);
//		Button btn_city_canle = (Button) layout.findViewById(R.id.pickcitycancle);
//		Button pickcityconfirm = (Button) layout.findViewById(R.id.pickcityconfirm);
//		btn_city_canle.getPaint().setFakeBoldText(true);
//		btn_city_canle.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				citypopupWindow.dismiss();
//				citypopupWindow.setFocusable(false);
//			}
//		});
//		pickcityconfirm.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				String cc = aa+bb;
//				citypopupWindow.dismiss();
//				citypopupWindow.setFocusable(false);
////				Toast.makeText(MainActivity.this, cc, Toast.LENGTH_LONG).show();
//			}
//		});
		WindowManager.LayoutParams params=CustomerPhoneActivity.this.getWindow().getAttributes();
        params.alpha=0.7f;
        
        CustomerPhoneActivity.this.getWindow().setAttributes(params);
        
		layout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (citypopupWindow.isShowing())
                {
					citypopupWindow.dismiss();
					WindowManager.LayoutParams params=CustomerPhoneActivity.this.getWindow().getAttributes();  
				    params.alpha=1f;  
				    CustomerPhoneActivity.this.getWindow().setAttributes(params); 
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
				JSONObject json = (JSONObject)areaBean.get(i);
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
			financialCardAdapter = new FinancialCardAdapter(CustomerPhoneActivity.this,firstname);
			myAdapter2 = new FinancialCardAdapter(CustomerPhoneActivity.this, secondname);
			listview.setAdapter(financialCardAdapter);
			listview1.setAdapter(myAdapter2);
			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
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
			
			listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					bb = secondname.get(arg2).getName();
					String cc = aa+bb;
					citypopupWindow.dismiss();
					citypopupWindow.setFocusable(false);
					
					//Toast.makeText(context, cc, Toast.LENGTH_LONG).show();
					
//					birthProvince = getIntent().getExtras().getString("birthProvince");
//					birthCity = getIntent().getExtras().getString("birthCity");
//					liveProvince = getIntent().getExtras().getString("liveProvince");
//					liveCity = getIntent().getExtras().getString("liveCity");
					
					if(tv_phone_customer_value==tv_phone_customer_birthaddress_value){
//						tv_phone_customer_birthaddress_value.setText(aa+" "+bb);
						birthProvince = aa;
						birthCity = bb ;
					}else if(tv_phone_customer_value==tv_phone_customer_liveaddress_value){
//						tv_phone_customer_liveaddress_value.setText(aa+" "+bb);
						liveProvince = aa;
						liveCity = bb;
					}
					tv_phone_customer_value.setText(aa+" "+bb);
					regionaProvince = aa;
					regionaCity =bb;
					WindowManager.LayoutParams params=CustomerPhoneActivity.this.getWindow().getAttributes();
				    params.alpha=1f;
				    CustomerPhoneActivity.this.getWindow().setAttributes(params);
				    
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
	
	
}
