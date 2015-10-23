package org.com.cnc.rosemont.adapter;

import java.util.List;

import org.com.cnc.common.android.Common;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.views.ItemStrengListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class ListStrengthAdapter extends ArrayAdapter<String> {
	private ListView listView;
	private int numberVIsibility = Common.SIZE_8;

	public ListStrengthAdapter(Context context, ListView listView,
			List<String> lItemSearchs) {
		super(context, R.layout.item_search, lItemSearchs);
		this.listView = listView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		String item = getItem(position);
		convertView = new ItemStrengListView(getContext());
		int height = listView.getHeight() / numberVIsibility;
		LayoutParams params = null;
		params = new LayoutParams(LayoutParams.FILL_PARENT, height);
		convertView.setLayoutParams(params);
		((ItemStrengListView) convertView).setData(item);
		return convertView;
	}
}