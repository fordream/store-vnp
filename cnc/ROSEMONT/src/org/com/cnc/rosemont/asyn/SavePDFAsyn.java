package org.com.cnc.rosemont.asyn;

import java.io.File;

import org.com.cnc.common.android.CommonDeviceId;
import org.com.cnc.common.android.CommonDownload;
import org.com.cnc.common.android.CommonView;
import org.com.cnc.rosemont.R.string;
import org.com.cnc.rosemont.activity.commom.CommonApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

public class SavePDFAsyn extends AsyncTask<String, String, String> {
	private Context context;
	private ProgressDialog dialog;
	private Handler handler = new Handler();
	private String fileName = "";

	public SavePDFAsyn(Context context, String fileName) {
		this.context = context;
		this.fileName = fileName;
	}

	private void showDialog(final boolean isShow) {
		handler.post(new Runnable() {
			public void run() {
				if (isShow) {
					String message = context.getResources().getString(
							string.loading);
					dialog = ProgressDialog.show(context, "", message);
				} else if (dialog != null) {
					dialog.dismiss();
				}
			}
		});
	}

	protected String doInBackground(String... params) {
		showDialog(true);
		CommonDownload.downloadfileRosemont(CommonApp.URL_MEDIA + fileName,
				fileName, true);
		showDialog(false);
		return null;
	}

	protected void onPostExecute(String result) {
		CommonDeviceId.rescanSdcard(context);
		File file = new File(CommonApp.PATH + fileName);
		if (file.exists()) {
			CommonApp.startPDF(CommonApp.PATH + fileName, context);
		} else {
			CommonView.viewDialog(context, "", context.getResources()
					.getString(string.dont_load_file));
			// CommonView.makeText(context,
			// context.getResources().getString(string.dont_load_file));
		}
	}
}
