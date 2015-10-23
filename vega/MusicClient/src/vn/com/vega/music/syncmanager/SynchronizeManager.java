package vn.com.vega.music.syncmanager;

import java.util.HashMap;

import vn.com.vega.music.clientserver.JsonSync;
import vn.com.vega.music.clientserver.SyncDownstreamPackage;
import vn.com.vega.music.clientserver.SyncUpstreamPackage;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.utils.Const;
import android.os.Handler;
import android.util.Log;

public class SynchronizeManager implements Runnable {

	private static final String LOG_TAG = Const.LOG_PREF + SynchronizeManager.class.getSimpleName();
	private static HashMap<String, SyncStatusListener> hmSyncStatusListener = new HashMap<String, SyncStatusListener>();
	private static SynchronizeManager sBackgroundWorker = null;
	private static Object sLock = new Object();

	private DataStore dataStore;

	private SynchronizeManager() {
		dataStore = DataStore.getInstance();
	}

	public static void startSyncThread() {
		synchronized (sLock) {
			if ((sBackgroundWorker == null) || (!sBackgroundWorker.running())) {
				sBackgroundWorker = new SynchronizeManager();
				Thread th = new Thread(sBackgroundWorker);
				th.start();
			}
		}
	}

	public static void stopSyncThread() {
		synchronized (sLock) {
			if ((sBackgroundWorker != null) && sBackgroundWorker.running()) {
				sBackgroundWorker.abort();
				sBackgroundWorker = null;
			}
		}
	}

	public static boolean isRunning() {
		synchronized (sLock) {
			if ((sBackgroundWorker != null) && sBackgroundWorker.running()) {
				return true;
			}
			return false;
		}
	}

	/*******************************************************************************
	 * 
	 * LISTENERS
	 * 
	 *******************************************************************************/
	private static Object sListenerLock = new Object();

	public static boolean addSyncStatusListener(String key, SyncStatusListener value) {
		boolean bRet = true;
		if (key == null || value == null) {
			Log.d(LOG_TAG, "addSyncStatusListener BADPARAM");
			return false;
		}

		synchronized (sListenerLock) {
			if (hmSyncStatusListener.containsKey(key) == false) {
				hmSyncStatusListener.put(key, value);
			} else {
				Log.d(LOG_TAG, "addSyncStatusListener: the key already exists");
				bRet = false;
			}
		}
		return bRet;
	}

	public static boolean removeSyncStatusListener(String key) {

		if (key == null) {
			Log.d(LOG_TAG, "removeSyncStatusListener BADPARAM");
			return false;
		}

		synchronized (sListenerLock) {

			if (hmSyncStatusListener.remove(key) == null) {
				Log.d(LOG_TAG, "removeSyncStatusListener: the specified key was not found");
			}
		}
		return true;
	}

	public static void removeAllSyncStatusListener() {
		synchronized (sListenerLock) {
			hmSyncStatusListener.clear();
		}
	}

	/*******************************************************************************
	 * 
	 * INTERNAL FUNCTIONS
	 * 
	 *******************************************************************************/

	private boolean aborted = false; // Cancel request
	private boolean isRunning = false; // Is thread running

	/**
	 * Mark to abort this thread
	 */
	private void abort() {
		synchronized (this) {
			aborted = true;
		}
	}

	/**
	 * Check if this thread is running
	 * 
	 * @return
	 */
	private boolean running() {
		synchronized (this) {
			return isRunning;
		}
	}

	@Override
	public void run() {

		synchronized (this) {
			isRunning = true;
		}

		try {
			while (true) {
				synchronized (this) {
					// check offline mode // true -> online; false -> offline
					if (dataStore.isInOfflineMode()) {
						isRunning = false;
						break;
					}

					if (aborted) {
						isRunning = false;
						break;
					}
				}
				// Step 1: send onSyncStart
				// this is not main UI thread so to update interface i should
				// use Handler to pass information back to
				// main UI thread
				// doSyncStatusNotify(true); // true -> start; false -> done //
				// old code
				mHandler.post(mBeginUpdateResults);

				// Step 2: get syncUpStreamPackage from DataStore
				SyncUpstreamPackage syncUpstreamPackage = dataStore.generateUpstreamPackage();

				// Send json and merge response to data store
				JsonSync js = JsonSync.doSync(syncUpstreamPackage);
				// Check Json result before perform merge

				if (js.isSuccess()) {
					// Only do this if is.isSuccess
					SyncDownstreamPackage syncDownstreamPackage = js.aDownstreamPackage;
					// Step 6: update new data
					dataStore.mergeDownstreamPackage(syncDownstreamPackage);
				} else {
					// On failed, revert it
					dataStore.revertSync(syncUpstreamPackage);
				}

				// Step 7: send onSyncDone
				// this is not main UI thread so will throw an exception if i
				// want to update interface here. I should
				// use Handler to pass information back to main thread
				// doSyncStatusNotify(false); // true -> start; false -> done //
				// old code
				mHandler.post(mEndUpdateResults);

				// Step 8: Sleep a moment for next query
				// Wrap it in try/catch thread to avoid exception throw to
				// higher try/catch
				try {
					Thread.sleep(30000); // 60 x 1000 = 60 seconds
				} catch (Throwable t) {
				}
			}
		} catch (Exception ex) {
			synchronized (this) {
				isRunning = false;
			}

			Log.d(LOG_TAG, "Error while synchronizing. Message: " + ex.getMessage());
		}
	}

	private static final boolean SYNC_BEGIN = true;
	private static final boolean SYNC_END = false;

	protected void doSyncStatusNotify(boolean flag) {

		// Hoai Ngo: be mind, this class is use for multiple thread, so be
		// thread-safe

		Log.d(LOG_TAG, "doSyncStatusNotify");

		// Hoai Ngo: We have to make a shadow copy here to avoid modify while
		// emumurating collection
		HashMap<String, SyncStatusListener> copy = null;
		synchronized (sListenerLock) {
			copy = new HashMap<String, SyncStatusListener>();
			copy.putAll(hmSyncStatusListener);
		}

		// Enumurate and send notifications
		for (SyncStatusListener listener : copy.values()) {
			try {
				if (flag == SYNC_BEGIN) // flag = true -> start; flag = false ->
										// done
					listener.onSyncStart();
				else
					listener.onSyncDone();

			} catch (Throwable t) {
				// Use try/catch to avoid crash from user code affect to sync
				// thread
				
			}
		}
	}

	// Need handler for callbacks to the UI thread
	final Handler mHandler = new Handler();

	// Create runnable for posting
	final Runnable mBeginUpdateResults = new Runnable() {
		public void run() {
			doSyncStatusNotify(SYNC_BEGIN);
		}
	};
	final Runnable mEndUpdateResults = new Runnable() {
		public void run() {
			doSyncStatusNotify(SYNC_END);
		}
	};
}
