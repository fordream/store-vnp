package org.com.cnc.rosemont.asyn;

import java.io.File;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android.CommonDownload;
import org.com.cnc.common.android.CommonNetwork;
import org.com.cnc.common.android.asyntask.CommonAsyncTask;
import org.com.cnc.rosemont.R.string;
import org.com.cnc.rosemont.activity.Activity;
import org.com.cnc.rosemont.activity.CalculatorActivity;
import org.com.cnc.rosemont.activity.DialogWarnningActivity;
import org.com.cnc.rosemont.activity.HomeActivity;
import org.com.cnc.rosemont.activity.SplashActivity;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.database.DBAdapter;
import org.com.cnc.rosemont.database.DBAdapterData;
import org.com.cnc.rosemont.database.table.Config;
import org.com.cnc.rosemont.database.table.First;
import org.com.cnc.rosemont.database.table.RosemontTable;
import org.com.cnc.rosemont.request.RequestUpdateData;
import org.com.cnc.rosemont.response.ResponseData;
import org.com.cnc.rosemont.response.ResponseLastUpdate;
import org.com.cnc.rosemont.response.ResponseUpdateData;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class AsynGetDataCaculator extends CommonAsyncTask {
	private boolean flagNetwork = false;
	public AsynGetDataCaculator(Activity activity) {
		super(activity);
	}

	protected String doInBackground(String... arg0) {
		String loading = getActivity().getResources().getString(string.loading);
		showDialog(true, "", loading);
		ResponseLastUpdate responseDate = new ResponseLastUpdate();
		responseDate.getData();
		Config config = new DBAdapter(getActivity()).getConfig();
		RosemontTable tempRosemontTable = new RosemontTable();
		if (responseDate.getLatUpdate() != null) {
			First first = new First(getActivity());
			if (first.isFirst()) {
				Log.i("FIRST", "FIrST");
				ResponseData response = new ResponseData();
				response.getData();

				tempRosemontTable = response.getTable();

				if (response.getTable().sizeOfRow() > 0) {
					new DBAdapterData(getActivity())
							.delete(new RosemontTable());
					new DBAdapterData(getActivity())
							.insert(response.getTable());
					config.setLastupdate(responseDate.getLatUpdate());
					new DBAdapter(getActivity()).update(config);

					first.setFirst(false);
					first.save(getActivity());
				}
			} else {
				Log.i("FIRST", "LAST");
				RosemontTable responseDelete = new RosemontTable();
				RosemontTable responseUpdate = new RosemontTable();
				RequestUpdateData data = new RequestUpdateData(
						config.getLastupdate());
				new ResponseUpdateData().getData(data, responseDelete,
						responseUpdate);

				DBAdapterData dbAdapterData = new DBAdapterData(getActivity());
				dbAdapterData.delete(responseDelete, true);

				dbAdapterData.insert(responseUpdate);

				tempRosemontTable = responseUpdate;
			}

			config.setLastupdate(responseDate.getLatUpdate());
			new DBAdapter(getActivity()).update(config);

		}

		int k = 0;
		int l = 1;
		CommonApp.getDataROSEMONT(getActivity());
		tempRosemontTable = CommonApp.ROSEMONT;
		for (int i = 0; i < tempRosemontTable.sizeOfRow(); i++) {
			String name = tempRosemontTable.getNameFile(i);
			name = CommonApp.changleName(name);

			File dir = new File(CommonApp.PATH + "/" + name);
			if (!Common.isNullOrBlank(name) && !dir.exists()) {
				k++;
			}
		}

		for (int i = 0; i < tempRosemontTable.sizeOfRow(); i++) {
			String name = tempRosemontTable.getNameFile(i);

			name = CommonApp.changleName(name);

			File dir = new File(CommonApp.PATH + "/" + name);
			if (isConnected()) {
				if (!Common.isNullOrBlank(name) && !dir.exists()) {
					
					update("Dowload file " + l + "/" + k);
					CommonDownload.downloadfileRosemont(CommonApp.URL_MEDIA
							+ name, name, true);
					if(dir.exists()){
						flagNetwork = false;
						l++;
					}else{
						flagNetwork = true;
					}
					
				}
			} else {
				flagNetwork = true;
				return null;
			}
		}
		// truong vv add
		CommonApp.getDataROSEMONT(getActivity());
		return null;
	}

	protected void onPostExecute(String result) {
		  showDialog(false, "", "");
		  if (flagNetwork) {
		   Intent intent = new Intent(getActivity(),
		     DialogWarnningActivity.class);
		   ((CalculatorActivity) getActivity()).startActivityForResult(intent,CommonApp.REQUESTCODE_FINISH);
		  }else{
		   ((Activity) getActivity()).reset();
		  }
		
	}
	public boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) getActivity()
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
