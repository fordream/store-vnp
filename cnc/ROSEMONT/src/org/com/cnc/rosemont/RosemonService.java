package org.com.cnc.rosemont;

import java.io.File;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android.CommonDownload;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.database.DBAdapter;
import org.com.cnc.rosemont.database.DBAdapterData;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.database.table.Config;
import org.com.cnc.rosemont.database.table.First;
import org.com.cnc.rosemont.database.table.RosemontTable;
import org.com.cnc.rosemont.request.RequestUpdateData;
import org.com.cnc.rosemont.response.ResponseData;
import org.com.cnc.rosemont.response.ResponseLastUpdate;
import org.com.cnc.rosemont.response.ResponseUpdateData;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

public class RosemonService extends Service implements Runnable {

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
		// create database
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// check start download
		DataStore.getInstance().init(this);
		if (intent != null && intent.getBooleanExtra("download", false)
				&& !DataStore.getInstance().getConfig("DOWNLOAD", false)) {
			new Thread(this).start();
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void run() {
		DataStore.getInstance().setConfig("DOWNLOAD", true);
		push(STATUS_DOWNLOAD, null);
		ResponseLastUpdate responseDate = new ResponseLastUpdate();
		responseDate.getData();
		Config config = new DBAdapter(this).getConfig();

		RosemontTable tempRosemontTable = new RosemontTable();
		if (responseDate.getLatUpdate() != null) {
			First first = new First(this);
			if (first.isFirst()) {
				// lan dau vao app download
				Log.i("FIRST", "FIRST");
				ResponseData response = new ResponseData();
				response.getData();

				tempRosemontTable = response.getTable();
				if (response.getTable().sizeOfRow() > 0) {
					new DBAdapterData(this).delete(new RosemontTable());
					new DBAdapterData(this).insert(response.getTable());
					config.setLastupdate(responseDate.getLatUpdate());
					new DBAdapter(this).update(config);
					first.setFirst(false);
					first.save(this);
				} else {
				}
			} else {
				// Log.i("FIRST", "LAST");
				// update content
				RosemontTable responseDelete = new RosemontTable();
				RosemontTable responseUpdate = new RosemontTable();
				RequestUpdateData data = new RequestUpdateData(
						config.getLastupdate());
				new ResponseUpdateData().getData(data, responseDelete,
						responseUpdate);
				if (responseUpdate.sizeOfRow() > 0) {
					DBAdapterData dbAdapterData = new DBAdapterData(this);
					dbAdapterData.delete(responseDelete, true);
					dbAdapterData.insert(responseUpdate);
					tempRosemontTable = responseUpdate;
				} else {
				}
			}

			config.setLastupdate(responseDate.getLatUpdate());
			new DBAdapter(this).update(config);
		} else {
		}

		// download file
		// int k = 0;
		// int l = 1;
		CommonApp.getDataROSEMONT(this);
		tempRosemontTable = CommonApp.ROSEMONT;
		for (int i = 0; i < tempRosemontTable.sizeOfRow(); i++) {
			String name = tempRosemontTable.getNameFile(i);
			name = CommonApp.changleName(name);
			File dir = new File(CommonApp.PATH + "/" + name);
			if (!Common.isNullOrBlank(name) && !dir.exists()) {
				// k++;
			}
		}

		// download file
		for (int i = 0; i < tempRosemontTable.sizeOfRow(); i++) {
			String name = tempRosemontTable.getNameFile(i);

			name = CommonApp.changleName(name);

			File dir = new File(CommonApp.PATH + "/" + name);
			if (isConnected()) {
				if (!Common.isNullOrBlank(name) && !dir.exists()) {
					push(STATUS_DOWNLOAD_FLIE, name);
					CommonDownload.downloadfileRosemont(CommonApp.URL_MEDIA
							+ name, name, true);
					if (dir.exists()) {
						// l++;
					} else {
						// l++;
						// download fail
					}
				} else {

				}
			} else {
				// return;
				break;
			}
		}
		push(STATUS_SUCESS, null);
		DataStore.getInstance().setConfig("DOWNLOAD", false);
	}

	public static final int STATUS_DOWNLOAD = 0;
	public static final int STATUS_SUCESS = 1;
	public static final int STATUS_FAIL = 2;
	public static final int STATUS_DOWNLOAD_FLIE = 3;

	/**
	 * 
	 * @param status
	 * 
	 */
	private void push(int status, String name) {
		Intent intent = new Intent(getPackageName() + "download");
		intent.putExtra("status", status);
		intent.putExtra("name", name);
		sendBroadcast(intent);
	}

	private boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if ((cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
				|| (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
						.getState() == NetworkInfo.State.CONNECTED)) {
			return true;
		}

		if ((cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTING)
				&& ((cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
						.getState() == NetworkInfo.State.CONNECTED))) {
			return false;
		}

		return false;
	}
}