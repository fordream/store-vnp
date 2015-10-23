package org.com.cnc.rosemont.asyn;

import org.com.cnc.common.android.asyntask.CommonAsyncTask;
import org.com.cnc.rosemont.activity.SplashActivity;
import org.com.cnc.rosemont.response.ResponseLastUpdate;

public class AsynGetLastUpdate extends CommonAsyncTask {
	private ResponseLastUpdate response = new ResponseLastUpdate();

	public AsynGetLastUpdate(SplashActivity activity) {
		super(activity);
	}

	protected String doInBackground(String... arg0) {
		showDialog(true, "", "");
		response.getData();
		return super.doInBackground(arg0);
	}

	protected void onPostExecute(String result) {
		showDialog(false, "", "");
	}
}
