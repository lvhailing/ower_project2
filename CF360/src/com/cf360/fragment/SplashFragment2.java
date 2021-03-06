package com.cf360.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.cf360.R;
import com.cf360.act.MainActivity;
import com.cf360.anim.SceneAnimation;
import com.cf360.uitls.PreferenceUtil;

public class SplashFragment2 extends Fragment implements OnClickListener {
	private ViewGroup vp;
	private ImageView animationIV;
	private AnimationDrawable animationDrawable;
	private Button btnJump;
	private SceneAnimation mAnimation;
	private final int[] pFrameRess = { R.drawable.splash_two1,
			R.drawable.splash_two2, R.drawable.splash_two3,
			R.drawable.splash_two4, R.drawable.splash_two5 };
	private final int[] pDuration = { 150, 150, 150, 150, 150 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		vp = (ViewGroup) inflater.inflate(R.layout.fragment_splash2, container,
				false);
		animationIV = (ImageView) vp.findViewById(R.id.splash_twotop);
		btnJump = (Button) vp.findViewById(R.id.splash_twojump);
		btnJump.setOnClickListener(this);
		mAnimation = new SceneAnimation(animationIV, pFrameRess, pDuration);
		mAnimation.startPaly(true);
		// mHandler = new MyHandler();
		// mthread = new Thread(myRunnable);
		// mthread.start();
		return vp;
	}

	// private MyHandler mHandler;
	// private Thread mthread;
	//
	// class MyHandler extends Handler {
	//
	// @Override
	// public void handleMessage(Message msg) {
	// super.handleMessage(msg);
	// if (msg.what == 1) {
	// animationIV.setBackgroundResource(R.drawable.splash_one1);
	// } else if (msg.what == 2) {
	// animationIV.setBackgroundResource(R.drawable.splash_one2);
	// } else if (msg.what == 3) {
	// animationIV.setBackgroundResource(R.drawable.splash_one3);
	// } else if (msg.what == 4) {
	// animationIV.setBackgroundResource(R.drawable.splash_one4);
	// } else if (msg.what == 5) {
	// animationIV.setBackgroundResource(R.drawable.splash_one5);
	// } else if (msg.what == 6) {
	// animationIV.setBackgroundResource(R.drawable.splash_one6);
	// }
	// }
	// }
	//
	// Runnable myRunnable = new Runnable() {
	//
	// @Override
	// public void run() {
	// int i = 0;
	// while (i < 6) {
	// Message msg = new Message();
	// i++;
	// msg.what = i;
	// mHandler.sendMessage(msg);
	// try {
	// Thread.sleep(150);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// };

	@Override
	public void onDestroy() {
		super.onDestroy();
		// animationDrawable = null;
		// mHandler.removeCallbacks(myRunnable);
		animationIV = null;
		unbindDrawables(vp);
	}

	private void unbindDrawables(View view) {
		if (view.getBackground() != null) {
			view.getBackground().setCallback(null);
		}
		if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				unbindDrawables(((ViewGroup) view).getChildAt(i));
			}
			((ViewGroup) view).removeAllViews();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mAnimation.stopPlay();
		// animationDrawable.stop();
	}

	@Override
	public void onResume() {
		super.onResume();
		// if (animationDrawable == null) {
		// animationDrawable = (AnimationDrawable) animationIV.getDrawable();
		// }
		// animationDrawable.start();
	}

	@Override
	public void onClick(View v) {
		Intent iMain = new Intent(getActivity(), MainActivity.class);
		startActivity(iMain);
		PreferenceUtil.setFirst(false);
		getActivity().finish();
	}

}
