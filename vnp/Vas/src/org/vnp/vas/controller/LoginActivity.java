package org.vnp.vas.controller;

import org.vnp.vas.R;
import org.vnp.vas.controller.service.VasService;
import org.vnp.vas.controller.service.VasServiceStuas;
import org.vnp.vas.model.Login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ict.library.activity.CommonBaseActivity;
import com.ict.library.common.CommonAndroid;
import com.ict.library.view.CommonLoadingView;
import com.vnp.core.facebook.DialogError;
import com.vnp.core.facebook.Facebook;
import com.vnp.core.facebook.Facebook.DialogListener;
import com.vnp.core.facebook.FacebookError;

public class LoginActivity extends CommonBaseActivity implements OnClickListener {
	private CommonLoadingView loginLoadingView;
	private EditText loginEdtUser;
	private EditText loginEditPassword;
	private Button loginBtnRegister;
	private Button loginBtnLogin;
	private Button loginBtnLoginFace;
	private Button loginForgotPassword;
	private Facebook facebook;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		loginLoadingView = getView(R.id.login_loadingView1);
		loginBtnLogin = getView(R.id.login_btnLogin);
		loginBtnLoginFace = getView(R.id.login_btnLoginFacebook);
		loginBtnRegister = getView(R.id.login_btnRegister);
		loginEdtUser = getView(R.id.login_edtUserName);
		loginEditPassword = getView(R.id.login_edtPassword);
		loginForgotPassword = getView(R.id.login_forgotpassord);

		// set acction
		loginBtnLogin.setOnClickListener(this);
		loginBtnLoginFace.setOnClickListener(this);
		loginBtnRegister.setOnClickListener(this);
		loginForgotPassword.setOnClickListener(this);
		// register broadcast
		IntentFilter intentFilter = new IntentFilter("login");
		registerReceiver(broadcastReceiver, intentFilter);

		facebook = new Facebook("413680098662330");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	protected void onStart() {
		super.onStart();

		// hidden loaddingView
		loginLoadingView.setVisibility(View.GONE);

		// set data
		Login login = new Login();
		loginEdtUser.setText(login.getUserName());
		loginEditPassword.setText(login.getPassword());
	}

	@Override
	public void onClick(View view) {
		// hidden keyboard
		CommonAndroid.hiddenKeyBoard(this);

		if (view == loginBtnLogin) {
			loginLoadingView.setVisibility(View.VISIBLE);
			// call to server for login
			Bundle extras = new Bundle();
			extras.putString("username", loginEdtUser.getText().toString()
					.trim());
			extras.putString("password", loginEditPassword.getText().toString()
					.trim());
			VasService.startService(extras, this, "login");
		} else if (view == loginBtnLoginFace) {
			loginFacebook();
		} else if (view == loginBtnRegister) {
			startActivity(new Intent(this, RegisterActivity.class));
		} else if (view == loginForgotPassword) {
			startActivity(new Intent(this, ForgotPasswordActivity.class));
		}
	}

	private void loginFacebook() {

		String[] PERMISSIONS = new String[] { "publish_stream", "read_stream" };
		facebook.authorize(this, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH,
				new DialogListener() {

					@Override
					public void onFacebookError(FacebookError arg0) {
						Toast.makeText(LoginActivity.this,
								"onFacebookError can't access",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(DialogError arg0) {
						Toast.makeText(LoginActivity.this,
								"onError can't access", Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onComplete(Bundle arg0) {

					}

					@Override
					public void onCancel() {
						Toast.makeText(LoginActivity.this, "onCancel Facebook",
								Toast.LENGTH_SHORT).show();
					}
				});

	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle extras = intent.getExtras();

			if (!isFinishing()) {
				loginLoadingView.setVisibility(View.GONE);
				if (VasService.getVasStatus(extras) == VasServiceStuas.FAIL) {
					Toast.makeText(LoginActivity.this,
							"Please, Check your account", Toast.LENGTH_SHORT)
							.show();
				} else if (VasService.getVasStatus(extras) == VasServiceStuas.CHECKNETWORK) {
					Toast.makeText(LoginActivity.this,
							"Please, Check your network", Toast.LENGTH_SHORT)
							.show();
				} else if (VasService.getVasStatus(extras) == VasServiceStuas.SUCCESS) {
					startActivity(new Intent(LoginActivity.this,
							MainActivity.class));
					finish();
				}
			}

		}
	};
}
