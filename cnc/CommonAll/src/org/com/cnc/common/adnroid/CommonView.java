package org.com.cnc.common.adnroid;

import android.app.AlertDialog.Builder;
import android.content.Context;

public class CommonView {
	public static void viewDialog(Context context, String title, String message) {
		Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setTitle(title);
		builder.setNegativeButton("Close", null);
		builder.show();
	}
}
