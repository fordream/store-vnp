package org.com.vnp.chickenbang.asyn;

import org.com.cnc.common.adnroid.activity.CommonActivity;
import org.com.cnc.common.android.asyn.CommonAsyncTask;
import org.com.vnp.chickenbang.ChickenBangActivity;

import android.app.Activity;
import android.content.Intent;

public class SplashAsyn extends CommonAsyncTask {

	public SplashAsyn(CommonActivity activity) {
		super(activity);
	}

	protected String doInBackground(String... arg0) {
		sleep(3000);
		return super.doInBackground(arg0);
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (!isClose()) {
			Activity activity = getActivity();
			Intent intent = new Intent(activity, ChickenBangActivity.class);
			activity.startActivity(intent);
			activity.finish();
		}
	}
}
