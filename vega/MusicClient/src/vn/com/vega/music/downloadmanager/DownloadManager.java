package vn.com.vega.music.downloadmanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;

import java.util.Hashtable;
import java.util.List;

import vn.com.vega.music.clientserver.JsonBase;
import vn.com.vega.music.clientserver.JsonDownloadUrl;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.device.FileStorage;
import vn.com.vega.music.network.ContentDownloader;
import vn.com.vega.music.network.NetworkStatusListener;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.utils.Const;

import android.os.Handler;
import android.util.Log;

public class DownloadManager implements Runnable, NetworkStatusListener {
	private static final String LOG_TAG = Const.LOG_PREF
			+ DownloadManager.class.getSimpleName();

	private static Hashtable<String, DownloadStatusListener> sStatusListeners = new Hashtable<String, DownloadStatusListener>();
	protected static Thread mThread;
	protected static DownloadManager mDownloader;
	private static boolean lastSongError = false;

	public static boolean addDownloadStatusListener(String key,
			DownloadStatusListener value) {
		synchronized (DownloadManager.class) {
			boolean bRet = true;
			if (key == null || value == null) {
				Log.d(LOG_TAG, "addDownloadStatusListener BADPARAM");
				return false;
			}

			Log.d(LOG_TAG, "addDownloadStatusListener: adding key " + key);
			if (sStatusListeners.containsKey(key) == false) {
				sStatusListeners.put(key, value);
			} else {
				Log.d(LOG_TAG,
						"addDownloadStatusListener: the key already exists");
				bRet = false;
			}
			return bRet;
		}
	}

	public static boolean removeDownloadStatusListener(String key) {
		synchronized (DownloadManager.class) {
			if (key == null) {
				Log.d(LOG_TAG, "removeDownloadStatusListener BADPARAM");
				return false;
			}

			Log.d(LOG_TAG, "removeDownloadStatusListener: removing key " + key);
			if (sStatusListeners.remove(key) == null) {
				Log.d(LOG_TAG,
						"removeDownloadStatusListener: the specified key was not found");
			}
			return true;
		}
	}

	public static void removeAllDownloadStatusListener() {
		synchronized (DownloadManager.class) {
			Log.d(LOG_TAG, "removeAllDownloadStatusListener: removing all keys");
			sStatusListeners.clear();
			return;
		}
	}

	public static boolean isLastSongError() {
		return lastSongError;
	}

	public static boolean isRunning() {
		synchronized (DownloadManager.class) {
			if (mDownloader != null) {
				return mDownloader.mIsRunning;
			}
			return false;
		}
	}

	public static boolean isPaused() {
		synchronized (DownloadManager.class) {
			if (mDownloader != null) {
				return mDownloader.mWantPause && mDownloader.mIsRunning;
			}
			return false;
		}
	}

	public static Playlist getCurrentDownloadPlaylist() {
		synchronized (DownloadManager.class) {
			if (mDownloader != null) {
				return mDownloader.playlistToDownload;
			}
			return null;
		}
	}

	public static int getCurrentDownloadPlaylistPercent() {
		synchronized (DownloadManager.class) {
			if (mDownloader != null) {
				return mDownloader.playlistToDownloadPercent;
			}
			return 0;
		}
	}

	public static Song getCurrentDownloadSong() {
		synchronized (DownloadManager.class) {
			if (mDownloader != null) {
				return mDownloader.songToDownload;
			}
			return null;
		}
	}

	public static int getCurrentDownloadSongPercent() {
		synchronized (DownloadManager.class) {
			if (mDownloader != null) {
				return mDownloader.songToDownloadPercent;
			}
			return 0;
		}
	}

	public static int getCountDownloadedSong() {
		synchronized (DownloadManager.class) {
			if (mDownloader != null) {
				return mDownloader.allsong_downloaded;
			}
			return 0;
		}
	}

	public static int getCountAllSong() {
		synchronized (DownloadManager.class) {
			if (mDownloader != null) {
				return mDownloader.allsong_count;
			}
			return 0;
		}
	}

	public static void setCountAllSong(int numOfSong) {
		synchronized (DownloadManager.class) {
			if (mDownloader != null) {
				mDownloader.allsong_count = numOfSong
						+ mDownloader.allsong_downloaded;
			}
		}
	}

	public static void startDownload() {
		synchronized (DownloadManager.class) {
			if ((mDownloader != null) && mDownloader.mIsRunning
					&& !mDownloader.mWantStop) {
				Log.d(LOG_TAG, "startDownload: downloader is already running");
				return; // It's aready running now
			}
			mDownloader = new DownloadManager();
			mThread = new Thread(mDownloader);
			mThread.start();
		}
	}

