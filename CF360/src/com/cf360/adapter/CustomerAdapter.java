package com.cf360.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cf360.R;
import com.cf360.act.AddScheduleActivity;
import com.cf360.act.CustomerInfoActivity;
import com.cf360.bean.ResultCustomerModifyContentBean;
import com.cf360.bean.ResultMyCustomerContentBean;
import com.cf360.bean.ResultMyCustomerContentItemBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.AssortPinyinList;
import com.cf360.uitls.LanguageComparator_CN;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomerAdapter extends BaseExpandableListAdapter {

	// 字符串
//	private List<ResultMyCustomerContentItemBean> strList;

//	private AssortPinyinList assort = new AssortPinyinList();

	private Context context;
	MouldList<ResultMyCustomerContentBean> customerBean;
	private LayoutInflater inflater;
	private int index = 0;
	// 中文排序
	private LanguageComparator_CN cnSort = new LanguageComparator_CN();

	public CustomerAdapter(Context context,
			List<ResultMyCustomerContentItemBean> strList,MouldList<ResultMyCustomerContentBean> customerBean) {
		super();
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.customerBean = customerBean;
//		this.strList = strList;
		
		if (strList == null) {
			strList = new ArrayList<ResultMyCustomerContentItemBean>();
		}

		long time = System.currentTimeMillis();
		// 排序
//		sort();
//		Toast.makeText(context,
//				String.valueOf(System.currentTimeMillis() - time), 1).show();

	}

//	private void sort() {
//		// 分类
//		for (ResultMyCustomerContentItemBean str : strList) {
//			assort.getHashList().add(str);
//		}
//		assort.getHashList().sortKeyComparator(cnSort);
//
//		for (int i = 0, length = assort.getHashList().size(); i < length; i++) {
////			 Collections.sort((assort.getHashList().getValueListIndex(key)),
////			 cnSort);
//		}
//
//	}

	public Object getChild(int group, int child) {
//		return assort.getHashList().getValueIndex(group, child);
		return customerBean.get(group);
	}

	public long getChildId(int group, int child) {
		return child;
	}

	public View getChildView(final int group, final int child, boolean arg2,
			View contentView, ViewGroup arg4) {
		Holder holder = null;
		if (contentView == null) {
			holder = new Holder();
			contentView = inflater.inflate(R.layout.adapter_chat, null);
			holder.iv_customer_sex = (ImageView) contentView
					.findViewById(R.id.iv_customer_sex);
			holder.im_cuystomer_remark = (ImageView) contentView
					.findViewById(R.id.im_cuystomer_remark);
			holder.im_cuystomer_edit = (ImageView) contentView
					.findViewById(R.id.im_cuystomer_edit);
			holder.tv_customer_name = (TextView) contentView
					.findViewById(R.id.tv_customer_name);
			holder.tv_customer_nick_name = (TextView) contentView
					.findViewById(R.id.tv_customer_nick_name);
			holder.rl_customer_item = (RelativeLayout) contentView.findViewById(R.id.rl_customer_item);
			
			contentView.setTag(holder);
		} else {
			holder = (Holder) contentView.getTag();
		}

		// holder.iv_customer_sex.setText(assort.getHashList().getValueIndex(group,
		// child).getCreateTime());
		// holder.im_cuystomer_remark.setText(assort.getHashList().getValueIndex(group,
		// child).getName());
		 holder.im_cuystomer_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						context,
						AddScheduleActivity.class);
				intent.putExtra("name", customerBean.get(group).getResult().get(child).getNAME());
				intent.putExtra("customerId", customerBean.get(group).getResult().get(child).getID());
				context.startActivity(intent);
				
			}
		});
		
//		 String sex = assort.getHashList().getValueIndex(group, child).getSEX();
		 String sex = customerBean.get(group).getResult().get(child).getSEX();
		 
		if(!TextUtils.isEmpty(sex)){
			if("women".equals(sex)){
				 holder.iv_customer_sex.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_customer_girl));
			}else if("man".equals(sex)){
				holder.iv_customer_sex.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_customer_man));
			}else{
				holder.iv_customer_sex.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_customer_man));
			}
		}else{
			holder.iv_customer_sex.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_customer_man));
		}
		
		
		
//		String name1= assort.getHashList().getValueIndex(group, child).getNAME();
		String name1= customerBean.get(group).getResult().get(child).getNAME();
