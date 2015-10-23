package com.cnc.maispreco.views;

import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.Common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

public class DialogCustom extends AlertDialog.Builder {
	private Context context;
	private Handler handler;
	public static final String title = "\"Mais Preço\" would like to use your Current Location";
	public static final String message = "Mais Preço needs to user your location to load data from server";

	public DialogCustom(Context arg0, Handler handler1) {
		super(arg0);
		this.context = arg0;
		this.handler = handler1;
		setCancelable(false);
		String title = context.getResources().getString(R.string.title);
		String message = context.getResources().getString(R.string.message);
		setTitle(title);
		setMessage(message);
		setNegativeButton("Allow", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				final Intent poke = new Intent();
				poke.setClassName("com.android.settings",
						"com.android.settings.widget.SettingsAppWidgetProvider");
				poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
				poke.setData(Uri.parse("3"));
				context.sendBroadcast(poke);

				Message message = new Message();
				message.what = Common.MESSAGE_WHAT_2;
				handler.sendMessage(message);
			}
		});

		setPositiveButton("Don't allow", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Message message = new Message();
				message.what = Common.MESSAGE_WHAT_2;
				handler.sendMessage(message);
			}
		});
	}
}