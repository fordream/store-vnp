package org.com.vnp.storeapp.database;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android.database.CommonDBAdapter;
import org.com.vnp.storeapp.adapter.items.Item;

import android.content.Context;

public class DBAdapter extends CommonDBAdapter {

	private static DBAdapter _shareInstance = null;

	public static void init(Context context) {
		if (_shareInstance == null) {
			_shareInstance = new DBAdapter(context);
		}
	}

	public static DBAdapter getInstance() {
		return _shareInstance;
	}

	private DBAdapter(Context ct) {
		super(ct, "database.sqlite", "org.com.vnp.storeapp");
	}

	public void select(QuanAn table, String arg0) {
		selectAll(table);
		for (int i = 0; i < table.sizeOfRow(); i++) {
			if (table instanceof QuanAn) {
				if (!Common.contains(arg0, table.getValue(i, QuanAn.DIADIEM))) {
					table.removeRow(i);
					if (i > 0) {
						i--;
					}
				}
			}
		}
	}

	public void select(Plan table, String arg0) {
		selectAll(table);
		for (int i = 0; i < table.sizeOfRow(); i++) {
			if (table instanceof Plan) {
				if (!Common.contains(arg0, table.getValue(i, Plan.CONTENT))) {
					table.removeRow(i);
					if (i > 0) {
						i--;
					}
				}
			}
		}
	}

	public void delete(Plan plan, Item item) {
		open();
		String whereClause = " " + Plan.CONTENT + "=? and " + Plan.DATE
				+ " = ?";
		String[] whereArgs = new String[] { item.getTitle(), item.getContent() };
		getSQLiteDatabase().delete(plan.getTableName(), whereClause, whereArgs);
		close();
	}

	public void delete(QuanAn quanAn, Item item) {
		open();
		String whereClause = " " + QuanAn.DIADIEM + "=? and " + QuanAn.COMMENT
				+ " = ?";
		String[] whereArgs = new String[] { item.getTitle(), item.getContent() };
		getSQLiteDatabase().delete(quanAn.getTableName(), whereClause,
				whereArgs);
		close();

	}
}
