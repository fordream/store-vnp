package vn.com.vega.music.player;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import vn.com.vega.chacha.R;
import vn.com.vega.music.view.NowPlayingActivity;

public class MediaService extends Service {

	private NotificationManager mNM;
	public static String mTitleofSong;

	public class MediaBinder extends Binder {
		public MediaService getService() {
			return MediaService.this;
		}
	}

	@Override
	public void onCreate() {
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		// Display a notification about us starting. We put an icon in the
		// status bar.
		showNotification();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		// Cancel the persistent notification.
		mNM.cancel(R.string.media_service_started);

		// Tell the user we stopped.
		// Toast.makeText(this, R.string.local_service_stopped,
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	// This is the object that receives interactions from clients. See
	// RemoteService for a more complete example.
	private final IBinder mBinder = new MediaBinder();

	/**
	 * Show a notification while this service is running.
	 */
	private void showNotification() {
		// In this sample, we'll use the same text for the ticker and the
		// expanded notification
		// CharSequence text = getText(R.string.media_service_started);
		CharSequence text = String.valueOf(mTitleofSong);

		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(
				R.drawable.ic_player_playing_notify, text,
				System.currentTimeMillis());

		// Add this to the flags field to group the notification under the
		// "Ongoing" title in the notifications window.
		// This indicates that the application is on-going — its processes are
		// still running in the background,
		// even when the application is not visible (such as with music or a
		// phone call).
		notification.flags |= Notification.FLAG_ONGOING_EVENT;

		// The PendingIntent to launch our activity if the user selects this
		// notification
		Intent _i = new Intent(getApplicationContext(),
				NowPlayingActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, _i, 0);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this, getString(R.string.app_name),
				text, contentIntent);

		// Send the notification.
		// We use a layout id because it is a unique number. We use it later to
		// cancel.
		mNM.notify(R.string.media_service_started, notification);
	}

}
