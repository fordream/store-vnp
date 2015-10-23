package org.com.vnp.shortheart.service;

import org.com.vnp.shortheart.R;

import android.app.Application;
import android.media.MediaPlayer;

public class Mapplication extends Application {
	private MediaPlayer mediaPlayer;

	@Override
	public void onCreate() {
		super.onCreate();
		//mediaPlayer = MediaPlayer.create(this, R.raw.minus);
		//mediaPlayer.setLooping(true);
		// mediaPlayer.start();
		//startThread();
	}

	private void startThread() {
		new Thread() {
			public void run() {
				while (!isRestricted()) {
					if (!mediaPlayer.isPlaying()) {
					//	mediaPlayer.start();
					}else{
						//mediaPlayer.pause();
					}
				}
			};
		}.start();
	}

}