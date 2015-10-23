package com.vnp.shortfirmfestival_rework.adapter;

import com.vnp.shortfirmfestival_rework.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

public class MenuLeftAdapter implements ExpandableListAdapter {
	private int[][] groups = new int[][] { new int[] { R.drawable.new_active, R.string.nenu_group_new },//
			new int[] { R.drawable.photo_active, R.string.nenu_group_photo },//
			//new int[] { R.drawable.movie_active, R.string.nenu_group_movie },//
			new int[] { R.drawable.app_active, R.string.nenu_group_app },//
	};//
	private int[] menu1 = new int[] {//
	R.string.menu_child_1_all,//
			R.string.menu_child_1_festival,//
			R.string.menu_child_1_biz,//
			R.string.menu_child_1_lounge,//
			R.string.menu_child_1_theater,//
	};//
	private int[] menu2 = new int[] {//
	R.string.menu_child_2_all,//
			R.string.menu_child_2_festival,//
			R.string.menu_child_2_biz,//
			R.string.menu_child_2_lounge,//
			R.string.menu_child_2_theater,//
	};//

	private int[] menu3 = new int[] {//
	R.string.menu_child_3_youtube,//
			R.string.menu_child_3_ustream //
	};//

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {

	}

	@Override
	public int getGroupCount() {
		return groups.length;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (groupPosition == 0) {
			return menu1.length;
		}
		if (groupPosition == 1) {
			// return menu2.length;
			return 0;
		}
//		if (groupPosition == 2) {
//			return menu3.length;
//		}
		if (groupPosition == 3) {
			return 0;
		}
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups[groupPosition];
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		if (groupPosition == 0) {
			return menu1[childPosition];
		} else if (groupPosition == 1) {
			return menu2[childPosition];
		}
		//} else if (groupPosition == 2) {
		//	return menu3[childPosition];
		//}

		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.menu_group, null);
		}

		int[] items = groups[groupPosition];
		convertView.findViewById(R.id.group_img).setBackgroundResource(items[0]);
		((TextView) convertView.findViewById(R.id.group_txt)).setText(items[1]);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.menu_child, null);
		}

		TextView textView = (TextView) convertView.findViewById(R.id.child_txt);
		if (groupPosition == 0) {
			textView.setText(menu1[childPosition]);
		} else if (groupPosition == 1) {
			textView.setText(menu2[childPosition]);
		} 
//		else if (groupPosition == 2) {
//			textView.setText(menu3[childPosition]);
//		}
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean areAllItemsEnabled() {

		return false;
	}

	@Override
	public boolean isEmpty() {

		return false;
	}

	@Override
	public void onGroupExpanded(int groupPosition) {

	}

	@Override
	public void onGroupCollapsed(int groupPosition) {

	}

	@Override
	public long getCombinedChildId(long groupId, long childId) {

		return 0;
	}

	@Override
	public long getCombinedGroupId(long groupId) {

		return 0;
	}

}
