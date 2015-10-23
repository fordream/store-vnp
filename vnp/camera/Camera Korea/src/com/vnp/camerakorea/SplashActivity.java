package com.vnp.camerakorea;

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
	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (!isFinishing()) {
			startActivity(new Intent(this, MenuActivity.class));
			overridePendingTransition(R.anim.abc_slide_in_bottom,
					R.anim.abc_slide_out_top);
			finish();
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}
}