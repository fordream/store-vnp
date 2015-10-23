package org.com.cnc.rosemont.activity;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android.CommonNetwork;
import org.com.cnc.common.android.activity.CommonActivity;
import org.com.cnc.rosemont.R.layout;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.activity.commom.Conts;
import org.com.cnc.rosemont.asyn.AsynGetData;
import org.com.cnc.rosemont.database.DBAdapter;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.database.table.Config;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends CommonActivity {
	// private LoadingView loadingView;

	// private LoadData loadData;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.splash);

		CommonApp.createFolder();
		CommonApp.reset();
		CommonApp.configSizeOftext(this);
		CommonApp.DATA.reset();
		//
		DataStore.init(this);
		DataStore.getInstance().setConfig(Conts.RE_SEARCH, false);
		DataStore.getInstance().setConfig(Conts.TAB_ID, 0);
		DataStore.getInstance().setConfig(Conts.BACKFROMCALCULATOR, false);
		DataStore.getInstance().setConfig(Conts.SEARCH_RELOAD, false);

		call();
		// loadData = new LoadData(this);

		// down load data ve tu trong thu muc aset vao trong app
		// loadData.execute("");

		CommonApp.hlCalculator = null;
	}

	protected void onPause() {
		// loadData.setClose(true);
		super.onPause();
	}

	// private class LoadData extends CommonAsyncTask {
	//
	// public LoadData(CommonActivity activity) {
	// super(activity);
	// }
	//
	// protected String doInBackground(String... arg0) {
	// new DBAdapter(getActivity()).createDB();
	// new DBAdapterData(getActivity()).createDB();
	// // CopyAssets();
	// return null;
	// }
	//
	// protected void onPostExecute(String result) {
	// //CommonDeviceId.rescanSdcard(getActivity());
	//
	// if (!isClose()) {
	// call();
	// }
	// }
	//
	// // copy pdf o aset vao the nho
	// private void CopyAssets() {
	// AssetManager assetManager = getAssets();
	// String[] files = null;
	// try {
	// files = assetManager.list("pdf/rosemont");
	// } catch (IOException e) {
	// }
	//
	// for (String filename : files) {
	// InputStream in = null;
	// OutputStream out = null;
	// try {
	// in = assetManager.open("pdf/rosemont/" + filename);
	// out = new FileOutputStream(CommonApp.PATH + filename);
	// copyFile(in, out);
	// in.close();
	// in = null;
	// out.flush();
	// out.close();
	// out = null;
	// } catch (Exception e) {
	// }
	// }
	// }
	//
	// private void copyFile(InputStream in, OutputStream out)
	// throws IOException {
	// byte[] buffer = new byte[1024];
	// int read;
	// while ((read = in.read(buffer)) != -1) {
	// out.write(buffer, 0, read);
	// }
	// }
	// }

	private void showDialog() {
		Intent intent = new Intent(SplashActivity.this,
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
				new AsynGetData(SplashActivity.this).execute("");

			} else if ((CommonApp.checkStatus3G(this))
					&& ("false".equalsIgnoreCase(config.getWifiupdate()))) {
				new AsynGetData(SplashActivity.this).execute("");

			} else if (!CommonNetwork.haveConnectTed(this)) {
				Intent intent = new Intent(SplashActivity.this,
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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Common.REQUESTCODE_01 && resultCode == RESULT_OK) {
			if (CommonNetwork.haveConnectTed(this)) {
				String wifiUpdate = new DBAdapter(this).getConfig()
						.getWifiupdate();
				new AsynGetData(this).execute("");

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
				new AsynGetData(SplashActivity.this).execute("");
			} else {
				goToCustomTab();
			}

		} else if (requestCode == Common.REQUESTCODE_03) {
			if (resultCode == RESULT_OK) {

				if (CommonNetwork.haveConnectTed(this)) {
					new AsynGetData(SplashActivity.this).execute("");
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
