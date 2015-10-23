package com.vnpgame.line98;

import org.com.cnc.common.adnroid.activity.CommonActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.vnpgame.undersea.music.Music;
import com.vnpgame.undersea.option.OptionScreen;
import com.vnpgame.undersea.play.activity.Play3Screen;
import com.vnpgame.undersea.score.activity.ScoreScreen;
import com.vnpgame.undersea.services.BindingActivity;

public class MenuActivity extends CommonActivity implements OnClickListener {
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
		new AsyncTask<String, String, String>() {
			protected String doInBackground(String... params) {
				return null;
			}

		}.execute("");

		ImageView splash = (ImageView) findViewById(R.id.imageView1);
		RotateAnimation anim = new RotateAnimation(0f, 360f, 50,50);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(700);

		splash.startAnimation(anim);

	}

	protected void onRestart() {
		super.onRestart();
		if (Music.runSoundBack) {
			Music.playerBackground.start();
		}
	}

	protected void onPause() {
		super.onPause();
		if (!isBack && !isButtonClick) {
			Music.playerBackground.pause();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isBack = true;
			Music.playerBackground.pause();
		}
		return super.onKeyDown(keyCode, event);
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
}
