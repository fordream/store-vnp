package org.cnc.qrcode.asyn;

import java.util.ArrayList;
import java.util.List;

import org.cnc.qrcode.GlobalActivity;
import org.cnc.qrcode.R.string;
import org.cnc.qrcode.adapter.RequestServerAdapter;
import org.cnc.qrcode.common.Answer;
import org.cnc.qrcode.common.Common;
import org.cnc.qrcode.common._Return;
import org.cnc.qrcode.config.Config;
import org.cnc.qrcode.database.DBHistoryAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class API2ASyn extends AsyncTask<String, String, String> {
	public static List<Answer> lAnswer = new ArrayList<Answer>();
	public static List<String> lContent = new ArrayList<String>();
	private Handler handler;
	private Context context;
	private ProgressDialog dialog;

	public API2ASyn(Handler handler, Context context) {
		super();
		this.handler = handler;
		this.context = context;
	}

	private _Return _return = null;

	protected String doInBackground(String... params) {
		handler.post(new Runnable() {
			public void run() {
				String message = Common.getString(context,
						string.searching_trans);
				dialog = ProgressDialog.show(context, null, message, true);
			}
		});
		RequestServerAdapter objRequest = new RequestServerAdapter(params[0]);
		_return = objRequest.parseXML1();
		String success = _return.getSuccess();
		String type = _return.getType();

		if (success == null || type == null) {
		} else {
			String key = GlobalActivity.questionContent;
			String time = Common.createDate();
			new DBHistoryAdapter(context).insertHistory(key, time, _return);
		}

		return null;
	}

	protected void onPostExecute(String result) {
		dialog.dismiss();
		lAnswer.clear();
		lContent.clear();
		String success = _return.getSuccess();
		String type = _return.getType();

		Config.getInstance().reset();
		Config.getInstance().errorkey = _return.getErrorCode();
		Config.getInstance().start = _return.getTimeStart();
		Config.getInstance().end = _return.getTimeEnd();

		if (success == null || type == null) {
			handler.sendMessage(createMessage(Common.MESSAGE_WHAT_6));
		} else if ("0".equals(success)) {
			// search not found
			Message message = createMessage(Common.MESSAGE_WHAT_7);
			Bundle data = new Bundle();
			data.putString("message", _return.getMessage());
			data.putString("url", null);

			message.setData(data);
			handler.sendMessage(message);
		} else if ("1".equals(success)) {
			if ("0".equals(type)) {
				Message message = createMessage(Common.MESSAGE_WHAT_7);
				Bundle data = new Bundle();
				data.putString("message", _return.getMessage());
				data.putString("lat", _return.getLat());
				data.putString("lng", _return.getLng());
				data.putString("address", _return.getAddress());
				data.putString("url", _return.getUrl());
				data.putParcelable(Common.ARG_PARCELABLE, _return);
				message.setData(data);
				handler.sendMessage(message);
			} else if ("1".equals(type) || "4".equals(type)) {
				Message message = createMessage(Common.MESSAGE_WHAT_7);
				Bundle data = new Bundle();
				data.putString("message", _return.getMessage());
				data.putString("address", _return.getAddress());
				if ("1".equals(type)) {
					data.putString("lat", _return.getNextPoint().getLat());
					data.putString("lng", _return.getNextPoint().getLog());
					data.putString("url", _return.getNextPoint().getUrl());
				} else {
					data.putString("lat", _return.getLat());
					data.putString("lng", _return.getLng());
					data.putString("url", _return.getUrl());
				}
				message.setData(data);
				handler.sendMessage(message);

			} else if ("2".equals(type) || "3".equals(type)) {
				lAnswer.clear();
				lContent.clear();
				lContent.addAll(_return.lCommon);
				if ("2".equals(type)) {
					Answer answer = new Answer();
					answer.setText(null);
					answer.setLat(_return.getNextPoint().getLat());
					answer.setLog(_return.getNextPoint().getLog());
					answer.setAddress(_return.getNextPoint().getAddress());
					answer.setUrl(_return.getNextPoint().getUrl());
					lAnswer.add(answer);
				} else {
					lAnswer.addAll(_return.lAnswre);
				}

				// View message
				Message message = createMessage(Common.MESSAGE_WHAT_8);
				handler.sendMessage(message);
			}
		}
	}

	private Message createMessage(int What) {
		Message message = new Message();
		message.what = What;
		return message;
	}
}