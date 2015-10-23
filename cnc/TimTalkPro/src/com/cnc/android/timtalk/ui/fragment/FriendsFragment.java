package com.cnc.android.timtalk.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.cnc.android.timtalk.ui.R;
import com.cnc.android.timtalk.ui.adapter.MyExpandAdapter;

public class FriendsFragment extends Fragment {
	DisplayMetrics metrics;
	int width;
	ExpandableListView expList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View fragView = inflater.inflate(R.layout.layout_friends, container,
				false);
		expList = (ExpandableListView) fragView.findViewById(R.id.exlist);
		metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		width = metrics.widthPixels;
		// this code for adjusting the group indicator into right side of the
		// view
		expList.setIndicatorBounds(width - GetDipsFromPixel(50), width
				- GetDipsFromPixel(10));
		expList.setAdapter(new MyExpandAdapter(getActivity()));

		return fragView;
	}

	public int GetDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}
}
