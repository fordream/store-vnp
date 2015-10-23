package com.cnc.buddyup.diaglog;

import android.app.AlertDialog.Builder;
import android.content.Context;

public class ProfileDialog extends Builder {

	public ProfileDialog(Context arg0, String title, String message) {
		super(arg0);
		setTitle(title);
		setMessage(message);
		setPositiveButton("Close", null);
		show();
	}
}
