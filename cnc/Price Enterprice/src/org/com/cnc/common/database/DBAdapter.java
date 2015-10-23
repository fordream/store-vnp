package org.com.cnc.common.database;

import java.sql.SQLException;

import android.content.Context;

public class DBAdapter {
	private DataBaseHelper dataBaseHelper;
	private Context context;

	public DBAdapter(Context context, String pakage, String filename) {
		this.context = context;
		dataBaseHelper = new DataBaseHelper(this.context, pakage, filename);
	}

	public DataBaseHelper getDataBaseHelper() {
		return dataBaseHelper;
	}

	public void setDataBaseHelper(DataBaseHelper dataBaseHelper) {
		this.dataBaseHelper = dataBaseHelper;
	}

	public void open() {
		try {
			dataBaseHelper.checkAndCopyDatabase();
			dataBaseHelper.openDataBase();
		} catch (SQLException sqle) {
		}
	}

	public void close() {
		dataBaseHelper.close();
	}
}