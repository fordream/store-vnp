package org.com.vnp.lmhtmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceBroadcastReceiver extends BroadcastReceiver {
	private Context mContext;

	public ServiceBroadcastReceiver(Context context) {
		mContext = context;
	}

	@Override
	public void onReceive(Context context, Intent intent) {

	}

}
