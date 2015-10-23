package org.com.vnp.storeapp.database;

import java.util.ArrayList;

import org.com.cnc.common.android.database.table.CommonTable;
import org.com.vnp.storeapp.adapter.items.Item;

public class QuanAn extends CommonTable {
	public static final String DIADIEM = "diadiem";
	public static final String COMMENT = "comment";

	public QuanAn() {
		setTableName("quanan");

		addColoumName(DIADIEM);
		addColoumName(COMMENT);
	}

	public ArrayList<Item> create() {
		ArrayList<Item> lItems = new ArrayList<Item>();

		for (int i = 0; i < sizeOfRow(); i++) {
			lItems.add(new Item(getValue(i, DIADIEM), getValue(i, COMMENT)));
		}

		return lItems;
	}
}
