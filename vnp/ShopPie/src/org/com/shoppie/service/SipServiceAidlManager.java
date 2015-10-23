package org.com.shoppie.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class SipServiceAidlManager {

	public void onStart(Context context) {
		Intent intent = new Intent(context, SipService.class);
		context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}

	public void onPause(Context context) {
		context.unbindService(mConnection);
	}

	IShopPieService mIRemoteService;
	private ServiceConnection mConnection = new ServiceConnection() {
		// Called when the connection with the service is established
		public void onServiceConnected(ComponentName className, IBinder service) {
			// Following the example above for an AIDL interface,
			// this gets an instance of the IRemoteInterface, which we can use
			// to call on the service
			mIRemoteService = IShopPieService.Stub.asInterface(service);
		}

		// Called when the connection with the service disconnects unexpectedly
		public void onServiceDisconnected(ComponentName className) {
			mIRemoteService = null;
		}
	};
}
