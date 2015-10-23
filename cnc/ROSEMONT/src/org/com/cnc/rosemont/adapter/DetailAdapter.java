package org.com.cnc.rosemont.adapter;

import java.util.ArrayList;
import java.util.List;
import org.com.cnc.common.android.Common;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.items.ItemSearch;
import org.com.cnc.rosemont.views.ItemDetailView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class DetailAdapter extends ArrayAdapter<ItemSearch> {
	private ListView listView;
	private int numberVIsibility = Common.SIZE_8;
	private List<ItemSearch> lItemSearchs = new ArrayList<ItemSearch>();

	public DetailAdapter(Context context, ListView listView,
			List<ItemSearch> lItemSearchs) {
		super(context, 0, lItemSearchs);
		this.listView = listView;
		this.lItemSearchs = lItemSearchs;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ItemSearch item = getItem(position);

		// if (item.getTxtHeader().equals(RosemontTable.VISCOSITY)) {
		// convertView = new ItemDetailView(getContext());
		// } else if (item.isChecked()) {
		// convertView = new ItemProductDetailView(getContext(),
		// ItemProductDetailView.TYPE_CHECK);
		// } else {
		// convertView = new ItemProductDetailView(getContext(),
		// ItemProductDetailView.TYPE_TEXT);
		// }

		convertView = new ItemDetailView(getContext());

		((ItemDetailView) convertView).updateData(item);

		if (listView != null) {
			int height = listView.getHeight() / numberVIsibility;
			LayoutParams params = null;
			params = new LayoutParams(LayoutParams.FILL_PARENT, height);
			convertView.setLayoutParams(params);
		}

		return convertView;
	}

	public void update(List<ItemSearch> lData) {
		lItemSearchs.clear();
		lItemSearchs.addAll(lData);
	}
}