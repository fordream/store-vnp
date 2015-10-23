/**
 * 
 */
package com.vnp.core.v2;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.vnp.core.base.view.CustomLinearLayoutView;

/**
 * @author tvuong1pc
 * 
 */
public abstract class BaseAdapter extends ArrayAdapter<Object> {
	private List<Object> lData;

	/**
	 * @param context
	 * @param textViewResourceId
	 */
	public BaseAdapter(Context context, List<Object> lData) {
		super(context, 0, lData);
		this.lData = lData;
	}

	public void addAllList(List<Object> list) {
		this.lData.clear();
		this.lData.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Object data = getItem(position);

		if (convertView == null) {
			convertView = getView(getContext(), data);
		}

		((CustomLinearLayoutView) convertView).setData(data);
		((CustomLinearLayoutView) convertView).setGender();
		((CustomLinearLayoutView) convertView).showHeader(isShowHeader(position));
		return convertView;
	}

	/**
	 * 
	 * @param position
	 * @return
	 */
	public abstract boolean isShowHeader(int position);

	public abstract CustomLinearLayoutView getView(Context context, Object data);
}