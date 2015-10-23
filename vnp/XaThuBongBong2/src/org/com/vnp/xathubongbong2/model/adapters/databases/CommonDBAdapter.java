package org.com.vnp.xathubongbong2.model.adapters.databases;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CommonDBAdapter {
	private static CommonDBAdapter COMMONDBADAPTER = null;

	public static CommonDBAdapter getInstance(Context ct, String dbName,
			String _package) {

		if (COMMONDBADAPTER == null) {
			COMMONDBADAPTER = new CommonDBAdapter(ct, dbName, _package);
		}

		return COMMONDBADAPTER;
	}

	private CommonDataBaseHelper dbHeper;

	private CommonDBAdapter(Context ct, String dbName, String packagename) {
		dbHeper = new CommonDataBaseHelper(ct, dbName, packagename);
	}

	public void open() {
		try {
			dbHeper.checkAndCopyDatabase();
			dbHeper.openDataBase();
		} catch (SQLException sqle) {
		}
	}

	public SQLiteDatabase getSQLiteDatabase() {
		return dbHeper.getMyDataBase();
	}

	public void close() {
		dbHeper.close();
	}

	public void createDB() {
		open();
		close();
	}

	public void insert(CommonTable table) {
		open();
		for (int i = 0; i < table.sizeOfRow(); i++) {
			ContentValues values = new ContentValues();
			List<String> row = table.getRow(i);
			for (int j = 0; j < table.sizeOfColumn(); j++) {
				values.put(table.getColumnName(j), row.get(j));
			}

			getSQLiteDatabase().insert(table.getTableName(), null, values);
		}
		close();
	}

	public void delete(CommonTable table) {
		open();
		getSQLiteDatabase().delete(table.getTableName(), null, null);
		close();
	}

	public void selectAll(CommonTable table) {
		open();
		String[] columns = new String[table.sizeOfColumn()];

		for (int i = 0; i < table.sizeOfColumn(); i++) {
			columns[i] = table.getColumnName(i);
		}

		Cursor cursor = getSQLiteDatabase().query(table.getTableName(),
				columns, null, null, null, null, null);
		while (cursor != null && cursor.moveToNext()) {
			List<String> row = new ArrayList<String>();
			for (int i = 0; i < table.sizeOfColumn(); i++) {
				row.add(cursor.getString(i));
			}

			table.addRow(row);
		}
		cursor.close();
		close();
	}
}