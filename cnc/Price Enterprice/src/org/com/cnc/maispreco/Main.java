package org.com.cnc.maispreco;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.client.android.CaptureActivity;

public class Main extends Activity {
	private Bundle b;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		// try{
		Intent intent = new Intent(getBaseContext(), CaptureActivity.class);
		startActivityForResult(intent, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("key");
				b = new Bundle();
				b.putString("key", contents);
				this.finish();
				startActivity(new Intent(this, Check.class).putExtras(b));
			} else if (resultCode == RESULT_CANCELED) {
				this.finish();
			}
		}
	}
}