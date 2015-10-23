/**
 * 
 */
package vn.vvn.bibook.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * @author haipn
 * 
 */
public class CheckWifi {
	public CheckWifi() {
		super();
	}

	public boolean check(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		boolean connected = networkInfo != null && networkInfo.isAvailable()
				&& networkInfo.isConnected();
		return connected;
	}

	public boolean checkAirPlaneMode(Context context) {
		return Settings.System.getInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) == 1;
	}
}
