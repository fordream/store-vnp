package org.cnc.qrcode.activity;

import org.cnc.qrcode.R;
import org.cnc.qrcode.common.Common;
import org.com.cnc.common.adnroid.CommonGPS;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;

public class LocationActivity extends Activity implements LocationListener {
	public static final String LAT = "lat";
	public static final String LONG = "log";
	private String provider = LocationManager.GPS_PROVIDER;
	private boolean isEnd = false;
	private static final int TIME = 30;
	private static final int TIME_GPS = 5;
	private LocationManager locationManager;
	LocationManager manager;

	// private ProgressDialog dialog;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String message = getResources().getString(R.string.get_gps_trans);
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		try {
			locationManager.requestLocationUpdates(provider, 20000, 1, this);
		} catch (Exception e) {
			provider = LocationManager.NETWORK_PROVIDER;
			locationManager.requestLocationUpdates(provider, 20000, 1, this);
		}
		manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			buildAlertMessageNoGps();
		} else {
			// dialog =
			ProgressDialog.show(this, "", message);
			new Check().execute("");
		}

	}

	private void buildAlertMessageNoGps() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				getResources().getString(R.string.gps_translate_check))
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								launchGPSOptions();
								// setResult(RESULT_FIRST_USER);
								// finish();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						setResult(RESULT_FIRST_USER);
						finish();
					}
				});

		builder.create().show();
	}

	private void launchGPSOptions() {
		CommonGPS.showGPSSetting(this, Common.REQUEST_0);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			buildAlertMessageNoGps();
		} else {
			// dialog =
			String message = getResources().getString(R.string.get_gps_trans);
			ProgressDialog.show(this, "", message);
			new Check().execute("");
		}
	}

	private void changle() {
		if (!provider.equals(LocationManager.NETWORK_PROVIDER)) {
			provider = LocationManager.NETWORK_PROVIDER;
			locationManager.requestLocationUpdates(provider, 20000, 1, this);
		}
	}

	private class Check extends AsyncTask<String, String, String> {
		protected String doInBackground(String... params) {
			int count = 0;
			while (count < TIME) {
				Common.sleep(1000);
				count++;
				if (count == TIME_GPS) {
					changle();
				}
			}
			return null;
		}

		protected void onPostExecute(String result) {
			if (!isEnd) {
				finish();
			}

			isEnd = true;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return true;
	}

	public void onLocationChanged(Location location) {
		if (location != null && !isEnd) {
			Common.longitude = String.valueOf(location.getLongitude());
			Common.latitude = String.valueOf(location.getLatitude());
			setResult(RESULT_OK);
			finish();
			isEnd = true;
		}
	}

	public void onProviderDisabled(String provider) {

	}

	public void onProviderEnabled(String provider) {

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {

	}
}