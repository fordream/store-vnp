package org.cnc.smashpaddles.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread thread=new Thread(new Thread() {
			
			@Override
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Intent intentHome = new Intent(SplashActivity.this,
						SplashScreen.class);
				startActivity(intentHome);
				finish();
			}
		});
		thread.start();
		
	}

}
