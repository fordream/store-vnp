package org.cnc.qrcode.adapter;

import java.util.List;

import org.cnc.qrcode.R;
import org.cnc.qrcode.common._Return;
import org.cnc.qrcode.views.ItemHistoryView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class AdapterHistory extends ArrayAdapter<_Return> {
	private static final int NUM_ROW = 6;
	private ListView listView;

	public AdapterHistory(Context context, List<_Return> objects,
			ListView listView) {
		super(context, R.layout.item_history, objects);
		this.listView = listView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		_Return return1 = getItem(position);

		convertView = new ItemHistoryView(getContext());
		
		int width = listView.getWidth();
		int height = listView.getHeight() / NUM_ROW;
		LayoutParams params = new LayoutParams(width, height);
		convertView.setLayoutParams(params);
		
		((ItemHistoryView) convertView).setData(return1);
		return convertView;
	}
}
