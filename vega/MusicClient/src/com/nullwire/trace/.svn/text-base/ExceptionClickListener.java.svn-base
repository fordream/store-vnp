package com.nullwire.trace;

import java.lang.ref.WeakReference;

import vn.com.vega.music.utils.Const;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

public class ExceptionClickListener implements OnClickListener {
	public static String LOG_TAG = Const.LOG_PREF + ExceptionClickListener.class.getSimpleName();

	WeakReference<Context> context;

	public ExceptionClickListener() {
	}

	public void onClick(DialogInterface dialog, int whichButton) {
		switch (whichButton) {
		case DialogInterface.BUTTON_POSITIVE:
			dialog.dismiss();
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			dialog.dismiss();
			Log.d(LOG_TAG, "Deleting old stack traces.");
			new Thread(new Runnable() {
				public void run() {
					ExceptionHandler.removeStackTraces();
				}
			}).start();
			break;
		default:
			Log.d(LOG_TAG, "Got unknown button click: " + whichButton);
			dialog.cancel();
		}
	}
}
