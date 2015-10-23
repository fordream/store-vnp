package org.vnp.vas.model.database;

import com.ict.library.database.table.CommonTable;

public class TableMusic extends CommonTable {

	public TableMusic() {
		setTableName("music");
		addColoumName("ID");
		addColoumName("NAME");
	}
}