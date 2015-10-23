package com.icts.shortfilmfestival.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends Activity {
	/** This field contain tag log name. */
	public void makeText(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// Keep screen in Portrait mode.
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onSearchRequested() {

		return true;
	}

}
