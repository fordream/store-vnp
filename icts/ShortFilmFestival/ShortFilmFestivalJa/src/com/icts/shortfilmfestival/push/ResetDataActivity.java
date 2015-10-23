package com.icts.shortfilmfestival.push;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ResetDataActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		C2DMMessageReceiver.listMessages.clear();
		MessageReceivedActivity.numberMessage = 0;
		C2DMMessageReceiver.id = 0;
		Log.d("RESET", "RESET");
		finish();
	}
}
