package org.com.vnp.thioi;

import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;

import com.ict.library.activity.CommonBaseActivity;
import com.ict.library.common.CommonResize;
import com.ict.library.database.CommonDataStore;
import org.com.vnp.thioi2.R;

public class SplashActivity extends CommonBaseActivity {
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		CommonResize.resizeLandW480H320(findViewById(R.id.idMain), 480, 320);
		CommonDataStore.getInstance().init(this);
		autoCreateShortCut(
				CommonDataStore.getInstance().get("createShortCut", true), this);
		CommonDataStore.getInstance().save("createShortCut", false);

		// AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
		// alphaAnimation.setDuration(1000 * 3);
		// alphaAnimation.setAnimationListener(new AnimationListener() {
		//
		// @Override
		// public void onAnimationStart(Animation animation) {
		//
		// }
		//
		// @Override
		// public void onAnimationRepeat(Animation animation) {
		//
		// }
		//
		// @Override
		// public void onAnimationEnd(Animation animation) {
		// if (!isFinishing()) {
		// startActivity(new Intent(SplashActivity.this,
		// MainActivity.class));
		// finish();
		//
		//
		// }
		// }
		// });
		// Animation animation = AnimationUtils.loadAnimation(this,
		// R.anim.anim_splash);
		AnimationDrawable animation = (AnimationDrawable) findViewById(
				R.id.imageView1).getBackground();
		// findViewById(R.id.imageView1).startAnimation(animation);
		animation.start();
		// startService(new Intent(this, MediaService.class));

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (!isFinishing()) {
					startActivity(new Intent(SplashActivity.this,
							MainActivity.class));
					finish();
				}
			}
		}, 1000 * 3);
	}

	@Override
	protected void onStop() {
		finish();
		super.onStop();
	}

	private void autoCreateShortCut(boolean hasShorcut, Context context) {
		if (hasShorcut) {
			Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
			shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			final Class<?> cls = SplashActivity.class;
			shortcutIntent.setClass(context, cls);

			Intent intentShortcut = new Intent();
			intentShortcut.putExtra("android.intent.extra.shortcut.INTENT",
					shortcutIntent);
			intentShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
					getString(R.string.app_name));
			ShortcutIconResource icon = Intent.ShortcutIconResource
					.fromContext(context, R.drawable.icon);
			intentShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
			intentShortcut
					.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
			intentShortcut.putExtra("duplicate", false);
			context.sendBroadcast(intentShortcut);
		}
	}
}