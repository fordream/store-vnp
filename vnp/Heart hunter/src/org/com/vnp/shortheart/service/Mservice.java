package org.com.vnp.shortheart.service;

import org.com.vnp.shortheart.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class Mservice extends Service {

	private MediaPlayer mediaPlayer;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (mediaPlayer == null)
			mediaPlayer = MediaPlayer.create(this, R.raw.minus);
		mediaPlayer.setLooping(true);

		mediaPlayer.start();
	}

	@Override
	public void onDestroy() {
		mediaPlayer.stop();
		mediaPlayer.release();
		super.onDestroy();
	}

}
