package org.vnp.vas.controller;

import org.vnp.vas.R;
import org.vnp.vas.model.Login;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class SplashActivity extends Activity implements AnimationListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		View view = new View(this);

		AlphaAnimation animation = new AlphaAnimation(0f, 1f);
		animation.setDuration(2 * 1000);
		animation.setAnimationListener(this);
		view.setBackgroundResource(R.drawable.ic_launcher);
		setContentView(view);
		view.startAnimation(animation);
	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// check activity is alive
		if (!isFinishing()) {
			// 1. check register
			Login login = new Login();
			if (login.isLogin()) {
				// 1.1 go to menu screen
				startActivity(new Intent(this, MainActivity.class));
			} else {
				// 1.2 goto Login Screen
				startActivity(new Intent(this, LoginActivity.class));
			}
			// 2. end activity
			finish();
		}
	}
}