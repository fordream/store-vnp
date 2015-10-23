package org.cnc.qrcode.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckStatusNetwork {

	private static boolean checkStatusWifi(
			ConnectivityManager connectivityManager) {
		NetworkInfo wifi = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifi != null) {
			return wifi.isConnected();
		} else
			return false;
	}

	private static boolean checkStatus3G(ConnectivityManager connectivityManager) {
		NetworkInfo mobile3G = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobile3G != null) {
			return mobile3G.isConnected();
		} else
			return false;
	}

	private static boolean checkInternetConnection(
			ConnectivityManager connectivityManager) {

		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info != null) {
			return info.isConnected();
		} else
			return false;
	}

	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (!(CheckStatusNetwork.checkInternetConnection(cm)
				|| CheckStatusNetwork.checkStatus3G(cm) || CheckStatusNetwork
					.checkStatusWifi(cm))) {
			return false;
		}
		return true;
	}
}