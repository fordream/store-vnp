package com.vnp.shortfirmfestival_rework.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.vnp.shortfirmfestival_rework.R;

public class ShortFirmBaseActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_nothing);
		// overridePendingTransition(R.anim.abc_slide_in_left,
		// R.anim.abc_slide_out_right);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.abc_nothing, R.anim.abc_slide_out_bottom);
		// overridePendingTransition(R.anim.abc_slide_in_left,
		// R.anim.abc_slide_out_right);

	}

	public long getTimeStartAnimation() {
		// @android:integer/config_mediumAnimTime
		return getResources().getInteger(android.R.integer.config_mediumAnimTime);
	}

	public void toast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}
}
