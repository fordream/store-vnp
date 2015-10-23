package org.com.vnp.line98.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.com.vnp.line98.database.item.Score;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

	public List<Score> getLScore() {
		open();
		List<Score> list = new ArrayList<Score>();
		try {
			Cursor cursor = dataBaseHelper.getMyDataBase().query("score",
					new String[] { "name", "score" }, null, null, null, null,
					 "score");
			if (cursor != null) {
				while (cursor.moveToNext()) {
					Score score = new Score();
					score.setName(cursor.getString(0));
					score.setScore(cursor.getInt(1));
					list.add(score);
				}
			}
		} catch (Exception e) {
		}
		close();
		return list;
	}

	public void insert(String name, int score) {
		open();
		SQLiteDatabase database = dataBaseHelper.getMyDataBase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("score", score);
		database.insert("score", null, values);
		close();
	}

	public void close() {
		dataBaseHelper.close();
	}

	public void create() {
		open();
		close();
	}
}