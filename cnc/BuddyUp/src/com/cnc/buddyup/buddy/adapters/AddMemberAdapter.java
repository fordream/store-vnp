package com.cnc.buddyup.buddy.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cnc.buddyup.R;
import com.cnc.buddyup.buddy.views.AddMemberItemView;

public class AddMemberAdapter extends ArrayAdapter<String> {
	private List<String> listData = new ArrayList<String>();

	public AddMemberAdapter(Context context, List<String> objects) {
		super(context, R.layout.addmemberitem, objects);
		listData = objects;
	}

	public AddMemberAdapter(Context context, int re, List<String> objects) {
		super(context, re, objects);
		listData = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View workView = convertView;

		if (workView == null) {
			workView = new AddMemberItemView(getContext());
		}

		final String item = listData.get(position);
		AddMemberItemView view = ((AddMemberItemView) workView);

		if (item != null) {
			view.showFooter(true);
			if (listData.size() == 1) {
				view.configBack(R.drawable.l4);
				view.showFooter(false);
				
			} else if (listData.size() > 1) {
				if (position == 0) {
					view.configBack(R.drawable.l1);
				} else {
					view.configBack(R.drawable.l2);
				}

				if (position == listData.size() - 1) {
					view.configBack(R.drawable.l3);
					view.showFooter(false);
				}
			}

			view.setData(item);
		}

		return workView;
	}
}
