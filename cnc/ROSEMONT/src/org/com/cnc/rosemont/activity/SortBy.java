package org.com.cnc.rosemont.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.com.cnc.common.android.Common;
import org.com.cnc.rosemont.items.ItemSearch;

public class SortBy {
	public static List<ItemSearch> sortAndAddHeader(
			List<ItemSearch> lItemSearchs) {
		Comparator<ItemSearch> comparator = new Comparator<ItemSearch>() {
			public int compare(ItemSearch lhs, ItemSearch rhs) {
				return lhs.getTxtHeader().compareToIgnoreCase(
						rhs.getTxtHeader());
			}
		};

		Collections.sort(lItemSearchs, comparator);

		List<ItemSearch> addAnphabe = new ArrayList<ItemSearch>();
		String temp = "";
		for (int i = 0; i < lItemSearchs.size(); i++) {
			ItemSearch item = lItemSearchs.get(i);
			String header = null;
			if (Common.isBeginFromAToZ(item.getTxtHeader())) {
				header = item.getTxtHeader().toUpperCase().substring(0, 1);
			}
	
			if (header != null) {
				if (!header.equals(temp)) {
					temp = header;
					ItemSearch itemS = new ItemSearch();
					itemS.setAnphabe(true);
					itemS.setTxtHeader(header);
					addAnphabe.add(itemS);
				}
			}
			item.setAnphabe(false);
			addAnphabe.add(item);
		}
		return addAnphabe;
	}
}
