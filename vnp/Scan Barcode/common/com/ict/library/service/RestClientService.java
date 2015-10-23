package com.ict.library.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

/**
 * class for call api by RestClient send to service : Bundle send Broadcast with
 * action com.example.senssortest.REST_ACTION
 * 
 * @author tvuong1pc
 * 
 */

public class RestClientService extends Service {
	public static final void startService(final Context context,
			final Bundle extras) {
		Intent intent = new Intent(context, RestClientService.class);
		// intent.putExtra(REST_METHOD, value)
		context.startService(intent);
	}

	public static final String REST_ACTION = "com.example.senssortest.REST_ACTION";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	// start service for call a api
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		return super.onStartCommand(intent, flags, startId);
	}
}