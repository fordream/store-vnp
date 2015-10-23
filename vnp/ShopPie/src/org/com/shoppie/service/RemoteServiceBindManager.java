package org.com.shoppie.service;

import org.com.shoppie.service.RemoteService.LocalBinder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class RemoteServiceBindManager {
	private Context mContext;
	private RemoteService mService;

	public RemoteServiceBindManager(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public RemoteService getMServiceRemoteService() {
		return mService;
	}

	public final void onStart() {
		Intent intent = new Intent(mContext, RemoteService.class);
		mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}

	public final void onStop() {
		if (mBound) {
			mContext.unbindService(mConnection);
		}
	}

	/** Defines callbacks for service binding, passed to bindService() */
	private boolean mBound = false;

	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			// We've bound to LocalService, cast the IBinder and get
			// LocalService instance
			LocalBinder binder = (LocalBinder) service;
			mService = binder.getService();
			mBound = true;

		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			mBound = false;
		}
	};

}