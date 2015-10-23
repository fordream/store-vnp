package org.com.vnp.xathubongbong2.model.adapters.databases;


import android.content.Context;

public class DBAdapter {
	private static CommonDBAdapter adapter;
	private static String DB_NAME = "";
	private static String PACKAGE = "org.com.vnp.xathubongbong2";

	public static CommonDBAdapter getInstance(Context ct) {
		if (adapter == null) {
			adapter = CommonDBAdapter.getInstance(ct, DB_NAME, PACKAGE);
			adapter.createDB();
		}

		return adapter;
	}

	// private DBAdapter(Context ct) {
	// adapter = CommonDBAdapter.getInstance(ct, DB_NAME, PACKAGE);
	// adapter.createDB();
	// }
}