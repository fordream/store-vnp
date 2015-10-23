package org.com.vnp.xathubongbong2.controller;

import org.com.vnp.xathubongbong2.R;
import org.com.vnp.xathubongbong2.model.CommonView;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class SplashController extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CommonView.hiddenTitleBarAndFullScreen(this);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.splash);

		gotoMenu();
	}

	private void gotoMenu() {
		Intent intent = new Intent(this, MenuController.class);
		startActivity(intent);
		finish();
	}
}