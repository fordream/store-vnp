package org.com.cnc.common.adnroid.activity.mediaconnect;

import org.com.cnc.common.adnroid.activity.CommonActivity;
import org.com.cnc.common.android.asyn.CommonAsyncTask;

public class CommonMediaConnectActivity extends CommonActivity {
	private CommonAsyncTask commonAsyncTask;

	public CommonAsyncTask getCommonAsyncTask() {
		return commonAsyncTask;
	}

	public void setCommonAsyncTask(CommonAsyncTask commonAsyncTask) {
		this.commonAsyncTask = commonAsyncTask;
	}

	protected void onDestroy() {
		if (commonAsyncTask != null) {
			commonAsyncTask.isClose();
		}
		super.onDestroy();
	}

	protected void onResume() {
		super.onResume();
		if (commonAsyncTask != null) {
			commonAsyncTask.setPause(false);
		}
	}

	protected void onPause() {
		if (commonAsyncTask != null) {
			commonAsyncTask.setPause(true);
		}
		super.onPause();
	}

	protected void onRestart() {
		super.onRestart();
		if (commonAsyncTask != null) {
			commonAsyncTask.setPause(false);
		}
 	}
}