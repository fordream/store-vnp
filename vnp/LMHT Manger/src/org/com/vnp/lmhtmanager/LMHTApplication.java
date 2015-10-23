package org.com.vnp.lmhtmanager;

import org.com.vnp.lmhtmanager.db.DBAdapter;
import org.com.vnp.lmhtmanager.service.LMHTServiceManager;

import android.database.sqlite.SQLiteDatabase;

import com.vnp.core.base.activity.BaseApplication;

public class LMHTApplication extends BaseApplication {

	DBAdapter dbAdapter;

	@Override
	public void onCreate() {
		super.onCreate();

		dbAdapter = new DBAdapter(getApplicationContext(), "gameStats_vn_VN.sqlite", getPackageName());
		dbAdapter.createDB();
	}

	public SQLiteDatabase getSQLiteDatabase() {
		// dbAdapter = new DBAdapter(getApplicationContext(),
		// "gameStats_vn_VN.sqlite", getPackageName());
		dbAdapter.open();
		return dbAdapter.getSQLiteDatabase();
	}

	public void init() {
		LMHTServiceManager.getInstance().init(this);
	}
}
