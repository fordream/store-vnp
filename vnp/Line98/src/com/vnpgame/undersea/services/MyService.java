package com.vnpgame.undersea.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
	int mStartMode; // indicates how to behave if the service is killed
	IBinder mBinder; // interface for clients that bind
	boolean mAllowRebind; // indicates whether onRebind should be used

	public void onCreate() {
		// The service is being created
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		// The service is starting, due to a call to startService()
		return mStartMode;
	}

	public IBinder onBind(Intent intent) {
		// A client is binding to the service with bindService()
		return mBinder;
	}

	public boolean onUnbind(Intent intent) {
		// All clients have unbound with unbindService()
		return mAllowRebind;
	}

	public void onRebind(Intent intent) {
		// A client is binding to the service with bindService(),
		// after onUnbind() has already been called
	}

	public void onDestroy() {
		// The service is no longer used and is being destroyed
	}
}