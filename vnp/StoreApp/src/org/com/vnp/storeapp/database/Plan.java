package org.com.vnp.storeapp.database;

import java.util.ArrayList;

import org.com.cnc.common.android.database.table.CommonTable;
import org.com.vnp.storeapp.adapter.items.Item;

public class Plan extends CommonTable {

	public static final String CONTENT = "content";
	public static final String DATE = "date";

	public Plan() {
		setTableName("plan");

		addColoumName(CONTENT);
		addColoumName(DATE);
	}

	public ArrayList<Item> create() {
		ArrayList<Item> lItems = new ArrayList<Item>();

		for (int i = 0; i < sizeOfRow(); i++) {
			lItems.add(new Item(getValue(i, CONTENT), getValue(i, DATE)));
		}

		return lItems;
	}
}
