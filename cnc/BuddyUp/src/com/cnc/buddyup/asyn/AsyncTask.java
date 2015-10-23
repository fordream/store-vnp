package com.cnc.buddyup.asyn;

public class AsyncTask extends android.os.AsyncTask<String, String, String> {
	private boolean isRun = false;
	private boolean isClose = false;
	
			
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


	protected String doInBackground(String... params) {
		return null;
	}

}
