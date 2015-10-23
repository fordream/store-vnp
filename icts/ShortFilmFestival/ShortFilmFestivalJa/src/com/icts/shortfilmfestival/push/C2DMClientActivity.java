package com.icts.shortfilmfestival.push;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.icts.shortfilmfestivalJa.R;

public class C2DMClientActivity extends Activity {

	public final static String AUTH = "authentication";   //authentication

	// Example Activity to trigger a request for a registration ID to the Google
	// server 
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpush);
	}

	public void register(View view) { 
		Log.w("C2DM", "start registration process");
		Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
		intent.putExtra("app",PendingIntent.getBroadcast(this, 0, new Intent(), 0));
		// Sender currently not used
		intent.putExtra("sender", "shortfilmfestivaldeveloper@gmail.com");
		startService(intent);
	}

	public void showRegistrationId(View view) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String string = prefs.getString(AUTH, "n/a");
		Toast.makeText(this, string, Toast.LENGTH_LONG).show();
		Log.d("C2DM RegId", string);
	}
}