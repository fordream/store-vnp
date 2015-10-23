package org.com.cnc.rosemont.adapter;

import java.util.List;

import org.com.cnc.common.android.Common;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.database.table.RosemontTable;
import org.com.cnc.rosemont.items.ItemSearch;
import org.com.cnc.rosemont.views.ItemProductDetailView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class ProductDetailAdapter extends ArrayAdapter<ItemSearch> {
	private ListView listView;
	private int numberVIsibility = Common.SIZE_8;

	public ProductDetailAdapter(Context context, ListView listView,
			List<ItemSearch> lItemSearchs) {
		super(context, R.layout.item_search, lItemSearchs);
		this.listView = listView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ItemSearch item = getItem(position);
		
		 if (item.getTxtHeader().equals(RosemontTable.VISCOSITY)) {
			convertView = new ItemProductDetailView(getContext(),
					ItemProductDetailView.TYPE_VISICOSITY);
		} else if (item.isChecked()) {
			convertView = new ItemProductDetailView(getContext(),
					ItemProductDetailView.TYPE_CHECK);
		} else {
			convertView = new ItemProductDetailView(getContext(),
					ItemProductDetailView.TYPE_TEXT);
		}

		((ItemProductDetailView) convertView).updateData(item);

		if (listView != null) {
			int height = listView.getHeight() / numberVIsibility;
			LayoutParams params = null;
			params = new LayoutParams(LayoutParams.FILL_PARENT, height);
			convertView.setLayoutParams(params);
		}

		return convertView;
	}
}