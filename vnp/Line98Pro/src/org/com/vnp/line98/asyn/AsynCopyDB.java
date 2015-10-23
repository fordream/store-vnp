package org.com.vnp.line98.asyn;

import org.com.vnp.line98.database.DBAdapter;

import android.content.Context;
import android.os.AsyncTask;

public class AsynCopyDB extends AsyncTask<String, String, String> {
	private Context context;

	public AsynCopyDB(Context context) {
		this.context = context;
	}

	protected String doInBackground(String... params) {
		new DBAdapter(context).create();
		return null;
	}
}
