package com.caferhythm.csn.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.caferhythm.csn.R;

public class WakeupActivity extends Activity {
	public static MediaPlayer mPlayer;
	private SoundPool soundPool;
	private int soundID;
	boolean loaded = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Window window = getWindow();

		// when set the window will cause the keyguard to be dismissed, only if
		// it
		// is not a secure lock keyguard.
		window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

		// when set as a window is being added or made visible, once the window
		// has been shown then the system will poke the power manager's
		// user activity (as if the user had woken up the device) to turn the
		// screen on.
		window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		// as long as this window is visible to the user, keep the device's
		// screen turned on and bright.
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.confirm_alarm_screen);

		mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
		mPlayer.setLooping(true);
		mPlayer.start();

	}
}
