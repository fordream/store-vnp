package com.cnc.maispreco.asyn;

import org.com.cnc.common.maispreco.request.LoginRequest;
import org.com.cnc.common.maispreco.response.LoginResponse;
import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.Common;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;

public class AsynTask extends AsyncTask<String, String, String> {
	protected MaisprecoScreen maisprecoScreen;
	protected Handler handler = new Handler();
	protected ProgressDialog dialog;

	public AsynTask(MaisprecoScreen maisprecoScreen) {
		this.maisprecoScreen = maisprecoScreen;
	}

	public void showDialog() {
		handler.post(new Runnable() {
			public void run() {
				String message = maisprecoScreen.getResources().getString(
						R.string.loading);
				dialog = ProgressDialog.show(maisprecoScreen, "", message);
			}
		});
	}

	public void dimiss() {
		if (dialog != null) {
			handler.post(new Runnable() {
				public void run() {
					dialog.dismiss();
				}
			});
		}
	}

	protected String doInBackground(String... params) {
		return null;
	}

	public void relogin() {
		LoginRequest loginRequest = new LoginRequest("" + Common.latitude, ""
				+ Common.longitude);
		LoginResponse loginResponse = LoginResponse
				.getLoginResponse(loginRequest);
		if (loginResponse != null) {
			Common.tooken = loginResponse.getToken();
		}
	}

}