	public static void stopDownload() {
		synchronized (DownloadManager.class) {
			if (mDownloader != null) {
				mDownloader.mWantStop = true;
			}

			// Reset ref to avoid further use
			mDownloader = null;
			mThread = null;
		}
	}

	public static void pauseDownload() {
		synchronized (DownloadManager.class) {
			if (mDownloader != null) {
				mDownloader.mWantPause = true;
			}
		}
	}

	public static void resumeDownload() {
		synchronized (DownloadManager.class) {
			if (mDownloader != null) {
				mDownloader.mWantPause = false;
			}
		}
	}

	/*****************************************************************************************
	 * 
	 * INTERNAL DOWNLOADER
	 * 
	 *****************************************************************************************/

	private boolean mIsRunning = false; // Indicate that it's downloading now
	private boolean mWantStop = false; // Indicate that it's expected to stop
										// asap
	private boolean mWantPause = false; // Indicate that it's expected to pause
										// asap
	private DataStore dataStore;

	private Hashtable<Integer, Integer> errorCount = new Hashtable<Integer, Integer>();
	private int lastDownloadErrorSongId = 0;

	private Song songToDownload = null;
	private int songToDownloadPercent = 0;
	private Playlist playlistToDownload = null;
	private int playlistToDownloadPercent = 0;

	private int playlist_downloaded = 0;
	private int allsong_count = 0;
	private int allsong_downloaded = 0;
	private int last_error = EventNotifier.ERROR_NONE;

	private Handler mHandler = new Handler(); // Need handler for callbacks to
												// the UI thread

	private DownloadManager() {
		dataStore = DataStore.getInstance();
	}

	private void pickSongToDownload(int excludeSongId) {
		songToDownload = null;

		int current = dataStore.getSongDownloadPool().size();
		allsong_count = allsong_downloaded + current;

		// Get next song to in current playlist
		if (playlistToDownload != null) {
			// Need to verify again with datastore, in case of this playlist
			// has been deleted while downloading it's song
			playlistToDownload = dataStore
					.getPlaylistByID(playlistToDownload.id);

			if (playlistToDownload != null) {
				songToDownload = playlistToDownload
						.nextSongWaitToDownload(excludeSongId);
			}
		}

		// No more song in current playlist
		if (songToDownload == null) {
			playlistToDownload = null;
			playlistToDownloadPercent = 0;

			// Try get next playlist
			List<Playlist> list = dataStore.getPlaylistDownloadPool();
			for (Playlist playlist : list) {
				Song applicant = playlist.nextSongWaitToDownload(excludeSongId);
				if (applicant != null) {
					songToDownload = applicant;
					playlistToDownload = playlist;
					playlist_downloaded = 0;
					mHandler.post(EventNotifier.createPlaylistDownloadEvent(
							sStatusListeners, playlistToDownload,
							EventNotifier.PLAYLIST_DOWNLOAD_START));
					// no need to loop anymore
					break;
				} else {
					dataStore.removePlaylistFromDownloadPool(playlist.id);
				}
			}

			// Still not have any pending playlist, look in to single song
			if (songToDownload == null) {
				List<Song> songList = dataStore.getSongDownloadPool();
				for (Song applicant : songList) {
					if (applicant.id != excludeSongId) {
						songToDownload = applicant;
					}
				}
			}
		}
	}

