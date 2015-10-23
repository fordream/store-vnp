package minh.app.mbook;

import minh.app.mbook.utils.MbookManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.vnp.core.activity.BaseActivity;

public class SplashActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Animation animation = new AlphaAnimation(0f, 1);
		animation.setDuration(1000l);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (!isFinishing()) {
					startActivity(new Intent(SplashActivity.this, MBook.class));
					finish();
				}
			}
		});
		getView(R.id.splash).startAnimation(animation);
	}

	@Override
	protected void onPause() {
		super.onPause();

		finish();
	}
}