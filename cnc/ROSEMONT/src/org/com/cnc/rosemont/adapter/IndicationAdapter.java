package org.com.cnc.rosemont.adapter;

import java.util.List;

import org.com.cnc.rosemont.items.ItemSearch;

import android.content.Context;
import android.widget.ListView;

public class IndicationAdapter extends CategoryAdapter{

	public IndicationAdapter(Context context, ListView listView,
			List<ItemSearch> lItemSearchs) {
		super(context, listView, lItemSearchs);
		// TODO Auto-generated constructor stub
	}
//ArrayAdapter<ItemSearch> implements
//		SectionIndexer {
//	private ListView listView;
//	private int numberVIsibility = Common.SIZE_6;;
//	private List<ItemSearch> lItemSearchs = new ArrayList<ItemSearch>();
//	private HashMap<String, Integer> alphaIndexer = new HashMap<String, Integer>();
//	private String[] sections;
//
//	public IndicationAdapter(Context context, ListView listView,
//			List<ItemSearch> lItemSearchs) {
//		super(context, R.layout.item_search, lItemSearchs);
//		this.listView = listView;
//		this.lItemSearchs = lItemSearchs;
//	}
//
//	private void put() {
//		alphaIndexer = new HashMap<String, Integer>();
//		int size = lItemSearchs.size();
//
//		for (int x = 0; x < size; x++) {
//			ItemSearch s = lItemSearchs.get(x);
//			if (s.isAnphabe()) {
//				String ch = s.getTxtHeader().substring(0, 1);
//				ch = ch.toUpperCase();
//				alphaIndexer.put(ch, x);
//			}
//		}
//		Set<String> sectionLetters = alphaIndexer.keySet();
//		ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
//		Collections.sort(sectionList);
//		sections = new String[sectionList.size()];
//		sectionList.toArray(sections);
//	}
//
//	public int getPositionForSection(int section) {
//		return alphaIndexer.get(sections[section]);
//	}
//
//	public int getSectionForPosition(int position) {
//		return 1;
//	}
//
//	public Object[] getSections() {
//		return sections;
//	}
//
//	public ItemSearch getItem(int position) {
//		if (position > lItemSearchs.size()) {
//			return null;
//		}
//		return lItemSearchs.get(position);
//	}
//
//	public void setData(List<ItemSearch> lItemSearch) {
//		this.lItemSearchs.clear();
//		this.lItemSearchs.addAll(SortBy.sortAndAddHeader(lItemSearch));
//		put();
//		notifyDataSetChanged();
//	}
//
//	public View getView(int position, View convertView, ViewGroup parent) {
//		ItemSearch item = lItemSearchs.get(position);
//		if (!item.isAnphabe()) {
//			convertView = new ItemSearchView(getContext());
//		} else if (item.isAnphabe()) {
//			convertView = new AnphabeView(getContext());
//		}
//
//		int height = listView.getHeight() / numberVIsibility;
//		LayoutParams params;
//		if (height >= 0 && convertView instanceof ItemSearchView) {
//			params = new LayoutParams(LayoutParams.FILL_PARENT, height);
//			convertView.setLayoutParams(params);
//			((ItemSearchView) convertView).updateData(item);
//		} else if (height >= 0 && convertView instanceof AnphabeView) {
//			params = new LayoutParams(LayoutParams.FILL_PARENT, height / 2);
//			convertView.setLayoutParams(params);
//			((AnphabeView) convertView).updateData(item);
//		}
//
//		return convertView;
//	}
}
