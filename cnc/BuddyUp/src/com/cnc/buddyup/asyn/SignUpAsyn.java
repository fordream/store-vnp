package com.cnc.buddyup.asyn;

import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.cnc.buddyup.SignUpScreen;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.request.RequestRegister;
import com.cnc.buddyup.response.ResponseRegister;

public class SignUpAsyn extends AsyncTask<RequestRegister, String, Boolean> {
	private Handler handler;
	private SignUpScreen context;
	private boolean isRun = false;
	private ResponseRegister response;

	public boolean isRun() {
		return isRun;
	}

	public boolean isRun(boolean isRun) {

		this.isRun = isRun;
		return this.isRun;
	}

	public SignUpAsyn(Handler handler, SignUpScreen context) {
		super();
		this.handler = handler;
		this.context = context;
	}

	protected Boolean doInBackground(RequestRegister... params) {
		RequestRegister login = params[0];
		handler.post(new Runnable() {
			public void run() {
				if (isRun) {
					context.showDialog(true);
				}
			}
		});
		response = ResponseRegister.getResponseRegister(login);
		handler.post(new Runnable() {
			public void run() {
				if (isRun) {
					context.showDialog(false);
				}
			}
		});

		return null;
	}

	protected void onPostExecute(Boolean result) {
		isRun = false;
		super.onPostExecute(result);
		if ("0".equals(response.getStatus())) {
			Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT)
					.show();
			handler.sendMessage(Common.createMessage(Common.MESSAGE_WHAT_0));
		} else {
			String title = "Error";
			String messgae = response.getMessage();
			if (messgae == null) {
				messgae = "Register fail!";
			}
			
			Common.builder(context, title, messgae);
		}
	}
}