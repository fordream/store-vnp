package vn.com.vega.music.downloadmanager;

import java.util.Hashtable;

import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.utils.Const;
import android.R.color;
import android.util.Log;

public class EventNotifier implements Runnable {

	private static final String LOG_TAG = Const.LOG_PREF + EventNotifier.class.getSimpleName();

	protected static final int TOTAL_DOWNLOAD_PROGRESS = 0;
	protected static final int PLAYLIST_DOWNLOAD_PROGRESS = 1;
	protected static final int PLAYLIST_DOWNLOAD_START = 2;
	protected static final int PLAYLIST_DOWNLOAD_DONE = 3;
	protected static final int SONG_DOWNLOAD_PROGRESS = 4;
	protected static final int SONG_DOWNLOAD_START = 5;
	protected static final int SONG_DOWNLOAD_DONE = 6;
	protected static final int DOWNLOAD_START = 7;
	protected static final int DOWNLOAD_STOPPED = 8;
	protected static final int SONG_DOWNLOAD_ERROR = 9;
	public static final int DOWNLOAD_IN_OFFLINE_MODE = 10;
	public static final int DOWNLOAD_VIA_3G_DISABLE = 11;

	public static final int ERROR_NONE = 0;
	public static final int ERROR_SAVING_FAILED = 1;
	public static final int ERROR_URL_NOT_FOUND = 2;
	public static final int ERROR_NETWORK_PROBLEM = 3;
	public static final int ERROR_POSTPONED = 4;


	int eventType;
	Playlist playlist;
	Song song;
	Integer totalSong;
	Integer totalSongDownloaded;
	Integer totalByte;
	Integer totalByteDownloaded;
	int error_code;

	Hashtable<String, DownloadStatusListener> hashListeners;

	private EventNotifier() {
	}

	public static EventNotifier createDownloadEvent(Hashtable<String, DownloadStatusListener> lis, int event, int total) {
		EventNotifier en = new EventNotifier();
		en.eventType = event;
		en.totalSong = total;
		en.hashListeners = lis;
		return en;
	}

	public static EventNotifier createSongDownloadError(Hashtable<String, DownloadStatusListener> lis, Song sg, int _error_code) {
		EventNotifier en = new EventNotifier();
		en.eventType = SONG_DOWNLOAD_ERROR;
		en.song = sg;
		en.error_code = _error_code;
		en.hashListeners = lis;
		return en;
	}

	public static EventNotifier createSongDownloadProgress(Hashtable<String, DownloadStatusListener> lis, Song _song, int total, int downloaded) {
		EventNotifier en = new EventNotifier();
		en.eventType = SONG_DOWNLOAD_PROGRESS;
		en.song = _song;
		en.totalByte = total;
		en.totalByteDownloaded = downloaded;
		en.hashListeners = lis;
		return en;
	}

	public static EventNotifier createSongDownloadEvent(Hashtable<String, DownloadStatusListener> lis, Song _song, int event) {
		EventNotifier en = new EventNotifier();
		en.eventType = event;
		en.song = _song;
		en.hashListeners = lis;
		return en;
	}

	public static EventNotifier createPlaylistDownloadEvent(Hashtable<String, DownloadStatusListener> lis, Playlist _playlist, int event) {
		EventNotifier en = new EventNotifier();
		en.eventType = event;
		en.playlist = _playlist;
		en.hashListeners = lis;
		return en;
	}

	public static EventNotifier createPlaylistDownloadProgress(Hashtable<String, DownloadStatusListener> lis, Playlist _playlist, int _totalSong, int _songDownloaded) {
		EventNotifier en = new EventNotifier();
		en.eventType = PLAYLIST_DOWNLOAD_PROGRESS;
		en.playlist = _playlist;
		en.totalSong = _totalSong;
		en.totalSongDownloaded = _songDownloaded;
		en.hashListeners = lis;
		return en;
	}

	public static EventNotifier createDownloadProgress(Hashtable<String, DownloadStatusListener> lis, int _totalSong, int _songDownloaded) {
		EventNotifier en = new EventNotifier();
		en.eventType = TOTAL_DOWNLOAD_PROGRESS;
		en.totalSong = _totalSong;
		en.totalSongDownloaded = _songDownloaded;
		en.hashListeners = lis;
		return en;
	}

	public void run() {
		for (DownloadStatusListener listener : hashListeners.values()) {
			try {
				switch (eventType) {
				case TOTAL_DOWNLOAD_PROGRESS:
					Log.d(LOG_TAG, ">> DOWNLOAD PROGRESS " + totalSongDownloaded + " of " + totalSong + " songs.");
					listener.onDownloadProgress(totalSong, totalSongDownloaded);
					break;
				case DOWNLOAD_START:
					Log.d(LOG_TAG, ">> DOWNLOAD START total = " + totalSong);
					listener.onDownloadStart(totalSong);
					break;
				case DOWNLOAD_STOPPED:
					Log.d(LOG_TAG, ">> DOWNLOAD STOPPED");
					listener.onDownloadStopped();
					break;

				case PLAYLIST_DOWNLOAD_START:
					Log.d(LOG_TAG, ">> DOWNLOAD PLAYLIST START id=" + playlist.id);
					listener.onPlaylistDownloadStart(playlist);
					break;
				case PLAYLIST_DOWNLOAD_DONE:
					Log.d(LOG_TAG, ">> DOWNLOAD PLAYLIST DONE id=" + playlist.id);
					listener.onPlaylistDownloadDone(playlist);
					break;
				case PLAYLIST_DOWNLOAD_PROGRESS:
					Log.d(LOG_TAG, ">> DOWNLOAD PLAYLIST PROGRESS id=" + playlist.id + ": " + totalSongDownloaded + " of " + totalSong + " songs.");
					listener.onPlaylistDownloadProgress(playlist, totalSong, totalSongDownloaded);
					break;

				case SONG_DOWNLOAD_START:
					Log.d(LOG_TAG, ">> DOWNLOAD SONG START id=" + song.id);
					listener.onSongDownloadStart(song);
					break;
				case SONG_DOWNLOAD_DONE:
					Log.d(LOG_TAG, ">> DOWNLOAD SONG DONE id=" + song.id);
					listener.onSongDownloadDone(song);
					break;
				case SONG_DOWNLOAD_PROGRESS:
					// Log.d(LOGTAG, ">> DOWNLOAD SONG PROGRESS id=" + song.id +
					// ": " + totalByteDownloaded + " of "
					// + totalByte + " bytes.");
					listener.onSongDownloadProgress(song, totalByte, totalByteDownloaded);
					break;
				case SONG_DOWNLOAD_ERROR:
					Log.d(LOG_TAG, ">> DOWNLOAD SONG ERROR " + error_code);
					listener.onSongDownloadError(song, error_code);
					break;
				case DOWNLOAD_IN_OFFLINE_MODE:
					listener.onDownloadInOfflineMode();
					break;
				case DOWNLOAD_VIA_3G_DISABLE:
					listener.onDownloadVia3gDisable();
					break;
				}
			} catch (Throwable t) {
				// Avoid any crash from listener affect to sender
				continue;
			}
		}
	}
}
