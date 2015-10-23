package org.cnc.qrcode.adapter;

import java.util.List;

import org.cnc.qrcode.R;
import org.cnc.qrcode.views.RowView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ApaterRow extends ArrayAdapter<String> {
	List<String> lAddress;
	int resource;
	Context context;
	int position = -1;

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public ApaterRow(Context context, List<String> objects) {
		super(context, R.layout.row, objects);
		this.context = context;
		lAddress = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View workView = convertView;

		if (workView == null) {
			workView = new RowView(getContext());
		}

		final String address = lAddress.get(position);

		if (address != null) {
			((RowView) workView).setText(address);
			((RowView) workView).setChecked(this.position == position);
		}

		return workView;
	}
}