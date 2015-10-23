package com.cnc.maispreco.common;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckStatusNetwork {

	public static boolean checkStatusWifi(
			ConnectivityManager connectivityManager) {
		NetworkInfo wifi = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifi != null) {
			return wifi.isConnected();
		} else
			return false;
	}

	public static boolean checkStatus3G(ConnectivityManager connectivityManager) {
		NetworkInfo mobile3G = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobile3G != null) {
			return mobile3G.isConnected();
		} else
			return false;
	}

	public static boolean checkInternetConnection(
			ConnectivityManager connectivityManager) {

		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info != null) {
			return info.isConnected();
		} else
			return false;
	}
}