//		String turnnumber = assort.getHashList().getValueIndex(group, child)
//				.getTURNNUMBER();
		final String turnnumber = customerBean.get(group).getResult().get(child).getTURNNUMBER();
		holder.tv_customer_name.setText(name1
				+ "("
				+ turnnumber + ")");
		
//		String remark = assort.getHashList().getValueIndex(group, child)
//				.getREMARK();
		final String remark = customerBean.get(group).getResult().get(child)
				.getREMARK();
		if (!TextUtils.isEmpty(remark)) {
			holder.tv_customer_nick_name.setText(remark);
		}else{
			holder.tv_customer_nick_name.setText("");
		}

		holder.rl_customer_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i_customer = new Intent();
				i_customer.setClass(context, CustomerInfoActivity.class);
//				String id = assort.getHashList()
//						.getValueIndex(group, child).getID();
				String id = customerBean.get(group).getResult().get(child).getID();
				i_customer.putExtra("id", id);
				i_customer.putExtra("turnnumber", turnnumber);
				
				context.startActivity(i_customer);
			}
		});
		
		
		holder.im_cuystomer_remark.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context); // 先得到构造器
//				builder.setTitle("客户备注"); // 设置标题
				
				RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.activity_customer_beizhu, null);
				
				final EditText et_customer_beizhu = (EditText) view.findViewById(R.id.et_customer_beizhu);
				
				et_customer_beizhu.setText(remark);
				Button btn_customer_cancel = (Button) view.findViewById(R.id.btn_customer_cancel);
				Button btn_customer_sure = (Button) view.findViewById(R.id.btn_customer_sure);
				
				
				builder.setView(view);
				
				// 参数都设置完成了，创建并显示出来
				final AlertDialog myDialog = builder.create();
				myDialog.show();
				btn_customer_cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						myDialog.dismiss(); // 关闭dialog
						
					}
				});
				
				btn_customer_sure.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						myDialog.dismiss(); // 关闭dialog
						String newName = et_customer_beizhu.getText().toString();
//						Toast.makeText(context, newName,
//								Toast.LENGTH_SHORT).show();
						customerBean.get(group).getResult().get(child).setREMARK(newName);
						
						requestData(newName,customerBean.get(group).getResult().get(child)
								.getID());
						
					}
				});
//				Window window = myDialog.getWindow();
//				window.setGravity(Gravity.BOTTOM);
			}
		});
		return contentView;
	}

	public void requestData(String remark,String customerId) {
		HtmlRequest.getCustomerModifyBeizhu(context, remark,customerId,new OnRequestListener() {
			@Override
			public void onRequestFinished(BaseParams params) {
				
				ResultCustomerModifyContentBean data = (ResultCustomerModifyContentBean) params.result;
				if (data != null) {
					if(data.getFlag().equals("true")){
						CustomerAdapter.this.notifyDataSetChanged();
					}
					/*
					 * customerList.clear(); for(int i=0;i<data.size();i++){
					 * customerList.addAll(data.get(i).getResult()); }
					 * 
					 * initData();
					 */
					
				} else {
					
				}
				// setView();
			}
		});
	}

	class Holder {

		ImageView iv_customer_sex;
		ImageView im_cuystomer_remark;
		ImageView im_cuystomer_edit;
		TextView tv_customer_name;
		TextView tv_customer_nick_name;
		RelativeLayout rl_customer_item;

	}

	public int getChildrenCount(int group) {
//		return assort.getHashList().getValueListIndex(group).size();
		return customerBean.get(group).getResult().size();
	}

	public Object getGroup(int group) {
//		return assort.getHashList().getValueListIndex(group);
		return customerBean.get(group);
	}

	public int getGroupCount() {
//		return assort.getHashList().size();
		return customerBean.size();
	}

	public long getGroupId(int group) {
		return group;
	}

	public View getGroupView(int group, boolean arg1, View contentView,
			ViewGroup arg3) {
		index = group;
		if (contentView == null) {
			contentView = inflater.inflate(R.layout.list_group_item, null);
			contentView.setClickable(true);
		}
		TextView textView = (TextView) contentView.findViewById(R.id.name);
//		String firstName = assort.getFirstChar(assort.getHashList()
//				.getValueIndex(group, 0).getNAME();
		String firstName = customerBean.get(group).getShortName();
		textView.setText(firstName);
		// 禁止伸展

		return contentView;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

//	public AssortPinyinList getAssort() {
//		return assort;
//	}
	public int getIndex(String str){
		for (int i = 0; i < customerBean.size(); i++) {
			if(customerBean.get(i).getShortName().equals(str)){
				return i;
			}
		}
		return -1;
	}

}
