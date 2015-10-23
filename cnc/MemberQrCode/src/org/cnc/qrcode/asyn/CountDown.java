package org.cnc.qrcode.asyn;

import org.cnc.qrcode.GlobalActivity;
import org.cnc.qrcode.database.DBAdapter;
import org.cnc.qrcode.database.DBHistory2Adapter;
import org.cnc.qrcode.database.DBHistoryAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

public class CountDown extends AsyncTask<String, String, String> {
	private boolean isStop = false;
	private Activity activity;

	public CountDown(Activity activity) {
		this.activity = activity;
	}

	public void stop() {
		isStop = true;
	}

	public void onFinish() {
		if (isStop) {
			return;
		}
		Intent i = new Intent(activity, GlobalActivity.class);
		activity.startActivity(i);
		activity.finish();
	}

	protected String doInBackground(String... params) {
		new DBHistoryAdapter(activity).createDB();
		new DBHistory2Adapter(activity).createDB();
		new DBAdapter(activity).createDB();
		int time = 0;
		while (time < 3) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			time++;
		}

		return null;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		onFinish();
	}
}