	private void onSongDownloaded(int error_code) {
		if (songToDownload == null) {
			Log.e(LOG_TAG, "OnSongDownloaded with song null, ec = "
					+ error_code);
			return;
		}

		// Log.e(LOGTAG, "OnSongDownloaded with song " + songToDownload.id +
		// " , ec = " + error_code);

		// Notify on download completed
		if (error_code == EventNotifier.ERROR_NONE) {
			mHandler.post(EventNotifier.createSongDownloadEvent(
					sStatusListeners, songToDownload,
					EventNotifier.SONG_DOWNLOAD_DONE));
			allsong_downloaded++;
		} else {

			lastDownloadErrorSongId = songToDownload.id;
			Integer ec = errorCount.get(lastDownloadErrorSongId);
			if (ec == null) {
				ec = 0;
			}
			ec += 1; // Increase error count

			if (ec >= 2) { // Maximum thread hold is 2 time failed
				// Remove song
				errorCount.remove(lastDownloadErrorSongId);
				dataStore.removeSongFromSongDownloadPool(songToDownload.id);
				mHandler.post(EventNotifier.createSongDownloadError(
						sStatusListeners, songToDownload, error_code));
				lastSongError = true;
			} else {
				// Skip this song, download later
				errorCount.put(lastDownloadErrorSongId, ec);
				if (error_code == EventNotifier.ERROR_SAVING_FAILED) {
					mHandler.post(EventNotifier.createSongDownloadError(
							sStatusListeners, songToDownload,
							EventNotifier.ERROR_SAVING_FAILED));
					lastSongError = true;
				} else {
					mHandler.post(EventNotifier.createSongDownloadError(
							sStatusListeners, songToDownload,
							EventNotifier.ERROR_POSTPONED));
				}
			}
		}

		if (playlistToDownload != null) {
			playlist_downloaded++;
			if (playlistToDownload.countSongWaitToDownload() == 0) {
				// Unmark playlist from download pool
				dataStore.removePlaylistFromDownloadPool(playlistToDownload.id);
				// All song is downloaded
				mHandler.post(EventNotifier.createPlaylistDownloadEvent(
						sStatusListeners, playlistToDownload,
						EventNotifier.PLAYLIST_DOWNLOAD_DONE));
			} else {
				int countAll = playlist_downloaded
						+ playlistToDownload.countSongWaitToDownload();
				mHandler.post(EventNotifier.createPlaylistDownloadProgress(
						sStatusListeners, playlistToDownload, countAll,
						playlist_downloaded));
				playlistToDownloadPercent = playlist_downloaded * 100
						/ countAll;
			}
		}
		mHandler.post(EventNotifier.createDownloadProgress(sStatusListeners,
				allsong_count, allsong_downloaded));
	}

	public void onNetworkUnavailable() {

	}

	public void onNetworkAvailable() {
		// if (NetworkUtility.getNetworkStatus() !=
		// NetworkUtility.CONNECTION_TYPE_OFF) {
		// DownloadManager.startDownload();
		// }
	}

	public void onNetworkChange() {
		// if (NetworkUtility.getNetworkStatus() !=
		// NetworkUtility.CONNECTION_TYPE_OFF) {
		// DownloadManager.startDownload();
		// }
	}

	private boolean checkQuota(long needed) {
		long avail = FileStorage.getAvaiableVolume();
		long used = FileStorage.getUsedSize();
		int percent = dataStore.getMemoryLimit();
		long quota = percent * (avail + used) / 100;
		long after = needed + used;

		if (after > quota) {
			return false;
		}
		return true;
	}

	private boolean checkDownloadVia3g() {
		boolean result = true;
		if (NetworkUtility.getNetworkStatus() == NetworkUtility.CONNECTION_TYPE_3G
				&& dataStore.isAllowingDownloadVia3g() == false) {
			result = false;
		}
		return result;
	}

