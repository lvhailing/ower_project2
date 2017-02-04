package com.cf360.view;

import com.cf360.uitls.ViewUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class AssortView extends Button {

	private Context context;

	public interface OnTouchAssortListener {
		public void onTouchAssortListener(String s);

		public void onTouchAssortUP();
	}

	public AssortView(Context context) {
		super(context);
		this.context = context;
	}

	public AssortView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public AssortView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	// ����
	private String[] assort = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "#" };
	private Paint paint = new Paint();
	// ѡ�������
	private int selectIndex = -1;
	// ��ĸ������
	private OnTouchAssortListener onTouch;

	public void setOnTouchAssortListener(OnTouchAssortListener onTouch) {
		this.onTouch = onTouch;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int height = getHeight();
		int width = getWidth();
		int interval = height / assort.length;

		for (int i = 0, length = assort.length; i < length; i++) {
			// �����
			paint.setAntiAlias(true);
			// Ĭ�ϴ���
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			// ��ɫ
			paint.setColor(Color.parseColor("#BB8855"));
			paint.setTextSize(25);
			if (i == selectIndex) {
				// ��ѡ�����ĸ�ı���ɫ�ʹ���
				paint.setColor(Color.parseColor("#3399ff"));
				paint.setFakeBoldText(true);
				paint.setTextSize(ViewUtils.px2sp(context, 25));
			}
			// ������ĸ��X���
			float xPos = width / 2 - paint.measureText(assort[i]) / 2;
			// ������ĸ��Y���
			float yPos = interval * i + interval;
			canvas.drawText(assort[i], xPos, yPos, paint);
			paint.reset();
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float y = event.getY();
		int index = (int) (y / getHeight() * assort.length);
		if (index >= 0 && index < assort.length) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				// ����ı�
				if (selectIndex != index) {
					selectIndex = index;
					if (onTouch != null) {
						onTouch.onTouchAssortListener(assort[selectIndex]);
					}

				}
				break;
			case MotionEvent.ACTION_DOWN:
				selectIndex = index;
				if (onTouch != null) {
					onTouch.onTouchAssortListener(assort[selectIndex]);
				}

				break;
			case MotionEvent.ACTION_UP:
				if (onTouch != null) {
					onTouch.onTouchAssortUP();
				}
				selectIndex = -1;
				break;
			}
		} else {
			selectIndex = -1;
			if (onTouch != null) {
				onTouch.onTouchAssortUP();
			}
		}
		invalidate();

		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
}
