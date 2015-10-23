package com.cnc.buddyup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import com.cnc.buddyup.asyn.ConfigLoginAsyn;
import com.cnc.buddyup.asyn.LoginAsyn;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.common.views.LoadingView;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.request.RequestLogin;

public class LoginScreen extends Activity implements OnClickListener {
	private LoginAsyn loginAsyn;
	private ConfigLoginAsyn configLoginAsyn;
	private LoadingView loadingView;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == Common.MESSAGE_WHAT_0) {
				config(msg.getData());
			} else if (msg.what == Common.MESSAGE_WHAT_1) {
				Intent intent = new Intent(LoginScreen.this,
						CustomTabsActivity.class);
				startActivity(intent);
				finish();
			}
		}
	};

	public void showLoad(boolean show) {
		loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		loadingView = (LoadingView) findViewById(R.id.loadingView1);
		configLoginAsyn = new ConfigLoginAsyn(handler, this);
		configLoginAsyn.execute("");
		getButton(R.id.btnLogin).setOnClickListener(this);
		getButton(R.id.btnSignup).setOnClickListener(this);
	}

	private void config(Bundle data) {
		getCheckBox(R.id.cBStatus).setChecked(data.getBoolean(Common.ARG0));
		if (data.getBoolean(Common.ARG0)) {
			getEditText(R.id.etUserName).setText(data.getString(Common.ARG1));
			getEditText(R.id.eTPassword).setText(data.getString(Common.ARG2));
		}
	}

	public void onClick(View view) {
		hiddenKeyboard(this, getEditText(R.id.etUserName));
		hiddenKeyboard(this, getEditText(R.id.eTPassword));
		if (view.getId() == R.id.btnLogin) {
			boolean status = getCheckBox(R.id.cBStatus).isChecked();
			String userName = getEditText(R.id.etUserName).getText().toString();
			String password = getEditText(R.id.eTPassword).getText().toString();
			RequestLogin request = new RequestLogin();
			request.setUsername(userName);
			request.setPassword(password);
			request.setIsCheck(status  + "");
			loginAsyn = new LoginAsyn(handler, this);
			loginAsyn.execute(request);
		} else if (R.id.btnSignup == view.getId()) {
			Intent intent = new Intent(this, SignUpScreen.class);
			startActivity(intent);
		}
	}

	protected void onDestroy() {
		if (loginAsyn != null) {
			loginAsyn.isClose();
		}

		if (configLoginAsyn != null) {
			configLoginAsyn.isClose();
		}
		super.onDestroy();
	}
}