/**
 * 
 */
package com.icts.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * @author haipn
 * 
 */
public class CheckWifi {
	private final int WIFI = 1;
	private final int MOBILE = 0;
	private final int NOTCONNECT = -1;

	public CheckWifi() {
		super();
	}

	public int check(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		// connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		// connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		boolean connected = networkInfo != null && networkInfo.isAvailable()
				&& networkInfo.isConnected();
		if (connected == true) {
			if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				return MOBILE;
			} else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				return WIFI;
			}
		} else {
			return NOTCONNECT;
		}
		// return connected;
		return -1;
	}

	public boolean checkAirPlaneMode(Context context) {
		return Settings.System.getInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) == 1;
	}
}
