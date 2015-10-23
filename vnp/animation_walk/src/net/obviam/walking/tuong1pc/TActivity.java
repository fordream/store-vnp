package net.obviam.walking.tuong1pc;

import android.app.Activity;
import android.os.Bundle;

public class TActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new TView(this));
	}
}
