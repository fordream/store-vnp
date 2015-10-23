package org.com.cnc.maispreco;

import org.com.cnc.maispreco.common.Common;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class MaisPrecoApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
	}

	/**
	 * location manager
	 */
	public MLocationListener getLocationListener() {
		return locationListener;
	}

	private MLocationListener locationListener;

	public class MLocationListener implements LocationListener {
		private double longitude, latitude;
		private Context context;

		public MLocationListener(Context context) {
			this.context = context;
		}

		private LocationManager getLoactionManager() {
			return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		}

		public void getLastLocation() {
			if (getLoactionManager() != null) {
				getLoactionManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
				getLoactionManager().requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
			}

			updateLocation(null);
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}

		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public void onLocationChanged(Location location) {
			updateLocation(location);
		}

		private void updateLocation(Location location) {

			if (location == null && getLoactionManager() != null) {
				location = getLoactionManager().getLastKnownLocation(LocationManager.GPS_PROVIDER);
				if (location == null) {
					location = getLoactionManager().getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				}
			}
			if (location != null) {
				longitude = location.getLongitude();
				latitude = location.getLatitude();
			}
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}
	}
}