	public void run() {
		// Check and start if contain song in download pool
		int total = dataStore.getSongDownloadPool().size();
		if ((total <= 0) || dataStore.isInOfflineMode()) {
			Log.d(LOG_TAG,
					"DownloadManager stopped due to offline mode or list empty ("
							+ total + " - " + dataStore.isInOfflineMode() + ")");
			return;
		}

		synchronized (DownloadManager.class) {
			if (this != mDownloader) {
				return; // Check to avoid two concurrent downloader running at
						// the same time
			}
			mIsRunning = true;
		}
		Log.d(LOG_TAG, "Downloader thread is starting ...");

		playlistToDownload = null;
		playlistToDownloadPercent = 0;
		songToDownload = null;
		songToDownloadPercent = 0;

		mHandler.post(EventNotifier.createDownloadEvent(sStatusListeners,
				EventNotifier.DOWNLOAD_START, total));

		while (!mWantStop) {
			// Step prepare: check mode
			if (dataStore.isInOfflineMode()) {
				mIsRunning = false;
				Log.d(LOG_TAG, "Offline Mode found, stop downloading");
				break;
			}

			int netStatus = NetworkUtility.getNetworkStatus();
			int counter = 0;
			while (!mWantStop
					&& (netStatus == NetworkUtility.CONNECTION_TYPE_OFF)
					&& (counter < 60)) {
				try {
					Thread.sleep(50);
				} catch (Throwable t) {
				}
				counter++;
				netStatus = NetworkUtility.getNetworkStatus();
			}

			if (mWantStop || dataStore.isInOfflineMode()
					|| checkDownloadVia3g() == false
					|| (netStatus == NetworkUtility.CONNECTION_TYPE_OFF)) {
				mIsRunning = false;
				Log.d(LOG_TAG,
						"Want to stop or in offline mode or no network or not allow download via 3g while using 3g network, stop downloading");
				break;
			}

			// Pick next song
			pickSongToDownload(lastDownloadErrorSongId);
			if ((songToDownload == null) && (lastDownloadErrorSongId > 0)) {
				lastDownloadErrorSongId = 0;
				pickSongToDownload(lastDownloadErrorSongId);
			}

			// No more song, exit thread
			if (songToDownload == null) {
				synchronized (DownloadManager.class) {
					Log.d(LOG_TAG, "Queue empty, downloader exit now ...");
					mIsRunning = false;
					break; // Break due to no more song to download
				}
			}

			// Franky here
			if (songToDownload.isAvailableLocally()) {
				dataStore.removeSongFromSongDownloadPool(songToDownload.id);
				onSongDownloaded(EventNotifier.ERROR_NONE);
				continue;
			}
			songToDownloadPercent = 0;
			lastSongError = false;

			Log.d(LOG_TAG, "Next song to download: " + songToDownload.id
					+ " >> " + songToDownload.title);

			mHandler.post(EventNotifier.createSongDownloadEvent(
					sStatusListeners, songToDownload,
					EventNotifier.SONG_DOWNLOAD_START));

			// Step 2: call clientserver to get download_url for this
			// song
			String audioProfileId = dataStore.getConfig(Const.MUSIC_QUALITY_ID);
			JsonDownloadUrl jsonDownloadUrl = JsonDownloadUrl.loadDownloadUrl(
					songToDownload.id, audioProfileId);
			if (!jsonDownloadUrl.isSuccess()) {
				if (jsonDownloadUrl.getErrorCode() == JsonBase.ERROR_POWER_EXPIRE) {
					mIsRunning = false;
					break;
				} else {
					Log.e(LOG_TAG, "Query download url error "
							+ jsonDownloadUrl.getErrorMessage());
					// Remove it from download pool
					onSongDownloaded(EventNotifier.ERROR_URL_NOT_FOUND);
					continue;
				}

			}

			String downloadUrl = jsonDownloadUrl.downloadUrl;
			ContentDownloader cd = NetworkUtility.doDownloadContent(
					downloadUrl, null, null);
			InputStream networkInputStream = null;
			int totalSize = 0;

			// Get network stream
			try {
				networkInputStream = cd.getInputStream();
				totalSize = cd.getContentSize();
			} catch (IOException e) {
				Log.e(LOG_TAG, "Error downloading song " + songToDownload.id
						+ ", error = " + e.getClass().getName() + ", msg = "
						+ e.getMessage());
				onSongDownloaded(EventNotifier.ERROR_NETWORK_PROBLEM);
				continue;
			}

			// Check quota first
			if (!checkQuota(totalSize)) {
				try {
					networkInputStream.close();
				} catch (IOException ioe) {
				}

				Log.d(LOG_TAG, "DownloadManager stopped due to disk quota!");
				onSongDownloaded(EventNotifier.ERROR_SAVING_FAILED);
				dataStore.clearSongDownloadPool();
				dataStore.clearPlaylistDownloadPool();
				mIsRunning = false;
				break;
			}

			// Step temp: call FileStorage to get saved path for this
			// song then create output stream
			FileOutputStream localFileOutputStream = null;
			String localFilePath = null;
			File localFile = null;
			try {
				// Open file to write
				FileStorage _fileStorage = new FileStorage();
				localFilePath = _fileStorage
						.createSongCachePath(songToDownload.id);
				localFile = new File(localFilePath);
				localFileOutputStream = new FileOutputStream(localFile);
			} catch (Exception e) {
				Log.e(LOG_TAG, "Error creating file to download song "
						+ songToDownload.id + ", error = "
						+ e.getClass().getName() + ", msg = " + e.getMessage());
				if (networkInputStream != null) {
					try {
						networkInputStream.close();
					} catch (IOException ioe) {
					}
				}

				if (localFileOutputStream != null) {
					try {
						localFileOutputStream.close();
					} catch (IOException ioe) {
					}
				}
				onSongDownloaded(EventNotifier.ERROR_SAVING_FAILED);
				break;
			}

			// Step 4: loop to download and send onSongDownloadProgress
			int downloadedSize = 0;
			byte[] buffer = new byte[5120]; // create a buffer...
			int bufferLength = 0;
			int subCounter = 0;
			while (!mWantStop && (downloadedSize < totalSize)) {
				// Step prepare: check mode.
				if (dataStore.isInOfflineMode()) {
					Log.d(LOG_TAG, "Offline Mode found, stop downloading");
					last_error = EventNotifier.ERROR_NONE;
					break;
				}

				// Don't do network activity while pause
				if (mWantPause) {
					try {
						Thread.sleep(100);
					} catch (Throwable t) {
					}
					continue;
				}

				// Read
				try {
					bufferLength = networkInputStream.read(buffer);
				} catch (IOException e) {
					Log.e(LOG_TAG,
							"Error downloading: read failed, song "
									+ songToDownload.id + ", error = "
									+ e.getClass().getName() + ", msg = "
									+ e.getMessage());
					last_error = EventNotifier.ERROR_NETWORK_PROBLEM;
					break;
				}
				if (bufferLength < 0) {
					break;
				}

				// check sd card status. If sdcard is full then break
				if (!FileStorage.isExtMounted()) {
					Log.e(LOG_TAG, "Error downloading: sdcard unmounted, song "
							+ songToDownload.id);
					last_error = EventNotifier.ERROR_SAVING_FAILED;
					break;
				}

				// Save
				try {
					localFileOutputStream.write(buffer, 0, bufferLength);
				} catch (IOException e) {
					Log.e(LOG_TAG,
							"Error downloading: write failed, song "
									+ songToDownload.id + ", error = "
									+ e.getClass().getName() + ", msg = "
									+ e.getMessage());
					last_error = EventNotifier.ERROR_SAVING_FAILED;
					break;
				}
				downloadedSize += bufferLength;

				subCounter += bufferLength;
				if (subCounter > 100000) {
					// Send notification
					mHandler.post(EventNotifier.createSongDownloadProgress(
							sStatusListeners, songToDownload, totalSize,
							downloadedSize));
					subCounter = 0;
					songToDownloadPercent = downloadedSize * 100 / totalSize;

					// Sleep a while to reduce work load
					try {
						Thread.sleep(2);
					} catch (Throwable t) {
					}
				}
			}

			// Close file and stream first, seperate into two try & catch
			// because we have to close two streams
			try {
				localFileOutputStream.close();
			} catch (IOException ioe) {
			}
			try {
				networkInputStream.close();
			} catch (IOException ioe) {
			}

			if (downloadedSize >= totalSize) {
				// Log.d(LOGTAG, "Download Completed " + songToDownload.id);
				dataStore.setSongCachedPath(songToDownload.id, localFilePath);
				onSongDownloaded(EventNotifier.ERROR_NONE);
			} else {
				localFile.delete(); // Remove saved file

				// Only delete file if error when network STILL available
				// On network NOT available, download again later
				if (NetworkUtility.getNetworkStatus() == NetworkUtility.CONNECTION_TYPE_OFF) {
					onSongDownloaded(EventNotifier.ERROR_NETWORK_PROBLEM);
				} else if (last_error == EventNotifier.ERROR_NONE) {
					onSongDownloaded(EventNotifier.ERROR_NETWORK_PROBLEM);
				} else {
					onSongDownloaded(last_error);
				}

				// Stop downloader on save error
				if (last_error == EventNotifier.ERROR_SAVING_FAILED) {
					mIsRunning = false;
					break;
				}
			}

			// Sleep a while to reduce work load
			try {
				Thread.sleep(1000);
			} catch (Throwable t) {
			}
		}
		mIsRunning = false;
		playlistToDownload = null;
		playlistToDownloadPercent = 0;
		songToDownload = null;
		songToDownloadPercent = 0;

		if (dataStore.isInOfflineMode() == true)
			mHandler.post(EventNotifier.createDownloadEvent(sStatusListeners,
					EventNotifier.DOWNLOAD_IN_OFFLINE_MODE, 0));
		else if (checkDownloadVia3g() == false)
			mHandler.post(EventNotifier.createDownloadEvent(sStatusListeners,
					EventNotifier.DOWNLOAD_VIA_3G_DISABLE, 0));

		mHandler.post(EventNotifier.createDownloadEvent(sStatusListeners,
				EventNotifier.DOWNLOAD_STOPPED, 0));

		int remain = dataStore.getListDownloadedSongs().size();
		if (remain > 0) {
			NetworkUtility.addNetworkStatusListener(LOG_TAG, this);
		} else {
			NetworkUtility.removeNetworkStatusListener(LOG_TAG);
		}
	}
}
