package org.cnc.qrcode.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.cnc.qrcode.common.Answer;
import org.cnc.qrcode.common._Return;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBHistoryAdapter {
	private DataHistoryBaseHelper dataBaseHelper;
	private Context context;

	public DBHistoryAdapter(Context context) {
		this.context = context;
		dataBaseHelper = new DataHistoryBaseHelper(this.context);

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

	public void insertHistory(String key, String time, _Return return1) {
		insert_Return(key, time, return1);
		insertNexPoint(time, return1);
	}

	private void insertNexPoint(String time, _Return return1) {
		open();
		for (int i = 0; i < return1.lAnswre.size(); i++) {
			try {
				Answer answer = return1.lAnswre.get(i);
				ContentValues values = new ContentValues();
				values.put("time", time);
				values.put("lat", answer.getLat());
				values.put("long", answer.getLog());
				values.put("name", answer.getName());
				values.put("address", answer.getAddress());
				values.put("url", answer.getUrl());
				values.put("_text", answer.getText());
				dataBaseHelper.getSQLiteDatabase().insert("nextpoint", null,
						values);
			} catch (Exception e) {
			}
		}
		close();
	}

	private void insert_Return(String key, String time, _Return return1) {
		open();
		ContentValues values = new ContentValues();
		values.put("key", key);
		values.put("time", time);
		values.put("address", return1.getAddress());
		values.put("lat", return1.getLat());
		values.put("long", return1.getLng());
		values.put("message", return1.getMessage());
		values.put("name", return1.getName());
		values.put("question", return1.getQuestion());
		values.put("success", return1.getSuccess());
		values.put("type", return1.getType());
		dataBaseHelper.getSQLiteDatabase().insert("_return", null, values);
		close();
	}

	public List<_Return> getHistory(String key) {
		List<_Return> lHistories = getList_Return(key);
		for (int i = 0; i < lHistories.size(); i++) {
			_Return return1 = lHistories.get(i);
			return1.lAnswre = getListAnser(return1.getTime());
		}
		return lHistories;
	}

	private List<Answer> getListAnser(String time) {
		List<Answer> l_Returns = new ArrayList<Answer>();
		open();
		String[] columns = new String[] { "time", "lat", "long", "name",
				"address", "url", "_text" };
		Cursor cursor = dataBaseHelper.getSQLiteDatabase().query("nextpoint",
				columns, "time=?", new String[] { time }, null, null, null);
		while (cursor != null && cursor.moveToNext()) {
			Answer return1 = new Answer();
			return1.setLat(cursor.getString(1));
			return1.setLog(cursor.getString(2));
			return1.setName(cursor.getString(3));
			return1.setAddress(cursor.getString(4));
			return1.setUrl(cursor.getString(5));
			return1.setText(cursor.getString(6));
			l_Returns.add(return1);
		}
		close();
		return l_Returns;
	}

	private List<_Return> getList_Return(String key) {
		List<_Return> l_Returns = new ArrayList<_Return>();
		open();
		String[] columns = new String[] { "key", "time", "address", "lat",
				"long", "message", "name", "question", "success", "type" };
		Cursor cursor = dataBaseHelper.getSQLiteDatabase().query("_return",
				columns, "key=?", new String[] { key }, null, null, null);
		while (cursor != null && cursor.moveToNext()) {
			_Return return1 = new _Return();
			return1.setTime(cursor.getString(1));
			return1.setAddress(cursor.getString(2));
			return1.setLat(cursor.getString(3));
			return1.setLng(cursor.getString(4));
			return1.setMessage(cursor.getString(5));
			return1.setName(cursor.getString(6));
			return1.setQuestion(cursor.getString(7));
			return1.setSuccess(cursor.getString(8));
			return1.setType(cursor.getString(9));
			l_Returns.add(return1);
		}
		close();
		return l_Returns;
	}
}