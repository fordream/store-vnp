package com.cnc.buddyup.asyn;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.cnc.buddyup.LoginScreen;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.database.DBAdapter;
import com.cnc.buddyup.login.connect.ResponseLogin;

public class ConfigLoginAsyn extends AsyncTask<String, String, String> {
	private Handler handler;
	private LoginScreen context;
	private boolean isClose = false;

	public ConfigLoginAsyn(Handler handler, LoginScreen context) {
		super();
		this.handler = handler;
		this.context = context;
	}

	protected String doInBackground(String... params) {
		handler.post(new Runnable() {
			public void run() {
				if (!isClose)
					context.showLoad(true);
			}
		});
		DBAdapter adapter = new DBAdapter(context);

		ResponseLogin user = adapter.getUser();
		handler.post(new Runnable() {
			public void run() {
				if (!isClose) {
					context.showLoad(false);
				}
			}
		});
		if (!isClose) {
			handler.sendMessage(createMesage(user.isStatus(), user.getUser(),
					user.getPassword()));
		}
		return null;
	}

	private Message createMesage(boolean status, String user, String pass) {
		Bundle data = new Bundle();
		data.putBoolean(Common.ARG0, status);
		data.putString(Common.ARG1, user);
		data.putString(Common.ARG2, pass);
		Message message = new Message();
		message.what = Common.MESSAGE_WHAT_0;
		message.setData(data);
		return message;
	}

	public void isClose() {
		isClose = true;
	}

}
