package org.com.vnp.shortheart;

import org.com.vnp.caothubongbong.views.SplashView;
import org.com.vnp.shortheart.database.DBAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class HeartHunterSplashScreen extends Activity {
	private SplashView view;
	private boolean canMove = true;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new SplashView(this);
		setContentView(view);
		view.configRec();
		new AsyncTask<String, String, String>() {

			protected String doInBackground(String... params) {
				new DBAdapter(HeartHunterSplashScreen.this).createDatabase();
				return null;
			}
		}.execute("");
	}

	public void update() {
		if (canMove) {
			Runnable action = new Runnable() {
				public void run() {
					finish();
					startActivity(new Intent(HeartHunterSplashScreen.this,
							MenuScreen.class));
				}
			};
			view.post(action);
		}
	}

	protected void onStop() {
		super.onStop();
		canMove = false;
		finish();
	}
}
