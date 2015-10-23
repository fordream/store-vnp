package com.uberspot.a2048;

import vnp.com.m2048.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

public class SplashActivity extends Activity implements AnimationListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
		animation.setAnimationListener(this);

		findViewById(R.id.vnp_logo).startAnimation(animation);
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (!isFinishing()) {
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}
}