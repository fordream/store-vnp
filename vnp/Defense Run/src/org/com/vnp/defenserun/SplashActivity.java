package org.com.vnp.defenserun;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.vnp.core.activity.BaseActivity;
import com.vnp.core.common.VNPResize;
import com.vnp.core.common.VNPResize.ICompleteInit;

public class SplashActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashactivity);

		VNPResize.getInstance().init(this, 320, 0, new ICompleteInit() {

			@Override
			public void complete() {
				if (!isFinishing()) {
					startActivity(new Intent(SplashActivity.this,
							MenuScreen.class));
					finish();
				}
			}
		}, (TextView) getView(R.id.baseTextView1));
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}