package com.commonsware.cwac.endless.demo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;

import com.commonsware.cwac.endless.EndlessAdapter;
import com.commonsware.cwac.endless.demo.R;

public class DemoAdapter extends EndlessAdapter {

	private static final int MAX_SIZE = 100;

	private RotateAnimation rotate = null;

	private Context mContext;

	public DemoAdapter(Context mContext, ArrayList<Integer> list) {
		super(new ArrayAdapter<Integer>(mContext, R.layout.row,
				android.R.id.text1, list));

		this.mContext = mContext;
		rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotate.setDuration(600);
		rotate.setRepeatMode(Animation.RESTART);
		rotate.setRepeatCount(Animation.INFINITE);
	}

	@Override
	protected View getPendingView(ViewGroup parent) {
		View row = ((Activity) mContext).getLayoutInflater().inflate(
				R.layout.row, null);

		View child = row.findViewById(android.R.id.text1);

		child.setVisibility(View.GONE);

		child = row.findViewById(R.id.throbber);
		child.setVisibility(View.VISIBLE);
		child.startAnimation(rotate);

		return (row);
	}

	private List<Integer> data = null;

	@Override
	protected boolean cacheInBackground() {
		SystemClock.sleep(1000); // pretend to do work

		data = null;

		data = new ArrayList<Integer>();

		ArrayAdapter<Integer> a = (ArrayAdapter<Integer>) getWrappedAdapter();

		int count = a.getCount();

		for (int i = 0; i < MAX_SIZE / 4; i++) {
			data.add(count + i + 1);
		}

		// load Data
		return (getWrappedAdapter().getCount() + data.size() < MAX_SIZE);
	}

	@Override
	protected void appendCachedData() {
		if (getWrappedAdapter().getCount() < MAX_SIZE && data != null) {
			@SuppressWarnings("unchecked")
			ArrayAdapter<Integer> a = (ArrayAdapter<Integer>) getWrappedAdapter();

			for (int i = 0; i < data.size(); i++) {
				a.add(data.get(i));
			}
		}
	}
}