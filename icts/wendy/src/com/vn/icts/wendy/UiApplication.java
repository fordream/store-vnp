package com.vn.icts.wendy;

import java.util.List;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.ict.library.database.DataStore;
import com.ict.library.service.LocationParacelable;
import com.ict.library.service.LocationService;
import com.ict.library.service.SensorService;
import com.vn.icts.wendy.async.WendyAsyn;
import com.vn.icts.wendy.async.WendyAsyn.CallBack;

/**
 * 
 * @author tvuong1pc
 * 
 */
public class UiApplication extends Application {
	private LocationParacelable location;
	private BroadcastReceiver broadcastReceiverLocation = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			location = intent.getExtras().getParcelable(
					LocationService.LOCATION_VALUE);
		}
	};

	/**
	 * start when open app
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		// init DataStore
		DataStore.getInstance().init(this);

		// start service for sensor
		Intent intent = new Intent(this, SensorService.class);
		startService(intent);

		// start service Location
		Intent intentLocation = new Intent(this, LocationService.class);
		startService(intentLocation);
		// register for location
		IntentFilter filter = new IntentFilter(LocationService.LOCATION_ACTION);
		registerReceiver(broadcastReceiverLocation, filter);

		// --------------------------------------------------
		// C2DM
		// --------------------------------------------------
		// GCMRegistrar.checkDevice(this);
		// GCMRegistrar.checkManifest(this);
		// final String regId = GCMRegistrar.getRegistrationId(this);
		// if (regId.equals("")) {
		// GCMRegistrar.register(this, "1077349026867");
		// Log.e("C2DM", "Registered");
		// } else {
		// Log.e("C2DM", "Already registered");
		// }
	}

	public LocationParacelable getLocation() {
		return location;
	}
}