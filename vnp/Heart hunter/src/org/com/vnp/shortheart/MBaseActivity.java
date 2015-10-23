package org.com.vnp.shortheart;

import org.anddev.andengine.ui.activity.BaseActivity;
import org.com.vnp.shortheart.service.Mservice;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MBaseActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onResume() {
		super.onResume();
		startService(new Intent(this, Mservice.class));
	}

	@Override
	protected void onPause() {
		super.onPause();
		stopService(new Intent(this, Mservice.class));
	}

}