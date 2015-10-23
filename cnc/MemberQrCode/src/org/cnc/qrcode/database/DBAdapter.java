package org.cnc.qrcode.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.Cursor;

public class DBAdapter {
	private DataBaseHelper dataBaseHelper;
	private Context context;

	public DBAdapter(Context context) {
		this.context = context;
		dataBaseHelper = new DataBaseHelper(this.context);

	}

	public void open() {
		try {
			dataBaseHelper.checkAndCopyDatabase();
			dataBaseHelper.openDataBase();
		} catch (SQLException sqle) {
		}
	}

	public Cursor getCurso(String strQuery) {
		try {
			return dataBaseHelper.QueryData(strQuery);
		} catch (SQLException e) {
			return null;
		}
	}

	public int getStatus() {

		Cursor cursor = getCurso("SELECT * from store");
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				return cursor.getInt(1);
			}
		}
		return 0;

	}

	public String getUersName() {

		Cursor cursor = getCurso("SELECT * from store");
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				return cursor.getString(2);
			}
		}
		return null;
	}

	public String getpassword() {

		Cursor cursor = getCurso("SELECT * from store");
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				return cursor.getString(3);
			}
		}
		return null;

	}

	public void updateStatus(int i, String userName, String password) {
		try {
			dataBaseHelper.exeSQLData("update store set status =  " + i
					+ " , username = '" + userName + "' , password = '"
					+ password + "'");
		} catch (SQLException e) {
		}
	}

	public void createDB() {
		open();
		close();
	}

	private void close() {
		dataBaseHelper.close();
	}
}