package org.com.cnc.rosemont.database.table;

import android.content.Context;
import android.content.SharedPreferences;

public class First {
	private static final String PREFS_NAME = "CONFIG";

	private static final String KEY = "KEY1";

	private boolean isFirst = false;

	public First(Context context) {
		SharedPreferences settings = context
				.getSharedPreferences(PREFS_NAME, 0);
		isFirst = settings.getBoolean(KEY, true);
	}

	public void save(Context context) {
		SharedPreferences settings = context
				.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(KEY, isFirst);
		
		editor.commit();
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
}
