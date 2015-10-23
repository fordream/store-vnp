package org.vnp.vas.controller.service;

import org.vnp.vas.model.Login;

import com.ict.library.anetwork.CommonNetwork;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class VasService extends Service {
	public static void startService(Bundle extras, Context context,
			String actionResponse) {

		if (extras == null) {
			extras = new Bundle();
		}

		Intent intent = new Intent(context, VasService.class);
		extras.putString("actionResponse", actionResponse);

		intent.putExtras(extras);
		context.startService(intent);
	}

	public static void addStatus(Bundle extras, VasServiceStuas status) {
		if (status == VasServiceStuas.FAIL) {
			extras.putInt("status", 0);
		} else if (status == VasServiceStuas.SUCCESS) {
			extras.putInt("status", 1);
		} else if (status == VasServiceStuas.CHECKNETWORK) {
			extras.putInt("status", 2);
		} else if (status == VasServiceStuas.TIMEOUT) {
			extras.putInt("status", 3);
		}
	}

	public static VasServiceStuas getVasStatus(Bundle extras) {
		int status = extras.getInt("status", 0);
		if (status == 0) {
			return VasServiceStuas.FAIL;
		} else if (status == 1) {
			return VasServiceStuas.SUCCESS;
		} else if (status == 2) {
			return VasServiceStuas.CHECKNETWORK;
		} else if (status == 3) {
			return VasServiceStuas.TIMEOUT;
		}
		return VasServiceStuas.FAIL;
	}

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		String actionResponse = intent.getExtras().getString("actionResponse");
		// check network
		if (!CommonNetwork.haveConnectTed(this)) {
			responseData(actionResponse, null, VasServiceStuas.CHECKNETWORK);
		} else if (actionResponse.equals("login")) {
			login(actionResponse, intent.getExtras());
		}

		return super.onStartCommand(intent, flags, startId);
	}

	private void login(String actionResponse, Bundle extras) {
		String username = extras.getString("username");
		String password = extras.getString("password");

		// 1.0 connect to server
		boolean connectSucess = true;
		if (connectSucess) {
			// save login data
			Login login = new Login();
			login.setUserName(username);
			login.setPassword(password);
			login.setLogin(true);

			// send board cast success
			responseData(actionResponse, null, VasServiceStuas.SUCCESS);
		} else {
			responseData(actionResponse, null, VasServiceStuas.FAIL);
		}
	}

	private void responseData(String actionResponse, Bundle extras,
			VasServiceStuas status) {

		if (extras == null) {
			extras = new Bundle();
		}

		addStatus(extras, status);
		Intent intent = new Intent();
		intent.setAction(actionResponse);
		intent.putExtras(extras);
		sendBroadcast(intent);
	}
}