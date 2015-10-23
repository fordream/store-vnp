package org.com.vnp.storeapp.adapter;

import java.util.ArrayList;

import org.com.vnp.storeapp.adapter.items.Item;
import org.com.vnp.storeapp.views.ItemList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class Adapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Item> lItems;

	public Adapter(Context context, ArrayList<Item> list) {
		mContext = context;
		lItems = list;
	}

	public void addData(ArrayList<Item> list) {
		lItems.clear();
		lItems.addAll(list);
		notifyDataSetChanged();
	}

	public int getCount() {
		return lItems.size();
	}

	public Object getItem(int arg0) {
		return lItems.get(arg0);
	}

	public long getItemId(int arg0) {
		return 0;
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		return new ItemList(mContext).addData(lItems.get(arg0));
	}

}
