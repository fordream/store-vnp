package minh.app.mbook.service;

import minh.app.mbook.model.LoadContentZipCallBack;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.vnp.core.callback.ExeCallBack;

public class MbookService extends Service {
	private final IBinder mBinder = new LocalBinder();
	private LoadContentZipCallBack loadContentCallBack;

	public class LocalBinder extends Binder {
		public MbookService getService() {
			return MbookService.this;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		com.vnp.core.datastore.DataStore.getInstance().init(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		// get get content to update
		if (loadContentCallBack == null) {
			loadContentCallBack = new LoadContentZipCallBack(this);
		}

		if (!loadContentCallBack.isConnectting()) {
			new ExeCallBack().executeAsynCallBack(loadContentCallBack);
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
}