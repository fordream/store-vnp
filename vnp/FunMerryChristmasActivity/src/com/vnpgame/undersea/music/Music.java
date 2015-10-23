package com.vnpgame.undersea.music;

import com.vnpgame.undersea.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class Music {
	public static MediaPlayer playerBackground;
	public static MediaPlayer playerMenu;
	public static MediaPlayer player;
	public static MediaPlayer playerNextLevel;
	public static MediaPlayer playerlose;
	public static boolean runSoundBack = true;
	public static boolean runSoundEffect = true;

	public static void create(Context context) {
		// runSoundBack = true;
		// runSoundEffect = true;
		init(context);
		if (playerMenu == null)
			playerMenu = MediaPlayer.create(context, R.raw.click);
		
		if (player == null)
			player = MediaPlayer.create(context, R.raw.click);
		
		if (playerNextLevel == null)
			playerNextLevel = MediaPlayer.create(context, R.raw.nextlevel);
		
		if (playerlose == null)
			playerlose = MediaPlayer.create(context, R.raw.lose);
		
		if (playerBackground == null) {
			playerBackground = MediaPlayer.create(context, R.raw.background);
			playerBackground.setLooping(true);
		}
	}

	private static void init(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"OptionXml", 0);

		runSoundBack = preferences.getBoolean("runSoundBack", true);
		runSoundEffect = preferences.getBoolean("runSoundEffect", true);
	}

	public static void setRunSoundBack(Context context, boolean b) {
		SharedPreferences preferences = context.getSharedPreferences(
				"OptionXml", 0);
		preferences.edit().putBoolean("runSoundBack", b).commit();
	}

	public static void setRunSoundEffect(Context context, boolean b) {
		SharedPreferences preferences = context.getSharedPreferences(
				"OptionXml", 0);
		preferences.edit().putBoolean("runSoundEffect", b).commit();

	}

	public static void playEffect() {
		if (runSoundEffect && player != null) {
			player.start();
		}
	}

	public static void playNextLevel() {
		if (runSoundEffect && playerNextLevel != null) {
			playerNextLevel.start();
		}

	}

	public static void playLostGame() {
		if (runSoundEffect && playerlose != null) {
			playerlose.start();
		}

	}

	public static void playerBackground() {
		if (runSoundBack && playerBackground != null) {
			playerBackground.start();
		}

	}

}