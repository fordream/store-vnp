package org.com.vnp.line98.asyn;

import org.com.vnp.line98.activity.Line98MenuActivity;

import android.os.AsyncTask;

public class NewGameAsyn extends AsyncTask<String, String, String> {
//	private boolean isStop = false;
	private Line98MenuActivity line98MenuActivity;

	public NewGameAsyn(Line98MenuActivity line98MenuActivity) {
		this.line98MenuActivity = line98MenuActivity;
	}

	protected String doInBackground(String... params) {
		//line98MenuActivity.showLoad(true);
		line98MenuActivity.newGame();
		//line98MenuActivity.showLoad(false);
		return null;
	}

}
