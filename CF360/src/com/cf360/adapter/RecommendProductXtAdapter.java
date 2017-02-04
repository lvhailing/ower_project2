package com.cf360.adapter;

import com.cf360.R;
import com.cf360.bean.ResultRecommendProductContentBean;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.MyProgressBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecommendProductXtAdapter extends BaseAdapter {

	private static final String tag = "recommendProductAdapter";
	private MouldList<ResultRecommendProductContentBean> list;
	private Context context;
	private LayoutInflater inflater;
	public RecommendProductXtAdapter(Context context,
			MouldList<ResultRecommendProductContentBean> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
//		Log.e(tag, "list=="+list.size());
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return getItem(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		Log.e(tag, "getView=="+list.get(position).getAMOUNT());
		Holder holder= null;
		if(convertView==null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.activity_recommend_product_item, null);
			holder.tv_recommendproduct_title_content = (TextView) convertView.findViewById(R.id.tv_recommendproduct_title_content);
			holder.iv_hot_product_creditlevel_text = (TextView) convertView.findViewById(R.id.iv_hot_product_creditlevel_text);
			
			holder.rl_product_saletype = (RelativeLayout) convertView.findViewById(R.id.rl_product_saletype);
			holder.rl_product_hottype = (RelativeLayout) convertView.findViewById(R.id.rl_product_hottype);
			holder.rl_product_recommendtype = (RelativeLayout) convertView.findViewById(R.id.rl_product_recommendtype);
			
			holder.tv_product_saletype = (TextView) convertView.findViewById(R.id.tv_product_saletype);
			holder.tv_product_hottype = (TextView) convertView.findViewById(R.id.tv_product_hottype);
			holder.tv_product_recommendtype = (TextView) convertView.findViewById(R.id.tv_product_recommendtype);
			
			holder.iv_recommend_product_creditlevel = (ImageView) convertView.findViewById(R.id.iv_recommend_product_creditlevel);
			
			holder.tv_prodeuct_recommend_content_first_one = (TextView) convertView.findViewById(R.id.tv_prodeuct_recommend_content_first_one);
			holder.tv_prodeuct_recommend_content_Second_one = (TextView) convertView.findViewById(R.id.tv_prodeuct_recommend_content_Second_one);
			holder.tv_product_recommend_content_Third_one = (TextView) convertView.findViewById(R.id.tv_product_recommend_content_Third_one);
			holder.mypb_recommendproduct_ProgressBar = (MyProgressBar) convertView.findViewById(R.id.mypb_recommendproduct_ProgressBar);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		
		holder.tv_recommendproduct_title_content.setText(list.get(position).getNAME());
		
		if(TextUtils.isEmpty(list.get(position).getSELLINGSTATUS())){
			holder.rl_product_hottype.setVisibility(View.GONE);
		}else{
			if(list.get(position).getSELLINGSTATUS().equals("热销")){
				holder.rl_product_hottype.setVisibility(View.VISIBLE);
			}else {
				holder.rl_product_hottype.setVisibility(View.GONE);
			}
		}
		
		if(TextUtils.isEmpty(list.get(position).getRECOMMENDSTATUS())){
			holder.rl_product_recommendtype.setVisibility(View.GONE);
		}else{
			if(list.get(position).getRECOMMENDSTATUS().equals("0")){
				holder.rl_product_recommendtype.setVisibility(View.GONE);
				
			}else if(list.get(position).getRECOMMENDSTATUS().equals("1")){
				
				holder.rl_product_recommendtype.setVisibility(View.VISIBLE);
			}
		}
		
		if(TextUtils.isEmpty(list.get(position).getSALETYPE())){
			
		}else{
			holder.rl_product_saletype.setVisibility(View.VISIBLE);
			if(list.get(position).getSALETYPE().equals("包销")){
				holder.tv_product_saletype.setText("包销");
			}else if(list.get(position).getSALETYPE().equals("分销")){
				holder.tv_product_saletype.setText("分销");
			}else{
				holder.rl_product_saletype.setVisibility(View.GONE);
			}
			
		}
//		holder.recommendproduct_type_recommend_text.setText(list.get(position).getSALETYPE());
		
		LayoutParams para;
		para = holder.iv_recommend_product_creditlevel.getLayoutParams();
		
		if(!TextUtils.isEmpty(list.get(position).getCREDITLEVEL())){
			holder.iv_recommend_product_creditlevel.setVisibility(View.VISIBLE);
			if(list.get(position).getCREDITLEVEL().equals("11")){
				holder.iv_hot_product_creditlevel_text.setVisibility(View.VISIBLE);
//				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_d);
				holder.iv_recommend_product_creditlevel.setVisibility(View.GONE);
//				para.width = 120;
//				para.height = 40;
			}else if(list.get(position).getCREDITLEVEL().equals("1")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_no);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("2")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_aaa);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("3")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_aa);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("4")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_a);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("5")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_bbb);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("6")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_bb);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("7")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_b);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("8")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_ccc);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("9")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_cc);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("10")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_c);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else{
				holder.iv_recommend_product_creditlevel.setVisibility(View.GONE);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
			}
