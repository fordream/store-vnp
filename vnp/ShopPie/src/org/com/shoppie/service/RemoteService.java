package org.com.shoppie.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class RemoteService extends Service {
	@Override
	public void onCreate() {
		super.onCreate();

	}

	private final IBinder mBinder = new LocalBinder();

	public class LocalBinder extends Binder {
		RemoteService getService() {
			// Return this instance of LocalService so clients can call public
			// methods
			return RemoteService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// Return the interface
		return mBinder;
	}

	public int getRandomNumber() {
		return 0;
	}

}
