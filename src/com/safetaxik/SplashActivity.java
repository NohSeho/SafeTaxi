package com.safetaxik;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Handler hdl = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				startActivity(new Intent(SplashActivity.this, MainActivity.class));
				finish();
			}
		};
		hdl.sendEmptyMessageDelayed(0, 1234); // 3초후 메시지 보내지
	}
}
