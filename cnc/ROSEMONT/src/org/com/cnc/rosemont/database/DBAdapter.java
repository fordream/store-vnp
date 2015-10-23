package org.com.cnc.rosemont.database;

import org.com.cnc.common.android.database.CommonDBAdapter;
import org.com.cnc.common.android.database.table.CommonTable;
import org.com.cnc.rosemont.database.table.Config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBAdapter extends CommonDBAdapter {

	public DBAdapter(Context ct) {
		super(ct, "rosement.sqlite", "org.com.cnc.rosemont");
	}

	public void update(Config config) {
		open();
		ContentValues values = new ContentValues();

		values.put(Config.APPVERTION, config.getAppversion() + "");
		values.put(Config.LASTUPDATE, config.getLastupdate() + "");

		String txt = (config.getRemovethestartup() + "").toLowerCase();
		boolean check = "true".equals(txt);
		values.put(Config.REMOVETHESTARTUP, check + "");

		String txt1 = (config.getWifiupdate() + "").toLowerCase();
		boolean check1 = "true".equals(txt1);
		values.put(Config.WIFIUPDATE, check1 + "");

		getSQLiteDatabase().update(Config.TABLE_NAME, values, null, null);
		close();
	}

	public Config getConfig() {
		Config config = new Config();
		open();
		String[] columns = new String[] { Config.APPVERTION, Config.LASTUPDATE,
				Config.REMOVETHESTARTUP, Config.WIFIUPDATE };
		Cursor cursor = getSQLiteDatabase().query(Config.TABLE_NAME, columns,
				null, null, null, null, null);
		while (cursor != null && cursor.moveToNext()) {
			config.setAppversion(cursor.getString(0));
			config.setLastupdate(cursor.getString(1));
			config.setRemovethestartup(cursor.getString(2));
			config.setWifiupdate(cursor.getString(3));
		}
		cursor.close();
		close();
		return config;
	}
@Override
public void selectAll(CommonTable table) {
	
	super.selectAll(table);
}
}
