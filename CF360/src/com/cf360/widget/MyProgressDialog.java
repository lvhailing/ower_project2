package com.cf360.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cf360.R;

public class MyProgressDialog extends Dialog {

    //private AnimationDrawable animationDrawable;

    private Drawable rotate;
    private ImageView imageView;

    @Override
    public void dismiss() {
        /*if (animationDrawable != null) {
            animationDrawable.stop();
        }*/
        if (rotate != null) {
            ((Animatable) rotate).stop();
        }
        super.dismiss();
    }

    public MyProgressDialog(Context context) {
        this(context, R.style.CustomDialog);
        this.setContentView(R.layout.customprogressdialog);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        init(context);
    }

    private void init(Context context) {
        // TODO Auto-generated method stub
        imageView = (ImageView) findViewById(R.id.loadingImageView);
        rotate = imageView.getBackground();

    }

    private MyProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus)
            return;
        /*animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();*/
        //rotate = imageView.getBackground();
        ((Animatable) rotate).start();
    }

    /**
     * setMessage 提示内容
     * @param strMessage
     * @return
     *
     */
    public void setMessage(String strMessage) {
        TextView tvMsg = (TextView) this.findViewById(R.id.id_tv_loadingmsg);

        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }
        tvMsg.setVisibility(View.GONE);
    }

}
