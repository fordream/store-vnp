package org.com.vnp.caothubongbong.asyn;

import org.com.cnc.common.adnroid16.activity.CommonActivity;
import org.com.cnc.common.adnroid16.asyn.CommonAsyncTask;
import org.com.vnp.caothubongbong.MenuActivity;
import org.com.vnp.caothubongbong.config.ConfigMediaplayer;
import org.com.vnp.caothubongbong.db.DBAdapter;

import android.content.Intent;

public class AsynSplash extends CommonAsyncTask {

	public AsynSplash(CommonActivity activity) {
		super(activity);
	}

	protected String doInBackground(String... arg0) {
		setClose(false);
		new DBAdapter(getActivity()).createDB();
		ConfigMediaplayer.createMediaplayer(getActivity());
		ConfigMediaplayer.start();
		ConfigMediaplayer.startKill(getActivity());
		return super.doInBackground(arg0);
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (!isClose()) {
			Intent intent = new Intent(getActivity(), MenuActivity.class);
			getActivity().startActivity(intent);
			getActivity().finish();
		} else {
			ConfigMediaplayer.stop();
		}
	}
}
