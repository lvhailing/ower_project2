package com.cf360.adapter;

import com.cf360.R;
import com.cf360.bean.ResultHotProductContentBean;
import com.cf360.mould.types.MouldList;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.view.MyProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
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

public class HotProductAdapter extends BaseAdapter {
    private static final String tag = "HotProductAdapter";
    private MouldList<ResultHotProductContentBean> list;
    private Context context;
    private LayoutInflater inflater;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public HotProductAdapter(Context context, MouldList<ResultHotProductContentBean> list, DisplayImageOptions options) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.options = options;
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
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.activity_hot_product_item, null);
            holder.tv_hotproduct_title_content = (TextView) convertView.findViewById(R.id.tv_hotproduct_title_content);
            holder.iv_hot_product_creditlevel_text = (TextView) convertView.findViewById(R.id.iv_hot_product_creditlevel_text);

            holder.rl_product_saletype = (RelativeLayout) convertView.findViewById(R.id.rl_product_saletype);
            holder.rl_product_hottype = (RelativeLayout) convertView.findViewById(R.id.rl_product_hottype);
            holder.rl_product_recommendtype = (RelativeLayout) convertView.findViewById(R.id.rl_product_recommendtype);

            holder.tv_product_saletype = (TextView) convertView.findViewById(R.id.tv_product_saletype);
            holder.tv_product_hottype = (TextView) convertView.findViewById(R.id.tv_product_hottype);
            holder.tv_product_recommendtype = (TextView) convertView.findViewById(R.id.tv_product_recommendtype);

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
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (TextUtils.isEmpty(list.get(position).getSELLINGSTATUS())) {
            holder.rl_product_hottype.setVisibility(View.GONE);
        } else {
            if (list.get(position).getSELLINGSTATUS().equals("热销")) {
                holder.rl_product_hottype.setVisibility(View.VISIBLE);
            } else {
                holder.rl_product_hottype.setVisibility(View.GONE);
            }
        }

        if (TextUtils.isEmpty(list.get(position).getRECOMMENDSTATUS())) {
            holder.rl_product_recommendtype.setVisibility(View.GONE);
        } else {
            if (list.get(position).getRECOMMENDSTATUS().equals("0")) {
                holder.rl_product_recommendtype.setVisibility(View.GONE);

            } else if (list.get(position).getRECOMMENDSTATUS().equals("1")) {

                holder.rl_product_recommendtype.setVisibility(View.VISIBLE);
            }
        }

        if (TextUtils.isEmpty(list.get(position).getSALETYPE())) {

        } else {
            holder.rl_product_saletype.setVisibility(View.VISIBLE);
            if (list.get(position).getSALETYPE().equals("包销")) {
                holder.tv_product_saletype.setText("包销");
            } else if (list.get(position).getSALETYPE().equals("分销")) {
                holder.tv_product_saletype.setText("分销");
            } else {
                holder.rl_product_saletype.setVisibility(View.GONE);
            }

        }

        holder.tv_hotproduct_title_content.setText(list.get(position).getNAME());

        imageLoader.displayImage(list.get(position).getPHOTOSATTACHMENTS(), holder.iv_hot_product_img, options);
//		LayoutParams para1;
//		para1 = holder.iv_hot_product_img.getH.getLayoutParams();
//		Toast.makeText(context, para1.height, 0).show();
//		Toast.makeText(context, para1.width, 0).show();
//		System.out.println("para1.height==="+holder.iv_hot_product_img.getHeight()+"  para1.width=="+holder.iv_hot_product_img.getWidth());

//		LayoutParams para;
//		para = holder.iv_hot_product_creditlevel.getLayoutParams();

        if (!TextUtils.isEmpty(list.get(position).getCREDITLEVEL()) && (list.get(position).getCATEGORY().equals("信托") || list.get(position).getCATEGORY().equals("资管"))) {
            holder.iv_hot_product_creditlevel.setVisibility(View.VISIBLE);
            if (list.get(position).getCREDITLEVEL().equals("11")) {
                holder.iv_hot_product_creditlevel_text.setVisibility(View.VISIBLE);
//				holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_d);
                holder.iv_hot_product_creditlevel.setVisibility(View.GONE);
//				para.width = 120;
//				para.height = 40;
            } else if (list.get(position).getCREDITLEVEL().equals("1")) {
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_no);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (list.get(position).getCREDITLEVEL().equals("2")) {
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_aaa);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (list.get(position).getCREDITLEVEL().equals("3")) {
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_aa);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (list.get(position).getCREDITLEVEL().equals("4")) {
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_a);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (list.get(position).getCREDITLEVEL().equals("5")) {
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_bbb);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (list.get(position).getCREDITLEVEL().equals("6")) {
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_bb);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (list.get(position).getCREDITLEVEL().equals("7")) {
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_b);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (list.get(position).getCREDITLEVEL().equals("8")) {
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_ccc);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (list.get(position).getCREDITLEVEL().equals("9")) {
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_cc);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (list.get(position).getCREDITLEVEL().equals("10")) {
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_c);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else {
                holder.iv_hot_product_creditlevel.setVisibility(View.GONE);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
            }
//			holder.iv_hot_product_creditlevel.setLayoutParams(para);
        } else {
            holder.iv_hot_product_creditlevel.setVisibility(View.GONE);
        }
        holder.tv_prodeuct_hot_content_first_two.setText(list.get(position).getNAME3());
        holder.tv_prodeuct_hot_content_first_one.setText(list.get(position).getNAME3KEY());
        holder.tv_prodeuct_hot_content_Second_two.setText(list.get(position).getNAME4());
        if (TextUtils.isEmpty(list.get(position).getNAME4KEY())) {
            holder.tv_prodeuct_hot_content_Second_one.setText("--");
        } else {
            holder.tv_prodeuct_hot_content_Second_one.setText(list.get(position).getNAME4KEY());
        }
        holder.tv_product_hot_content_Third_two.setText(list.get(position).getNAME5());

        if (TextUtils.isEmpty(PreferenceUtil.isAuditStatus())) {
            holder.tv_product_hot_content_Third_one.setText("认证可见");
        } else {
            if (PreferenceUtil.isAuditStatus().equals("success")) {
                holder.tv_product_hot_content_Third_one.setText(list.get(position).getNAME5KEY());
            } else {
                holder.tv_product_hot_content_Third_one.setText("认证可见");
            }
        }

        if (list.get(position).getISSHOW().equals("true")) {
            holder.mypb_hotproduct_ProgressBar.setVisibility(View.VISIBLE);
            String progress = list.get(position).getRECRUITMENTPROCESS();
            int progressInt = (int) (Double.parseDouble(progress) * 100);
            holder.mypb_hotproduct_ProgressBar.setProgress(progressInt);
            holder.mypb_hotproduct_ProgressBar.initText(15);
        } else {
            holder.mypb_hotproduct_ProgressBar.setVisibility(View.GONE);
        }

        return convertView;
    }

    class Holder {
        TextView tv_hotproduct_title_content;
        TextView iv_hot_product_creditlevel_text;
        RelativeLayout rl_product_saletype;
        RelativeLayout rl_product_hottype;
        RelativeLayout rl_product_recommendtype;

        TextView tv_product_saletype;
        TextView tv_product_hottype;
        TextView tv_product_recommendtype;

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
