package org.com.vnp.caothubongbong;

import org.com.cnc.common.adnroid16.activity.CommonActivity;
import org.com.vnp.caothubongbong.R.layout;
import org.com.vnp.caothubongbong.asyn.AsynSplash;

import android.os.Bundle;

public class Splash extends CommonActivity {
	private AsynSplash asynSplash;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.splash);
		asynSplash = new AsynSplash(this);
		asynSplash.execute("");
	}

	protected void onPause() {
		super.onPause();
		asynSplash.setClose(true);
		finish();
	}
}
