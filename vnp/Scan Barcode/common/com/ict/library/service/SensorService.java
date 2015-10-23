package com.ict.library.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;

@SuppressWarnings("deprecation")
public class SensorService extends Service implements SensorListener {
	public static final String SENSSOR_ACTION = "com.example.senssortest.SENSSOR_ACTION";
	public static final String SENSSOR_KEY = "SENSSOR_KEY";
	public static final String SENSSOR_VALUE = "SENSSOR_VALUE";
	private SensorManager sensorManager;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorManager.registerListener(this, SensorManager.SENSOR_ORIENTATION);
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		sensorManager.unregisterListener(this);
		super.onDestroy();
	}

	public void onAccuracyChanged(int sensor, int accuracy) {

	}

	public void onSensorChanged(int sensor, float[] values) {
		if (sensor == SensorManager.SENSOR_ORIENTATION) {

			// orientation
			// int orientation = (int) values[0];
			// Log.w(SENSSOR_KEY, values[0] + "");
			Bundle extras = new Bundle();
			
			// values[0] is z(0<=azimuth<360). 0 = North, 90 = East, 180 = South, 270 = West 
			// values[1] is x(-180<=pitch<=180), with positive values when the z-axis moves toward the y-axis. 
			// values[2] is y(-90<=roll<=90), with positive values when the z-axis moves toward the x-axis. 
			extras.putFloatArray(SENSSOR_VALUE, values);

			Intent intent = new Intent(SENSSOR_ACTION);
			intent.putExtras(extras);

			// send broadcast
			sendBroadcast(intent);
		}
	}
}
