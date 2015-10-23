package org.cnc.qrcode;

import org.cnc.qrcode.asyn.CountDown;
import org.com.cnc.common.adnroid.CommonView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class SplashScreenActivity extends Activity {
	private CountDown countDown;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen_layout);
		countDown = new CountDown(this);
		countDown.execute("");
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			countDown.stop();
		}

		return super.onKeyDown(keyCode, event);
	}

	protected void onPause() {
		super.onPause();
		countDown.stop();
		finish();
	}

	public void showWeb(View view) {
		finish();
		String url = "http://daqiri.com";
		callWeb(this, url);
	}

	private boolean callWeb(Context context, String url) {
		try {
			Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			context.startActivity(myIntent);
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
