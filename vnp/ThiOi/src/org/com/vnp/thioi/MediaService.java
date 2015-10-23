package org.com.vnp.thioi;

import com.ict.library.common.CommonScreenAction;
import org.com.vnp.thioi2.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MediaService extends Service {
	private MediaPlayer mediaPlayer;
	private boolean isCanPlay = true;

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
		mediaPlayer = MediaPlayer.create(this, R.raw.login_theme);
		mediaPlayer.setLooping(true);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setScreenOnWhilePlaying(false);
		// if (!mediaPlayer.isPlaying()) {
		mediaPlayer.start();
		// }
		CommonScreenAction action = new CommonScreenAction(this) {

			@Override
			public void screenOn() {
				isCanPlay = true;
			}

			@Override
			public void screenOff() {
				isCanPlay = false;
				mediaPlayer.pause();
			}

			@Override
			public void screenOnHaveLock() {

			}

			@Override
			public void screenUnlock() {
				isCanPlay = true;
			}
		};

		action.register();
		new Thread() {
			public void run() {
				while (!isRestricted()) {
					if (!checkApplicationRunning(MediaService.this)) {
						if (mediaPlayer.isPlaying()) {
							mediaPlayer.pause();
						}
					} else {
						if (!mediaPlayer.isPlaying() && isCanPlay) {
							mediaPlayer.start();
						}
					}
				}
			};
		}.start();

		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public boolean checkApplicationRunning(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Activity.ACTIVITY_SERVICE);
		String packageName = am.getRunningTasks(1).get(0).topActivity
				.getPackageName();
		if (packageName.equalsIgnoreCase(context.getPackageName())) {
			return true;
		}
		return false;
	}
}