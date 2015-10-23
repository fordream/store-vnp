package org.com.cnc.rosemont;

import org.com.cnc.rosemont.database.DBAdapter;
import org.com.cnc.rosemont.database.DBAdapterData;

import android.app.Application;

public class RosemontApplication extends Application {
	@Override
	public void onCreate() {
		// CommonApp.createFolder();
		// CommonApp.reset();
		// CommonApp.DATA.reset();

		// DataStore.init(this);
		// DataStore.getInstance().setConfig(Conts.RE_SEARCH, false);
		// DataStore.getInstance().setConfig(Conts.TAB_ID, 0);
		// DataStore.getInstance().setConfig(Conts.BACKFROMCALCULATOR, false);
		// DataStore.getInstance().setConfig(Conts.SEARCH_RELOAD, false);

		new DBAdapter(this).createDB();
		new DBAdapterData(this).createDB();
		super.onCreate();
	}
}