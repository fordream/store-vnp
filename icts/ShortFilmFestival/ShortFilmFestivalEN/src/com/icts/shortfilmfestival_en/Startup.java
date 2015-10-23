package com.icts.shortfilmfestival_en;

import com.vnp.shortfilmfestival.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Startup extends Activity {
	private static final String TAG = "LOG_STARTUP";
	private ImageView mSlashImageView;
	private Animation mAnimationSlash;

	
	public void onCreate(Bundle savedInstanceState) {
		// super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup);
		mSlashImageView = (ImageView) findViewById(R.id.splash_id);
		mAnimationSlash = AnimationUtils.loadAnimation(this,
				R.anim.fadeoutslash);
	}

	
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "LOG_ONRESUME");
		mSlashImageView.startAnimation(mAnimationSlash);
		mAnimationSlash.setAnimationListener(new AnimationListener() {

			
			public void onAnimationStart(Animation animation) {

			}

			
			public void onAnimationRepeat(Animation animation) {

			}

			
			public void onAnimationEnd(Animation animation) {
				mSlashImageView.setBackgroundResource(0);
				Intent mIntentShortFilm = new Intent();
				mIntentShortFilm.setClass(getBaseContext(),
						com.icts.shortfilmfestival_en.MainTabActivity.class);
				startActivity(mIntentShortFilm);
				finish();
			}
		});

	}

	
	protected void onDestroy() {
		Log.d(TAG, "LOG_ONDESTROY");
		System.gc();
		super.onDestroy();
		
		mSlashImageView.setBackgroundResource(0);
		mSlashImageView.clearAnimation();
		mSlashImageView = null;

	}
}
