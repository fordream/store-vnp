package com.vnpgame.undersea;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.vnpgame.undersea.music.Music;
import com.vnpgame.undersea.option.OptionScreen;
import com.vnpgame.undersea.play.activity.Play3Screen;
import com.vnpgame.undersea.score.activity.ScoreScreen;
import com.vnpgame.undersea.services.BindingActivity;

public class FunMerryChristmasActivity extends Activity implements
		OnClickListener {
	boolean isBack = false;
	boolean isButtonClick = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.btnExit).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button3).setOnClickListener(this);
		findViewById(R.id.button4).setOnClickListener(this);
		findViewById(R.id.button5).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri
						.parse("market://search?q=pub:Truong Vuong Van"));
				startActivity(intent);
			}
		});

		new AsyncTask<String, String, String>() {
			protected String doInBackground(String... params) {
				Music.create(FunMerryChristmasActivity.this);
				
				Music.playerBackground();
				
				return null;
			}
		}.execute("");

		String adUnitId = "a14edad3d74598a";
		AdView adView = new AdView(this, AdSize.BANNER, adUnitId);
		AdRequest request = new AdRequest();
		request.setTesting(true);
		adView.loadAd(request);
		((LinearLayout) findViewById(R.id.linearLayout2)).addView(adView);
	}

	protected void onRestart() {
		super.onRestart();
		if (Music.runSoundBack) {
			Music.playerBackground.start();
		}
	}

	protected void onPause() {
		super.onPause();
		if (!isBack && !isButtonClick && Music.playerBackground != null) {
			Music.playerBackground.pause();
		}
	}

	public void onClick(View arg0) {
		isButtonClick = true;
		if (arg0.getId() == R.id.button1) {
			Intent intent = new Intent(this, Play3Screen.class);
			startActivityForResult(intent, 0);
		} else if (arg0.getId() == R.id.btnExit) {
			Music.playerBackground.pause();
			finish();
		} else if (arg0.getId() == R.id.button2) {
			startActivityForResult(new Intent(this, ScoreScreen.class), 0);
		} else if (arg0.getId() == R.id.button3) {
			startActivityForResult(new Intent(this, OptionScreen.class), 0);
		} else if (arg0.getId() == R.id.button4) {
			startActivityForResult(new Intent(this, BindingActivity.class), 0);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		isButtonClick = false;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isBack = true;
			Music.playerBackground.pause();
		}
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_HOME) {
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
}
