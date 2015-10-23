package org.com.vnp.lmhtmanager.db;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonTable {
	public CommonTable() {
		addColoumList();
	}

	public abstract void addColoumList();

	private List<String> columName = new ArrayList<String>();
	private List<List<String>> data = new ArrayList<List<String>>();

	public abstract String getTableName();

	public abstract String getTableIDName();

	public boolean addColoumName(String name) {
		if (name == null || name != null && name.trim().equals("")) {
			return false;
		}
		
		boolean check = false;
		for (int i = 0; i < columName.size(); i++) {
			String columnName = columName.get(i);
			if (columnName.toUpperCase().equals(name.toUpperCase())) {
				check = true;
				break;
			}
		}

		if (!check) {
			columName.add(name);
		}

		return !check;
	}

	public int sizeOfColumn() {
		return columName.size();
	}

	public List<String> getHeaderList() {
		return columName;
	}

	public String getColumnName(int index) {
		if (index < sizeOfColumn()) {
			return columName.get(index);
		}

		return null;
	}

	public void addRow(List<String> row) {
		if (row != null && row.size() == sizeOfColumn()) {
			data.add(row);
		}
	}

	public int sizeOfRow() {
		return data.size();
	}

	public List<String> getRow(int index) {
		if (index < sizeOfRow()) {
			return data.get(index);
		}

		return null;
	}

	public int getIndexColumns(String columnsName) {
		if (columnsName == null) {
			return -1;
		}
		for (int i = 0; i < sizeOfColumn(); i++) {
			if (columnsName.equals(columName.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public String getValue(int row, int indexofColumn) {
		return getRow(row).get(indexofColumn);
	}

	public String getValue(int row, String nameColumn) {
		return getRow(row).get(getIndexColumns(nameColumn));
	}

	public void removeRow(int index) {
		data.remove(index);
	}
}
