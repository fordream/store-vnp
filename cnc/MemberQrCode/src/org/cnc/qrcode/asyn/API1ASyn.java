package org.cnc.qrcode.asyn;

import java.util.List;

import org.cnc.qrcode.adapter.RequestServerAdapter;
import org.cnc.qrcode.common.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class API1ASyn extends AsyncTask<String, String, String> {
	private Handler handler;
	private Context context;
	private List<String> arrResult;
	private ProgressDialog dialog;

	public API1ASyn(Handler handler, Context context) {
		super();
		this.handler = handler;
		this.context = context;
	}

	protected String doInBackground(String... params) {
		handler.post(new Runnable() {
			public void run() {
				dialog = ProgressDialog.show(context, null, "Downloading ...",
						true);
			}
		});

		RequestServerAdapter objRequestServerFirst = new RequestServerAdapter(
				params[0]);
		arrResult = objRequestServerFirst.parseXmlFirst();
		return null;
	}

	protected void onPostExecute(String result) {
		dialog.dismiss();

		if (arrResult.size() > 0) {
			String imageURL = "";
			String status = "";

			if ((arrResult.size() >= 1) && (arrResult.get(0) != null)) {
				status = arrResult.get(0);
			}

			if ((arrResult.size() >= 2) && (arrResult.get(1) != null)) {
				imageURL = arrResult.get(1);
			}

			Message message = null;
			if (status.equals("1")) {
				message = createMessage(Common.MESSAGE_WHAT_1);
			} else if (status.equals("0")) {
				message = createMessage(Common.MESSAGE_WHAT_0);
			}else{
				message = createMessage(Common.MESSAGE_WHAT_2);
			}
			
			if(message != null){
				Bundle data = new Bundle();
				data.putString(Common.ARG0, imageURL);
				message.setData(data);
				handler.sendMessage(message);
			}
		}else{
			Message message = createMessage(Common.MESSAGE_WHAT_2);
			Bundle data = new Bundle();
			data.putString(Common.ARG0, "");
			message.setData(data);
			handler.sendMessage(message);
		}
	}

	private Message createMessage(int What) {
		Message message = new Message();
		message.what = What;
		return message;
	}

}
