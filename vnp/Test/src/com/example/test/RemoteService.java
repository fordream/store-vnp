package com.example.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class RemoteService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// Return the interface
		return mBinder;
	}

	private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
		public int getPid() {
			return 1;
		}

		@Override
		public void basicTypes() throws RemoteException {
			
			
		}
	};
}