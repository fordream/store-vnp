package com.vnpgame.undersea.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.vnpgame.chickenmerrychristmas.R;
import com.vnpgame.undersea.services.LocalService.LocalBinder;

public class BindingActivity extends Activity implements OnClickListener {
	LocalService mService;
	boolean mBound = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player1);
		findViewById(R.id.button1).setOnClickListener(this);
	}

	protected void onStart() {
		super.onStart();
		Intent intent = new Intent(this, LocalService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}

	protected void onStop() {
		super.onStop();
		if (mBound) {
			unbindService(mConnection);
			mBound = false;
		}
	}

	public void onButtonClick(View v) {
		if (mBound) {
			int num = mService.getRandomNumber();
			Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
		}
	}

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			LocalBinder binder = (LocalBinder) service;
			mService = binder.getService();
			mBound = true;
		}

		public void onServiceDisconnected(ComponentName arg0) {
			mBound = false;
		}
	};

	public void onClick(View arg0) {
		if (mBound) {
			int num = mService.getRandomNumber();
			Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
		}
		
	}
}