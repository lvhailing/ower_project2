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

/**
 * 首页热销产品 Adapter 类
 */
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

        //判断产品销售状态：包销，热销，推荐
       /* if (TextUtils.isEmpty(list.get(position).getSELLINGSTATUS())) {
            holder.rl_product_hottype.setVisibility(View.GONE);
        } else {
            if (list.get(position).getSELLINGSTATUS().equals("热销")) {
                holder.rl_product_hottype.setVisibility(View.VISIBLE);
            } else {
                holder.rl_product_hottype.setVisibility(View.GONE);
            }
        }*/
        String status = list.get(position).getSELLINGSTATUS();
        if (!TextUtils.isEmpty(status) && status.equals("热销")) {
            holder.rl_product_hottype.setVisibility(View.VISIBLE);
        } else {
            holder.rl_product_hottype.setVisibility(View.GONE);
        }

        //判断 “推荐” 标签是否显示（ 0  表示不显示，1 显示）
       /* if (TextUtils.isEmpty(list.get(position).getRECOMMENDSTATUS())) {
            holder.rl_product_recommendtype.setVisibility(View.GONE);
        } else {
            if (list.get(position).getRECOMMENDSTATUS().equals("0")) {
                holder.rl_product_recommendtype.setVisibility(View.GONE);
            } else if (list.get(position).getRECOMMENDSTATUS().equals("1")) {
                holder.rl_product_recommendtype.setVisibility(View.VISIBLE);
            }
        }*/
        String recommendstatus = list.get(position).getRECOMMENDSTATUS();
        if (!TextUtils.isEmpty(recommendstatus) && !recommendstatus.equals("0")) {
            holder.rl_product_recommendtype.setVisibility(View.VISIBLE);
        } else {
            holder.rl_product_recommendtype.setVisibility(View.GONE);
        }

        //判断销售方式（包销/分销）
       /* if (TextUtils.isEmpty(list.get(position).getSALETYPE())) {

        } else {
            holder.rl_product_saletype.setVisibility(View.VISIBLE);
            if (list.get(position).getSALETYPE().equals("包销")) {
                holder.tv_product_saletype.setText("包销");
            } else if (list.get(position).getSALETYPE().equals("分销")) {
                holder.tv_product_saletype.setText("分销");
            } else {
                holder.rl_product_saletype.setVisibility(View.GONE);
            }
        }*/
        String saletype = list.get(position).getSALETYPE();
        if (!TextUtils.isEmpty(saletype)) {
            holder.rl_product_saletype.setVisibility(View.VISIBLE);
            if (saletype.equals("包销")) {
                holder.tv_product_saletype.setText("包销");
            } else if (saletype.equals("分销")) {
                holder.tv_product_saletype.setText("分销");
            }
        } else {
            holder.rl_product_saletype.setVisibility(View.GONE);
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

        String creditlevel = list.get(position).getCREDITLEVEL(); //信用等级
        String category = list.get(position).getCATEGORY(); //产品类型
        if (!TextUtils.isEmpty(creditlevel) && (category.equals("信托") || category.equals("资管"))) {
            holder.iv_hot_product_creditlevel.setVisibility(View.VISIBLE);
            if (creditlevel.equals("11")) {
                holder.iv_hot_product_creditlevel_text.setVisibility(View.VISIBLE);
//				holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_d);
                holder.iv_hot_product_creditlevel.setVisibility(View.GONE);
//				para.width = 120;
//				para.height = 40;
            } else if (creditlevel.equals("1")) { //当信托等级等于1时，显示图片“不限”
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_no);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE); // “无评级” 隐藏
//				para.width = 80;
            } else if (creditlevel.equals("2")) { //当信托等级等于2时，显示图片3A
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_aaa);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (creditlevel.equals("3")) { //当信托等级等于3时，显示图片2A
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_aa);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (creditlevel.equals("4")) {  //当信托等级等于4时，显示图片1A
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_a);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (creditlevel.equals("5")) {  //当信托等级等于5时，显示图片3B
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_bbb);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (creditlevel.equals("6")) { //当信托等级等于6时，显示图片2B
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_bb);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (creditlevel.equals("7")) { //当信托等级等于7时，显示图片1B
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_b);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (creditlevel.equals("8")) { //当信托等级等于8时，显示图片3C
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_ccc);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (creditlevel.equals("9")) { //当信托等级等于9时，显示图片2C
                holder.iv_hot_product_creditlevel.setImageResource(R.drawable.img_cc);
                holder.iv_hot_product_creditlevel_text.setVisibility(View.GONE);
//				para.width = 80;
            } else if (creditlevel.equals("10")) { //当信托等级等于10时，显示图片1C
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
        holder.tv_prodeuct_hot_content_first_two.setText(list.get(position).getNAME3()); //认购金额，保险公司
        holder.tv_prodeuct_hot_content_first_one.setText(list.get(position).getNAME3KEY()); //认购金额(保险公司)对应的值
        holder.tv_prodeuct_hot_content_Second_two.setText(list.get(position).getNAME4()); //产品期限，累计净值，投资期限，保险期间
        if (TextUtils.isEmpty(list.get(position).getNAME4KEY())) {
            holder.tv_prodeuct_hot_content_Second_one.setText("--");
        } else {
            holder.tv_prodeuct_hot_content_Second_one.setText(list.get(position).getNAME4KEY()); //产品期限，累计净值，投资期限，保险期间对应的值
        }
        holder.tv_product_hot_content_Third_two.setText(list.get(position).getNAME5());

        if (TextUtils.isEmpty(PreferenceUtil.isAuditStatus())) {
            holder.tv_product_hot_content_Third_one.setText("认证可见");
        } else {
            if (PreferenceUtil.isAuditStatus().equals("success")) {
                holder.tv_product_hot_content_Third_one.setText(list.get(position).getNAME5KEY()); //前端返佣，后端返佣，返佣比例对应的值
            } else {
                holder.tv_product_hot_content_Third_one.setText("认证可见");
            }
        }

        if (list.get(position).getISSHOW().equals("true")) {
            holder.mypb_hotproduct_ProgressBar.setVisibility(View.VISIBLE);
            String progress = list.get(position).getRECRUITMENTPROCESS(); //进度条数据
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
