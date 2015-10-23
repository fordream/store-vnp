package com.cnc.timtalk.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.widget.TextView;

public class TalkSplashScreen extends Activity {

	protected boolean mActive = true;
	protected int mSplashTime = 3000;
	String cnc;
	TextView txtCopyright;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash);
		// thread for displaying the SplashScreen
		txtCopyright = (TextView) findViewById(R.id.txt_copyright);
		cnc = getString(R.string.lable_copyright);
		txtCopyright.setText(Html.fromHtml("<a href=\"http://www.google.com\">"
				+ cnc + "</a> "));
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (mActive && (waited < mSplashTime)) {
						sleep(100);
						if (mActive) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					startActivity(new Intent(
							"com.cnc.timtalk.ui.intent.LOGIN_SCREEN"));
					// stop();
					finish();
				}
			}
		};
		splashTread.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mActive = false;
		}
		return true;
	}
}