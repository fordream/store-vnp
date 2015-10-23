package org.com.cnc.maispreco;

import org.com.cnc.maispreco.common.Common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.cnc.maispreco.listenner.MLocationListener;
import com.cnc.maispreco.thread.MyCount;
import com.cnc.maispreco.thread.MySyn;
import com.cnc.maispreco.views.DialogCustom;
import com.cnc.maispreco.views.LocalityView;

public class PresentationScreen extends Activity {
	private ProgressDialog dialog;
	private LocationManager locationManager;
	public static final long TIME = 3000;
	public static final long DELAY = 1000;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.presentationscreen);
		LocalityView.itemChangle = null;
		Common.isOnline(this);
		Common.isDebug(this);
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		Common.tooken = null;
		LocationListener listener = new MLocationListener();
		String provider = LocationManager.GPS_PROVIDER;
		try {
			locationManager.requestLocationUpdates(provider, 0, 0, listener);
			provider = LocationManager.NETWORK_PROVIDER;
			locationManager.requestLocationUpdates(provider, 0, 0, listener);
			provider = LocationManager.NETWORK_PROVIDER;
			Location location = locationManager.getLastKnownLocation(provider);
			if (location == null) {
				provider = LocationManager.GPS_PROVIDER;
				location = locationManager.getLastKnownLocation(provider);
			}

			if (location != null) {
				Common.latitude = location.getLatitude();
				Common.longitude = location.getLongitude();
			}
		} catch (Exception e) {
		}

		provider = LocationManager.GPS_PROVIDER;
		// if(isSupportGPS())
		if (Common.isSupportGPS(this) && !locationManager.isProviderEnabled(provider)) {
			new DialogCustom(this, handler).show();
		} else {
			Resources response = getResources();
			dialog = ProgressDialog.show(PresentationScreen.this, null, response.getString(R.string.loading), true);
			new MyCount(handler, TIME, DELAY).start();
		}
	}

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Context context = PresentationScreen.this;
			if (msg.what == Common.MESSAGE_WHAT_0) {
				new MySyn(handler, PresentationScreen.this).execute("");
			} else if (msg.what == Common.MESSAGE_WHAT_1) {
				if (dialog != null) {
					dialog.dismiss();
				}

				if (msg.getData().getString("arg0") != null) {
				}
				Intent intent = new Intent(context, MaisprecoScreen.class);
				intent.putExtras(msg.getData());
				startActivity(intent);
				finish();
			} else if (msg.what == Common.MESSAGE_WHAT_2) {
				Resources resources = getResources();
				String message = resources.getString(R.string.loading);
				dialog = ProgressDialog.show(context, null, message, true);
				new MyCount(handler, TIME, DELAY).start();
			}
		}
	};

}