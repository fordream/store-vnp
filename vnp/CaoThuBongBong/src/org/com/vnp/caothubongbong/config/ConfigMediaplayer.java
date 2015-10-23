package org.com.vnp.caothubongbong.config;

import org.com.vnp.caothubongbong.R;

import android.content.Context;
import android.media.MediaPlayer;

public class ConfigMediaplayer {
	private static MediaPlayer mediaPlayer;

	public static void createMediaplayer(Context context) {
		mediaPlayer = MediaPlayer.create(context.getApplicationContext(),
				R.raw.elbeland);
		mediaPlayer.setLooping(true);
	}

	public static void start() {
		mediaPlayer.start();
	}

	public static void stop() {
		mediaPlayer.stop();
	}

	public static void pause() {
		mediaPlayer.pause();
	}

	public static void startKill(Context context) {
		try {
			MediaPlayer.create(context, R.raw.kill).start();
		} catch (Exception e) {
		}
	}
}
