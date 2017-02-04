package com.cf360.adapter;

import com.cf360.R;
import com.cf360.bean.ResultRecommendProductContentBean;
import com.cf360.mould.types.MouldList;
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

public class RecommendProductAdapter extends BaseAdapter {

	private static final String tag = "recommendProductAdapter";
	private MouldList<ResultRecommendProductContentBean> list;
	private Context context;
	private LayoutInflater inflater;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	public RecommendProductAdapter(Context context,
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
//			holder.recommendproduct_type_recommend_text = (TextView) convertView.findViewById(R.id.recommendproduct_type_recommend_text);
			holder.iv_recommend_product_creditlevel = (ImageView) convertView.findViewById(R.id.iv_recommend_product_creditlevel);
			
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
		holder.tv_recommendproduct_title_content.setText(list.get(position).getNAME());
		if(TextUtils.isEmpty(list.get(position).getSALETYPE())){
			holder.h_type_recommend_image.setVisibility(View.GONE);
		}else{
			holder.h_type_recommend_image.setVisibility(View.VISIBLE);
			holder.recommendproduct_type_recommend_text.setText(list.get(position).getSALETYPE());
		}
		
		if(!TextUtils.isEmpty(list.get(position).getCREDITLEVEL())){
			if(list.get(position).getCREDITLEVEL().equals("AAA")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_aaa);
			}else
			if(list.get(position).getCREDITLEVEL().equals("AA")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_aa);
			}else
			if(list.get(position).getCREDITLEVEL().equals("A")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_a);
			}else
			if(list.get(position).getCREDITLEVEL().equals("BBB")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_bbb);
			}else
			if(list.get(position).getCREDITLEVEL().equals("BB")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_bb);
			}else
			if(list.get(position).getCREDITLEVEL().equals("B")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_b);
			}else
			if(list.get(position).getCREDITLEVEL().equals("CCC")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_ccc);
			}else
			if(list.get(position).getCREDITLEVEL().equals("CC")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_cc);
			}else
			if(list.get(position).getCREDITLEVEL().equals("C")){
				holder.iv_recommend_product_creditlevel.setImageResource(R.drawable.img_c);
			}
		}else{
			holder.iv_recommend_product_creditlevel.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	class Holder{
		TextView tv_recommendproduct_title_content;
		RelativeLayout h_type_recommend_image;
		TextView recommendproduct_type_recommend_text;
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
