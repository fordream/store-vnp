package com.example.test;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.vnp.core.service.v2.CommonV2RestClientServiceObserver;

public class MAplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		CommonV2RestClientServiceObserver.getInStance().init(this);
		//bindService(new Intent(IRemoteService.class.getName()), mConnection,
		//		Context.BIND_AUTO_CREATE);

	}

	public int getID(){
		try {
			return mIRemoteService.getPid();
		} catch (RemoteException e) {
			return 0;
		}
	}

	private IRemoteService mIRemoteService;
	private ServiceConnection mConnection = new ServiceConnection() {
		// Called when the connection with the service is established
		public void onServiceConnected(ComponentName className, IBinder service) {
			// Following the example above for an AIDL interface,
			// this gets an instance of the IRemoteInterface, which we can use
			// to call on the service
			Log.e("mIRemoteService", "Service has unexpectedly Connect");
			mIRemoteService = IRemoteService.Stub.asInterface(service);
		}

		// Called when the connection with the service disconnects unexpectedly
		public void onServiceDisconnected(ComponentName className) {
			Log.e("mIRemoteService", "Service has unexpectedly disconnected");
			mIRemoteService = null;
		}
	};
}
