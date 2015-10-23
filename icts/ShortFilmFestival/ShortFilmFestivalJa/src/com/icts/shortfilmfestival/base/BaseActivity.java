package com.icts.shortfilmfestival.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class BaseActivity extends Activity {
	/** This field contain tag log name. */

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

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// If User back from dummy tab then close web view activity.
		// Back current tab to home.

	}
}
