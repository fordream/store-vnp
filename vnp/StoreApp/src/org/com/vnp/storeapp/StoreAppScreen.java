package org.com.vnp.storeapp;

import org.com.cnc.common.android.database.DataStore;
import org.com.vnp.storeapp.common.Conts;
import org.com.vnp.storeapp.database.DBAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StoreAppScreen extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		DataStore.init(this);
		DataStore.getInstance().setConfig(Conts.OPEN, "" + true);
		DBAdapter.init(this);

		finish();
		startActivity(new Intent(this, MenuScreen.class));

		// execute(this, null, 0);
	}

	@Override
	protected void onStop() {
		DataStore.getInstance().setConfig(Conts.OPEN, "" + false);
		super.onStop();
		finish();
	}
}