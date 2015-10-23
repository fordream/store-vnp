package org.com.vnp.caothubongbong.db;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.adnroid16.database.CommonDBAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class DBAdapter extends CommonDBAdapter {

	public DBAdapter(Context ct) {
		super(ct, "shootballoons.sqlite", "org.com.vnp.caothubongbong");
	}

	public void insertScore(int score) {
		String table = "score";
		
		ContentValues values = new ContentValues();
		values.put("score", score);
		open();
		getSQLiteDatabase().insert(table, null, values);
		close();
	}

	public List<Integer> getLScore() {
		List<Integer> lScore = new ArrayList<Integer>();
		open();
		Cursor cursor = getSQLiteDatabase().query("score",
				new String[] { "score" }, null, null, null, null, "score DESC");
		Log.i("AAAAA", cursor + "")
;		if (cursor != null) {
			while (cursor.moveToNext()) {
				lScore.add(cursor.getInt(0));
			}
		}
		close();
		return lScore;
	}
}