//			holder.iv_recommend_product_creditlevel.setLayoutParams(para);
		}else{
			holder.iv_recommend_product_creditlevel.setVisibility(View.GONE);
		}
		
		String progress = list.get(position).getRECRUITMENTPROCESS();
		int progressInt = (int)(Double.parseDouble(progress)*100);
		holder.mypb_recommendproduct_ProgressBar.setProgress(progressInt);
		holder.mypb_recommendproduct_ProgressBar.initText(15);
		
		holder.tv_prodeuct_recommend_content_first_one.setText(list.get(position).getAMOUNT());
		holder.tv_prodeuct_recommend_content_Second_one.setText(list.get(position).getTIMELIMIT());
//		if(list.get(position).getTIMELIMITUNIT().equals("year")){
//			holder.tv_prodeuct_recommend_content_Second_one.setText(list.get(position).getTIMELIMIT()+"年");
//		}else if(list.get(position).getTIMELIMITUNIT().equals("month")){
//			holder.tv_prodeuct_recommend_content_Second_one.setText(list.get(position).getTIMELIMIT()+"个月");
//		}
		
//		if(!PreferenceUtil.isLogin()){
//			holder.tv_product_recommend_content_Third_one.setText("登录可见");
//		}else{
//			holder.tv_product_recommend_content_Third_one.setText(list.get(position).getCOMMISSION());
//		}
		
		if(TextUtils.isEmpty(PreferenceUtil.isAuditStatus())){
			holder.tv_product_recommend_content_Third_one.setText("认证可见");
		}else{
			if(PreferenceUtil.isAuditStatus().equals("success")){
				holder.tv_product_recommend_content_Third_one.setText(list.get(position).getCOMMISSION());
			}else{
				holder.tv_product_recommend_content_Third_one.setText("认证可见");
			}
		}
		
		return convertView;
	}
	class Holder{
		TextView tv_recommendproduct_title_content;
		TextView iv_hot_product_creditlevel_text;
		
		RelativeLayout h_type_recommend_image;
		RelativeLayout xintuo_type_Branch_image;
		RelativeLayout rl_product_saletype;
		RelativeLayout rl_product_hottype;
		RelativeLayout rl_product_recommendtype;
		
		TextView tv_product_saletype;
		TextView tv_product_hottype;
		TextView tv_product_recommendtype;
		ImageView iv_recommend_product_creditlevel;
		TextView tv_prodeuct_recommend_content_first_one;
		TextView tv_prodeuct_recommend_content_Second_one;
		TextView tv_product_recommend_content_Third_one;
		MyProgressBar mypb_recommendproduct_ProgressBar;
		
	}
}
