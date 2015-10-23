package org.com.cnc.rosemont.activity;

import org.com.cnc.common.android.Common;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.activity.commom.Conts;
import org.com.cnc.rosemont.asyn.AsynGetDataListProduct;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.handler.HandlerTab;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class HomeActivity extends Activity {
	private HandlerTab handler;
	private boolean isSecond = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gotoHome();
		isSecond = true;
		handler = getIntent().getExtras().getParcelable(Common.KEY01);
		CommonApp.hlHome = new Handler() {
			public void dispatchMessage(Message msg) {
				clearActivity();
				gotoHome();
			}
		};

	}
	protected void onStart() {
		super.onStart();
		if (DataStore.getInstance().getConfig(Conts.RE_SEARCH, false)) {
			new AsynGetDataListProduct(HomeActivity.this).execute("");
			DataStore.getInstance().setConfig(Conts.RE_SEARCH, false);
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		isSecond = false;
	}

	public void sendMessageToSearch() {
		Message message = new Message();
		message.what = Common.REQUESTCODE_01;
		handler.sendMessage(message);
	}

	public void reset() {
		super.reset();
	}

	@Override
	protected void onResume() {
		super.onResume();
		DataStore.getInstance().setConfig(Conts.ISCALCULATOR, false);
		DataStore.getInstance().setConfig(Conts.TAB_ID, 0);
		DataStore.getInstance().setConfig(Conts.BACKFROMCALCULATOR, false);
	}
}
