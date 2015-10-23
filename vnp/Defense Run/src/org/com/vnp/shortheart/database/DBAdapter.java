package org.com.vnp.shortheart.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class DBAdapter {
	private Context context;

	public DBAdapter(Context context) {
		this.context = context;

	}

	private DataBaseHelper open() {
		try {
			DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
			dataBaseHelper.checkAndCopyDatabase();
			dataBaseHelper.openDataBase();
			return dataBaseHelper;
		} catch (SQLException sqle) {
			return null;
		}
	}

	public void createDatabase() {
		close(open());
	}

	private void close(DataBaseHelper dataBaseHelper) {
		dataBaseHelper.close();
	}

	public void insertScore(int score) {
		DataBaseHelper helper = open();
		helper.insertScore(score);
		close(helper);
	}
	
	public List<Integer> getlScore(){
		List<Integer> list = new ArrayList<Integer>();
		DataBaseHelper helper = open();
		list = helper.getListScore();;
		close(helper);
		return list;
	}
}