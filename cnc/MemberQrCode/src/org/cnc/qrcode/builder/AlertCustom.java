package org.cnc.qrcode.builder;

import org.cnc.qrcode.common.Common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

public class AlertCustom extends AlertDialog.Builder {

	public AlertCustom(Context arg0, String title, String message,
			final Handler handler) {
		super(arg0);
		setTitle(title);
		setMessage(message);
		setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				sendMessgae(handler);
			}
		});

		setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					sendMessgae(handler);
				}
				return false;
			}

		});
	}

	private void sendMessgae(Handler handler) {
		Message message = new Message();
		message.what = Common.MESSAGE_WHAT_0;
		handler.sendMessage(message);
	}
}
