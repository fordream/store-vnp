package com.vnpgame.undersea.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.vnpgame.undersea.Score;

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

	public List<Score> getListScore() {
		List<Score> lScores = new ArrayList<Score>();
		open();
		String columns[] = { "id", "name", "score", "level" };
		try {
			Cursor cursor = dataBaseHelper.getMyDatabase().query("score",
					columns, null, null, null, null, null, null);
			// cursor.moveToFirst();
			while (cursor.moveToNext()) {
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				String score = cursor.getString(2);
				String level = cursor.getString(3);
				lScores.add(new Score(id, name, score, level));
			}
		} catch (Exception e) {
		}
		close();
		return lScores;
	}

	public void saveScore(int id, String name, String score, String level) {
		open();
		try {
			ContentValues values = new ContentValues();
			values.put("name", name);
			values.put("score", score);
			values.put("level", level);
			dataBaseHelper.getMyDatabase().insert("score", null, values);
		} catch (Exception e) {
		}
		close();
	}

	private void close() {
		dataBaseHelper.close();
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
}