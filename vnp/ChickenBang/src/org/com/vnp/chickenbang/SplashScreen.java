package org.com.vnp.chickenbang;

import org.com.cnc.common.adnroid.activity.CommonActivity;
import org.com.vnp.chickenbang.asyn.SplashAsyn;

import android.os.Bundle;

public class SplashScreen extends CommonActivity {
	private SplashAsyn splashAsyn;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		splashAsyn = new SplashAsyn(this);
		splashAsyn.execute("");
	}
	
	protected void onDestroy() {
		super.onDestroy();
		splashAsyn.setClose(true);
	}
}
