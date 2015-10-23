package org.com.cnc.common.android.contact;


import android.content.Context;
import android.os.Build;

public class ContactAdapter {
	@SuppressWarnings("static-access")
	public static ContactCommon getContactCommon(Context context,
			Build.VERSION build) {
		if (build.SDK_INT < 5) {
			return new ContactCommon1x(context);
		}
		return new ContactCommon1x(context);
	}
}
