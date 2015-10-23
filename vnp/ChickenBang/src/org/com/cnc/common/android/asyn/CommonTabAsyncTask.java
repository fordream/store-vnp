package org.com.cnc.common.android.asyn;

import org.com.cnc.common.adnroid.activity.CommonTabActivity;

import android.os.AsyncTask;
import android.os.Handler;

public class CommonTabAsyncTask extends AsyncTask<String, String, String> {
	protected Handler handler = new Handler();
	private boolean isRun = false;
	private boolean isClose = false;
	private boolean isPause = false;
	private boolean isFirst = false;
	private CommonTabActivity activity;

	public CommonTabAsyncTask(CommonTabActivity activity) {
		this.activity = activity;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isPause() {
		return isPause;
	}

	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}

	protected String doInBackground(String... arg0) {
		return null;
	}

	protected void showLoading(final boolean show) {
		activity.showLoading(show);
	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	public boolean isClose() {
		return isClose;
	}

	public void setClose(boolean isClose) {
		this.isClose = isClose;
	}

	public void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	public CommonTabActivity getActivity() {
		return activity;
	}

	public void setActivity(CommonTabActivity activity) {
		this.activity = activity;
	}

}