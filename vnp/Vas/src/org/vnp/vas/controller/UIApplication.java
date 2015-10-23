package org.vnp.vas.controller;

import org.vnp.vas.model.database.DBAdapter;

import android.app.Application;

import com.ict.library.database.CommonDataStore;

public class UIApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		CommonDataStore dataStore = CommonDataStore.getInstance();
		dataStore.init(this);

		DBAdapter adapter = new DBAdapter(this);
		adapter.createDB();

	}
}