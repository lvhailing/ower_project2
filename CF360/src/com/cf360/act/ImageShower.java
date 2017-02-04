package com.cf360.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.cf360.R;
import com.cf360.view.ImageLoadingDialog;

public class ImageShower extends BaseActivity {
private ImageView imageview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageshower);
		/*if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;*/
		imageview=(ImageView) findViewById(R.id.imgeView);
		
		Intent intent = getIntent();
		if (intent != null) {
			Bitmap bitmap = intent.getParcelableExtra("bitmap");
			Matrix matrix = new Matrix(); //接收图片之后放大 4倍
            matrix.postScale(4f, 4f);
            Bitmap bit = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
			imageview.setImageBitmap(bit);
		}

		final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
			}
		}, 1000 * 2);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

}
