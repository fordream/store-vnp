package com.cnc.buddyup.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.cnc.buddyup.login.connect.ResponseLogin;
import com.cnc.buddyup.response.item.Age;
import com.cnc.buddyup.sign.paracelable.Country;

public class DBAdapter {
	private DataBaseHelper dataBaseHelper;
	private Context context;

	public DBAdapter(Context context) {
		this.context = context;
		dataBaseHelper = new DataBaseHelper(this.context);

	}

	private void open() {
		try {
			dataBaseHelper.checkAndCopyDatabase();
			dataBaseHelper.openDataBase();
		} catch (SQLException sqle) {
		}
	}

	private void close() {
		dataBaseHelper.close();
	}

	public Cursor getCurso(String strQuery) {
		Cursor cursor = null;
		try {
			cursor = dataBaseHelper.QueryData(strQuery);
		} catch (SQLException e) {
		}
		return cursor;
	}

	public boolean getStatus() {
		boolean check = false;
		open();
		Cursor cursor = getCurso("SELECT * from store");
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				check = "1".equals(cursor.getInt(1) + "");
			}
		}
		close();
		return check;

	}

	public ResponseLogin getUser() {
		ResponseLogin user = new ResponseLogin(false, "", "");
		open();
		Cursor cursor = getCurso("SELECT * from store");
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				boolean check = "1".equals(cursor.getInt(1) + "");
				String username = cursor.getString(2);
				String pass = cursor.getString(3);
				user = new ResponseLogin(check, username, pass);
			}
		}
		close();
		return user;

	}

	public String getUersName() {
		String username = null;
		open();
		Cursor cursor = getCurso("SELECT * from store");
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				username = cursor.getString(2);
			}
		}
		close();
		return username;
	}

	public String getPassword() {
		String pass = null;
		open();
		Cursor cursor = getCurso("SELECT * from store");
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				pass = cursor.getString(3);
			}
		}
		close();
		return pass;

	}

	public void updateStatus(int i, String userName, String password) {
		try {
			open();
			String query = "update store set status =  " + i
					+ " , username = '" + userName + "' , password = '"
					+ password + "'";
			dataBaseHelper.exeSQLData(query);
		} catch (SQLException e) {
		}
		close();
	}

	public List<Age> getLAge() {
		List<Age> lAges = new ArrayList<Age>();
		open();
		String[] columns = new String[] { "id", "value" };
		Cursor cursor = dataBaseHelper.getMyDataBase().query("age", columns,
				null, null, null, null, null);
		while (cursor.moveToNext()) {
			String id = cursor.getString(0);
			String value = cursor.getString(1);
			Age age = new Age();
			age.setId(id);
			age.setValue(value);

			lAges.add(age);
		}
		close();
		return lAges;
	}

	public void deleteAge() {
		open();
		dataBaseHelper.getMyDataBase().delete("age", null, null);
		close();
	}

	public List<Country> getLCountry() {
		List<Country> lAges = new ArrayList<Country>();
		open();
		String[] columns = new String[] { "id", "value" };
		Cursor cursor = dataBaseHelper.getMyDataBase().query("country",
				columns, null, null, null, null, null);
		while (cursor.moveToNext()) {
			String id = cursor.getString(0);
			String value = cursor.getString(1);
			lAges.add(new Country(id, value));
		}

		close();

		return lAges;
	}

	public void deleteCountry() {
		open();
		dataBaseHelper.getMyDataBase().delete("country", null, null);
		close();
	}
}