package vnp.com.gamegate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashGamegateActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (!isFinishing()) {
					gotoMenu();
				}
			}

		}, 2 * 1000);
	}

	private void gotoMenu() {
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}