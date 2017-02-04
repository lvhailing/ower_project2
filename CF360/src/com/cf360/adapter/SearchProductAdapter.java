package com.cf360.adapter;

import com.cf360.R;
import com.cf360.bean.ResultHotProductContentBean;
import com.cf360.bean.ResultSearchProductContentBean;
import com.cf360.bean.SearchProductContentBean;
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

public class SearchProductAdapter extends BaseAdapter {

	private MouldList<SearchProductContentBean> list;
	private Context context;
	private LayoutInflater inflater;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	public SearchProductAdapter(Context context,
			MouldList<SearchProductContentBean> list) {
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
			convertView = inflater.inflate(R.layout.activity_search_product_item, null);
			holder.tv_hotproduct_title_content = (TextView) convertView.findViewById(R.id.tv_hotproduct_title_content);
			holder.iv_hot_product_creditlevel_text = (TextView) convertView.findViewById(R.id.iv_hot_product_creditlevel_text);
			holder.rl_product_hottype = (RelativeLayout) convertView.findViewById(R.id.rl_product_hottype);
			holder.rl_product_recommendtype = (RelativeLayout) convertView.findViewById(R.id.rl_product_recommendtype);
			holder.rl_product_saletype = (RelativeLayout) convertView.findViewById(R.id.rl_product_saletype);
			holder.tv_product_saletype = (TextView) convertView.findViewById(R.id.tv_product_saletype);
			holder.iv_hot_product_img = (ImageView) convertView.findViewById(R.id.iv_hot_product_img);
			holder.iv_hot_product_creditlevel = (ImageView) convertView.findViewById(R.id.iv_hot_product_creditlevel);
			
			holder.tv_prodeuct_hot_content_first_one = (TextView) convertView.findViewById(R.id.tv_prodeuct_hot_content_first_one);
			holder.tv_prodeuct_hot_content_first_two = (TextView) convertView.findViewById(R.id.tv_prodeuct_hot_content_first_two);
			holder.tv_prodeuct_hot_content_Second_one = (TextView) convertView.findViewById(R.id.tv_prodeuct_hot_content_Second_one);
			holder.tv_prodeuct_hot_content_Second_two = (TextView) convertView.findViewById(R.id.tv_prodeuct_hot_content_Second_two);
			holder.tv_product_hot_content_Third_one = (TextView) convertView.findViewById(R.id.tv_product_hot_content_Third_one);
			holder.tv_product_hot_content_Third_two = (TextView) convertView.findViewById(R.id.tv_product_hot_content_Third_two);
			holder.mypb_hotproduct_ProgressBar = (MyProgressBar) convertView.findViewById(R.id.mypb_hotproduct_ProgressBar);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		
		holder.tv_hotproduct_title_content.setText(list.get(position).getNAME());
		
		if(TextUtils.isEmpty(list.get(position).getSELLINGSTATUS())){
			holder.rl_product_hottype.setVisibility(View.GONE);
		}else{
			if(list.get(position).getSELLINGSTATUS().equals("0")){
				holder.rl_product_hottype.setVisibility(View.GONE);
				
			}else if(list.get(position).getSELLINGSTATUS().equals("1")){
				
				holder.rl_product_hottype.setVisibility(View.VISIBLE);
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
			if(list.get(position).getSALETYPE().equals("0")){
				holder.tv_product_saletype.setText("包销");
			}else if(list.get(position).getSALETYPE().equals("1")){
				holder.tv_product_saletype.setText("分销");
			}else{
				holder.rl_product_saletype.setVisibility(View.GONE);
			}
			
		}
		
		LayoutParams para;
		para = holder.iv_hot_product_creditlevel.getLayoutParams();
		
		if(!TextUtils.isEmpty(list.get(position).getCREDITLEVEL())){
			holder.iv_hot_product_creditlevel.setVisibility(View.VISIBLE);
			if(list.get(position).getCREDITLEVEL().equals("11")){
				
				holder.iv_hot_product_creditlevel_text.setVisibility(View.VISIBLE);
//				holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_d);
				holder.iv_hot_product_creditlevel.setVisibility(View.GONE);
//				para.width = 120;
//				para.height = 40;
			}else if(list.get(position).getCREDITLEVEL().equals("1")){
				holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_no);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("2")){
				holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_aaa);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("3")){
				holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_aa);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("4")){
				holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_a);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("5")){
				holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_bbb);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("6")){
				holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_bb);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("7")){
				holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_b);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("8")){
				holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_ccc);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("9")){
				holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_cc);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else
			if(list.get(position).getCREDITLEVEL().equals("10")){
				holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_c);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 100;
			}else{
				holder.iv_hot_product_creditlevel.setVisibility(View.GONE);
				holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
			}
			
		}else{
			holder.iv_hot_product_creditlevel.setVisibility(View.GONE);
			holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
		}
		holder.tv_prodeuct_hot_content_first_two.setText(list.get(position).getNAME3());
		holder.tv_prodeuct_hot_content_first_one.setText(list.get(position).getAMOUNT());
		holder.tv_prodeuct_hot_content_Second_two.setText(list.get(position).getNAME4());
		holder.tv_prodeuct_hot_content_Second_one.setText(list.get(position).getINVESTMENTSTAGE());
		holder.tv_product_hot_content_Third_two.setText(list.get(position).getNAME5());
		
		if(PreferenceUtil.isAuditStatus().equals("success")){
			if(!PreferenceUtil.isLogin()){
				holder.tv_product_hot_content_Third_one.setText("认证可见");
			}else{
				holder.tv_product_hot_content_Third_one.setText(list.get(position).getCOMMISSION());
			}
		}else{
			holder.tv_product_hot_content_Third_one.setText("认证可见");
		}
		
		
		
		if(list.get(position).getHASPROCESS().equals("true")){
			holder.mypb_hotproduct_ProgressBar.setVisibility(View.VISIBLE);
			String progress = list.get(position).getRECRUITMENTPROCESS();
			int progressInt = (int)(Double.parseDouble(progress)*100);
			holder.mypb_hotproduct_ProgressBar.setProgress(progressInt);
			holder.mypb_hotproduct_ProgressBar.initText(15);
		}else{
			holder.mypb_hotproduct_ProgressBar.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	class Holder{
		TextView tv_hotproduct_title_content;
		TextView iv_hot_product_creditlevel_text;
		RelativeLayout rl_product_hottype;
		RelativeLayout rl_product_recommendtype;
		RelativeLayout rl_product_saletype;
		
		TextView tv_product_saletype;
		ImageView iv_hot_product_creditlevel;
		ImageView iv_hot_product_img;
		TextView tv_prodeuct_hot_content_first_one;
		TextView tv_prodeuct_hot_content_first_two;
		TextView tv_prodeuct_hot_content_Second_one;
		TextView tv_prodeuct_hot_content_Second_two;
		TextView tv_product_hot_content_Third_one;
		TextView tv_product_hot_content_Third_two;
		MyProgressBar mypb_hotproduct_ProgressBar;
		
	}
}
