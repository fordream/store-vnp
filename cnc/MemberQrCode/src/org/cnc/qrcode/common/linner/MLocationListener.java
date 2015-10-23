package org.cnc.qrcode.common.linner;

import org.cnc.qrcode.GlobalActivity;
import org.cnc.qrcode.common.Common;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;


public class MLocationListener implements LocationListener{
	private GlobalActivity activity;
	public MLocationListener(GlobalActivity activity){
		this.activity = activity;
	}
	public void onLocationChanged(Location location) {
		if (location != null) {
			Common.longitude = String.valueOf(location.getLongitude());
			Common.latitude = String.valueOf(location.getLatitude());
			activity.upSearchGPS();
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