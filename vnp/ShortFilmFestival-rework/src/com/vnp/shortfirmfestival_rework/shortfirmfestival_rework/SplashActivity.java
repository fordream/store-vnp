package com.vnp.shortfirmfestival_rework.shortfirmfestival_rework;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.vnp.shortfirmfestival_rework.R;
import com.vnp.shortfirmfestival_rework.base.ShortFirmBaseActivity;

public class SplashActivity extends ShortFirmBaseActivity implements Runnable {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		new Handler().postDelayed(this, getTimeStartAnimation());
	}

	@Override
	public void run() {

		if (!isFinishing()) {
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}
	}
}