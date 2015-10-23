package org.com.vnp.lmhtmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.vnp.core.base.activity.BaseActivity;

public class SplashActivity extends BaseActivity {
	private TextView textStatus;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.a_bot_to_top_in, R.anim.a_nothing);
		setContentView(R.layout.splash);
		textStatus = (TextView) findViewById(R.id.textStatus);
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}