package org.com.shoppie.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.RemoteException;

public class SipService extends Service {
	private final IShopPieService.Stub mBinder = new IShopPieService.Stub() {

		@Override
		public int getPid() throws RemoteException {
			return 0;
		}

		@Override
		public void basicTypes(int anInt, long aLong, boolean aBoolean,
				float aFloat, double aDouble, String aString)
				throws RemoteException {
			
		}

		@Override
		public void startWithAccount(String account, String statusAccount,
				String passwrod) throws RemoteException {
			
		}

		@Override
		public void logout() throws RemoteException {
			
		}

		@Override
		public void makeCall(String calle) throws RemoteException {
			
		}

		@Override
		public void makeKey(int idCall, String key) throws RemoteException {
			
		}

		@Override
		public void forward(int callId, String forwardNumber)
				throws RemoteException {
			
		}

		@Override
		public void transferNumber(int callId, String number)
				throws RemoteException {
			
		}

		@Override
		public Rect example() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}
	};
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

}