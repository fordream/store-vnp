package vn.com.vega.music.player;

import java.io.IOException;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.media.MediaPlayer.OnBufferingUpdateListener;

public class MusicPlayer extends MediaPlayer implements
		OnBufferingUpdateListener {
	private final static int MSG_PROGRESS_UPDATE = 0;
	private final static int MSG_FINISHED = 1;
	private boolean updatedPlayingTime = false;
	private int playedSeconds = 0;
	private int bufferedSeconds = 0;
	private static boolean stop = true;
	private int duration = 0; // (seconds)
	private OnMusicPlayerListener mListener = null;
	private static MusicPlayer musicPlayer = null;
	private Context context = null;
	public MediaService mBoundService;
	private boolean isBound;
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == MSG_PROGRESS_UPDATE) {

				if ((playedSeconds < (duration)) && (stop == false)) {
					playedSeconds++;
					if ((mListener != null)) {
						mListener.OnSongProgressSecondUpdate();
					}
				}

				if (playedSeconds >= duration) {
					if (mListener != null) {
						stop = true;
						unBindService();
						sendEmptyMessage(MSG_FINISHED);
					}
				} else
					sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, 1000);

			} else if (msg.what == MSG_FINISHED) {
				stop = true;
				mListener.OnSongIsFinishPlayed();

			}
		};
	};

	public static MusicPlayer getInstance() {
		return musicPlayer;
	}

	public static void stopMusicPlayer() {
		MusicPlayer _mp = MusicPlayer.getInstance();
		// Create MusicPlayer and play song
		if (_mp != null) {
			if (MusicPlayer.isMusicPlaying()) {
				_mp.reset();
			}
		}
	}

	public MusicPlayer(Context _context) {
		super();
		musicPlayer = this;
		context = _context;
		setOnBufferingUpdateListener(this);
		startSignalLevelListener();
	}

	public void play(Uri _uri) {

		if (_uri == null) {
			return;
		}

		try {
			setDataSource(context, _uri);
			prepare();

			start();

			duration = getDuration() / 1000;
			playedSeconds = 0;
			bufferedSeconds = 0;

			handler.sendEmptyMessage(MSG_PROGRESS_UPDATE);

			updatedPlayingTime = true;

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reset() {

		updatedPlayingTime = false;
		duration = 0;
		playedSeconds = 0;
		bufferedSeconds = 0;
		stop = true;
		unBindService();
		super.reset();
		handler.removeMessages(MSG_PROGRESS_UPDATE);

	}

	public void playNextSong(Uri _nextSongUri) {
		super.reset();
		updatedPlayingTime = false;
		stop = true;
		unBindService();
		handler.removeMessages(MSG_PROGRESS_UPDATE);
		play(_nextSongUri);
	}

	public void playPrevSong(Uri _prevSongUri) {
		super.reset();
		updatedPlayingTime = false;
		stop = true;
		unBindService();
		handler.removeMessages(MSG_PROGRESS_UPDATE);
		play(_prevSongUri);
	}

	public boolean isUpdated() {
		return updatedPlayingTime;
	}

	// @Override
	// public void stop() throws IllegalStateException {
	// updatedPlayingTime = false;
	// playedSeconds = 0;
	// bufferedSeconds = 0;
	// handler.removeMessages(MSG_PROGRESS_UPDATE);
	// super.stop();
	// stop = true;
	// unBindService();
	//
	// }

	@Override
	public void pause() throws IllegalStateException {
		super.pause();
		stop = true;
		unBindService();
	}

	@Override
	public void start() throws IllegalStateException {
		super.start();

		stop = false;
		bindService();

	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		int _duration = getDurationTime();
		bufferedSeconds = _duration * percent / 100;
		if (mListener != null) {
			mListener.OnSongBufferingUpdate();
		}

	}

	public void setOnMusicPlayerListener(OnMusicPlayerListener listener) {
		mListener = listener;
	}

	public int getPlayedSeconds() {
		return playedSeconds;
	}

	public int getBufferedSeconds() {
		return bufferedSeconds;
	}

	public void setPlayedSeconds(int playedSeconds) {
		this.playedSeconds = playedSeconds;
	}

	public static boolean isMusicPlaying() {
		return (stop == false);
	}

	public void seekToTime(int seconds) {
		if (0 <= seconds && seconds <= duration) {
			playedSeconds = seconds;
			seekTo(seconds * 1000);
		}

	}

	public int getDurationTime() {
		return duration;
	}

	// ------------------ BIND NOTIFICATION SERVICE ------------------------
	public void bindService() {
		Intent _service = new Intent(context, MediaService.class);
		isBound = context.bindService(_service, mConnection,
				Context.BIND_AUTO_CREATE);

	}

	public void unBindService() {
		if (isBound) {
			context.unbindService(mConnection);
			isBound = false;

		}
	}

	public ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {

			mBoundService = ((MediaService.MediaBinder) service).getService();

		}

		public void onServiceDisconnected(ComponentName className) {
			mBoundService = null;

		}
	};
	// --------------------- PHONE STATE REGISTER -----------------
	private final NowPlayingPhoneStateListener phoneStateListener = new NowPlayingPhoneStateListener();

	private void startSignalLevelListener() {

		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		int events = PhoneStateListener.LISTEN_CALL_STATE;
		tm.listen(phoneStateListener, events);
	}
	// private void stopListening(){
	// TelephonyManager tm = (TelephonyManager)
	// context.getSystemService(Context.TELEPHONY_SERVICE);
	//
	// tm.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
	// }

}
