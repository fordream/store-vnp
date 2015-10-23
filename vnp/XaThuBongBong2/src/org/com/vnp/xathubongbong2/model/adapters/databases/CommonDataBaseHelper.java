package org.com.vnp.xathubongbong2.model.adapters.databases;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class CommonDataBaseHelper extends SQLiteOpenHelper {
	private String DB_PATH = "/data/data/org.cnc.qrcode/databases/";
	private String DB_NAME = "/data/data/org.cnc.qrcode/databases/";

	private SQLiteDatabase myDataBase;
	private final Context myContext;

	public CommonDataBaseHelper(Context context, String dbName, String pkName) {
		super(context, dbName, null, 1);
		DB_PATH = "/data/data/" + pkName + "/databases/";
		DB_NAME = dbName;
		this.myContext = context;
	}

	public void onCreate(SQLiteDatabase db) {

	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public synchronized void close() {
		if (myDataBase != null)
			myDataBase.close();
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

	public SQLiteDatabase getMyDataBase() {
		return myDataBase;
	}
}
