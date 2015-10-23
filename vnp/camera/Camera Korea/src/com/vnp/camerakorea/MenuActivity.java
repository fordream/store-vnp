package com.vnp.camerakorea;

import android.app.Activity;
import android.os.Bundle;

public class MenuActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
	}
	
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.abc_slide_in_bottom,
				R.anim.abc_slide_out_bottom);
	}
}