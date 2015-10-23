package com.vnpgame.undersea.music;

import android.content.Context;
import android.media.MediaPlayer;

import com.vnpgame.chickenmerrychristmas.R;

public class Music {
	public static MediaPlayer playerBackground;
	public static MediaPlayer playerMenu;
	public static MediaPlayer player;
	public static MediaPlayer playerNextLevel;
	public static MediaPlayer playerlose;
	public static boolean runSoundBack = true;
	public static boolean runSoundEffect = true;

	public static void create(Context context) {
		runSoundBack = true;
		runSoundEffect = true;
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