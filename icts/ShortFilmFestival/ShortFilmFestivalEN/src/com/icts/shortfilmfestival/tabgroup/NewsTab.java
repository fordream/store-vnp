package com.icts.shortfilmfestival.tabgroup;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.vnp.shortfilmfestival.R;

public class NewsTab extends FragmentActivity {

	public static boolean isDeailShow = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_news);
		isDeailShow = false;
	}

	@Override
	public void onBackPressed() {
		Log.d("LOG_MAINTAB_ACTIVITY", "isDeailShow:" + isDeailShow);
		if (!isDeailShow) {
			Log.d("LOG_MAINTAB_ACTIVITY", "LOG_MAINTAB_ACTIVITY");
			super.onBackPressed();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		System.gc();
		super.onDestroy();
	}
}
