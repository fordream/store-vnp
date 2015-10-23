package com.cnc.buddyup;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class CustomTabsActivity extends TabActivity implements
		OnTabChangeListener {
	private TabHost tabHost;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);
		Intent intent = null;
		//intent = new Intent(this, ProfileScreen.class);
		//addTab(intent, R.drawable.icon, 4, "Profile");

		intent = new Intent(this, BuddiesScreen.class);
		addTab(intent, R.drawable.icon, 0, "Buddies");
		intent = new Intent(this, SearchScreen.class);
		addTab(intent, R.drawable.icon, 1, "Search");
		intent = new Intent(this, ActivityScreen.class);
		addTab(intent, R.drawable.icon, 2, "Activity");
		intent = new Intent(this, MessageScreen.class);
		addTab(intent, R.drawable.icon, 3, "Message");
		 intent = new Intent(this, ProfileScreen.class);
		 addTab(intent, R.drawable.icon, 4, "Profile");
		config("tab0");

	}

	private ImageView[] views = new ImageView[5];

	private void addTab(Intent intent, int drawableId, int index, String text) {
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + index);

		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, getTabWidget(), false);
		// views[index] = tabIndicator;
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(text);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);

		views[index] = icon;

		icon.setImageResource(drawableId);

		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return true;
	}

	public void onTabChanged(String tabId) {
		config(tabId);
	}

	private void config(String tabId) {
		int tabCurent = 0;
		if (tabId.equals("tab0")) {
			tabCurent = 0;
		} else if (tabId.equals("tab1")) {
			tabCurent = 1;
		} else if (tabId.equals("tab2")) {
			tabCurent = 2;
		} else if (tabId.equals("tab3")) {
			tabCurent = 3;
		} else if (tabId.equals("tab4")) {
			tabCurent = 4;
		}

		config(tabCurent == 0, R.drawable.buddy2, R.drawable.buddy1, 0);
		config(tabCurent == 1, R.drawable.search2, R.drawable.search1, 1);
		config(tabCurent == 2, R.drawable.activity2, R.drawable.activity1, 2);
		config(tabCurent == 3, R.drawable.message2, R.drawable.message1, 3);
		config(tabCurent == 4, R.drawable.profile2, R.drawable.profile1, 4);

	}

	private void config(boolean b, int contact12, int contact001, int i) {
		if (views[i] != null) {
			views[i].setImageResource(b ? contact12 : contact001);
		}
	}
}