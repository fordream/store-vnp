package org.com.vnp.lmhtmanager.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class LMHTServiceManager implements ServiceConnection {
	private static LMHTServiceManager lmhtServiceManager = new LMHTServiceManager();
	private LMHTService lmhtService;

	public LMHTService getLmhtService() {
		return lmhtService;
	}

	private LMHTServiceManagerCallBack serviceManagerCallBack;

	public void setServiceManagerCallBack(
			LMHTServiceManagerCallBack serviceManagerCallBack) {
		this.serviceManagerCallBack = serviceManagerCallBack;
	}

	public static LMHTServiceManager getInstance() {
		return lmhtServiceManager;
	}

	public void init(Context context) {
		String action = "org.com.vnp.lmhtmanager.service.LMHTService";
		if (lmhtService == null) {
			context.bindService(new Intent(action), this,
					Context.BIND_AUTO_CREATE);
		} else {
			if (serviceManagerCallBack != null) {
				serviceManagerCallBack.initServiceConnected();
			}
		}

	}

	private LMHTServiceManager() {
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		lmhtService = ((LMHTBinder) service).getLmhtService();
		if (serviceManagerCallBack != null) {
			serviceManagerCallBack.initServiceConnected();
		}

	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		lmhtService = null;
		if (serviceManagerCallBack != null) {
			serviceManagerCallBack.initServiceDisconnected();
		}
	}

	public interface LMHTServiceManagerCallBack {
		public void initServiceConnected();

		public void initServiceDisconnected();

		public void onProgess(String string);

		public void onProgessSuccess(String string);
	}

	public void loadContent() {
		getLmhtService().loadContent(serviceManagerCallBack);
	}
}
