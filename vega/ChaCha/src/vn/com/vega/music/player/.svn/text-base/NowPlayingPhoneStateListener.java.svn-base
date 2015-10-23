package vn.com.vega.music.player;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class NowPlayingPhoneStateListener extends PhoneStateListener {
	boolean pauseFlag = false;

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {

		super.onCallStateChanged(state, incomingNumber);
		MusicPlayer _musicPlayer = MusicPlayer.getInstance();

		if (_musicPlayer != null) {
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				if (pauseFlag == true) {
					_musicPlayer.start();
					pauseFlag = false;
				}
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				if (MusicPlayer.isMusicPlaying()) {
					_musicPlayer.pause();
					pauseFlag = true;
				}
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				if (MusicPlayer.isMusicPlaying()) {
					_musicPlayer.pause();
					pauseFlag = true;
				}
				break;
			}
		}
	}
}