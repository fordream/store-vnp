package org.com.vnp.shortheart.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
	private static String DB_PATH = "/data/data/org.com.vnp.shortheart/databases/";
	private static String DB_NAME = "data.sqlite";
	private SQLiteDatabase myDataBase;
	private final Context myContext;

	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	public void onCreate(SQLiteDatabase db) {
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public synchronized void close() {
		if (myDataBase != null) {
			myDataBase.close();
		}

		super.close();
	}

	public void checkAndCopyDatabase() {
		boolean dbExist = checkDataBase();
		if (dbExist) {
		} else {
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
			}
		}
	}

	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
		} catch (SQLiteException e) {
		}
		if (checkDB != null)
			checkDB.close();
		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException {
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	public void openDataBase() throws SQLException {
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	public void exeSQLData(String sql) throws SQLException {
		myDataBase.execSQL(sql);
	}

	public Cursor QueryData(String query) throws SQLException {
		return myDataBase.rawQuery(query, null);
	}

	public SQLiteDatabase getMyDataBase() {
		return myDataBase;
	}

	public void setMyDataBase(SQLiteDatabase myDataBase) {
		this.myDataBase = myDataBase;
	}

	public void insertScore(int score) {
		ContentValues values = new ContentValues();
		values.put("score", score);
		myDataBase.insert("score", null, values);
	}

	public List<Integer> getListScore() {
		List<Integer> list = new ArrayList<Integer>();
		Cursor cursor = myDataBase.query("score", new String[] { "score" },
				null, null, null, null, "score");
		if (cursor != null) {
			while (cursor.moveToNext()) {
				Integer integer = cursor.getInt(0);
				list.add(integer);
			}
		}
		return list;
	}

}