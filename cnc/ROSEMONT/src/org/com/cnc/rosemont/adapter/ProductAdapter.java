package org.com.cnc.rosemont.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.com.cnc.common.android.Common;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.items.ItemSearch;
import org.com.cnc.rosemont.views.AnphabeView;
import org.com.cnc.rosemont.views.ItemSearchView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.SectionIndexer;

public class ProductAdapter extends ArrayAdapter<ItemSearch> implements
		SectionIndexer {
	private ListView listView;
	private int numberVIsibility = Common.SIZE_6;;
	private List<ItemSearch> lItemSearchs = new ArrayList<ItemSearch>();
	private HashMap<String, Integer> alphaIndexer = new HashMap<String, Integer>();
	private String[] sections;

	public ProductAdapter(Context context, ListView listView,
			List<ItemSearch> lItemSearchs) {
		super(context, R.layout.item_search, lItemSearchs);
		this.listView = listView;
		this.lItemSearchs = lItemSearchs;
	}

	private void put() {
		alphaIndexer = new HashMap<String, Integer>();
		int size = lItemSearchs.size();

		for (int x = 0; x < size; x++) {
			ItemSearch s = lItemSearchs.get(x);
			if (s.isAnphabe()) {
				String ch = s.getTxtHeader().substring(0, 1);
				ch = ch.toUpperCase();
				alphaIndexer.put(ch, x);
			}
		}
		Set<String> sectionLetters = alphaIndexer.keySet();
		ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
		Collections.sort(sectionList);
		sections = new String[sectionList.size()];
		sectionList.toArray(sections);
	}

	public int getPositionForSection(int section) {
		return alphaIndexer.get(sections[section]);
	}

	public int getSectionForPosition(int position) {
		return 1;
	}

	public Object[] getSections() {
		return sections;
	}

	public ItemSearch getItem(int position) {
		if (position > lItemSearchs.size()) {
			return null;
		}
		return lItemSearchs.get(position);
	}

	public void setData(List<ItemSearch> lItemSearch) {
		this.lItemSearchs.clear();
		this.lItemSearchs.addAll(lItemSearch);
		put();
		notifyDataSetChanged();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ItemSearch item = lItemSearchs.get(position);
		if (!item.isAnphabe()) {
			convertView = new ItemSearchView(getContext());
			convertView.clearFocus();
		} else if (item.isAnphabe()) {
			convertView = new AnphabeView(getContext());
			convertView.clearFocus();
		}

		int height = listView.getHeight() / numberVIsibility;
		LayoutParams params;
		if (height >= 0 && convertView instanceof ItemSearchView) {
			params = new LayoutParams(LayoutParams.FILL_PARENT, height);
			convertView.setLayoutParams(params);
			((ItemSearchView) convertView).updateData(item);
		} else if (height >= 0 && convertView instanceof AnphabeView) {
			params = new LayoutParams(LayoutParams.FILL_PARENT, height / 2);
			convertView.setLayoutParams(params);
			((AnphabeView) convertView).updateData(item);
		}
		convertView.clearFocus();
		return convertView;
	}
}
