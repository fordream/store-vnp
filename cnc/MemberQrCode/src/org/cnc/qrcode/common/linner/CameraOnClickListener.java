package org.cnc.qrcode.common.linner;

import org.cnc.qrcode.GlobalActivity;
import org.cnc.qrcode.R;
import org.cnc.qrcode.common.Common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

public class CameraOnClickListener implements OnClickListener {
	private Context context;
	private Handler handler;

	public CameraOnClickListener(Context context, Handler handler) {
		super();
		this.handler = handler;
		this.context = context;
	}

	public void onClick(View v) {
		((GlobalActivity) context).indexOfbutton = 2;
		try {
			if (GlobalActivity.questionContent == null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				String message = Common.getText((Activity) context,
						R.string.Your_must_input_your_key_number_trans);
				builder.setMessage(message);
				builder.setCancelable(false);
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Message message = new Message();
								message.what = Common.MESSAGE_WHAT_0;
								handler.sendMessage(message);
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
			} else {
				Message message = new Message();
				message.what = Common.MESSAGE_WHAT_2;
				handler.sendMessage(message);
			}
		} catch (Exception e) {
			Message message = new Message();
			message.what = Common.MESSAGE_WHAT_3;
			handler.sendMessage(message);

		}
	}

}
