package com.caferhythm.csn.activity;

import com.caferhythm.csn.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SPtop01Activity extends Activity {
	private Button registerButton;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fade_in,R.anim.fade_out );
		setContentView(R.layout.flash_screen);
		registerButton = (Button) findViewById(R.id.bt_flash_screen_register);
		registerButton.setVisibility(View.VISIBLE);
		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						RegisterActivity.class));
			}
		});
	}
}
