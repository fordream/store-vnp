package com.vnpgame.line98.splash.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;

import com.vnpgame.line98.MenuActivity;
import com.vnpgame.line98.R;
import com.vnpgame.undersea.database.DBAdapter;
import com.vnpgame.undersea.music.Music;

public class Line98Screen extends Activity {
	// private ProgressBar progressBar;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		// progressBar = getProgressBar(R.id.progressBar1);
		new MyCount().execute("");
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public class MyCount extends AsyncTask<String, String, String> {

		protected String doInBackground(String... params) {
			Music.create(Line98Screen.this);
			Music.playerBackground();
			DBAdapter adapter = new DBAdapter(Line98Screen.this);
			adapter.open();
			
			//org.com.cnc.common.Common.sleep(3000);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			startActivity(new Intent(Line98Screen.this,
					MenuActivity.class));
			finish();
		}

	}
}
