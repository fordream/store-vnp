package org.com.cnc.rosemont.activity;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android.CommonNetwork;
import org.com.cnc.common.android.activity.CommonActivity;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.layout;
import org.com.cnc.rosemont.RosemonService;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.activity.commom.Conts;
import org.com.cnc.rosemont.database.DBAdapter;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.database.table.Config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class VSplashActivity extends CommonActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.splash);
		CommonApp.createFolder();
		CommonApp.reset();
		CommonApp.configSizeOftext(this);
		CommonApp.DATA.reset();
		DataStore.init(this);
		DataStore.getInstance().setConfig(Conts.RE_SEARCH, false);
		DataStore.getInstance().setConfig(Conts.TAB_ID, 0);
		DataStore.getInstance().setConfig(Conts.BACKFROMCALCULATOR, false);
		DataStore.getInstance().setConfig(Conts.SEARCH_RELOAD, false);
		CommonApp.hlCalculator = null;

		if (!DataStore.getInstance().getConfig("DOWNLOAD", false)) {
			call();
		} else {
			// update content
			showProcess(true);
		}

		registerReceiver(broadcastReceiver, new IntentFilter(getPackageName()
				+ "download"));
	}

	private void showProcess(boolean b) {
		findViewById(R.id.progressBar1).setVisibility(
				b ? View.VISIBLE : View.GONE);
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			int _status = intent.getIntExtra("status", -1);
			if (_status == RosemonService.STATUS_DOWNLOAD) {
				showProcess(true);
			} else if (_status == RosemonService.STATUS_FAIL) {
				// showProcess(true);
				showProcess(false);
			} else if (_status == RosemonService.STATUS_SUCESS) {
				showProcess(false);
				goToCustomTab();
			} else if (_status == RosemonService.STATUS_DOWNLOAD_FLIE) {
				String name = intent.getStringExtra("name");
				TextView textView = (TextView) findViewById(R.id.textView1);
				textView.setVisibility(View.VISIBLE);
				textView.setText("Download file : " + name);
			}
		}
	};

	@Override
	protected void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	}

	private void showDialog() {
		Intent intent = new Intent(VSplashActivity.this,
				HomeDialogActivity.class);
		startActivityForResult(intent, Common.REQUESTCODE_01);
	}

	// check trang thai removestarup o setting neu true(NO) thi hien popup
	private void call() {
		// config luu setting cua app
		Config config = new DBAdapter(this).getConfig();
		if ("true".equals(config.getRemovethestartup())) {
			showDialog();
		} else {
			if (CommonApp.checkStatusWifi(this)) {
				// new AsynGetData(VSplashActivity.this).execute("");
				push();
			} else if ((CommonApp.checkStatus3G(this))
					&& ("false".equalsIgnoreCase(config.getWifiupdate()))) {
				// new AsynGetData(VSplashActivity.this).execute("");
				push();
			} else if (!CommonNetwork.haveConnectTed(this)) {
				Intent intent = new Intent(VSplashActivity.this,
						DialogWarnningActivity.class);
				startActivityForResult(intent, Common.REQUESTCODE_02);
				// goToCustomTab();
			}

			// String wifiUpdate = config.getWifiupdate();

			/*
			 * if (CommonNetwork.haveConnectTed(this)) {// co mang thi down data
			 * // moi new AsynGetData(SplashActivity.this).execute(""); } else
			 * if ("true".equals(wifiUpdate)) {// check 3G trong setting //
			 * true(ON) thi nhay den // popup2 Intent intent = new
			 * Intent(SplashActivity.this, DialogWarnningActivity.class);
			 * startActivityForResult(intent, Common.REQUESTCODE_02); } else {//
			 * chuyen den tab goToCustomTab(); }
			 */

		}

	}

	private void push() {
		showProcess(true);
		Intent intent = new Intent(this, RosemonService.class);
		intent.putExtra("download", true);
		startService(intent);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Common.REQUESTCODE_01 && resultCode == RESULT_OK) {
			if (CommonNetwork.haveConnectTed(this)) {
				String wifiUpdate = new DBAdapter(this).getConfig()
						.getWifiupdate();
				// new AsynGetData(this).execute("");
				push();
			} else {
				Intent intent = new Intent(this, DialogWarnningActivity.class);
				startActivityForResult(intent, Common.REQUESTCODE_02);
			}
		} else if (requestCode == Common.REQUESTCODE_01
				&& resultCode == RESULT_CANCELED) {

			Intent intent = new Intent(this, HomeDialogNoActivity.class);
			startActivityForResult(intent, Common.REQUESTCODE_03);
		} else if (requestCode == Common.REQUESTCODE_02
				&& resultCode == RESULT_CANCELED) {
			finish();
		} else if (requestCode == Common.REQUESTCODE_02
				&& resultCode == RESULT_OK) {

			if (CommonNetwork.haveConnectTed(this)) {
				// new AsynGetData(VSplashActivity.this).execute("");
				push();
			} else {
				goToCustomTab();
			}

		} else if (requestCode == Common.REQUESTCODE_03) {
			if (resultCode == RESULT_OK) {
				if (CommonNetwork.haveConnectTed(this)) {
					// new AsynGetData(VSplashActivity.this).execute("");
					push();
				} else {
					goToCustomTab();
				}
			} else {
				finish();
			}
		}
	}

	public void goToCustomTab() {
		Intent intent = new Intent(this, CustomTabsActivity.class);
		startActivity(intent);
		finish();
	}

}
