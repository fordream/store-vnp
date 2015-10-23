package org.cnc.qrcode.widget;

import android.app.AlertDialog;
import android.content.Context;

public class DialogCancelSupport extends AlertDialog.Builder {

	public DialogCancelSupport(Context context, String title, String message) {
		super(context);
		setTitle(title);
		setCancelable(true);
		setMessage(message);

		setPositiveButton("OK", null);
	}
}
