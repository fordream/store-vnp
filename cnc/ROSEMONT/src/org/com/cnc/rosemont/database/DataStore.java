package org.com.cnc.rosemont.database;

import android.content.Context;

public class DataStore {

	private static DataStore _shareInstance;

	private static Context _shareContext;

	private static String NAME = "DataStore";

	public static void init(Context context) {
		if (_shareInstance == null) {
			_shareInstance = new DataStore();
		}

		if (_shareContext == null) {
			_shareContext = context;
		}
	}

	public static DataStore getInstance(String NAME_DATA) {

		// use data default
		NAME = DataStore.class.getName();

		if (NAME_DATA != null) {
			NAME = NAME_DATA;
		}

		if (_shareInstance == null) {
			_shareInstance = new DataStore();
		}

		return _shareInstance;
	}

	public static DataStore getInstance() {

		// use data default
		NAME = "DataStore";

		if (_shareInstance == null) {
			_shareInstance = new DataStore();
		}

		return _shareInstance;
	}

	public final void setConfig(String key, String value) {
		_shareContext.getSharedPreferences(NAME, 0).edit()
				.putString(key, value).commit();
	}

	public final void setConfig(String key, int value) {
		_shareContext.getSharedPreferences(NAME, 0).edit().putInt(key, value)
				.commit();
	}

	public final void setConfig(String key, boolean value) {
		_shareContext.getSharedPreferences(NAME, 0).edit()
				.putBoolean(key, value).commit();
	}

	public final void setConfig(String key, float value) {
		_shareContext.getSharedPreferences(NAME, 0).edit().putFloat(key, value)
				.commit();
	}

	public final void setConfig(String key, long value) {
		_shareContext.getSharedPreferences(NAME, 0).edit().putLong(key, value);
	}

	public final long getConfig(String key, long value) {
		return _shareContext.getSharedPreferences(NAME, 0).getLong(key, value);
	}

	public final int getConfig(String key, int value) {
		return _shareContext.getSharedPreferences(NAME, 0).getInt(key, value);
	}

	public final boolean getConfig(String key, boolean value) {
		return _shareContext.getSharedPreferences(NAME, 0).getBoolean(key,
				value);
	}

	public final String getConfig(String key, String value) {
		return _shareContext.getSharedPreferences(NAME, 0)
				.getString(key, value);
	}

	public final String getConfig(String key) {
		return getConfig(key, "");
	}

	public int getCount() {
		int count = _shareContext.getSharedPreferences(NAME, 0).getInt(
				"COUNT_ID", -1) + 1;

		if (count >= 10000) {
			count = 0;
		}

		setConfig("COUNT_ID", count);

		return count;
	}
}