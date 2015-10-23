package org.cnc.qrcode.asyn;

import org.cnc.qrcode.database.DBHistory2Adapter;
import org.cnc.qrcode.database.item.History2;

import android.app.Activity;
import android.os.AsyncTask;

public class SaveHistory extends AsyncTask<String, String, String> {
	private Activity activity;
	private History2 history;

	public SaveHistory(Activity activity, History2 history) {
		this.activity = activity;
		this.history = history;
	}

	protected String doInBackground(String... params) {
		new DBHistory2Adapter(activity).deleteHistory();
		new DBHistory2Adapter(activity).insert(history);
		return null;
	}
}