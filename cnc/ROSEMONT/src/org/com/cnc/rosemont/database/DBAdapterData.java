package org.com.cnc.rosemont.database;

import java.util.List;

import org.com.cnc.common.android.database.CommonDBAdapter;
import org.com.cnc.common.android.database.table.CommonTable;
import org.com.cnc.rosemont.database.table.RosemontTable;

import android.content.Context;

public class DBAdapterData extends CommonDBAdapter {
	public DBAdapterData(Context ct) {
		super(ct, "rosementdata.sqlite", "org.com.cnc.rosemont");
	}

	public void delete(RosemontTable responseDelete, boolean b) {
		open();

		for (int i = 0; i < responseDelete.sizeOfRow(); i++) {
			String whereClause = " " + RosemontTable.ID_PRODUCT + " = '"
					+ responseDelete.getValue(i, RosemontTable.ID_PRODUCT)
					+ "'";
			getSQLiteDatabase().delete(responseDelete.getTableName(),
					whereClause, null);
		}

		close();
	}

	@Override
	public void selectAll(CommonTable table) {
		super.selectAll(table);

		for (int i = 0; i < table.sizeOfRow(); i++) {
			List<String> row = table.getRow(i);
			if ("0".equals(table.getValue(i, "published"))) {
				table.removeRow(i);
				if (i > 0) {
					i--;
				}
			}
		}
	}
}