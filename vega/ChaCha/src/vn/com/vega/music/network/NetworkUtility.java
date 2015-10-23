/***********************************************************************
 * Module:  NetworkUtility.java
 * Author:  Administrator
 * Purpose: Defines the Class NetworkUtility
 ***********************************************************************/

package vn.com.vega.music.network;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import vn.com.vega.music.utils.Const;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkUtility {
	private static final String LOG_TAG = Const.LOG_PREF + NetworkUtility.class.getSimpleName(); 

	private static final int ACTION_NOTIFY_NONE = 0;
	private static final int ACTION_NOTIFY_CONNECTED = 1;
	private static final int ACTION_NOTIFY_DISCONNECTED = 2;
	private static final int ACTION_NOTIFY_CHANGE = 3;

	public static final int CONNECTION_TYPE_OFF = 0;
	public static final int CONNECTION_TYPE_WIFI = 1;
	public static final int CONNECTION_TYPE_3G = 2;

	private static int mCurrentNetworkStatus = CONNECTION_TYPE_OFF;
	private static int mNetworkNotify = ACTION_NOTIFY_NONE;
	private static HashMap<String, NetworkStatusListener> hmNetworkStatusListener = new HashMap<String, NetworkStatusListener>();

	private static boolean isViettel = false;
	
	public static int getNetworkStatus() {
		return mCurrentNetworkStatus;
	}

	public static void setIsViettel(boolean value){
		synchronized (NetworkUtility.class) {
			isViettel = value;
		}
	}
	
	public static boolean hasNetworkConnection(){
		return (mCurrentNetworkStatus == CONNECTION_TYPE_OFF ? false : true);
	}
	
	public static boolean getIsViettel(){
		synchronized (NetworkUtility.class) {
			return isViettel;
		}
	}
	
	/*
	 * Note: When you want to cancel request. You have to call: cd.close() to
	 * cancel listener call back.
	 */
	public static NetworkRequest doGetUrlAsync(String url, RequestStatusListener requestListener) {
		NetworkRequest cd = new NetworkRequest(url, requestListener);
		Thread th = new Thread(cd);
		th.start();

		return cd;
	}

	public static NetworkRequest doGetUrlSync(String url) {

		NetworkRequest cd = new NetworkRequest(url, null);
		Thread th = new Thread(cd);
		th.start();

		// Time out: 100ms x 100 = 10 seconds
		for (int i = 0; i < 100; i++) {
			if (cd.isFinished()) {
				break;
			}

			try {
				Thread.sleep(100);
			} catch (Throwable t) {
			}
		}
		// Kill it now
		cd.abort();

		return cd;
	}

	public static NetworkRequest doPostUrlSync(String url, String content) {
		NetworkRequest cd = new NetworkRequest(url, content, null);
		Thread th = new Thread(cd);
		th.start();

		// Time out: 100ms x 100 = 10 seconds
		for (int i = 0; i < 100; i++) {
			if (cd.isFinished()) {
				break;
			}

			try {
				Thread.sleep(100);
			} catch (Throwable t) {
			}
		}
		// Kill it now
		cd.abort();

		return cd;
	}
	
	public static NetworkRequest doPostUrlSync(String url, Bitmap content) {
		NetworkRequest cd = new NetworkRequest(url, content, null);
		Thread th = new Thread(cd);
		th.start();

		// Time out: 100ms x 100 = 10 seconds
		for (int i = 0; i < 100; i++) {
			if (cd.isFinished()) {
				break;
			}

			try {
				Thread.sleep(100);
			} catch (Throwable t) {
			}
		}
		// Kill it now
		cd.abort();

		return cd;
	}

	public static NetworkRequest doPostUrlAsync(String url, String content, RequestStatusListener requestListener) {
		NetworkRequest cd = new NetworkRequest(url, content, requestListener);
		Thread th = new Thread(cd);
		th.start();

		return cd;
	}
	
	public static NetworkRequest doPostUrlAsync(String url, Bitmap content, RequestStatusListener requestListener) {
		NetworkRequest cd = new NetworkRequest(url, content, requestListener);
		Thread th = new Thread(cd);
		th.start();

		return cd;
	}


	public static ContentDownloader doDownloadContent(String url, String content, RequestStatusListener requestListener) {
		if (requestListener != null) {
			ContentDownloader cd = new ContentDownloader(url, content, requestListener, true);
			Thread th = new Thread(cd);
			th.start();
			return cd;
		}

		ContentDownloader cd = new ContentDownloader(url, content, requestListener, true);
		cd.run();
		return cd;
	}

	public static BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			mNetworkNotify = ACTION_NOTIFY_NONE;
			// boolean noConnectivity =
			// intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,
			// false);
			String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
			Log.d(LOG_TAG, "iMuzik3G connection receiver Reason: " + reason);
			// boolean isFailover =
			// intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER,
			// false);
			NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			// NetworkInfo otherNetworkInfo = (NetworkInfo)
			// intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

			if (currentNetworkInfo == null) {
				Log.d(LOG_TAG, "BroadcastReceiver currentNetworkInfo NULL");
				return;
			}

			if (currentNetworkInfo.isConnected()) {
				if (currentNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {

					if (mCurrentNetworkStatus == CONNECTION_TYPE_3G) { // 3G ->
																		// WIFI
						mNetworkNotify = ACTION_NOTIFY_CHANGE;
						Log.d(LOG_TAG, "NETWORK CHANGE: 3G->WIFI");
					} else if (mCurrentNetworkStatus == CONNECTION_TYPE_OFF) { // OFF
																				// ->
																				// WIFI
						mNetworkNotify = ACTION_NOTIFY_CONNECTED;
						Log.d(LOG_TAG, "NETWORK CHANGE: OFF->WIFI");
					} else {
					}
					mCurrentNetworkStatus = CONNECTION_TYPE_WIFI;
					Log.d(LOG_TAG, "TYPE-WIFI");

				} else {
					if (mCurrentNetworkStatus == CONNECTION_TYPE_WIFI) {// WIFI
																		// -> 3G
						mNetworkNotify = ACTION_NOTIFY_CHANGE;
						Log.d(LOG_TAG, "NETWORK CHANGE: WIFI->3G");
					} else if (mCurrentNetworkStatus == CONNECTION_TYPE_OFF) {// OFF
																				// ->
																				// 3G
						mNetworkNotify = ACTION_NOTIFY_CONNECTED;
						Log.d(LOG_TAG, "NETWORK CHANGE: OFF->3G");
					} else {
					}
					mCurrentNetworkStatus = CONNECTION_TYPE_3G;
				}
				// do application-specific task(s) based on the current network
				// state, such
				// as enabling queuing of HTTP requests when currentNetworkInfo
				// is
				// connected etc.

			} else {
				if (mCurrentNetworkStatus != CONNECTION_TYPE_OFF) {// 3G of WIFI
																	// -> OFF
					mNetworkNotify = ACTION_NOTIFY_DISCONNECTED;
				}
				mCurrentNetworkStatus = CONNECTION_TYPE_OFF;
				Log.d(LOG_TAG, "NETWORK DISCONNECTED");
			}

			// Notify status network to registered object
			doNetworkStatusNotify();
		}
	};

	protected static void doNetworkStatusNotify() {
		Log.d(LOG_TAG, "doNetworkStatusNotify");
		if (mNetworkNotify == ACTION_NOTIFY_NONE || hmNetworkStatusListener.size() == 0) {
			return;
		}

		Set<Entry<String, NetworkStatusListener>> set = hmNetworkStatusListener.entrySet();

		Iterator<Entry<String, NetworkStatusListener>> i = set.iterator();

		while (i.hasNext()) {
			Map.Entry<String, NetworkStatusListener> entry = (Map.Entry<String, NetworkStatusListener>) i.next();
			NetworkStatusListener listener = entry.getValue();

			if (mNetworkNotify == ACTION_NOTIFY_CONNECTED) {
				listener.onNetworkAvailable();
			} else if (mNetworkNotify == ACTION_NOTIFY_DISCONNECTED) {
				listener.onNetworkUnavailable();
			} else if (mNetworkNotify == ACTION_NOTIFY_CHANGE) {
				listener.onNetworkChange();
			} else {
			}
		}
		mNetworkNotify = ACTION_NOTIFY_NONE;

		return;
	}

	/*
	 * method to be invoked to register the receiver. Should invoke onCreate
	 * function
	 */
	public static void registerNetworkNotification(Activity context) {
		context.registerReceiver(mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		return;
	}

	/*
	 * method to be invoked to unregister the receiver. Should invoke onDestroy
	 * function
	 */
	public static void unregisterNetworkNotification(Activity context) {
		context.unregisterReceiver(mConnReceiver);
		return;
	}

	public static boolean addNetworkStatusListener(String key, NetworkStatusListener value) {
		boolean bRet = true;
		if (key == null || value == null) {
			Log.d(LOG_TAG, "addNetworkStatusListener BADPARAM");
			return false;
		}

		if (hmNetworkStatusListener.containsKey(key) == false) {
			Log.d(LOG_TAG, "addNetworkStatusListener: OK");
			hmNetworkStatusListener.put(key, value);
		} else {
			Log.d(LOG_TAG, "addNetworkStatusListener: the key already exists");
			bRet = false;
		}
		return bRet;
	}

	public static boolean removeNetworkStatusListener(String key) {

		if (key == null) {
			Log.d(LOG_TAG, "removeNetworkStatusListener BADPARAM");
			return false;
		}

		if (hmNetworkStatusListener.remove(key) == null) {
			Log.d(LOG_TAG, "addNetworkStatusListener: the specified key was not found");
		}
		return true;
	}

	public static void removeAllNetworkStatusListener() {

		hmNetworkStatusListener.clear();

		return;
	}
}