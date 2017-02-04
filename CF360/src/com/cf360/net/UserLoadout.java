package com.cf360.net;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.cf360.act.LoginActivity;
import com.cf360.bean.ResultLoginOffContentBean;
import com.cf360.mould.BaseParams;
import com.cf360.mould.HtmlRequest;
import com.cf360.mould.BaseRequester.OnRequestListener;
import com.cf360.uitls.PreferenceUtil;

public class UserLoadout {

	private Context context;

	public UserLoadout(Context context) {
		this.context = context;
	}

	public void requestData() {
		HtmlRequest.loginoff(context, new OnRequestListener() {

			@Override
			public void onRequestFinished(BaseParams params) {
				ResultLoginOffContentBean b = (ResultLoginOffContentBean) params.result;
//				if (b != null) {
//					if (Boolean.parseBoolean(b.getFlag())) {
						PreferenceUtil.setAutoLoginAccount("");
						PreferenceUtil.setAutoLoginPwd("");
						PreferenceUtil.setLogin(false);
						PreferenceUtil.setPhone("");
						PreferenceUtil.setUserId("");
						PreferenceUtil.setUserNickName("");
						PreferenceUtil.setCookie("");
						
						// i.putExtra("result", "exit");
						// setResult(9, i);
						Intent tent = new Intent("vjinkeexit");// 广播的标签，一定要和需要接受的一致。
						tent.putExtra("result", "exit");
						context.sendBroadcast(tent);// 发送广播
						Toast.makeText(context, "退出成功", Toast.LENGTH_LONG)
								.show();
						Intent i = new Intent();
						i.setClass(context, LoginActivity.class);
						context.startActivity(i);
//					} else {
//						Toast.makeText(context, b.getMsg(), Toast.LENGTH_LONG)
//								.show();
//					}
//				}
			}
		});
	}
}
