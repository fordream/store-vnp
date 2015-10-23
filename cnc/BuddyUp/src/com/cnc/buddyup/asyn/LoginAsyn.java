package com.cnc.buddyup.asyn;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;

import com.cnc.buddyup.CustomTabsActivity;
import com.cnc.buddyup.LoginScreen;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.database.DBAdapter;
import com.cnc.buddyup.request.RequestLogin;
import com.cnc.buddyup.response.ResponseLogin;

public class LoginAsyn extends AsyncTask<RequestLogin, String, Boolean> {
	private Handler handler1 = new Handler();
	private LoginScreen context;
	private boolean isClose = false;
	private ResponseLogin response;

	public LoginAsyn(Handler handler, LoginScreen context) {
		super();
		this.context = context;
	}

	protected Boolean doInBackground(RequestLogin... params) {
		RequestLogin login = params[0];
		handler1.post(new Runnable() {
			public void run() {
				if (!isClose) {
					context.showLoad(true);
				}
			}
		});
		DBAdapter adapter = new DBAdapter(context);
		if (login.getIsCheck().equals("true")) {
			adapter.updateStatus(1, login.getUsername(), login.getPassword());
		} else {
			adapter.updateStatus(0, "", "");
		}

		response = ResponseLogin.getResponseRegister(login);
		handler1.post(new Runnable() {
			public void run() {
				if (!isClose) {
					context.showLoad(false);
				}
			}
		});

		return null;
	}

	protected void onPostExecute(Boolean result) {

		super.onPostExecute(result);
		if (!isClose) {
			if ("0".equals(response.getStatus())) {
				Common.token = response.getToken();
				Common.id = response.getId();
				 Intent intent = new Intent(context,
				 CustomTabsActivity.class);
//				Intent intent = new Intent(context,
//						SkillLevelWheelActivity.class);
				context.startActivity(intent);
				context.finish();
			} else {
				String title = "Error";
				String message = response.getMessage();

				if (message == null) {
					message = "Login fail!";
				}

				Common.builder(context, title, message);
			}
		}
	}

	public void isClose() {
		isClose = true;
	}
}
