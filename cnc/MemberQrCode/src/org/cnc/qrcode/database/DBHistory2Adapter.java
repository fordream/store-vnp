package org.cnc.qrcode.database;

import java.sql.SQLException;

import org.cnc.qrcode.database.item.History2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBHistory2Adapter {
	private DataHistory2BaseHelper dataBaseHelper;
	private Context context;

	public DBHistory2Adapter(Context context) {
		this.context = context;
		dataBaseHelper = new DataHistory2BaseHelper(this.context);

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

	public void createDB() {
		open();
		close();
	}

	public void deleteHistory() {
		// open();
		// dataBaseHelper.getSQLiteDatabase().delete(History2.TABLE_NAME, null,
		// null);
		// close();
	}

	public void insert(History2 history) {
		ContentValues values = new ContentValues();
		values.put(History2.COLUMNS[0], history.getKey());
		values.put(History2.COLUMNS[1], history.getMessge());
		values.put(History2.COLUMNS[2], history.getAddress());
		values.put(History2.COLUMNS[3], history.getLat());
		values.put(History2.COLUMNS[4], history.get_long());
		values.put(History2.COLUMNS[5], history.getUrl());
		open();
		dataBaseHelper.getSQLiteDatabase().insert(History2.TABLE_NAME, null,
				values);
		close();
	}

	public History2 getHistory(String key) {
		History2 history = new History2();
		open();

		Cursor cursor = dataBaseHelper.getSQLiteDatabase().query(
				History2.TABLE_NAME, History2.COLUMNS, "key=?",
				new String[] { key }, null, null, null);
		while (cursor != null && cursor.moveToNext()) {
			history = new History2();
			history.setKey(cursor.getString(0));
			history.setMessge(cursor.getString(1));
			history.setAddress(cursor.getString(2));
			history.setLat(cursor.getString(3));
			history.set_long(cursor.getString(4));
			history.setUrl(cursor.getString(5));
		}

		close();

		return history;
	}
}