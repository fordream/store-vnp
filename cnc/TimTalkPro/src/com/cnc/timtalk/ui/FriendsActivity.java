package com.cnc.timtalk.ui;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ExpandableListView;

import com.cnc.timtalk.ui.adapter.MyExpandAdapter;
import com.cnc.timtalk.ui.widget.ActionBar;

public class FriendsActivity extends ExpandableListActivity {

	ActionBar mActionBar;

	DisplayMetrics metrics;
	int width;
	ExpandableListView expList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_friends);
		mActionBar = (ActionBar) findViewById(R.id.actionbar);
		mActionBar.setTitle("Friends");

		expList = getExpandableListView();
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		width = metrics.widthPixels;
		// this code for adjusting the group indicator into right side of the
		// view
		expList.setIndicatorBounds(width - GetDipsFromPixel(50), width
				- GetDipsFromPixel(10));
		expList.setAdapter(new MyExpandAdapter(this));
	}

	public int GetDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}
}
