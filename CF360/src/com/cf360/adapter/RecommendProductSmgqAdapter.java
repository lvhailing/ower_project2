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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecommendProductSmgqAdapter extends BaseAdapter {

	private static final String tag = "recommendProductAdapter";
	private MouldList<ResultRecommendProductContentBean> list;
	private Context context;
	private LayoutInflater inflater;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	public RecommendProductSmgqAdapter(Context context,
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
			holder.iv_recommend_product_creditlevel = (ImageView) convertView.findViewById(R.id.iv_recommend_product_creditlevel);
			holder.rl_product_saletype = (RelativeLayout) convertView.findViewById(R.id.rl_product_saletype);
			holder.rl_product_hottype = (RelativeLayout) convertView.findViewById(R.id.rl_product_hottype);
			holder.rl_product_recommendtype = (RelativeLayout) convertView.findViewById(R.id.rl_product_recommendtype);
			
			holder.tv_product_saletype = (TextView) convertView.findViewById(R.id.tv_product_saletype);
			holder.tv_product_hottype = (TextView) convertView.findViewById(R.id.tv_product_hottype);
			holder.tv_product_recommendtype = (TextView) convertView.findViewById(R.id.tv_product_recommendtype);
			holder.tv_prodeuct_recommend_content_first_one = (TextView) convertView.findViewById(R.id.tv_prodeuct_recommend_content_first_one);
			holder.tv_prodeuct_recommend_content_first_two = (TextView) convertView.findViewById(R.id.tv_prodeuct_recommend_content_first_two);
			holder.tv_prodeuct_recommend_content_Second_one = (TextView) convertView.findViewById(R.id.tv_prodeuct_recommend_content_Second_one);
			holder.tv_prodeuct_recommend_content_Second_two = (TextView) convertView.findViewById(R.id.tv_prodeuct_recommend_content_Second_two);
			holder.tv_product_recommend_content_Third_one = (TextView) convertView.findViewById(R.id.tv_product_recommend_content_Third_one);
			holder.tv_product_recommend_content_Third_two = (TextView) convertView.findViewById(R.id.tv_product_recommend_content_Third_two);
			holder.mypb_recommendproduct_ProgressBar = (MyProgressBar) convertView.findViewById(R.id.mypb_recommendproduct_ProgressBar);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
//		holder.mypb_recommendproduct_ProgressBar.setVisibility(View.GONE);
		
		String progress = list.get(position).getRECRUITMENTPROCESS();
		int progressInt = (int)(Double.parseDouble(progress)*100);
		holder.mypb_recommendproduct_ProgressBar.setProgress(progressInt);
		holder.mypb_recommendproduct_ProgressBar.initText(15);
		
		holder.tv_recommendproduct_title_content.setText(list.get(position).getNAME());
		holder.iv_recommend_product_creditlevel.setVisibility(View.GONE);
		/*if(TextUtils.isEmpty(list.get(position).getSALETYPE())){
			holder.h_type_recommend_image.setVisibility(View.GONE);
		}else{
			holder.h_type_recommend_image.setVisibility(View.VISIBLE);
			holder.recommendproduct_type_recommend_text.setText(list.get(position).getSALETYPE());
		}*/
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
		
		holder.tv_prodeuct_recommend_content_Second_two.setText("投资期限");
		holder.tv_product_recommend_content_Third_two.setText("前端返佣");
		holder.tv_prodeuct_recommend_content_first_one.setText(list.get(position).getAMOUNT());
		
		if(TextUtils.isEmpty(list.get(position).getNAME5())){
			holder.tv_prodeuct_recommend_content_Second_one.setText("--");
		}else{
			holder.tv_prodeuct_recommend_content_Second_one.setText(list.get(position).getNAME5());
		}
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
		RelativeLayout rl_product_saletype;
		RelativeLayout rl_product_hottype;
		RelativeLayout rl_product_recommendtype;
		
		TextView tv_product_saletype;
		TextView tv_product_hottype;
		TextView tv_product_recommendtype;
		ImageView iv_recommend_product_creditlevel;
		TextView tv_prodeuct_recommend_content_first_one;
		TextView tv_prodeuct_recommend_content_first_two;
		TextView tv_prodeuct_recommend_content_Second_one;
		TextView tv_prodeuct_recommend_content_Second_two;
		TextView tv_product_recommend_content_Third_one;
		TextView tv_product_recommend_content_Third_two;
		MyProgressBar mypb_recommendproduct_ProgressBar;
		
	}
}
