package com.cf360.anim;

import android.widget.ImageView;

public class SceneAnimation {
	private ImageView mImageView;
	private int[] mFrameRess;
	private int[] mDurations;
	private int mDuration;

	private int mLastFrameNo;
	private long mBreakDelay;

	private int pFrameNo = 1;
	private boolean always = false;

	public SceneAnimation(ImageView pImageView, int[] pFrameRess,
			int[] pDurations) {
		mImageView = pImageView;
		mFrameRess = pFrameRess;
		mDurations = pDurations;
		mLastFrameNo = pFrameRess.length - 1;

		mImageView.setBackgroundResource(mFrameRess[0]);
	}

	public SceneAnimation(ImageView pImageView, int[] pFrameRess, int pDuration) {
		mImageView = pImageView;
		mFrameRess = pFrameRess;
		mDuration = pDuration;
		mLastFrameNo = pFrameRess.length - 1;

		mImageView.setBackgroundResource(mFrameRess[0]);
		playConstant(1);
	}

	public SceneAnimation(ImageView pImageView, int[] pFrameRess,
			int pDuration, long pBreakDelay) {
		mImageView = pImageView;
		mFrameRess = pFrameRess;
		mDuration = pDuration;
		mLastFrameNo = pFrameRess.length - 1;
		mBreakDelay = pBreakDelay;

		mImageView.setBackgroundResource(mFrameRess[0]);
		playConstant(1);
	}

	private void play(final int pFrameNo) {
		mImageView.postDelayed(myPlayRunnable, mDurations[pFrameNo]);
	}

	private void playConstant(final int pFrameNo) {
		mImageView.postDelayed(new Runnable() {
			public void run() {
				mImageView.setBackgroundResource(mFrameRess[pFrameNo]);

				if (pFrameNo == mLastFrameNo)
					playConstant(0);
				else
					playConstant(pFrameNo + 1);
			}
		}, pFrameNo == mLastFrameNo && mBreakDelay > 0 ? mBreakDelay
				: mDuration);
	}

	Runnable myPlayRunnable = new Runnable() {
		public void run() {
			mImageView.setBackgroundResource(mFrameRess[pFrameNo]);
			if (always) {
				pFrameNo = pFrameNo % mLastFrameNo;
				play(pFrameNo++);
			} else {
				if (pFrameNo == mLastFrameNo)
					play(0);
				else
					play(pFrameNo++);
			}

		}
	};

	public void stopPlay() {
		mImageView.removeCallbacks(myPlayRunnable);
	}

	public void startPaly(boolean always) {
		this.always = always;
		play(1);
	}
}