package com.vn.icts.wendy;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;

import com.vn.icts.wendy.controller.HomeTabActivity;

public class SplashActivity extends Activity implements Runnable {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		AlphaAnimation animation = new AlphaAnimation(0.0f, 1);
		animation.setDuration(3000);
		findViewById(R.id.rl).startAnimation(animation);
	}

	@Override
	protected void onStart() {
		super.onStart();
		// thread run for wait 3s
		// when 3s, goto mainscreen
		new Thread(this).start();
	}

	/**
	 * when use home key, goto other screen or power key use close app
	 */
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	/**
	 * block keyboard back
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(3 * 1000);
		} catch (Exception exception) {
		}

		//
		if (!isFinishing()) {
			// when app is open
			// goto main Screen
			startActivity(new Intent(this, HomeTabActivity.class));
		}
	}
}