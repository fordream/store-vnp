package com.cnc.android.timtalk.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginScreen extends Activity implements OnClickListener {

	private Button mLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		mLogin = (Button) findViewById(R.id.btn_login);
		mLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login:
			// check login
			/***********/
			// goto TalkMainScreen
			startActivity(new Intent(
					"com.cnc.android.timtalk.ui.intent.TALK_MAIN_SCREEN"));
			break;

		default:
			break;
		}
	}

}
