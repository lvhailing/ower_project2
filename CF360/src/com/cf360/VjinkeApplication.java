package com.cf360;

import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cf360.uitls.APNManager;
import com.cf360.uitls.NetworkUtils;
import com.cf360.uitls.PreferenceUtil;
import com.cf360.uitls.SystemInfo;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class VjinkeApplication extends Application {
	private static VjinkeApplication instance;
	public static String mAppId;
	public static String mDownloadPath;

	public static VjinkeApplication getInstance() {
		return instance;
	}

	public static void timer1() {


		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("-------设定要指定任务--------");
			}
		}, 2000);// 设定指定的时间time,此处为2000毫秒
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		NetworkUtils.setContext(this);
		// CrashHandler.getInstance().init(this);
		PreferenceUtil.initialize(this);
		SystemInfo.initialize(this);
		initNetReceiver();
		initImageLoader();
		APNManager.getInstance().checkNetworkType(this);
		mAppId = getString(R.string.app_id);
		mDownloadPath = "/" + mAppId + "/download";
//		timer1();
	}


	/****
	 * 初始化imageloader
	 */
	private void initImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	public interface NetListener {
		public void onNetWorkChange(String netType);
	}

	private BroadcastReceiver mReceiver;
	public String netType;
	HashSet<NetListener> mListeners = new HashSet<NetListener>();
	IntentFilter mFilter;

	/**
	 * 加入网络监听
	 * 
	 * @param l
	 * @return
	 */
	public boolean addNetListener(NetListener l) {
		boolean rst = false;
		if (l != null && mListeners != null) {
			rst = mListeners.add(l);
		}
		return rst;
	}

	/**
	 * 移除网络监听
	 * 
	 * @param l
	 * @return
	 */
	public boolean removeNetListener(NetListener l) {
		return mListeners.remove(l);
	}

	/**
	 * 初始化网络监听器
	 */
	private void initNetReceiver() {
		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
					ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo info = manager.getActiveNetworkInfo();
					if (info != null && info.isConnected()) {
						netType = info.getTypeName();
					} else {
						netType = "";
					}
					for (NetListener lis : mListeners) {
						if (lis != null) {
							lis.onNetWorkChange(netType);
						}
					}
				}
			}
		};
		mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
	}

	/**
	 * 注册网络监听器
	 */
	public void registReceiver() {
		try {
			registerReceiver(mReceiver, mFilter);
		} catch (Exception e) {
		}
	}

	/**
	 * 注销网络监听器
	 */
	public void unRegisterNetListener() {
		if (mListeners != null) {
			mListeners.clear();
		}
		try {
			unregisterReceiver(mReceiver);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
