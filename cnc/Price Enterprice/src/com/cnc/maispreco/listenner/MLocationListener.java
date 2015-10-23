package com.cnc.maispreco.listenner;

import org.com.cnc.maispreco.common.Common;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MLocationListener implements LocationListener{
	public void onLocationChanged(Location location) {
		if (location != null) {
			Common.longitude = location.getLongitude();
			Common.latitude = location.getLatitude();
		}
	}

	public void onStatusChanged(String provider, int status,
			Bundle extras) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onProviderDisabled(String provider) {
	}
}
