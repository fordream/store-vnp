package org.com.cnc.common.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.Cursor;

public class DBAdapterPage {
	private DataBaseHelper dataBaseHelper;
	private Context context;

	public DBAdapterPage(Context context) {
		this.context = context;
		dataBaseHelper = new DataBaseHelper(this.context,
				"org.com.cnc.maispreco", "maisprecode.sqlite");
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

	public int getPage() {
		int page = 5;
		try {
			open();
			Cursor cursor = dataBaseHelper.QueryData("select * from page");
			cursor.moveToFirst();
			page = cursor.getInt(0);
			cursor.close();
			close();
		} catch (SQLException e) {
		}

		return page;
	}

	public boolean updatePage(int page) {
		try {
			open();
			String querry = "update page set page = '" + page + "'";
			dataBaseHelper.getMyDatabase().execSQL(querry);
			close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public String getLocationId() {
		String page = "";
		try {
			open();
			Cursor cursor = dataBaseHelper.QueryData("select * from location");
			cursor.moveToFirst();
			page = cursor.getString(0);
			cursor.close();
			close();
		} catch (SQLException e) {
		}

		return page;
	}

	public boolean updateLocation(String page) {
		try {
			open();
			String querry = "update location set id = '" + page + "'";
			dataBaseHelper.getMyDatabase().execSQL(querry);
			close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}