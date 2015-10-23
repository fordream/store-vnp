package com.cnc.android.timtalk.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.cnc.android.timtalk.ui.R;

public class MyExpandAdapter extends BaseExpandableListAdapter {
	static final String arrGroupelements[] = { "CNC Software", "CNC Mobile",
			"CNC Media", "CNC R&D" };
	static final String arrChildelements[][] = {
			{ "Jennifer Phạm", "Jennifer Phạm", "Jennifer Phạm",
					"Jennifer Phạm" },
			{ "Jennifer Phạm", "Jennifer Phạm", "Jennifer Phạm" },
			{ "Jennifer Phạm", "Jennifer Phạm", "Jennifer Phạm" },
			{ "Jennifer Phạm", "Jennifer Phạm", "Jennifer Phạm" } };
	Context mContext;

	public MyExpandAdapter(Context context) {

		this.mContext = context;
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_child_row, null);
		}

		TextView tvPlayerName = (TextView) convertView
				.findViewById(R.id.tvPlayerName);
		tvPlayerName.setText(arrChildelements[groupPosition][childPosition]);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return arrChildelements[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return arrGroupelements.length;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return arrGroupelements.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_group_row, null);
		}

		TextView tvGroupName = (TextView) convertView
				.findViewById(R.id.tvGroupName);
		tvGroupName.setText(arrGroupelements[groupPosition] + " ("
				+ arrChildelements[groupPosition].length + ")");

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

}
