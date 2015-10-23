package org.com.shoppie.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

public class MPagerAdapter extends PagerAdapter {
	protected List<Object> list = new ArrayList<Object>();

	public MPagerAdapter(Context ctx) {
		list.clear();
	}

	public void addData(List<Object> list2) {
		list.clear();
		list.addAll(list2);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object instantiateItem(View collection, int position) {
		TextView view = new TextView(collection.getContext());
		view.setText(list.get(position).toString());
		((ViewPager) collection).addView(view);
		return view;
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void startUpdate(View arg0) {
	}

	@Override
	public void finishUpdate(View arg0) {
	}

}
